package com.example.p_kontrol.UI.Map;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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

public class MapContext extends AppCompatActivity implements OnMapReadyCallback {

    String TAG = "MapContext";

    //Defaults
    private int DEFAULT_ZOOM = 17;
    private int DEFAULT = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(55.676098, 	12.56833);

    //Map Functionality NECESARY VARIABLES
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private Location mLastKnownLocation;
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
        if ( ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            map.setMyLocationEnabled(true);
        }
        styleMapCall();
        centerMapOnLocation();


        // todo move into States.
        //Center the Camera on the Map.
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentState.centerMapOnLocation(thisContext);
                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }
        });
        listener.onReady();

        // Activate Standard State . in this case it is Standby.
        setStateStandby();
    }

    //Public Calls
    public void setStateStandby(){
        currentState = new StateStandby(this);
    }
    public void setStateSelectLocation(IMapSelectedLocationListener selectListener){

        currentState = new StateSelectLocation(this);
        currentState.setDoneListner(selectListener);

    }

    public void moveCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(geo)
                .zoom(DEFAULT_ZOOM).build();
        //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void zoomCamara(int zoom){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .zoom(zoom).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    public void centerMapOnLocation() {
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener( this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                    } else {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });

        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public void getDeviceLocation() {
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener( this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        mLastKnownLocation = (Location) task.getResult();
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                new LatLng(mLastKnownLocation.getLatitude(),
                                        mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));

                    } else {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });

        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
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

}
