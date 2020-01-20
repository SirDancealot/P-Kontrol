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

public class StateStandby extends State {


    public StateStandby(MapFragment parent) {
        super(parent);
        map.clear();


        LiveData<List<TipDTO>> tipList = viewModel.getTipList();

        tipList.observe(parent.getViewLifecycleOwner(), list -> {
            try {
                updateMap(list);
            } catch (NullPointerException e) {
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );
        // todo ViewModel Se Her
        //viewModel.updateTips(null); TODO this might be redundant with the service implementation
        updateMap(tipList.getValue());
    }

    @Override
    public void updateMap(List<TipDTO> list ) {
        MarkerOptions markerOptions = null;
        map.clear();

        if(list != null) {
            int i = 0;
            for (TipDTO tip : list) {
                // todo ret navne

                Pins pin = Pins.paid;
                if(tip.getType() != 0){
                    // CANNOT BE SWITCH BECAUSE SWITCH DOSENT ALLOW ENUMERATIONS AS CONSTANT EXPRESSION
                    if( tip.getType() == TipTypes.paid.getValue() ){
                        pin = Pins.paid;
                    }
                    else if(tip.getType() == TipTypes.free.getValue() ){
                        pin = Pins.free;
                    }
                    else if(tip.getType() == TipTypes.alarm.getValue() ) {
                        pin = Pins.alarm;
                    }
                }

                String pinName = pin.getName();
                int scalingConst = pin.getDimY() / 100;       //100 is the desired height
                int pinX = pin.getDimX() / scalingConst;
                int pinY = pin.getDimY() / scalingConst;


                markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pinName, pinX, pinY)));
                map.addMarker(markerOptions.position(new LatLng(tip.getL().getLatitude(), tip.getL().getLongitude())).title(String.valueOf(i++)));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {

                @Override
                public boolean onMarkerClick(Marker marker) {
                        listener.onTipClick(Integer.parseInt(marker.getTitle()));
                        return true;
                    }
                });

            }
        }
    }

}
