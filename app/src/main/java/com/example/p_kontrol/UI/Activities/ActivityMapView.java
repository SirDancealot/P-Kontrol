package com.example.p_kontrol.UI.Activities;

import android.content.Intent;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Adapters.TipBobblesAdapter;
import com.example.p_kontrol.UI.Contexts.IMapContextListener;
import com.example.p_kontrol.UI.Contexts.IMapSelectedLocationListener;
import com.example.p_kontrol.UI.Contexts.MapContext;
import com.example.p_kontrol.UI.Fragments.FragMessageWrite;
import com.example.p_kontrol.UI.Fragments.FragTipBobble;
import com.example.p_kontrol.UI.Fragments.FragTopMessageBar;
import com.example.p_kontrol.UI.Fragments.ITipWriteListener;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import java.sql.Date;
import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class ActivityMapView extends AppCompatActivity implements View.OnClickListener {


    private static final int STANDARD_TIP_BEGIN_RATING = 0;
    final String TAG = "ActivityMapView";
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    ConstraintLayout rootContainer;
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;

    boolean drag_State;

    //Booleans for Open Closing Fragments.
    boolean boolFragMessageWrite    ;
    boolean boolFragTipBobble       ;
    boolean boolFragTopMessageBar   ;

    //ViewPager - Tip bobbles.
    FragmentPagerAdapter adapter_TipBobbles;
    ViewPager viewPager_tipBobles;

    //Fragments
    FragMessageWrite    fragment_messageWrite   ;
    FragTipBobble       fragment_tipBobble      ;
    FragTopMessageBar   fragment_topMessage     ;

    //WriteTip this is a final object, due to listener pattern requiring it as such.
    final ITipDTO newTipDTO = new TipDTO();

    // Maps
    MapContext mapContext               ;
    IMapContextListener mapListener     ;
    private Button centerBtn, acceptBtn ;
    List<ITipDTO> dtoList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

        setUpDemoTip();

        fragmentManager = this.getSupportFragmentManager();
        setupMenu();
        setupFragments();
        setupMap();
    }

    // setups belonging to onCreate
    private void setupMenu(){

        rootContainer       = findViewById(R.id.ActivityMapView_RootContainer);
        // Menu Buttons.
        menuBtnContainer     = findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = findViewById(R.id.menuBtn_draggingHandle)      ;

        menuBtn_profile      = findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = findViewById(R.id.menuBtn_Contribute)          ;
        menuBtn_Community    = findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = findViewById(R.id.menuBtn_PVagt)               ;

        dragHandle.setOnClickListener(this);
        menuBtn_profile.setOnClickListener(this);
        menuBtn_FreePark.setOnClickListener(this);
        menuBtn_Contribute.setOnClickListener(this);
        menuBtn_Community.setOnClickListener(this);
        menuBtn_ParkAlarm.setOnClickListener(this);
        menuBtn_PVagt.setOnClickListener(this);

        //Map Buttons
        acceptBtn = findViewById(R.id.contino);
        centerBtn = findViewById(R.id.centerBut);
        // map buttons recieve listeners inside of MapContext.

        // ViewPager
        viewPager_tipBobles = findViewById(R.id.viewPager_TipBobbles);

        // Setup Menu Toggle Position
        drag_State = false;
        menuBtnContainer.setVisibility(View.GONE);
    }
    private void setupFragments(){

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;
        boolFragTopMessageBar   = false;

        fragment_messageWrite = new FragMessageWrite()  ;
        fragment_tipBobble    = new FragTipBobble()     ;
        fragment_topMessage   = new FragTopMessageBar() ;
    }
    private void setupMap(){
        mapListener = new IMapContextListener() {
            @Override
            public void onReady() {
                mapContext.setListOfTipDto(dtoList);
            }

            @Override
            public void onChangeState() {

            }

            @Override
            public void onSelectedLocation() {

            }

            @Override
            public void onUpdate(){
                mapContext.setListOfTipDto(dtoList);
/*
                // kald den metode du gerne vil have
                Toast.makeText(ActivityMapView.this, "tip med id: " + marker.getTitle(), Toast.LENGTH_SHORT).show();

                adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, newListofDTOs);
                viewPager_tipBobles.setAdapter(adapter_TipBobbles);
                viewPager_tipBobles.setCurrentItem(Integer.parseInt(marker.getTitle()) - 1);
                viewPager_tipBobles.setVisibility(View.VISIBLE);*/
            }

            @Override
            public void onAcceptButton(LatLng location) {

            }

            @Override
            public void onTipClick(int index) {
                adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, dtoList);
                viewPager_tipBobles.setAdapter(adapter_TipBobbles);
                viewPager_tipBobles.setCurrentItem(index);
                viewPager_tipBobles.setVisibility(View.VISIBLE);
            }

            @Override
            public void showTipsAtIndex(int index) {

            }
        };
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapContext = new MapContext(
                mapFrag,
                this,
                centerBtn,
                acceptBtn,
                mapListener
        );
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case ( R.id.menuBtn_draggingHandle):
                menu_dragHandle(v);
                break;
                // Menu Line 1.
            case (R.id.menuBtn_profile):
                menuBtn_profile(v);
                break;
            case (R.id.menuBtn_FreePark):
                menuBtn_FreePark(v);
                break;
            case (R.id.menuBtn_Contribute):
                menuBtn_Contribute(v);
                break;
                // Menu Line 2.
            case (R.id.menuBtn_Community):
                menuBtn_Community(v);
                break;
            case (R.id.menuBtn_ParkAlarm):
                menuBtn_ParkAlarm(v);
                break;
            case (R.id.menuBtn_PVagt):
                menuBtn_PVagt(v);
                break;
        }
    }

    private void menu_dragHandle( View view ){

        // drag state is a boolean, so if 1 its open, if 0 its closed. standard is 0.
        if(drag_State){
            Log.v("click","Menu Container Closed\n");
            menuBtnContainer.setVisibility(View.GONE);
            drag_State = false;
        }else{
            Log.v("click","Menu Container Open\n");
            menuBtnContainer.setVisibility(View.VISIBLE);
            drag_State = true;
        }

    }
    private void menuBtn_profile(View view){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }
    private void menuBtn_FreePark(View view){
        Log.i("click","FreePark btn clicked \n");
        //setupTipBobblesPagerViewer();
        viewPager_tipBobles.setVisibility(View.VISIBLE);
    }
    private void menuBtn_Contribute(View view){

        // Closing the Menu down.
        menu_dragHandle(view);
        acceptBtn.setVisibility(View.VISIBLE);

        fragment_messageWrite = new FragMessageWrite();
        FragmentToogleTransaction(R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;


        fragment_messageWrite.setFragWriteMessageListener(new ITipWriteListener() {
           @Override
           public void onMessageDone(ITipDTO dto) {
               newTipDTO.setMessage(dto.getMessage());
               Log.i(TAG, "onMessageDone: BEFORE SET STATE SELECT LOCATION ");
               hideUIWriteTip();

               mapContext.setStateSelectLocation(new IMapSelectedLocationListener() {

                   @Override
                   public void onSelectedLocation(LatLng location) {
                       newTipDTO.setLocation(location);
                       Log.i(TAG, "onSelectedLocation:  Before Creating TIP ");
                       // todo set this copy self into Interface. such that type casting is unesesary.
                       //IUserDTO author, String message, LatLng location, int rating, Date creationDate
                       createtip( new TipDTO( UserInfoDTO.getUserInfoDTO().getUserDTO() ,newTipDTO.getMessage(),location,STANDARD_TIP_BEGIN_RATING, null));
                       mapContext.setStateStandby();
                       acceptBtn.setVisibility(View.GONE);
                   }

               });
           }
           @Override
           public void onCancelTip() {

           }
       });



        /*ITipDTO returnDTO = newTipDTO.copy();

        //todo set Author of newTipDTO;

        //todo set Tip ontoMap.
        List<>.add(returnDTO);
        map.updateTips(List);
        //todo send Tip to BackEnd
        backend.WriteTip(returnDTO);
        Log.i("click", "Contribute btn clicked \n");*/

    }
    private void menuBtn_Community(View view){
        Log.i("click","Community btn clicked \n");

    }
    private void menuBtn_ParkAlarm(View view){
        Log.i("click","Park Alarm btn clicked \n");
    }
    private void menuBtn_PVagt(View view){
        Log.i("click","P-Vagt btn clicked \n");

    }

    // Open And Close a Fragment.
    private void FragmentToogleTransaction(int containerId, Fragment fragment, boolean Open){

            if(!Open){
                transaction = fragmentManager.beginTransaction();
                try {
                    Log.v("transaction", "Adding fragment");
                    transaction.add(containerId, fragment);
                }catch (IllegalStateException e){
                    Log.v("transaction", "Replacing fragment");
                    transaction.replace(containerId, fragment);
                }
                transaction.commit();
            }else{
                transaction = fragmentManager.beginTransaction();
                transaction.remove(fragment);
                transaction.commit();
                Log.v("transaction","Removing fragment");
            }
    }

    // TipBobbles View Pager
    public void CloseTipBobbleViewPager(){
      //  rootContainer.removeView(viewPager_tipBobles);
        viewPager_tipBobles.setVisibility(View.GONE);
        Log.i(TAG, "CloseTipBobbleViewPager: Closed");
    }

    private void setUpDemoTip(){
        ITipDTO tip1 = new TipDTO();
        tip1.setLocation(new LatLng(	55.676098, 	12.568337));
        tip1.setAuthor(new UserDTO(1,"August","the Non-Human","https://graph.facebook.com/" + "1224894504" + "/picture?type=normal"));
        tip1.setMessage(getResources().getString(R.string.tip1));
        Date date = new Date(1563346249);
        tip1.setCreationDate(date);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate tempDate = LocalDate.of(2019, 2, 9);
            //tip1.setDate(tempDate);

        }

        ITipDTO tip2 = new TipDTO();
        tip2.setLocation(new LatLng(	55.679098, 	12.569337));
        tip2.setAuthor(new UserDTO(2,"Hans","the Human","https://graph.facebook.com/" + "100009221661122" + "/picture?type=normal"));
        tip2.setMessage(getResources().getString(R.string.tip2));
        date = new Date(1543346249);
        tip2.setCreationDate(date);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate tempDate = LocalDate.of(2019, 7, 13);
            //tip2.setDate(tempDate);
        }

        dtoList.add(tip1);
        dtoList.add(tip2);

    }


    public void hideUIWriteTip(){
        FragmentToogleTransaction(R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;
    }
    public void createtip(ITipDTO dto){
        Log.i(TAG, "createtip:  Before Creating TIP ");
        //todo Implement Backend CreateTip here.
        dtoList.add(dto);
    }
    public void markerIsClick(int index){
        adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, dtoList);
        viewPager_tipBobles.setAdapter(adapter_TipBobbles);
        viewPager_tipBobles.setCurrentItem(index);
        viewPager_tipBobles.setVisibility(View.VISIBLE);
    }
}
