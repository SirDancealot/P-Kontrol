package com.example.p_kontrol.UI.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.p_kontrol.R;

public class ActivityFeedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    Spinner dropDownCategory;
    EditText feedbackText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        dropDownCategory = findViewById(R.id.feedbackDropDownMenu);


        //TODO skift til at vælge strings fra xml
        //slet hvis hentning fra string res virker
        String[] categoryItems = new String[] {"Map", "Profile", "Login", "Other"};


        ArrayAdapter<CharSequence> adapter =


//                ArrayAdapter.createFromResource(this, R.array.feedback_categories_array, R.layout.support_simple_spinner_dropdown_item);

                //slet hvis linje over virker
                new ArrayAdapter<>(this,R.layout.support_simple_spinner_dropdown_item, categoryItems);


        dropDownCategory.setAdapter(adapter);
        dropDownCategory.setOnItemSelectedListener(this);


        feedbackText = findViewById(R.id.feedbackEditText);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

}
