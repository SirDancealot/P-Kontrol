package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class StateStandby extends State {


    public StateStandby(MapFragment parent) {
        super(parent);
        map.clear();
        centerMethod();


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
    public void updateMap(List<ATipDTO> list) {

        int i = 0;
        for (ATipDTO tip : list) {
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(Pins.paid.getName(), 69, 100)));
            map.addMarker(markerOptions.position(new LatLng(tip.getL().getLatitude(), tip.getL().getLongitude())).title(String.valueOf(i++)));
            map.setOnMarkerClickListener(marker -> {
                listener.onTipClick(Integer.parseInt(marker.getTitle()));
                Log.i(TAG, "updateMap: PUT A PIN IN IT!!!!! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ! ");
                return true;
            });
        }
    }

}
