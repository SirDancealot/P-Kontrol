package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.content.res.Resources;

import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface IMapFragment {


    void centerMap();
    void setStateParking();
    void setStateStandby();
    void setStateFreePark();
    void setStateSelectLocation();
    IState getCurrentState();
    void updatePermissions();

    boolean isFreeParkEnabled();
    boolean isParkingEnabled();
}
