package com.example.p_kontrol.Backend;

import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IBackend {

    //Tips
    List<ATipDTO> getTips(LatLng location);
    void createTip(ATipDTO tip);
    void rateTip(int star, ATipDTO tip);

    //User
    AUserDTO getUser(int id);

    //feedback
    boolean postFeedback(String category, String message);











}
