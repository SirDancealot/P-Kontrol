package com.example.p_kontrol.UI.LogIn;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Activity_LoginScreen_01 extends AppCompatActivity {

    String TAG = "LoginScreen_01";
    View trans_circle_1;
    View trans_circle_2;
    View trans_logo;
    View trans_background;

    private UserInfoDTO userInfoDTO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkLoginSession();
        setContentView(R.layout.activity_loginscreen_01);


        // Elements to change when ready
        trans_circle_1  = findViewById(R.id.LoginScreen_Circle1)        ;
        trans_circle_2  = findViewById(R.id.LoginScreen_Circle2)        ;
        trans_logo      = findViewById(R.id.LoginScreen_LogoContainer)  ;
        trans_background= findViewById(R.id.LoginScreen_BackgroundBlue) ;

    }


    public void changeAct() {
        Log.w(TAG, " Changing Activities " );

        //Android Standard Shared Elements. must or have a Clipping.
        View trans_TopBar = findViewById(android.R.id.statusBarBackground);
        if(trans_TopBar == null){
            Log.e("Null Error", "Top Bar = null");
        }

        Intent login_intent = new Intent(Activity_LoginScreen_01.this, Activity_LoginScreen_03.class );
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

    public void checkLoginSession(){
        userInfoDTO = UserInfoDTO.getUserInfoDTO();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        System.out.println("--------kkkkk");
        System.out.println(user);
        System.out.println("--------kkkkk");

        if(user != null){
            System.out.println("--------inde!!");
            userInfoDTO.setUser(user);
            Intent changeActivity = new Intent( this , MainMenuActivity.class);
            startActivity(changeActivity);
            System.out.println("--------ude!!");
        } else {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
//                    changeAct();
//TODO use changeAct() when it works
                    Intent login_intent = new Intent(Activity_LoginScreen_01.this, Activity_LoginScreen_03.class );
                    startActivity(login_intent);

                }
            }, 1000);
        }
    }

}
