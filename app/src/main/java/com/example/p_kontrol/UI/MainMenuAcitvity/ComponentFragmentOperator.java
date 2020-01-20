package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.util.Log;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.TopMessageBar.FragTopMessageBar;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;

import java.util.List;
/**
 * @responsibilty to contain all the responsibility for initiating, knowing, and using the map.
 * */
class ComponentFragmentOperator implements IFragmentOperator {

    // Android Specifics
    private MainMenuActivity context;
    private String TAG = this.getClass().getName();

    // Views and Fragments
    private View view;
    private FragMessageWrite fragment_messageWrite  ;
    private FragTopMessageBar fragment_topMessage   ;

    // The ViewPager
    private FragmentPagerAdapter adapter_TipBobbles ;
    private ViewPager viewPager_tipBobles           ;

    //Specials
    private FragmentManager fragmentManager         ;
    private FragmentTransaction transaction         ;

    //Booleans for Open Closing Fragments.
    private boolean boolFragMessageWrite    ;
    private boolean boolFragTipBobble       ;
    private boolean boolFragTopMessageBar   ;

    // Data Access
    private LiveDataViewModel model;
    private LiveData<List<ITipDTO>> tipList;

    /**
     * @responsibilty to contain all the responsibility for initiating, knowing, and using the map.
     *
     *  ComponentFragmentOperator is the Component which has the Delegated responsibility to Manage the Opening and Closing of Fragments. Not when and where they open, but simply to open them
     *  @param context  the Parent Activity, such that the reference can be passed on to the fragments. its necessary due to lifeCycle things.
     *  @param view     the layout view, needed to search for xml views in the layout.
     *
     *  The Fragments it manages .
     *  @see {@link com.example.p_kontrol.UI.WriteTip.FragMessageWrite}
     *  @see {@link com.example.p_kontrol.UI.ReadTips.FragTipBobble}
     *  @see {@link com.example.p_kontrol.UI.TopMessageBar.FragTopMessageBar}
     *
     *  Relevant Listeners , only for writing tips.
     *  @See {@link com.example.p_kontrol.UI.WriteTip.ITipWriteListener}
     *
     *  Relevant Adapters , only for reading tips.
     *  @See {@link com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter}
     *
     * */
    ComponentFragmentOperator(MainMenuActivity context, View view){

        this.context = context;
        this.view = view;

        fragmentManager = context.getSupportFragmentManager();
        //TipBobbles are all inside this ViewPager Container
        viewPager_tipBobles = this.view.findViewById(R.id.mainMenu_viewPager_TipBobbles);
        viewPager_tipBobles.setVisibility(ViewPager.GONE);

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;

        //Open topMessageBar. is not Opened from anywhere but here, but hidden and Shown.
        fragment_topMessage   = new FragTopMessageBar() ;
        FragmentToggleTransaction(R.id.mainMenu_topMsgBarContainer,  fragment_topMessage, true);

        // Live Data list , that calls adapter to notify of changes when changes are made.
        model = ViewModelProviders.of(context).get(LiveDataViewModel.class);
        tipList = model.getTipList();
        tipList.observe(context, list -> {
            try {
                adapter_TipBobbles.notifyDataSetChanged();
            }catch (NullPointerException e){
                Log.i(TAG, "ComponentFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );
        adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, tipList,this);
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void openWriteTip(ITipWriteListener writeListener) {
        fragment_messageWrite = new FragMessageWrite(writeListener,this.context);
        FragmentToggleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , true);
        boolFragMessageWrite = true;
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void closeWriteTip(){
        FragmentToggleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , false);
        boolFragMessageWrite = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void showTipBobbles(int index) {

        List<ITipDTO> list = null;

        viewPager_tipBobles.setVisibility(View.VISIBLE);
        viewPager_tipBobles.setAdapter(adapter_TipBobbles);
        viewPager_tipBobles.setCurrentItem(index);

    }
    /**
     * @inheritDoc
     * */
    @Override
    public void closeTipBobbles(){
        viewPager_tipBobles.setVisibility(View.GONE);
    }

    //TopMessageBar
    /**
     * @inheritDoc
     * */
    @Override
    public void showTopMsgBar(int imageId, String header, String subTitle) {

        fragment_topMessage.setHeader(header);
        fragment_topMessage.setSubtitle(subTitle);
        fragment_topMessage.setImage(imageId);
        fragment_topMessage.show();
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void hideTopMsgBar() {
        fragment_topMessage.hide();
    }

    // Booleans
    /**
     * @inheritDoc
     * */
    @Override
    public boolean isWriteTipOpen(){
        return boolFragMessageWrite;
    }
    /**
     * @inheritDoc
     * */
    @Override
    public boolean isTipBobbleOpen(){
        return boolFragTipBobble;
    }
    /**
     * @inheritDoc
     * */
    @Override
    public boolean isTopBarOpen(){
        return boolFragTopMessageBar;
    }

    // Open Close Fragments and or Views.
    /**
     FragmentToggleTransaction is a method to open and close fragments using a slightly complicated Transaction method
     @param containerId the container the fragment will be placed into, note it must be an Android View item id, in the current layout
     @param fragment    teh fragment to place into the container
     @param Open        true if open, false if remove
     * */
    private void FragmentToggleTransaction(int containerId, Fragment fragment, boolean Open){
        if(Open){
            transaction = fragmentManager.beginTransaction();
            try {
                Log.v("transaction", "Adding fragment");
                transaction.add(containerId, fragment);
            }catch (IllegalStateException e){
                Log.v("transaction", "Replacing fragment");
                transaction.replace(containerId, fragment);
            }
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            Log.v("transaction","Removing fragment");
        }
    }

//    @Override
//    public LiveDataViewModel getViewModel() {
//        return context.getViewModel();
//    }
}