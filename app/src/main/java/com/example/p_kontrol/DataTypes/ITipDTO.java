package com.example.p_kontrol.DataTypes;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public interface ITipDTO {

    IUserDTO getAuthor();

    void setAuthor(IUserDTO author);

    String getMessage();

    void setMessage(String message);

    GeoPoint getL();

    void setL(GeoPoint location);

    int getRating();

    void setRating(int rating);

    Date getCreationDate();

    void setCreationDate(Date date);

    String getG();

    void setG(String g);



}
