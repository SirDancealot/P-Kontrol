package com.example.p_kontrol.Backend;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IdbTipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.List;

public interface IDatabase {

    List<ITipDTO> getTipList(LatLng location, double radius);

    void createTip(ITipDTO tip);

    void updateTip(ITipDTO tip);

    AUserDTO getUser(int id);

    boolean checkPAlert(LatLng location);

    /**
     * Method to query for tips at specific location, all found tips will be added to a list
     * @param location the location given in a LatLang for where to query
     * @param radius a {@code double} radius in kilometers for the query
     * @param targetList the target list in which the tips should end
     */
    void queryByLocation(MutableLiveData<LatLng> location, MutableLiveData<Float> radius, MutableLiveData<List<ITipDTO>> targetList);

}
