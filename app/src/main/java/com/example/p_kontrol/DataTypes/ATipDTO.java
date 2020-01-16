package com.example.p_kontrol.DataTypes;

import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.core.GeoHash;

import java.util.Date;
import java.util.Objects;

public class ATipDTO implements ITipDTO{

    private AUserDTO author;
    private String message;
    private int rating;
    private Date creationDate;
    private String g; //location geohash
    private GeoPoint l; //location

    public ATipDTO(){}

    @Override
    public AUserDTO getAuthor() {
        return author;
    }
    @Override
    public void setAuthor(AUserDTO author) {
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

    // todo MICHAEL RENAME THESE
    @Override
    public String getG() {
        return g;
    }
    @Override
    public void setG(String g) {
        this.g = g;
    }

    @Override
    public GeoPoint getL() {
        return l;
    }
    @Override
    public void setL(GeoPoint l) {
        this.l = l;
        g = new GeoHash(l.getLatitude(), l.getLongitude()).getGeoHashString();
    }


    @Override
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ATipDTO aTipDTO = (ATipDTO) o;
        return rating == aTipDTO.rating &&
                Objects.equals(author, aTipDTO.author) &&
                Objects.equals(message, aTipDTO.message) &&
                Objects.equals(creationDate, aTipDTO.creationDate) &&
                Objects.equals(g, aTipDTO.g) &&
                Objects.equals(l, aTipDTO.l);
    }

    @Override
    public int hashCode() {
        return Objects.hash(author, message, rating, creationDate, g, l);
    }
}
