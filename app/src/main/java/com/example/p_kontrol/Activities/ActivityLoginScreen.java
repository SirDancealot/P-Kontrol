package com.example.p_kontrol.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

public class ActivityLoginScreen extends AppCompatActivity{

    // Login Screen 1.
    Button screen1_loginBtn;
    Button screen1_SignUpBtn;

    // Login Screen 2.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_scene1);

        // Screen 1's elements
        screen1_loginBtn = findViewById(R.id.LoginScreen_1_LoginBtn);
        screen1_SignUpBtn = findViewById(R.id.LoginScreen_1_SignUpBtn);

        setupListeners();
    }


    private void setupListeners(){
        // Screen 1
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

    // Btn Methods for Screen 1.
    private void screen1_loginBtn(View v){
        Log.v("screen 1","Login btn clicked \n");
    }
    private void screen1_SignInBtn(View v){
        Log.v("screen 1","Sign in btn clicked \n");

    }

    // Btn Methods for Screen 2.


}
