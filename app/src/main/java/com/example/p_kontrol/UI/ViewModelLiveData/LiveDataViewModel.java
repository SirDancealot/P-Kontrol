package com.example.p_kontrol.UI.ViewModelLiveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Handler;

public class LiveDataViewModel extends ViewModel {

    private MutableLiveData<List<ATipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ATipDTO> tipCreateObject;

    // Map Data.
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<LatLng> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;

    private IBackend bk = BackendStub.getBackend();


    public void updateTips(LatLng location){
        bk.getTips(location, tipList);
    }

    public void createTip() {
        try {
            bk.createTip(tipCreateObject.getValue());
        }catch (Exception e){

        }
    }

    public void updatePVagter(LatLng location){
        bk.getPVagter(location, pVagtList );
    }

    public void createPVagt(PVagtDTO vagt){



        bk.createPVagt(vagt);
    }


    //######    Getters     ######
    public LiveData<List<ATipDTO>> getTipList() {
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        return tipList;
    }



    public LiveData<List<PVagtDTO>> getPvagtList() {
        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>();
        }



        return pVagtList;
    }


    public MutableLiveData<ATipDTO> getMutableTipCreateObject() {
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>(new ATipDTO());
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
