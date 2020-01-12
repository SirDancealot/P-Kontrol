package com.example.p_kontrol.Backend.NetworkAsyncCalls;

import android.os.AsyncTask;

import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.ATipDTO;

import java.util.List;

public class AsyncCreateTip extends AsyncTask<ATipDTO, Void, Void> {

    FirestoreDAO DAO = new FirestoreDAO();



    @Override
    protected Void doInBackground(ATipDTO... params) {
        try {
            DAO.createTip(params[0]);


        }catch(Exception e){
            e.printStackTrace();
        }
        return null;

    }

    protected void onPostExecute(List<ATipDTO> result) {




    }
}
