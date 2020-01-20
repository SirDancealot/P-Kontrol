package com.example.p_kontrol.DataBase;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.DataTypes.*;
import com.example.p_kontrol.DataTypes.Interfaces.IPVagtDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
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

public class FirestoreDAO extends Service implements IDatabase {
    String TAG = "FirestoreDAO";
    FirebaseFirestore fireDB = FirebaseFirestore.getInstance();
    CollectionReference tips = fireDB.collection("tips");
    CollectionReference pVagter = fireDB.collection("pvagter");
    GeoFirestore geoFirestore = new GeoFirestore(tips);
    GeoQuery tipQuery, pVagtQuery;
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
                });
    }

    @Override
    public void createPVagt(IPVagtDTO pVagt) {
        String id = pVagt.getL().toString() + "-" + System.currentTimeMillis();
        pVagter.document(id).set(pVagt)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "createTip: tip \"" + id + "\" added to database"))
                .addOnCompleteListener(task -> {
                    if (task.isCanceled()) {
                        Log.w(TAG, "createTip: Canceled with message: ", task.getException());
                    } else if (!task.isSuccessful()) {
                        Log.w(TAG, "createTip: Failed with message: ", task.getException());
                    }
                });
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
    public void queryTipByLocation(final MutableLiveData<LatLng> location, final MutableLiveData<Float> radius, final MutableLiveData<List<ITipDTO>> tipList) {
        Log.d(TAG, "queryTipByLocation: " + location);
        if (tipQuery != null)
            //tipQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), radius.getValue());
            tipQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), 10f);
        else {
            //tipQuery =  geoFirestore.queryAtLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), radius.getValue());
            tipQuery =  geoFirestore.queryAtLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), 10f);
            tipQuery.addGeoQueryEventListener(new CustomGeoQueryLocation<>(this, tipList, tips, TipDTO.class));
        }

        location.observeForever(local -> {
            //tipQuery.setLocation(new GeoPoint(local.latitude, local.longitude), radius.getValue());
            if (radius.getValue() >= 12.7f)
                tipQuery.setLocation(new GeoPoint(local.latitude, local.longitude), 10f);
            else
                tipQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), 0f);
        });

        radius.observeForever(rad -> {
            //tipQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), rad);
            if (rad >= 12.8f)
                tipQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), 10f);
            else
                tipQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), 0f);

        });
    }

    @Override
    public void queryPVagtByLocation(final MutableLiveData<LatLng> location, final MutableLiveData<Float> radius, final MutableLiveData<List<IPVagtDTO>> PVagtList) {
        if (pVagtQuery != null)
            pVagtQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), radius.getValue());
        else {
            pVagtQuery =  geoFirestore.queryAtLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), radius.getValue());
            pVagtQuery.addGeoQueryEventListener(new CustomGeoQueryLocation<>(this, PVagtList, pVagter, PVagtDTO.class));
        }

        location.observeForever(local -> {
            pVagtQuery.setLocation(new GeoPoint(local.latitude, local.longitude), 6000f /*radius.getValue()*/);
        });

        radius.observeForever(rad -> {
            pVagtQuery.setLocation(new GeoPoint(location.getValue().latitude, location.getValue().longitude), 6000f /*rad*/);
        });
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return daoBinder;
    }

    /**
     * Inner class for at kunne udvide den eksisterende GeoQueryLocation primært med en constructor
     */
    private class CustomGeoQueryLocation<I, T extends I> implements GeoQueryEventListener {
        String TAG = "GeoFirestore";

        IDatabase dao;
        MutableLiveData<List<I>> targetList;
        CollectionReference collection;
        final Class<T> typeClass;


        public CustomGeoQueryLocation(IDatabase dao, MutableLiveData<List<I>> targetList, CollectionReference collection, Class<T> typeClass) {
            this.dao = dao;
            this.targetList = targetList;
            this.collection = collection;
            this.typeClass = typeClass;
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
            Log.d(TAG, "onKeyEntered: " + s);

                List<String> list = new ArrayList<>();
                list.add(s);

                collection.document(s).get().addOnSuccessListener(documentSnapshot -> {
                    T DTO = documentSnapshot.toObject(typeClass);

                List<I> temp = targetList.getValue();//TODO make this thread safe
                if (temp != null) {
                    temp.add(DTO);
                    targetList.postValue(temp);
                }
            });

        }


        /**
         * Called when a document that was queried leaves the range
         *
         * @param s The key of the document that was moved
         */
        @Override
        public void onKeyExited(@NotNull String s) {
            Log.d(TAG, "onKeyExited: " + s);

            List<I> target = new ArrayList<>(targetList.getValue());

            collection.document(s)
                    .get()
                    .addOnCompleteListener(
                            task -> {
                                TipDTO exitedT = null;
                                if (task.isSuccessful() && task.getResult() != null) {
                                    exitedT = task.getResult().toObject(TipDTO.class);
                                }
                                List<I> removeList = new ArrayList<>();
                                for (I t : target) {
                                    if (t.equals(exitedT))
                                        removeList.add(t);
                                }

                                List<I> finalList = targetList.getValue();
                                finalList.removeAll(removeList);
                                targetList.setValue(finalList);
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
