package com.example.p_kontrol.DataLayer;

import android.os.Build;
import android.os.Handler;
import android.util.Log;

import com.example.p_kontrol.TechnicalServices.ITipDTO;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.firestore.QuerySnapshot;

import org.imperiumlabs.geofirestore.*;
import org.imperiumlabs.geofirestore.listeners.GeoQueryEventListener;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import androidx.annotation.RequiresApi;

public class ExampleDB {
    private FirebaseFirestore db;
    private GeoFirestore geoDb;
    private String TAG = "DataLayer";

    public ExampleDB(){
        db = FirebaseFirestore.getInstance();
        geoDb = new GeoFirestore(db.collection("tips"));
    }

    void postTip(ITipDTO tip){
        String id = tip.getAuthor() + "-" + System.currentTimeMillis(); // TODO: make better algorithm to generate id's
        db.collection(geoDb.getCollectionReference().getPath()).document(id)
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

        Log.d(TAG, "postTip: document id = " + (ref != null ? ref.getId() : "null"));

    }

    /**
     *
     * @param pos the center of the radius of the query
     * @param radius the radius of the search in kilometers
     * @return returns an array of all the tips found
     */
    List<ITipDTO> queryTips(GeoPoint pos, double radius) {
        QuerySnapshot result;
        result = db.collection("tips")
                .get()
                .getResult()
        ;

        assert result != null;
        return result.toObjects(ITipDTO.class);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void testDB(){
        ITipDTO tip1 = new TipDTO("Bob", "This place has free parking... i promise >:)", new GeoPoint(22, 44));
        ITipDTO tip2 = new TipDTO("Eva", "This spot is always free, though you risk getting a little wet", new GeoPoint(55, 10));
        postTip(tip1);
        postTip(tip2);

        queryTips(new GeoPoint(0,0), 10)
                .forEach(System.out::println);
    }

    private class TipDTO implements ITipDTO {
        String Author;
        String msg;
        GeoPoint loc;

        public TipDTO(String author, String msg, GeoPoint loc) {
            Author = author;
            this.msg = msg;
            this.loc = loc;
        }

        @Override
        public String getAuthor() {
            return Author;
        }

        @Override
        public String getMessage() {
            return msg;
        }

        @Override
        public GeoPoint getLocation() {
            return loc;
        }
    }
}
