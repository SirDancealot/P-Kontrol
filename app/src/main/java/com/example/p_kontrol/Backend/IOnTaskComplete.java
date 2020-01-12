package com.example.p_kontrol.Backend;



import com.example.p_kontrol.DataTypes.ATipDTO;

import java.util.List;

public interface IOnTaskComplete {
    void OnTaskComplete(List<ATipDTO> list);
}
