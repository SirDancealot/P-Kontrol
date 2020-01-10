package com.example.p_kontrol.UI.Map;

import android.location.Location;
import android.util.Log;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class StateSelectLocation extends State {

    String TAG = "State Select Loaction ";
    IMapSelectedLocationListener listenerDone;
    LatLng currentMarkerLocation = null;

    public StateSelectLocation(MapContext context) {
        super(context);
        zoomIn();
        map.clear();
        currentMarkerLocation = context.getLocation();
        map.addMarker(new MarkerOptions().position(currentMarkerLocation));

    }


    // Listeners
    @Override
    public void setListeners(){

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                currentMarkerLocation = latLng;
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });

    }

    @Override
    public void centerMethod(){
        this.centerMethod();
    }

    @Override
    public void updateMap(List<ITipDTO> list ) {

    }
    @Override
    public void updateLocation() {
        context.setSelectedLocation(currentMarkerLocation);
    }


}
