package com.example.p_kontrol.UI.ViewModelLiveData;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;

import androidx.core.content.ContextCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.BackendStub;
import com.example.p_kontrol.Backend.IBackend;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.Interfaces.IPVagtDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IRatingDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private String TAG = "ViewModelMaster";
    private FirestoreDAO dao;

    private MutableLiveData<List<ITipDTO>> tipList;
    private MutableLiveData<List<IPVagtDTO>> pVagtList;
    private MutableLiveData<ITipDTO> tipCreateObject;
    private MutableLiveData<LatLng> currentLocation;

    // Map Data.MutableLiveData<
    private MutableLiveData<LatLng> map_WindowLocation;
    private MutableLiveData<Float> map_WindowZoom;
    private MutableLiveData<LatLng> map_currentLocation;


    private MarkerOptions tipPinRegular, tipPinPaid, tipPinAlarm, pVagtPinAlarm, pVagtPinAlarmOld, parkingspot;

    // todo er dette rigtigt? August
    // Write tip
    private ITipDTO currentTip;
    private UserInfoDTO userInfoDTO;

    public LiveDataViewModel(){
        tipList = new MutableLiveData<>(new ArrayList<>());
        pVagtList = new MutableLiveData<>(new ArrayList<>());
        tipCreateObject = new MutableLiveData<>(new TipDTO());
        userInfoDTO = UserInfoDTO.getUserInfoDTO();
    }



    public ITipDTO getCurrentTip() {
        return currentTip;
    }
    public void setCurrentTip(ITipDTO currentTip) {
        this.currentTip = currentTip;
    }

    //Raing
    private MutableLiveData<List<IRatingDTO>> ratings;

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

    public MutableLiveData<List<IPVagtDTO>> getPvagtList() {
        Log.d(TAG, "getPvagtList: " + this);
        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>(new ArrayList<>());
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

    public void createPVagt(PVagtDTO vagt){
        Log.d(TAG, "CreatePVagt: " + this);

        if (pVagtList == null) {
            pVagtList = new MutableLiveData<>(new ArrayList<>());
        }
        dao.createPVagt(vagt);
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
        dao.queryTipByLocation(getCurrentWindowLocation(), getCurrentWindowZoom(), getTipList());
    }

    public void startPVagtQuery() {
        if (dao == null){
            Log.e(TAG, "startTipQuery: dao is null");
            return;
        }

        Log.d(TAG, "startTipQuery: ");
        dao.queryPVagtByLocation(getCurrentLocation(), getCurrentWindowZoom(), getPvagtList());
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
                dto.setAuthor(userInfoDTO.getSimpleUser());
                dto.setCreationDate(new Date());
                dao.createTip(dto);
                //firestoreDAO.createTip(dto);
            } else {
                Log.e(TAG, "createTip: tipCreateObject.getValue() is null");
            }
        } else {
            Log.e(TAG, "createTip: tipCreateObject is null");
        }
    }

    public MarkerOptions getPin(String resourceName, Context context, int dimX, int dimY) {
        /*
            free("map_tip_pin_regular", 354, 512, R.drawable.map_tip_pin_regular),
            paid("map_tip_pin_paid", 368, 527, R.drawable.map_tip_pin_paid),
            alarm("map_tip_pin_alarm", 368, 527, R.drawable.map_tip_pin_alarm),
            pVagt("map_pvagt_pin_alarm", 366, 525, R.drawable.map_pvagt_pin_alarm),
            pVagtOld("map_pvagt_pin_alarmold", 366, 525, R.drawable.map_pvagt_pin_alarmold),
            parkingSpot("map_parkingspot", 382, 230, R.drawable.map_parkingspot);
        */

        int scalingConst = dimY / 100;
        dimX /= scalingConst;
        dimY /= scalingConst;

        switch (resourceName) {
            case "map_tip_pin_regular":
                if (tipPinRegular == null) {
                    Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName()));
                    Bitmap imageBitmap = ((BitmapDrawable)drawable).getBitmap();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, dimX, dimY, false);
                    tipPinRegular = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                }
                return tipPinRegular;
            case "map_tip_pin_paid":
                if (tipPinPaid == null) {
                    Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName()));
                    Bitmap imageBitmap = ((BitmapDrawable)drawable).getBitmap();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, dimX, dimY, false);
                    tipPinPaid = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                }
                return tipPinPaid;
            case "map_tip_pin_alarm":
                if (tipPinAlarm == null) {
                    Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName()));
                    Bitmap imageBitmap = ((BitmapDrawable)drawable).getBitmap();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, dimX, dimY, false);
                    tipPinAlarm = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                }
                return tipPinAlarm;
            case "map_pvagt_pin_alarm":
                if (pVagtPinAlarm == null) {
                    Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName()));
                    Bitmap imageBitmap = ((BitmapDrawable)drawable).getBitmap();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, dimX, dimY, false);
                    pVagtPinAlarm = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                }
                return pVagtPinAlarm;
            case "map_pvagt_pin_alarmold":
                if (pVagtPinAlarmOld == null) {
                    Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName()));
                    Bitmap imageBitmap = ((BitmapDrawable)drawable).getBitmap();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, dimX, dimY, false);
                    pVagtPinAlarmOld = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                }
                return pVagtPinAlarmOld;
            case "map_parkingspot":
                if (parkingspot == null) {
                    Drawable drawable = ContextCompat.getDrawable(context, context.getResources().getIdentifier(resourceName,"drawable",context.getPackageName()));
                    Bitmap imageBitmap = ((BitmapDrawable)drawable).getBitmap();
                    Bitmap resizedBitmap = Bitmap.createScaledBitmap(imageBitmap, dimX, dimY, false);
                    parkingspot = new MarkerOptions().icon(BitmapDescriptorFactory.fromBitmap(resizedBitmap));
                }
                return parkingspot;
        }

        return null;
    }
}
