package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;

/**
 * @responsibilty responsibility to create a room to type in the text for the tip.
 *
 * */
public class WriteTipState_WriteText extends AbstractWriteTipState {

    String TAG = "WriteTip STATE WriteText ";

    View view;
    TextView text;
    LiveDataViewModel viewModel;
    ITipDTO dto = new TipDTO();

    /**
     * @inheritDoc
     * */
    public WriteTipState_WriteText(IWriteTipStateListener listener) {
        super(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // important Views
        view = inflater.inflate(R.layout.fragment_write_tip_state_writetext, container, false);
        text = view.findViewById(R.id.WriteTip_TextInput);

        // data access
        viewModel = ViewModelProviders.of(this.getActivity()).get(LiveDataViewModel.class);
        dto = viewModel.getTipCreateObject().getValue();
        dto.setMessage("");
        viewModel.setTipCreateObject(dto);

        //the data is set here, when the text has been entered.
        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                dto.setMessage(s.toString());
                viewModel.setTipCreateObject(dto);
            }
        });

        return view;
    }
}
