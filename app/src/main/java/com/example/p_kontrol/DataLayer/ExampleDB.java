package com.example.p_kontrol.DataLayer;

import android.os.Build;
import android.util.Log;

import com.example.p_kontrol.TechnicalServices.ITipDTO;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.GeoFirestore;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import androidx.annotation.RequiresApi;
import androidx.lifecycle.MutableLiveData;

public class ExampleDB {
    private FirebaseFirestore db;
    private GeoFirestore geoDb;
    private String TAG = "DataLayer";

    public ExampleDB(){
        db = FirebaseFirestore.getInstance();
        geoDb = new GeoFirestore(db.collection("tips"));
        Log.d(TAG, "ExampleDB: instantiated");
    }

    void postTip(ITipDTO tip){
        String id = tip.getAuthor() + "-" + System.currentTimeMillis(); // TODO: make better algorithm to generate id's


        db.collection("tips").document(id)
                .set(tip)
                .addOnCompleteListener(
                        (task) -> {
                            if (task.isSuccessful()) {
                                Log.d(TAG, "postTip: tip saved to database successfully");
                            } else {
                                Log.w(TAG, "postTip: failed to write data", task.getException());
                                throw new RuntimeException("postTip: failed to write data", task.getException());
                            }
                        }

                )
        ;

    }

    /**
     *
     * @param pos the center of the radius of the query
     * @param radius the radius of the search in kilometers
     * @return returns an array of all the tips found
     */
    List<ITipDTO> queryTips(GeoPoint pos, double radius) {
        // TODO: return a MutableLiveData<List<TipDTO>> and update it with a collection.addSnapshotListener
        // the listener can then be used to update the live data.

        List<ITipDTO> list = new ArrayList<>();

        Task<QuerySnapshot> task = db.collection("tips").get();

        try {
            list.addAll(Tasks.await(task).toObjects(TipDTO.class));
        } catch (ExecutionException | InterruptedException | NullPointerException e ) {
            e.printStackTrace();
        }


        return list;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void testDB(){
        ITipDTO tip1 = new TipDTO("Bob", "This place has free parking... i promise >:)", new GeoPoint(22, 44));
        ITipDTO tip2 = new TipDTO("Eva", "This spot is always free, though you risk getting a little wet ;)", new GeoPoint(55, 10));
        postTip(tip1);
        postTip(tip2);

        List<ITipDTO> tips = queryTips(new GeoPoint(0,0), 10);



            Log.d(TAG, "testDB: tips queried. number of tips: " + tips.size());
            for (ITipDTO t : tips)
                Log.d(TAG, "Author: " + t.getAuthor() + "\t\tMessage: " + t.getMessage());


    }


}
