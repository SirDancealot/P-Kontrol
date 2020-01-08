package com.example.p_kontrol.Backend;



import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;


public class BackendStub {





    //Test data creation


    private GeoPoint dtulocaiton1 = new GeoPoint(55.78256, 12.51958);
    private GeoPoint dtulocaiton2 = new GeoPoint(55.78266, 12.51968);
    private GeoPoint dtulocaiton3 = new GeoPoint(55.78296, 12.51998);
    long millis = System.currentTimeMillis();
    Date date = new Date(millis);



    UserDTO user = new UserDTO("valdemar", "h", "niceimg");

    //IUserDTO author, String message, LatLng location, int rating, Date creationDate
    ITipDTO tip1 = new TipDTO(user, "test Message", new LatLng(dtulocaiton1.getLatitude(), dtulocaiton1.getLongitude()), 4, date);
    ITipDTO tip2 = new TipDTO(user, "test Message", new LatLng(dtulocaiton2.getLatitude(), dtulocaiton2.getLongitude()), 4, date);
    ITipDTO tip3 = new TipDTO(user, "test Message", new LatLng(dtulocaiton3.getLatitude(), dtulocaiton3.getLongitude()), 4, date);

    List<ITipDTO> demoList = new ArrayList<>();









    public List<ITipDTO> getDemoTips() {
        demoList.add(tip1);
        demoList.add(tip2);
        demoList.add(tip3);

        return demoList;
    }

}
