package com.example.p_kontrol.UI.ReadTips;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


import com.example.p_kontrol.DataTypes.ITipDTO;

import java.text.SimpleDateFormat;
import java.util.List;

public class TipBobblesAdapter extends FragmentPagerAdapter {

    // Defaults
    final int TIP_SHORT_MAX_LENGTH = 250;

    // Argument Keys
    final String BOBBLE_NAME = "bobbleTip_name";
    final String BOBBLE_TEXT = "bobbleTip_text";
    final String BOBBLE_URL  = "bobbleTip_URL";
    final String BOBBLE_DATE = "bobbleTip_DATE";
    final String TAG = "TipAdapter";
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");


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
        Log.d(TAG, "getItem:" + thisElement.getAuthor().getFirstName());
        bundle.putString(BOBBLE_NAME, thisElement.getAuthor().getFirstName() );
        Log.d(TAG, "getItem:" + thisElement.getMessage());
        bundle.putString(BOBBLE_TEXT, thisElement.getMessage() );
        Log.d(TAG, "getItem:" + thisElement.getAuthor().getFirstName());
        bundle.putString(BOBBLE_DATE, DATE_FORMAT.format(thisElement.getCreationDate().toString()) );
        Log.d(TAG, "getItem:" + thisElement.getAuthor().getFirstName());
        try {
            bundle.putString(BOBBLE_URL, thisElement.getAuthor().getProfileSRC());
        } catch (Exception e){
            Log.d(TAG, "getItem: no picture");
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
