package com.example.p_kontrol.UI.ViewModelLiveData;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";

    private MutableLiveData<List<ATipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ATipDTO> tipCreateObject;
    private MutableLiveData<LatLng> currentLocation;

    // Map Data.MutableLiveData<
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<LatLng> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;

    private IBackend bk = BackendStub.getBackend();
    List<PVagtDTO> l = new LinkedList<>();


    public void updateTips(LatLng location){
        Log.d(TAG, "updateTips: " + this);
        bk.getTips(location, tipList);
    }
    public void createTip() {
        Log.d(TAG, "createTip: " + this + "\n" + tipCreateObject.getValue());
        if (tipCreateObject != null) {
            ATipDTO dto = tipCreateObject.getValue();
            bk.createTip(dto);
            updateTips(new LatLng(55.43521, 12.23504));//todo make this not hardcoded
        }
    }





    //######    Setters     ######


    public void setTipCreateObject(ATipDTO tipCreateObject) {
        Log.d(TAG, "setTipCreateObject: " + this + "\n" + tipCreateObject + "\n");

        this.tipCreateObject.setValue(tipCreateObject);
    }

    //######    Getters     ######
    public LiveData<List<ATipDTO>> getTipList() {
        Log.d(TAG, "getTipList: " + this);
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        if (tipList.getValue() == null)
            tipList.setValue(new ArrayList<ATipDTO>());

        return tipList;
    }

    public LiveData<List<PVagtDTO>> getPvagtList() {
        Log.d(TAG, "getPvagtList: " + this);
        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>();
            //l.add(new PVagtDTO(new LatLng(55.676098,12.568337), new Date(), "123"));
            //l.add(new PVagtDTO(new LatLng(55.686098,12.568337), new Date(1000), "123"));
            //l.add(new PVagtDTO(new LatLng(55.696098,12.568337), new Date(), "123"));
            //l.add(new PVagtDTO(new LatLng(55.626098,12.568337), new Date(1000000000), "123"));
            //pVagtList.setValue(l);
        }


        return pVagtList;
    }
    public LiveData<ATipDTO> getTipCreateObject() { //TODO make getter and let this
        Log.d(TAG, "getTipCreateObject: " + this);
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>();
            tipCreateObject.setValue(new ATipDTO());
        }

        return tipCreateObject;
    }

    // Map Data
    public MutableLiveData<LatLng> getCurrentWindowLocation(){
        Log.d(TAG, "getCurrentWindowLocation: " + this);
        if(map_WindowLocation == null){
            map_WindowLocation = new MutableLiveData<>();
        }
        return  map_WindowLocation;
    }
    public MutableLiveData<LatLng> getCurrentWindowZoom(){
        Log.d(TAG, "getCurrentWindowZoom: " + this);
        if(map_WindowZoom == null){
            map_WindowZoom = new MutableLiveData<>();
        }
        return  map_WindowZoom;
    }
    public MutableLiveData<LatLng> getCurrentLocation(){ // The User location or Car Location
        Log.d(TAG, "getCurrentLocation: " + this);
        if(map_currentLocation == null){
            map_currentLocation = new MutableLiveData<>();
        }
        return  map_currentLocation;
    }
    public void updatePVagter(LatLng location){
        Log.d(TAG, "updatePVagter: " + this);
        if (pVagtList == null)
            pVagtList = new MutableLiveData<>();

        bk.getPVagter(location, pVagtList );
    }

    public void createPVagt(PVagtDTO vagt){
        Log.d(TAG, "CreatePVagt: " + this);

        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>();
        }
        //l.add(vagt);
        //pVagtList.setValue(l);
        bk.createPVagt(vagt);
    }


}
