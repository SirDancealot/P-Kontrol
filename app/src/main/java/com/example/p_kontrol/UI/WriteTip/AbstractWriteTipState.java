package com.example.p_kontrol.UI.WriteTip;

import androidx.fragment.app.Fragment;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.ITipDTO;
/** @responsibilty to contain base implementation for a Write Tip stage, and act as a common type of objects  */
public class AbstractWriteTipState extends Fragment  {

    ITipDTO dto = new TipDTO();
    IWriteTipStateListener listener;

    /**
     * abstract implementation to be similar across all States.
     * @param listener;
     * */
    public AbstractWriteTipState(IWriteTipStateListener listener) {
        super();
        this.listener = listener;
    }

}
