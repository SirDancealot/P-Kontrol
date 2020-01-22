package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.DataTypes.Interfaces.IPVagtDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.core.GeoHash;

import java.util.Date;


/**
 * @responsibilty be a Data class
 * Pvagt dto contains datat about it self
 *
 * */
public class PVagtDTO implements IPVagtDTO {

    private Date creationDate;
    private String uid;
    private String g; //location geohash
    private GeoPoint l; //location

    public PVagtDTO() {}

    // gets and sets
    public Date getCreationDate(){
        return creationDate;
    }
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    /**
     * @return User ID
     * */
    public String getUid(){
        return uid;
    }
    /**
     * @param uid User ID
     * */
    public void setUid(String uid) {
        this.uid = uid;
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










}
