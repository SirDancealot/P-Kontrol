package com.example.p_kontrol.UI.Map;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IState {
    void setDoneListner(IMapContextListener listenerDone);
    void centerMethod();
    void updateLocation();
    void updateMap(List<ATipDTO> list);
}
