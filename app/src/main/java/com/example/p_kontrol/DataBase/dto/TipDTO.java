package com.example.p_kontrol.DataBase.dto;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;

public class TipDTO {

    private int tipId;
    private String author;
    private String messege;
    private LatLng location;      //Dette er måske ikke den rigtige type lokation

    public int getTipId() {
        return tipId;
    }

    public void setTipId(int tipId) {
        this.tipId = tipId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "TipDTO{" +
                "author='" + author + '\'' +
                ", messege='" + messege + '\'' +
                ", location=" + location +
                '}';
    }
}
