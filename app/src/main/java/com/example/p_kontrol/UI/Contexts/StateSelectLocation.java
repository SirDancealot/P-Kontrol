package com.example.p_kontrol.UI.Contexts;

import com.google.android.gms.maps.model.LatLng;

public class StateSelectLocation implements IState {

    MapContext context;
    IMapStateListener listener = null;
    public StateSelectLocation(MapContext context, IMapStateListener listener) {
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

    }

    @Override
    public void updateTips() {

    }
}
