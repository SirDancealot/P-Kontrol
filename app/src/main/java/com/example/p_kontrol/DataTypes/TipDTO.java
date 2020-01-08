package com.example.p_kontrol.DataTypes;

import com.google.android.gms.maps.model.LatLng;

import java.util.Date;

public class TipDTO implements ITipDTO {



    private IUserDTO author;
    private String message;
    private LatLng location;
    private int rating;
    private Date creationDate;

    public TipDTO(){}
    public TipDTO(IUserDTO author, String message, LatLng location, int rating, Date creationDate) {
        this.author = author;
        this.message = message;
        this.location = location;
        this.rating = rating;
        this.creationDate = creationDate;
    }

    public TipDTO copySelf(){
        TipDTO tipDTO = new TipDTO(author,message,location,rating,creationDate);
        return tipDTO;
    }

    @Override
    public IUserDTO getAuthor() {
        return author;
    }
    @Override
    public void setAuthor(IUserDTO author) {
        this.author = author;
    }

    @Override
    public String getMessage() {
        return message;
    }
    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public LatLng getLocation() {
        return location;
    }
    @Override
    public void setLocation(LatLng location) {
        this.location = location;
    }

    @Override
    public int getRating() {
        return rating;
    }
    @Override
    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public Date getCreationDate() {
        return creationDate;
    }
    @Override
    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    //todo implement this in the Interface.
    public TipDTO copy(){
        TipDTO newDTO = new TipDTO();
        newDTO.setAuthor(author);
        newDTO.setCreationDate(creationDate);
        newDTO.setLocation(location);
        newDTO.setMessage(message);
        newDTO.setRating(rating);
        return newDTO;
    }

    /*
    private int tipId;
    private String author;
    private String messege;
    private LatLng location;      //Dette er måske ikke den rigtige type lokation
    private String url;
    private LocalDate date;
    private String type;

    public int getTipId() {
        return tipId;
    }

    public void setTipId(int tipId) {
        this.tipId = tipId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getMessege() {
        return messege;
    }

    public void setMessege(String messege) {
        this.messege = messege;
    }

    public LatLng getLocation() {
        return location;
    }

    public void setLocation(LatLng location) {
        this.location = location;
    }



    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
*/

    @Override
    public String toString() {
        return "TipDTO{" +
                "author='" + author + '\'' +
                ", messege='" + message + '\'' +
                ", location=" + location +
                '}';
    }
}
