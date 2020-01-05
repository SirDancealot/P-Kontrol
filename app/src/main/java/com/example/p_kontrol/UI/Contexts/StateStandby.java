package com.example.p_kontrol.UI.Contexts;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.facebook.appevents.suggestedevents.ViewOnClickListener;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateStandby extends State {

    public StateStandby(MapContext context) {
        super(context);
        acceptBtn.setVisibility(View.GONE);
        cancelBtn.setVisibility(View.GONE);
    }

    @Override
    public void centerMethod(){
        centerMapOnLocation(context);
    }

}
