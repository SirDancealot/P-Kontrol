package com.example.p_kontrol.DataTypes;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class PVagtDTO implements IPVagtDTO{
    LatLng location;
    Date creationDate;


    public PVagtDTO(LatLng location, Date creationDate){
        this.location = location;
        this. creationDate = creationDate;
    }


    public LatLng getLocation(){
        return location;
    }

    public Date getCreationDate(){
        return creationDate;
    }



}
