package com.example.p_kontrol.UI.Contexts;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateSelectLocation extends State {

    //Constructor Given
    MapContext context;

    //Context Retrieved
    GoogleMap map;
    Button acceptBtn;
    LatLng currentMarker = null;

    public StateSelectLocation(MapContext context) {
        super(context);
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
                    Log.d("Accept", "onClick() called with: v = [" + v + "]");
                }
            }
        });

    }


}
