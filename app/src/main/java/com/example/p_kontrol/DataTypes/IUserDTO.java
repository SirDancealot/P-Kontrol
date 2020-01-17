package com.example.p_kontrol.DataTypes;

import java.util.List;

public interface IUserDTO {

    static IUserDTO getUserDTO(IUserDTO EmptyUser ,int userId, String firstName , String lastName, String profileImg){
        EmptyUser.setUserId(userId);
        EmptyUser.setFirstName(firstName);
        EmptyUser.setLastName(lastName);
        EmptyUser.setProfileSRC(profileImg);
        return EmptyUser;
    }

    int getUserId();
    String getFirstName();
    String getLastName();
    String getProfileSRC();

    // Sets
    void setUserId(int userId);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setProfileSRC(String profileSrc);

}
