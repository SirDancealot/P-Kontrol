package com.example.p_kontrol.UI.Map;

public interface IState {
    void setDoneListner(Object listenerDone);
    void centerMapOnLocation(MapContext context);
    void updateMap();
}
