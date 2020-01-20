package com.example.p_kontrol.UI.ReadTips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
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
    String URL;
    int type;
    UserInfoDTO userInfoDTO;
    List<ITipDTO> tips;
    SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // regular Variables
    private View view, tipcontainer,suroundings;
    private TextView readMore, tip, name;
    private CircleImageView profImg;
    private ITipDTO tipDTO;
    private LinearLayout topBar;
    private ImageView like, dislike;
    private int likeStatus;

    private IFragmentOperator fragmentOperator;

    public FragTipBobble(IFragmentOperator fragmentOperator, ITipDTO tipDTO){
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
        tipcontainer= view.findViewById(R.id.bobbelTip_container)   ;
        topBar      = view.findViewById(R.id.bobbelTip_top_bar)    ;
        like        = view.findViewById(R.id.bobbelTip_like)              ;
        dislike     = view.findViewById(R.id.bobbelTip_dislike)           ;

        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        tipcontainer.setOnClickListener(this);
        view.setOnClickListener(this);
        readMore.setOnClickListener(this);

        likeStatus = 0;


        //Get Arguments
        preRenderLikes();
        evalTipType();
        trimText(); // if text is to loong, trim it down.
        try{
            //name of Profile
            if (tipDTO.getAuthor().getFirstName() != null){
                name.setText(tipDTO.getAuthor().getFirstName());
            } else {
                name.setText("Anonym");
            }

        }catch (Exception e){
            System.out.println(e);
            // if nothing is recieved.
            name.setText("Unknown Name");
            tip.setText("Unknown Tip");
        }
        getProfileImage();

        return view;
    }

    private void preRenderLikes(){
        if(tipDTO.getLikers() != null) {
            if (tipDTO.getLikers().contains(userInfoDTO.getId())) {
                dislike.setImageResource(R.drawable.ic_tip_dislike);
                like.setImageResource(R.drawable.ic_tip_like_on);
                likeStatus = 1;
            }
        }
        if(tipDTO.getDislikers() != null) {
            if (tipDTO.getDislikers().contains(userInfoDTO.getId())) {
                dislike.setImageResource(R.drawable.ic_tip_dislike_on);
                like.setImageResource(R.drawable.ic_tip_like);
                likeStatus = -1;
            }
        }
    }
    private void evalTipType(){
        //tip type
        type = tipDTO.getType();
        if(type == TipTypes.paid.getValue()){
            topBar.setBackgroundResource(R.drawable.shape_squarerounded_top_blue);
        } else if(type == TipTypes.free.getValue()){
            topBar.setBackgroundResource(R.drawable.shape_squarerounded_top_green);
        } else if(type == TipTypes.alarm.getValue()){
            topBar.setBackgroundResource(R.drawable.shape_squarerounded_top_red);
        }
    }
    private void trimText(){
        // tip Shortend Text.
        String tipText = tipDTO.getMessage();
        if(tipText != null) {
            String[] split_tip_test = tipText.split("\n");
            if (split_tip_test.length > 4) {
                tipText = split_tip_test[0] + split_tip_test[1] + split_tip_test[2] + split_tip_test[3];
            } else {
                if (tipText.length() > 65) {
                    tipText = tipText.substring(0, 65) + "...";
                } else {
                    //readMore.setText(DATE_FORMAT.format(tipDTO.getCreationDate()));
                    readMore.setText("date stand in");
                }
            }
            tip.setText(tipText);
        }
    }
    private void getProfileImage(){
        if (tipDTO .getAuthor() != null ) {
            if (tipDTO.getAuthor().getProfileSRC() != null) {
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
        } else {
            Log.e(TAG, "getProfileImage: author == null" );
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case (R.id.bobbelTip_FragmentContainer):    // clicking on the Suroundings of the TipBobble Closes it
                fragmentOperator.closeTipBobbles();
                break;
            case (R.id.bobbelTip_readMore):             // readMore
                readMore.setText(DATE_FORMAT.format(tipDTO.getCreationDate()));
                tip.setText(tipDTO.getMessage());
                break;
            case (R.id.bobbelTip_like):         // Like a Tip
                if(likeStatus == 1){
                    like.setImageResource(R.drawable.ic_tip_like);
                    likeStatus = 0;
                } else {
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    like.setImageResource(R.drawable.ic_tip_like_on);
                    likeStatus = 1;
                }
                break;
            case (R.id.bobbelTip_dislike):      // Dislike a Tip
                if(likeStatus == -1){
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    likeStatus = 0;
                    //todo remove dislike
                } else {
                    dislike.setImageResource(R.drawable.ic_tip_dislike_on);
                    like.setImageResource(R.drawable.ic_tip_like);
                    likeStatus = -1;
                    // todo set dislike
                }
                break;
            default:
                // do nothing
            break;
        }
    }
}
