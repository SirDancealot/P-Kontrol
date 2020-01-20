package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.lifecycle.ViewModelProviders;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;

/**
 * @responsibilty responsibility to give the Tip a type, that specifies the category of tip.
 *
 * */
public class WriteTipState_Type extends AbstractWriteTipState implements View.OnClickListener {

    String TAG = "WriteTip STATE WriteText ";

    View view;
    LinearLayout bagground;
    LiveDataViewModel viewModel;
    Button btnFreePark, btnPaidPark, btnWarning;
    int activeButtonid = 0;

    /**
     * @inheritDoc
     * */
    public WriteTipState_Type(IWriteTipStateListener listener) {
        super(listener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view        = inflater.inflate(R.layout.fragment_write_tip_state_type, container, false);
        btnFreePark = view.findViewById(R.id.WriteTip_TipCategory_free);
        btnPaidPark = view.findViewById(R.id.WriteTip_TipCategory_paid);
        btnWarning  = view.findViewById(R.id.WriteTip_TipCategory_warning);

        btnFreePark.setOnClickListener(this);
        btnPaidPark.setOnClickListener(this);
        btnWarning.setOnClickListener(this);

        viewModel = ViewModelProviders.of(this.getActivity()).get(LiveDataViewModel.class);
        return view;
    }

    @Override
    public void onClick(View v) {

        dto = viewModel.getCurrentTip();
        if(dto == null){
            dto = new TipDTO();
        }
        dto.setType(TipTypes.paid.getValue());

        switch (v.getId()){
            case R.id.WriteTip_TipCategory_free:
                dto.setType(TipTypes.free.getValue());
                activeButtonid = 1;
                updateActiveButton();
                break;
            case R.id.WriteTip_TipCategory_paid:
                dto.setType(TipTypes.paid.getValue());
                activeButtonid = 2;
                updateActiveButton();
                break;
            case R.id.WriteTip_TipCategory_warning:
                dto.setType(TipTypes.alarm.getValue());
                activeButtonid = 3;
                updateActiveButton();
                break;
        }
        viewModel.setCurrentTip(dto);

    }

    /**
     * an internal call that makes sure only one button is visibly checked
     * */
    private void updateActiveButton(){
        switch (activeButtonid){
            case 1:
                btnFreePark.setBackgroundResource(R.drawable.shape_buttonstyle_02_activated);
                btnPaidPark.setBackgroundResource(R.drawable.shape_buttonstyle_02);
                btnWarning.setBackgroundResource(R.drawable.shape_buttonstyle_02);
                break;
            case 2:
                btnFreePark.setBackgroundResource(R.drawable.shape_buttonstyle_02);
                btnPaidPark.setBackgroundResource(R.drawable.shape_buttonstyle_02_activated);
                btnWarning.setBackgroundResource(R.drawable.shape_buttonstyle_02);
                break;
            case 3:
                btnFreePark.setBackgroundResource(R.drawable.shape_buttonstyle_02);
                btnPaidPark.setBackgroundResource(R.drawable.shape_buttonstyle_02);
                btnWarning.setBackgroundResource(R.drawable.shape_buttonstyle_02_activated);
                break;
        }
    }
}
