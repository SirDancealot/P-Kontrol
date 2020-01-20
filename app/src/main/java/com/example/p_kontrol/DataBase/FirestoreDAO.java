package com.example.p_kontrol.DataBase;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Binder;
import android.os.IBinder;
import android.util.AtomicFile;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataTypes.*;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IdbTipDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;
import org.imperiumlabs.geofirestore.GeoQuery;
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class FirestoreDAO extends Service implements IDatabase {
    String TAG = "FirestoreDAO";
    FirebaseFirestore fireDB = FirebaseFirestore.getInstance();
    CollectionReference tips = fireDB.collection("tips");
    GeoFirestore geoFirestore = new GeoFirestore(tips);
    GeoQuery query;
    private final IBinder daoBinder = new DAOBinder();

    public class DAOBinder extends Binder {
        public FirestoreDAO getService() {
            return FirestoreDAO.this;
        }
    }

    @Override
    public List<ITipDTO> getTipList(LatLng location, double radius) {
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
    public void createTip(ITipDTO tip) {
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
    public void updateTip(ITipDTO tip) { }

    @Override
    public AUserDTO getUser(int id) {
        return null;
    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }



    @Override
    public void queryByLocation(LatLng location, double radius, MutableLiveData<List<ITipDTO>> tipList) {
        Log.d(TAG, "queryByLocation: " + location);
        if (query != null)
            query.setLocation(new GeoPoint(location.latitude, location.longitude), radius);
        else
            query =  geoFirestore.queryAtLocation(new GeoPoint(location.latitude, location.longitude), radius);

        query.addGeoQueryEventListener(new CustomGeoQueryLocation(this, tipList, tips));
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return daoBinder;
    }

    /**
     * Inner class for at kunne udvide den eksisterende GeoQueryLocation primært med en constructor
     */
    private class CustomGeoQueryLocation implements GeoQueryEventListener {
        String TAG = "GeoFirestore";

        IDatabase dao;
        MutableLiveData<List<ITipDTO>> tipList;
        CollectionReference collection;


        public CustomGeoQueryLocation(IDatabase dao, MutableLiveData<List<ITipDTO>> tipList, CollectionReference collection) {
            this.dao = dao;
            this.tipList = tipList;
            this.collection = collection;
        }

        @Override
        public void onGeoQueryError(@NotNull Exception e) {
            Log.e(TAG, "onGeoQueryError: An error has occurred in querying", e);
        }

        /**
         * Bliver kaldt når alle de allerede eksisterende tips er blevet fundet til at man kan initialisere mange på en gang
         */
        @Override
        public void onGeoQueryReady() {
           Log.d(TAG, "all data now found");

        }


        /**
         * This method is called if a document enters the queried area
         *
         * @param s The key of the document that was moved
         * @param geoPoint The location of the document
         */
        @Override
        public void onKeyEntered(@NotNull String s, @NotNull GeoPoint geoPoint) {
            Log.d(TAG, "onKeyEntered: ");

                tips.document(s).get().addOnSuccessListener(documentSnapshot -> {
                    ITipDTO tipDTO = documentSnapshot.toObject(TipDTO.class);

                List<ITipDTO> temp = tipList.getValue();//TODO make this thread safe
                if (temp != null) {
                    temp.add(tipDTO);
                    tipList.postValue(temp);
                }
            });

        }


        /**
         * Called when a document that was queried leaves the range
         *
         * @param s The key of the document that was moved
         */
        @Override
        public void onKeyExited(@NotNull String s) { // todo fix null pointer exceptions that araise whenever this is called
            Log.d(TAG, "onKeyExited: ");
            
            List<ITipDTO> tips = tipList.getValue();//TODO make this thread safe

            final ITipDTO[] exitedTip = new TipDTO[1];

            collection.document(s)
                    .get()
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    exitedTip[0] = task.getResult().toObject(TipDTO.class);
                                }

                                if (tips != null) {
                                    for (ITipDTO tip : tips) {
                                        if (tip.equals(exitedTip[0]))
                                            tips.remove(tip);
                                    }
                                }

                                tipList.setValue(tips);
                            }
                    );

        }

        /**
         * This method is called when a document in range of the search is moved
         *
         * @param s The key of the document that was moved
         * @param geoPoint The location of the document
         */
        @Override
        public void onKeyMoved(@NotNull String s, @NotNull GeoPoint geoPoint) {
            Log.d(TAG, "onKeyMoved: ");
            //Dette er kun nødvendigt at implementere hvis tips positioner kommer til at kunne fløtte sig
        }
    }
}
