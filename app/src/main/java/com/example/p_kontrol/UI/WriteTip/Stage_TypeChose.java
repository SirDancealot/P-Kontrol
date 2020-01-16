package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.p_kontrol.R;

/**
 * @responsibilty responsibility to create a room to type in the text for the tip.
 *
 * */
public class Stage_TypeChose extends AbstractWriteTipState implements View.OnClickListener {

    String TAG = "WriteTip STATE WriteText ";

    View view;
    LinearLayout bagground;
    ImageView green, yellow, red;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view        = inflater.inflate(R.layout.fragment_write_tip_state_type, container, false);
        green = view.findViewById(R.id.type_green);
        yellow = view.findViewById(R.id.type_yellow);
        red = view.findViewById(R.id.type_red);
        bagground = view.findViewById(R.id.type_bagground);

        green.setOnClickListener(this);
        yellow.setOnClickListener(this);
        red.setOnClickListener(this);

        dto.setType("normal");
        bagground.setBackgroundColor(getResources().getColor(R.color.typeYellow));



        return view;
    }

    @Override
    public void onClick(View v) {

        if(v == green){
            dto.setType("free");
            bagground.setBackgroundColor(getResources().getColor(R.color.typeGreen));
        } else if(v == yellow){
            dto.setType("normal");
            bagground.setBackgroundColor(getResources().getColor(R.color.typeYellow));
        } else if(v == red){
            dto.setType("alert");
            bagground.setBackgroundColor(getResources().getColor(R.color.typeRed));

        }
    }
}
