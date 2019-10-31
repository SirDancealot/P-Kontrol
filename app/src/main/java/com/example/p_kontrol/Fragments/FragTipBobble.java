package com.example.p_kontrol.Fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.p_kontrol.R;

import de.hdodenhof.circleimageview.CircleImageView;


public class FragTipBobble extends Fragment implements View.OnClickListener {


    private TextView readMore;
    private TextView tip;
    private CircleImageView tipPerson;
    private String string;
    private View view;
    private boolean dragState;

    private View con;

    private FragBottomMenu.OnFragmentInteractionListener mListener;


    public FragTipBobble() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_tip_bobble, container, false);




        tip = (TextView) view.findViewById(R.id.bobbelTiptextView);
        readMore = (TextView) view.findViewById(R.id.bobbelTipreadMore);
        readMore.setOnClickListener(this);
        con = view.findViewById(R.id.fagTip);
        con.setOnClickListener(this);
        tipPerson = (CircleImageView) view.findViewById(R.id.bobbelTipImg);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.dontAnimate();
        Glide.with(FragTipBobble.this).load(R.drawable.tipprofileimg).into(tipPerson);
        string = getString(R.string.tip1);
        if (string.length() > 65)
        {
            string = string.substring(0, 65) + "...";
        }
        tip.setText(string);



        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        if (v == con){
            con.setVisibility(View.GONE);
        } else if (v == readMore){
            readMore.setText("ikke lavet endnu");
        }

    }


    public void showTip(){
        con.setVisibility(View.VISIBLE);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
