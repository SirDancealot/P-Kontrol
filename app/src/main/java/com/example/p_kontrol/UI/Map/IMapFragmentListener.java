package com.example.p_kontrol.UI.Map;

import com.google.android.gms.maps.model.LatLng;

public interface IMapFragmentListener {

    // on something call backs
    void onReady();
    void onChangeState();
    void onSelectedLocation();
    void onUpdate();
    void onTipClick(int index);
}
