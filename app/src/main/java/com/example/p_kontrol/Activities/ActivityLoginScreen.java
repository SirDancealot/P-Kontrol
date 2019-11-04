package com.example.p_kontrol.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.ViewCompat;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

public class ActivityLoginScreen extends AppCompatActivity{

    // Login Screen 1.
    Button screen1_loginBtn;
    Button screen1_SignUpBtn;

    // Login Screen 2.
    Button screen2_loginManual;
    Button screen2_loginFaceB;
    Button screen2_loginGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_scene1);

        // Screen 1's elements
        screen1_loginBtn = findViewById(R.id.LoginScreen_1_LoginBtn);
        screen1_SignUpBtn = findViewById(R.id.LoginScreen_1_SignUpBtn);

        setupListeners_Screen1();
    }


    private void setupListeners_Screen1(){
        screen1_loginBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screen1_loginBtn(v);
            }
        });

        screen1_SignUpBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screen1_SignInBtn(v);
            }
        });

    }
    private void setupListeners_Screen2(){
        screen2_loginManual.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screen2_loginManual(v);
            }
        });

        screen2_loginFaceB.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screen2_loginFaceB(v);
            }
        });

        screen2_loginGoogle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                screen2_loginGoogle(v);
            }
        });

    }

    // Btn Methods for Screen 1.
    private void screen1_loginBtn(View v){
        Log.v("screen 1","Login btn clicked \n");
        setContentView(R.layout.activity_login_screen);

        screen2_loginManual = findViewById(R.id.LoginScreen_2_SignIn)       ;
        screen2_loginFaceB  = findViewById(R.id.LoginScreen_2_LogFaceBook)  ;
        screen2_loginGoogle = findViewById(R.id.LoginScreen_2_LogGoogle)    ;

        setupListeners_Screen2();

    }
    private void screen1_SignInBtn(View v){
        Log.v("screen 1","Sign in btn clicked \n");
    }

    // Btn Methods for Screen 2.
    private void screen2_loginManual(View v){
        Log.v("screen 2","login Manual btn clicked \n");
        changeto_Activiy_MapView();
    }
    private void screen2_loginFaceB(View v){
        Log.v("screen 2","login FaceBook btn clicked \n");
        changeto_Activiy_MapView();
    }
    private void screen2_loginGoogle(View v){
        Log.v("screen 2","login Google btn clicked \n");
        changeto_Activiy_MapView();
    }
    private void changeto_Activiy_MapView(){
        Intent changeActivity = new Intent(this, ActivityMapView.class);
        startActivity(changeActivity);
    }
}
