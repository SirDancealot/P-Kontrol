package com.example.p_kontrol.Backend;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class TipDTO implements ITipDTO {
    String author;
    String message;
    String imageSRC;
    int rating;
    LatLng location;
    Date creationDate;


    @Override
    public IUserDTO getAuthor() {
        return null;
    }

    @Override
    public String getMessage() {
        return null;
    }

    @Override
    public LatLng getLocation() {
        return null;
    }

    @Override
    public int getRating() {
        return 0;
    }

    @Override
    public Date getCreationDate() {
        return null;
    }
}
