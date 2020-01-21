package com.example.p_kontrol.UI.ReadTips;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IRatingDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.RatingDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.UI.MainMenuAcitvity.IFragmentOperator;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @responsibilty to contain all the responsibility for showing it Data for a single tip in a specific layout
 * */
public class FragTipBobble extends Fragment implements View.OnClickListener{

    // Settings
    final String TAG = this.getClass().getName();

    private String URL;
    private int type;
    private UserInfoDTO userInfoDTO;
    private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

    // regular Variables
    private View view, tipcontainer;
    private TextView readMore, tip, name;
    private CircleImageView profImg;
    private ITipDTO tipDTO;
    private LinearLayout topBar;
    private ImageView like, dislike;
    private int likeStatus;

    private IFragmentOperator fragmentOperator;

    /** @responsibilty to contain all the responsibility for showing it Data for a single tip in a specific layout
     * @param  fragmentOperator passed by adapter.
     * @param tipDTO this TipDto Data
     * */
    public FragTipBobble(IFragmentOperator fragmentOperator, ITipDTO tipDTO){
        this.fragmentOperator = fragmentOperator;
        this.tipDTO = tipDTO;
    }

// android things

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
        container   = view.findViewById(R.id.bobbelTip_container)   ;
        topBar      = view.findViewById(R.id.bobbelTip_top_bar)     ;
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


    /**
     * determine the Images for likes button, based on if a user have liked or not.
     * */
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
    /**
     * color Fragment Header based on Type of Tip
     * */
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
    /**
     *  Trims down text if it is to large
     * */
    private void trimText(){
        // tip Shortend Text.
        String tipText = tipDTO.getMessage();
        if(tipText != null) {
            String[] split_tip_test = tipText.split("\n");
            if (split_tip_test.length > 4) {
                tipText = split_tip_test[0] + " " + split_tip_test[1] + " " + split_tip_test[2] + " " + split_tip_test[3];
            } else {
                if (tipText.length() > 65) {
                    tipText = tipText.substring(0, 65) + "...";
                } else {
                    readMore.setText(DATE_FORMAT.format(tipDTO.getCreationDate()));
                }
            }
            tip.setText(tipText);
        }
    }
    /**
     * gets the profileImage to show on the tip
     * */
    private void getProfileImage(){
        if (tipDTO .getAuthor() != null ) {
            if (tipDTO.getAuthor().getProfileSRC() != null) {
                System.out.println("kkkkk ---------- henter img");
                System.out.println(tipDTO.getAuthor().getProfileSRC());
                URL = tipDTO.getAuthor().getProfileSRC();
                RequestOptions requestOptions = new RequestOptions();
                requestOptions.dontAnimate();
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

    /**
     * used to calculate the distance between the users current location and {@code this} tip.
     *
     * code to convert latitude and longtitude to distance in km copied from:
     *      https://www.geodatasource.com/developers/java
     * @return the distance from {@code LiveDataViewModel.getCurrentLocation()} to the location of the tip contained in {@code this FragTipBobble}
     */
    private double distanceToUser() {
        LiveDataViewModel vm = ViewModelProviders.of(this).get(LiveDataViewModel.class);
        LatLng userPosition = vm.getCurrentLocation().getValue();
        LatLng tipPosition = new LatLng(tipDTO.getL().getLatitude(), tipDTO.getL().getLongitude());

        if ((userPosition.latitude == tipPosition.latitude) && (userPosition.longitude == tipPosition.longitude)) {
            return 0;
        }
        else {
            double theta = userPosition.longitude - tipPosition.longitude;
            double dist = Math.sin(Math.toRadians(userPosition.latitude)) * Math.sin(Math.toRadians(tipPosition.latitude)) + Math.cos(Math.toRadians(userPosition.latitude)) * Math.cos(Math.toRadians(tipPosition.latitude)) * Math.cos(Math.toRadians(theta));
            dist = Math.acos(dist);
            dist = Math.toDegrees(dist);
            dist = dist * 60 * 1.1515;
            dist = dist * 1.609344;
            return (dist);
        }
    }
}
