package com.example.p_kontrol.UI.ViewModelLiveData;

import android.app.Service;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";

    private MutableLiveData<List<ITipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ITipDTO> tipCreateObject;
    private MutableLiveData<LatLng> currentLocation;

    // Map Data.MutableLiveData<
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<LatLng> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;


    // todo er dette rigtigt? August
    // Write tip
    private ITipDTO currentTip;
    private UserInfoDTO userInfoDTO;

    public LiveDataViewModel(){
        tipList = new MutableLiveData<>();
        pVagtList = new MutableLiveData<>();
        tipCreateObject = new MutableLiveData<>(new TipDTO());
        userInfoDTO = UserInfoDTO.getUserInfoDTO();
    }



    public ITipDTO getCurrentTip() {
        return currentTip;
    }
    public void setCurrentTip(ITipDTO currentTip) {
        this.currentTip = currentTip;
    }

    private IBackend bk = BackendStub.getBackend();
    List<PVagtDTO> l = new LinkedList<>();





    //######    Setters     ######


    public void setTipCreateObject(ITipDTO tipCreateObject) {
        Log.d(TAG, "setTipCreateObject: " + this + "\n" + tipCreateObject + "\n");

        this.tipCreateObject.setValue(tipCreateObject);
    }

    //######    Getters     ######
    public LiveData<List<ITipDTO>> getTipList() {
        Log.d(TAG, "getTipList: " + this);
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        if (tipList.getValue() == null)
            tipList.setValue(new ArrayList<ITipDTO>());

        return tipList;
    }

    public LiveData<List<PVagtDTO>> getPvagtList() {
        Log.d(TAG, "getPvagtList: " + this);
        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>();
        }
        return pVagtList;
    }
    public LiveData<ITipDTO> getTipCreateObject() { //TODO make getter and let this
        Log.d(TAG, "getTipCreateObject: " + this);
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>();
            ITipDTO tip = new TipDTO();
            tipCreateObject.setValue(tip);
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

    // todo fix this . cannot have input of FireBase DAO, UI dosent know it.
    public void createTip() {
        Log.d(TAG, "createTip: \n" + tipCreateObject.getValue());
        if (tipCreateObject != null) {
            if (tipCreateObject.getValue() != null) {
                ITipDTO dto = tipCreateObject.getValue();
                dto.setAuthor(userInfoDTO.getSimpleUser());
                dto.setCreationDate(new Date());
               // firestoreDAO.createTip(dto);
            } else {
                Log.e(TAG, "createTip: tipCreateObject.getValue() is null");
            }
        } else {
            Log.e(TAG, "createTip: tipCreateObject is null");
        }
    }
}
