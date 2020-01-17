package com.example.p_kontrol.Backend;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface IDatabase {

    List<TipDTO> getTipList(LatLng location, double radius);

    public List<DocumentSnapshot> getDocumentList (CollectionReference collection, List<String> ids);

    void createTip(TipDTO tip);

    void updateTip(TipDTO tip);

    AUserDTO getUser(int id);

    boolean checkPAlert(LatLng location);

    /**
     * Method to query for tips at specific location, all found tips will be added to a list
     * @param location the location given in a LatLang for where to query
     * @param radius a {@code double} radius in kilometers for the query
     * @param targetList the target list in which the tips should end
     */
    void queryByLocation(LatLng location, double radius, MutableLiveData<List<TipDTO>> targetList);

}
