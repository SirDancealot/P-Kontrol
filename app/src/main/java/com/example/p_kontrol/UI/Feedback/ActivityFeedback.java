package com.example.p_kontrol.UI.Feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.p_kontrol.R;

public class ActivityFeedback extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener {

    Spinner dropDownCategory;
    EditText feedbackText;
    Button sendFeedbackBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);


        dropDownCategory = findViewById(R.id.feedbackDropDownMenu);
        ArrayAdapter<CharSequence> adapter =
                ArrayAdapter.createFromResource(this, R.array.feedback_categories_array,
                        R.layout.support_simple_spinner_dropdown_item);
        dropDownCategory.setAdapter(adapter);
        dropDownCategory.setOnItemSelectedListener(this);

        feedbackText = findViewById(R.id.feedbackEditText);

        sendFeedbackBtn = findViewById(R.id.feedbackButton);
        sendFeedbackBtn.setOnClickListener(this);

    }
    String categoryText;
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
       categoryText = parent.getItemAtPosition(position).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    @Override
    public void onClick(View v) {

        if (categoryText.equals(getResources().getString(R.string.feedback_choose_category))) {
            sendFeedbackBtn.setError("Please choose a category in the drop down menu");
            Toast.makeText(this, sendFeedbackBtn.getError(), Toast.LENGTH_LONG).show();
        } else {
            //TODO slet efter færdig med at teste
            Toast.makeText(v.getContext(),categoryText +"\n"+ feedbackText.getText(), Toast.LENGTH_SHORT).show();

            //TODO send til database/send til mail
        }
    }
}
