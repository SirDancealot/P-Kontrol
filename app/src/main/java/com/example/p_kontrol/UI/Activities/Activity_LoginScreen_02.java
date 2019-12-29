package com.example.p_kontrol.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.example.p_kontrol.R;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.facebook.CallbackManager;

public class Activity_LoginScreen_02 extends AppCompatActivity implements View.OnClickListener{

    String TAG = "LoginScreen2";
    // Login Screen 1.
    Button screen2_LoginBtn;
    Button screen2_SignUpBtn;

    //transition Elements
    View trans_circle_1, trans_circle_2,trans_logo,trans_background,trans_TopBar;

    // facebook
    CallbackManager cbman;
    UserInfoDTO userInfoDTO;

   @Override
   protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        // if already logged in, skip rest
        ifAlreadyLoggedIn();

        // Setup Functionality
        setContentView(R.layout.activity_loginscreen_02);
        screen2_LoginBtn = findViewById(R.id.LoginScreen_2_LoginBtn);
        screen2_SignUpBtn = findViewById(R.id.LoginScreen_2_SignUpBtn);

        screen2_LoginBtn.setOnClickListener(this);
        screen2_SignUpBtn.setOnClickListener(this);

        // SetUp Transition elements
       trans_circle_1 = findViewById(R.id.LoginScreen_Circle1);
       trans_circle_2 = findViewById(R.id.LoginScreen_Circle2);
       trans_background = findViewById(R.id.LoginScreen_BackgroundBlue);
       trans_logo = findViewById(R.id.LoginScreen_LogoContainer);
   }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.LoginScreen_2_LoginBtn:
                    Login();
                break;
            case R.id.LoginScreen_2_SignUpBtn:
                    SignUp();
                break;
        }
    }

    public void ifAlreadyLoggedIn(){
       // todo implement this.
        boolean isAlreadyLoggedin = false;
        if( isAlreadyLoggedin ){
            changeActNext();
        }
    }
    public void SignUp(){
        Log.e(TAG, "SignUp: Not Implemented as of yet" );
    }
    public void Login(){
       //todo login stuff implement here
        changeActNext();
    }

    public void changeActNext(){

        Intent changeActivity = new Intent( Activity_LoginScreen_02.this, Activity_LoginScreen_03.class);
        ActivityOptionsCompat transitionParameters = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Activity_LoginScreen_02.this,
                // All Custom Shared elements
                new Pair<>(trans_logo, trans_logo.getTransitionName())              ,
                new Pair<>(trans_background,trans_background.getTransitionName())
        );
        startActivity(changeActivity, transitionParameters.toBundle());
        //startActivity(changeActivity);
    }
    public void changeActPrev(){
        Intent changeActivity = new Intent( Activity_LoginScreen_02.this, Activity_LoginScreen_01.class);
        ActivityOptionsCompat transitionParameters = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Activity_LoginScreen_02.this,
                // All Custom Shared elements
                new Pair<>(trans_circle_1, trans_circle_1.getTransitionName())      ,
                new Pair<>(trans_circle_2, trans_circle_2.getTransitionName())      ,
                new Pair<>(trans_logo, trans_logo.getTransitionName())              ,
                new Pair<>(trans_background,trans_background.getTransitionName())   ,
                // Android standard elements
                new Pair<>(trans_TopBar, Window.STATUS_BAR_BACKGROUND_TRANSITION_NAME)
        );
        startActivity(changeActivity, transitionParameters.toBundle());
    }


/*
@Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if (AccessToken.getCurrentAccessToken() != null) {
            getFBContent(AccessToken.getCurrentAccessToken());
        } else {

            setContentView(R.layout.activity_loginscreen_02);

            // Screen 1's elements
            screen1_loginBtn = findViewById(R.id.LoginScreen_3_LoginBtn);
            screen1_SignUpBtn = findViewById(R.id.LoginScreen_3_SignUpBtn);
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
        setContentView(R.layout.activity_LoginScreen_04);

        screen2_loginManual = findViewById(R.id.LoginScreen_3_SignIn)       ;
        screen2_loginFaceB  = findViewById(R.id.login_button)               ;
        screen2_loginGoogle = findViewById(R.id.LoginScreen_3_LogGoogle)    ;

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
                Toast.makeText(Activity_LoginScreen_02.this, "Error: No Wifi", Toast.LENGTH_SHORT).show();

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
                Toast.makeText(Activity_LoginScreen_02.this,"Logget ud",Toast.LENGTH_LONG).show();
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

                    Intent i = new Intent(Activity_LoginScreen_02.this, ActivityMapView.class);
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
    */


}
