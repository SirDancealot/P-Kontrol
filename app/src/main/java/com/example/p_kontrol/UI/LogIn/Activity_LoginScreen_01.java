package com.example.p_kontrol.UI.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.ViewModelProviders;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.UserFactory;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;

import com.example.p_kontrol.UI.MainMenuAcitvity.MainMenuActivity;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
/**
 * @responsibilty to check if the User is already logged in.
 * */
public class Activity_LoginScreen_01 extends AppCompatActivity {

    String TAG = this.getClass().getName();

    // views
    View trans_circle_1;
    View trans_circle_2;
    View trans_logo;
    View trans_background;

    //Service Connection , also Data Access
    private LiveDataViewModel model;
    protected FirestoreDAO mService;
    private boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FirestoreDAO.DAOBinder binder = (FirestoreDAO.DAOBinder) service;
            mService = binder.getService();
            bound = true;
            model.setDao(mService);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            model.setDao(null);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen_01);


        // Elements to change when ready
        trans_circle_1  = findViewById(R.id.LoginScreen_Circle1)        ;
        trans_circle_2  = findViewById(R.id.LoginScreen_Circle2)        ;
        trans_logo      = findViewById(R.id.LoginScreen_LogoContainer)  ;
        trans_background= findViewById(R.id.LoginScreen_BackgroundBlue) ;

    }

    @Override
    protected void onResume() {
        super.onResume();

        //connect to service
        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        Intent startService = new Intent(this, FirestoreDAO.class);
        bindService(startService, connection, Context.BIND_AUTO_CREATE);


        Handler handler = new Handler();
        handler.postDelayed(() -> {
            checkLoginSession();
        }, 500);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(connection);
    }


    /**
     * checks if the phone is already logged in.
     * if so go in to MainMenuAcitivity
     * @see {@link com.example.p_kontrol.UI.MainMenuAcitvity.MainMenuActivity}
     * */
    private void checkLoginSession(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            UserFactory factory = UserFactory.getFactory();
            mService.getUser(user.getUid(), factory, user);

            Intent changeActivity = new Intent( this , MainMenuActivity.class);
            startActivity(changeActivity);
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    changeAct();
                }
            }, 1000);
        }
    }
    /**
     * changes activity, simple method to keep code looking simple.
     * changes to Login Screen 2. is only called if your not already logged in.
     * @see {@link com.example.p_kontrol.UI.LogIn.Activity_LoginScreen_02}
     * */
    private void changeAct() {
        Log.w(TAG, " Changing Activities " );

        //Android Standard Shared Elements. must or have a Clipping.
        View trans_TopBar = findViewById(android.R.id.statusBarBackground);
        if(trans_TopBar == null){
            Log.e("Null Error", "Top Bar = null");
        }

        Intent login_intent = new Intent(Activity_LoginScreen_01.this, Activity_LoginScreen_02.class );
        ActivityOptionsCompat transitionParameters = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Activity_LoginScreen_01.this,
                // All Custom Shared elements
                new Pair<>(trans_circle_1, trans_circle_1.getTransitionName())      ,
                new Pair<>(trans_circle_2, trans_circle_2.getTransitionName())      ,
                new Pair<>(trans_logo, trans_logo.getTransitionName())              ,
                new Pair<>(trans_background,trans_background.getTransitionName())   ,
                // Android standard elements
                new Pair<>(trans_TopBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
        );
        startActivity(login_intent, transitionParameters.toBundle());
    }
}
