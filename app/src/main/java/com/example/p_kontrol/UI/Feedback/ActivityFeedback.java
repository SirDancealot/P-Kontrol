package com.example.p_kontrol.UI.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.R;

public class ActivityFeedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

//    IBackend backend = new Backend();
//    Spinner dropDownCategory;
//    EditText feedbackText;
//    Button sendFeedbackBtn;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feedback);
//
//
//        dropDownCategory = findViewById(R.id.feedbackDropDownMenu);
//        ArrayAdapter<CharSequence> adapter =
//                ArrayAdapter.createFromResource(this, R.array.feedback_categories_array,
//                        R.layout.support_simple_spinner_dropdown_item);
//        dropDownCategory.setAdapter(adapter);
//        dropDownCategory.setOnItemSelectedListener(this);
//
//        feedbackText = findViewById(R.id.feedbackEditText);
//
//        sendFeedbackBtn = findViewById(R.id.feedbackButton);
//        sendFeedbackBtn.setOnClickListener(this);
//
//    }
//    String categoryText;
//    @Override
//    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//       categoryText = parent.getItemAtPosition(position).toString();
//    }
//
//    @Override
//    public void onNothingSelected(AdapterView<?> parent) {
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//
//        if (categoryText.equals(getResources().getString(R.string.feedback_choose_category))) {
//            sendFeedbackBtn.setError("Please choose a category in the drop down menu");
//            Toast.makeText(this, sendFeedbackBtn.getError(), Toast.LENGTH_LONG).show();
//        } else {
//            backend.postFeedback(categoryText, feedbackText.getText().toString());
//        }
//
//
//
//
//
//    }

    final String FB_GROUP_ID = "1057084904626319";

    Button navToFacebookBtn;
    //IBackend backend = new Backend();
    Spinner dropDownCategory;
    EditText feedbackText;
    Button sendFeedbackBtn;

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
        navToFacebookPage(FB_GROUP_ID);
    }

    public void navToFacebookPage(String id) {
        try {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://group/" + id));
            startActivity(i);
        } catch (ActivityNotFoundException e) {
            Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/groups/" + id));
            startActivity(i);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {    }
    public void onClick(View v) {

        if (categoryText.equals(getResources().getString(R.string.feedback_choose_category))) {
            sendFeedbackBtn.setError("Please choose a category in the drop down menu");
            Toast.makeText(this, sendFeedbackBtn.getError(), Toast.LENGTH_LONG).show();
        } else {
            //backend.postFeedback(categoryText, feedbackText.getText().toString());
        }
    }
}
