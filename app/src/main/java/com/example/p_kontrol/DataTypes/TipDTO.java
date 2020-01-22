package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IdbTipDTO;
import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.core.GeoHash;

import java.util.Date;
import java.util.List;
import java.util.Objects;

    public class TipDTO implements IdbTipDTO {

    private UserInfoDTO author;
    private String message, docId;
    private int rating;
    private Date creationDate;
    private String g; //location geohash
    private GeoPoint l; //location
    private int type;
    private int likers, dislikers;

    public TipDTO(){}

    @Override
    public int hashCode() {
        return Objects.hash(author, message, g);
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TipDTO tipDTO = (TipDTO) o;
        return Objects.equals(author, tipDTO.author) &&
                Objects.equals(message, tipDTO.message) &&
                Objects.equals(g, tipDTO.g);
    }

    //  IdbTipDTO extends ITIPDTO and IMetatData.
    // interface TipDTO
    @Override
    public int getType() {
        return type;
    }
    @Override
    public void setType(int type) {
        this.type = type;
    }

    @Override
    public UserInfoDTO getAuthor() {
        return author;
    }
    @Override
    public void setAuthor(UserInfoDTO author) {
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

    /**
     * @return the location GeoHash
     * */
    public String getG() {
        return g;
    }
    /**
     * @param g is the location GeoHash
     * */
    public void setG(String g) {
        this.g = g;
    }

    /**
     * @return l GeoPoint location
     */
    public GeoPoint getL() {
        return l;
    }
    /**
     * @param l GeoPoint location
     */
    public void setL(GeoPoint l) {
        this.l = l;
        g = new GeoHash(l.getLatitude(), l.getLongitude()).getGeoHashString();
    }

    @Override
    public int getLikers() {
        return this.likers;
    }
    @Override
    public void setLikers(int likers) {
        this.likers = likers;
    }

    @Override
    public int getDislikers() {
        return this.dislikers;
    }
    @Override
    public void setDislikers(int dislikers) {
        this.dislikers = dislikers;
    }

    @Override
    public String getDocId() {
        return docId;
    }
    @Override
    public void setDocId(String docId) {
            this.docId = docId;
        }

    // interface MetaData
    @Override
    public String getDocName() {
        return docId;
    }
    @Override
    public void setDocName(String name) {
        docId = name;
    }


}
