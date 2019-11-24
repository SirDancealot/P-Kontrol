package com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Fragments.FragMessageWrite;

public class WriteTip_Stage2 extends Fragment implements IWriteTipStage {

    View view;
    FragMessageWrite parentFrag;
    public WriteTip_Stage2(FragMessageWrite parentFrag) {
        this.parentFrag = parentFrag;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_write_tip__stage2, container, false);
        Button button = view.findViewById(R.id.WriteTip_SubmitButton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parentFrag.finishTip();
            }
        });

        return view;
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
