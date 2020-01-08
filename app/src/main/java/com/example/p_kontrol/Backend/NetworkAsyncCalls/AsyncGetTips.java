package com.example.p_kontrol.Backend.NetworkAsyncCalls;


import android.os.AsyncTask;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AsyncGetTips extends AsyncTask< Void, Void, List<ITipDTO> >{

    private IDatabase DAO = new FirestoreDAO();
    private List list = new ArrayList<ITipDTO>();
    private double radius;
    private LatLng location;

    private IOnTaskComplete onTaskComplete;


    public AsyncGetTips(LatLng location, double radius, IOnTaskComplete onComplete){
        this.location = location;
        this.radius = radius;
        onTaskComplete = onComplete;
    }

    @Override
    protected List<ITipDTO> doInBackground(Void... params) {
        try {
            list = DAO.getTipList(location, radius);


        }catch(Exception e){
            e.printStackTrace();
        }
        return list;

    }

    protected void onPostExecute(List<ITipDTO> result) {
        onTaskComplete.OnTaskComplete(result);

    }




}