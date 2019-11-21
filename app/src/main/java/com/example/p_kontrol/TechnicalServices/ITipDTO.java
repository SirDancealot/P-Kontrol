package com.example.p_kontrol.TechnicalServices;


import com.google.firebase.firestore.GeoPoint;

public interface ITipDTO {

    String getAuthor();
    String getMessage();
    GeoPoint getLocation();


}
