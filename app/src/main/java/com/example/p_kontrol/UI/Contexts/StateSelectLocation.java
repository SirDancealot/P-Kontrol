package com.example.p_kontrol.UI.Contexts;

import android.view.View;
import android.widget.Button;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateSelectLocation implements IState {

    //Constructor Given
    MapContext context;
    IMapStateListener listener = null;

    //Context Retrieved
    GoogleMap map;
    Button acceptBtn;
    LatLng currentMarker = null;

    public StateSelectLocation(MapContext context, IMapStateListener listener) {
        this.context = context;
        this.listener = listener;
        map = context.getMap();
        acceptBtn = context.getAcceptBtn();

        setupMapListener();
    }

    private void setupMapListener(){

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                currentMarker = latLng;
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });

        acceptBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener != null && currentMarker != null ){
                    listener.onAcceptButton(currentMarker);
                }
            }
        });

    }

    @Override
    public void setStateInteractionListener(IMapStateListener listener) {
        this.listener = listener;
    }


}
