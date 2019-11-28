package com.example.p_kontrol.UI.Services;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public interface ITipDTO {





    IUserDTO getAuthor();

    String getMessage();

    void setMessage(String message);

    LatLng getLocation();

    void setLocation(LatLng location);

    int getRating();

    void setRating(int rating);

    Date getCreationDate();

    void setCreationDate(Date date);



}
