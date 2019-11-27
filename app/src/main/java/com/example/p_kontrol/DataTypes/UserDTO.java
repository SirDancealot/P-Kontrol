package com.example.p_kontrol.DataTypes;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;

import java.util.List;

public class UserDTO implements IUserDTO {

    int userId;
    String firstName;
    String lastName;
    String profileSrc;
    List<ITipDTO> tips = null;

    public UserDTO(int userId, String firstName, String lastName, String profileSrc) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileSrc = profileSrc;
    }

    @Override
    public int getUserId() {
        return 0;
    }

    @Override
    public String getFirstName() {
        return null;
    }

    @Override
    public String getLastName() {
        return null;
    }

    @Override
    public String getProfileSRC() {
        return null;
    }

    @Override
    public List<ITipDTO> getTips() {
        return null;
    }
}
