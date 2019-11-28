package com.example.p_kontrol.UI.Services;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public interface ITipDTO {

    IUserDTO getAuthor();
    String getMessage();
    LatLng getLocation();
    int getRating();
    Date getCreationDate();

    void setAuthor(IUserDTO author);
    void setMessage(String message);
    void setLocation(LatLng location);
    void setRating(int rating);
    void setCreationDate(Date creationDate);

}
