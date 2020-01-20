package com.example.p_kontrol.UI.Map;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.p_kontrol.DataTypes.TipDTO;
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

    /** Select Location is a state where you can place a location on the map, in addition it automaticly sets your currentlocation
     * as the standard placement of the selected location.
     *
     * StateSelectLocation Extends State
     * @see {@link com.example.p_kontrol.UI.Map.State}
     *
     * and there fore implements
     * @see {@link com.example.p_kontrol.UI.Map.IState}
     * */
    public StateSelectLocation(MapFragment parent) {
        super(parent);
        map.clear();

        map.addMarker(new MarkerOptions().position(map.getCameraPosition().target));
        TipDTO dto = viewModel.getTipCreateObject().getValue();
        dto.setL(new GeoPoint(parent.DEFAULT_LOCATION.latitude, parent.DEFAULT_LOCATION.longitude));
        viewModel.setTipCreateObject(dto);

    }

    // Listeners
    @Override
    public void setListeners(){

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();

                TipDTO dto = viewModel.getTipCreateObject().getValue();
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
    @Override
    public void updateMap(List<TipDTO> list) {

    }
    @Override
    public void centerMethod(){
        try {
            Task locationResult = parent.getFusedLocationProviderClient().getLastLocation();
            locationResult.addOnCompleteListener( parent.getContext() , new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if ( task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location location = (Location) task.getResult();
                        LatLng result = new LatLng(location.getLatitude(),location.getLongitude());
                        viewModel.getCurrentLocation().setValue(result);
                        map.addMarker(new MarkerOptions().position(viewModel.getCurrentLocation().getValue()));
                        animeCamara(result);

                    } else {
                        // if location cannot be found.
                        viewModel.getCurrentLocation().setValue( parent.DEFAULT_LOCATION );
                        animeCamara( parent.DEFAULT_LOCATION );
                    }
                }
            });

        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }

}
