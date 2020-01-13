package com.example.p_kontrol.UI.UserPersonalisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivitySettings extends AppCompatActivity implements View.OnClickListener, OnCompleteListener<AuthResult> {

    String TAG = "signUp";

    EditText formEmail;
    EditText formPassword;
    TextView create;

    private FirebaseAuth mAuth;
    UserInfoDTO userInfoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.example_settings);

        // Login Formulae Inputs
        formEmail       = findViewById(R.id.signUp_FormEmail);
        formPassword    = findViewById(R.id.signUp_FormPassword);
        create          = findViewById(R.id.signUp_SignIn);
        create.setOnClickListener(this);
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        validateForm();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this);
    }



    public void signUp(){
        System.out.println("------kkkkk");
        createAccount(formEmail.getText().toString(), formPassword.getText().toString());
    }

    private boolean validateForm() {
        boolean valid = true;

        String email = formEmail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            formEmail.setError("Required.");
            valid = false;
        } else {
            formEmail.setError(null);
        }

        String password = formPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            formPassword.setError("Required.");
            valid = false;
        } else {
            formPassword.setError(null);
        }

        return valid;
    }

    @Override
    public void onClick(View v) {
        System.out.println("tryk--------");
        signUp();
    }

    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            // Sign in success, update UI with the signed-in user's information
            Log.d(TAG, "signInWithCredential:success");
            System.out.println("---------kkkkk facebook inde");
            FirebaseUser user = mAuth.getCurrentUser();
            userInfoDTO.setUser(user);
            ChangeActivityNext();
        } else {
            // If sign in fails, display a message to the user.
            Log.w(TAG, "signInWithCredential:failure", task.getException());
        }
    }


    public void ChangeActivityNext(){
        Intent changeActivity = new Intent( this, MainMenuActivity.class);
        changeActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(changeActivity);
    }
}
