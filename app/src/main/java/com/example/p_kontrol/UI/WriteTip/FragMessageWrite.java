package com.example.p_kontrol.UI.WriteTip;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
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
    Activity activity;
    private View view,contentContainer, WriteTip_outerBounds;
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
    public FragMessageWrite(ITipWriteListener listener, Activity activity) {
        this.listener = listener;
        this.activity = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view        = inflater.inflate(R.layout.fragment_message_write, container, false);
        viewModel   = ViewModelProviders.of(this.getActivity()).get(LiveDataViewModel.class);

        // View Retrieving
        viewPagerContent    = view.findViewById(R.id.WriteTip_InternalViewPager);
        navNext             = view.findViewById(R.id.WriteTip_Navigation_next)  ;
        navPrev             = view.findViewById(R.id.WriteTip_Navigation_prev)  ;
        navCancel           = view.findViewById(R.id.WriteTip_ButtonCancel)     ;
        progressBar         = view.findViewById(R.id.WriteTip_NavBar)           ;
        WriteTip_outerBounds= view.findViewById(R.id.WriteTip_outerBounds)      ;
        contentContainer    = view.findViewById(R.id.WriteTip_ContentContainer)      ;

        // Navigation Button listners
        navNext.setOnClickListener(this);
        navPrev.setOnClickListener(this);
        navCancel.setOnClickListener(this);
        WriteTip_outerBounds.setOnClickListener(this);
        contentContainer.setOnClickListener(this);

        // Page listener for using the Navigation Bar in the top.
        pageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                stateIndex = position;
                setProgresBarProgress(position);
                hideOrShowNextPrevButtons();
                hideKeyboard();
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
        hideOrShowNextPrevButtons();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.WriteTip_Navigation_next:
                if( !((stateIndex + 1) > (statesList.size()-1))  ){
                    hideKeyboard();
                    viewPagerContent.setCurrentItem(++stateIndex,true);
                    setProgresBarProgress(stateIndex);
                }
                break;
            case R.id.WriteTip_Navigation_prev:
                if( (stateIndex) > 0  ){
                    hideKeyboard();
                    viewPagerContent.setCurrentItem(--stateIndex,true);
                    setProgresBarProgress(stateIndex);
                }
                break;
            case R.id.WriteTip_ButtonCancel:
                listener.onCancelTip();
                break;
            case R.id.WriteTip_outerBounds:
                listener.onCancelTip();
                break;
        }
    }

    @Override
    public void onMessageSubmit(){

        // todo check Message for syntax Errors
        boolean validated = true;

        if (viewModel.getCurrentTip() == null
                || !(viewModel.getCurrentTip().getMessage().trim().length() > 0)
        ) {
            validated = false;
        }

        if(validated){
            listener.onMessageDone();
        }else{
            listener.onCancelTip();
        }

    }

    private void setProgresBarProgress(int i ){
        progressBar.setProgressValue(i + 1);
    }
    private void hideOrShowNextPrevButtons(){
        if(stateIndex == 0){
            navPrev.setVisibility(View.GONE);
            navNext.setVisibility(View.VISIBLE);
        }else if(stateIndex == (statesList.size()-1) ){
            navNext.setVisibility(View.GONE);
            navPrev.setVisibility(View.VISIBLE);
        }else{
            navPrev.setVisibility(View.VISIBLE);
            navNext.setVisibility(View.VISIBLE);
        }

    }

    private void hideKeyboard() {
        try {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
            //Find the currently focused view, so we can grab the correct window token from it.
            View view = activity.getCurrentFocus();
            //If no view currently has focus, create a new one, just so we can grab a window token from it
            if (view == null) {
                view = new View(activity);
            }
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }catch (Exception e){

        }
    }
}



