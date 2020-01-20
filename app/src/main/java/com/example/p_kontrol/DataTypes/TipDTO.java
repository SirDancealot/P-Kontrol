package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IdbTipDTO;
import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.core.GeoHash;

import java.util.Date;
import java.util.List;

public class TipDTO implements IdbTipDTO {

    private AUserDTO author;
    private String message, docId;
    private int rating;
    private Date creationDate;
    private String g; //location geohash
    private GeoPoint l; //location
    private int type;
    private List<String> likers, dislikers;

    public TipDTO(){}

    @Override
    public int getType() {
        return type;
    }
    @Override
    public void setType(int type) {
        this.type = type;
    }

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
    public List<String> getLikers() {
        return this.likers;
    }

    @Override
    public void setLikers(List<String> likers) {
        this.likers = likers;
    }

    @Override
    public List<String> getDislikers() {
        return this.dislikers;
    }

    @Override
    public void setDislikers(List<String> dislikers) {
        this.dislikers = dislikers;
    }

    @Override
    public String toString() {
        return "TipDTO{\n" +
                "author = '" + author + "'\n" +
                ", message = '" + message + "'\n" +
                ", location = " + l + "'\n" +
                '}';
    }

    @Override
    public String getDocName() {
        return docId;
    }
}
