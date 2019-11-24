package com.example.p_kontrol.UI.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.Temp.tipDTO;
import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.example.p_kontrol.UI.Fragments.FragMessageWrite;
import com.example.p_kontrol.UI.Fragments.FragTipBobble;
import com.example.p_kontrol.UI.Fragments.FragTopMessageBar;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class ActivityMapView extends AppCompatActivity implements OnMapReadyCallback ,View.OnClickListener {

    final String TAG = "tag";
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    ConstraintLayout rootContainer;
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ,
            contino;
    boolean drag_State;

    //Booleans for Open Closing Fragments.
    boolean boolFragMessageWrite    ;
    boolean boolFragTipBobble       ;
    boolean boolFragTopMessageBar   ;

    //ViewPager - Tip bobbles.
    FragmentPagerAdapter adapter_TipBobbles;
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
    private LatLng currentMarker;
    private Location mLastKnownLocation;
    private Button center;
    private static final int DEFAULT_ZOOM = 15;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private TipDTO currentTip;


    // temp hardcode
    List<TipDTO> dtoList = new ArrayList<TipDTO>();
    TipDTO tip1, tip2;


    private boolean tempBool = false;
    private int tempID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        // maps
        setUpDemoTip();
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        contino = findViewById(R.id.contino);

        fragmentManager = this.getSupportFragmentManager();
        setupMenu();
        setupFragments();
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
        contino.setOnClickListener(this);

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
            case (R.id.contino):
                continueContribute();
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
        //setupTipBobblesPagerViewer();
        viewPager_tipBobles.setVisibility(View.VISIBLE);
    }
    private void menuBtn_Contribute(View view){

        contino.setVisibility(View.VISIBLE);
        contino.setEnabled(false);
        menu_dragHandle(view);

        zoomCamara(17);
        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                contino.setEnabled(true);
                mMap.clear();
                currentMarker = latLng;
                mMap.addMarker(new MarkerOptions().position(latLng));
            }
        });





        Log.i("click", "Contribute btn clicked \n");

    }

    private void continueContribute(){


        mMap.clear();
        mMap.setOnMapClickListener(null);
        contino.setVisibility(View.GONE);

        fragment_messageWrite = new FragMessageWrite();
        FragmentToogleTransaction(R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;
    }




    private void menuBtn_Community(View view){
        Log.i("click","Community btn clicked \n");


        if (tempBool){
            zoomCamara(DEFAULT_ZOOM);

            // temp løsning
            String text;
            if (currentMarker == null) {
                text = "Ingen marker sat";
            } else {
                // lav sej metode her!
                text = currentMarker.toString();

                TipDTO newTip = new TipDTO();
                newTip.setLocation(currentMarker);
                newTip.setTipId(getNewID());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    newTip.setDate(LocalDate.now());
                }
                if (UserInfoDTO.getUserInfoDTO().getUrl() != null){
                    newTip.setUrl(UserInfoDTO.getUserInfoDTO().getUrl());
                }
                if (UserInfoDTO.getUserInfoDTO().getName() != null) {
                    newTip.setAuthor(UserInfoDTO.getUserInfoDTO().getName());
                }
                newTip.setMessege("xxx"); // write tip
                dtoList.add(newTip);

            }

            Toast.makeText(ActivityMapView.this, text, Toast.LENGTH_SHORT).show();


            mMap.clear();
            mMap.setOnMapClickListener(null);

            tempBool = false;
            currentMarker = null;

            updateMapTips(dtoList);
        } else {
            zoomCamara(17);
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                  return true;
                }
            });
            mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    mMap.clear();
                    currentMarker = latLng;
                    mMap.addMarker(new MarkerOptions().position(latLng));
                }
            });
            tempBool = true;
        }


    }
    private void menuBtn_ParkAlarm(View view){
        Log.i("click","Park Alarm btn clicked \n");
    }
    private void menuBtn_PVagt(View view){
        Log.i("click","P-Vagt btn clicked \n");

        updateDeviceLocation();
        Toast.makeText(ActivityMapView.this,
                "Alarm P-vagt ved: " + String.valueOf(mLastKnownLocation.getLatitude())
                        + " " + String.valueOf(mLastKnownLocation.getLongitude()),
                Toast.LENGTH_SHORT).show();



    }

    private void FragmentToogleTransaction(int containerId, Fragment fragment, boolean Open){

            if(!Open){
                transaction = fragmentManager.beginTransaction();
                try {
                    Log.v("transaction", "Adding fragment");
                    transaction.add(containerId, fragment);
                }catch (IllegalStateException e){
                    Log.v("transaction", "Replacing fragment");
                    transaction.replace(containerId, fragment);
                }
                transaction.commit();
            }else{
                transaction = fragmentManager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                Log.v("transaction","Removing fragment");
            }
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


        updateMapTips(dtoList);

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
    public void zoomCamara(int zoom){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mMap.getCameraPosition().target)
                .zoom(zoom).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void updateMapTips(List<TipDTO> tips){
        for(TipDTO tip: tips){
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("tip",100,100)));
            mMap.addMarker(markerOptions.position(tip.getLocation()).title(String.valueOf(tip.getTipId())));
            mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    // kald den metode du gerne vil have
                    Toast.makeText(ActivityMapView.this, "tip med id: " + marker.getTitle(), Toast.LENGTH_SHORT).show();

                    adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, dtoList);
                    viewPager_tipBobles.setAdapter(adapter_TipBobbles);
                    viewPager_tipBobles.setCurrentItem(Integer.parseInt(marker.getTitle()) - 1);
                    viewPager_tipBobles.setVisibility(View.VISIBLE);




                    return true;
                }
            });
        }

    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
    //https://stackoverflow.com/questions/14851641/change-marker-size-in-google-maps-api-v2
    private void getDeviceLocation() {
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

        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void updateDeviceLocation() {

        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();

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
    private int getNewID(){
        tempID++;
        return tempID;
    }

    private void setUpDemoTip(){
        tip1 = new TipDTO();
        tip1.setTipId(getNewID());
        tip1.setLocation(new LatLng(	55.676098, 	12.568337));
        tip1.setUrl("https://graph.facebook.com/" + "1224894504" + "/picture?type=normal");
        tip1.setAuthor("August");
        tip1.setMessege(getResources().getString(R.string.tip1));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate tempDate = LocalDate.of(2019, 2, 9);
            tip1.setDate(tempDate);
        }
        tip2 = new TipDTO();
        tip2.setTipId(getNewID());
        tip2.setLocation(new LatLng(	55.679098, 	12.569337));
        tip2.setUrl("https://graph.facebook.com/" + "100009221661122" + "/picture?type=normal");
        tip2.setAuthor("Hans the Human");
        tip2.setMessege(getResources().getString(R.string.tip2));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate tempDate = LocalDate.of(2019, 7, 13);
            tip2.setDate(tempDate);
        }
        dtoList.add(tip1);
        dtoList.add(tip2);

    }


    public void makeTip(TipDTO tip){


        tip.setLocation(currentMarker);
        tip.setTipId(getNewID());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            tip.setDate(LocalDate.now());
        }
        if (UserInfoDTO.getUserInfoDTO().getUrl() != null){
            tip.setUrl(UserInfoDTO.getUserInfoDTO().getUrl());
        }
        if (UserInfoDTO.getUserInfoDTO().getName() != null) {
            tip.setAuthor(UserInfoDTO.getUserInfoDTO().getName());
        }
        dtoList.add(tip);
        zoomCamara(DEFAULT_ZOOM);
        updateMapTips(dtoList);
        FragmentToogleTransaction(R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;
    }






}
