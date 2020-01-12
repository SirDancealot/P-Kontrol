package com.example.p_kontrol.Backend;

import android.util.Log;

import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncCreateTip;
import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncGetTips;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
/**
 * @responsibility responsibility to Handle and maintain data;
 *
 * */
public class Backend implements IBackend {
    //TODO make backend handle preferences

    // Android internal
    private String TAG = "Backend";

    // Final data.
    final int TIP_SEARCH_RADIUS = 120;

    // private data
    private List<ITipDTO> dtoList = new LinkedList<>();

    @Override // when needing tips from new location
    public List<ITipDTO> getTips(LatLng location) {

        dtoList = new LinkedList<>();
        AsyncGetTips async = new AsyncGetTips(location, TIP_SEARCH_RADIUS, new IOnTaskComplete(){

            @Override
            public void OnTaskComplete(List<ITipDTO> result) {
                if ( dtoList.addAll( result ) )
                    Log.d(TAG, "OnTaskComplete: successful");
                else
                    Log.w(TAG, "OnTaskComplete: failed", null);
            }

        });
        async.execute();

        return dtoList;
    }

    @Override
    public void createTip(ITipDTO tip) {

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
    public void rateTip(int star, ITipDTO tip) {

    }

    @Override
    public boolean postFeedback(String category, String message) {
        return false; //TODO implement this method
    }

    @Override
    public IUserDTO getUser(int id) {
        return null;
    }




}
