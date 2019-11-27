package com.example.p_kontrol.UI.Contexts;

import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateStandby extends State {

    // Constructer retrieved Vars
    MapContext context;
    IMapContextListener listener = null;

    // Context retrieved Vars
    GoogleMap map;

    public StateStandby(MapContext context) {
        super(context);
    }

    private void setupListeners(){
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("onMapClick", "onMapClick() called with: latLng = [" + latLng + "]");
            }
        });
    }
}
