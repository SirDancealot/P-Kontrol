package com.example.p_kontrol.DataTypes;

import com.firebase.ui.auth.data.model.User;

import java.io.Serializable;
import java.util.List;

public class UserDTO extends AUserDTO {

    String firstName;
    String lastName;
    String profileSrc;

    public UserDTO(){}
    public UserDTO(String firstName, String lastName, String profileSrc) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.profileSrc = profileSrc;
        this.setUserId( 0 ); // todo lav en kommentar der forkalre hvorfor 0.
    }
}
