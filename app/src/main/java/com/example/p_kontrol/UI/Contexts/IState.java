package com.example.p_kontrol.UI.Contexts;

import com.google.android.gms.maps.model.LatLng;

public interface IState {

    LatLng getLocation();
    void centerMapOnLocation();
    void setupMap();
    void setStateInteractionListener(IMapInteractionListener listener);

}
