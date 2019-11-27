package com.example.p_kontrol.UI.Contexts;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

public class MapContext implements OnMapReadyCallback {

    //Defaults
    private int DEFAULT_ZOOM = 17;
    private int DEFAULT = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(55.676098, 	12.56833);
    String TAG = "MapContext";

    //Map Functionality NECESARY VARIABLES
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Activity activity;

    //Regular Variables
    private List<ITipDTO> listOfTipDto;
    private IState state;

    // Views
    private GoogleMap map   ;
    private Button centerBtn;
    private Button acceptBtn;

    //UserData
    private Location userlocation     ;
    private LatLng currentMarkerLoc ;

    //States
    private IState stateStandby ,stateSelectLocation;

    //Listeners
    private View.OnClickListener mapAcceptButtonListener;
    private IMapContextListener listener;

    private final MapContext thisContext = this;

    public MapContext(SupportMapFragment mapFragment,
                      Activity context,
                      Button centerBtn,
                      Button acceptBtn,
                      IMapContextListener listener){

        this.mapFragment = mapFragment;
        this.activity = context;
        this.map = map;
        this.centerBtn = centerBtn;
        this.acceptBtn = acceptBtn;
        this.listener = listener;

        mFusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(context);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap){
        Log.d(TAG, "onMapReady() called with: googleMap = [" + googleMap + "]");

        // Setting Up the Map
        map = googleMap;
        if ( ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            map.setMyLocationEnabled(true);
        }
        centerMapOnLocation();

        //Center the Camera on the Map.
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                state.centerMapOnLocation(thisContext);

                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }
        });

        // Retrieving Information.

        // Sends Back a listener call.
        listener.onReady();

        setStateStandby();

    }

    //Public Calls
    public void setStateStandby(){

        state = new StateStandby(this);
    }
    public void setStateSelectLocation(IMapSelectedLocationListener selectListener){
        state = new StateSelectLocation(this);
        state.setDoneListner(selectListener);
    }

    // private Calls
    public void centerMapOnLocation(){
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        userlocation = (Location) task.getResult();

                    } else {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });

        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    public Resources getResources(){
        return activity.getResources();
    }


    public void setListOfTipDto(List<ITipDTO> tips){
        listOfTipDto = tips;

    }





    public int getDEFAULT_ZOOM() {
        return DEFAULT_ZOOM;
    }

    public LatLng getDEFAULT_LOCATION() {
        return DEFAULT_LOCATION;
    }

    public String getTAG() {
        return TAG;
    }

    public SupportMapFragment getMapFragment() {
        return mapFragment;
    }

    public FusedLocationProviderClient getmFusedLocationProviderClient() {
        return mFusedLocationProviderClient;
    }

    public Activity getActivity() {
        return activity;
    }

    public List<ITipDTO> getListOfTipDto() {
        return listOfTipDto;
    }

    public IState getState() {
        return state;
    }

    public GoogleMap getMap() {
        return map;
    }

    public Button getCenterBtn() {
        return centerBtn;
    }

    public Button getAcceptBtn() {
        return acceptBtn;
    }

    public Location getUserlocation() {
        return userlocation;
    }

    public LatLng getCurrentMarkerLoc() {
        return currentMarkerLoc;
    }

    public IState getStateStandby() {
        return stateStandby;
    }

    public IState getStateSelectLocation() {
        return stateSelectLocation;
    }

    public View.OnClickListener getMapAcceptButtonListener() {
        return mapAcceptButtonListener;
    }

    public IMapContextListener getListener() {
        return listener;
    }
}
