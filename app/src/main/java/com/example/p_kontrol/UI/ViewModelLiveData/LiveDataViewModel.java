package com.example.p_kontrol.UI.ViewModelLiveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private MutableLiveData<List<ATipDTO>> tipList;

    private Backend bk = Backend.getBackend();

    public void updateTips(LatLng location){
        bk.getTips(location, tipList);
    }

    public LiveData<List<ATipDTO>> getTipList() {
        return tipList;
    }
}
