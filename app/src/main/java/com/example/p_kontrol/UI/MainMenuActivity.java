package com.example.p_kontrol.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.Backend.Backend;
import com.example.p_kontrol.Backend.IBackend;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.AUserDTO;
import com.example.p_kontrol.DataTypes.ITipDTO;
import com.example.p_kontrol.DataTypes.IUserDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.DataTypes.UserDTO;
import com.example.p_kontrol.DataTypes.UserInfoDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.Feedback.ActivityFeedback;
import com.example.p_kontrol.UI.Map.IMapContext;
import com.example.p_kontrol.UI.Map.IMapContextListener;
import com.example.p_kontrol.UI.Map.MapContext;
import com.example.p_kontrol.UI.ReadTips.FragTipBobble;
import com.example.p_kontrol.UI.ReadTips.FragTopMessageBar;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.GeoPoint;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.Date;
import java.util.LinkedList;
import java.util.List;


/**
 * @responsibilty responsibility to Handle UI interaction on this XML layout.
 *
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * */
public class MainMenuActivity extends MainMenuActivity_androidMethods {

    final static TipDTO newTipDTO = new TipDTO(); // Static methods require a Static object to maneuver

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    // this class is the top of the Stack so where the controll of Avitivity what to do fist()

    @NonNull
    @Override
    public void menuBtn_profile(){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }
    @NonNull
    @Override
    public void menuBtn_FreePark(){
        Log.i("click","FreePark btn clicked \n");
        //setupTipBobblesPagerViewer();
    }
    @NonNull
    @Override
    public void menuBtn_Contribute(){

        // Closing the Menu down.
        menu_dragHandle();

        // starting Contribute process at index 0. meaning the very first step.
        CreateTip();

    }
    @NonNull
    @Override
    public void menuBtn_Community(){
        Log.i("click","Community btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityFeedback.class);
        startActivity(changeActivity);
    }
    @NonNull
    @Override
    public void menuBtn_ParkAlarm(){
        Log.i("click","Park Alarm btn clicked \n");
    }
    @NonNull
    @Override
    public void menuBtn_PVagt(){
        Log.i("click","P-Vagt btn clicked \n");

    }

    @NonNull
    @Override
    public void CreateTip(){
        CreateTip_Process(0);
    }
    private void CreateTip_Process ( int i){
        switch (i) {
            case 0: // Chose location
                fillInTip_Map();
                break;
            case 1: // Write Tip
                fillInTip_WriteTip();
                break;
            case 2: // finish Tip and send to back end for saving.
                CreateTip_finish();
                break;
        }
    }
    private void fillInTip_Map(){

        mapContext.setStateSelectLocation();
        mapView_btnContainerAceptCancel.setVisibility(View.VISIBLE);
        mapView_acceptBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map_setupCenterButton();
                GeoPoint location = new GeoPoint(mapContext.getSelectedLocation().latitude, mapContext.getSelectedLocation().longitude);
                newTipDTO.setL(location); // newTipDTO is a static object that can always be called
                mapContext.setStateStandby();
                mapView_btnContainerAceptCancel.setVisibility(View.GONE);
                CreateTip_Process(1);
            }
        });

        mapView_cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map_setupCenterButton();
                mapContext.setStateStandby();
            }
        });
    }
    private void fillInTip_WriteTip(){
        fragment_messageWrite = new FragMessageWrite();
        toogleFragment_WriteTip(true);
        fragment_messageWrite.setFragWriteMessageListener(new ITipWriteListener() {

            @Override
            public void onMessageDone(ATipDTO dto) {
                newTipDTO.setMessage(dto.getMessage());// newTipDTO is a static object that can always be called
                toogleFragment_WriteTip(false);
                getSupportFragmentManager().popBackStack();
                CreateTip_Process(2); // calls self to Complete the tip
            }

            @Override
            public void onCancelTip() {
                toogleFragment_WriteTip(false);
                // does not call self to complete.
            }

        });
    }
    private void CreateTip_finish(){
        AUserDTO currentUser = new UserDTO("tempUser", "tempLastName", "");
        Date dateNow = new Date(System.currentTimeMillis());

        newTipDTO.setAuthor(currentUser);
        newTipDTO.setCreationDate(dateNow);
        TipDTO tipDTO = newTipDTO.copy();

        backend.createTip(tipDTO);
        temp_listofDTO.add(tipDTO);
        mapContext.setListOfTipDto(getDTOlist());
    }

    @NonNull
    @Override
    public void showTipBooble(int index){
        List<ATipDTO> list = getDTOlist();

        adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, list);


        viewPager_tipBobles.setVisibility(View.VISIBLE);
        viewPager_tipBobles.setAdapter(adapter_TipBobbles);
        viewPager_tipBobles.setCurrentItem(index);

        if(drag_State){
            menu_dragHandle();
        }
    }
    @NonNull
    @Override
    public void showTopMessageBar(){

    }

}
abstract class MainMenuActivity_androidMethods      extends MainMenuActivity_FragmentController {

    MainMenuCloseFragment fragment_close        ;
    private String TAG = "MainMenuActivity_androidMethods";
    static final int RC_SIGN_IN = 3452;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragment_close= new MainMenuCloseFragment(this);
    }

    // Android Specifiks
    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Something happened");
        if (mapContext.getCurrentState() instanceof StateSelectLocation) {
            Log.d(TAG, "onBackPressed: stateSelect");
            mapContext.setStateStandby();
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {

        if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mapContext.updatePermissions();
        } else {
            // Permission was denied. Display an error message.
            Log.d(TAG, "onRequestPermissionsResult: false");
        }

    }
    // [START auth_fui_result]

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            IdpResponse response = IdpResponse.fromResultIntent(data);

            if (resultCode == RESULT_OK) {
                // Successfully signed in
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                userInfoDTO.setUser(user);

                // ...
            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
            }
        }
    }
}
abstract class MainMenuActivity_FragmentController  extends MainMenuActivity_MapSetup{

    private String TAG = "MainMenuActivity_FragmentController";

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = this.getSupportFragmentManager();
        //TipBobbles are all inside this ViewPager Container
        viewPager_tipBobles = findViewById(R.id.mainMenu_viewPager_TipBobbles);
        viewPager_tipBobles.setVisibility(ViewPager.GONE);

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;
        boolFragTopMessageBar   = false;

        fragment_messageWrite = new FragMessageWrite()  ;
        fragment_tipBobble    = new FragTipBobble()     ;
        fragment_topMessage   = new FragTopMessageBar() ;
    }


    // Open Close Fragments and or Views.
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
    public void toogleFragment_WriteTip(boolean open){
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , open);
        boolFragMessageWrite = open;
    }
    public void toogleFragment_WriteTip(){
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , boolFragMessageWrite);
        boolFragMessageWrite =!boolFragMessageWrite;
    }
    public void closeTipBobbleViewPager() {
        viewPager_tipBobles.setVisibility(View.GONE);
        Log.i(TAG, "closeTipBobbleViewPager: Closed");
    }

}
abstract class MainMenuActivity_MapSetup            extends MainMenuActivity_BackEnd{
    private String TAG = "MainMenuActivity_MapSetup";

    IMapContext mapContext              ;
    IMapContextListener mapListener     ;

    Button mapView_centerBtn;
    Button mapView_acceptBtn;
    Button mapView_cancelBtn;
    View mapView_btnContainerAceptCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mapView_centerBtn = findViewById(R.id.mainMenu_Map_centerBtn);
        mapView_acceptBtn = findViewById(R.id.mainMenu_map_acceptBtn);
        mapView_cancelBtn = findViewById(R.id.mainMenu_map_cancelBtn);
        mapView_btnContainerAceptCancel = findViewById(R.id.mainMenu_acceptCancelContainer);
        mapView_btnContainerAceptCancel.setVisibility(View.GONE);

        mapListener = new IMapContextListener() {
            @Override
            public void onReady() {
                map_UpdateTips();
            }

            @Override
            public void onChangeState() {

            }

            @Override
            public void onSelectedLocation() {

            }

            @Override
            public void onUpdate(){
                map_UpdateTips();
            }

            @Override
            public void onTipClick(int index) {
                showTipBooble(index);
            }

        };
        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mainMenu_map);
        mapContext = new MapContext( mapFrag,this, mapListener);

        mapView_centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapContext.centerMap();
            }
        });
    }

    @NonNull
    @Override
    public void map_UpdateTips(){
        mapContext.setListOfTipDto(getDTOlist());
    }

    @NonNull
    @Override
    public LatLng map_getCurrentViewLocation(){
        return mapContext.getLocation();
    }

    public void map_setupCenterButton(){
        mapView_centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapContext.centerMap();
            }
        });
    }
}
abstract class MainMenuActivity_BackEnd             extends MainMenuActivity_MenuSetup{

    private String TAG = "MainMenuActivity_BackEnd";
    IBackend backend = new Backend();
    List<ATipDTO> temp_listofDTO = new LinkedList<>();
    UserInfoDTO userInfoDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backend = new Backend();
        userInfoDTO = UserInfoDTO.getUserInfoDTO();

    }

    public List<ATipDTO> getDTOlist(){
        List<ATipDTO> list = backend.getTips(map_getCurrentViewLocation());

        return temp_addTipsToList(list);
    }
    private List<ATipDTO> temp_addTipsToList(List<ATipDTO> list){
        if(list == null){
            list = new LinkedList<>();
        }
        for(int i = 0; i < temp_listofDTO.size(); i++){
            list.add(temp_listofDTO.get(i));
        }
        return list;
    };
    private List<ATipDTO> temp_setUpDemoTips(List<ATipDTO> list){


        AUserDTO user = new UserDTO("hans","byager","");
        GeoPoint gp = new GeoPoint(37.4219983,-122.084);
        String text = "hej";
        ATipDTO tip = new TipDTO();
        Date date = new Date();
        tip.setMessage(text);
        tip.setL(gp);
        tip.setAuthor(user);
        tip.setCreationDate(date);


        list.add(tip);

        return list;
    }


    @NonNull
    public abstract void map_UpdateTips();

    @NonNull
    public abstract LatLng map_getCurrentViewLocation();

}
abstract class MainMenuActivity_MenuSetup           extends AppCompatActivity implements View.OnClickListener{

    private String TAG = "MainMenuActivity_MenuSetup";
    // -- * -- MENU -- * --

    // Menu Views.
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    // menu Open or Close State
    boolean drag_State;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);

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
    public void menu_dragHandle( ){

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
    public abstract void  menuBtn_profile();
    @NonNull
    public abstract void  menuBtn_FreePark();
    @NonNull
    public abstract void  menuBtn_Contribute();
    @NonNull
    public abstract void  menuBtn_Community();
    @NonNull
    public abstract void menuBtn_ParkAlarm();
    @NonNull
    public abstract void menuBtn_PVagt();

    // implement this to onClick Start and Close fragments, in the Propper Acitivity
    @NonNull
    public abstract void CreateTip();
    @NonNull
    public abstract void showTipBooble(int index);
    @NonNull
    public abstract void showTopMessageBar();

}

//todo backend skal være nederst.
// Activity Controller
// androidBackStackManager
// fragmentOpenCloser
// menuSetup
// mapSetup
// backend