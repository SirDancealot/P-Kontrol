package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.media.MediaPlayer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


import java.util.List;
/**
 * @responsibilty to keep track of wher a user have parked, and give feedback if a Pvagt is in range. within the last 20 minutes
 *
 * */
public class StateParking extends State {

    Activity context;

    //Alert
    int time = 1200000;//20 min
    MediaPlayer m;

    LatLng currentLocation;

    /** @responsibilty to keep track of wher a user have parked, and give feedback if a Pvagt is in range. within the last 20 minutes
     *
     * if you are warned of a P-vagt then a small tone will play, if the alert is older than 20min it is a grayer version of the alert symbol
     *
     * StateParking Extends State
     * @see {@link com.example.p_kontrol.UI.Map.State}
     *
     * */
    public StateParking(MapFragment parent) {
        super(parent);
        this.context = parent.getContext();
        map.clear();

        // MediaPlayer
        m = MediaPlayer.create(context, R.raw.alarm);

        //Data
        LiveDataViewModel model = ViewModelProviders.of(parent.getActivity()).get(LiveDataViewModel.class);
        currentLocation = viewModel.getCurrentLocation().getValue();
        model.getPvagtList().observe(parent, pVagtList -> updatePVagter(pVagtList));
        model.updatePVagter(currentLocation);

        //Pin
        Pins pin = Pins.parkingSpot;
        String pinName = pin.getName();
        int scalingConst = pin.getDimY() / 45;       //45 is the desired height
        int pinX = pin.getDimX() / scalingConst;
        int pinY = pin.getDimY() / scalingConst;

        //Mark Current Location of Car parking
        MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons(pinName, pinX, pinY)));
        map.addMarker(markerOptions.position(currentLocation));

    }

    /**
     * a method to update the P-vagt's shown on the map
     * @param pVagtList a list of the Pvagt alerts in the area.
     * */
    public void updatePVagter(List<PVagtDTO> pVagtList) {
        int i = 0;

        for (PVagtDTO vagt : pVagtList) {

            if (System.currentTimeMillis() > vagt.getCreationDate().getTime() + time) {

                //Pin
                Pins pin = Pins.pVagtOld;
                String pinName = pin.getName();
                int scalingConst = pin.getDimY() / 75;       //100 is the desired height
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
                int scalingConst = pin.getDimY() / 75;       //75 is the desired height
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