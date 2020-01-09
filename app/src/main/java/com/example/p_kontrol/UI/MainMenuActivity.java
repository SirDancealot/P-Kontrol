package com.example.p_kontrol.UI;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;


import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.Map.IMapContextListener;
import com.example.p_kontrol.UI.Map.IMapSelectedLocationListener;
import com.example.p_kontrol.UI.Map.MapContext;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.ReadTips.FragTipBobble;
import com.example.p_kontrol.UI.ReadTips.FragTopMessageBar;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */

public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int STANDARD_TIP_BEGIN_RATING = 0;
    final String TAG = "MainMenuActivity";

// -- * -- MENU -- * --

    // Menu Views.
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    // menu Open or Close State
    boolean drag_State;

// -- * -- FRAGMENTS -- * --

    //Fragments
    FragMessageWrite    fragment_messageWrite   ;
    FragTipBobble       fragment_tipBobble      ;
    FragTopMessageBar   fragment_topMessage     ;

    //ViewPager - Tip bobbles.
    FragmentPagerAdapter adapter_TipBobbles;
    ViewPager viewPager_tipBobles;

    //Specials
    FragmentManager fragmentManager;
    FragmentTransaction transaction;

    //Booleans for Open Closing Fragments.
    boolean boolFragMessageWrite    ;
    boolean boolFragTipBobble       ;
    boolean boolFragTopMessageBar   ;

// -- * -- MAP -- * --

    //Maps
    MapContext mapContext               ;
    IMapContextListener mapListener     ;

// -- * -- Local DATA objects -- * --

    // in order to create new TipDto's from static contexts a Final Object is required, this object will then often be overwritten
    // todo make I_TIPDTO when Copy() has been added to interface.
    final TipDTO newTipDTO = new TipDTO();
    List<ITipDTO> dtoList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        // todo Fjern og få dem fra BackEnd.
        setUpDemoTip();

        fragmentManager = this.getSupportFragmentManager();
        setupMenu();
        setupFragments();
        setupMap();
    }

    // setups called by onCreate.
    private void setupMenu(){

        // Menu Buttons.
        menuBtnContainer     = findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = findViewById(R.id.menuBtn_draggingHandle)      ;

        // Menu Category Buttons
        menuBtn_profile      = findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = findViewById(R.id.menuBtn_Contribute)          ;
        menuBtn_Community    = findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = findViewById(R.id.menuBtn_PVagt)               ;

        // Setting Listeners
        dragHandle.setOnClickListener(this);
        menuBtn_profile.setOnClickListener(this);
        menuBtn_FreePark.setOnClickListener(this);
        menuBtn_Contribute.setOnClickListener(this);
        menuBtn_Community.setOnClickListener(this);
        menuBtn_ParkAlarm.setOnClickListener(this);
        menuBtn_PVagt.setOnClickListener(this);

        // Setup Menu Toggle Position
        drag_State = false;
        menuBtnContainer.setVisibility(View.GONE);
    }
    private void setupFragments(){

        //TipBobbles are all inside this ViewPager Container
        viewPager_tipBobles = findViewById(R.id.mainMenu_viewPager_TipBobbles);

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;
        boolFragTopMessageBar   = false;

        fragment_messageWrite = new FragMessageWrite()  ;
        fragment_tipBobble    = new FragTipBobble()     ;
        fragment_topMessage   = new FragTopMessageBar() ;
    }
    private void setupMap(){

        //Map Interaction Buttons
        Button centerBtn = findViewById(R.id.mainMenu_Map_centerBtn);
        Button acceptBtn = findViewById(R.id.mainMenu_map_acceptBtn);
        Button cancelBtn = findViewById(R.id.mainMenu_map_cancelBtn);
        View btnContainerAceptCancel = findViewById(R.id.mainMenu_acceptCancelContainer);

        // Listener
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

        // Actually Making the Map Do things
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mainMenu_map);
        mapContext = new MapContext( mapFrag,this,centerBtn,cancelBtn,acceptBtn,btnContainerAceptCancel,mapListener);

    }

    // onClick Methods.
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

        // starting Contribute process at index 0. meaning the very first step.
        contributeProcess(0);

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

    // Methods to implement menuOnClicks()
    private void contributeProcess(int i){
        switch (i){
            case 0: // Chose location
                mapContext.setStateSelectLocation(new IMapSelectedLocationListener() {
                    @Override
                    public void onSelectedLocation(LatLng location) {
                        newTipDTO.setLocation(location); // newTipDTO is a static object that can always be called
                        mapContext.setStateStandby();
                        contributeProcess(1);
                    }

                    @Override
                    public void onCancelSelection() {
                        mapContext.setStateStandby();
                    }


                });
                break;
            case 1: // Write Tip
                Log.i(TAG, "contributeProcess: 1");
                fragment_messageWrite = new FragMessageWrite();
                Log.i(TAG, "contributeProcess: 2");
                toogleFragment_WriteTip(true);
                Log.i(TAG, "contributeProcess: 3");
                fragment_messageWrite.setFragWriteMessageListener(new ITipWriteListener() {

                    @Override
                    public void onMessageDone(ITipDTO dto) {
                        newTipDTO.setMessage(dto.getMessage());// newTipDTO is a static object that can always be called
                        toogleFragment_WriteTip(false);
                        getSupportFragmentManager().popBackStack();
                        contributeProcess(2); // calls self to Complete the tip
                    }

                    @Override
                    public void onCancelTip(){
                        toogleFragment_WriteTip(false);
                        // does not call self to complete.
                    }

                });

                break;
            case 2: // finish Tip and send to back end for saving.
                //todo simplify user Data
                // fix this with the login.
                UserDTO currentUser = new UserDTO( "tempUser", "tempLastName", "");
                newTipDTO.setCreationDate(new Date(System.currentTimeMillis()));
                newTipDTO.setAuthor(currentUser);
                TipDTO tipDTO = newTipDTO.copy();
                dtoList.add(tipDTO);
                mapContext.updateMap();

                //todo Implement Backend CreateTip here.
                break;
        }
    }

    // Open And Close Fragments.
    private void FragmentToogleTransaction(int containerId, Fragment fragment, boolean Open){

        if(Open){
            transaction = fragmentManager.beginTransaction();
            try {
                Log.v("transaction", "Adding fragment");
                transaction.add(containerId, fragment);
            }catch (IllegalStateException e){
                Log.v("transaction", "Replacing fragment");
                transaction.replace(containerId, fragment);
            }
            transaction.addToBackStack(null);
            transaction.commit();
        }else{
            transaction = fragmentManager.beginTransaction();
            transaction.remove(fragment);
            transaction.commit();
            Log.v("transaction","Removing fragment");
        }
    }
    public void toogleFragment_WriteTip(){
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;
    }
    public void toogleFragment_WriteTip(boolean openValue) {
        try {
            FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite, openValue);
            boolFragMessageWrite = openValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    // specially TipVViewPager
    public void CloseTipBobbleViewPager(){
        viewPager_tipBobles.setVisibility(View.GONE);
        Log.i(TAG, "CloseTipBobbleViewPager: Closed");
    }

    //todo remove this.
    private void setUpDemoTip(){
        ITipDTO tip1 = new TipDTO();
        tip1.setLocation(new LatLng(	55.676098, 	12.568337));
        tip1.setAuthor(new UserDTO("August","the Non-Human","https://graph.facebook.com/" + "1224894504" + "/picture?type=normal"));
        tip1.setMessage(getResources().getString(R.string.tip1));
        Date date = new Date(1563346249);
        tip1.setCreationDate(date);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            LocalDate tempDate = LocalDate.of(2019, 2, 9);
            //tip1.setDate(tempDate);

        }

        ITipDTO tip2 = new TipDTO();
        tip2.setLocation(new LatLng(	55.679098, 	12.569337));
        tip2.setAuthor(new UserDTO("","Hans","https://graph.facebook.com/" + "100009221661122" + "/picture?type=normal"));
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


    // Get and Sets
    public List<ITipDTO> getDTOList(){
        return dtoList;
    }


    @Override
    public void onBackPressed() {
        if (mapContext.getCurrentState() instanceof StateSelectLocation)
            ((StateSelectLocation) mapContext.getCurrentState()).cancelMethod();
        else if (drag_State)
            menu_dragHandle(findViewById(R.id.menu_btnContainer));
        else
            super.onBackPressed();
    }
}
