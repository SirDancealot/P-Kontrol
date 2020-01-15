package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * @responsibilty be the parent for the Map States.
 *
 *
 *
 * */
public class MapFragment extends FragmentActivity implements OnMapReadyCallback, IMapFragment {

    String TAG = "MapFragment";
    Activity context;

    //Defaults
    final int DEFAULT_ZOOM_CLOSEUP = 17;
    final int DEFAULT_ZOOM = 15;
    final LatLng DEFAULT_LOCATION = new LatLng(55.676098, 12.56833);

    //Map Functionality NECESARY VARIABLES
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private LatLng mLastKnownLocation = DEFAULT_LOCATION;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private LatLng selectedLocation = null;

    LiveDataViewModel viewModel;

    //Data
    private IState currentState;

    // Views
    private GoogleMap map;

    //Listeners
    private IMapFragmentListener listener;

    boolean mapReady = false;


    // -- METHODS --  --  --  --  --  --  --  --  --  --  --  --  --
    public MapFragment(SupportMapFragment mapFragment, Activity activity, IMapFragmentListener listener) {

        //Android Stuffs
        this.mapFragment    = mapFragment;
        this.context        = activity  ;
        this.listener       = listener  ;

        // Map Fragment Stuffs
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity);
        mapFragment.getMapAsync(this);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        getPermission();
        styleMapCall();
        map.getUiSettings().setMyLocationButtonEnabled(false);

        viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);

        // Activate Standard State . in this case it is Standby.
        if (map.isMyLocationEnabled()) {
            Log.d(TAG, "onMapReady: location true");
            setStateStandby();
            currentState.centerMethod();
        } else {
            Log.d(TAG, "onMapReady: location false");
        }

        viewModel.getTipList().observe(this, tips -> {
            //currentState.updateMap(tips);
        });

       // listener.onReady();
    }

    private void styleMapCall() {
        // Styling the Map
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = map.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle( (Context) context, R.raw.mapstyling_json ));
            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

    }
    private void getPermission() {
        if (ContextCompat.checkSelfPermission( (context).getApplicationContext(),
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
                            //    listener.onReady();
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions((context),
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
                .addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {

                        if (location != null) {
                            mLastKnownLocation = new LatLng(location.getLatitude(),location.getLongitude());
                            Log.d(TAG, "onSuccess: fandt location");
                            //listener.onReady();
                        }
                    }
                });
    }

    @Override
    public Resources getResources(){
        return context.getResources();
    }
   /* @Override
    public GoogleMap getMap() {
        return map;
    }*/
    @Override
    public void centerMap(){
        currentState.centerMethod();
    }
    @Override
    public void setStateStandby() {//currentState = new StateStandby(this,this);
    }
    @Override
    public void setStateSelectLocation() {
        //currentState = new StateSelectLocation(this,this);
    }
    @Override
    public IState getCurrentState(){
        return currentState;
    }
   /* @Override
    public IMapFragmentListener getFragmentListener() {
        return listener;
    }
    @Override
    public Activity getContext() {
        return context;
    }
    @Override
    public FusedLocationProviderClient getFusedLocationProviderClient() {return mFusedLocationProviderClient;
    }
    @Override
    public LiveDataViewModel getViewModel() {
        return viewModel;
    }*/


    /*


    @Override
    public String getPackageName(){
        return parent.getPackageName();
    }

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
    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return mFusedLocationProviderClient;
    }

    */
}