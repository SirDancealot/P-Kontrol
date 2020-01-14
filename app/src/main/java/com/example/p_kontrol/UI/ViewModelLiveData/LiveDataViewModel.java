package com.example.p_kontrol.UI.ViewModelLiveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private Backend bk = Backend.getBackend();

    private MutableLiveData<List<ATipDTO>> tipList;
    private MutableLiveData<ATipDTO> tipCreateObject;


    public void updateTips(LatLng location){
        bk.getTips(location, tipList);
    }
    public void createTip() { bk.createTip(tipCreateObject.getValue()); }


    //######    Getters     ######

    public LiveData<List<ATipDTO>> getTipList() {
        return tipList;
    }

    public MutableLiveData<ATipDTO> getMutableTipCreateObject() {
        return tipCreateObject;
    }
}
