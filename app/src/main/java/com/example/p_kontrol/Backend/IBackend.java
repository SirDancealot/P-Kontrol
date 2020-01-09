package com.example.p_kontrol.Backend;

import com.example.p_kontrol.Backend.IOnTaskComplete;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public interface IBackend {


    //Tips

    List<ITipDTO> getTips(LatLng location, double radius);

     void createTip(ITipDTO tip);

     void rateTip(int star, ITipDTO tip);

     boolean checkPAlert(LatLng location);

     boolean isNewTipAvalible();


     //User

    IUserDTO getUser(int id);












}
