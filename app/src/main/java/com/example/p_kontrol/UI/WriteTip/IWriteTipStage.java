package com.example.p_kontrol.UI.WriteTip;

import com.example.p_kontrol.DataTypes.ATipDTO;

public interface IWriteTipStage {

    void setOnWriteTipStageListener(IWriteTipStageListener listener);
    ATipDTO getDTO();
}
