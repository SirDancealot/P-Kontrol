package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.content.res.Resources;

import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface IMapFragment {


    void centerMap();
    void setStateStandby();
    void setStateSelectLocation();
    IState getCurrentState();
    void updatePermissions();

}
