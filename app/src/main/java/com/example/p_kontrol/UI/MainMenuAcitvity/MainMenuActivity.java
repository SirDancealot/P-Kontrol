package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
/**
 * @responsibilty to be the Main android Framework for everything in the app that uses a MainMenu, Map , ReadTips, WriteTips, Parking, and P alerts.
 *
 * */
public  class MainMenuActivity extends AppCompatActivity implements IMenuOperationsController , IMapOperatorController{

// VARIABLES

    // Android Specific things
    public String TAG = "MenuController";
    private YesNoDialogFragment dialogClose, dialogFeedback;

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

    // create Tip Process required data
    private int stageOfProcess = 0;
    private final Handler handler = new Handler();
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
        dialogClose = new YesNoDialogFragment(this, 0);
        dialogFeedback = new YesNoDialogFragment(this, 2);


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
        menuOperator.deToggleMenuButtons();

        createTip();

    }

    /**
     * @inheritDoc
     * */
    @Override
    public void menuBtn_FeedBack(){
        Log.i("click","Community btn clicked \n");

        dialogFeedback.show(getSupportFragmentManager(), "closeFragment");

        //Intent changeActivity = new Intent( this , ActivityFeedback.class);
        //startActivity(changeActivity);
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
        pvagt.setL(new GeoPoint(model.getCurrentLocation().getValue().latitude, model.getCurrentLocation().getValue().longitude));

        model.createPVagt(pvagt);

        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle), R.color.colorAlarm);


        // resetting the TopMsgBar
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
            }
        }, 3000);

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
        if(stageOfProcess == 0){ // if were arent already in the process of creating a tip
            stageOfProcess = 1;
            createTip_Process(); // start the CreateTip Process at stage 0.
            menuOperator.toggleMenuBtnContribute(true);
        }else{
            createTip_Process_cancel();
        }
    }

    /**
     * complicated method to controll step-by-step,  the create tip Process
     */
    private void createTip_Process(){
        switch (stageOfProcess) {
            case 1: // Chose location

                showTopMsgBar(R.drawable.ic_topmsgbar_selectlocation, getResources().getString(R.string.topbar_createTip_header), getResources().getString(R.string.topbar_createTip_subTitle));
                mapOperator.setStateSelection();
                mapOperator.visibilityOfInteractBtns(View.VISIBLE);

                mapOperator.onAcceptClick( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mapOperator.setStateStandby();
                        ++stageOfProcess;
                        createTip_Process();
                    }
                });
                mapOperator.onCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        createTip_Process_cancel();
                    }
                });
                break;
            case 2: // Write Tip
                showTopMsgBar(R.drawable.ic_topmsgbar_writing, getResources().getString(R.string.topbar_writeTip_heaeder), getResources().getString(R.string.topbar_writeTip_subTitle));
                mapOperator.visibilityOfInteractBtns(View.INVISIBLE);
                mapOperator.setStateStandby();
                fragmentOperator.openWriteTip(new ITipWriteListener() {
                    @Override
                    public void onMessageDone() {
                        fragmentOperator.closeWriteTip();
                        ++stageOfProcess;
                        createTip_Process();
                    }
                    @Override
                    public void onCancelTip() {
                        createTip_Process_cancel();
                    }
                });
                break;
            case 3: // finish Tip and send to back end for saving.
                showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_contributedMessage_header), getResources().getString(R.string.topbar_contributedMessage_subTitle));
                model.createTip();
                stageOfProcess = 0;

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
                    }
                }, 3000);
                break;
        }
    }

    /**
     * method to close the createTipProcess regardless of what stage of the process is the current stage.
     */
    private void createTip_Process_cancel(){
        switch (stageOfProcess){
            case 1: // set the map location
                mapOperator.setStateStandby();
                showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
                break;
            case 2: // write the message
                fragmentOperator.closeWriteTip();
                showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
                break;
        }
        menuOperator.toggleMenuBtnContribute(false);
        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, getResources().getString(R.string.topbar_pTip_header), getResources().getString(R.string.topbar_pTip_subTitle));
        stageOfProcess = 0; // were not creating this .
    }

    /**
     * simple method to edit the top messageBar, such that the managing of the top bar would be easy.
     * by using a find usages in the same class.
     */
    private void showTopMsgBar(int imageId, String header, String subtitle){
        fragmentOperator.showTopMsgBar(imageId, header, subtitle, R.color.color_pureWhite);
    }
    private void showTopMsgBar(int imageId, String header, String subtitle, int colorId){
        fragmentOperator.showTopMsgBar(imageId, header, subtitle, colorId);
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
        }
        else if (menuOperator.isMenuOpen()) {
            Log.d(TAG, "onBackPressed: Bottom menue");
            menuOperator.closeMenu();
        }

        else {
            Log.d(TAG, "onBackPressed: back pressed");
            //TODO: find ud af om vi skal bruge dialog box eller fade out
            dialogClose.show(getSupportFragmentManager(), "closeFragment");
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