package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.content.res.Resources;

import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;

public interface IMapFragment {

    // Methods
    /** method for centering the map, calls the IState centerMethod()
     * @see {@link com.example.p_kontrol.UI.Map.IState}
     * */
    void centerMap();
    /** required to update permissions on the GoogleMap Object */
    void updatePermissions();

    // States
    /** changes the current state into the Parking State
     * @see {@link com.example.p_kontrol.UI.Map.StateParking}
     * */
    void setStateParking();

    /** changes the current state into the StandBy State
     * @see {@link com.example.p_kontrol.UI.Map.StateStandby}
     * */
    void setStateStandby();

    /** changes the current state into the Free Parking State
     *  Free Park state is a Filter that only shows the tips of the Free Category
     * @see {@link com.example.p_kontrol.UI.Map.StateFreePark}
     * */
    void setStateFreePark();

    /** changes the current state into the Select Location State
     *  Free Park state is a Filter that only shows the tips of the Free Category
     * @see {@link com.example.p_kontrol.UI.Map.StateSelectLocation}
     * */
    void setStateSelectLocation();

    /** returns the Current State of the State Pattern
     * interface
     * @return {@link com.example.p_kontrol.UI.Map.IState}
     *
     * implementation
     * @see {@link com.example.p_kontrol.UI.Map.IState}
     * @see {@link com.example.p_kontrol.UI.Map.StateSelectLocation}
     * @see {@link com.example.p_kontrol.UI.Map.StateFreePark}
     * @see {@link com.example.p_kontrol.UI.Map.StateStandby}
     * @see {@link com.example.p_kontrol.UI.Map.StateParking}
     *
     * abstract implementation
     * @see {@link com.example.p_kontrol.UI.Map.State}
     * */
    IState getCurrentState();

    /**
     * @return true if Enabled else false*/
    boolean isFreeParkEnabled();

    /**
     * @return true if Enabled else false*/
    boolean isParkingEnabled();


}
