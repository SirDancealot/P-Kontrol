package com.example.p_kontrol.UI.Map;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class State implements IState  {

    // Defaults.
    final int DEFAULT_MAP_ZOOM = 15;
    final int DEFAULT_ZOOM_ZOOM = 17;

    // Objects
    MapContext context;
    IMapContextListener listener = null;
    GoogleMap map;
    Button centerBtn, acceptBtn, cancelBtn;

    public State(MapContext context) {
        //retrieving Objects
        this.context = context;
        map = context.getMap();
        listener = context.getListener();
        centerBtn = context.getCenterBtn();
        acceptBtn = context.getAcceptBtn();
        cancelBtn = context.getCancelBtn();

        // Setting Listeners
        setListeners();
        setupButtonListeners();

        // Updating Map look.
        updateMap();

        // Calling the listener that a new State has been initiated.
        if(listener != null)
            listener.onChangeState();
    }

    //Interface
    @Override
    public void setDoneListner(Object listenerDone){}
    @Override
    public void centerMapOnLocation(MapContext context){
        context.centerMapOnLocation();
    }
    @Override
    public void updateMap() {
        // todo her er der sket et "NumberFormatException" s== null . line 80. i StateSelectLocation.
        int i = 0;
        for(ITipDTO tip: context.getListOfTipDto()){
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("map_tip_pin_icon",69,100)));
            map.addMarker(markerOptions.position(tip.getLocation()).title(String.valueOf(i++)));
            map.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    listener.onTipClick(Integer.parseInt(marker.getTitle()));
                    return true;
                }
            });
        }

    }

    //Map on clicks
    public void setListeners(){
        map.setOnMapClickListener(null);
    }

    //Buttons methods
    public void setupButtonListeners(){
        try {
            centerBtn.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    centerMethod();
                }
            });
        }catch (NullPointerException e){}
        try {
        acceptBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                acceptMethod();
            }
        });
        }catch (NullPointerException e){}
        try {
        cancelBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                cancelMethod();
            }
        });
        }catch (NullPointerException e){}
    }
    public void centerMethod(){}
    public void acceptMethod(){}
    public void cancelMethod(){}

    // Regular methods
    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier(iconName, "drawable", context.getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
    public void zoomIn(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .zoom(DEFAULT_ZOOM_ZOOM).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void zoomOut(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .zoom(DEFAULT_MAP_ZOOM).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
}
