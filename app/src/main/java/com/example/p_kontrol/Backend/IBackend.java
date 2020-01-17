package com.example.p_kontrol.Backend;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IBackend {

    //Tips
    List<ATipDTO> getTips(LatLng location, MutableLiveData<List<ATipDTO>> list);
    void createTip(ATipDTO tip);
    void rateTip(int star, ATipDTO tip);


    void getPVagter(LatLng location, List<PVagtDTO> list);


    //User
    AUserDTO getUser(int id);

    //feedback
    boolean postFeedback(String category, String message);











}
