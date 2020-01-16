package com.example.p_kontrol.UI.Map;

import android.app.Activity;
import android.media.MediaPlayer;
import android.util.Log;

import androidx.fragment.app.FragmentActivity;
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
        zoomIn();
        map.clear();
        this.context = parent.getContext();

        LiveDataViewModel model = ViewModelProviders.of(parent).get(LiveDataViewModel.class);
        currentLocation = viewModel.getCurrentLocation().getValue();
        model.getPvagtList().observe(parent, pVagtList -> updatePVagter(pVagtList));

        m.create(context, R.raw.alarm);


    }

   /* @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

       // currentLocation = parent.getLocation();

        LiveDataViewModel model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        model.updatePVagter(currentLocation);
        model.getPvagtList().observe(this, pVagtList -> updatePVagter(pVagtList));
    }
*/

    public void updatePVagter(List<PVagtDTO> pVagtList) {




        int i = 0;
        for (PVagtDTO vagt : pVagtList) {

            if (System.currentTimeMillis() > vagt.getCreationDate().getTime() + time) {
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("known_danger_icon", 100, 100)));
                map.addMarker(markerOptions.position(vagt.getLocation()));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        listener.onTipClick(Integer.parseInt(marker.getTitle()));
                        return true;
                    }
                });






            } else {

                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("danger_icon", 100, 100)));

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