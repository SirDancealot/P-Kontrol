package com.example.p_kontrol.UI.Contexts;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateSelectLocation extends State {

    IMapSelectedLocationListener listener;

    Button acceptBtn;
    LatLng currentMarker = null;

    public StateSelectLocation(MapContext context) {
        super(context);
        acceptBtn = context.getAcceptBtn();
        zoomIn();
        map.clear();
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
                    map.clear();
                    zoomOut();
                    listener.onSelectedLocation(currentMarker);
                    Log.d("Accept", "onClick() called with: v = [" + v + "]");
                }
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
    public void setDoneListner(Object listener){
        this.listener = (IMapSelectedLocationListener) listener;
    }

}
