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
    public void setStateInteractionListener(IMapStateListener listener) {

    }
}
