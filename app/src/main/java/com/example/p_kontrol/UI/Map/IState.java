package com.example.p_kontrol.UI.Map;

import com.example.p_kontrol.DataTypes.ATipDTO;

import java.util.List;

public interface IState {

    void setDoneListner(IMapFragmentListener listenerDone);
    void centerMethod();
    void updateMap(List<ATipDTO> list);
}
