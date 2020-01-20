package com.example.p_kontrol.UI.ViewModelLiveData;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.Interfaces.IRatingDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";
    private FirestoreDAO dao;

    private MutableLiveData<List<ITipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ITipDTO> tipCreateObject;
    private MutableLiveData<LatLng> currentLocation;

    // Map Data.MutableLiveData<
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<Float> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;


    // todo er dette rigtigt? August
    // Write tip
    private ITipDTO currentTip;

    public LiveDataViewModel(){
        tipList = new MutableLiveData<>();
        pVagtList = new MutableLiveData<>();
        tipCreateObject = new MutableLiveData<>(new TipDTO());
    }



    public ITipDTO getCurrentTip() {
        return currentTip;
    }
    public void setCurrentTip(ITipDTO currentTip) {
        this.currentTip = currentTip;
    }

    //Raing
    private MutableLiveData<List<IRatingDTO>> ratings;

    private IBackend bk = BackendStub.getBackend();
    List<PVagtDTO> l = new LinkedList<>();





    //######    Setters     ######


    public void setDao(FirestoreDAO dao) {
        this.dao = dao;
    }

    public void setTipCreateObject(ITipDTO tipCreateObject) {
        if (this.tipCreateObject != null)
            Log.d(TAG, "setTipCreateObject: before set: \n" + this.tipCreateObject.getValue() + "\n");
        else
            Log.d(TAG, "setTipCreateObject: before set: null");

        Log.d(TAG, "setTipCreateObject: input: \n" + tipCreateObject + "\n");

        this.tipCreateObject.setValue(tipCreateObject);
    }

    //######    Getters     ######
    public MutableLiveData<List<ITipDTO>> getTipList() {
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
            tipCreateObject.setValue(new TipDTO());
        }

        return tipCreateObject;
    }

    // Map Data
    public MutableLiveData<LatLng> getCurrentWindowLocation(){
        Log.d(TAG, "getCurrentWindowLocation: " + this);
        if(map_WindowLocation == null){
            map_WindowLocation = new MutableLiveData<>();
            map_WindowLocation.setValue(new LatLng(0, 0));
        }
        return  map_WindowLocation;
    }
    public MutableLiveData<Float> getCurrentWindowZoom(){
        Log.d(TAG, "getCurrentWindowZoom: " + this);
        if(map_WindowZoom == null){
            map_WindowZoom = new MutableLiveData<>();
            map_WindowZoom.setValue(0.0f);
        }
        return map_WindowZoom;
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

    public void createRating(IRatingDTO rating){
        if(ratings == null)
            ratings = new MutableLiveData();

        ratings.getValue().add(rating);
    }

    public void getRatings(ITipDTO tip){
        //
    }

    public MutableLiveData<List<IRatingDTO>> getRatingsObject(){
        if(ratings == null)
            ratings = new MutableLiveData();

        return ratings;
    }








    //######    Service calls     ######
    public void startTipQuery(){
        if (dao == null){
            Log.e(TAG, "startTipQuery: dao is null");
            return;
        }

        Log.d(TAG, "startTipQuery: ");
        dao.queryByLocation(getCurrentWindowLocation(), getCurrentWindowZoom(), getTipList());
    }


    // todo fix this . cannot have input of FireBase DAO, UI dosent know it.
    public void createTip() {
        if (dao == null){
            Log.e(TAG, "createTip: dao is null");
            return;
        }

        Log.d(TAG, "createTip: \n" + tipCreateObject.getValue());
        if (tipCreateObject != null) {
            if (tipCreateObject.getValue() != null) {
                ITipDTO dto = tipCreateObject.getValue();
                if (dto.getAuthor() != null) {
                    dao.createTip(dto);
                } else {
                    Log.e(TAG, "createTip: tipCreateObject.getAuthor is null");
                }
            } else {
                Log.e(TAG, "createTip: tipCreateObject.getValue() is null");
            }
        } else {
            Log.e(TAG, "createTip: tipCreateObject is null");
        }
    }
}
