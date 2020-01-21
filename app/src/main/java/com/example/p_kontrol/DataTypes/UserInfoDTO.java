package com.example.p_kontrol.DataTypes;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

import java.util.List;
import java.util.Map;

public class UserInfoDTO {

    private Object token;

    private String firstName;
    private String lastName;
    private String email;
    private String uid;
    private String imageUrl;
    private Map<String, String> ratings;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Map<String, String> getRatings() {
        return ratings;
    }

    public void setRatings(Map<String, String> ratings) {
        this.ratings = ratings;
    }
}
