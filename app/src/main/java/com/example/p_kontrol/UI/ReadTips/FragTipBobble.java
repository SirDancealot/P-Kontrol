package com.example.p_kontrol.UI.ReadTips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.UI.IFragmentOperator;
import com.example.p_kontrol.UI.MainMenuActivity;
import com.example.p_kontrol.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragTipBobble extends Fragment implements View.OnClickListener{
    // Settings
    final int TIP_SHORT_MAX_LENGTH = 250;
    final String TAG ="FragTipBobble";
    // Argument Keys
    final String BOBBLE_NAME = "bobbleTip_name";
    final String BOBBLE_TEXT = "bobbleTip_text";
    final String BOBBLE_URL = "bobbleTip_URL";
    final String BOBBLE_DATE = "bobbleTip_DATE";
    String URL;

    // regular Variables
    private View view, container,suroundings;
    private TextView readMore, tip, name;
    private CircleImageView profImg;

    private IFragmentOperator fragmentOperator;

    public FragTipBobble(IFragmentOperator fragmentOperator){
    this.fragmentOperator = fragmentOperator;
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
        suroundings = view.findViewById(R.id.bobbelTip_FragmentContainer)                ;
        container   = view.findViewById(R.id.bobbelTip_container)   ;
        //Get Arguments
        try{

            //name of Profile

            if (getArguments().getString(BOBBLE_NAME) != null){
                name.setText(getArguments().getString(BOBBLE_NAME));
            } else {
                name.setText("Anonym");
            }



            // tip Shortend Text.
            String tipText = getArguments().getString(BOBBLE_TEXT);

            if (tipText.length() > 65) {
                tipText = tipText.substring(0, 65) + "...";
            } else {
                readMore.setText(getArguments().getString(BOBBLE_DATE));
            }
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


        if (getArguments().getString(BOBBLE_URL) != null && getArguments().getString(BOBBLE_URL) != ""){
            URL = getArguments().getString(BOBBLE_URL);
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.dontAnimate();
            //Glide.with(FragTipBobble.this).load(R.drawable.tipprofileimg).into(profImg);
            Glide.with(FragTipBobble.this).load(URL).into(profImg);
        } else {
            RequestOptions requestOptions = new RequestOptions();
            requestOptions.dontAnimate();
            Glide.with(FragTipBobble.this).load(R.drawable.anonym).into(profImg);
        }



        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.bobbelTip_FragmentContainer): // clicking on the Suroundings of the TipBobble Closes it
                fragmentOperator.closeTipBobbles();
            break;
            case (R.id.bobbelTip_container):
                // do nothing.
                Log.i(TAG, "onClick:bobbelTip_container  ");
                break;
            case (R.id.bobbelTip_readMore):
                readMore.setText(getArguments().getString(BOBBLE_DATE));
                tip.setText(getArguments().getString(BOBBLE_TEXT));


                Log.i(TAG, "onClick: ReadBox ");
                break;
            default:
                // do nothing
            break;
        }
    }
}
