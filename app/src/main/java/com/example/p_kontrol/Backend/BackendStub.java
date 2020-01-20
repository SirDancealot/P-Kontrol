package com.example.p_kontrol.Backend;



import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;


public class BackendStub implements IBackend{

    private String TAG = "BackendStub";

    //Test data creation
    private GeoPoint dtulocaiton1 = new GeoPoint(55.98256, 12.51958);
    private GeoPoint dtulocaiton2 = new GeoPoint(55.18266, 12.51968);
    private GeoPoint dtulocaiton3 = new GeoPoint(55.78396, 12.51998);
    long millis = System.currentTimeMillis();
    Date date = new Date(millis);
    UserInfoDTO userInfoDTO = UserInfoDTO.getUserInfoDTO();

    ITipDTO tip1 = new TipDTO();

    List<ITipDTO> demoList = new ArrayList<>();
    List<PVagtDTO> l;

    MutableLiveData<List<PVagtDTO>> pvagtlist;


    static IBackend backend = null;

    private BackendStub(){
        tip1.setAuthor(userInfoDTO.getSimpleUser());
        demoList.add(tip1);
        tip1.setMessage("test Message");
        tip1.setMessage("test Message");
        tip1.setCreationDate(new Date() );
        tip1.setL(new GeoPoint(dtulocaiton1.getLatitude(), dtulocaiton1.getLongitude()));

        l = new LinkedList<>();
        l.add(new PVagtDTO(new LatLng(55.676098,12.568337), new Date(), "123"));
        l.add(new PVagtDTO(new LatLng(55.686098,12.568337), new Date(1000), "123"));
        l.add(new PVagtDTO(new LatLng(55.696098,12.568337), new Date(), "123"));
        l.add(new PVagtDTO(new LatLng(55.626098,12.568337), new Date(1000000000), "123"));


    }



    public static IBackend getBackend(){
        if(backend == null){
           backend = new BackendStub();
        }


        return backend;
    }

    @Override
    public List<ITipDTO> getTips(LatLng location, MutableLiveData<List<ITipDTO>> list) {
        // todo ViewModel Se Her
        list.setValue(demoList);
        return demoList;
    }

    @Override
    public void createTip(ITipDTO tip) {
        tip.setAuthor(userInfoDTO.getSimpleUser());
        tip.setCreationDate( new Date() );
        demoList.add(tip);
    }

    @Override
    public void rateTip(int star, ITipDTO tip) {

    }

    @Override
    public void getPVagter(LatLng location, MutableLiveData<List<PVagtDTO>> list) {
        Log.d(TAG, "getPVagter: " + list + "{" + list.getValue() + "}");
        this.pvagtlist = list;
        pvagtlist.setValue(l);




    }

    public void createPVagt(PVagtDTO vagt){
        Log.d(TAG, "createPVagt: ");


        l.add(vagt);
        pvagtlist.setValue(l);

    }

    @Override
    public AUserDTO getUser(int id) {
        return userInfoDTO.getSimpleUser();
    }

    @Override
    public boolean postFeedback(String category, String message) {
        return false;
    }
}
