package com.example.p_kontrol.DataTypes;

import java.util.List;

public class UserDTO implements IUserDTO {
    //TODO implement this class

    String firstName;
    String lastName;
    String profileSrc;

    public UserDTO(String firstName, String lastName, String profileSrc){
        this.firstName = firstName;
        this. lastName = lastName;
        this.profileSrc = profileSrc;

    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public String getProfileSRC() {
        return profileSrc;
    }

    @Override
    public List<ITipDTO> getTips() {
        return null;
    }
}
