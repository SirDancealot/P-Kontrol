package com.example.p_kontrol.UI.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.p_kontrol.UI.Activities.ActivityProfile;
import com.example.p_kontrol.R;


public class FragBottomMenu extends Fragment {

    // Menu Code.
    View menuBtnContainer       ;
    View dragHandle          ;

    Button menuBtn_profile   ;
    Button menuBtn_FreePark  ;
    Button menuBtn_Contribute;
    Button menuBtn_Community ;
    Button menuBtn_ParkAlarm ;
    Button menuBtn_PVagt     ;

    View view;
    boolean drag_State;

    private OnFragmentInteractionListener mListener;

    public FragBottomMenu() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_frag_bottom_menu, container, false);

       // Menu Buttons.
        menuBtnContainer     = (View)   view.findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = (View)   view.findViewById(R.id.menuBtn_draggingHandle)      ;

        menuBtn_profile      = (Button) view.findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = (Button) view.findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = (Button) view.findViewById(R.id.menuBtn_Contribute)          ;
        menuBtn_Community    = (Button) view.findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = (Button) view.findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = (Button) view.findViewById(R.id.menuBtn_PVagt)               ;

        setupMenuListeners(
                dragHandle          ,
                menuBtn_profile     ,
                menuBtn_FreePark    ,
                menuBtn_Contribute  ,
                menuBtn_Community   ,
                menuBtn_ParkAlarm   ,
                menuBtn_PVagt
        );

        // Setup Menu Toggle Position
        drag_State = false;

        // SetupMenuBtnListeners(menuBtn_profile, menuBtn_FreePark, menuBtn_Contribute, menuBtn_Community, menuBtn_ParkAlarm, menuBtn_PVagt);
        return view ;
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

    private void setupMenuListeners(
            View dragHandle,
            Button menuBtn_profile,
            Button menuBtn_FreePark,
            Button menuBtn_Contribute,
            Button menuBtn_Community,
            Button menuBtn_ParkAlarm,
            Button menuBtn_PVagt
    ){
        // Dragging Handle
        dragHandle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu_dragHandle(v);
            }
        });

    // Menu Buttons Row 1

        // Profile
        menuBtn_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_profile(v);
            }
        });

        // FreePark
        menuBtn_FreePark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_FreePark(v);
            }
        });

        // Contribute
        menuBtn_Contribute.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_Contribute(v);
            }
        });

    // Menu Buttons Row 2

        // Community
        menuBtn_Community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_Community(v);
            }
        });

        // Park Alarm
        menuBtn_ParkAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_ParkAlarm(v);
            }
        });

        // P-Vagt
        menuBtn_PVagt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menuBtn_PVagt(v);
            }
        });
    }

    // Dragging Handle
    private void menu_dragHandle( View view ){

        // drag state is a boolean, so if 1 its open, if 0 its closed. standard is 0.
        if(drag_State){
            Log.v("click","Menu Container Closed\n");
            menuBtnContainer.setVisibility(View.GONE);
            drag_State = false;
        }else{
            Log.v("click","Menu Container Open\n");
            menuBtnContainer.setVisibility(View.VISIBLE);
            drag_State = true;
        }

    }

    // Menu Buttons on click functions.
    private void menuBtn_profile(View view){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( getActivity() , ActivityProfile.class );
        startActivity(changeActivity);
    }
    private void menuBtn_FreePark(View view){
        Log.i("click","FreePark btn clicked \n");
    }
    private void menuBtn_Contribute(View view){
        Log.i("click","Contribute btn clicked \n");
    }
    private void menuBtn_Community(View view){
        Log.i("click","Community btn clicked \n");
    }
    private void menuBtn_ParkAlarm(View view){
        Log.i("click","Park Alarm btn clicked \n");
    }
    private void menuBtn_PVagt(View view){
        Log.i("click","P-Vagt btn clicked \n");
    }
}
