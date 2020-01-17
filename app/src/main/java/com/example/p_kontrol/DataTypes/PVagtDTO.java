package com.example.p_kontrol.DataTypes;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class PVagtDTO implements IPVagtDTO{
    LatLng location;
    Date creationDate;
    String uid;


    public PVagtDTO(LatLng location, Date creationDate, String uid){
        this.location = location;
        this. creationDate = creationDate;
        this.uid = uid;
    }


    public LatLng getLocation(){
        return location;
    }

    public Date getCreationDate(){
        return creationDate;
    }

    public String getUid(){
        return uid;
    }



}
