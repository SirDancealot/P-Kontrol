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
import com.example.p_kontrol.DataTypes.UserFactory;
import com.example.p_kontrol.R;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.UI.LogIn.Activity_LoginScreen_01;
import com.example.p_kontrol.UI.MainMenuAcitvity.YesNoDialogFragment;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import de.hdodenhof.circleimageview.CircleImageView;

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener {
    ImageView backpress;

    ImageView profilePic;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;
    private CircleImageView fimg;
    private TextView fname, femail;
    private UserInfoDTO userInfoDTO;
    private Button logOut, delete;
    private YesNoDialogFragment dialogDelete;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userInfoDTO = UserFactory.getFactory().getDto();
        dialogDelete = new YesNoDialogFragment(this, 1);

        fimg = findViewById(R.id.profilePic);
        fname = findViewById(R.id.profile_profileName);
        logOut = findViewById(R.id.logOut);
        delete = findViewById(R.id.deleteData);

        logOut.setOnClickListener(this);
        delete.setOnClickListener(this);

        fname.setText(userInfoDTO.getFirstName() + " " + userInfoDTO.getLastName());
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();
        Glide.with(ActivityProfile.this).load(userInfoDTO.getImageUrl()).into(fimg);

    }


    @Override
    public void onClick(View v) {
        if (v == backpress) {
            onBackPressed();
        }
        if (v == delete) {
            dialogDelete.show(getSupportFragmentManager(), "closeFragment");
            // todo delete all user data
        }
        if (v == logOut){
            UserFactory.getFactory().setDto(null);
            AuthUI.getInstance()
                    .signOut(this)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        public void onComplete(@NonNull Task<Void> task) {
                            Intent i = new Intent(ActivityProfile.this, Activity_LoginScreen_01.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(i);
                        }
                    });
        }
    }
}
