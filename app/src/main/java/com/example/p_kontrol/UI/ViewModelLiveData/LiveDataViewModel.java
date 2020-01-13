package com.example.p_kontrol.UI.ViewModelLiveData;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.p_kontrol.Backend.Backend;
import java.util.List;

public class LiveDataViewModel extends ViewModel {

    private MutableLiveData<List> tipList;

    //private Backend bk = Backend.getBack();

    public void updateTips(){
        //bk.getTips();

    }

    public LiveData<List> getTipList() {
        return tipList;
    }
}
