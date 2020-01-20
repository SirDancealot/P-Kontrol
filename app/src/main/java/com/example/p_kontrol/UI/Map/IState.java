package com.example.p_kontrol.UI.Map;

import androidx.lifecycle.LiveData;

import com.example.p_kontrol.DataTypes.TipDTO;

import java.util.List;

public interface IState {

    void setDoneListner(IMapFragmentListener listenerDone);
    void centerMethod();
    void updateMap(List<TipDTO> list);
}
