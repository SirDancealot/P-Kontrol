package com.example.p_kontrol.UI.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;
/**
 * @responsibilty to give users options to give feedback, on a facebook discussion
 * */
public class ActivityFeedback extends AppCompatActivity implements View.OnClickListener {


    private final String facebookGroupId = "1057084904626319";
    private Button navToFacebookBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        navToFacebookBtn = findViewById(R.id.feedbackFbBtn);
        navToFacebookBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //TODO This links to "DTU - brugte bøger", change this to the id in the addressbar of the desired group.
        navToFacebookPage(facebookGroupId);
    }

    /**
     * Navigate to Facebook page, on facebook app, or Browser window.
     * */
    public void navToFacebookPage(String id) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/" + id));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/" + id));
            startActivity(i);
        }
    }

}
