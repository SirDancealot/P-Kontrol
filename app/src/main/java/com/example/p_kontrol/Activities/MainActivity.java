package com.example.p_kontrol.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.p_kontrol.R;

public class MainActivity extends AppCompatActivity {

    //todo slet denne myBtn, erstat mead timed load.
    Button myBtn;

    // Listing elemenets to change when it has been loaded
    View trans_circle_1;
    View trans_circle_2;
    View trans_logo;
    View trans_background;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elements to change when ready
        trans_circle_1  = findViewById(R.id.LoginScreen_Circle1)        ;
        trans_circle_2  = findViewById(R.id.LoginScreen_Circle2)        ;
        trans_logo      = findViewById(R.id.LoginScreen_LogoContainer)  ;
        trans_background= findViewById(R.id.LoginScreen_BackgroundBlue) ;

        myBtn = findViewById(R.id.LoginScreen_LogoImg);
        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                splashTransition();
            }

        });

    }

    public void splashTransition() {
        //Android Standard Shared Elements
        View trans_TopBar = findViewById(android.R.id.statusBarBackground);
        if(trans_TopBar == null){
            Log.e("Null Error", "Top Bar = null");
        }

        Intent login_intent = new Intent(MainActivity.this, ActivityLoginScreen.class );
        ActivityOptionsCompat transitionParameters = ActivityOptionsCompat.makeSceneTransitionAnimation(
                MainActivity.this,
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
