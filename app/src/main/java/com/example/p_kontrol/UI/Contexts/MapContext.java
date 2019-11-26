package com.example.p_kontrol.UI.Contexts;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.Activities.ActivityMapView;
import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.example.p_kontrol.UI.Fragments.IFragWriteMessageListener;
import com.example.p_kontrol.UI.Services.ITipDTO;
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

import java.util.List;

public class MapContext implements OnMapReadyCallback {

    //Defaults
    private int DEFAULT_ZOOM = 17;
    private final LatLng DEFAULT_LOCATION = new LatLng(55.676098,12.56833);
    String TAG = "MapContext";

    //Map Functionality NECESARY VARIABLES
    private SupportMapFragment mapFragment;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private Activity context;

    //Regular Variables
    private List<ITipDTO> listOfTipDto;
    private IState state;

    // Views
    private GoogleMap map;
    private Button centerBtn;
    private Button acceptBtn;

    //UserData
    private LatLng userlocation;
    private LatLng currentMarkerLoc;

    //States
    private IState stateStandby     ,stateSelectLocation;
    private IMapStateListener sateStandbyListener, stateSelectLocListener;

    //Listeners
    private View.OnClickListener mapAcceptButtonListener;
    private IMapContextListener listener;

    public MapContext(SupportMapFragment mapFragment,
                      Activity context,
                      GoogleMap map,
                      Button centerBtn,
                      Button acceptBtn,
                      IMapContextListener listener){

        this.mapFragment = mapFragment;
        this.context = context;
        this.map = map;
        this.centerBtn = centerBtn;
        this.acceptBtn = acceptBtn;
        this.listener = listener;

        mFusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(context);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap){

        map = googleMap;
        Log.d(TAG, "onMapReady() called with: googleMap = [" + googleMap + "]");

        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            map.setMyLocationEnabled(true);
        }

        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerMapOnLocation();

                Log.d(TAG, "onClick() called with: v = [" + v + "]");
            }
        });
        centerMapOnLocation();


        /*
        listener.onReady();
        updateTips();
        setStateStandby();
        setupListeners();

        // States being initialized.
        stateStandby = new StateStandby(this, sateStandbyListener);
        stateSelectLocation = new StateSelectLocation(this, stateSelectLocListener); // will be overWritten When doigb*/
    }

    //Public Calls
    public void setStateStandby(){
        state = stateStandby;
        state.updateTips();
    }
    public void setStateLocationSelect(View.OnClickListener onClickerListener){
        acceptBtn.setOnClickListener(onClickerListener);
        state = stateSelectLocation;
    }

    // private Calls
    private void centerMapOnLocation(){
        try {
            Task locationResult = mFusedLocationProviderClient.getLastLocation();
            locationResult.addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location mLastKnownLocation = (Location) task.getResult();

                    } else {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
                        map.getUiSettings().setMyLocationButtonEnabled(false);
                    }
                }
            });

        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    private void updateTips(){
        listener.onUpdate();
        state.updateTips();
    }



    /*
    private void setupListeners(){
        stateSelectLocListener = new IMapStateListener() {
            @Override
            public void onClickMarker() {

            }

            @Override
            public void onDone() {

            }
        };
        sateStandbyListener = new IMapStateListener() {
            @Override
            public void onClickMarker() {

            }

            @Override
            public void onDone() {

            }
        };
    }
    private LatLng getDeviceLocation(){
        return null;
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        //https://stackoverflow.com/questions/14851641/change-marker-size-in-google-maps-api-v2

        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier(iconName, "drawable", context.getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
    public void setListOfTipDto(List<ITipDTO> tips){
        listOfTipDto = tips;
    }

   public void zoomCamara(int zoom){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mMap.getCameraPosition().target)
                .zoom(zoom).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }*/

}
