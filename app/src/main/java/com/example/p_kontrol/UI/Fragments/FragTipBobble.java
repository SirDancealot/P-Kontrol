package com.example.p_kontrol.UI.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.p_kontrol.UI.Activities.ActivityMapView;
import com.example.p_kontrol.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragTipBobble extends Fragment implements View.OnClickListener{
    // Settings
    final int TIP_SHORT_MAX_LENGTH = 250;
    final String TAG ="FragTipBobble";
    // Argument Keys
    final String BOBBLE_NAME = "bobbleTip_name";
    final String BOBBLE_TEXT = "bobbleTip_text";

    // regular Variables
    private View view, container,suroundings;
    private TextView readMore, tip, name;
    private CircleImageView profImg;

    public FragTipBobble() {
        // Requiired empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tip_bobble, container, false);

        // find View objects
        name        = view.findViewById(R.id.bobbelTip_PersonName)  ;
        profImg     = view.findViewById(R.id.bobbelTip_Img)         ;
        tip         = view.findViewById(R.id.bobbelTip_mainTextView);
        readMore    = view.findViewById(R.id.bobbelTip_readMore)    ;
        suroundings = view.findViewById(R.id.fagTip)                ;
        container   = view.findViewById(R.id.bobbelTip_container)   ;
        //Get Arguments
        try{

            //name of Profile
            name.setText(getArguments().getString(BOBBLE_NAME));

            // tip Shortend Text.
            String tipText = getArguments().getString(BOBBLE_TEXT);
            tipText = tipText.substring(0, Math.min(tipText.length(), TIP_SHORT_MAX_LENGTH));
            tip.setText(tipText);

        }catch (Exception e){
            // if nothing is recieved.
            name.setText("Unknown Name");
            tip.setText("Unknown Tip");
        }

        //Litseners
        container.setOnClickListener(this);
        view.setOnClickListener(this);
        readMore.setOnClickListener(this);


        /*RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();
        Glide.with(FragTipBobble.this).load(R.drawable.tipprofileimg).into(profImg);
        string = getString(R.string.tip1);
        if (string.length() > 65)
        {
            string = string.substring(0, 65) + "...";
        }
        tip.setText(string);
        */


        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.fagTip): // clicking on the Suroundings of the TipBobble Closes it
                ActivityMapView act = (ActivityMapView) getActivity();
                act.CloseTipBobbleViewPager();
            break;
            case (R.id.bobbelTip_container):
                // do nothing.
                Log.i(TAG, "onClick:bobbelTip_container  ");
                break;
            case (R.id.bobbelTip_readMore):
                //todo Impl: exspand Tip Box.
                Log.i(TAG, "onClick: ReadBox ");
                break;
            default:
                // do nothing
            break;
        }
    }
}
