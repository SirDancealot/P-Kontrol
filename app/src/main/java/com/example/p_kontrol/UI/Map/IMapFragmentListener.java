package com.example.p_kontrol.UI.Map;

import com.google.android.gms.maps.model.LatLng;
/** @responsibilty to tell the IMapFragment what to do when a tip is clicked on. */
public interface IMapFragmentListener {

    /**
     * a Listener call to what happens when a user clicks on a tip on the map.
     * */
    void onTipClick(String index);
}
