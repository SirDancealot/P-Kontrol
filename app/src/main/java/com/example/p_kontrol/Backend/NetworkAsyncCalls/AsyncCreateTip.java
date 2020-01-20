package com.example.p_kontrol.Backend.NetworkAsyncCalls;

import android.os.AsyncTask;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;

import java.util.List;

public class AsyncCreateTip extends AsyncTask<ITipDTO, Void, Void> {

    FirestoreDAO DAO = new FirestoreDAO();



    @Override
    protected Void doInBackground(ITipDTO... params) {
        try {
            DAO.createTip(params[0]);


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    protected void onPostExecute(List<ITipDTO> result) {




    }
}
