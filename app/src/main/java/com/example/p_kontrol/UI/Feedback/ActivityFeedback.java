package com.example.p_kontrol.UI.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.p_kontrol.R;

public class ActivityFeedback extends AppCompatActivity {

    Spinner dropDownCategory;
    EditText feedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        dropDownCategory = findViewById(R.id.feedbackDropDownMenu);
        String[] categoryItems = new String[] {"Map", "Profile", "Login", "Other"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, categoryItems);
        dropDownCategory.setAdapter(adapter);

        feedbackText = findViewById(R.id.feedbackEditText);

    }
}
