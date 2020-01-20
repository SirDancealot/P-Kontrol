package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;

public class StateParking extends State {


    MutableLiveData pVagtList;
    Activity context;

    //Active Alert Time
    //20 min
    int time = 1200000;

    //Mediaplayer
    MediaPlayer m;

    LatLng currentLocation;


    public StateParking(MapFragment parent) {
        super(parent);
        map.clear();
        this.context = parent.getContext();

        // MediaPlayer
        m = MediaPlayer.create(context, R.raw.alarm);

        LiveDataViewModel model = ViewModelProviders.of(parent.getActivity()).get(LiveDataViewModel.class);
        currentLocation = viewModel.getCurrentLocation().getValue();
        model.getPvagtList().observe(parent, pVagtList -> updatePVagter(pVagtList));
        model.updatePVagter(currentLocation);


        //Pin
        Pins pin = Pins.parkingSpot;
        String pinName = pin.getName();
        int scalingConst = pin.getDimY() / 75;       //75 is the desired height
        int pinX = pin.getDimX() / scalingConst;
        int pinY = pin.getDimY() / scalingConst;


        //Mark Current Location of Car parking
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pinName, pinX, pinY)));
        map.addMarker(markerOptions.position(currentLocation));


    }


    public void updatePVagter(List<PVagtDTO> pVagtList) {
        int i = 0;

        for (PVagtDTO vagt : pVagtList) {

            if (System.currentTimeMillis() > vagt.getCreationDate().getTime() + time) {

                //Pin
                Pins pin = Pins.pVagtOld;
                String pinName = pin.getName();
                int scalingConst = pin.getDimY() / 100;       //100 is the desired height
                int pinX = pin.getDimX() / scalingConst;
                int pinY = pin.getDimY() / scalingConst;


                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pinName, pinX, pinY)));
                map.addMarker(markerOptions.position(vagt.getLocation()));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        listener.onTipClick(Integer.parseInt(marker.getTitle()));
                        return true;
                    }
                });

            } else {

                //Pin
                Pins pin = Pins.pVagt;
                String pinName = pin.getName();
                int scalingConst = pin.getDimY() / 100;       //100 is the desired height
                int pinX = pin.getDimX() / scalingConst;
                int pinY = pin.getDimY() / scalingConst;

                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pinName, pinX, pinY)));

                map.addMarker(markerOptions.position(vagt.getLocation()));
                map.setOnMarkerClickListener(marker -> {
                    listener.onTipClick(Integer.parseInt(marker.getTitle()));
                    return true;
                });


              m.start();



            }
        }
    }
}