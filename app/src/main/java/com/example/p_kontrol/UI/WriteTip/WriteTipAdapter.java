package com.example.p_kontrol.UI.WriteTip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
/**
 * @responsibilty responsibility to change out stages of writing a tip.
 *
 * */
public class WriteTipAdapter extends FragmentPagerAdapter {

    List<AbstractWriteTipState> fragmentList;
    public WriteTipAdapter(@NonNull FragmentManager fm, List<AbstractWriteTipState> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
