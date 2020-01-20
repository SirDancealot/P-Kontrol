package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Feedback.ActivityFeedback;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;

import java.util.Date;
/**
 * @responsibilty to be the Main android Framework for everything in the app that uses a MainMenu, Map , ReadTips, WriteTips, Parking, and P alerts.
 *
 * */
public  class MainMenuActivity extends AppCompatActivity implements IMenuOperationsController , IMapOperatorController{

// VARIABLES

    // Android Specific things
    public String TAG = "MenuController";
    private MainMenuCloseFragment fragment_close;

    //Activity Controller Objects, these are delegates of Responsibility to Operate Different Areas of the Code.
    private IMenuOperator       menuOperator;       // Start the Menu Views, setup Listeners and Know them.
    private IFragmentOperator   fragmentOperator;   // Start the ReadTip, WriteTip, TopMsgBar Fragments, know them and have methods ready to be called to operate them.
    private IMapOperator        mapOperator;        // Contain the map, setup the map, know it, and know different states of the map.
    private View container;                         // the layout.

    //Service Connection , also Data Access
    private LiveDataViewModel model;            //
    protected FirestoreDAO mService;
    private boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FirestoreDAO.DAOBinder binder = (FirestoreDAO.DAOBinder) service;
            mService = binder.getService();
            bound = true;
            model.setDao(mService);
            model.startTipQuery();
            model.startPVagtQuery();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

// METHODS

    // Android LifeCycle Calls, onCreate onStart onResume.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // setup the Layout and the Main View
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        container = findViewById(R.id.mainMenu_layout);

        // setting responsibility delegating components
        menuOperator     = new ComponentMenuOperator(this, container );
        mapOperator      = new ComponentMapOperator(this,container, this);
        fragmentOperator = new ComponentFragmentOperator(this,container);

        // getting data
        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        fragment_close= new MainMenuCloseFragment(this);

        // setting up the center Click button , since it dosent change, set it here.
        mapOperator.onCenterClick(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapOperator.centerOnUserLocation();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
    }

    @Override
    protected void onResume() {
        super.onResume();

        //connect to service
        Log.d(TAG, "onResume: ");
        Intent startService = new Intent(this, FirestoreDAO.class);
        bindService(startService, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        model.setDao(null);
    }

    //  -- * -- * -- * -- * -- * IMenuOperationsController -- * -- * -- * -- * -- * -- *

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_profile(){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_FreePark(){
        Log.i("click","FreePark btn clicked \n");
        mapOperator.toggleStateFreePark();
        menuOperator.toggleMenuBtnFreePark();
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_Contribute(){

        // Closing and hide the Menu down.
        menuOperator.closeMenu();
        // Go out of parking state or free parking state.
        menuOperator.deToggleMenuButton();

        createTip();

    }

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_FeedBack(){
        Log.i("click","Community btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityFeedback.class);
        startActivity(changeActivity);
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_Parking(){
        Log.i("click","Park Alarm btn clicked \n");
        mapOperator.toggleStateParking();
        menuOperator.toggleMenuBtnParking();
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_PVagt(){
        Log.i("click","P-Vagt btn clicked \n");

        //report pVagt at current location
        PVagtDTO pvagt = new PVagtDTO();
        pvagt.setCreationDate(new Date());
        pvagt.setUid("123");

        model.createPVagt(pvagt);

    }

    // -- * -- * -- * -- * -- * Map IMapOperatorController -- * -- * -- * -- * -- * -- *

    /**
     * @inheritDoc
     * */
    @Override
    public void onTipClick(int index){
        fragmentOperator.showTipBobbles(index);
        menuOperator.closeMenu();
    }

    // -- * -- * -- * -- * -- * Private Controller Methods -- * -- * -- * -- * -- * -- * -- *

    /**
     * simple method to start the CreateTip process, since it is a step by step process.
     * see createTip_Process(int i);
     */
    private void createTip(){
        createTip_Process(0); // start the CreateTip Process at stage 0.
    }

    /**
     * complicated method to controll step-by-step,  the create tip Process
     */
    private void createTip_Process(int i){
        switch (i) {
            case 0: // Chose location
                menuOperator.setMenuHandleVisibility(View.INVISIBLE);
                showTopMsgBar(R.drawable.ic_topmsgbar_selectlocation, getResources().getString(R.string.topbar_createTip_header), getResources().getString(R.string.topbar_createTip_subTitle));

                mapOperator.setStateSelection();
                mapOperator.visibilityOfInteractBtns(View.VISIBLE);

                mapOperator.onAcceptClick( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mapOperator.setStateStandby();
                        createTip_Process(1);
                    }
                });
                mapOperator.onCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mapOperator.setStateStandby();
                        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
                        menuOperator.setMenuHandleVisibility(View.VISIBLE);
                    }
                });
                break;
            case 1: // Write Tip
                showTopMsgBar(R.drawable.ic_topmsgbar_writing, getResources().getString(R.string.topbar_writeTip_heaeder), getResources().getString(R.string.topbar_writeTip_subTitle));
                mapOperator.visibilityOfInteractBtns(View.INVISIBLE);
                mapOperator.setStateStandby();
                fragmentOperator.openWriteTip(new ITipWriteListener() {
                    @Override
                    public void onMessageDone() {
                        fragmentOperator.closeWriteTip();
                        createTip_Process(2);
                    }
                    @Override
                    public void onCancelTip() {
                        fragmentOperator.closeWriteTip();
                        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
                        menuOperator.setMenuHandleVisibility(View.VISIBLE);
                    }
                });
                break;
            case 2: // finish Tip and send to back end for saving.
                showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
                menuOperator.setMenuHandleVisibility(View.VISIBLE);
                model.createTip();
                break;
        }
    }

    /**
     * simple method to edit the top messageBar, such that the managing of the top bar would be easy.
     * by using a find usages in the same class.
     */
    private void showTopMsgBar(int imageId, String header, String subtitle){
        fragmentOperator.showTopMsgBar(imageId, header, subtitle);
    }


    // -- * -- * -- * -- * -- * Android Specific things -- * -- * -- * -- * -- * -- * -- *
    /**
     * BackStack management
     */
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Something happened");
        if (mapOperator.getCurrentState() instanceof StateSelectLocation) {
            Log.d(TAG, "onBackPressed: stateSelect");
            mapOperator.setStateStandby();
        }


        else if ( fragmentOperator.isTipBobbleOpen()) {
            Log.d(TAG, "onBackPressed: viewPager");
            fragmentOperator.closeTipBobbles();
            menuOperator.setMenuHandleVisibility(View.VISIBLE);
        }
        else if (menuOperator.isMenuOpen()) {
            Log.d(TAG, "onBackPressed: Bottom menue");
            menuOperator.closeMenu();
        }
        else {
            Log.d(TAG, "onBackPressed: back pressed");
            //TODO: find ud af om vi skal bruge dialog box eller fade out
            fragment_close.show(getSupportFragmentManager(), "closeFragment");
            //super.onBackPressed();
            //overridePendingTransition(0, android.R.anim.fade_out);
        }
    }
    /**
     * is an important call, that updates the permissions on the google map implementation,
     * these permissions are
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mapOperator.updatePermissions();
        } else {
            // Permission was denied. Display an error message.
            Log.d(TAG, "onRequestPermissionsResult: false");
        }

    }
}