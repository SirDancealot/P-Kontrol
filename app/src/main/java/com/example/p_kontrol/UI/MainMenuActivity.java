package com.example.p_kontrol.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
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

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.IBackend;

import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Feedback.ActivityFeedback;
import com.example.p_kontrol.UI.Map.IMapContextListener;
import com.example.p_kontrol.UI.Map.IMapSelectedLocationListener;
import com.example.p_kontrol.UI.Map.MapContext;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.example.p_kontrol.UI.ReadTips.FragTipBobble;
import com.example.p_kontrol.UI.ReadTips.FragTopMessageBar;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;


import java.time.LocalDate;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
/**
 * @responsibilty responsibility to Handle UI interaction on this XML layout.
 *
 * */
public class MainMenuActivity extends AppCompatActivity implements View.OnClickListener {

// Local Data Objects

    final static TipDTO newTipDTO = new TipDTO(); // Static methods require a Static object to maneuver
    static IBackend backend = new Backend();
    final String TAG = "MainMenuActivity";

// -- * -- MENU -- * --

    // Menu Views.
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    // menu Open or Close State
    boolean drag_State;

// -- * -- FRAGMENTS -- * --

    FragMessageWrite    fragment_messageWrite   ;
    FragTipBobble       fragment_tipBobble      ;
    FragTopMessageBar   fragment_topMessage     ;
    MainMenuCloseFragment fragment_close        ;

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

    MapContext mapContext               ;
    IMapContextListener mapListener     ;

// -- * -- Local DATA objects -- * --

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

        //TODO send person til logind 1 hvis de ikke er logget ind

        fragmentManager = this.getSupportFragmentManager();
        setupMenu();
        setupFragments();
        backend = new Backend();
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
        viewPager_tipBobles.setVisibility(ViewPager.GONE);

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;
        boolFragTopMessageBar   = false;

        fragment_messageWrite = new FragMessageWrite()  ;
        fragment_tipBobble    = new FragTipBobble()     ;
        fragment_topMessage   = new FragTopMessageBar() ;
        fragment_close        = new MainMenuCloseFragment(this);
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
                mapContext.setListOfTipDto(getDTOList());
            }

            @Override
            public void onChangeState() {

            }

            @Override
            public void onSelectedLocation() {

            }

            @Override
            public void onUpdate(){
                mapContext.setListOfTipDto(getDTOList());
            }

            @Override
            public void onAcceptButton(LatLng location) {

            }

            @Override
            public void onTipClick(int index) {
                adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, getDTOList());
                viewPager_tipBobles.setAdapter(adapter_TipBobbles);
                viewPager_tipBobles.setCurrentItem(index);
                viewPager_tipBobles.setVisibility(View.VISIBLE);

                if(drag_State){
                    menu_dragHandle();
                }
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
                menu_dragHandle();
                break;
                // Menu Line 1.
            case (R.id.menuBtn_profile):
                menuBtn_profile();
                break;
            case (R.id.menuBtn_FreePark):
                menuBtn_FreePark();
                break;
            case (R.id.menuBtn_Contribute):
                menuBtn_Contribute();
                break;
                // Menu Line 2.
            case (R.id.menuBtn_Community):
                menuBtn_Community();
                break;
            case (R.id.menuBtn_ParkAlarm):
                menuBtn_ParkAlarm();
                break;
            case (R.id.menuBtn_PVagt):
                menuBtn_PVagt();
                break;
        }
    }
    private void menu_dragHandle( ){

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
    private void menuBtn_profile(){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }
    private void menuBtn_FreePark(){
        Log.i("click","FreePark btn clicked \n");
        //setupTipBobblesPagerViewer();
    }
    private void menuBtn_Contribute(){

        // Closing the Menu down.
        menu_dragHandle();

        // starting Contribute process at index 0. meaning the very first step.
        contributeProcess(0);

    }
    private void menuBtn_Community(){
        Log.i("click","Community btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityFeedback.class);
        startActivity(changeActivity);
    }
    private void menuBtn_ParkAlarm(){
        Log.i("click","Park Alarm btn clicked \n");
    }
    private void menuBtn_PVagt(){
        Log.i("click","P-Vagt btn clicked \n");

    }

    // Methods to implement menuOnClicks()
    private void contributeProcess ( int i){
        switch (i) {
            case 0: // Chose location

                mapContext.setStateSelectLocation(new IMapSelectedLocationListener() {
                    @Override
                    public void onSelectedLocation(LatLng location) {
                        newTipDTO.setL(new GeoPoint(location.latitude, location.longitude)); // newTipDTO is a static object that can always be called
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

                fragment_messageWrite = new FragMessageWrite();
                toogleFragment_WriteTip(true);
                fragment_messageWrite.setFragWriteMessageListener(new ITipWriteListener() {

                    @Override
                    public void onMessageDone(ITipDTO dto) {
                        newTipDTO.setMessage(dto.getMessage());// newTipDTO is a static object that can always be called
                        toogleFragment_WriteTip(false);
                        getSupportFragmentManager().popBackStack();
                        contributeProcess(2); // calls self to Complete the tip
                    }

                    @Override
                    public void onCancelTip() {
                        toogleFragment_WriteTip(false);
                        // does not call self to complete.
                    }

                });

                break;

            case 2: // finish Tip and send to back end for saving.

                //todo simplify user Data
                // fix this with the login.
                UserDTO currentUser = new UserDTO("tempUser", "tempLastName", "");
                newTipDTO.setCreationDate(new Date(System.currentTimeMillis()));
                newTipDTO.setAuthor(currentUser);
                TipDTO tipDTO = newTipDTO.copy();
                backend.createTip(tipDTO);
                mapContext.updateMap();
                backend.createTip(tipDTO);


                break;

        }
    }
    public void closeTipBobbleViewPager () {
        viewPager_tipBobles.setVisibility(View.GONE);
        Log.i(TAG, "closeTipBobbleViewPager: Closed");
    }

    // Fragments Open Close.
    private void toogleFragment_WriteTip(boolean openValue) {
        try {
            FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite, openValue);
            boolFragMessageWrite = openValue;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
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



    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Something happened");
        if (mapContext.getCurrentState() instanceof StateSelectLocation) {
            Log.d(TAG, "onBackPressed: stateSelect");
            ((StateSelectLocation) mapContext.getCurrentState()).cancelMethod();
        }
        else if (viewPager_tipBobles.getVisibility() == ViewPager.VISIBLE) {
            Log.d(TAG, "onBackPressed: viewPager");
            closeTipBobbleViewPager();
        }
        else if (drag_State) {
            Log.d(TAG, "onBackPressed: Bottom menue");
            menu_dragHandle();
        }
        else {
            Log.d(TAG, "onBackPressed: back pressed");
            //TODO: find ud af om vi skal bruge dialog box eller fade out
            //fragment_close.show(getSupportFragmentManager(), "closeFragment");
            super.onBackPressed();
            overridePendingTransition(0, android.R.anim.fade_out);
        }
    }



    // todo see this again.. where is it called, why is it here.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mapContext.updatePermissions();
        } else {
            // Permission was denied. Display an error message.
            Log.d(TAG, "onRequestPermissionsResult: false");
        }

    }


    // Get and Sets
    public List<ITipDTO> getDTOList(){
        List<ITipDTO> list = backend.getTips(mapContext.getmLastKnownLocation());
        list.add(new TipDTO(new UserDTO("TEST","test", ""),
                "hey \n\n more here\n\n\n\n\n\n olo \n\n \n\n\n   eifd \n\n\n\n\n\n\n\n\n\nie \n",
                new LatLng(55.7, 12.6),
                0,
                new Date()));
        return list;
    }

}
