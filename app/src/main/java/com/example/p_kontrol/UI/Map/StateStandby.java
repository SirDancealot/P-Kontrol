package com.example.p_kontrol.UI.Map;

import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.security.Provider;
import java.util.List;
import java.util.Objects;

public class StateStandby extends State {

    public StateStandby(MapContext context) {
        super(context);
        map.clear();
        TAG = "StateStandby";
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
        Log.d(TAG, "onCreateView: ");

        return super.onCreateView(name, context, attrs);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        Log.d(TAG, "onCreate: ");


        LiveDataViewModel viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        viewModel.getTipList().observe(this, this::updateMap);
        //viewModel.updateTips(context.getLocation()); //TODO find ud af om det er det view location eller physical location

        updateMap(Objects.requireNonNull(viewModel.getTipList().getValue()));
    }

    @Override
    public void updateMap(List<ATipDTO> list ) {
        Log.d(TAG, "updateMap: ");
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
