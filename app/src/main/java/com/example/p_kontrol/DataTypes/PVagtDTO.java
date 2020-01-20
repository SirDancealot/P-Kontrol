package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.DataTypes.Interfaces.IPVagtDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import org.imperiumlabs.geofirestore.core.GeoHash;

import java.util.Date;

public class PVagtDTO implements IPVagtDTO {
    Date creationDate;
    String uid;

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    private String g; //location geohash
    private GeoPoint l; //location

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
        g = new GeoHash(l.getLatitude(), l.getLongitude()).getGeoHashString();
    }

    /*
    public PVagtDTO(LatLng location, Date creationDate, String uid){
        this.location = location;
        this. creationDate = creationDate;
        this.uid = uid;
    }*/

    public PVagtDTO() {}

    public Date getCreationDate(){
        return creationDate;
    }

    public String getUid(){
        return uid;
    }



}
