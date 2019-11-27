package com.example.p_kontrol.UI.Contexts;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IMapContextListener {

    // on something call backs
    void onReady();
    void onChangeState();
    void onSelectedLocation();
    void onUpdate();
    void onAcceptButton(LatLng location);

    // commands to execute.
    void showTipsAtIndex(int index);
}
