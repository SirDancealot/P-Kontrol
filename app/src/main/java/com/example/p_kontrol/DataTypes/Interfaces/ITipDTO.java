package com.example.p_kontrol.DataTypes.Interfaces;

import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;
import java.util.List;

public interface ITipDTO {

    int getType();
    void setType(int type);

    UserInfoDTO getAuthor();
    void setAuthor(UserInfoDTO author);

    String getMessage();
    void setMessage(String message);

    int getRating();
    void setRating(int rating);

    Date getCreationDate();
    void setCreationDate(Date date);

    String getG();
    void setG(String g);

    GeoPoint getL();
    void setL(GeoPoint location);

    int getLikers();
    void setLikers(int likers);

    int getDislikers();
    void setDislikers(int dislikers);

    String getDocId();
    void setDocId(String id);
}
