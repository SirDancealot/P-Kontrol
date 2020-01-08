package com.example.p_kontrol.UI.Contexts;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public interface IMapSelectedLocationListener {
    void onSelectedLocation(LatLng location);
    void onCancelSelection();
}
