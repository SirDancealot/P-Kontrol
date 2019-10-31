package com.example.p_kontrol.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

public class MainActivity extends AppCompatActivity {

    Button myBtn;
    boolean testB;

    // Listing elemenets to change when it has been loaded
    View circle_1;
    View circle_2;
    View logo;
    View backGround;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Elements to change when ready
        circle_1    = findViewById(R.id.LoginScreen_Circle1)        ;
        circle_2    = findViewById(R.id.LoginScreen_Circle2)        ;
        logo        = findViewById(R.id.LoginScreen_LogoContainer)  ;
        backGround  = findViewById(R.id.LoginScreen_BackgroundBlue) ;

        myBtn = findViewById(R.id.LoginScreen_LogoImg);
        testB = false;


        myBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ChangeActivity_MapView();
                //splashTransition();
            }

        });

        }
        //ChangeActivity_MapView();


    public void ChangeActivity_MapView() {
        Intent mapView_intent = new Intent(MainActivity.this, ActivityLoginScreen.class);
        startActivity(mapView_intent);
    }


    public void splashTransition() {
        Intent login_intent = new Intent(MainActivity.this, ActivityLoginScreen.class);
        startActivity(login_intent);
        overridePendingTransition(R.anim.fade_in, R.anim.slide_up);
    }

}
