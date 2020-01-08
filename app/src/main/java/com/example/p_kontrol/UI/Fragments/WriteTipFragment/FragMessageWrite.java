package com.example.p_kontrol.UI.Fragments.WriteTipFragment;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Fragments.ITipWriteListener;
import com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments.IWriteTipStageListener;
import com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments.WriteTipState;
import com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments.WriteTip_State_WriteText;
import com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments.WriteTip_Stage_TakePicture;
import com.example.p_kontrol.UI.Fragments.WriteTipFragment.WriteTipInternalFragments.WriteTip_Stage_Submit;
import com.example.p_kontrol.DataTypes.ITipDTO;

import java.util.LinkedList;
import java.util.List;

public class FragMessageWrite extends Fragment {
    String TAG = "Fragment Message Write";

    // Containers
    private View view;
    private ViewPager ContentContainer;
    List<Fragment> fragmentList;
    ITipWriteListener listener = null ;

    // States
    WriteTipState state_WriteText, state_TakePicture, state_SubmitTip;
    WriteTipState currentSate = null;

    // Objects
    ITipDTO dto;

    // Listeners
    ViewPager.OnPageChangeListener pageChangeListener = null;
    IWriteTipStageListener stateListener_SubmitListener = null;

    /* -- FRAGMENT IMPLEMENTATION -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */
    public FragMessageWrite(){
        dto = new TipDTO();
        // instantiating objects.
        fragmentList = new LinkedList<>();
        state_WriteText     = new WriteTip_State_WriteText();
        state_TakePicture   = new WriteTip_Stage_TakePicture();
        state_SubmitTip     = new WriteTip_Stage_Submit();

        // Adding them to the list in the Order we want them to be shown.
        fragmentList.add(state_WriteText);
        fragmentList.add(state_TakePicture);
        fragmentList.add(state_SubmitTip);

        currentSate = state_WriteText; // this is the first added state, so it is the current state.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_write, container, false);
        ContentContainer = view.findViewById(R.id.WriteTip_InternalViewPager);

        // initiating Listeners.
        setPageChangeListener();    // !IMPORTANT! if change is made to the order of the fragment list, change is also needed here! Retrieving information.
        setupStateListeners();      // this is where we tell the state Submit to call finish method when clicked.
        state_SubmitTip.setOnWriteTipStageListener(stateListener_SubmitListener);


        // Adapter Management.
        WriteTipAdapter adapter = new WriteTipAdapter(getChildFragmentManager(),fragmentList);
        ContentContainer.setAdapter(adapter);
        ContentContainer.setOnPageChangeListener(pageChangeListener);

        return view;
    }

    // private Calls
    private void setPageChangeListener() {
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            // GATHERING PAGE INFORMATION HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        currentSate = state_WriteText;
                        break;
                    case 1: // means State 0 is done and the message is done, so get it before changing.
                        dto.setMessage(currentSate.getDTO().getMessage());
                        currentSate = state_TakePicture;
                        break;
                    case 2:
                        currentSate = state_SubmitTip;
                        break;
                }
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        };
    }
    private void setupStateListeners() {
        stateListener_SubmitListener = new IWriteTipStageListener() {
            @Override
            public void onDone() {
                ITipDTO newDTO = new TipDTO();
                newDTO.setMessage(dto.getMessage());

                if(listener != null)
                    listener.onMessageDone(dto);
            }
        };
    }


    // public Calls
    public void setFragWriteMessageListener(ITipWriteListener listener){
        this.listener = listener;
    }
}