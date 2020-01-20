package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.DataTypes.Interfaces.IUserDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

public class AUserDTO implements IUserDTO, Serializable {

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
    public void setProfileSRC(String profileSrc) {
        this.profileSrc = profileSrc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AUserDTO aUserDTO = (AUserDTO) o;
        return Objects.equals(firstName, aUserDTO.firstName) &&
                Objects.equals(lastName, aUserDTO.lastName) &&
                Objects.equals(getUserId(), aUserDTO.getUserId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
