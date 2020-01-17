package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.ATipDTO;
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


        LiveData<List<ATipDTO>> tipList = viewModel.getTipList();
        List<ATipDTO>  temp = tipList.getValue();

        tipList.observe(parent.getViewLifecycleOwner(), list -> {
            try {
                updateMap(list);
            }catch (NullPointerException e){
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );
        // todo ViewModel Se Her
        viewModel.updateTips(null);
    }

    @Override
    public void updateMap(List<ATipDTO> list ) {
        MarkerOptions markerOptions = null;

        if(list != null) {
            int i = 0;
            for (ATipDTO tip : list) {
                // todo ret navne
                String tipPinName = Pins.paid.getName();
                if(tip.getType() != 0){
                    // CANNOT BE SWITCH BECAUSE SWITCH DOSENT ALLOW ENUMERATIONS AS CONSTANT EXPRESSION
                    if( tip.getType() == TipTypes.paid.getValue() ){
                        tipPinName = Pins.paid.getName();
                    }
                    else if(tip.getType() == TipTypes.free.getValue() ){
                        tipPinName = Pins.free.getName();
                    }
                    else if(tip.getType() == TipTypes.alarm.getValue() ) {
                        tipPinName = Pins.alarm.getName();
                    }
                }

                markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(tipPinName, 69, 100)));
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
