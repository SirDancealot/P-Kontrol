package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @responsibilty be the context for the Map States.
 *
 *
 *
 * */
public class MapContext extends FragmentActivity implements OnMapReadyCallback, IMapContext, IMapStateInformationExpert {

    String TAG = "MapContext";
    private Activity activity;

    //Defaults
    private int DEFAULT_ZOOM_CLOSEUP = 17;
    private int DEFAULT_ZOOM = 15;
    private LatLng DEFAULT_LOCATION = new LatLng(55.676098, 12.56833);

    //Map Functionality NECESARY VARIABLES
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private LatLng mLastKnownLocation = DEFAULT_LOCATION;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng selectedLocation = null;

    //Data
    private IState currentState;
    LiveDataViewModel model;

    // Views
    private GoogleMap map;

    //Listeners
    private IMapContextListener listener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onCreate: ");

        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        model.getTipList().observe(this, tips -> {
            currentState.updateMap(tips);
        });
        model.setCurrentLocation(mLastKnownLocation);


        currentState.updateMap(model.getTipList().getValue());

    }

    // -- METHODS --  --  --  --  --  --  --  --  --  --  --  --  --
    public MapContext(SupportMapFragment mapFragment, Activity activity, IMapContextListener listener) {

        //Android Stuffs
        this.mapFragment = mapFragment;
        this.activity = activity;
        this.listener = listener;

        // Map Fragment Stuffs
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        // Setting Up the Map
        map = googleMap;
        getPermission();
        styleMapCall();
        map.getUiSettings().setMyLocationButtonEnabled(false);


        // Activate Standard State . in this case it is Standby.
        setStateStandby();
        if (map.isMyLocationEnabled()) {
            Log.d(TAG, "onMapReady: location true");
            currentState.centerMethod();
        } else {
            Log.d(TAG, "onMapReady: location false");
        }


        Log.d(TAG, "getLanLng: returner");

        listener.onReady();
    }
    private void styleMapCall() {
        // Styling the Map
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle( (Context) activity , R.raw.mapstyling_json ));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }
    private void getPermission() {
        if (ContextCompat.checkSelfPermission( ( activity).getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            map.setMyLocationEnabled(true);
            Log.d(TAG, "getPermission: true");
            mFusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            if (location != null) {
                                mLastKnownLocation = new LatLng(location.getLatitude(),location.getLongitude());
                                Log.d(TAG, "onSuccess: fandt location");
                                listener.onReady();
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions((activity),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d(TAG, "getPermission: false");
        }
    }
    public void updatePermissions(){
        map.setMyLocationEnabled(true);
        currentState.centerMethod();
        Log.d(TAG, "onRequestPermissionsResult: true");
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener( activity, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            mLastKnownLocation = new LatLng(location.getLatitude(),location.getLongitude());
                            Log.d(TAG, "onSuccess: fandt location");
                            listener.onReady();
                        }
                    }
                });
    }


    @Override
    public LatLng getLocation(){
        currentState.updateLocation();
        return mLastKnownLocation;
    }
    @Override
    public LatLng getSelectedLocation() {
        currentState.updateLocation();
        return selectedLocation;
    }
    @Override
    public void setSelectedLocation(LatLng selectedLocation){
        this.selectedLocation = selectedLocation;
    }
    @Override
    public void setLocaton(LatLng locaton) {

    }
    @Override
    public GoogleMap getMap() {
        return map;
    }
    @Override
    public IMapContextListener getContextListener() {
        return listener;
    }
    @Override
    public void centerMap(){
        currentState.centerMethod();
    }
    @Override
    public void setStateStandby() {
        currentState = new StateStandby(this);
    }
    @Override
    public void setStateSelectLocation() {
        currentState = new StateSelectLocation(this);
    }
    @Override
    public IState getCurrentState(){
        return currentState;
    }
    @Override
    public FusedLocationProviderClient getFusedLocationProviderClient() {
       return mFusedLocationProviderClient;
    }
    @Override
    public Resources getResources(){
        return activity.getResources();
    }

    // INTERFACE INFORMATION EXPERT
    @Override
    public int getDEFUALTmapZoom() {
        return DEFAULT_ZOOM;
    }
    @Override
    public int getDEFAULTmapZoom_closeUp() {
        return DEFAULT_ZOOM_CLOSEUP;
    }
    @Override
    public LatLng getDEFAULTlocation() {
        return DEFAULT_LOCATION;
    }
    @Override
    public String getPackageName(){
        return activity.getPackageName();
    }
}