package com.example.p_kontrol.UI.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;

public class Activity_LoginScreen_03  extends AppCompatActivity implements View.OnClickListener{

    String TAG = "Login Screen 3";

    // Login Formulae Inputs
    EditText formEmail;
    EditText formPassword;
    TextView formForgotPass;

    // Main Interaction Buttons
    Button signIn_regular, signIn_faceBook, signIn_Google;
    TextView signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen_03);

        // Login Formulae Inputs
        formEmail       = findViewById(R.id.LoginScreen_3_FormEmail);
        formPassword    = findViewById(R.id.LoginScreen_3_FormPassword);
        formForgotPass  = findViewById(R.id.LoginScreen_3_FormForgottenPass);

        // Main Interaction Buttons
        signIn_regular = findViewById(R.id.LoginScreen_3_SignIn);
        signIn_faceBook = findViewById( R.id.LoginScreen_3_SignIn_FaceBook);
        signIn_Google = findViewById( R.id.LoginScreen_3_SignIn_Google);
        signUp = findViewById(R.id.LoginScreen_3_SignUp);

        // setting listeners to it self. see onClick method.
        signIn_regular.setOnClickListener(this);
        signIn_faceBook.setOnClickListener(this);
        signIn_Google.setOnClickListener(this);
        signUp.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // each event possible have a unique method for that button event. see below.
        switch(v.getId()){
            case R.id.LoginScreen_3_SignIn:
                signIn_Methodregular();
                break;
            case R.id.LoginScreen_3_SignIn_FaceBook:
                signIn_MethodfaceBook();
                break;
            case R.id.LoginScreen_3_SignIn_Google:
                signIn_MethodGoogle();
                break;
            case R.id.LoginScreen_3_SignUp:
                signUp_Method();
                break;
        }
    }

    public void signIn_Methodregular(){
        //todo implement
        Log.e(TAG, "Login Regular Not Implemented" );
        ChangeActivityNext();
    };
    public void signIn_MethodfaceBook(){
        // todo, move ot backend , from previous commits.
        Log.e(TAG, "Login Facebook not implemented on this level. must be called from backend." );
    };
    public void signIn_MethodGoogle(){
        //todo implement this properly.
        Log.e(TAG, "Login Regular Not Implemented But Continue Without Login" );

    };
    public void signUp_Method(){
        //todo implement
        Log.e(TAG, "Creating a User Not Implemented" );
    };

    public void ChangeActivityNext(){
        Intent changeActivity = new Intent( this, MainMenuActivity.class);
        changeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(changeActivity);
    }
    public void ChangeActivityPrev(){

        View trans_logo         = findViewById(R.id.LoginScreen_LogoContainer),
             trans_background   = findViewById(R.id.LoginScreen_BackgroundBlue);

        Intent changeActivity = new Intent( Activity_LoginScreen_03.this, Activity_LoginScreen_02.class);
        ActivityOptionsCompat transitionParameters = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Activity_LoginScreen_03.this,
                // All Custom Shared elements
                new Pair<>(trans_logo, trans_logo.getTransitionName())              ,
                new Pair<>(trans_background,trans_background.getTransitionName())
        );
        startActivity(changeActivity, transitionParameters.toBundle());
    }



}
