package com.example.p_kontrol.Backend;

import com.example.p_kontrol.UI.Services.ITipDTO;
import com.example.p_kontrol.UI.Services.IUserDTO;
import com.google.android.gms.maps.model.LatLng;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import com.example.p_kontrol.UI.Services.TipDTO;
import com.example.p_kontrol.UI.Services.UserDTO;
import com.google.firebase.firestore.GeoPoint;


public class BackendStub {





    //Test data creation


    private GeoPoint dtulocaiton1 = new GeoPoint(55.78256, 12.51958);
    private GeoPoint dtulocaiton2 = new GeoPoint(55.78266, 12.51968);
    private GeoPoint dtulocaiton3 = new GeoPoint(55.78296, 12.51998);
    long millis = System.currentTimeMillis();
    Date date = new Date(millis);


    UserDTO user = new UserDTO("valdemar", "h", "niceimg");

    TipDTO tip1 = new TipDTO("test Message", 4, user, dtulocaiton1, date);
    TipDTO tip2 = new TipDTO("test Message", 4, user, dtulocaiton2, date);
    TipDTO tip3 = new TipDTO("test Message", 4, user, dtulocaiton3, date);

    List<TipDTO> demoList = new ArrayList<>();









    public List<TipDTO> getDemoTips() {
        demoList.add(tip1);
        demoList.add(tip2);
        demoList.add(tip3);

        return demoList;
    }

}
