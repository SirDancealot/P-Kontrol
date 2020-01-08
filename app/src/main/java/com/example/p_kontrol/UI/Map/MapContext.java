package com.example.p_kontrol.UI.Map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Build;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
import java.util.concurrent.Executor;

public class MapContext extends FragmentActivity implements OnMapReadyCallback {

    String TAG = "MapContext";

    //Defaults
    private int DEFAULT_ZOOM = 17;
    private int DEFAULT = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(55.676098, 	12.56833);

    //Map Functionality NECESARY VARIABLES
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private Location mLastKnownLocation;
    private boolean mLocationPermissionGranted;

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public void setDEFAULT_ZOOM(int DEFAULT_ZOOM) {
        this.DEFAULT_ZOOM = DEFAULT_ZOOM;
    }

    public int getDEFAULT() {
        return DEFAULT;
    }

    public void setDEFAULT(int DEFAULT) {
        this.DEFAULT = DEFAULT;
    }

    public static int getPermissionsRequestAccessFineLocation() {
        return PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
    }

    public void setMapFragment(SupportMapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public Location getmLastKnownLocation() {
        return mLastKnownLocation;
    }

    public void setmLastKnownLocation(Location mLastKnownLocation) {
        this.mLastKnownLocation = mLastKnownLocation;
    }

    public void setmFusedLocationProviderClient(FusedLocationProviderClient mFusedLocationProviderClient) {
        this.mFusedLocationProviderClient = mFusedLocationProviderClient;
    }

    public void setActivity(MainMenuActivity activity) {
        this.activity = activity;
    }

    public MapContext getThisContext() {
        return thisContext;
    }

    public void setThisContext(MapContext thisContext) {
        this.thisContext = thisContext;
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public void setUserlocation(Location userlocation) {
        this.userlocation = userlocation;
    }

    public void setCurrentMarkerLoc(LatLng currentMarkerLoc) {
        this.currentMarkerLoc = currentMarkerLoc;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public void setCenterBtn(Button centerBtn) {
        this.centerBtn = centerBtn;
    }

    public void setCancelBtn(Button cancelBtn) {
        this.cancelBtn = cancelBtn;
    }

    public void setAcceptBtn(Button acceptBtn) {
        this.acceptBtn = acceptBtn;
    }

    public void setStateStandby(IState stateStandby) {
        this.stateStandby = stateStandby;
    }

    public void setStateSelectLocation(IState stateSelectLocation) {
        this.stateSelectLocation = stateSelectLocation;
    }

    public void setListener(IMapContextListener listener) {
        this.listener = listener;
    }

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MainMenuActivity activity;
    private MapContext thisContext =this;

    //Data
    private List<ITipDTO> listOfTipDto;
    private IState currentState;
    private Location userlocation   ;
    private LatLng currentMarkerLoc ;

    // Views
    private GoogleMap map   ;
    private Button centerBtn;
    private Button cancelBtn;
    private Button acceptBtn;

    //States
    private IState stateStandby ,stateSelectLocation;

    //Listeners
    private IMapContextListener listener;

// -- METHODS --  --  --  --  --  --  --  --  --  --  --  --  --
    public MapContext(SupportMapFragment mapFragment, MainMenuActivity activity, Button centerBtn, Button cancelBtn, Button acceptBtn, IMapContextListener listener){

        //Android Stuffs
        this.mapFragment= mapFragment;
        this.activity   = activity;
        this.listener   = listener;

        // interaction Buttons
        this.centerBtn = centerBtn;
        this.cancelBtn = cancelBtn;
        this.acceptBtn = acceptBtn;

        // Map Fragment Stuffs
        mFusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(activity);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap){

        // Setting Up the Map
        map = googleMap;



        styleMapCall();
        getPersission();


        // todo move into States.
        //Center the Camera on the Map.
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState.centerMapOnLocation();
                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }
        });
        listener.onReady();

        // Activate Standard State . in this case it is Standby.
        setStateStandby();
        currentState.centerMapOnLocation();
    }

    //Public Calls
    public void setStateStandby(){
        currentState = new StateStandby(this);
    }
    public void setStateSelectLocation(IMapSelectedLocationListener selectListener){

        currentState = new StateSelectLocation(this);
        currentState.setDoneListner(selectListener);

    }



    public Resources getResources(){
        return activity.getResources();
    }
    private void styleMapCall(){
        // Styling the Map
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            activity, R.raw.mapstyling_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }



    // ---  GET / SET ---  GET / SET ---  GET / SET ---  GET / SET
    // get UI elements
    public GoogleMap getMap() {
        return map;
    }
    public Button getCenterBtn() {
        return centerBtn;
    }
    public Button getAcceptBtn() {
        return acceptBtn;
    }
    public Button getCancelBtn() {
        return cancelBtn;
    }

    // State Pattern Specifiks
    public IState getCurrentState() {
        return currentState;
    }
    public IState getStateStandby() {
        return stateStandby;
    }
    public IState getStateSelectLocation() {
        return stateSelectLocation;
    }
    public IMapContextListener getListener() {
        return listener;
    }

    // Google Map Specifiks and Defaults refering to Google Map
    public void updateMap(){
        listOfTipDto = activity.getDTOList();
        currentState.updateMap();
    }
    public int getDEFAULT_ZOOM() {
        return DEFAULT_ZOOM;
    }
    public LatLng getDEFAULT_LOCATION() {
        return DEFAULT_LOCATION;
    }
    public Location getUserlocation() {
        return userlocation;
    }
    public LatLng getCurrentMarkerLoc() {
        return currentMarkerLoc;
    }
    public SupportMapFragment getMapFragment() {
        return mapFragment;
    }
    public FusedLocationProviderClient getmFusedLocationProviderClient() {
        return mFusedLocationProviderClient;
    }

    // Tips
    public void setListOfTipDto(List<ITipDTO> tips){
        listOfTipDto = tips;

    }
    public List<ITipDTO> getListOfTipDto() {
        return listOfTipDto;
    }

    // android specifiks.
    public String getTAG() {
        return TAG;
    }
    public Activity getActivity() {
        return activity;
    }



    private void getPersission(){

        if (ContextCompat.checkSelfPermission(this.getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }


    }


    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        Log.d(TAG, "onRequestPermissionsResult: OK");
        map.setMyLocationEnabled(true);
    }



}
