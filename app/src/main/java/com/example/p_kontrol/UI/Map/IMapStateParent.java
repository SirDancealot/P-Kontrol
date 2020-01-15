package com.example.p_kontrol.UI.Map;

import android.app.Activity;

import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;

public interface IMapStateParent {

    GoogleMap getMap();
    Activity getContext();
    IMapFragmentListener getFragmentListener();
    FusedLocationProviderClient getFusedLocationProviderClient();
    LiveDataViewModel getViewModel();

}
