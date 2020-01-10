package com.example.p_kontrol.DataTypes;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.core.GeoHash;

import java.util.Date;

public class TipDTO implements ITipDTO {



    private IUserDTO author;
    private String message;
    private int rating;
    private Date creationDate;
    private String g; //location geohash
    private GeoPoint l; //location


    public TipDTO(){}

    public TipDTO(IUserDTO author, String message, int rating, Date creationDate, GeoPoint l) {
        this.author = author;
        this.message = message;
        this.rating = rating;
        this.creationDate = creationDate;
        this.g = new GeoHash(l.getLatitude(), l.getLongitude()).getGeoHashString();
        this.l = l;
    }

    @Override
    public IUserDTO getAuthor() {
        return author;
    }

    @Override
    public void setAuthor(IUserDTO author) {
        this.author = author;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getG() {
        return g;
    }

    public void setG(String g) {
        this.g = g;
    }

    public GeoPoint getL() {
        return l;
    }

    public void setL(GeoPoint l) {
        this.l = l;
    }

    public TipDTO copySelf(){
        TipDTO tipDTO = new TipDTO(author, message, rating, creationDate, l);
        return tipDTO;
    }

    //todo implement this in the Interface.
    public TipDTO copy(){
        TipDTO newDTO = new TipDTO();
        newDTO.setAuthor(author);
        newDTO.setCreationDate(creationDate);
        newDTO.setL(l);
        newDTO.setG(g);
        newDTO.setMessage(message);
        newDTO.setRating(rating);
        return newDTO;
    }



    @Override
    public String toString() {
        return "TipDTO{" +
                "author='" + author + '\'' +
                ", messege='" + message + '\'' +
                ", location=" + l +
                '}';
    }
}
