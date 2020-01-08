package com.example.p_kontrol.UI.Fragments;

import com.example.p_kontrol.UI.Services.ITipDTO;

public interface ITipWriteListener {
    void onMessageDone(ITipDTO dto);
    void onCancelTip();
}
