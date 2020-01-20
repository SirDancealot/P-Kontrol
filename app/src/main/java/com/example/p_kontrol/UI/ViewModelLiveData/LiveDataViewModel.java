package com.example.p_kontrol.UI.ViewModelLiveData;

import android.app.Service;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";

    private MutableLiveData<List<TipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<TipDTO> tipCreateObject;
    private MutableLiveData<LatLng> currentLocation;

    // Map Data.MutableLiveData<
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<LatLng> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;


    // todo er dette rigtigt? August
    // Write tip
    private TipDTO currentTip;
    public TipDTO getCurrentTip() {


    public LiveDataViewModel(){
        tipList = new MutableLiveData<>();
        pVagtList = new MutableLiveData<>();
        tipCreateObject = new MutableLiveData<>(new ATipDTO());

    }



    public ATipDTO getCurrentTip() {
        return currentTip;
    }
    public void setCurrentTip(TipDTO currentTip) {
        this.currentTip = currentTip;
    }

    private IBackend bk = BackendStub.getBackend();
    List<PVagtDTO> l = new LinkedList<>();



    public void updatePVagter(LatLng location){
        bk.getPVagter(location, pVagtList.getValue());
    }



    //######    Setters     ######


    public void setTipCreateObject(TipDTO tipCreateObject) {
        Log.d(TAG, "setTipCreateObject: " + this + "\n" + tipCreateObject + "\n");

        this.tipCreateObject.setValue(tipCreateObject);
    }

    //######    Getters     ######
    public LiveData<List<TipDTO>> getTipList() {
        Log.d(TAG, "getTipList: " + this);
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        if (tipList.getValue() == null)
            tipList.setValue(new ArrayList<TipDTO>());

        return tipList;
    }

    public LiveData<List<PVagtDTO>> getPvagtList() {
        Log.d(TAG, "getPvagtList: " + this);
        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>();

        return pVagtList;
    }
    public LiveData<TipDTO> getTipCreateObject() { //TODO make getter and let this
        Log.d(TAG, "getTipCreateObject: " + this);
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>();
            tipCreateObject.setValue(new TipDTO());
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





    //######    Service calls     ######
    public void startTipQuery(FirestoreDAO firestoreDAO){
        firestoreDAO.queryByLocation(map_currentLocation.getValue(), 20, tipList);
    }

    public void createTip(FirestoreDAO firestoreDAO) {
        Log.d(TAG, "createTip: \n" + tipCreateObject.getValue());
        if (tipCreateObject != null) {
            if (tipCreateObject.getValue() != null) {
                ATipDTO dto = tipCreateObject.getValue();
                firestoreDAO.createTip(dto);
            } else {
                Log.e(TAG, "createTip: tipCreateObject.getValue() is null");
            }
        } else {
            Log.e(TAG, "createTip: tipCreateObject is null");
        }
    }
}
