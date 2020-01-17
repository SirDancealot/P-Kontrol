package com.example.p_kontrol.UI.WriteTip;

import androidx.fragment.app.Fragment;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;

public class AbstractWriteTipState extends Fragment implements IWriteTipState {

    ATipDTO dto = new TipDTO();
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
