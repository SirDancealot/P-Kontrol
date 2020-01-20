package com.example.p_kontrol.UI.Map;

import com.google.android.gms.maps.model.LatLng;

public interface IMapFragmentListener {

    /**
     * a Listener call to what happens when a user clicks on a tip on the map.
     * */
    void onTipClick(int index);
}
