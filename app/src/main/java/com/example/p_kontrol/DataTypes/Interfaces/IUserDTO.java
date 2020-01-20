package com.example.p_kontrol.DataTypes.Interfaces;

public interface IUserDTO {

    static IUserDTO getUserDTO(IUserDTO EmptyUser, String userId, String firstName , String lastName, String profileImg){
        EmptyUser.setUserId(userId);
        EmptyUser.setFirstName(firstName);
        EmptyUser.setLastName(lastName);
        EmptyUser.setProfileSRC(profileImg);
        return EmptyUser;
    }

    String getUserId();
    String getFirstName();
    String getLastName();
    String getProfileSRC();

    // Sets
    void setUserId(String userId);
    void setFirstName(String firstName);
    void setLastName(String lastName);
    void setProfileSRC(String profileSrc);

}
