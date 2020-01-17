package com.example.p_kontrol.UI.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;

abstract public class State implements IState  {

    // Defaults.
    final String TAG = "State"      ;

    MapFragment parent             ;
    IMapFragmentListener listener   ;
    GoogleMap map                   ;

    LiveDataViewModel viewModel     ;

    public State( MapFragment parent ) {
        //retrieving Objects
        this.parent     = parent;

        map         = parent.getMap();
        listener    = parent.getFragmentListener();
        viewModel   = ViewModelProviders.of(this.parent.getActivity()).get(LiveDataViewModel.class); //parent.getViewModel();

        // Setting Listeners
        centerMethod();
        setListeners();
    }

    @Override
    public void updateMap(List<ATipDTO> list) {}
    @Override
    public void setDoneListner(IMapFragmentListener listener) {
        this.listener = listener;
    }
    @Override
    public void centerMethod(){
        try {
            Task locationResult = parent.getFusedLocationProviderClient().getLastLocation();
            locationResult.addOnCompleteListener( parent.getContext() , new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if ( task.isSuccessful()) {
                        // Set the map's camera position to the current location of the device.
                        Location location = (Location) task.getResult();
                        LatLng result = null;
                        if (location != null) {
                            result = new LatLng(location.getLatitude(),location.getLongitude());
                        }
                        viewModel.getCurrentLocation().setValue(result);
                        animeCamara(result);

                    } else {
                        // if location cannot be found.
                        viewModel.getCurrentLocation().setValue( parent.DEFAULT_LOCATION );
                        animeCamara( parent.DEFAULT_LOCATION );
                    }
                }
            });

        } catch(SecurityException e) {
            Log.e("Exception: %s", e.getMessage());
        }
    }


    public void animeCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(geo)
                .zoom(parent.DEFAULT_ZOOM ).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void setListeners(){
        map.setOnMapClickListener(null);
    }
    public Bitmap resizeMapIcons(String iconName, int width, int height){

        Resources res = parent.getContext().getResources();
        int tipResource = res.getIdentifier(iconName, "drawable", parent.getContext().getPackageName() );

        Bitmap imageBitmap = BitmapFactory.decodeResource(res,tipResource);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }
    public void zoomIn(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .zoom(parent.DEFAULT_ZOOM_CLOSEUP).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }
    public void zoomOut(){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(map.getCameraPosition().target)
                .zoom(parent.DEFAULT_ZOOM).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /*


    public void setListeners(){
        map.setOnMapClickListener(null);
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
    }*/
}
