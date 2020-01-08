package com.example.p_kontrol.Backend;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IDatabase {

    List<ITipDTO> getTipList(LatLng location, double radius);

    IUserDTO getUser(int id);



}
