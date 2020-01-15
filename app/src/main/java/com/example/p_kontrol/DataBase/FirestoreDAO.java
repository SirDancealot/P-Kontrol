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
import com.google.firebase.firestore.Query;
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
    GeoQuery query;

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

        if (query != null)
            query.setLocation(new GeoPoint(location.latitude, location.longitude), radius);
        else
            query =  geoFirestore.queryAtLocation(new GeoPoint(location.latitude, location.longitude), radius);

        query.addGeoQueryEventListener(new CustomGeoQueryLocation(this, tipList, tips));

    }

    /**
     * Inner class for at kunne udvide den eksisterende GeoQueryLocation primært med en constructor
     */
    private class CustomGeoQueryLocation implements GeoQueryEventListener {
        IDatabase dao;
        MutableLiveData<List<ATipDTO>> tipList;
        CollectionReference collection;

        boolean updateIndividual = false;
        ArrayList<String> documents = new ArrayList<>();

        public CustomGeoQueryLocation(IDatabase dao, MutableLiveData<List<ATipDTO>> tipList, CollectionReference collection) {
            this.dao = dao;
            this.tipList = tipList;
            this.collection = collection;
        }

        @Override
        public void onGeoQueryError(@NotNull Exception e) {
            Log.e(TAG, "onGeoQueryError: An error has occured in querying", e);
        }

        /**
         * Bliver kaldt når alle de allerede eksisterende tips er blevet fundet til at man kan initialisere mange på en gang
         */
        @Override
        public void onGeoQueryReady() {
            updateIndividual = true;
            List<ATipDTO> tips = tipList.getValue();
            List<QuerySnapshot> snapshots = dao.getDocumentList(collection, documents);
            for (QuerySnapshot snapshot : snapshots) {
                tips.addAll(snapshot.toObjects(ATipDTO.class));
            }
            tipList.postValue(tips);
        }

        @Override
        public void onKeyEntered(@NotNull String s, @NotNull GeoPoint geoPoint) {
            if (updateIndividual) {
                //Kode til at opdatere tips løbende skal være her

            } else {
                documents.add(s);
            }
        }

        @Override
        public void onKeyExited(@NotNull String s) {
            List<ATipDTO> tips = tipList.getValue();
            ArrayList<String> documents = new ArrayList<String>();
            documents.add(s);
            List<QuerySnapshot> snapshots = dao.getDocumentList(collection, documents);
            QuerySnapshot snapshot = snapshots.get(0);
            snapshot.toObjects(ATipDTO.class); //find tippet der matcher denne i tips og slet den fra listen og sæt tips på ny i MutableLiveData


        }

        @Override
        public void onKeyMoved(@NotNull String s, @NotNull GeoPoint geoPoint) {
            //Dette er kun nødvendigt at implementere hvis tips positioner kommer til at kunne fløtte sig
        }
    }
}
