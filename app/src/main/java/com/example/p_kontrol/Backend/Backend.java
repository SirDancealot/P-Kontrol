package com.example.p_kontrol.Backend;

import android.os.AsyncTask;

import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncCreateTip;
import com.example.p_kontrol.Backend.NetworkAsyncCalls.AsyncGetTips;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.UI.Services.IBackend;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Backend implements IBackend {

    boolean updating;
    boolean downloading;

    double radius;


    public List<ITipDTO> getTips(LatLng location, double radius) {


        //maps ref need to go into async


        AsyncGetTips async = new AsyncGetTips(location, radius);
        async.execute();




        return null;
    }

    @Override
    public void createTip(ITipDTO tip) {

        AsyncCreateTip asyncC = new AsyncCreateTip();
        asyncC.execute(tip);

    }

    @Override
    public void rateTip(int star, ITipDTO tip) {

    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }

    @Override
    public boolean isNewTipAvalible() {
        return false;
    }

    @Override
    public IUserDTO getUser(int id) {
        return null;
    }




}
