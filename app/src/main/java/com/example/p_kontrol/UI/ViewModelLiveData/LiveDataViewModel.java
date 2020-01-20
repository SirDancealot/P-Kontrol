package com.example.p_kontrol.UI.ViewModelLiveData;

import android.app.Service;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";
    private FirestoreDAO dao;

    private MutableLiveData<List<ATipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ATipDTO> tipCreateObject;

    // Map Data.
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<LatLng> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;


    // todo er dette rigtigt? August
    // Write tip
    private ATipDTO currentTip;


    public LiveDataViewModel(){
        tipList = new MutableLiveData<>();
        pVagtList = new MutableLiveData<>();
        tipCreateObject = new MutableLiveData<>(new ATipDTO());

    }



    public ATipDTO getCurrentTip() {
        return currentTip;
    }
    public void setCurrentTip(ATipDTO currentTip) {
        this.currentTip = currentTip;
    }

    private IBackend bk = BackendStub.getBackend();


    public void updatePVagter(LatLng location){
        bk.getPVagter(location, pVagtList.getValue());
    }



    //######    Setters     ######


    public void setDao(FirestoreDAO dao) {
        this.dao = dao;
    }

    public void setTipCreateObject(ATipDTO tipCreateObject) {
        if (this.tipCreateObject != null)
            Log.d(TAG, "setTipCreateObject: before set: \n" + this.tipCreateObject.getValue() + "\n");
        else
            Log.d(TAG, "setTipCreateObject: before set: null");

        Log.d(TAG, "setTipCreateObject: input: \n" + tipCreateObject + "\n");

        this.tipCreateObject.setValue(tipCreateObject);

        if (this.tipCreateObject != null)
            Log.d(TAG, "setTipCreateObject: after set: \n" + this.tipCreateObject.getValue());
        else
            Log.d(TAG, "setTipCreateObject: after set: null");
    }

    //######    Getters     ######
    public LiveData<List<ATipDTO>> getTipList() {
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        if (tipList.getValue() == null)
            tipList.setValue(new ArrayList<>());

        return tipList;
    }
    public LiveData<List<PVagtDTO>> getPvagtList() {
        if (pVagtList == null)
            pVagtList = new MutableLiveData<>();

        return pVagtList;
    }
    public LiveData<ATipDTO> getTipCreateObject() { //TODO make getter and let this
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>();
            tipCreateObject.setValue(new ATipDTO());
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



    //######    Service calls     ######
    public void startTipQuery(){
        if (dao == null){
            Log.e(TAG, "startTipQuery: dao is null");
            return;
        }

        Log.d(TAG, "startTipQuery: ");
        dao.queryByLocation(map_currentLocation.getValue(), 20, tipList);
    }

    public void createTip() {
        if (dao == null){
            Log.e(TAG, "createTip: dao is null");
            return;
        }

        Log.d(TAG, "createTip: \n" + tipCreateObject.getValue());
        if (tipCreateObject != null) {
            if (tipCreateObject.getValue() != null) {
                ATipDTO dto = tipCreateObject.getValue();
                dao.createTip(dto);
            } else {
                Log.e(TAG, "createTip: tipCreateObject.getValue() is null");
            }
        } else {
            Log.e(TAG, "createTip: tipCreateObject is null");
        }
    }
}
