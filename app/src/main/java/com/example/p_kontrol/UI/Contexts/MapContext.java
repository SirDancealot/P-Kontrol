package com.example.p_kontrol.UI.Contexts;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Activities.ActivityMapView;
import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class MapContext implements OnMapReadyCallback {

    //Defaults
    private int DEFAULT_ZOOM = 17;

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

    //UserData
    private LatLng userlocation;
    private LatLng currentMarkerLoc;

    private IMapContextListener listener;

    public MapContext(SupportMapFragment mapFragment, Activity context, GoogleMap map, Button centerBtn, IMapContextListener listener) {

        this.mapFragment = mapFragment;
        this.context = context;
        this.map = map;
        this.centerBtn = centerBtn;
        this.listener = listener;

        mFusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(context);

        mapFragment.getMapAsync(this);
        setState(state);

    }
    @Override
    public void onMapReady(GoogleMap googleMap) {

        map = googleMap;
        centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                centerMapOnLocation();
            }
        });

        if ( ContextCompat.checkSelfPermission( context, Manifest.permission.ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED ) {
            map.setMyLocationEnabled(true);
        }
        centerMapOnLocation();
        listener.onReady();
        updateTips();
    }

    private void updateTips(){
        listener.onUpdate();
        state.updateTips();
    }
    public void setStateStandby(){
        state = standbyState;
        state.updateTips();
    }
    public void setStateLocationSelect(){
        state = new selectionState();
    }

    private void centerMapOnLocation(){}

    private LatLng getDeviceLocation(){

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
        //https://stackoverflow.com/questions/14851641/change-marker-size-in-google-maps-api-v2
        Bitmap imageBitmap = BitmapFactory.decodeResource(getResources(),getResources().getIdentifier(iconName, "drawable", getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

    public void setListOfTipDto(List<ITipDTO> tips){
        listOfTipDto = tips;
    }
}
