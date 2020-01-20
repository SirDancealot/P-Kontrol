package com.example.p_kontrol.UI.Map;

import com.example.p_kontrol.DataTypes.Interfaces.ITipDTO;

import java.util.List;

public interface IState {
    /**
     * the Real implementation of the centermethod, it is individual to each state, such that it can be overidden.
     * */
    void centerMethod();
    /**
     * an Update Method, also here for the right to be overidden
     * @param list the list of tipsMarkers to set on the map.
     * */
    void updateMap(List<ITipDTO> list);
}
