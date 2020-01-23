package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;

import com.example.p_kontrol.UI.Map.State;

/**
 * @responsibilty to specify methods to operate the Map.
 * */
interface IMapOperator{

    void visibilityOfInteractBtns(int visibility);

    // Google Map Specifics
    /**
     * necesary method to update permissions of google map object.
     * */
    void updatePermissions();

    // onClicks
    /**
     * because the IMap Operator has the responsibility to setup the View connected to the map, it has these to call the specifik listeners at a given time
     * sets the AcceptButton onclick method
     * */
    void onAcceptClick(View.OnClickListener onclick);
    /**
     * because the IMap Operator has the responsibility to setup the View connected to the map, it has these to call the specifik listeners at a given time
     * sets the AcceptButton onclick method
     * */
    void onCancelClick(View.OnClickListener onclick);
    /**
     * because the IMap Operator has the responsibility to setup the View connected to the map, it has these to call the specifik listeners at a given time
     * sets the Center onclick method
     * */
    void onCenterClick(View.OnClickListener onclick);
    /**
     * uses the Abstract class method center();
     * @See {@link com.example.p_kontrol.UI.Map.State}
     * */
    void centerOnUserLocation();


    // Setting States
    /**
     * activates the Selection State on the Map
     * */
    void setStateSelection();
    /**
     * activates the Standby State on the Map
     * */
    void setStateStandby();
    /**
     * activates the Parking State on the Map
     * */
    void toggleStateParking();

    /**
     * activates the Free Park State on the Map
     * Free park is essentialy a filter to only see the tips in the free parking category.
     * */
    void toggleStateFreePark();
    /**
     * @return State implementation, it is used to check what state you are in, foreksample in the backstack handleing.
     * */
    State getCurrentState();

    void onCenterClick(View v);
}

// interfaces Menu

