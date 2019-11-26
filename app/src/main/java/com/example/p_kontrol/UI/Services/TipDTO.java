package com.example.p_kontrol.UI.Services;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public class TipDTO implements ITipDTO {

    private UserDTO author;
    private String message;
    private GeoPoint location;
    private int rating;
    private Date creationDate;


    public TipDTO(String message, int rating, UserDTO author, GeoPoint location, Date creationDate){
        this.author = author;
        this.rating = rating;
        this.location = location;
        this.creationDate = creationDate;
    }


    public void setMessage(String message) {
        this.message = message;
    }

    public void setLocation(GeoPoint location) {
        this.location = location;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public IUserDTO getAuthor() {
        return author;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public LatLng getLocation() {
        if (location != null)
            return new LatLng(location.getLatitude(), location.getLongitude());
        else
            return null;
    }

    @Override
    public void setLocation(LatLng location) {

    }

    @Override
    public int getRating() {
        return rating;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }

    @Override
    public String toString() {
        return "TipDTO{" +
                "author='" + author + '\'' +
                ", messege='" + message + '\'' +
                ", location=" + location +
                '}';
    }

    public void setAuthor(UserDTO author) {
        this.author = author;
    }
}
