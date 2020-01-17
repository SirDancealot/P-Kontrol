package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

class CompositionMenuOperator   implements View.OnClickListener, IMenuOperator{

    IMenuOperationsController context;
    private String TAG = this.getClass().getName();

    // Menu Views.
    View menuBtnContainer,dragHandle;
    Button menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    // menu Open or Close State
    boolean drag_State;

    public CompositionMenuOperator(IMenuOperationsController context, View view){
        this.context = context;

        // Menu Buttons.
        menuBtnContainer     = view.findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = view.findViewById(R.id.menuBtn_draggingHandle)      ;

        // Menu Category Buttons
        menuBtn_profile      = view.findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = view.findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = view.findViewById(R.id.menuBtn_CreateTip)          ;
        menuBtn_Community    = view.findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = view.findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = view.findViewById(R.id.menuBtn_PVagt)               ;

        // Setting Listeners
        dragHandle.setOnClickListener(this);
        menuBtn_profile.setOnClickListener(this);
        menuBtn_FreePark.setOnClickListener(this);
        menuBtn_Contribute.setOnClickListener(this);
        menuBtn_Community.setOnClickListener(this);
        menuBtn_ParkAlarm.setOnClickListener(this);
        menuBtn_PVagt.setOnClickListener(this);

        // Setup Menu Toggle Position
        drag_State = false;
        menuBtnContainer.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case ( R.id.menuBtn_draggingHandle):
                toggleMenu();
                break;
            // Menu Line 1.
            case (R.id.menuBtn_profile):
                context.menuBtn_profile();
                break;
            case (R.id.menuBtn_FreePark):
                context.menuBtn_FreePark();
                break;
            case (R.id.menuBtn_CreateTip):
                context.menuBtn_Contribute();
                break;
            // Menu Line 2.
            case (R.id.menuBtn_Community):
                context.menuBtn_Community();
                break;
            case (R.id.menuBtn_ParkAlarm):
                context.menuBtn_ParkAlarm();
                break;
            case (R.id.menuBtn_PVagt):
                context.menuBtn_PVagt();
                break;
        }
    }

    // Interface
    @Override
    public void toggleMenu( ){
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
    @Override
    public void closeMenu() {
        menuBtnContainer.setVisibility(View.GONE);
        drag_State = false;
    }
    @Override
    public void openMenu() {
        menuBtnContainer.setVisibility(View.VISIBLE);
        drag_State = true;
    }
    @Override
    public boolean isMenuOpen() {
        return drag_State;
    }
}