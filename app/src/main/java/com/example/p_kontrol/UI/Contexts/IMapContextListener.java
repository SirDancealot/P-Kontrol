package com.example.p_kontrol.UI.Contexts;

import com.example.p_kontrol.UI.Services.ITipDTO;

import java.util.List;

public interface IMapContextListener {

    void onReady();
    void onChangeState();
    void onSelectedLocation();
    void onUpdate();
}
