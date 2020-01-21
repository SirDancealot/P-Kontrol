package com.example.p_kontrol.DataTypes;

import com.google.firebase.auth.FirebaseUser;

import java.util.HashMap;

public class UserFactory {
    private static UserFactory factory;
    private UserInfoDTO dto;

    private UserFactory() { }

    public void setUser(FirebaseUser user) {
        dto = new UserInfoDTO();
        //dto.setToken(user.getIdToken(true)); TODO is token required
        if(user.getDisplayName() != null){
            if( user.getDisplayName().split(" ").length > 1) {
                dto.setFirstName(user.getDisplayName().split(" ")[0]);
                dto.setLastName(user.getDisplayName().split(" ")[user.getDisplayName().split(" ").length - 1]);
            }
            else {
                dto.setFirstName(user.getDisplayName());
                dto.setLastName("");
            }
        } else {
            dto.setFirstName(user.getEmail().split("@")[0]);
            dto.setLastName("");
        }
        if(user.getPhotoUrl() != null){
            dto.setImageUrl(user.getPhotoUrl().toString());
        }
        if(user.getEmail() != null){
            dto.setEmail(user.getEmail());
        }

        dto.setUid(user.getUid());
        dto.setRatings(new HashMap<>());

        System.out.println(dto.getFirstName());
    }

    public static UserFactory getFactory() {
        if (factory == null)
            factory = new UserFactory();
        return factory;
    }

    public void setDto(UserInfoDTO dto) {
        this.dto = dto;
    }

    public UserInfoDTO getDto() {
        return dto;
    }
}
