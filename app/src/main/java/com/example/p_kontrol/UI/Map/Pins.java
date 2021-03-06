package com.example.p_kontrol.UI.Map;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.model.MarkerOptions;

/**
 * * @responsibilty keep track of data regarding the Marker icons, such that they can be easely managed
 * */
public enum Pins{
    free("map_tip_pin_regular", 354, 512),
    paid("map_tip_pin_paid", 368, 527),
    alarm("map_tip_pin_alarm", 368, 527),
    pVagt("map_pvagt_pin_alarm", 366, 525),
    pVagtOld("map_pvagt_pin_alarmold", 366, 525),
    parkingSpot("map_parkingspot", 382, 230);

    private final String name;
    private final int dimX;
    private final int dimY;
    private MarkerOptions marker = null;

    Pins(String name, int dimX, int dimY) {
        this.name = name;
        this.dimX = dimX;
        this.dimY = dimY;
    }

    public String getName() {
        return name;
    }

    public int getDimX() {
        return dimX;
    }

    public int getDimY() {
        return dimY;
    }

    public MarkerOptions getMarker() {
        return marker;
    }

    public void initMarkers(Fragment frag) {
        LiveDataViewModel vm = ViewModelProviders.of(frag).get(LiveDataViewModel.class);
        marker = vm.getPin(name, frag.getContext(), dimX, dimY);
    }
}



