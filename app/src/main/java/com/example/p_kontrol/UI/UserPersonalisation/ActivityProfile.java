package com.example.p_kontrol.UI.UserPersonalisation;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.R;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.UI.LogIn.Activity_LoginScreen_01;
import com.facebook.AccessToken;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener {
    ImageView backpress;

    ImageView profilePic;
    TextView changeProfilePic;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private CircleImageView fimg;
    private TextView fname, femail;
    private UserInfoDTO userInfoDTO;
    private Button logud;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userInfoDTO = UserInfoDTO.getUserInfoDTO();

        fimg = findViewById(R.id.profilePic);
        fname = findViewById(R.id.profile_profileName);
        logud = findViewById(R.id.logud);
        logud.setOnClickListener(this);




        setContent();

/*
        backpress = findViewById(R.id.backpressImageView);


        changeProfilePic = findViewById(R.id.changeProfilePicTextView);

        profilePic = findViewById(R.id.profilePicImageView);



        backpress.setOnClickListener(this);
        changeProfilePic.setOnClickListener(this);*/
    }


    @Override
    public void onClick(View v) {
        if (v == backpress) {
            onBackPressed();
        }
        if (v == changeProfilePic) {
            openGallery();
        }
        if (v == logud){
            userInfoDTO.setLogin(false);
            userInfoDTO.setUrl("");
            userInfoDTO.setEmail("");
            userInfoDTO.setName("");
            userInfoDTO.setName2("");
            Toast.makeText(ActivityProfile.this,"Logget ud",Toast.LENGTH_LONG).show();
            setContent();
            // [START auth_fui_signout]
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            // ...
                        }
                    });
            // [END auth_fui_signout]
            Handler handler = new Handler();
            handler.postDelayed(() -> {
                Intent i = new Intent(ActivityProfile.this, Activity_LoginScreen_01.class);
                startActivity(i);
            }, 500);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void openGallery() {
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(i, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //TODO adding error handling and tip, if wrong imageformat is chosen
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            profilePic.setImageURI(imageUri);
        }
    }


    public void setContent(){



        if (userInfoDTO.getLogin()) {
            fname.setText(userInfoDTO.getName() + " " + userInfoDTO.getName2());
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.dontAnimate();
            Glide.with(ActivityProfile.this).load(userInfoDTO.getUrl()).into(fimg);
        } else {
            fname.setText("Name");
            fimg.setImageResource(0);
        }
    }
}
