package com.example.p_kontrol.Backend;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncCreateTip;
import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncGetTips;
import com.example.p_kontrol.DataTypes.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.List;
/**
 * @responsibility responsibility to Handle and maintain data;
 *
 * */
public class Backend implements IBackend {

    //singleton init

    private static Backend b = null;
    private Backend(){};

    public static Backend getBackend(){
        if(b==null){
            b = new Backend();
            return b;
        }else{
            return b;
        }
    }




    //TODO make backend handle preferences

    List<ATipDTO> aTipDTOS;

    // Android internal
    private String TAG = "Backend";

    // Final data.
    final int TIP_SEARCH_RADIUS = 120;

    // private data
    private List<ATipDTO> dtoList = new LinkedList<>();

    @Override // when needing tips from new location
    public List<ATipDTO> getTips(LatLng location, MutableLiveData<List<ATipDTO>> tipList) {
        // todo rethink getTips and updateTipsFromDB
        updateTipsFromDB( location, tipList);
         return  aTipDTOS;
    }
    private void updateTipsFromDB(LatLng location, MutableLiveData<List<ATipDTO>> tipList) {
        dtoList = new LinkedList<>();
        AsyncGetTips async = new AsyncGetTips(location, TIP_SEARCH_RADIUS, tipList);
        async.execute();
    }

    @Override
    public void createTip(ATipDTO tip) {

        UserInfoDTO userInfoDTO = UserInfoDTO.getUserInfoDTO();
        if(userInfoDTO.getToken() != null){

        }

        // todo do this.
        dtoList.add(tip);
        Log.i(TAG, "createTip: ");
        AsyncCreateTip asyncC = new AsyncCreateTip();
        asyncC.execute(tip);
    }

    @Override
    public void rateTip(int star, ATipDTO tip) {

    }

    @Override
    public boolean postFeedback(String category, String message) {
        return false; //TODO implement this method
    }

    @Override
    public AUserDTO getUser(int id) {
        return null;
    }




}
