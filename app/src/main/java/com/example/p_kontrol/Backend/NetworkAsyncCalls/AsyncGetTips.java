package com.example.p_kontrol.Backend.NetworkAsyncCalls;


import android.os.AsyncTask;
import android.util.Log;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AsyncGetTips extends AsyncTask< Void, Void, List<ATipDTO> >{
    private static final String TAG = "AsyncGetTips";
    private IDatabase DAO = new FirestoreDAO();
    private List list = new ArrayList<ATipDTO>();
    private double radius;
    private LatLng location;

    private IOnTaskComplete onTaskComplete;


    public AsyncGetTips(LatLng location, double radius, IOnTaskComplete onComplete){
        this.location = location;
        this.radius = radius;
        onTaskComplete = onComplete;
    }

    @Override
    protected List<ATipDTO> doInBackground(Void... params) {
        try {
            list = DAO.getTipList(location, radius);


        }catch(Exception e){
            Log.e(TAG, "doInBackground: ", e);
        }
        return list;

    }

    protected void onPostExecute(List<ATipDTO> result) {
        onTaskComplete.OnTaskComplete(result);

    }




}