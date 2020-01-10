package com.example.p_kontrol.Backend;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.IUserDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public interface IDatabase {

    List<ITipDTO> getTipList(LatLng location, double radius);

    public List<QuerySnapshot> getDocumentList (CollectionReference collection, List<String> ids);

    void createTip(ITipDTO tip);

    void updateTip(ITipDTO tip);

    IUserDTO getUser(int id);

    boolean checkPAlert(LatLng location);





}
