package com.example.p_kontrol.DataTypes;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public interface ITipDTO {

    IUserDTO getAuthor();
    void setAuthor(IUserDTO author);

    String getMessage();
    void setMessage(String message);

    int getRating();
    void setRating(int rating);

    Date getCreationDate();
    void setCreationDate(Date date);

    // todo setG setL er ikke descriptive nok, beskriv bedre
    String getG();
    void setG(String g);

    GeoPoint getL();
    void setL(GeoPoint location);

    ITipDTO copy();
}
