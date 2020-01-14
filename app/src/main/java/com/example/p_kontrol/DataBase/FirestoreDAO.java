package com.example.p_kontrol.DataBase;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataTypes.*;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FirestoreDAO implements IDatabase {
    String TAG = "FirestoreDAO";
    FirebaseFirestore fireDB = FirebaseFirestore.getInstance();
    CollectionReference tips = fireDB.collection("tips");
    GeoFirestore geoFirestore = new GeoFirestore(tips);

    @Override
    public List<QuerySnapshot> getDocumentList (CollectionReference collection, List<String> ids){
        return null;
    }

    @Override
    public List<ATipDTO> getTipList(LatLng location, double radius) {
        Task<QuerySnapshot> query = tips.get();

        try {
             Tasks.await(query);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (query.getException() != null) {
            Log.w(TAG, "getTipList: ", query.getException());
        }

        return new ArrayList<>(
                Objects.requireNonNull( //Makes sure that a null pointer exception is avoided (recommended by IntelliJ)
                    query.getResult()).toObjects(TipDTO.class)

        );
    }

    @Override
    public void createTip(ATipDTO tip) {
        String id = tip.getAuthor().getUserId()+ "-" + System.currentTimeMillis();
        tips.document(id).set(tip)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "createTip: tip \"" + id + "\" added to database"))
                .addOnCompleteListener(task -> {
                    if (task.isCanceled()) {
                        Log.w(TAG, "createTip: Canceled with message: ", task.getException());
                    } else if (!task.isSuccessful()) {
                        Log.w(TAG, "createTip: Failed with message: ", task.getException());
                    }
                })
        ;

    }

    @Override
    public void updateTip(ATipDTO tip) { }

    @Override
    public AUserDTO getUser(int id) {
        return null;
    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }



    @Override
    public void queryByLocation(LatLng location, double radius, MutableLiveData<List<ATipDTO>> tipList) {
        String collection = "tips";

        GeoQuery query =  geoFirestore.queryAtLocation(new GeoPoint(location.latitude, location.longitude), radius);

        query.addGeoQueryEventListener(new GeoQueryEventListener() {
            boolean updateIndividual = false;
            ArrayList<String> documents = new ArrayList<>();
            @Override
            public void onKeyEntered(@NotNull String s, @NotNull GeoPoint geoPoint) {
                Log.d(TAG, "onKeyEntered: Document " + s + " has entered the search area at " + geoPoint.toString());
                if (updateIndividual) {

                } else {
                    documents.add(s);
                }
            }

            @Override
            public void onKeyExited(@NotNull String s) {
                Log.d(TAG, "onKeyExited: Document " + s + " has left the search area");
            }

            @Override
            public void onKeyMoved(@NotNull String s, @NotNull GeoPoint geoPoint) {
                Log.d(TAG, "onKeyMoved: Document " + s + " has moved within the search area to " + geoPoint.toString());
            }

            @Override
            public void onGeoQueryReady() {
                Log.d(TAG, "onGeoQueryReady: all initial data has been loaded");
                updateIndividual = true;
            }

            @Override
            public void onGeoQueryError(@NotNull Exception e) {
                Log.e(TAG, "onGeoQueryError: there was an error with this querry", e);
            }
        });
    }
}
