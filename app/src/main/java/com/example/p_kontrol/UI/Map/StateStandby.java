package com.example.p_kontrol.UI.Map;

import android.util.Log;
import android.view.View;

import com.example.p_kontrol.DataTypes.ATipDTO;
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
        String iconName = null;

        if(list != null) {
            int i = 0;
            for (ATipDTO tip : list) {
                // todo ret navne
                if(tip.getType() != null){
                    if(tip.getType() == "normal"){
                        iconName = "ic_tip_like";
                    } else if(tip.getType() == "free"){
                        iconName = "ic_tip_like_on";
                    } else if(tip.getType() == "alert") {
                        iconName = "ic_tip_dislike_on";
                    }
                } else {
                    iconName = "map_tip_pin_icon";
                }


                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(iconName, 69, 100)));
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
