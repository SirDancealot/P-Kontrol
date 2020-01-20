package com.example.p_kontrol.UI.ReadTips;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;


import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.MainMenuAcitvity.IFragmentOperator;

import java.util.List;

public class TipBobblesAdapter extends FragmentPagerAdapter {

    // Defaults
    final int TIP_SHORT_MAX_LENGTH = 250;

    // Argument Keys
    final String BOBBLE_INDEX = "bobbleTip_index";
    final String TAG = "TipAdapter";



    // Variables
    IFragmentOperator fragmentOperator;
    LiveData<List<ITipDTO>> tips;

    public TipBobblesAdapter(FragmentManager fm, LiveData<List<ITipDTO>> tips, IFragmentOperator fragmentOperator){
        super(fm);
        this.tips = tips;
        this.fragmentOperator = fragmentOperator;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        FragTipBobble frag = new FragTipBobble(fragmentOperator, tips.getValue().get(position));

        return frag;
    }

    @Override
    public int getCount() {
        return tips.getValue().size();
    }
}
