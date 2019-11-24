package com.example.p_kontrol.UI.Fragments;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.UI.Activities.ActivityMapView;
import com.example.p_kontrol.UI.Adapters.WriteTipAdapter;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments.IWriteTipStage;
import com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments.WriteTip_Stage0;
import com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments.WriteTip_Stage1;
import com.example.p_kontrol.UI.Fragments.WriteTipInternalFragments.WriteTip_Stage2;

import java.util.LinkedList;
import java.util.List;

public class FragMessageWrite extends Fragment  implements View.OnClickListener {

    // Containers
    private View view;
    private ViewPager ContentContainer;
    List<Fragment> fragmentList;

    Fragment stage0,stage1,stage2;

    public FragMessageWrite() {
        fragmentList = null;
        stage0 = null;
        stage1 = null;
        stage2 = null;
    }

    /* -- FRAGMENT IMPLEMENTATION -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_write, container, false);
        ContentContainer = view.findViewById(R.id.WriteTip_InternalViewPager);

        fragmentList = new LinkedList<>();
        stage0 = new WriteTip_Stage0();
        stage1 = new WriteTip_Stage1();
        stage2 = new WriteTip_Stage2(this);

        fragmentList.add(stage0);
        fragmentList.add(stage1);
        fragmentList.add(stage2);


        WriteTipAdapter adapter = new WriteTipAdapter(getChildFragmentManager(),fragmentList);
        ContentContainer.setAdapter(adapter);
        ContentContainer.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return view;
    }

    @Override
    public void onClick(View v) {

    }

    public void finishTip(){
        String tipText = null;
        String tipCate = null;
        String imageUrl = null;
        for(int i = 0; i < fragmentList.size(); i++) {
            IWriteTipStage thisItem = (IWriteTipStage) fragmentList.get(i);
            switch (i) {
                case 0:
                    // stage where you get Description and Category
                    tipText = thisItem.getText();
                    tipCate = thisItem.getOther();
                    break;
                case 1:
                    // stage where you Give an Image
                    break;
                case 2:
                    // stage where you submit.
                    break;
            }
        }
        TipDTO dto = new TipDTO();
        dto.setMessege(tipText);

        ActivityMapView parentAct = (ActivityMapView) getActivity();
        parentAct.makeTip(dto);
            // Call Activity With Method.
    }

}