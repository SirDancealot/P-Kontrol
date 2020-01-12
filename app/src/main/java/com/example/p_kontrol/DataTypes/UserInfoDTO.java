package com.example.p_kontrol.DataTypes;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;

public class UserInfoDTO {

    // Dette er en Singleton da det udelukkende er den indloggede bruger.

    static UserInfoDTO userInfoDTO;
    private String name;
    private String name2;
    private String email;
    private String id;
    private Object token;


    private String url;
    private boolean login;
    private boolean taskInBack;
    private FirebaseUser user;

    private UserInfoDTO(){this.login = false;}


    public static UserInfoDTO getUserInfoDTO() {
        if (userInfoDTO != null)
            return userInfoDTO;
        else {
            userInfoDTO = new UserInfoDTO();
            return userInfoDTO;
        }
    }



    public static void newUserInfoDTO() {
        UserInfoDTO.userInfoDTO = new UserInfoDTO();
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean gettaskInBack() {
        return taskInBack;
    }

    public void settaskInBack(boolean taskInBack) {
        this.taskInBack = taskInBack;
    }

    public String getName2() {
        return name2;
    }

    public void setName2(String name2) {
        this.name2 = name2;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Boolean getLogin() {
        return login;
    }

    public void setLogin(Boolean login) {
        this.login = login;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Object getToken() {
        return token;
    }

    public void setToken(Object token) {
        this.token = token;
    }

    public FirebaseUser getUser() {
        return user;
    }

    public void setUser(FirebaseUser user) {
        this.user = user;
        this.token = user.getIdToken(true);
        if( user.getDisplayName().split(" ").length > 1) {
            this.name = user.getDisplayName().split(" ")[0];
            this.name2 = user.getDisplayName().split(" ")[user.getDisplayName().split(" ").length - 1];
        }
        else{
            this.name = user.getDisplayName();
            this.name2 = "";
        }
        if(user.getPhotoUrl() != null){
            this.url = user.getPhotoUrl().toString();
        }
        if(user.getEmail() != null){
            this.email = user.getEmail();
        }
        this.login = true;

    }
}
