package com.example.p_kontrol.UI.Contexts;

import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class State implements IState  {

    @Override
    public void setStateInteractionListener(IMapStateListener listener) {

    }

    public void centerMapOnLocation(MapContext context){
        context.centerMapOnLocation();
    }
}
