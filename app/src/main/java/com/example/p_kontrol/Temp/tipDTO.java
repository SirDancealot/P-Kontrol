package com.example.p_kontrol.Temp;

public class tipDTO {

    String authorName;
    String tipText;

    public tipDTO(String authorName, String tipText) {
        this.authorName = authorName;
        this.tipText = tipText;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTipText() {
        return tipText;
    }

    public void setTipText(String tipText) {
        this.tipText = tipText;
    }
}
