package com.example.p_kontrol.UI.LogIn;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuAcitvity.MainMenuActivity;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


// code from:
// https://www.youtube.com/watch?v=EO-_vwfVi7c
// https://github.com/firebase/snippets-android/blob/f6c050fab9aa28112dd3057e1997cd3d11e3c1cf/auth/app/src/main/java/com/google/firebase/quickstart/auth/FirebaseUIActivity.java#L31-L45
public class Activity_LoginScreen_Demo extends AppCompatActivity {


    private static final int RC_SIGN_IN = 3452;
    private List<AuthUI.IdpConfig> providers;
    private Button btn;
    private UserInfoDTO userInfoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__login_screen__demo);
        btn = findViewById(R.id.btn_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        createSignInIntent();
    }

    public void createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers
        providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.login)
                        .setLogo(R.drawable.logo)
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                btn.setEnabled(true);
                Intent changeActivity = new Intent( this , MainMenuActivity.class );
                startActivity(changeActivity);
                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
    // [END auth_fui_result]



    public void delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
        // [END auth_fui_delete]
    }

    public void themeAndLogo() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.logo)      // Set logo drawable
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_theme_logo]
    }

    public void privacyAndTerms() {
        List<AuthUI.IdpConfig> providers = Collections.emptyList();
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN);
        // [END auth_fui_pp_tos]
    }
}
