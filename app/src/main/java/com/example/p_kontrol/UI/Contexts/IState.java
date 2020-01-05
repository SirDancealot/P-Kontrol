package com.example.p_kontrol.UI.Contexts;

import com.google.android.gms.maps.model.LatLng;

public interface IState {
    void setDoneListner(Object listenerDone);
    void centerMapOnLocation(MapContext context);
    void updateMap();
}
