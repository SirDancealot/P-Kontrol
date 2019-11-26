package com.example.p_kontrol.UI.Contexts;

import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateStandby implements IState {

    // Constructer retrieved Vars
    MapContext context;
    IMapStateListener listener = null;

    // Context retrieved Vars
    GoogleMap map;

    public StateStandby(MapContext context, IMapStateListener listener) {
        this.context = context;
        this.listener = listener;
        map = context.getMap();

        setupListeners();
    }
    private void setupListeners(){
        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Log.d("onMapClick", "onMapClick() called with: latLng = [" + latLng + "]");
            }
        });
    }

    @Override
    public void setStateInteractionListener(IMapStateListener listener) {

    }

}
