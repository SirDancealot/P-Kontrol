package com.example.p_kontrol.DataTypes;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public interface IPVagtDTO {



    public LatLng getLocation();

    public Date getCreationDate();

    public String getUid();

}
