package com.example.p_kontrol.UI.Map;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;
/**
 * @responsibilty Select Location is a state where you can place a location on the map, and give visual feedback
 *
 * */
public class StateSelectLocation extends State {

    String TAG = "State Select Loaction ";

    /** @responsibilty Select Location is a state where you can place a location on the map, and give visual feedback
     *
     * StateSelectLocation Extends State
     * @see {@link com.example.p_kontrol.UI.Map.State}
     *
     * */
    public StateSelectLocation(MapFragment parent) {
        super(parent);
        map.clear();

        // Setting marker to location in the center of the map.
        LatLng cameraLocation = map.getCameraPosition().target;
        map.addMarker(new MarkerOptions().position(cameraLocation));

        // initiating the new tip object in the ViewModel.
        ITipDTO dto = viewModel.getTipCreateObject().getValue();
        dto.setL(new GeoPoint(cameraLocation.latitude, cameraLocation.longitude));
        viewModel.setTipCreateObject(dto);
    }

    /**
     * Overides the current listeners such that when you click on the map you set a marker,
     * click again and it removes the previous marker, and adds a new.
     *
     * and no Marker on click listener.
     * */
    @Override
    public void setListeners(){

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();

                ITipDTO dto = viewModel.getTipCreateObject().getValue();
                dto.setL(new GeoPoint(latLng.latitude, latLng.longitude));
                viewModel.setTipCreateObject(dto);

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

    /**
     * empty method.
            * */
    @Override
    public void updateMap(List<ITipDTO> list) {
    }

}
