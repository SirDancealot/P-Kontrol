package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Feedback.ActivityFeedback;
import com.example.p_kontrol.UI.Map.StateFreePark;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;

public  class MainMenuActivityController extends AppCompatActivity implements IMenuOperationsController , IMapOperatorController{

    // THIS IS THE CONTROLLER CLASS
    final static TipDTO newTipDTO = new TipDTO(); // Static methods require a Static object to maneuver
    IMenuOperator       menuOperator;
    IFragmentOperator   fragmentOperator;
    IMapOperator        mapOperator;
    View container;

    // -- ** -- View Model stuff  -- ** -- **  -- ** -- **  -- ** -- **

    LiveDataViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        container = findViewById(R.id.mainMenu_layout);

        // setting compositions
        // delegates responsibility for Creating Views out to keep code simple, however, each referes back here
        // for controll.
        menuOperator     = new CompositionMenuOperator(this, container );
        mapOperator      = new CompositionMapOperator(this,container, this);
        fragmentOperator = new CompositionFragmentOperator(this,container);

        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);

    }

    @Override
    protected void onStart() {
        super.onStart();
        showTopMsgBar(R.drawable.ic_topmsgbar_readtip, "P-Tip", "read or create a tip?");
    }

    // this class is the top of the Stack so where the controll of Avitivity what to do fist()
    // Menu Controll.
    @Override
    public void menuBtn_profile(){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }
    @Override
    public void menuBtn_FreePark(){
        Log.i("click","FreePark btn clicked \n");
        mapOperator.toggleStateParking();
        menuOperator.toggleFreeParkEnabled();
    }
    @Override
    public void menuBtn_Contribute(){

        // Closing the Menu down.
        menuOperator.toggleMenu();

        // starting Contribute process at index 0. meaning the very first step.
        createTip();

    }
    @Override
    public void menuBtn_Community(){
        Log.i("click","Community btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityFeedback.class);
        startActivity(changeActivity);
    }
    @Override
    public void menuBtn_ParkAlarm(){
        Log.i("click","Park alarm btn clicked \n");
    }
    @Override
    public void menuBtn_PVagt(){
        Log.i("click","P-Vagt btn clicked \n");

    }

    // Map
    @Override
    public void onTipClick(int index){
        fragmentOperator.showTipBobbles(index);
        menuOperator.closeMenu();
    }
    @Override
    public void onCenterClick(View v){
        mapOperator.centerOnUserLocation();
    }

    // Free park state


    // Create Tip
    private void createTip(){
        createTip_Process(0);
    }
    private void createTip_Process(int i){
        switch (i) {
            case 0: // Chose location
                showTopMsgBar(R.drawable.ic_topmsgbar_selectlocation, "Creating a Tip", "Select a Location to Place tip");

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
                    }
                });
                break;
            case 1: // Write Tip
                showTopMsgBar(R.drawable.ic_topmsgbar_writing, "Creating a Tip", "Write the actual Tip");
                mapOperator.visibilityOfInteractBtns(View.GONE);
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
                    }
                });
                break;
            case 2: // finish Tip and send to back end for saving.
                showTopMsgBar(R.drawable.ic_topmsgbar_readtip, "P-Tip", "read or create a tip?");
                model.createTip();
                break;
        }
    }


    private void showTopMsgBar(int imageId, String header, String subtitle){
        fragmentOperator.showTopMsgBar(imageId, header, subtitle);
    }

    public LiveDataViewModel getViewModel(){
        return model;
    }

}