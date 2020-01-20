package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.p_kontrol.R;
/**
 * @responsibilty to contain all the responsibility for initiating, knowing, and using the overlaying fragments of the app.
 * */
class ComponentMenuOperator implements View.OnClickListener, IMenuOperator{

    private IMenuOperationsController context;
    private String TAG = this.getClass().getName();

    // Menu Views.
    private View menuBtnContainer, dragHandle;
    private Button  menuBtn_profile      ,menuBtn_FreePark   ,menuBtn_Contribute ,
                    menuBtn_Community   ,menuBtn_Parking    ,menuBtn_PVagt      ;

    // menu Open or Close State
    private boolean drag_State      = false ;
    private boolean stateFreePark   = false ;
    private boolean stateParking    = false ;

    /**
     * @responsibilty to contain all the responsibility for initiating, knowing, and using the overlaying fragments of the app.
     *  ComponentMenuOperator is the Component which has the Delegated responsibility to Manage the menu.
     *  @param context       is an interface IMenuOperationsController to manage callbacks, because this class does not have the responsibility to manage what happens on menubuttons clicks. that is reserved for the context
     *  @param view          the layout view, needed to search for xml views in the layout.
     *
     *  the relevant interface
     *  @see {@link com.example.p_kontrol.UI.MainMenuAcitvity.IMenuOperationsController}
     *
     * */
    ComponentMenuOperator(IMenuOperationsController context, View view){
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
     * @inheritDoc
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
     * @inheritDoc
     * */
    @Override
    public void closeMenu() {
        menuBtnContainer.setVisibility(View.GONE);
        drag_State = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void openMenu() {
        menuBtnContainer.setVisibility(View.VISIBLE);
        drag_State = true;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public boolean isMenuOpen() {
        return drag_State;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void toggleMenuBtnFreePark() {
        if(stateFreePark){
            menuBtn_FreePark.setBackgroundResource(R.color.color_pureWhite);
        }else{
            menuBtn_FreePark.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        stateFreePark = !stateFreePark;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void toggleMenuBtnParking(){
        if(stateParking){
            menuBtn_Parking.setBackgroundResource(R.color.color_pureWhite);
        }else{
            menuBtn_Parking.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        stateParking = !stateParking;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void deToggleMenuButton(){
        if(stateFreePark){
            menuBtn_FreePark.setBackgroundResource(R.color.color_pureWhite);
            stateFreePark = !stateFreePark;
        }
        if(stateParking){
            menuBtn_Parking.setBackgroundResource(R.color.color_pureWhite);
            stateParking = !stateParking;
        }
    }

    /** @inheritDoc */
    @Override
    public void setMenuHandleVisibility(int visibility){
        dragHandle.setVisibility(visibility);
    }
}