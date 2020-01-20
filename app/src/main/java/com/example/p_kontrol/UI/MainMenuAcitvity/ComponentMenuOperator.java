package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;

class ComponentMenuOperator implements View.OnClickListener, IMenuOperator{

    IMenuOperationsController context;
    private String TAG = this.getClass().getName();

    // Menu Views.
    View menuBtnContainer,dragHandle;
    Button menuBtn_profile      ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_Parking    ,menuBtn_PVagt      ;

    // menu Open or Close State
    boolean drag_State      = false ;
    boolean stateFreePark   = false ;
    boolean stateParking    = false ;

    public ComponentMenuOperator(IMenuOperationsController context, View view){
        this.context = context;

        // Menu Buttons.
        menuBtnContainer     = view.findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = view.findViewById(R.id.menuBtn_draggingHandle)      ;

        // Menu Category Buttons
        menuBtn_profile      = view.findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = view.findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = view.findViewById(R.id.menuBtn_CreateTip)          ;
        menuBtn_Community    = view.findViewById(R.id.menuBtn_Community)           ;
        menuBtn_Parking = view.findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = view.findViewById(R.id.menuBtn_PVagt)               ;

        // Setting Listeners
        dragHandle.setOnClickListener(this);
        menuBtn_profile.setOnClickListener(this);
        menuBtn_FreePark.setOnClickListener(this);
        menuBtn_Contribute.setOnClickListener(this);
        menuBtn_Community.setOnClickListener(this);
        menuBtn_Parking.setOnClickListener(this);
        menuBtn_PVagt.setOnClickListener(this);

        // Setup Menu Toggle Position
        menuBtnContainer.setVisibility(View.GONE);
    }

    // interface View.OnClickListener
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
                context.menuBtn_FeedBack();
                break;
            case (R.id.menuBtn_ParkAlarm):
                context.menuBtn_Parking();
                break;
            case (R.id.menuBtn_PVagt):
                context.menuBtn_PVagt();
                break;
        }
    }

    // Interface IMenuOperator
    /**
     *  is Called mainly from the Menu Handle.
     *  toggleMenu opens and closes the Menu repeatedly
     * */
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
    /**
     *  closes the menu
     * */
    @Override
    public void closeMenu() {
        menuBtnContainer.setVisibility(View.GONE);
        drag_State = false;
    }
    /**
     *  opens the menu
     * */
    @Override
    public void openMenu() {
        menuBtnContainer.setVisibility(View.VISIBLE);
        drag_State = true;
    }
    /**
     *  returns false if map is closed, true if it is open.
     * */
    @Override
    public boolean isMenuOpen() {
        return drag_State;
    }
    /**
     *  Enables and Disables the FreePark State.
     *  Free Park is a Filter state that only shows tips in the category of free tips.
     * */
    @Override
    public void toggleFreePark() {
        if(stateFreePark){
            menuBtn_FreePark.setBackgroundResource(R.color.color_pureWhite);
        }else{
            menuBtn_FreePark.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        stateFreePark = !stateFreePark;
    }
    /**
    * Enables and Disables the Parking State
    * Parking state is a state where you place down your location as a parking location,
    * and that location is then set to listens for P alerts.
    * */
    @Override
    public void toggleParking(){
        if(stateParking){
            menuBtn_Parking.setBackgroundResource(R.color.color_pureWhite);
        }else{
            menuBtn_Parking.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        stateParking = !stateParking;
    }
}