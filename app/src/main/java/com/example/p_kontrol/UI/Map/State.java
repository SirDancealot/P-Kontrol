package com.example.p_kontrol.UI.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

abstract public class State extends FragmentActivity implements IState  { //TODO this should not extend activity, make it a regular object instead



    // Defaults.
    final int DEFAULT_MAP_ZOOM = 15;
    final int DEFAULT_ZOOM_ZOOM = 17;
    String TAG = "state";

    IMapContext context;
    IMapStateInformationExpert defaultINFO;
    IMapContextListener listener = null;
    GoogleMap map;

    LiveDataViewModel model;

    public State(MapContext context) {
        //retrieving Objects
        this.context = context;
        map = context.getMap();
        listener = context.getContextListener();
        defaultINFO = context;

        // Setting Listeners
        setListeners();

        // Calling the listener that a new State has been initiated.
        if(listener != null)
            listener.onChangeState();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
    }

    @Override
    public void updateMap(List<ATipDTO> list ) {
    }

    @Override
    public void setDoneListner(IMapContextListener listener) {
        this.listener = listener;
    }
    @Override
    public void centerMethod(){
        if (map.isMyLocationEnabled()) {
            Log.d(TAG, "centerMethod: t");
        } else {
            Log.d(TAG, "centerMethod: f");
        }
        try {
            Task locationResult = context.getFusedLocationProviderClient().getLastLocation();
            locationResult.addOnCompleteListener( this, new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location location = (Location) task.getResult();
                        LatLng resault = new LatLng(location.getLatitude(),location.getLongitude());
                        context.setLocaton(resault);
                        animeCamara(context.getLocation());
                    } else {
                        // ingen lokation fejl
                        Log.d(TAG, "onComplete: ingen lokation fejl");
                    }
                }
            });

        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }
    @Override
    public void updateLocation() {
            context.getFusedLocationProviderClient().getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                context.setLocaton(new LatLng(location.getLatitude(), location.getLongitude()));
                                Log.d(TAG, "onSuccess: fandt location");
                            }
                        }
                    });
    }



    public void setListeners(){
        map.setOnMapClickListener(null);
    }

//context.getActivity().getPackageName()
    // Regular methods
    public Bitmap resizeMapIcons(String iconName, int width, int height){

        Resources res = context.getResources();
        int tipResource = res.getIdentifier("map_tip_pin_icon", "drawable", context.getPackageName() );

        Bitmap imageBitmap = BitmapFactory.decodeResource(res,tipResource);
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
    public void moveCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(geo)
                .zoom(DEFAULT_MAP_ZOOM ).build();
        //map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void animeCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(geo)
                .zoom(DEFAULT_MAP_ZOOM ).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void zoomCamara(int zoom){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .zoom(zoom).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }


}
