package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.example.p_kontrol.UI.Map.Pins.free;

public class StateFreePark extends State {


    public StateFreePark(MapFragment parent) {
        super(parent);

        LiveData<List<TipDTO>> liveDataTipList = viewModel.getTipList();
        List<TipDTO> tipList = liveDataTipList.getValue();
        liveDataTipList.observe(parent.getViewLifecycleOwner(), list -> {
            try {
                updateMap(list);
            }catch (NullPointerException e){
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );

        // todo ViewModel Se Her
        //viewModel.updateTips(null); //TODO might be redundant with service db
        updateMap(tipList);
    }



    @Override
    public void updateMap(List<TipDTO> list ) {
        MarkerOptions markerOptions = null;
        map.clear();

        Pins pin = Pins.free;
        String pinName = pin.getName();
        int scalingConst = pin.getDimY() / 100;       //100 is the desired height
        int pinX = pin.getDimX() / scalingConst;
        int pinY = pin.getDimY() / scalingConst;

        if(list != null) {
            int i = 0;
            for (TipDTO tip : list) {
                if(tip.getType() != 0){
                    if(tip.getType() == TipTypes.free.getValue()) {
                        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pinName, pinX, pinY)));
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
    }
}
