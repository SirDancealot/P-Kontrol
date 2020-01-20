package com.example.p_kontrol.UI.Map;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

import java.util.List;
/**
 * @responsibilty act as a base type and implementation for all states
 * */
abstract public class State  {

    // Defaults.
    final String TAG = "State"      ;

    MapFragment parent             ;
    IMapFragmentListener listener   ;
    GoogleMap map                   ;

    LiveDataViewModel viewModel     ;

    /**
     * @responsibilty act as a base type and implementation for all states
     * @param parent the State Wrapper, is needed to call data from the activity and to be able to change states from within the states.
     * */
    public State( MapFragment parent ){
        //retrieving Objects
        this.parent     = parent;

        map         = parent.getMap();
        listener    = parent.getFragmentListener();
        viewModel   = ViewModelProviders.of(this.parent.getActivity()).get(LiveDataViewModel.class); //parent.getViewModel();

        // Setting Listeners
        setListeners();
    }
    // interface
    /**
     * an Update Method, also here for the right to be overidden
     * @param list the list of tipsMarkers to set on the map.
     * */
    public void updateMap(List<ITipDTO> list) {}

    /**
     * the Real implementation of the centermethod, it is individual to each state, such that it can be overidden.
     * */
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

    // methods every State needs for it self
    /**
     * used to animate the camera to a location
     * */
    public void animeCamara(LatLng geo){
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(geo)
                .zoom(parent.DEFAULT_ZOOM ).build();
        map.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
        //map.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    /**
     * needed to overide specifik listeners in specifik states.
     * */
    public void setListeners(){
        map.setOnMapClickListener(null);
    }

    /**
     * used to set Markers on the map. in a certain height and width.
     * */
    public Bitmap resizeMapIcons(String iconName, int width, int height){

        Resources res = parent.getContext().getResources();
        int tipResource = res.getIdentifier(iconName, "drawable", parent.getContext().getPackageName() );

        Bitmap imageBitmap = BitmapFactory.decodeResource(res,tipResource);
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, width, height, false);
        return resizedBitmap;
    }

}
