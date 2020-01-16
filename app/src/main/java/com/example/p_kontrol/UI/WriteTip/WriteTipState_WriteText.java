package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.ATipDTO;
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

    public WriteTipState_WriteText(IWriteTipStateListener listener) {
        super(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view        = inflater.inflate(R.layout.fragment_write_tip_state_writetext, container, false);
        //category    = view.findViewById(R.id.WriteTip_CategorySpinner);
        text        = view.findViewById(R.id.WriteTip_TextInput);


        viewModel = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        text.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                dto.setMessage(s.toString());
                // todo make sure this no Null pointer casts
                ATipDTO dto = viewModel.getMutableTipCreateObject().getValue();
                dto.setMessage(s.toString());
                viewModel.getMutableTipCreateObject().setValue(dto);

                //                TODO add this to backend when writing tips
//                boolean tooManyNewlineChars = Pattern.matches(".*\\n{3,}?.*",text.toString());

//                String str = text.getText().toString();
//                str = str.replace(" ", ""); //removes whitespace for checking for too many newlines
//                str = str.replace("\t", "");
//                boolean foundNewline = str.contains("\n\n\n\n"); // looks for newlines one after another
//
//                while (str.charAt(str.length() - 1) == '\n') //test this. should remove newlines at end of message.
//                    str = str.substring(0, str.length() - 2);
//





            }

            @Override
            public void afterTextChanged(Editable s) {}
        });


        return view;
    }
}
