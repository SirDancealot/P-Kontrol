package com.example.p_kontrol.Backend.NetworkAsyncCalls;

import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class AsyncGetPVagter extends AsyncTask<Void, Void, Void> {

    private static final String TAG = "AsyncGetPVagt";
    private IDatabase DAO = new FirestoreDAO();
    private double radius;
    private LatLng location;
    private MutableLiveData<List<PVagtDTO>> PVagtList;


    public AsyncGetPVagter(LatLng location, double radius, MutableLiveData<List<PVagtDTO>> PVagtList) {
        this.location = location;
        this.radius = radius;
        this.PVagtList = PVagtList;
    }

    @Override
    protected Void doInBackground(Void... params) {
        try {

          //  DAO.((PVAGT BY LOCATION))(location, radius, PVagtList );


        } catch (Exception e) {
            Log.e(TAG, "doInBackground: ", e);
        }

        return null;
    }

    protected void onPostExecute(List<ITipDTO> result) {

    }
}

