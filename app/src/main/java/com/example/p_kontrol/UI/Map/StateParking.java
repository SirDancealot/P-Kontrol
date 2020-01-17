package com.example.p_kontrol.UI.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

public class StateParking extends State {

    LatLng currentLocation;

    MutableLiveData pVagtList;

    //Active Alert Time
    //20 min
    int time = 1200000;

    //Mediaplayer
    //MediaPlayer m = new MediaPlayer().create(this, R.raw.alarm);


    public StateParking(MapFragment parent, FragmentActivity lifeOwner) {
        super(parent);
        zoomIn();
        map.clear();
       // currentLocation = context.getLocation();


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
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("danger_icon", 69, 100)));
                map.addMarker(markerOptions.position(vagt.getLocation()));
                map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                    @Override
                    public boolean onMarkerClick(Marker marker) {
                        listener.onTipClick(Integer.parseInt(marker.getTitle()));
                        return true;
                    }
                });


                //Play annoying alarm sound
    //            m.start();


            } else {
                MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("known_danger_icon", 69, 100)));
                map.addMarker(markerOptions.position(vagt.getLocation()));
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