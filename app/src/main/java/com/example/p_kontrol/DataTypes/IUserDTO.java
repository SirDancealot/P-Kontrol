package com.example.p_kontrol.DataTypes;

import java.util.List;

public interface IUserDTO {

    int getUserId();
    String getFirstName();
    String getLastName();
    String getProfileSRC();
    List<ITipDTO> getTips();

    // Sets
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setProfileSrc(String profileSrc);

}
