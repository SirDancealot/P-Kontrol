package com.example.p_kontrol.UI.LogIn;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.UserFactory;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuAcitvity.MainMenuActivity;
import com.example.p_kontrol.UI.MainMenuAcitvity.YesNoDialogFragment;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
/**
 * @responsibilty to give the user options for logging in.
 * */
public class Activity_LoginScreen_02 extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    // Android Specifiks
    String TAG = "Login Screen 3";

    // dialog frame.
    private YesNoDialogFragment dialogCloseApp;

    // Login with Google
    private static final int RC_SIGN_IN_GOOGLE = 9001;
    private GoogleSignInClient googleSignInClient;

    // Login with FaceBook
    private static final int RC_SIGN_IN_FACEBOOK = 64206;
    private CallbackManager facebookCallbackManager;

    //General
    private FirebaseAuth firebaseAuthenticator;
    private UserInfoDTO userInfoDTO;
    private View loadingSymbol;

    //Service Connection , also Data Access
    private LiveDataViewModel viewModel;
    protected FirestoreDAO dao;
    private boolean bound = false;
    private ServiceConnection connection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            FirestoreDAO.DAOBinder binder = (FirestoreDAO.DAOBinder) service;
            dao = binder.getService();
            bound = true;
            viewModel.setDao(dao);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
            viewModel.setDao(null);
        }
    };

    // code taken from taken from:
    //https://firebase.google.com/docs/auth/android/google-signin
    //https://firebase.google.com/docs/auth/android/facebook-login
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginscreen_02);

        viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);

        firebaseAuthenticator = FirebaseAuth.getInstance();
        userInfoDTO = UserFactory.getFactory().getDto();
        findViewById(R.id.LoginScreen_3_SignIn_Google).setOnClickListener(this);
        dialogCloseApp = new YesNoDialogFragment(this, 1);


        // Login Formulae Inputs
        loadingSymbol = findViewById(R.id.progress_bar);


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        facebookCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.LoginScreen_3_SignIn_FaceBook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
//                System.out.println("---------kkkkk");
                handleFacebookAccessToken(loginResult.getAccessToken());

            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");

            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);

            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //connect to service
        Intent startService = new Intent(this, FirestoreDAO.class);
        bindService(startService, connection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {
        super.onPause();

        unbindService(connection);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.d(TAG, "onActivityResult: " + requestCode);
        loadingSymbol.setVisibility(View.VISIBLE);


        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN_GOOGLE) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        } else if(requestCode == RC_SIGN_IN_FACEBOOK) {
            facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
        } else {
            loadingSymbol.setVisibility(View.GONE);

        }

    }



    //interfaces

    // OnCompleteListener<AuthResult>
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success");

            FirebaseUser user = firebaseAuthenticator.getCurrentUser();
            UserFactory factory = UserFactory.getFactory();
            dao.getUser(user.getUid(), factory, user);

            ChangeActivityNext();
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
        }
    }

    //View.OnClickListener
    @Override
    public void onClick(View v) {
        // each event possible have a unique method for that button event. see below.
        switch(v.getId()){
            case R.id.LoginScreen_3_SignIn_Google:
                signIn_MethodGoogle();
                break;
        }
    }


    // internal calls
    /** operates the signing in with google */
    public void signIn_MethodGoogle(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN_GOOGLE);

    }

    /** operates the signing in with facebook */
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuthenticator.signInWithCredential(credential)
                .addOnCompleteListener(this);
    }

    /** simply changes to next activity, will be called on a succesfull logic*/
    public void ChangeActivityNext(){
        Intent changeActivity = new Intent( this, MainMenuActivity.class);
        changeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(changeActivity);
    }

    /**  checks user with firebase */
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuthenticator.signInWithCredential(credential).addOnCompleteListener(this);
    }


    /**
     * BackStack management
     */
    @Override
    public void onBackPressed() {
        dialogCloseApp.show(getSupportFragmentManager(), "closeFragment");
    }
}
