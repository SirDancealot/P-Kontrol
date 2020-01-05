package com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments;

import com.example.p_kontrol.UI.Services.ITipDTO;

public interface IWriteTipState {

    void setOnWriteTipStageListener(IWriteTipStageListener listener);
    ITipDTO getDTO();
}
