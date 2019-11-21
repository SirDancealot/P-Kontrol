package com.example.p_kontrol.UI.Fragments;

import com.example.p_kontrol.DataBase.dto.TipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IMapFragmet {

    // Update Map
    void updateMapTips(List<TipDTO> tips);

    // Write A tip.
    void setMarker();
    void clearMarker();
    LatLng getMarkerLokation();
    LatLng getCurrentLokation();

    // se written tips.
    void moveCamera(LatLng geo, int zoom);
    void resetCamera();

}
