package com.example.p_kontrol.UI.WriteTip;

import com.example.p_kontrol.DataTypes.ITipDTO;

public interface IWriteTipState {

    void setOnWriteTipStageListener(IWriteTipStageListener listener);
    ITipDTO getDTO();
}
