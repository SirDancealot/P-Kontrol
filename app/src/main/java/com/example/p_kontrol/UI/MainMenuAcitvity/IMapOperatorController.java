package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;
/**
 * @responsibilty to specify listener calls to the MapOperator it can call when are clicked.
 * */
public interface IMapOperatorController{

    /**
     * implements interface IMapOperatorController.
     * to controll what happens when a Tip marker is clicked on the map.
     */
    void onTipClick(String index);
}