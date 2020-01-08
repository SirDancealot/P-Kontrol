package com.example.p_kontrol.DataTypes;

public class UserInfoDTO {

    // Dette er en Singleton da det udelukkende er den indloggede bruger.

    static UserInfoDTO userInfoDTO;
    private String name;
    private String name2;
    private String email;
    private String id;
    private String url;
    private boolean login;
    private boolean taskInBack;

    private UserInfoDTO(){this.login = false;}


    public static UserInfoDTO getUserInfoDTO() {
        if (userInfoDTO != null)
            return userInfoDTO;
        else {
            userInfoDTO = new UserInfoDTO();
            return userInfoDTO;
        }
    }

    public IUserDTO getUserDTO(){
        // todo Replace 1 with an Actual UserID
        IUserDTO userDTO = new UserDTO(1,name,name2,url);
        return userDTO;
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
}
