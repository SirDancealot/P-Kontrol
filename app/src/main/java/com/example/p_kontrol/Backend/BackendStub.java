package com.example.p_kontrol.Backend;



import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.DataTypes.PVagtDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;


public class BackendStub implements IBackend{

    //Test data creation
    private GeoPoint dtulocaiton1 = new GeoPoint(55.98256, 12.51958);
    private GeoPoint dtulocaiton2 = new GeoPoint(55.18266, 12.51968);
    private GeoPoint dtulocaiton3 = new GeoPoint(55.78396, 12.51998);
    long millis = System.currentTimeMillis();
    Date date = new Date(millis);

    UserDTO user = new UserDTO("valdemar", "h", "niceimg");


    ATipDTO tip1 = new TipDTO(user, "test Message", 4, date, new GeoPoint(dtulocaiton1.getLatitude(), dtulocaiton1.getLongitude()));
    ATipDTO tip2 = new TipDTO(user, "test Message", 4, date, new GeoPoint(dtulocaiton2.getLatitude(), dtulocaiton2.getLongitude()));
    ATipDTO tip3 = new TipDTO(user, "test Message", 4, date, new GeoPoint(dtulocaiton3.getLatitude(), dtulocaiton3.getLongitude()));

    List<ATipDTO> demoList = new ArrayList<>();
    List<PVagtDTO> l;

    MutableLiveData<List<PVagtDTO>> pvagtlist;


    static IBackend backend = null;

    public BackendStub(){
        demoList.add(tip1);
        demoList.add(tip2);
        demoList.add(tip3);
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
    public List<ATipDTO> getTips(LatLng location, MutableLiveData<List<ATipDTO>> list) {
        // todo ViewModel Se Her
        list.setValue(demoList);
        return demoList;
    }

    @Override
    public void createTip(ATipDTO tip) {
        tip.setAuthor(user);
        tip.setCreationDate( new Date() );
        demoList.add(tip);
    }

    @Override
    public void rateTip(int star, ATipDTO tip) {

    }

    @Override
    public void getPVagter(LatLng location, MutableLiveData<List<PVagtDTO>> list) {

        this.pvagtlist = list;



    }

    public void createPVagt(PVagtDTO vagt){





        l.add(vagt);
        pvagtlist.setValue(l);

    }

    @Override
    public AUserDTO getUser(int id) {
        return user;
    }

    @Override
    public boolean postFeedback(String category, String message) {
        return false;
    }
}
