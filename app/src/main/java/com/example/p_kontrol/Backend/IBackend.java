package com.example.p_kontrol.Backend;

import androidx.lifecycle.MutableLiveData;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IBackend {

    //Tips
    List<TipDTO> getTips(LatLng location, MutableLiveData<List<TipDTO>> list);
    void createTip(TipDTO tip);
    void rateTip(int star, TipDTO tip);


    void getPVagter(LatLng location, MutableLiveData<List<PVagtDTO>> list);

    void createPVagt(PVagtDTO vagt);


    //User
    AUserDTO getUser(int id);

    //feedback
    boolean postFeedback(String category, String message);











}
