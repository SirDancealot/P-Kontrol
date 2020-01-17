package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.p_kontrol.R;
/**
 * @responsibility responsibility to createListner call when user submits.
 *
 * */
public class WriteTipState_Submit extends AbstractWriteTipState {

    View view;

    public WriteTipState_Submit(IWriteTipStateListener listener) {
        super(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_write_tip_state_submit, container, false);
        Button button = view.findViewById(R.id.WriteTip_SubmitButton);

        // only Event is a click for done.
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onMessageSubmit();
            }
        });

        return view;
    }

}