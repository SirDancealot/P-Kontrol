package com.example.p_kontrol.UI.UserPersonalisation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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
    private CircleImageView profileImage;
    private TextView profileName, femail;
    private UserInfoDTO userInfoDTO;
    private Button btn_logout, btn_deleteData;
    private YesNoDialogFragment dialogDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userInfoDTO     = UserFactory.getFactory().getDto();
        dialogDelete    = new YesNoDialogFragment(this, 1);

        profileImage    = findViewById(R.id.profilePic);
        profileName     = findViewById(R.id.profile_profileName);
        btn_logout      = findViewById(R.id.logOut);
        btn_deleteData  = findViewById(R.id.deleteData);

        btn_logout.setOnClickListener(this);
        btn_deleteData.setOnClickListener(this);

        String name = userInfoDTO.getFirstName() + " " + userInfoDTO.getLastName();
        profileName.setText(name);
        Glide.with(ActivityProfile.this).load(userInfoDTO.getImageUrl()).into(profileImage);

    }


    @Override
    public void onClick(View v) {
        if (v == backpress) {
            onBackPressed();
        }
        if (v == btn_deleteData) {
            dialogDelete.show(getSupportFragmentManager(), "closeFragment");
        }
        if (v == btn_logout){
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
