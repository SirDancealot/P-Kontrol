package com.example.p_kontrol.UI.ReadTips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.UI.MainMenuAcitvity.IFragmentOperator;
import com.example.p_kontrol.R;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragTipBobble extends Fragment implements View.OnClickListener{
    // Settings
    final int TIP_SHORT_MAX_LENGTH = 250;
    final String TAG ="FragTipBobble";
    // Argument Keys
    final String BOBBLE_INDEX = "bobbleTip_index";
    String URL, type;
    UserInfoDTO userInfoDTO;
    List<ATipDTO> tips;
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // regular Variables
    private View view, container,suroundings;
    private TextView readMore, tip, name;
    private CircleImageView profImg;
    private ATipDTO tipDTO;
    private LinearLayout topBar;
    private ImageView like, dislike;
    private String likeStatus;

    private IFragmentOperator fragmentOperator;

    public FragTipBobble(IFragmentOperator fragmentOperator, ATipDTO tipDTO){
    this.fragmentOperator = fragmentOperator;
        this.tipDTO = tipDTO;
        // Requiired empty public constructor
    }


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
        topBar      = view.findViewById(R.id.bobbelTip_top_bar)    ;
        like        = view.findViewById(R.id.bobbelTip_like)              ;
        dislike     = view.findViewById(R.id.bobbelTip_dislike)           ;

        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        likeStatus = "normal";


        //Get Arguments
        try{

            if(tipDTO.getLikers() != null) {
                if (tipDTO.getLikers().contains(userInfoDTO.getId())) {
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    like.setImageResource(R.drawable.ic_tip_like_on);
                    likeStatus = "like";
                }
            }
            if(tipDTO.getDislikers() != null) {
                if (tipDTO.getDislikers().contains(userInfoDTO.getId())) {
                    dislike.setImageResource(R.drawable.ic_tip_dislike_on);
                    like.setImageResource(R.drawable.ic_tip_like);
                    likeStatus = "dislike";
                }
            }


            //tip type
            type = tipDTO.getType();
            if(type == "normal"){
                //todo Hans set farven til standart farve
            } else if(type == "free"){
                //todo Hans set farven til grøn
            } else if(type == "alert"){
                //todo Hans set farven til rød
            }

            //name of Profile

            if (tipDTO.getAuthor().getFirstName() != null){
                name.setText(getArguments().getString(tipDTO.getAuthor().getFirstName()));
            } else {
                name.setText("Anonym");
            }



            // tip Shortend Text.
            String tipText = tipDTO.getMessage();
            String[] split_tip_test = tipText.split("\n");


            if (split_tip_test.length > 4){
                tipText = split_tip_test[0] + split_tip_test[1] + split_tip_test[2] + split_tip_test[3];
            } else {
                if (tipText.length() > 65){
                    tipText = tipText.substring(0, 65) + "...";
                } else{
                    readMore.setText(DATE_FORMAT.format(tipDTO.getCreationDate()));
                }
            }
            tip.setText(tipText);



        }catch (Exception e){
            System.out.println(e);
            // if nothing is recieved.
            name.setText("Unknown Name");
            tip.setText("Unknown Tip");
        }

        //Litseners
        container.setOnClickListener(this);
        view.setOnClickListener(this);
        readMore.setOnClickListener(this);



        if ( tipDTO.getAuthor().getProfileSRC() != null){
            System.out.println("kkkkk ---------- henter img");
            System.out.println(tipDTO.getAuthor().getProfileSRC());
            URL = tipDTO.getAuthor().getProfileSRC();
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
                readMore.setText(DATE_FORMAT.format(tipDTO.getCreationDate()));
                tip.setText(tipDTO.getMessage());


                Log.i(TAG, "onClick: ReadBox ");
                break;
            case (R.id.bobbelTip_like):
                if(likeStatus == "like"){
                    like.setImageResource(R.drawable.ic_tip_like);
                    likeStatus = "normal";

                } else {
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    like.setImageResource(R.drawable.ic_tip_like_on);
                    likeStatus = "like";
                }
                break;
            case (R.id.bobbelTip_dislike):
                if(likeStatus == "dislike"){
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    likeStatus = "normal";

                } else {
                    dislike.setImageResource(R.drawable.ic_tip_dislike_on);
                    like.setImageResource(R.drawable.ic_tip_like);
                    likeStatus = "dislike";
                }
                break;
            default:
                // do nothing
            break;
        }
    }
}
