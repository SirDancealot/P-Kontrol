package com.example.p_kontrol.UI.Map;

import androidx.fragment.app.FragmentActivity;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.firestore.GeoPoint;

import java.util.List;

public class StateSelectLocation extends State {

    String TAG = "State Select Loaction ";
    IMapSelectedLocationListener listenerDone;
    LatLng currentMarkerLocation = null;

    public StateSelectLocation(MapFragment parent, FragmentActivity lifeOwner) {
        super(parent, lifeOwner);
        zoomIn();
        map.clear();

        currentMarkerLocation = viewModel.getCurrentLocation().getValue();
        //currentMarkerLocation = parent.getLocation();

        map.addMarker(new MarkerOptions().position(currentMarkerLocation));

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
    public void centerMethod(){
        this.centerMethod();
    }
    @Override
    public void updateMap(List<ATipDTO> list ) {

    }

}
