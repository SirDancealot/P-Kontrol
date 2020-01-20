package com.example.p_kontrol.DataTypes.Interfaces;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.util.Date;

public interface IPVagtDTO {



    public GeoPoint getL();

    public Date getCreationDate();

    public String getUid();

}
