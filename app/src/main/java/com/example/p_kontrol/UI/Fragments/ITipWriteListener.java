package com.example.p_kontrol.UI.Fragments;

import com.example.p_kontrol.DataTypes.ITipDTO;

public interface ITipWriteListener {
    void onMessageDone(ITipDTO dto);
    void onCancelTip();
}
