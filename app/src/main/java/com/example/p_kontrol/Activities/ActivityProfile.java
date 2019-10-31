package com.example.p_kontrol.Activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p_kontrol.R;

public class ActivityProfile extends AppCompatActivity implements View.OnClickListener {
    ImageView backpress;

    ImageView profilePic;
    TextView changeProfilePic;
    Uri imageUri;
    private static final int PICK_IMAGE = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        backpress = findViewById(R.id.backpressImageView);


        changeProfilePic = findViewById(R.id.changeProfilePicTextView);

        profilePic = findViewById(R.id.profilePicImageView);



        backpress.setOnClickListener(this);
        changeProfilePic.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if (v == backpress) {
            onBackPressed();
        }
        if (v == changeProfilePic) {
            openGallery();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //    private void pickProfilePictureFromGallery() {
//        //Create an Intent with action as ACTION_PICK
//        Intent intent=new Intent(Intent.ACTION_PICK);
//        // Sets the type as image/*. This ensures only components of type image are selected
//        intent.setType("image/*");
//        //We pass an extra array with the accepted mime types. This will ensure only components with these MIME types as targeted.
//        String[] mimeTypes = {"image/jpeg", "image/png"};
//        intent.putExtra(Intent.EXTRA_MIME_TYPES,mimeTypes);
//        // Launching the Intent
//        startActivityForResult(intent,GALLERY_REQUEST_CODE);
//    }

//    @Override
//    public void onActivityResult(int requestCode,int resultCode,Intent data){
//        // Result code is RESULT_OK only if the user selects an Image
//        if (resultCode == Activity.RESULT_OK)
//            switch (requestCode){
//                case GALLERY_REQUEST_CODE:
//                    //data.getData returns the content URI for the selected Image
//                    Uri selectedImage = data.getData();
//                    ImageView imageView = (ImageView) findViewById(R.id.profilePicImageView);
//                    imageView.setImageURI(selectedImage);
//                    break;
//            }
//    }

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
}
