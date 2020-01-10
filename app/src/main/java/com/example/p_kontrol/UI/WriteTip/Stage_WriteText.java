package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p_kontrol.R;
/**
 * @responsibilty responsibility to create a room to type in the text for the tip.
 *
 * */
public class Stage_WriteText extends WriteTipState {

    String TAG = "WriteTip STATE WriteText ";

    View view;
    Spinner category;
    TextView text;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view        = inflater.inflate(R.layout.fragment_write_tip_state_writetext, container, false);
        //category    = view.findViewById(R.id.WriteTip_CategorySpinner);
        text        = view.findViewById(R.id.WriteTip_TextInput);

        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dto.setMessage(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        return view;
    }
}
