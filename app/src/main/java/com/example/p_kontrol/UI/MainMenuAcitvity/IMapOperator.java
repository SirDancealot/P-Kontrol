package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;

import com.example.p_kontrol.UI.Map.IState;

//InterFaces Map
interface IMapOperator{

    void updatePermissions();
    IState getCurrentState();

    void visibilityOfInteractBtns(int visibility);
    void onAcceptClick(View.OnClickListener onclick);
    void onCancelClick(View.OnClickListener onclick);

    void setStateSelection();
    void setStateStandby();
    void setStateParking();
    void centerOnUserLocation();
}

// interfaces Menu

