package com.example.p_kontrol.UI.ViewModelLiveData;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.ArrayList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";

    private MutableLiveData<List<ATipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ATipDTO> tipCreateObject;
    private MutableLiveData<LatLng> currentLocation;

    // Map Data.
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<LatLng> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;

    private IBackend bk = BackendStub.getBackend();

    public void updateTips(LatLng location){
        Log.d(TAG, "updateTips: ");
        bk.getTips(location, tipList);
    }
    public void createTip() {
        Log.d(TAG, "createTip: ");
        bk.createTip(tipCreateObject.getValue());
        updateTips(new LatLng(55.43521, 12.23504));//todo make this not hardcoded
    }
    public void updatePVagter(LatLng location){
        bk.getPVagter(location, pVagtList.getValue());
    }

    //######    Getters     ######
    public LiveData<List<ATipDTO>> getTipList() {
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        if (tipList.getValue() == null)
            tipList.setValue(new ArrayList<ATipDTO>());

        return tipList;
    }
    public LiveData<List<PVagtDTO>> getPvagtList() {
        if (pVagtList == null)
            pVagtList = new MutableLiveData<>();

        return pVagtList;
    }
    public MutableLiveData<ATipDTO> getMutableTipCreateObject() { //TODO make getter and let this
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>();
        }

        return tipCreateObject;
    }

    // Map Data
    public MutableLiveData<LatLng> getCurrentWindowLocation(){
        if(map_WindowLocation == null){
            map_WindowLocation = new MutableLiveData<>();
        }
        return  map_WindowLocation;
    }
    public MutableLiveData<LatLng> getCurrentWindowZoom(){
        if(map_WindowZoom == null){
            map_WindowZoom = new MutableLiveData<>();
        }
        return  map_WindowZoom;
    }
    public MutableLiveData<LatLng> getCurrentLocation(){ // The User location or Car Location
        if(map_currentLocation == null){
            map_currentLocation = new MutableLiveData<>();
        }
        return  map_currentLocation;
    }
}
