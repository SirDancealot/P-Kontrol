package com.example.p_kontrol.UI.Map;

import android.util.Log;
import android.view.View;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class StateStandby extends State {


    public StateStandby(MapContext context) {
        super(context);
        map.clear();
    }

    @Override
    public void updateMap(List<ATipDTO> list ) {
        if(list != null) {
            int i = 0;
            for (ATipDTO tip : list) {
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("map_tip_pin_icon", 69, 100)));
                map.addMarker(markerOptions.position(new LatLng(tip.getL().getLatitude(), tip.getL().getLongitude())).title(String.valueOf(i++)));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        listener.onTipClick(Integer.parseInt(marker.getTitle()));
                        Log.i(TAG, "updateMap: PUT A PIN IN IT!!!!! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ");
                        return true;

                    }
                });
            }
        }
    }
}
