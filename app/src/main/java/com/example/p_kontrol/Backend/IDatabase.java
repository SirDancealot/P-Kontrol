package com.example.p_kontrol.Backend;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IDatabase {

    List<ITipDTO> getTipList(LatLng location, double radius);

    void createTip(ITipDTO tip);

    void updateTip(ITipDTO tip);

    IUserDTO getUser(int id);

    boolean checkPAlert(LatLng location);





}
