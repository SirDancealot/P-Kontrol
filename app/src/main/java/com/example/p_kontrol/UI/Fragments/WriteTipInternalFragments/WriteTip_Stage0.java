package com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p_kontrol.R;

public class WriteTip_Stage0 extends WriteTipStage {

    View view;
    Spinner category;
    TextView text;
    public WriteTip_Stage0() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view        =  inflater.inflate(R.layout.fragment_write_tip__stage0, container, false);
        category    = view.findViewById(R.id.WriteTip_CategorySpinner);
        text        = view.findViewById(R.id.WriteTip_TextInput);

        return view;
    }

    @Override
    public String getText() {
        return text.getText().toString();
    }

    @Override
    public String getOther() {
        return null;
    }

}
