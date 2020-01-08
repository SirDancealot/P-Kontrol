package com.example.p_kontrol.DataTypes;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public interface ITipDTO {





    IUserDTO getAuthor();

    void setAuthor(IUserDTO author);

    String getMessage();

    void setMessage(String message);

    LatLng getLocation();

    void setLocation(LatLng location);

    int getRating();

    void setRating(int rating);

    Date getCreationDate();

    void setCreationDate(Date date);



}
