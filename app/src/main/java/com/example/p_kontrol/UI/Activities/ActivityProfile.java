package com.example.p_kontrol.UI.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Services.UserInfoDTO;
import com.facebook.AccessToken;

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
            AccessToken.setCurrentAccessToken(null);
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
