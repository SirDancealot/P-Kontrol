package com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p_kontrol.R;

public class WriteTip_Stage1 extends WriteTipStage {

    public WriteTip_Stage1() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_write_tip__stage1, container, false);
    }

    @Override
    public String getText() {
        return null;
    }

    @Override
    public String getOther() {
        return null;
    }

}
