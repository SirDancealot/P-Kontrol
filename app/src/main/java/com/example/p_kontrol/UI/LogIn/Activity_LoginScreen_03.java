package com.example.p_kontrol.UI.LogIn;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.Arrays;

public class Activity_LoginScreen_03  extends AppCompatActivity implements View.OnClickListener{

    String TAG = "Login Screen 3";
    private static final int RC_SIGN_IN_GOOGLE = 9001;



    // https://github.com/firebase/quickstart-android/blob/90389865dc8a64495b1698c4793cd4deecc4d0ee/auth/app/src/main/java/com/google/firebase/quickstart/auth/java/GoogleSignInActivity.java#L101-L120
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;
    UserInfoDTO userInfoDTO;

    // Login Formulae Inputs
    EditText formEmail;
    EditText formPassword;
    TextView formForgotPass;

    // Main Interaction Buttons
    Button signIn_regular, signIn_faceBook;
    TextView signUp;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen_03);

        userInfoDTO = UserInfoDTO.getUserInfoDTO();
        findViewById(R.id.LoginScreen_3_SignIn_Google).setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        // Login Formulae Inputs
        formEmail       = findViewById(R.id.LoginScreen_3_FormEmail);
        formPassword    = findViewById(R.id.LoginScreen_3_FormPassword);
        formForgotPass  = findViewById(R.id.LoginScreen_3_FormForgottenPass);

        // Main Interaction Buttons
        signIn_regular = findViewById(R.id.LoginScreen_3_SignIn);
        signIn_faceBook = findViewById( R.id.LoginScreen_3_SignIn_FaceBook);
        signUp = findViewById(R.id.LoginScreen_3_SignUp);

        // setting listeners to it self. see onClick method.
        signIn_regular.setOnClickListener(this);
        signIn_faceBook.setOnClickListener(this);
        signUp.setOnClickListener(this);






        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

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
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);

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
        trans_background        = findViewById(R.id.LoginScreen_BackgroundBlue);

        Intent changeActivity = new Intent( Activity_LoginScreen_03.this, Activity_LoginScreen_02.class);
        ActivityOptionsCompat transitionParameters = ActivityOptionsCompat.makeSceneTransitionAnimation(
                Activity_LoginScreen_03.this,
                // All Custom Shared elements
                new Pair<>(trans_logo, trans_logo.getTransitionName())              ,
                new Pair<>(trans_background,trans_background.getTransitionName())
        );
        startActivity(changeActivity, transitionParameters.toBundle());
    }














    // google

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
                ChangeActivityNext();
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }


    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            userInfoDTO.setUser(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());

                        }

                    }
                });
    }






}
