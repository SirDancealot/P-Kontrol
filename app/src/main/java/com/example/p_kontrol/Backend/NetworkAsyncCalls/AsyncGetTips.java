package com.example.p_kontrol.Backend.NetworkAsyncCalls;


import android.os.AsyncTask;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.UI.Services.*;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AsyncGetTips extends AsyncTask< Void, Void, List<ITipDTO> >{

    FirestoreDAO DAO = new FirestoreDAO();
    List list = new ArrayList<ITipDTO>();
    double radius;
    LatLng location;

   public AsyncGetTips(LatLng location, double radius){
        this.location = location;
        this.radius = radius;
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

       

    }




}