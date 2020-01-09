package com.example.p_kontrol.UI.WriteTip;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.Util.CustomProgressBar;

import java.util.LinkedList;
import java.util.List;

public class FragMessageWrite extends Fragment implements View.OnClickListener {
    String TAG = "Fragment Message Write";

    // Containers
    private View view, WriteTip_outerBounds;
    private ViewPager viewPagerContent;
    private Button navNext, navPrev, navCancel;
    private CustomProgressBar progressBar;

    List<Fragment> fragmentList;
    ITipWriteListener listener = null ;

    // States
    WriteTipState state_WriteText, state_SubmitTip;
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
        state_WriteText     = new State_WriteText();
        state_SubmitTip     = new State_Submit();

        // Adding them to the list in the Order we want them to be shown.
        fragmentList.add(state_WriteText);
        fragmentList.add(state_SubmitTip);

        currentSate = state_WriteText; // this is the first added state, so it is the current state.
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_write, container, false);
        viewPagerContent = view.findViewById(R.id.WriteTip_InternalViewPager);
        navNext     = view.findViewById(R.id.WriteTip_Navigation_next);
        navPrev     = view.findViewById(R.id.WriteTip_Navigation_prev);
        navCancel   = view.findViewById(R.id.WriteTip_ButtonCancel);
        progressBar = view.findViewById(R.id.WriteTip_NavBar);
        WriteTip_outerBounds = view.findViewById(R.id.WriteTip_outerBounds);


        // initiating Listeners.
        setPageChangeListener();    // !IMPORTANT! if change is made to the order of the fragment list, change is also needed here! Retrieving information.
        setupStateListeners();      // this is where we tell the state Submit to call finish method when clicked.
        state_SubmitTip.setOnWriteTipStageListener(stateListener_SubmitListener);
        navNext.setOnClickListener(this);
        navPrev.setOnClickListener(this);
        navCancel.setOnClickListener(this);
        WriteTip_outerBounds.setOnClickListener(this);

        // Adapter Management.
        WriteTipAdapter adapter = new WriteTipAdapter(getChildFragmentManager(),fragmentList);
        viewPagerContent.setAdapter(adapter);
        viewPagerContent.setOnPageChangeListener(pageChangeListener);

        return view;
    }
    @Override
    public void onClick(View v) {
        int t = viewPagerContent.getCurrentItem();
        switch (v.getId()){

            case R.id.WriteTip_Navigation_next:
                viewPagerContent.setCurrentItem(viewPagerContent.getCurrentItem() + 1, true);
                setProgress(t);
                break;
            case R.id.WriteTip_Navigation_prev:

                viewPagerContent.setCurrentItem(t - 1, true);
                setProgress(t);
                break;

            default:
                listener.onCancelTip();

        }
    }



    // private Calls
    private void setPageChangeListener() {
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            // important! GATHERING PAGE INFORMATION HERE !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        currentSate = state_WriteText;
                        break;
                    case 1: // means State 0 is done and the message is done, so get it before changing.
                        dto.setMessage(currentSate.getDTO().getMessage());
                        currentSate = state_SubmitTip;
                        break;
                }
                setProgress(position);
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
    private void setProgress(int i ){
        progressBar.setProgressValue(i + 1);
    }

    // public Calls
    public void setFragWriteMessageListener(ITipWriteListener listener){
        this.listener = listener;
    }

}