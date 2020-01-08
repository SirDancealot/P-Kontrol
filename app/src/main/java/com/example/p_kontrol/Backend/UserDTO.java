package com.example.p_kontrol.Backend;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.IUserDTO;

import java.util.List;

public class UserDTO implements IUserDTO {

    int userId;
    String firstName;
    String lastName;
    String profileImageSRC;
    List<ITipDTO> tips;

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
