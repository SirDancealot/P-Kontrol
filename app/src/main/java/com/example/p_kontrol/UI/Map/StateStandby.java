package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class StateStandby extends State {


    public StateStandby(MapFragment2 parent) {
        super(parent);
        map.clear();
        centerMethod();
    }

    @Override
    public void updateMap(List<ATipDTO> list ) {

        int i = 0;
        for(ATipDTO tip: list){
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("map_tip_pin_icon",69,100)));
            map.addMarker(markerOptions.position(new LatLng(tip.getL().getLatitude(), tip.getL().getLongitude())).title(String.valueOf(i++)));
            map.setOnMarkerClickListener(marker -> {
                listener.onTipClick(Integer.parseInt(marker.getTitle()));
                Log.i(TAG, "updateMap: PUT A PIN IN IT!!!!! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ");
                return true;

            });
        }
    }

}
