package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.location.Location;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Map.IMapFragment;
import com.example.p_kontrol.UI.Map.IMapFragmentListener;
import com.example.p_kontrol.UI.Map.IState;
import com.example.p_kontrol.UI.Map.StateStandby;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.tasks.OnSuccessListener;


public class MapFragment extends Fragment implements OnMapReadyCallback , IMapFragment {

    // Defaults
    final int   DEFAULT_ZOOM_CLOSEUP = 17;
    final int   DEFAULT_ZOOM = 15;
    final LatLng DEFAULT_LOCATION = new LatLng(55.731318, 12.396567);

    // Android
    String TAG = this.getClass().getName();

    // Google Map Thingies
    private FusedLocationProviderClient fusedLocationProviderClient;
    SupportMapFragment mapFragment;
    GoogleMap map;

    // My Data
    Activity context;
    IState currentState;
    IMapFragmentListener listener;
    LiveDataViewModel viewModel;

    boolean isFreeParkEnabled = false;

    public MapFragment(Activity context, IMapFragmentListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this.getActivity()).get(LiveDataViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_map, container, false);
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mainMenu_mapfragment);
        if(mapFragment == null){
            FragmentManager fm = getFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            mapFragment = SupportMapFragment.newInstance();
            ft.replace(R.id.mainMenu_mapfragment, mapFragment).commit();
        }
        viewModel.getCurrentLocation().setValue(new LatLng(DEFAULT_LOCATION.latitude, DEFAULT_LOCATION.longitude));
        mapFragment.getMapAsync(this);
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap){
        map = googleMap;

        //sets the position of the ui elements of the map, a bit lower, as to not overlap with the topbar
        // automaticly adjusts the centering to the mapped area shown with the topbar
        map.setPadding(0,170,0,0);



        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        getPermission();
        setStateStandby();
    }



    // IMapFragment
    @Override
    public void setStateStandby(){
        currentState = new StateStandby(this );
        isFreeParkEnabled = false;
    }
    @Override
    public void setStateFreePark(){
        currentState = new StateFreePark(this );
        isFreeParkEnabled = true;
    }
    @Override
    public void setStateSelectLocation() {
        currentState = new StateSelectLocation(this );
        isFreeParkEnabled = false;
    }
    @Override
    public void centerMap() {
        currentState.centerMethod();
    }
    @Override
    public IState getCurrentState() {
        return currentState;
    }
    @Override
    public void updatePermissions() {
        map.setMyLocationEnabled(true);
        currentState.centerMethod();
        Log.d(TAG, "onRequestPermissionsResult: true");
        fusedLocationProviderClient.getLastLocation()
                .addOnSuccessListener(context, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            viewModel.getCurrentLocation().setValue(new LatLng(location.getLatitude(),location.getLongitude()));
                        }
                    }
                });
    }
    @Override
    public boolean isFreeParkEnabled() {
        return isFreeParkEnabled;
    }

    // States Need these
    @NonNull
    public GoogleMap getMap() {
        return map;
    }
    @NonNull
    public Activity getContext(){
        return context;
    }
    @NonNull
    public IMapFragmentListener getFragmentListener() {
        return listener;
    }
    @NonNull
    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return fusedLocationProviderClient;
    }
    @NonNull
    public LiveDataViewModel getViewModel() {
        return viewModel;
    }

    // Internal methods
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
            fusedLocationProviderClient.getLastLocation()
                    .addOnSuccessListener(new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                            viewModel.getCurrentLocation().setValue(new LatLng(location.getLatitude(), location.getLongitude()));
                            /*
                            if (location != null) {
                                mLastKnownLocation = new LatLng(location.getLatitude(),location.getLongitude());
                                Log.d(TAG, "onSuccess: fandt location");
                                listener.onReady();
                            }*/
                        }
                    });
        } else {
            ActivityCompat.requestPermissions((context),
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    1);
            Log.d(TAG, "getPermission: false");
        }
    }
}