package com.example.p_kontrol.UI.Activities;

import android.Manifest;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.example.p_kontrol.UI.Fragments.FragMessageWrite;
import com.example.p_kontrol.UI.Fragments.FragTipBobble;
import com.example.p_kontrol.UI.Fragments.FragTopMessageBar;
import com.example.p_kontrol.R;
import com.example.p_kontrol.Temp.tipDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.util.LinkedList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class ActivityMapView extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener ,View.OnClickListener {

    final String TAG = "tag";
    FragmentManager fragmentManager;
    FragmentTransaction transaction;
    boolean firstTransAction;

    ConstraintLayout rootContainer;
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    boolean drag_State;

    //Booleans for Open Closing Fragments.
    boolean boolFragMessageWrite    ;
    boolean boolFragTipBobble       ;
    boolean boolFragTopMessageBar   ;

    //ViewPager - Tip bobbles.
    FragmentStatePagerAdapter adapter_TipBobbles;
    ViewPager viewPager_tipBobles;

    //Fragments
    FragMessageWrite    fragment_messageWrite   ;
    FragTipBobble       fragment_tipBobble      ;
    FragTopMessageBar   fragment_topMessage     ;

    // Maps
    private GoogleMap mMap;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private boolean mLocationPermissionGranted;
    private final LatLng mDefaultLocation = new LatLng(55.676098, 	12.56833);
    private Location mLastKnownLocation;
    private Button center;
    private static final int DEFAULT_ZOOM = 15;
    private FusedLocationProviderClient mFusedLocationProviderClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        // maps
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        fragmentManager = this.getSupportFragmentManager();
        setupMenu();
        setupFragments();
        setupTipBobblesPagerViewer();
        firstTransAction = true;
    }

    // setups
    private void setupMenu(){

        rootContainer       = findViewById(R.id.ActivityMapView_RootContainer);
        // Menu Buttons.
        menuBtnContainer     = findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = findViewById(R.id.menuBtn_draggingHandle)      ;

        menuBtn_profile      = findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = findViewById(R.id.menuBtn_Contribute)          ;
        menuBtn_Community    = findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = findViewById(R.id.menuBtn_PVagt)               ;

        dragHandle.setOnClickListener(this);
        menuBtn_profile.setOnClickListener(this);
        menuBtn_FreePark.setOnClickListener(this);
        menuBtn_Contribute.setOnClickListener(this);
        menuBtn_Community.setOnClickListener(this);
        menuBtn_ParkAlarm.setOnClickListener(this);
        menuBtn_PVagt.setOnClickListener(this);

        // ViewPager
        viewPager_tipBobles = findViewById(R.id.viewPager_TipBobbles);

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
    private void setupTipBobblesPagerViewer(){

        List<tipDTO> tipsList = new LinkedList();
        tipsList.add(new tipDTO("Mig","lad være at parkere her! det er min plads og kun min!!!! "));
        tipsList.add(new tipDTO("Dig","sur gammel dame kaster egg efter min bil"));
        tipsList.add(new tipDTO("bente"," fugle der skider på bilen hvis dine spejle ikke er dækkede"));
        tipsList.add(new tipDTO("gurli"," ulovlig parking mellem 10 - 12. med mindre den er gul, eller det er onsdag"));
        tipsList.add(new tipDTO("Bob"," ny besked "));

        adapter_TipBobbles = new TipBobblesAdapter(fragmentManager,tipsList);
        viewPager_tipBobles.setAdapter(adapter_TipBobbles);

    }

    // Listener
    @Override
    public void onClick(View v){
        if (v == center){
            getDeviceLocation();
        }
        switch(v.getId()){
            case ( R.id.menuBtn_draggingHandle):
                menu_dragHandle(v);
                break;
                // Menu Line 1.
            case (R.id.menuBtn_profile):
                menuBtn_profile(v);
                break;
            case (R.id.menuBtn_FreePark):
                menuBtn_FreePark(v);
                break;
            case (R.id.menuBtn_Contribute):
                menuBtn_Contribute(v);
                break;
                // Menu Line 2.
            case (R.id.menuBtn_Community):
                menuBtn_Community(v);
                break;
            case (R.id.menuBtn_ParkAlarm):
                menuBtn_ParkAlarm(v);
                break;
            case (R.id.menuBtn_PVagt):
                menuBtn_PVagt(v);
                break;
                // TipBobbleViewPager
        }

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
            transaction.add(R.id.midScreenFragmentContainer, fragment_messageWrite);
            transaction.commit();
            firstTransAction = false;
            Log.i("transaction","First TransAction");
        }else {
            if(!boolFragMessageWrite){
                Log.i("transaction","Replacing fragment");
                transaction = fragmentManager.beginTransaction();
                transaction.replace(R.id.midScreenFragmentContainer, fragment_messageWrite);
                transaction.commit();
            }else{
                transaction = fragmentManager.beginTransaction();
                transaction.remove(fragment_messageWrite);
                transaction.commit();
                Log.i("transaction","Removing fragment");
            }
        }
        boolFragMessageWrite = !boolFragMessageWrite;

        Log.i("click", "Contribute btn clicked \n");

    }

    // TipBobbles View Pager
    public void CloseTipBobbleViewPager(){
      //  rootContainer.removeView(viewPager_tipBobles);
        viewPager_tipBobles.setVisibility(View.GONE);
        Log.i(TAG, "CloseTipBobbleViewPager: Closed");
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        center = findViewById(R.id.centerBut);

        center.setOnClickListener(this);

        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.logo));

        LatLng tip = new LatLng(	55.675098, 	12.569337);
        LatLng currentGeo = new LatLng(	55.676098, 	12.568337);
        mMap.addMarker(markerOptions.position(tip).title("tip"));
        mMap.setOnMarkerClickListener(this);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        }

        getDeviceLocation();
    }

    public void moveCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(geo)
                .zoom(DEFAULT_ZOOM).build();
        //mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public boolean onMarkerClick(Marker marker) {

        // jeg kan ikke finde ud af at navigerer til ActivtyProfile så har bare gjort
        // så når man trykker på et tip kommer man derover. lav gerne om!
        Intent i = new Intent(ActivityMapView.this, ActivityProfile.class);
        startActivity(i);

        //Toast.makeText(ActivityMapView.this, "tip", Toast.LENGTH_SHORT).show();
        return true;
    }








    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                    } else {
                        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                        mMap.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
