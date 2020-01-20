package com.example.p_kontrol.UI.WriteTip;

import androidx.fragment.app.Fragment;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;

public class AbstractWriteTipState extends Fragment implements IWriteTipState {

    TipDTO dto = new TipDTO();
    IWriteTipStateListener listener;

    public AbstractWriteTipState(IWriteTipStateListener listener) {
        super();
        this.listener = listener;
    }

    public AbstractWriteTipState(){}

    @Override
    public ITipDTO getDTO() {
        return null;
    }

}
