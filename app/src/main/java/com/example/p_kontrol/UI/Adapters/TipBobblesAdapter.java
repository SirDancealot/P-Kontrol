package com.example.p_kontrol.UI.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.p_kontrol.UI.Fragments.FragTipBobble;
import com.example.p_kontrol.Temp.tipDTO;

import java.util.List;

public class TipBobblesAdapter extends FragmentStatePagerAdapter {

    // Settings
    final int TIP_SHORT_MAX_LENGTH = 250;

    // Argument Keys
    final String BOBBLE_NAME = "bobbleTip_name";
    final String BOBBLE_TEXT = "bobbleTip_text";

    // Variables
    List<tipDTO> tips;

    public TipBobblesAdapter(FragmentManager fm, List<tipDTO> tips){
        super(fm);
        this.tips = tips;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        FragTipBobble frag = new FragTipBobble();
        Bundle bundle = new Bundle();
        tipDTO thisElement = tips.get(position);
        bundle.putString(BOBBLE_NAME, thisElement.getAuthorName() );
        bundle.putString(BOBBLE_TEXT, thisElement.getTipText() );
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getCount() {
        return tips.size();
    }
}
