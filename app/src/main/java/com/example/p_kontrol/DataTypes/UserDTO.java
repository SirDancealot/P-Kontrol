package com.example.p_kontrol.DataTypes;

import com.firebase.ui.auth.data.model.User;

import java.io.Serializable;
import java.util.List;

public class UserDTO implements IUserDTO, Serializable {
    //TODO implement this class

    String firstName;
    String lastName;
    String profileSrc;

    public UserDTO(){}
    public UserDTO(String firstName, String lastName, String profileSrc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileSrc = profileSrc;
    }

/*
    public UserDTO(String firstName, String lastName, String profileSrc){
        this.firstName = firstName;
        this. lastName = lastName;
        this.profileSrc = profileSrc;

    }*/



    @Override
    public int getUserId() {
        return 0;
    }
    @Override
    public void setUserId(){

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

    @Override
    public List<ITipDTO> getTips() {
        return null;
        }






}
