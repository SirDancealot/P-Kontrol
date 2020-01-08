package com.example.p_kontrol.UI.map;

import android.util.Log;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class StateSelectLocation extends State {

    String TAG = "State Select Loaction ";
    IMapSelectedLocationListener listenerDone;
    // todo change to current location of User.
    LatLng currentMarkerLocation = null;

    public StateSelectLocation(MapContext context) {
        super(context);
        zoomIn();
        map.clear();
        acceptBtn.setVisibility(View.VISIBLE);
        cancelBtn.setVisibility(View.VISIBLE);
    }

    // interface Overrides
    @Override
    public void updateMap(){}

    // Listeners
    @Override
    public void setListeners(){

        map.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                map.clear();
                currentMarkerLocation = latLng;
                map.addMarker(new MarkerOptions().position(latLng));
            }
        });

        map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });

    }
    @Override
    public void setDoneListner(Object listenerDone){
        this.listenerDone = (IMapSelectedLocationListener) listenerDone;
    }

    // Button Calls
    @Override
    public void centerMethod(){
        centerMapOnLocation(context);
    }
    @Override
    public void acceptMethod(){
// todo still accepts a null location. must not, but should be implemented with a standard location, and a check here to se if there is a marker. BOTH ARE NECESARY!!
        Log.i(TAG, "acceptMethod: ");
        map.clear();
        zoomOut();

        if(listenerDone != null)
            listenerDone.onSelectedLocation(currentMarkerLocation);
    }
    @Override
    public void cancelMethod(){
        Log.i(TAG, "cancelMethod: ");

        map.clear();
        zoomOut();

        if(listenerDone != null)
            listenerDone.onCancelSelection();
    }

}
