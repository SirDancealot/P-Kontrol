package com.example.p_kontrol.DataLayer;

import com.example.p_kontrol.TechnicalServices.ITipDTO;
import com.google.firebase.firestore.GeoPoint;

class TipDTO implements ITipDTO {
    private String Author;
    private String msg;
    private GeoPoint loc;

    public TipDTO() {
    }

    public TipDTO(String author, String msg, GeoPoint loc) {
        Author = author;
        this.msg = msg;
        this.loc = loc;
    }

    public void setAuthor(String author) {
        Author = author;
    }

    public void setMessage(String msg) {
        this.msg = msg;
    }

    public void setLocation(GeoPoint loc) {
        this.loc = loc;
    }

    @Override
    public String getAuthor() {
        return Author;
    }

    @Override
    public String getMessage() {
        return msg;
    }

    @Override
    public GeoPoint getLocation() {
        return loc;
    }
}