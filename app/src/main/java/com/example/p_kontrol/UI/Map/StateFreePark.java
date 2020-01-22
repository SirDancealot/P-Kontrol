package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import static com.example.p_kontrol.UI.Map.Pins.free;
/**
 * @responsibilty to sort out tips, like the Standby state, but only show the "free" category
 *@see {@link com.example.p_kontrol.UI.Map.StateStandby}
 * */
public class StateFreePark extends State {

    /** @responsibilty to sort out tips, like the Standby state, but only show the "free" category
     *@see {@link com.example.p_kontrol.UI.Map.StateStandby}
     *
     * pin grafic id's
     * @see {@link com.example.p_kontrol.UI.Map.Pins}
     *
     * Free park Extends
     * @see {@link com.example.p_kontrol.UI.Map.State}
     *
     * */
    public StateFreePark(MapFragment parent) {
        super(parent);

        LiveData<List<ITipDTO>> liveDataTipList = viewModel.getTipList();
        List<ITipDTO> tipList = liveDataTipList.getValue();
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

    /** Sets markers on the map but sorts out the tips currently in the Memory that arent free Tips
     *  and therefore only shows free tips
     *
     * @param list, a list of all the tips in memory, but only a portion will be shown
     * */
    @Override
    public void updateMap(List<ITipDTO> list ) {
        MarkerOptions markerOptions = null;
        map.clear();

        Pins pin = Pins.free;

        if(list != null) {
            int i = 0;
            for (ITipDTO tip : list) {
                if(tip.getType() != 0){
                    if(tip.getType() == TipTypes.free.getValue()) {

                        if (pin.getMarker() == null)
                            pin.initMarkers(parent);
                        markerOptions = pin.getMarker();

                        map.addMarker(markerOptions.position(new LatLng(tip.getL().getLatitude(), tip.getL().getLongitude())).title(String.valueOf(i++)));
                        map.setOnMarkerClickListener(marker -> {
                            listener.onTipClick(marker.getTitle());
                            Log.i(TAG, "updateMap: PUT A PIN IN IT!!!!! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ");
                            return true;
                        });
                    }
                }
            }
        }
    }
}
