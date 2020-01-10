package com.example.p_kontrol.UI.Map;

import android.view.View;

public class StateStandby extends State {

    public StateStandby(MapContext context) {
        super(context);
        btnContainerAcceptCancel.setVisibility(View.GONE);
    }

    @Override
    public void centerMethod(){
        centerMapOnLocation();
    }

}
