package com.example.p_kontrol.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

public class ActivityLoginScreen extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_screen_scene1);
        Button login = findViewById(R.id.login_button);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

    }

    // todo ?? hvad er dette view == ... det lyder sært.
    @Override
    public void onClick(View view) {
        if (view == findViewById(R.id.login_button)){
            setContentView(R.layout.activity_login_screen);
        }
        if (view == findViewById(R.id.login_button)){
            Intent mapView_intent = new Intent(this, ActivityMapView.class);
            startActivity(mapView_intent);
        }
    }
}
