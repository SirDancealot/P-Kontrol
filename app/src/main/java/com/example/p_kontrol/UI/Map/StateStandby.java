package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;
/**
 * @responsibilty to show the tips on the map. and make em clickable
 *
 * */
public class StateStandby extends State {

    /**@responsibilty to show the tips on the map. and make em clickable
     *
     * StateSelectLocation Extends State
     * @see {@link com.example.p_kontrol.UI.Map.State}
     *
     * */
    public StateStandby(MapFragment parent) {
        super(parent);
        map.clear();


        LiveData<List<ITipDTO>> tipList = viewModel.getTipList();

        tipList.observe(parent.getViewLifecycleOwner(), list -> {
            try {
                if (parent.getCurrentState() instanceof StateStandby)
                    updateMap(list);
            } catch (NullPointerException e) {
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );
        updateMap(tipList.getValue());
    }

    /** sets all tips onto the map ready to read.
     * @param list, a list of all the tips in memory, all will be shown
     * */
    @Override
    public void updateMap(List<ITipDTO> list ) {
        MarkerOptions markerOptions = null;
        map.clear();

        if(list != null) {
            int i = 0;
            map.setOnMarkerClickListener(marker -> {
                listener.onTipClick(marker.getTitle());
                return true;
            });
            for (ITipDTO tip : list) {

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

                if (pin.getMarker() == null)
                    pin.initMarkers(parent);
                markerOptions = pin.getMarker();


                map.addMarker(markerOptions.position(new LatLng(tip.getL().getLatitude(), tip.getL().getLongitude())).title(tip.getAuthor().getUid() + "-" + tip.getG()));
            }
        }
    }

}
