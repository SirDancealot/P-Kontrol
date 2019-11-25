package com.example.p_kontrol.Backend;

import com.example.p_kontrol.UI.Services.IBackend;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class BackendController implements IBackend {

    double radius;

    
    @Override
    public List<ITipDTO> getTips(LatLng location) {
        return null;
    }

    @Override
    public void createTip(ITipDTO tip) {
        if(tip.getMessage() == ""){

        }

    }

    @Override
    public void rateTip(int star, ITipDTO tip) {

    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }

    @Override
    public boolean isNewTipAvalible() {
        return false;
    }

    @Override
    public IUserDTO getUser(int id) {
        return null;
    }
}
