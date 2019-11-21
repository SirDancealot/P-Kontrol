package com.example.p_kontrol.dao;

import android.location.Location;

import com.example.p_kontrol.dto.TipDTO;

import java.util.List;

/**
 * The generel representation of data access operations for a tip
 */
public interface ITipDAO {

    //Create
    void createTip(TipDTO tipDTO);

    //Read
    TipDTO getTip(int tipId);

    List<TipDTO> getTips(Location location);        //vi kan lave det til at der fås tips i en radius om den givne location

    //Update
    void updateTip(TipDTO tipDTO);

    //Delete
    void deleteTip(int tipId);
}
