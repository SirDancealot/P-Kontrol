package com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p_kontrol.R;

public class WriteTip_Stage_TakePicture extends WriteTipState {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_tip_selectimage, container, false);
    }

}
