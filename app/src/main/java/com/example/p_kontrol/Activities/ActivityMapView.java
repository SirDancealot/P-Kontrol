package com.example.p_kontrol.Activities;

import android.annotation.SuppressLint;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.p_kontrol.Fragments.FragBottomMenu;
import com.example.p_kontrol.Fragments.FragMessageWrite;
import com.example.p_kontrol.Fragments.FragTipBobble;
import com.example.p_kontrol.Fragments.FragTopMessageBar;
import com.example.p_kontrol.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.LinkedList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class ActivityMapView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    boolean firstTransAction;

    // Menu Code.
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    boolean drag_State;

    //Booleans for Open Closing Fragments.
    boolean boolFragMessageWrite    ;
    boolean boolFragTipBobble       ;
    boolean boolFragTopMessageBar   ;

    //Fragments
    FragMessageWrite    fragment_messageWrite   ;
    FragTipBobble       fragment_tipBobble      ;
    FragTopMessageBar   fragment_topMessage     ;

    // Maps
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        // maps
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fragmentManager = this.getSupportFragmentManager();
        setupMenu();
        setupFragments();
        firstTransAction = true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
    }


    // setups
    private void setupMenu(){
        // Menu Buttons.
        menuBtnContainer     = findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = findViewById(R.id.menuBtn_draggingHandle)      ;

        menuBtn_profile      = findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = findViewById(R.id.menuBtn_Contribute)          ;
        menuBtn_Community    = findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = findViewById(R.id.menuBtn_PVagt)               ;

        setupMenuListeners(
                dragHandle          ,
                menuBtn_profile     ,
                menuBtn_FreePark    ,
                menuBtn_Contribute  ,
                menuBtn_Community   ,
                menuBtn_ParkAlarm   ,
                menuBtn_PVagt
        );

        // Setup Menu Toggle Position
        drag_State = false;
        menuBtnContainer.setVisibility(View.GONE);
    }
    private void setupFragments(){

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;
        boolFragTopMessageBar   = false;

        fragment_messageWrite = new FragMessageWrite()  ;
        fragment_tipBobble    = new FragTipBobble()     ;
        fragment_topMessage   = new FragTopMessageBar() ;
    }

    // Listener
    private void setupMenuListeners(
            View dragHandle             ,
            Button menuBtn_profile      ,
            Button menuBtn_FreePark     ,
            Button menuBtn_Contribute   ,
            Button menuBtn_Community    ,
            Button menuBtn_ParkAlarm    ,
            Button menuBtn_PVagt        ){
        // Dragging Handle
        dragHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dragHandle(v);
            }
        });

        // Menu Buttons Row 1

        // Profile
        menuBtn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_profile(v);
            }
        });

        // FreePark
        menuBtn_FreePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_FreePark(v);
            }
        });

        // Contribute
        menuBtn_Contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_Contribute(v);
            }
        });

        // Menu Buttons Row 2

        // Community
        menuBtn_Community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_Community(v);
            }
        });

        // Park Alarm
        menuBtn_ParkAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_ParkAlarm(v);
            }
        });

        // P-Vagt
        menuBtn_PVagt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_PVagt(v);
            }
        });
    }

    // Dragging Handle
    private void menu_dragHandle( View view ){

        // drag state is a boolean, so if 1 its open, if 0 its closed. standard is 0.
        if(drag_State){
            Log.v("click","Menu Container Closed\n");
            menuBtnContainer.setVisibility(View.GONE);
            drag_State = false;
        }else{
            Log.v("click","Menu Container Open\n");
            menuBtnContainer.setVisibility(View.VISIBLE);
            drag_State = true;
        }

    }

    // Menu Buttons on click functions.
    private void menuBtn_profile(View view){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }
    private void menuBtn_FreePark(View view){
        Log.i("click","FreePark btn clicked \n");
    }
    private void menuBtn_Contribute(View view){

        if(!boolFragMessageWrite){
            Log.v("bool","bool true");
            fragment_messageWrite = new FragMessageWrite();
        }
        useTransaction (R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite = !boolFragMessageWrite;

        Log.i("click", "Contribute btn clicked \n");

    }
    private void menuBtn_Community(View view){
        Log.i("click","Community btn clicked \n");
    }
    private void menuBtn_ParkAlarm(View view){
        Log.i("click","Park Alarm btn clicked \n");
    }
    private void menuBtn_PVagt(View view){
        Log.i("click","P-Vagt btn clicked \n");
    }

    private void useTransaction (int containerId, Fragment fragment, boolean openOrClose){
        if(firstTransAction){
            transaction = fragmentManager.beginTransaction();
            transaction.add(containerId, fragment);
            transaction.commit();
            firstTransAction = false;
            Log.i("transaction","First TransAction");
        }else {
            if(!openOrClose){
                Log.i("transaction","Replacing fragment");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(containerId, fragment);
                transaction.commit();
            }else{
                transaction = fragmentManager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                Log.i("transaction","Removing fragment");
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.logo));

        // Add a marker in Sydney and move the camera
        LatLng tip = new LatLng(	55.676098, 	12.568337);
        mMap.addMarker(markerOptions.position(tip).title("tip"));
        mMap.setOnMarkerClickListener(this);
        mMap.moveCamera(CameraUpdateFactory.newLatLng(tip));
        mMap.animateCamera(CameraUpdateFactory.zoomIn());
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        System.out.println("yeeer");

        //Toast.makeText(ActivityMapView.this, "tip", Toast.LENGTH_SHORT).show();
        return true;
    }
}
