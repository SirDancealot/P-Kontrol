package com.example.p_kontrol.DataTypes;

import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

public interface ITipDTO {

    String getType();
    void setType(String type);

    AUserDTO getAuthor();
    void setAuthor(AUserDTO author);

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

    List<String> getLikers();
    void setLikers(List<String> likers);

    List<String> getDislikers();
    void setDislikers(List<String> dislikers);
}
