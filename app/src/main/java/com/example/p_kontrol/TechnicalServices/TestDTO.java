package com.example.p_kontrol.TechnicalServices;

import com.google.firebase.firestore.DocumentReference;

public class TestDTO {
    private DocumentReference user;
    private String message;

    public TestDTO(){};

    public DocumentReference getUser() {
        return user;
    }

    public void setUser(DocumentReference user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
