package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * @responsibilty responsibility to Handle UI interaction on this XML layout.
 *
 * implemented using Composition(with a touch of delegation) Pattern and inheritance;
 *
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * */
public class MainMenuActivity extends MainMenuActivityController{

    // this Handels the backStack, to see controlls look at MainMenuAcitivityController.

    MainMenuCloseFragment fragment_close        ;
    private String TAG = "MainMenuActivity_androidMethods";
    static final int RC_SIGN_IN = 3452;

    //Service Connection
    FirestoreDAO mService;
    boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FirestoreDAO.DAOBinder binder = (FirestoreDAO.DAOBinder) service;
            mService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment_close= new MainMenuCloseFragment(this);
    }

    @Override
    protected void onStart() {
        super.onStart();

        //connect to service
        Log.d(TAG, "onStart: start");
        Intent startService = new Intent(this, FirestoreDAO.class);
        bindService(startService, connection, Context.BIND_AUTO_CREATE);

        if (bound){
            ATipDTO serviceTipTest = new ATipDTO();
            serviceTipTest.setAuthor(new AUserDTO());
            serviceTipTest.setMessage("Tip created by service");
            mService.createTip(serviceTipTest);
        } else {
            Log.d(TAG, "onStart: service not bound");
        }
        
    }

    // Android Specifiks
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
            fragment_close.show(getSupportFragmentManager(), "closeFragment");
            //super.onBackPressed();
            //overridePendingTransition(0, android.R.anim.fade_out);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mapOperator.updatePermissions();
        } else {
            // Permission was denied. Display an error message.
            Log.d(TAG, "onRequestPermissionsResult: false");
        }

    }
    // [START auth_fui_result]



}






