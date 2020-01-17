package com.example.p_kontrol.UI.WriteTip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.Util.CustomProgressBar;

import java.util.LinkedList;
import java.util.List;

public class FragMessageWrite extends Fragment implements View.OnClickListener, IWriteTipStateListener {

    // Views
    private View view, WriteTip_outerBounds;
    private ViewPager viewPagerContent;
    private Button navNext, navPrev, navCancel;
    private CustomProgressBar progressBar;

    // States
    WriteTipAdapter adapter;
    List<AbstractWriteTipState> statesList;
    ViewPager.OnPageChangeListener pageChangeListener;
    ITipWriteListener listener;
    int stateIndex = 0;

    LiveDataViewModel viewModel;
    public FragMessageWrite(ITipWriteListener listener) {
        this.listener = listener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view        = inflater.inflate(R.layout.fragment_message_write, container, false);
        viewModel   = ViewModelProviders.of(this).get(LiveDataViewModel.class);

        // View Retrieving
        viewPagerContent    = view.findViewById(R.id.WriteTip_InternalViewPager);
        navNext             = view.findViewById(R.id.WriteTip_Navigation_next)  ;
        navPrev             = view.findViewById(R.id.WriteTip_Navigation_prev)  ;
        navCancel           = view.findViewById(R.id.WriteTip_ButtonCancel)     ;
        progressBar         = view.findViewById(R.id.WriteTip_NavBar)           ;
        WriteTip_outerBounds= view.findViewById(R.id.WriteTip_outerBounds)      ;

        // Navigation Button listners
        navNext.setOnClickListener(this);
        navPrev.setOnClickListener(this);
        navCancel.setOnClickListener(this);
        WriteTip_outerBounds.setOnClickListener(this);

        // Page listener for using the Navigation Bar in the top.
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                setProgresBarProgress(position);
            }
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageScrollStateChanged(int state) {}
        };

        // setList af States, this is also the ;
        statesList = new LinkedList<>();
        statesList.add(new WriteTipState_WriteText(this));
        statesList.add(new WriteTipState_Type(this));
        statesList.add(new WriteTipState_Submit(this));
        adapter = new WriteTipAdapter(getChildFragmentManager(),statesList);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        viewPagerContent.setAdapter(adapter);
        viewPagerContent.setOnPageChangeListener(pageChangeListener);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.WriteTip_Navigation_next:
                if( !((stateIndex + 1) >= (statesList.size()-1))  ){

                    viewPagerContent.setCurrentItem(stateIndex++);
                    setProgresBarProgress(stateIndex);

                }
                break;
            case R.id.WriteTip_Navigation_prev:
                break;
            case R.id.WriteTip_ButtonCancel:
                break;
            case R.id.WriteTip_outerBounds:
                break;
        }
    }

    @Override
    public void onMessageSubmit(){

        // todo check Message for syntax Errors
        boolean validated = true;

        if(validated){
            listener.onMessageDone();
        }else{
            listener.onCancelTip();
        }

    }

    private void setProgresBarProgress(int i ){
        progressBar.setProgressValue(i + 1);
    }

}



