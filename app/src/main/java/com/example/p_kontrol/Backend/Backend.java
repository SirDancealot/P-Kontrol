package com.example.p_kontrol.Backend;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncCreateTip;
import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncGetTips;
import com.example.p_kontrol.DataTypes.*;
import com.example.p_kontrol.DataTypes.Interfaces.IPVagtDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
/**
 * @responsibility responsibility to Handle and maintain data;
 *
 * */
public class Backend implements IBackend {

    //singleton init

    private static Backend b = null;
    private Backend(){
    }

    public static Backend getBackend(){
        if(b==null){
            b = new Backend();
            return b;
        }else{
            return b;
        }
    }




    //TODO make backend handle preferences

    List<ITipDTO> tipDTOS;

    // Android internal
    private String TAG = "Backend";

    // Final data.
    final int TIP_SEARCH_RADIUS = 120;
    final int PVAGT_SEARCH_RADIUS = 120;

    // private data
    private List<ITipDTO> dtoList = new LinkedList<>();

    @Override // when needing tips from new location
    public List<ITipDTO> getTips(LatLng location, MutableLiveData<List<ITipDTO>> tipList) {
        // todo rethink getTips and updateTipsFromDB
        updateTipsFromDB( location, tipList);
         return tipDTOS;
    }
    private void updateTipsFromDB(LatLng location, MutableLiveData<List<ITipDTO>> tipList) {
        dtoList = new LinkedList<>();
        AsyncGetTips async = new AsyncGetTips(location, TIP_SEARCH_RADIUS, tipList);
        async.execute();
    }

    //Get P vagter from parking location
    public void getPVagter(LatLng location, MutableLiveData<List<IPVagtDTO>> pVagtList){

      //  AsyncGetPVagter asyncPVagt = new AsyncGetPVagter(location, PVAGT_SEARCH_RADIUS, pVagtList);
      //  asyncPVagt.execute();
    }

    public void createPVagt(PVagtDTO vagt){

    }


    @Override
    public void createTip(ITipDTO tip) {
        Log.d(TAG, "createTip: ");
        UserInfoDTO userInfoDTO = UserFactory.getFactory().getDto();

        tip.setAuthor(userInfoDTO);
        tip.setCreationDate(new Date());
        // todo do this.
        dtoList.add(tip);
        Log.i(TAG, "createTip: ");
        AsyncCreateTip asyncC = new AsyncCreateTip();
        asyncC.execute(tip);
    }

    @Override
    public void rateTip(int star, ITipDTO tip) {

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
