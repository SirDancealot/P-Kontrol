package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.TopMessageBar.FragTopMessageBar;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;

import java.util.List;

class CompositionFragmentOperator   implements IFragmentOperator {

    MainMenuActivityController context;
    View view;
    private String TAG = this.getClass().getName();

    FragMessageWrite fragment_messageWrite   ;
    FragTopMessageBar fragment_topMessage     ;

    //ViewPager - Tip bobbles.
    FragmentPagerAdapter adapter_TipBobbles;
    ViewPager viewPager_tipBobles;

    //Specials
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    //Booleans for Open Closing Fragments.
    boolean boolFragMessageWrite    ;
    boolean boolFragTipBobble       ;
    boolean boolFragTopMessageBar   ;


    // DAta Acces
    LiveDataViewModel model;
    LiveData<List<ATipDTO>> tipList;


    public CompositionFragmentOperator(MainMenuActivityController context, View view){

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
        FragmentToogleTransaction(R.id.mainMenu_topMsgBarContainer,  fragment_topMessage, true);

        // Live Data list , that calls adapter to notify of changes when changes are made.
        model = ViewModelProviders.of(context).get(LiveDataViewModel.class);
        tipList = model.getTipList();
        tipList.observe(context, list -> {
            try {
                adapter_TipBobbles.notifyDataSetChanged();
            }catch (NullPointerException e){
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );
        adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, tipList,this);
    }

    // Open Close Fragments and or Views.
    private void FragmentToogleTransaction(int containerId, Fragment fragment, boolean Open){
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

    // Toggles

    // write Tip
    @Override
    public void openWriteTip(ITipWriteListener writeListener) {
        fragment_messageWrite = new FragMessageWrite(writeListener,this.context);
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , true);
        boolFragMessageWrite = true;
    }
    @Override
    public void closeWriteTip(){
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , false);
        boolFragMessageWrite = false;
    }

    // Tip Boobles
    @Override
    public void showTipBobbles(int index) {

        List<ATipDTO> list = null;

        viewPager_tipBobles.setVisibility(View.VISIBLE);
        viewPager_tipBobles.setAdapter(adapter_TipBobbles);
        viewPager_tipBobles.setCurrentItem(index);

    }
    @Override
    public void closeTipBobbles(){
        viewPager_tipBobles.setVisibility(View.GONE);
    }

    //TopMsgBar
    @Override
    public void showTopMsgBar(int imageId, String header, String subTitle) {

        fragment_topMessage.setHeader(header);
        fragment_topMessage.setSubtitle(subTitle);
        fragment_topMessage.setImage(imageId);
        fragment_topMessage.show();
    }
    @Override
    public void hideTopMsgBar() {
        fragment_topMessage.hide();
    }

    // Booleans
    @Override
    public boolean isWriteTipOpen(){
        return boolFragMessageWrite;
    }
    @Override
    public boolean isTipBobbleOpen(){
        return boolFragTipBobble;
    }
    @Override
    public boolean isTopBarOpen(){
        return boolFragTopMessageBar;
    }

//    @Override
//    public LiveDataViewModel getViewModel() {
//        return context.getViewModel();
//    }
}