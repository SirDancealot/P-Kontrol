package com.example.p_kontrol.UI.WriteTip;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.List;
/**
 * @responsibilty responsibility to insert WriteTip states into a ViewPager, and Show them, when needed
 * */
public class WriteTipAdapter extends FragmentPagerAdapter {

    List<AbstractWriteTipState> fragmentList;

    /**
     * @responsibilty responsibility to insert WriteTip states into a ViewPager, and Show them, when needed
     *
     * @param fm            needed for all FragmentPagerAdapter adapters.
     * @param fragmentList  list of the AbstractWriteTipState's to show in the viewpager
     *
     * @see {@link com.example.p_kontrol.UI.WriteTip.AbstractWriteTipState}
     *
     * extends
     * @see {@link androidx.fragment.app.FragmentPagerAdapter}
     * */
    public WriteTipAdapter(@NonNull FragmentManager fm, List<AbstractWriteTipState> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    /**
     * @inheritDoc
     * */
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    /**
     * @inheritDoc
     * */
    @Override
    public int getCount() {
        return fragmentList.size();
    }
}
