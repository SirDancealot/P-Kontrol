package com.example.p_kontrol.UI.WriteTip;

import androidx.fragment.app.Fragment;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.ITipDTO;

public class WriteTipState extends Fragment implements IWriteTipStage {

    ITipDTO dto = new TipDTO();
    IWriteTipStageListener listener;

    @Override
    public void setOnWriteTipStageListener(IWriteTipStageListener listener) {
        this.listener = listener;
    }

    @Override
    public ITipDTO getDTO(){
        return dto;
    }
}
