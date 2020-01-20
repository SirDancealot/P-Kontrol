package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.DataTypes.Interfaces.IRatingDTO;

public class RatingDTO implements IRatingDTO {


    private String uid;
    private boolean likeType;

    public RatingDTO(String uid, boolean likeType){
        this.uid = uid;
        this.likeType = likeType;
    }

    @Override
    public boolean getTypeLike() {
        return likeType;
    }

    @Override
    public void setTypeLike(boolean type) {
        this.likeType = type;
    }

    @Override
    public String getAuthorUID() {
        return uid;
    }

    @Override
    public void setAuthorUID(String author) {
        this.uid = author;
    }
}
