package com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments;

import androidx.fragment.app.Fragment;

public class WriteTipStage extends Fragment implements IWriteTipStage {

    IWriteTipStageListener listener;

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getOther() {
        return null;
    }


    @Override
    public void setOnWriteTipStageListener(IWriteTipStageListener listener) {
        this.listener = listener;
    }
}
