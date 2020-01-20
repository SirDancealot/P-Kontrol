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

    /**
     * Retrieves a list of documents by id in from the provided location.
     *
     * @param collection the collection to be searched
     * @param ids a list of the specific documents to be retrieved from the collection
     * @return Returns a list of DocumentSnapshot's for the requested documents
     */
    @Override
    public List<DocumentSnapshot> getDocumentList (CollectionReference collection, @NonNull @NotNull List<String> ids){
        Log.d(TAG, "getDocumentList: begin");
        
        @NotNull @NonNull List<DocumentSnapshot> list = new LinkedList<>();
        LinkedList<Boolean> counter = new LinkedList(); //list used to count number of processed elements and store data about their success. true means successful.
        List<Task<DocumentSnapshot>> taskList = new LinkedList<>();

        //For all documents, retrieve them and add them to a list
        for (String id : ids) {
            taskList.add(collection.document(id).get()
//                    .addOnCompleteListener(
//                        task -> {
//                            Log.d(TAG, "getDocumentList: complete");
//                            if (task.isSuccessful()) {
//                                synchronized (list) {
//                                    list.add(task.getResult());
//                                }
//                            } else {
//                                Log.e(TAG, "getDocumentList: document: " + id, task.getException());
//                            }
//
//
//                        }
//                    )
            )
            ;
        }

        try {
            Tasks.await(
                    Tasks.whenAllComplete(
                            taskList.toArray(new Task[taskList.size()]) //hvorfor er vi på main thread her??????
                    )
            );
            Log.d(TAG, "getDocumentList: got past await");
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        for (Task<DocumentSnapshot> task : taskList) {
            list.add(task.getResult());
        }
        Log.d(TAG, "getDocumentList: return");
        return list;
    }

    @Override
    public List<TipDTO> getTipList(LatLng location, double radius) {
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
    public void createTip(TipDTO tip) {
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
    public void updateTip(TipDTO tip) { }

    @Override
    public AUserDTO getUser(int id) {
        return null;
    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }



    @Override
    public void queryByLocation(LatLng location, double radius, MutableLiveData<List<TipDTO>> tipList) {
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
        MutableLiveData<List<TipDTO>> tipList;
        CollectionReference collection;

        boolean updateIndividual = false; //Boolean describing whether or not to wait for a batch or not
        ArrayList<String> documents = new ArrayList<>();

        public CustomGeoQueryLocation(IDatabase dao, MutableLiveData<List<TipDTO>> tipList, CollectionReference collection) {
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
           /* Log.d(TAG, "onGeoQueryReady: begin");

            updateIndividual = true;
            List<TipDTO> tips = tipList.getValue();
            List<DocumentSnapshot> snapshots = dao.getDocumentList(collection, documents);

            Log.d(TAG, "onGeoQueryReady: start loop");
            int i = 0;
            if (tips != null) {
                for (DocumentSnapshot snapshot : snapshots) {
                    Log.d(TAG, "onGeoQueryReady: in loop at " + i);
                    tips.add(Objects.requireNonNull(snapshot.toObject(TipDTO.class)));
                }
            }

            Log.d(TAG, "onGeoQueryReady: post value");
            tipList.postValue(tips);*/
        }

        @Override
        public void onKeyEntered(@NotNull String s, @NotNull GeoPoint geoPoint) {
            Log.d(TAG, "onKeyEntered: ");
            
            //if (updateIndividual) {
                List<String> list = new ArrayList<>();
                list.add(s);

                tips.document(s).get().addOnSuccessListener(documentSnapshot -> {
                    TipDTO tipDTO = documentSnapshot.toObject(TipDTO.class);

                    List<ATipDTO> temp = tipList.getValue();
                    if (temp != null) {
                        temp.add(tipDTO);
                        tipList.postValue(temp);
                    }
                });



            //} else {
              //  documents.add(s);
            //}
        }

        @Override
        public void onKeyExited(@NotNull String s) {
            Log.d(TAG, "onKeyExited: ");
            
            List<TipDTO> tips = tipList.getValue();

            final TipDTO[] exitedTip = new TipDTO[1];

            collection.document(s)
                    .get()
                    .addOnCompleteListener(
                            task -> {
                                if (task.isSuccessful() && task.getResult() != null) {
                                    exitedTip[0] = task.getResult().toObject(TipDTO.class);
                                }
                            }
                    );

            if (tips != null) {
                for (ATipDTO tip : tips) {
                    if (tip.equals(exitedTip[0]))
                        tips.remove(tip);
                }
            }

            tipList.setValue(tips);

        }

        @Override
        public void onKeyMoved(@NotNull String s, @NotNull GeoPoint geoPoint) {
            Log.d(TAG, "onKeyMoved: ");
            //Dette er kun nødvendigt at implementere hvis tips positioner kommer til at kunne fløtte sig
        }
    }
}
