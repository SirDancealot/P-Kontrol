package com.example.p_kontrol.UI.Contexts;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.NonNull;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.Activities.ActivityMapView;
import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class State implements IState  {


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

        updateMap();
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

    public Bitmap resizeMapIcons(String iconName, int width, int height){
        Bitmap imageBitmap = BitmapFactory.decodeResource(context.getResources(),context.getResources().getIdentifier(iconName, "drawable", context.getActivity().getPackageName()));
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}
