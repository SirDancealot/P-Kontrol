package com.example.p_kontrol.UI.Map;

import com.google.android.gms.maps.model.LatLng;

public interface IMapSelectedLocationListener {
    void onSelectedLocation(LatLng location);
    void onCancelSelection();
}
