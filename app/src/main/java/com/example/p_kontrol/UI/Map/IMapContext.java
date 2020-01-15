package com.example.p_kontrol.UI.Map;

import android.content.res.Resources;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IMapContext {


    void centerMap();
    void setStateStandby();
    void setStateSelectLocation();
    IState getCurrentState();
    void updatePermissions();
    FusedLocationProviderClient getFusedLocationProviderClient();
    Resources getResources();

    LatLng getLocation();
    LatLng getSelectedLocation();
    void setSelectedLocation(LatLng selectedLocation);
    void setLocaton(LatLng locaton);
    GoogleMap getMap();
    IMapContextListener getContextListener();
    public String getPackageName();
}
