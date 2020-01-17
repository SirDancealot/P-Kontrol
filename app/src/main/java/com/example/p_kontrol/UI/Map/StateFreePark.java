package com.example.p_kontrol.UI.Map;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class StateFreePark extends State {


    public StateFreePark(MapFragment parent) {
        super(parent);

        LiveData<List<ATipDTO>> liveDataTipList = viewModel.getTipList();
        List<ATipDTO> tipList = liveDataTipList.getValue();
        liveDataTipList.observe(parent.getViewLifecycleOwner(), list -> {
            try {
                updateMap(list);
            }catch (NullPointerException e){
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );

        // todo ViewModel Se Her
        viewModel.updateTips(null);
        updateMap(tipList);
    }


    @Override
    public void animeCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    @Override
    public void updateMap(List<ATipDTO> list ) {
        MarkerOptions markerOptions = null;
        map.clear();

        if(list != null) {
            int i = 0;
            for (ATipDTO tip : list) {
                if(tip.getType() != 0){
                    if(tip.getType() == TipTypes.paid.getValue()) {
                        markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(Pins.free.getName(), 69, 100)));
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
