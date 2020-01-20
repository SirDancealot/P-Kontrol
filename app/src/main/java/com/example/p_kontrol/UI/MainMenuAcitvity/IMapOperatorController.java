package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;

public interface IMapOperatorController{

    /**
     * implements interface IMapOperatorController.
     * to controll what happens when a Tip marker is clicked on the map.
     */
    void onTipClick(int index);

    /**
     * implements interface IMapOperatorController.
     * to controll what happens when the CenterButton is clicked.
     */
    void onCenterClick(View v);

}