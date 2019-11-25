package com.example.p_kontrol.DataBase;

import android.util.Log;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class FireStoreDAO implements IDatabase {
    String TAG = "FirestoreDAO";
    FirebaseFirestore fireDB = FirebaseFirestore.getInstance();

    @Override
    public List<ITipDTO> getTipList(LatLng location, double radius) {
        Task<com.google.firebase.firestore.QuerySnapshot> query =
            fireDB.collection("tips").get();

        try {
             Tasks.await(query);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (query.getException() != null) {
            Log.w(TAG, "getTipList: ", query.getException());
        }

        return new ArrayList<>(query.getResult().toObjects(TipDTO.class));
    }

    @Override
    public void createTip(ITipDTO tip) {

    }

    @Override
    public void updateTip(ITipDTO tip) {

    }

    @Override
    public IUserDTO getUser(int id) {
        return null;
    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }
}
