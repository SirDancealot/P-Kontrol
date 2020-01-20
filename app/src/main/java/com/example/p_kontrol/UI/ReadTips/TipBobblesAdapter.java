package com.example.p_kontrol.UI.ReadTips;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.LiveData;


import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.MainMenuAcitvity.IFragmentOperator;

import java.util.List;
/**
 * @responsibilty to be the adapter of a list of tips, and update self on list changes.
 * */
public class TipBobblesAdapter extends FragmentPagerAdapter {

    // Defaults
    final int TIP_SHORT_MAX_LENGTH = 250;

    // Argument Keys
    final String BOBBLE_INDEX = "bobbleTip_index";
    final String TAG = "TipAdapter";



    // Variables
    IFragmentOperator fragmentOperator;
    LiveData<List<ITipDTO>> tips;
    /**
     * @responsibilty to be the adapter of a list of tips, and update self on list changes.
     *
     * @param fm                needed for extending FragmentPagerAdapter;
     * @param tips              liveData object, used with their observer pattern.
     * @param fragmentOperator  needed to pass to each TipBobble Fragment, needed to know when the View is clicked OutofBounds then close the TipBobble.
     * */
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
