package com.example.p_kontrol.UI.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.R;
import com.example.p_kontrol.DataBase.dto.UserInfoDTO;
import com.example.p_kontrol.UI.Services.IBackend;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class ActivityLoginScreen extends AppCompatActivity{

    // Login Screen 1.
    Button screen1_loginBtn;
    Button screen1_SignUpBtn;

    // Login Screen 2.
    Button screen2_loginManual;
    LoginButton screen2_loginFaceB;
    Button screen2_loginGoogle;

    // facebook
    CallbackManager cbman;
    UserInfoDTO userInfoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (AccessToken.getCurrentAccessToken() != null) {
            getFBContent(AccessToken.getCurrentAccessToken());
        } else {

            setContentView(R.layout.login_screen_scene1);

            // Screen 1's elements
            screen1_loginBtn = findViewById(R.id.LoginScreen_1_LoginBtn);
            screen1_SignUpBtn = findViewById(R.id.LoginScreen_1_SignUpBtn);
            cbman = CallbackManager.Factory.create();

            setupListeners_Screen1();
        }



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
        screen2_loginFaceB  = findViewById(R.id.login_button)               ;
        screen2_loginGoogle = findViewById(R.id.LoginScreen_2_LogGoogle)    ;

        screen2_loginFaceB.setPermissions(Arrays.asList("email", "public_profile"));
        screen2_loginFaceB.registerCallback(cbman, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                System.out.println("1");

            }

            @Override
            public void onCancel() {
                System.out.println("2");

            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(ActivityLoginScreen.this, "Error: No Wifi", Toast.LENGTH_SHORT).show();

            }
        });


        System.out.println("vi går ind");

        if (AccessToken.getCurrentAccessToken() != null) getFBContent(AccessToken.getCurrentAccessToken());

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


    //facebook

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        cbman.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }

    AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
        @Override
        protected void onCurrentAccessTokenChanged(AccessToken oldAccessToken, AccessToken currentAccessToken) {
            System.out.println(currentAccessToken);
            if (currentAccessToken != null){
                getFBContent(currentAccessToken);
            } else {
                userInfoDTO.setLogin(false);
                userInfoDTO.setUrl("");
                userInfoDTO.setEmail("");
                userInfoDTO.setName("");
                userInfoDTO.setName2("");
                Toast.makeText(ActivityLoginScreen.this,"Logget ud",Toast.LENGTH_LONG).show();
            }
        }
    };

    private void getFBContent(AccessToken token) {

        userInfoDTO = UserInfoDTO.getUserInfoDTO();

        GraphRequest request = GraphRequest.newMeRequest(token, new GraphRequest.GraphJSONObjectCallback() {


            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                System.out.println(">Z>>>>>>>>>> vi er her");

                try {

                    userInfoDTO.setName(object.getString("first_name"));
                    userInfoDTO.setName2(object.getString("last_name"));
                    userInfoDTO.setEmail(object.getString("email"));
                    userInfoDTO.setId(object.getString("id"));
                    System.out.println(userInfoDTO.getName() + " " + userInfoDTO.getEmail() + "\n");

                    userInfoDTO.setUrl("https://graph.facebook.com/" + userInfoDTO.getId() + "/picture?type=normal");
                    userInfoDTO.setLogin(true);

                    Intent i = new Intent(ActivityLoginScreen.this, ActivityMapView.class);
                    startActivity(i);

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        });

        Bundle parameters = new Bundle();
        parameters.putString("fields","first_name,last_name,email,id");
        request.setParameters(parameters);
        request.executeAsync();
    }
}
