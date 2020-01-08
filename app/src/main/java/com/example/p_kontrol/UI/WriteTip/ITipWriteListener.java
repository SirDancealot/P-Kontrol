package com.example.p_kontrol.UI.WriteTip;

import com.example.p_kontrol.DataTypes.ITipDTO;

public interface ITipWriteListener {
    void onMessageDone(ITipDTO dto);
    void onCancelTip();
}
