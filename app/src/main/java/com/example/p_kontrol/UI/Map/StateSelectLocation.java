package com.example.p_kontrol.UI.Map;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class StateSelectLocation extends State {

    String TAG = "State Select Loaction ";
    IMapSelectedLocationListener listenerDone;
    LatLng currentMarkerLocation = null;

    public StateSelectLocation(MapFragment2 parent) {
        super(parent);
        zoomIn();
        map.clear();

        currentMarkerLocation = viewModel.getCurrentLocation().getValue();

        map.addMarker(new MarkerOptions().position(viewModel.getCurrentLocation().getValue()));

    }

    // Listeners
    @Override
    public void setListeners(){

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();

                ATipDTO dto = viewModel.getMutableTipCreateObject().getValue();
                dto.setL(new GeoPoint(latLng.latitude, latLng.longitude));
                viewModel.getMutableTipCreateObject().setValue(dto);

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
    public void updateMap(List<ATipDTO> list ) {

    }

}
