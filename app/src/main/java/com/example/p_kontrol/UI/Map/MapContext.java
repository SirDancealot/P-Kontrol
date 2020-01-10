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

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Executor;

public class MapContext extends FragmentActivity implements OnMapReadyCallback {

    String TAG = "MapContext";

    //Defaults
    private int DEFAULT_ZOOM = 17;
    private int DEFAULT = 15;
    private final LatLng DEFAULT_LOCATION = new LatLng(55.676098, 12.56833);

    //Map Functionality NECESARY VARIABLES
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private SupportMapFragment mapFragment;
    private LatLng mLastKnownLocation = DEFAULT_LOCATION;

    private FusedLocationProviderClient mFusedLocationProviderClient;
    private MainMenuActivity activity;
    private MapContext thisContext = this;

    //Data
    private List<ITipDTO> listOfTipDto;
    private IState currentState;
    private Location userlocation;
    private LatLng currentMarkerLoc;

    // Views
    private GoogleMap map;
    private Button centerBtn;
    private Button cancelBtn;
    private Button acceptBtn;
    View btnContainerAceptCancel;

    //States
    private IState stateStandby, stateSelectLocation;

    //Listeners
    private IMapContextListener listener;

    // -- METHODS --  --  --  --  --  --  --  --  --  --  --  --  --
    public MapContext(SupportMapFragment mapFragment, MainMenuActivity activity, Button centerBtn, Button cancelBtn, Button acceptBtn, View btnContainerAceptCancel, IMapContextListener listener) {

        //Android Stuffs
        this.mapFragment = mapFragment;
        this.activity = activity;
        this.listener = listener;
        this.btnContainerAceptCancel = btnContainerAceptCancel;

        // interaction Buttons
        this.centerBtn = centerBtn;
        this.cancelBtn = cancelBtn;
        this.acceptBtn = acceptBtn;

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

        if (map.isMyLocationEnabled()) {
            Log.d(TAG, "onMapReady: location true");
            currentState.centerMapOnLocation();
        } else {
            Log.d(TAG, "onMapReady: location false");
        }


        Log.d(TAG, "getLanLng: returner");


    }
    // important Map Methods.
    public Resources getResources() {
        return activity.getResources();
    }
    private void styleMapCall() {
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
    public void updateMap() {
        listOfTipDto = activity.getDTOList();
        currentState.updateMap();
    }
    private void getPermission() {

        if (ContextCompat.checkSelfPermission(activity.getApplicationContext(),
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
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
            Log.d(TAG, "getPermission: false");
        }


    }
    public void updatePermissions(){
        map.setMyLocationEnabled(true);
        currentState.centerMapOnLocation();
        Log.d(TAG, "onRequestPermissionsResult: true");
        mFusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(activity, new OnSuccessListener<Location>() {
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

    //Public Calls
    public void setStateStandby() {
        currentState = new StateStandby(this);
    }
    public void setStateSelectLocation(IMapSelectedLocationListener selectListener) {

        currentState = new StateSelectLocation(this);
        currentState.setDoneListner(selectListener);

    }

    // Get and Sets.


    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
    }

    public int getDEFAULT_ZOOM() {
        return DEFAULT_ZOOM;
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

    public LatLng getDEFAULT_LOCATION() {
        return DEFAULT_LOCATION;
    }

    public static int getPermissionsRequestAccessFineLocation() {
        return PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
    }

    public SupportMapFragment getMapFragment() {
        return mapFragment;
    }

    public void setMapFragment(SupportMapFragment mapFragment) {
        this.mapFragment = mapFragment;
    }

    public LatLng getmLastKnownLocation() {
        return mLastKnownLocation;
    }

    public void setmLastKnownLocation(LatLng mLastKnownLocation) {
        this.mLastKnownLocation = mLastKnownLocation;
    }

    public FusedLocationProviderClient getmFusedLocationProviderClient() {
        return mFusedLocationProviderClient;
    }

    public void setmFusedLocationProviderClient(FusedLocationProviderClient mFusedLocationProviderClient) {
        this.mFusedLocationProviderClient = mFusedLocationProviderClient;
    }

    public MainMenuActivity getActivity() {
        return activity;
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

    public List<ITipDTO> getListOfTipDto() {
        return listOfTipDto;
    }

    public void setListOfTipDto(List<ITipDTO> listOfTipDto) {
        this.listOfTipDto = listOfTipDto;
    }

    public IState getCurrentState() {
        return currentState;
    }

    public void setCurrentState(IState currentState) {
        this.currentState = currentState;
    }

    public Location getUserlocation() {
        return userlocation;
    }

    public void setUserlocation(Location userlocation) {
        this.userlocation = userlocation;
    }

    public LatLng getCurrentMarkerLoc() {
        return currentMarkerLoc;
    }

    public void setCurrentMarkerLoc(LatLng currentMarkerLoc) {
        this.currentMarkerLoc = currentMarkerLoc;
    }

    public GoogleMap getMap() {
        return map;
    }

    public void setMap(GoogleMap map) {
        this.map = map;
    }

    public Button getCenterBtn() {
        return centerBtn;
    }

    public void setCenterBtn(Button centerBtn) {
        this.centerBtn = centerBtn;
    }

    public Button getCancelBtn() {
        return cancelBtn;
    }

    public void setCancelBtn(Button cancelBtn) {
        this.cancelBtn = cancelBtn;
    }

    public Button getAcceptBtn() {
        return acceptBtn;
    }

    public void setAcceptBtn(Button acceptBtn) {
        this.acceptBtn = acceptBtn;
    }

    public View getBtnContainerAceptCancel() {
        return btnContainerAceptCancel;
    }

    public void setBtnContainerAceptCancel(View btnContainerAceptCancel) {
        this.btnContainerAceptCancel = btnContainerAceptCancel;
    }

    public IState getStateStandby() {
        return stateStandby;
    }

    public void setStateStandby(IState stateStandby) {
        this.stateStandby = stateStandby;
    }

    public IState getStateSelectLocation() {
        return stateSelectLocation;
    }

    public void setStateSelectLocation(IState stateSelectLocation) {
        this.stateSelectLocation = stateSelectLocation;
    }

    public IMapContextListener getListener() {
        return listener;
    }

    public void setListener(IMapContextListener listener) {
        this.listener = listener;
    }
}