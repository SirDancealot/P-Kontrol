package com.example.p_kontrol.DataTypes;

import java.io.Serializable;
import java.util.List;

public class AUserDTO implements IUserDTO , Serializable {

    String firstName;
    String lastName;
    String profileSrc;

    public AUserDTO(){}

    @Override
    public int getUserId() {
        return 0;
    }
    @Override
    public void setUserId(int userId){

    }

    @Override
    public String getFirstName() {
        return firstName;
    }
    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }
    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String getProfileSRC() {
        return profileSrc;
    }

    @Override
    public void setProfileImageSRC(String profileSrc) {
        this.profileSrc = profileSrc;
    }

}
