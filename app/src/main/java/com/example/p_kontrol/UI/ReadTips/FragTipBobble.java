package com.example.p_kontrol.UI.ReadTips;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.DataBase.FirestoreDAO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.Interfaces.IRatingDTO;
import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;
import com.example.p_kontrol.DataTypes.RatingDTO;
import com.example.p_kontrol.DataTypes.TipTypes;
import com.example.p_kontrol.DataTypes.UserFactory;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.UI.MainMenuAcitvity.IFragmentOperator;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

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
    private TextView readMore, tip, name, distance;
    private CircleImageView profImg;
    private ITipDTO tipDTO;
    private LinearLayout topBar;
    private ImageView like, dislike;
    private int likeStatus;
    LiveDataViewModel vm;

    private IFragmentOperator fragmentOperator;

    /** @responsibilty to contain all the responsibility for showing it Data for a single tip in a specific layout
     * @param  fragmentOperator passed by adapter.
     * @param tipDTO this TipDto Data
     * */
    public FragTipBobble(IFragmentOperator fragmentOperator, ITipDTO tipDTO){
        this.fragmentOperator = fragmentOperator;
        this.tipDTO = tipDTO;

        userInfoDTO = UserFactory.getFactory().getDto();
        if (userInfoDTO.getRatings().containsKey(tipDTO.getAuthor().getUid() + "-" + tipDTO.getG()))
            likeStatus = (userInfoDTO.getRatings().get(tipDTO.getAuthor().getUid() + "-" + tipDTO.getG()).equals("1") ? 1 : -1);
        else
            likeStatus = 0;
    }

// android things

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tip_bobble, container, false);

        vm = ViewModelProviders.of(getActivity()).get(LiveDataViewModel.class);

        // find View objects
        name        = view.findViewById(R.id.bobbelTip_PersonName)  ;
        profImg     = view.findViewById(R.id.bobbelTip_Img)         ;
        tip         = view.findViewById(R.id.bobbelTip_mainTextView);
        readMore    = view.findViewById(R.id.bobbelTip_readMore)    ;
        topBar      = view.findViewById(R.id.bobbelTip_top_bar)     ;
        tipcontainer= view.findViewById(R.id.bobbelTip_container)   ;
        topBar      = view.findViewById(R.id.bobbelTip_top_bar)     ;
        like        = view.findViewById(R.id.bobbelTip_like)        ;
        dislike     = view.findViewById(R.id.bobbelTip_dislike)     ;
        topBar      = view.findViewById(R.id.bobbelTip_top_bar)     ;
        distance     = view.findViewById(R.id.bobbelTip_Distance)   ;

        like.setOnClickListener(this);
        dislike.setOnClickListener(this);
        tipcontainer.setOnClickListener(this);
        view.setOnClickListener(this);
        readMore.setOnClickListener(this);

        //Get Arguments
        preRenderLikes();
        evalTipType();
        addDistance();
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
                    Map<String, String> ratings = userInfoDTO.getRatings();
                    ratings.remove(tipDTO.getAuthor().getUid() + "-" + tipDTO.getG());
                    userInfoDTO.setRatings(ratings);
                    like.setImageResource(R.drawable.ic_tip_like);
                    tipDTO.setLikers(tipDTO.getLikers() - 1);
                    likeStatus = 0;
                } else {
                    Map<String, String> ratings = userInfoDTO.getRatings();
                    ratings.put(tipDTO.getAuthor().getUid() + "-" + tipDTO.getG(), "1");

                    //tip.getAuthor().getUid()+ "-" + tip.getG()

                    userInfoDTO.setRatings(ratings);
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    like.setImageResource(R.drawable.ic_tip_like_on);
                    if (likeStatus == -1)
                        tipDTO.setDislikers(tipDTO.getDislikers() - 1);
                    tipDTO.setLikers(tipDTO.getLikers() + 1);
                    likeStatus = 1;
                }
                break;
            case (R.id.bobbelTip_dislike):      // Dislike a Tip
                if(likeStatus == -1){
                    Map<String, String> ratings = userInfoDTO.getRatings();
                    ratings.remove(tipDTO.getAuthor().getUid() + "-" + tipDTO.getG());
                    userInfoDTO.setRatings(ratings);
                    dislike.setImageResource(R.drawable.ic_tip_dislike);
                    likeStatus = 0;
                    tipDTO.setDislikers(tipDTO.getDislikers() - 1);
                } else {
                    Map<String, String> ratings = userInfoDTO.getRatings();
                    ratings.put(tipDTO.getAuthor().getUid() + "-" + tipDTO.getG(), "0");
                    userInfoDTO.setRatings(ratings);
                    dislike.setImageResource(R.drawable.ic_tip_dislike_on);
                    if (likeStatus == 1)
                        tipDTO.setLikers(tipDTO.getLikers() - 1);
                    like.setImageResource(R.drawable.ic_tip_like);
                    likeStatus = -1;
                    tipDTO.setDislikers(tipDTO.getDislikers() + 1);
                }
                break;
            default:
                // do nothing
                break;
        }
        vm.updateRating(tipDTO, userInfoDTO);
    }


    /**
     * determine the Images for likes button, based on if a user have liked or not.
     * */
    private void preRenderLikes(){
        if(likeStatus == 1) {
            dislike.setImageResource(R.drawable.ic_tip_dislike);
            like.setImageResource(R.drawable.ic_tip_like_on);
        }
        if(likeStatus == -1) {
            dislike.setImageResource(R.drawable.ic_tip_dislike_on);
            like.setImageResource(R.drawable.ic_tip_like);
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
            if (tipDTO.getAuthor().getImageUrl() != null) {
                System.out.println("kkkkk ---------- henter img");
                System.out.println(tipDTO.getAuthor().getImageUrl());
                URL = tipDTO.getAuthor().getImageUrl();
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


    private void addDistance() {

        double dist = distanceToUser();
        String distText;

        if (dist >= 1){
            distText = String.format("%.2f km", dist);
        } else {
            distText = String.format("%.0f m", dist*1000);
        }

        distance.setText(distText);
    }
}
