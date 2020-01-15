package com.example.p_kontrol.UI.ViewModelLiveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private MutableLiveData<List<ATipDTO>> tipList;
    private MutableLiveData<List<PVagtDTO>> pVagtList;
    private MutableLiveData<ATipDTO> tipCreateObject;

    private Backend bk = Backend.getBackend();



    public void updateTips(LatLng location){
        bk.getTips(location, tipList);
    }

    public void createTip() { bk.createTip(tipCreateObject.getValue()); }

    public void updatePVagter(LatLng location){
        bk.getPVagter(location, pVagtList);
    }


    //######    Getters     ######

    public LiveData<List<ATipDTO>> getTipList() {
        if (tipList == null) {
            tipList = new MutableLiveData<>();
        }

        return tipList;
    }

    public LiveData<List<PVagtDTO>> getPvagtList() {
        if (pVagtList == null)
            pVagtList = new MutableLiveData<>();

        return pVagtList;
    }

    public MutableLiveData<ATipDTO> getMutableTipCreateObject() {
        if (tipCreateObject == null) {
            tipCreateObject = new MutableLiveData<>();
        }

        return tipCreateObject;
    }
}
