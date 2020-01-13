package com.example.p_kontrol.UI.Map;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.List;

public class StateParking extends State {

    LatLng currentMarkerLocation = null;

    public StateParking(MapContext context) {
        super(context);
        zoomIn();
        map.clear();
        currentMarkerLocation = context.getLocation();
        map.addMarker(new MarkerOptions().position(currentMarkerLocation));

    }


    @Override
    public void updateMap(List<ATipDTO> li) {


        //add observe live data ??

        //map.addMarker(new MarkerOptions().position(latLng));

    }
}





}
