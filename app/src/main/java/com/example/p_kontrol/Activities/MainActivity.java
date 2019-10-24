package com.example.p_kontrol.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.p_kontrol.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ChangeActivity_MapView();
    }

    public void ChangeActivity_MapView() {
        Intent mapView_intent = new Intent(MainActivity.this, ActivityMapView.class);
        startActivity(mapView_intent);
    }
}
