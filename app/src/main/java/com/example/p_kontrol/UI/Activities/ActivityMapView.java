package com.example.p_kontrol.UI.Activities;

import android.content.Intent;
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
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Contexts.IMapContextListener;
import com.example.p_kontrol.UI.Contexts.IMapStateListener;
import com.example.p_kontrol.UI.Contexts.MapContext;
import com.example.p_kontrol.UI.Fragments.FragMessageWrite;
import com.example.p_kontrol.UI.Fragments.FragTipBobble;
import com.example.p_kontrol.UI.Fragments.FragTopMessageBar;
import com.example.p_kontrol.UI.Fragments.IFragWriteMessageListener;
import com.example.p_kontrol.UI.Services.ITipDTO;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class ActivityMapView extends AppCompatActivity implements View.OnClickListener {

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

    //WriteTip
    final ITipDTO newTipDTO = new TipDTO();

    // Maps
    MapContext mapContext;
    IMapContextListener mapListener;
    private GoogleMap googleMap;
   //private final LatLng mDefaultLocation = new LatLng(55.676098, 	12.56833);
    private Button centerBtn, acceptBtn;
    private FusedLocationProviderClient mFusedLocationProviderClient;

    private TipDTO currentTip;

    private boolean tempBool = false;
    private int tempID = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_view);

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

            }

            @Override
            public void onChangeState() {

            }

            @Override
            public void onSelectedLocation() {

            }

            @Override
            public void onUpdate(){
                // todo get backend List of Tips.

                List<ITipDTO> newListofDTOs = null;
                mapContext.setListOfTipDto(null);
/*
                // kald den metode du gerne vil have
                Toast.makeText(ActivityMapView.this, "tip med id: " + marker.getTitle(), Toast.LENGTH_SHORT).show();

                adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, newListofDTOs);
                viewPager_tipBobles.setAdapter(adapter_TipBobbles);
                viewPager_tipBobles.setCurrentItem(Integer.parseInt(marker.getTitle()) - 1);
                viewPager_tipBobles.setVisibility(View.VISIBLE);*/
            }
        };
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapContext = new MapContext(
                mapFrag,
                this,
                googleMap,
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

                // TipBobbleViewPager
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
        acceptBtn.setEnabled(false);

        fragment_messageWrite = new FragMessageWrite();
        FragmentToogleTransaction(R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;
        fragment_messageWrite.setFragWriteMessageListener(new IFragWriteMessageListener() {
            @Override
            public void OnMessageDone(ITipDTO dto) {
                newTipDTO.setMessage(dto.getMessage());
              /*  dto.setLocation(currentMarker);
                // todo give a User to the Tip.
                dtoList.add(dto);
                zoomCamara(DEFAULT_ZOOM);
                updateMapTips(dtoList);*/
            }
            @Override
            public void OnClose(){
                FragmentToogleTransaction(R.id.midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
                boolFragMessageWrite =!boolFragMessageWrite;
            }
        });

        mapContext.setStateLocationSelect(new IMapStateListener(){
            @Override
            public void onAcceptButton(LatLng location){
                newTipDTO.setLocation(location);
                acceptBtn.setVisibility(View.GONE);
            }
        });

        //
        //todo set Author of newTipDTO;
        //todo set Tip ontoMap.
        //todo send Tip to BackEnd

        Log.i("click", "Contribute btn clicked \n");

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

}
