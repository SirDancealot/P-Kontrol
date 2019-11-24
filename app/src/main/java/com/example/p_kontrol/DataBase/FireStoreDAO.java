package com.example.p_kontrol.DataBase;

import com.example.p_kontrol.Backend.IDatabase;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class FireStoreDAO implements IDatabase {
    @Override
    public List<ITipDTO> getTipList(LatLng location, double radius) {
        return null;
    }

    @Override
    public void createTip(ITipDTO tip) {

    }

    @Override
    public void updateTip(ITipDTO tip) {

    }

    @Override
    public IUserDTO getUser(int id) {
        return null;
    }

    @Override
    public boolean checkPAlert(LatLng location) {
        return false;
    }
}
