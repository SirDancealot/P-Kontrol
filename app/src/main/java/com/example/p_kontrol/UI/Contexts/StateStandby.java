package com.example.p_kontrol.UI.Contexts;

import android.view.View;
import android.widget.Toast;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.Activities.ActivityMapView;
import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class StateStandby implements IState {

    MapContext context;
    IMapInteractionListener listener = null;

    public StateStandby(MapContext context) {
        this.context = context;
    }

    @Override
    public LatLng getLocation() {
        return null;
    }

    @Override
    public void setStateInteractionListener(IMapInteractionListener listener) {
        this.listener = listener;
    }

    public void zoomCamara(int zoom){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mMap.getCameraPosition().target)
                .zoom(zoom).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


}
