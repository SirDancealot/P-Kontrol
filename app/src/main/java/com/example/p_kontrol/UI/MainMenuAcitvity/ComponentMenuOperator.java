package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.p_kontrol.R;
/**
 * @responsibilty to contain all the responsibility for initiating, knowing, and using the overlaying fragments of the app.
 * */
class ComponentMenuOperator implements View.OnClickListener, IMenuOperator{

    private IMenuOperationsController context;
    private String TAG = this.getClass().getName();

    // Menu Views.
    private ImageView dragImage;
    private View menu_hiddenRow, dragHandle;
    private Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
                    menuBtn_Community   ,menuBtn_Parking    ,menuBtn_PVagt      ;

    // menu Open or Close State
    private boolean drag_State      = false , stateFreePark   = false , stateParking    = false ,
                    contributeState = false ;

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
        dragImage       = view.findViewById(R.id.menuBtn_draggingHandle_image)  ;
        menu_hiddenRow = view.findViewById(R.id.menu_row_2)                     ;
        dragHandle           = view.findViewById(R.id.menuBtn_draggingHandle)   ;

        // Menu Category Buttons
        menuBtn_profile      = view.findViewById(R.id.menuBtn_profile)          ;
        menuBtn_FreePark     = view.findViewById(R.id.menuBtn_FreePark)         ;
        menuBtn_Contribute   = view.findViewById(R.id.menuBtn_CreateTip)        ;
        menuBtn_Community    = view.findViewById(R.id.menuBtn_FeedBack)         ;
        menuBtn_Parking = view.findViewById(R.id.menuBtn_ParkHere)              ;
        menuBtn_PVagt        = view.findViewById(R.id.menuBtn_PVagt)            ;

        // Setting Listeners
        dragHandle.setOnClickListener(this);
        menuBtn_profile.setOnClickListener(this);
        menuBtn_FreePark.setOnClickListener(this);
        menuBtn_Contribute.setOnClickListener(this);
        menuBtn_Community.setOnClickListener(this);
        menuBtn_Parking.setOnClickListener(this);
        menuBtn_PVagt.setOnClickListener(this);

        // Setup Menu Toggle Position
        menu_hiddenRow.setVisibility(View.GONE);
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
            case (R.id.menuBtn_FeedBack):
                context.menuBtn_FeedBack();
                break;
            case (R.id.menuBtn_ParkHere):
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
            closeMenu();
        }else{
            openMenu();
        }
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void closeMenu() {
        dragImage.setImageResource(R.drawable.ic_dragginghandle_closedmenu);
        menu_hiddenRow.setVisibility(View.GONE);
        drag_State = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void openMenu() {
        dragImage.setImageResource(R.drawable.ic_dragginghandle_openmenu);
        menu_hiddenRow.setVisibility(View.VISIBLE);
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
        deToggleMenuButtons_graficsOnly();
        if (!stateFreePark) {
            menuBtn_FreePark.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        stateFreePark = !stateFreePark;
        stateParking = false;
        contributeState = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void toggleMenuBtnParking(){
        deToggleMenuButtons_graficsOnly();
        if (!stateParking){
            menuBtn_Parking.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        stateParking = !stateParking;
        stateFreePark = false ;
        contributeState = false;
    }

    /**
     * @inheritDoc
     * */
    @Override
    public void toggleMenuBtnContribute(){
        deToggleMenuButtons_graficsOnly();
        if (!contributeState) {
            menuBtn_Contribute.setBackgroundResource(R.drawable.shape_squarerounded_full_matwhite);
        }
        contributeState = !contributeState;
        stateParking = false;
        stateFreePark = false ;
    }


    private void deToggleMenuButtons_graficsOnly(){
            menuBtn_FreePark.setBackgroundResource(R.color.color_pureWhite);
            menuBtn_Parking.setBackgroundResource(R.color.color_pureWhite);
            menuBtn_Contribute.setBackgroundResource(R.color.color_pureWhite);
    }

}