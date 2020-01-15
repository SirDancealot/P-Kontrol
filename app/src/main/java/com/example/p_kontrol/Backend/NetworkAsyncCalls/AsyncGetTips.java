package com.example.p_kontrol.Backend.NetworkAsyncCalls;


import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class AsyncGetTips extends AsyncTask< Void, Void, Void >{
    private static final String TAG = "AsyncGetTips";
    private IDatabase DAO = new FirestoreDAO();
    private double radius;
    private LatLng location;
    private MutableLiveData<List<ATipDTO>> tipList;

    private IOnTaskComplete onTaskComplete;


    public AsyncGetTips(LatLng location, double radius, MutableLiveData<List<ATipDTO>> tipList){
        this.location = location;
        this.radius = radius;
        this.tipList = tipList;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

            DAO.queryByLocation(location, radius, tipList);


        }catch(Exception e){
            Log.e(TAG, "doInBackground: ", e);
        }

        return null;
    }

    protected void onPostExecute(List<ATipDTO> result) {

    }




}