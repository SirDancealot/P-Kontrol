package com.example.p_kontrol.UI.Services;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public interface ITipDTO {

    IUserDTO getAuthor();

    String getMessage();

    LatLng getLocation();

    int getRating();

    Date getCreationDate();




}
