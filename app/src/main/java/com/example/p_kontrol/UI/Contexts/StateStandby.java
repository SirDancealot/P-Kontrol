package com.example.p_kontrol.UI.Contexts;

import com.google.android.gms.maps.model.LatLng;

public class StateStandby implements IState {

    MapContext context;
    IMapStateListener listener = null;

    public StateStandby(MapContext context, IMapStateListener listener) {
        this.context = context;
        this.listener = listener;
    }

    @Override
    public LatLng getLocation() {
        return null;
    }

    @Override
    public void centerMapOnLocation() {

    }

    @Override
    public void setupMap() {

    }

    @Override
    public void setStateInteractionListener(IMapStateListener listener) {
        this.listener = listener;
    }

    @Override
    public void updateTips() {

    }

    public void zoomCamara(int zoom){
        /*
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(mMap.getCameraPosition().target)
                .zoom(zoom).build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //mMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));*/
    }


}
