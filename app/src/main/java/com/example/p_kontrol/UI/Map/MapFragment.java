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
    final int   DEFAULT_ZOOM = 15;
    final LatLng DEFAULT_LOCATION = new LatLng(55.731318, 12.396567);

    // Android
    String TAG = this.getClass().getName();

    // Google Map Thingies
    private FusedLocationProviderClient fusedLocationProviderClient;
    private SupportMapFragment mapFragment;
    private GoogleMap map;

    // My Data
    private Activity context;
    private IState currentState;
    private IMapFragmentListener listener;
    private LiveDataViewModel viewModel;

    private boolean isFreeParkEnabled = false;
    private boolean isParkingEnabled = false;

    /**
     *  MapFragment is a fragment, that ought be treated as a single object, but is implemented as a StatePatten.
     *  @param context  the Parent Activity, such that the reference can be passed on to the states. is needed due to certain calls needs special android acces
     *  @param listener contains the onTipClick method, given from the Activity.
     *  @see {@link com.example.p_kontrol.UI.Map.IMapFragmentListener}
     *
     *  implements
     *  @see {@link com.example.p_kontrol.UI.Map.IMapFragment}
     *  OnMapReadyCallback.
     *
     *  Relevant files
     *  @see {@link com.example.p_kontrol.UI.Map.IState}
     *  @see {@link com.example.p_kontrol.UI.Map.State}
     *  @see {@link com.example.p_kontrol.UI.Map.StateStandby}
     *  @see {@link com.example.p_kontrol.UI.Map.StateFreePark}
     *  @see {@link com.example.p_kontrol.UI.Map.StateSelectLocation}
     *  @see {@link com.example.p_kontrol.UI.Map.StateParking}
     *
     * */
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

        styleMapCall();
        map.getUiSettings().setMyLocationButtonEnabled(false);


        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);

        getPermission();
        setStateStandby();
        currentState.centerMethod(); // todo Hans!! har jeg gjort noget forkert? her?
    }



    // interface IMapFragment
        // States
    /**
     * @inheritDoc
     * */
    @Override
    public void setStateParking(){
        currentState = new StateParking(this );
        isFreeParkEnabled = false;
        isParkingEnabled = true;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void setStateStandby(){
        currentState = new StateStandby(this );
        isFreeParkEnabled = false;
        isParkingEnabled = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void setStateFreePark(){
        currentState = new StateFreePark(this );
        isFreeParkEnabled = true;
        isParkingEnabled = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void setStateSelectLocation() {
        currentState = new StateSelectLocation(this );
        isFreeParkEnabled = false;
        isParkingEnabled = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public IState getCurrentState() {
        return currentState;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public boolean isFreeParkEnabled() {
        return isFreeParkEnabled;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public boolean isParkingEnabled(){return isParkingEnabled; }

        // not States
    /**
     * @inheritDoc
     * */
    @Override
    public void centerMap() {
        currentState.centerMethod();
    }

    /**
     * @inheritDoc
     * */
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


    // States Need these
    /**
     * @return GoogleMap, is needed to hand out the map, when needed. is a way to keep the State Constructor simple.
     * */
    public GoogleMap getMap() {
        return map;
    }

    /**
     * @return Owning Activity, is needed to hand out the map, when needed. is a way to keep the State Constructor simple.
     * */
    public Activity getContext(){
        return context;
    }

    /**
     * @return IMapFragmentListener, is needed to hand out the map, when needed. is a way to keep the State Constructor simple.
     *
     * IMapFragmentListener has one method, onTipClick();
     * @see {@link com.example.p_kontrol.UI.Map.IMapFragmentListener}
     * */
    public IMapFragmentListener getFragmentListener() {
        return listener;
    }

    /**
     * FusedLocationProviderClient is a GoogleMap specifik,
     * @return FusedLocationProviderClient, is needed to hand out the map, when needed. is a way to keep the State Constructor simple.
     * */
    public FusedLocationProviderClient getFusedLocationProviderClient() {
        return fusedLocationProviderClient;
    }

    /**
     * is used to drag out a style json file from the Storage to style the map with.
     * */
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
    /**
     * is a way to pass permission to GoogleMap Object .
     * */
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
