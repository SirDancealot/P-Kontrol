package com.example.p_kontrol.Activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class ActivityMapView extends AppCompatActivity {

    View dragHandle;
    Button menuBtn_profile;
    Button menuBtn_FreePark;
    Button menuBtn_Contribute;
    Button menuBtn_Community;
    Button menuBtn_ParkAlarm;
    Button menuBtn_PVagt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        // Menu Code.
        View menuContainer          = (View) findViewById(R.id.menu_Container);
        View dragHandle             = (View) findViewById(R.id.menuBtn_draggingHandle);

        Button menuBtn_profile      = (Button) findViewById(R.id.menuBtn_profile);
        Button menuBtn_FreePark     = (Button) findViewById(R.id.menuBtn_FreePark);
        Button menuBtn_Contribute   = (Button) findViewById(R.id.menuBtn_Contribute);
        Button menuBtn_Community    = (Button) findViewById(R.id.menuBtn_Community);
        Button menuBtn_ParkAlarm    = (Button) findViewById(R.id.menuBtn_ParkAlarm);
        Button menuBtn_PVagt        = (Button) findViewById(R.id.menuBtn_PVagt);

        // Resetting UI Elements Position.
        int dragHandlePOS = 0;
        //menuContainer.

        // Dragging Handle
        dragHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        // Menu Buttons
        // Profile
        menuBtn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile btn clicked \n");
            }
        });
        // Free Park
        menuBtn_FreePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile btn clicked \n");
            }
        });
        // Contribute
        menuBtn_Contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile btn clicked \n");
            }
        });
        // Community
        menuBtn_Community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile btn clicked \n");
            }
        });
        // ParkAlarm
        menuBtn_ParkAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile btn clicked \n");
            }
        });
        // P-Vagt
        menuBtn_PVagt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("profile btn clicked \n");
            }
        });

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

    }

}
