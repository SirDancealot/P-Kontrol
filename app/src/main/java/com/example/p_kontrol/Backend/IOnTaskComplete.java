package com.example.p_kontrol.Backend;



import com.example.p_kontrol.DataTypes.TipDTO;

import java.util.List;

public interface IOnTaskComplete {
    void OnTaskComplete(List<TipDTO> list);
}
