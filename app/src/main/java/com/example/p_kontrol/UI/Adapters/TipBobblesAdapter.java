package com.example.p_kontrol.UI.Adapters;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentStatePagerAdapter;


import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.Fragments.FragTipBobble;
import com.example.p_kontrol.UI.Services.ITipDTO;

import java.util.List;

public class TipBobblesAdapter extends FragmentPagerAdapter {

    // Defaults
    final int TIP_SHORT_MAX_LENGTH = 250;

    // Argument Keys
    final String BOBBLE_NAME = "bobbleTip_name";
    final String BOBBLE_TEXT = "bobbleTip_text";
    final String BOBBLE_URL  = "bobbleTip_URL";
    final String BOBBLE_DATE = "bobbleTip_DATE";

    // Variables
    List<ITipDTO> tips;

    public TipBobblesAdapter(FragmentManager fm, List<ITipDTO> tips){
        super(fm);
        this.tips = tips;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        FragTipBobble frag = new FragTipBobble();
        Bundle bundle = new Bundle();
        ITipDTO thisElement = tips.get(position);
        bundle.putString(BOBBLE_NAME, thisElement.getAuthor().getFirstName() );
        bundle.putString(BOBBLE_TEXT, thisElement.getMessage() );
        bundle.putString(BOBBLE_DATE, thisElement.getCreationDate().toString().replace("\n", "") );
        try {
            bundle.putString(BOBBLE_URL, thisElement.getAuthor().getProfileSRC());
        } catch (Exception e){
            System.out.println("ingen billede");
        }
        frag.setArguments(bundle);
        System.out.println(bundle.toString());
        return frag;
    }

    @Override
    public int getCount() {
        return tips.size();
    }
}
