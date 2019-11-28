package com.example.p_kontrol.UI.Contexts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class State implements IState  {

    final int DEFAULT_MAP_ZOOM = 15;
    final int DEFAULT_ZOOM_ZOOM = 17;

    // Constructer retrieved Vars
    MapContext context;
    IMapContextListener listener = null;

    // Context retrieved Vars
    GoogleMap map;

    public State(MapContext context) {
        this.context = context;
        map = context.getMap();
        listener = context.getListener();

        if(listener != null)
            listener.onChangeState();
        System.out.println(context.getListOfTipDto().size() + " SIZE OF LIST ");
        updateMap();
        setListeners();
    }
    private void setListeners(){
        map.setOnMapClickListener(null);
    }

    public void centerMapOnLocation(MapContext context){
        context.centerMapOnLocation();
    }

    @Override
    public void updateMap() {
        int i = 0;
        for(ITipDTO tip: context.getListOfTipDto()){
            MarkerOptions markerOptions = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizeMapIcons("tip",100,100)));
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

    @Override
    public void setDoneListner(Object obj) {
    }

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
