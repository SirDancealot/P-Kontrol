package com.example.p_kontrol.UI;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.p_kontrol.DataTypes.ATipDTO;
import com.example.p_kontrol.DataTypes.TipDTO;
import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Map.IState;
import com.example.p_kontrol.UI.Map.MapFragment;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.Feedback.ActivityFeedback;
import com.example.p_kontrol.UI.Map.IMapFragment;
import com.example.p_kontrol.UI.Map.IMapFragmentListener;
import com.example.p_kontrol.UI.ReadTips.FragTopMessageBar;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.List;

/**
 * @responsibilty responsibility to Handle UI interaction on this XML layout.
 *
 * implemented using Composition(with a touch of delegation) Pattern and inheritance;
 *
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * */
public class MainMenuActivity extends MainMenuActivityController{

    // this Handels the backStack, to see controlls look at MainMenuAcitivityController.

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
        if (mapOperator.getCurrentState() instanceof StateSelectLocation) {
            Log.d(TAG, "onBackPressed: stateSelect");
            mapOperator.setStateStandby();
        }


        else if ( fragmentOperator.isTipBobbleOpen()) {
            Log.d(TAG, "onBackPressed: viewPager");
            fragmentOperator.closeTipBobbles();
        }
        else if (menuOperator.isMenuOpen()) {
            Log.d(TAG, "onBackPressed: Bottom menue");
            menuOperator.closeMenu();
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
            mapOperator.updatePermissions();
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
                //todo replace with WM
                //userInfoDTO.setUser(user);

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
class MainMenuActivityController extends AppCompatActivity implements IMenuOperationsController , IMapOperatorController{

    // THIS IS THE CONTROLLER CLASS
    final static TipDTO newTipDTO = new TipDTO(); // Static methods require a Static object to maneuver
    IMenuOperator       menuOperator;
    IFragmentOperator   fragmentOperator;
    IMapOperator        mapOperator;
    View container;

    // -- ** -- View Model stuff  -- ** -- **  -- ** -- **  -- ** -- **

    LiveDataViewModel model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmenu);
        container = findViewById(R.id.mainMenu_layout);

        // setting compositions
        // delegates responsibility for Creating Views out to keep code simple, however, each referes back here
        // for controll.
        menuOperator     = new CompositionMenuOperator(this, container );
        mapOperator      = new CompositionMapOperator(this,container, this);
        fragmentOperator = new CompositionFragmentOperator(this,container);

        model = ViewModelProviders.of(this).get(LiveDataViewModel.class);
    }

    // this class is the top of the Stack so where the controll of Avitivity what to do fist()
    // Menu Controll.
    @Override
    public void menuBtn_profile(){
        Log.i("click","Profile btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityProfile.class );
        startActivity(changeActivity);
    }
    @Override
    public void menuBtn_FreePark(){
        Log.i("click","FreePark btn clicked \n");
        //setupTipBobblesPagerViewer();
    }
    @Override
    public void menuBtn_Contribute(){

        // Closing the Menu down.
        menuOperator.toggleMenu();

        // starting Contribute process at index 0. meaning the very first step.
        CreateTip();

    }
    @Override
    public void menuBtn_Community(){
        Log.i("click","Community btn clicked \n");
        Intent changeActivity = new Intent( this , ActivityFeedback.class);
        startActivity(changeActivity);
    }
    @Override
    public void menuBtn_ParkAlarm(){
        Log.i("click","Park Alarm btn clicked \n");
    }
    @Override
    public void menuBtn_PVagt(){
        Log.i("click","P-Vagt btn clicked \n");

    }

    // Map
    @Override
    public void onTipClick(int index){
        fragmentOperator.showTipBobbles(index);
        menuOperator.closeMenu();
    }
    @Override
    public void onCenterClick(View v){
        mapOperator.centerOnUserLocation();
    }

    //User InterActionMethods
    // Create Tip
    private void CreateTip(){
        CreateTip_Process(0);
    }
    private void CreateTip_Process ( int i){
        switch (i) {
            case 0: // Chose location

                model.getMutableTipCreateObject();

                mapOperator.setStateSelection();
                mapOperator.visibilityOfInteractBtns(View.VISIBLE);

                mapOperator.onAcceptClick( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mapOperator.setStateStandby();
                        CreateTip_Process(1);
                    }
                });
                mapOperator.onCancelClick(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mapOperator.setStateStandby();
                    }
                });
                break;
            case 1: // Write Tip
                fragmentOperator.openWriteTip(new ITipWriteListener() {
                    @Override
                    public void onMessageDone(ATipDTO dto) {
                        fragmentOperator.closeWriteTip();
                        CreateTip_Process(2);
                    }
                    @Override
                    public void onCancelTip() {
                        fragmentOperator.closeWriteTip();
                    }
                });
                break;
            case 2: // finish Tip and send to back end for saving.
                ATipDTO dto = model.getMutableTipCreateObject().getValue();
                model.createTip();
                break;
        }
    }
/*
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

        newTipDTO.setAuthor(currentUser);
        newTipDTO.setCreationDate(dateNow);
        TipDTO tipDTO = newTipDTO.copy();

        backend.createTip(tipDTO);
        temp_listofDTO.add(tipDTO);
        mapFragment.setListOfTipDto(getDTOlist());
    }*/

}
class CompositionFragmentOperator   implements IFragmentOperator {

    AppCompatActivity context;
    View view;
    private String TAG = this.getClass().getName();

    FragMessageWrite    fragment_messageWrite   ;
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


    // DAta Acces
    LiveDataViewModel model;
    LiveData<List<ATipDTO>> tipList;


    public CompositionFragmentOperator(AppCompatActivity context, View view){

        this.context = context;
        this.view = view;

        fragmentManager = context.getSupportFragmentManager();
        //TipBobbles are all inside this ViewPager Container
        viewPager_tipBobles = this.view.findViewById(R.id.mainMenu_viewPager_TipBobbles);
        viewPager_tipBobles.setVisibility(ViewPager.GONE);

        boolFragMessageWrite    = false;
        boolFragTipBobble       = false;
        boolFragTopMessageBar   = false;

        fragment_messageWrite = new FragMessageWrite()  ;
        fragment_topMessage   = new FragTopMessageBar() ;

        // Live Data list , that calls adapter to notify of changes when changes are made.

        model = ViewModelProviders.of(context).get(LiveDataViewModel.class);
        tipList = model.getTipList();
        tipList.observe(context, list -> {
            try {
                adapter_TipBobbles.notifyDataSetChanged();
            }catch (NullPointerException e){
                Log.i(TAG, "CompositionFragmentOperator: Null pointer, adapter for tips was null");
            }
        } );
        adapter_TipBobbles = new TipBobblesAdapter(fragmentManager, tipList,this);

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
   /*public void toogleFragment_WriteTip(boolean open){
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , open);
        boolFragMessageWrite = open;
    }
    public void toogleFragment_WriteTip(){

    }
    public void closeTipBobbleViewPager() {
        viewPager_tipBobles.setVisibility(View.GONE);
        Log.i(TAG, "closeTipBobbleViewPager: Closed");
    }*/

    // Toogles
    @Override
    public void openWriteTip(ITipWriteListener writeListener) {
        fragment_messageWrite.setFragWriteMessageListener(writeListener);
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , true);
        boolFragMessageWrite = true;
    }
    @Override
    public void closeWriteTip(){
        FragmentToogleTransaction(R.id.mainMenu_midScreenFragmentContainer, fragment_messageWrite , false);
        boolFragMessageWrite = false;
    }
    @Override
    public void showTipBobbles(int index) {

        List<ATipDTO> list = null;

        viewPager_tipBobles.setVisibility(View.VISIBLE);
        viewPager_tipBobles.setAdapter(adapter_TipBobbles);
        viewPager_tipBobles.setCurrentItem(index);

    }
    @Override
    public void closeTipBobbles(){
        viewPager_tipBobles.setVisibility(View.GONE);
    }


    // Booleans
    @Override
    public boolean isWriteTipOpen(){
        return boolFragMessageWrite;
    }
    @Override
    public boolean isTipBobbleOpen(){
        return boolFragTipBobble;
    }
    @Override
    public boolean isTopBarOpen(){
        return boolFragTopMessageBar;
    }



}
class CompositionMapOperator        implements IMapOperator   {

    AppCompatActivity context;
    IMapOperatorController mapController;
    View view;
    private String TAG = this.getClass().getName();

    IMapFragment mapFragment;
    IMapFragmentListener mapListener     ;

    Button mapView_centerBtn;
    Button mapView_acceptBtn;
    Button mapView_cancelBtn;
    View mapView_btnContainerAceptCancel;

    public CompositionMapOperator(AppCompatActivity context,View view, IMapOperatorController mapController ){
        this.context = context;
        this.view = view;
        this.mapController = mapController;

        mapView_centerBtn = view.findViewById(R.id.mainMenu_Map_centerBtn);
        mapView_acceptBtn = view.findViewById(R.id.mainMenu_map_acceptBtn);
        mapView_cancelBtn = view.findViewById(R.id.mainMenu_map_cancelBtn);
        mapView_btnContainerAceptCancel = view.findViewById(R.id.mainMenu_acceptCancelContainer);
        mapView_btnContainerAceptCancel.setVisibility(View.GONE);

        mapListener = new IMapFragmentListener() {
            @Override
            public void onReady() {}

            @Override
            public void onChangeState() {
            }

            @Override
            public void onSelectedLocation() {
            }

            @Override
            public void onUpdate(){
            }

            @Override
            public void onTipClick(int index) {
                mapController.onTipClick(index);
            }

        };
        SupportMapFragment mapFrag = (SupportMapFragment) this.context.getSupportFragmentManager().findFragmentById(R.id.mainMenu_map);
        mapFragment = new MapFragment( mapFrag,context, mapListener);

        mapView_centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               mapController.onCenterClick(v);
               //mapFragment.centerMap();
            }
        });
    }

    @Override
    public void centerOnUserLocation(){
        mapFragment.centerMap();
    }
    @Override
    public void setStateSelection() {
        mapFragment.setStateSelectLocation();
    }
    @Override
    public void setStateStandby() {
        mapFragment.setStateStandby();
    }
    @Override
    public void setStateParking() {

    }


    @Override
    public void onAcceptClick(View.OnClickListener onclick){
        mapView_acceptBtn.setOnClickListener(onclick);
    }
    @Override
    public void onCancelClick(View.OnClickListener onclick){
        mapView_cancelBtn.setOnClickListener(onclick);
    }

    @Override
    public IState getCurrentState() {
        return mapFragment.getCurrentState();
    }
    @Override
    public void updatePermissions(){
        mapFragment.updatePermissions();
    }


    @Override
    public void visibilityOfInteractBtns(int visibility){
        mapView_btnContainerAceptCancel.setVisibility(visibility);
    }

}
class CompositionMenuOperator       implements View.OnClickListener, IMenuOperator{

    IMenuOperationsController context;
    private String TAG = this.getClass().getName();

    // Menu Views.
    View menuBtnContainer,dragHandle;
    Button  menuBtn_profile     ,menuBtn_FreePark   ,menuBtn_Contribute ,
            menuBtn_Community   ,menuBtn_ParkAlarm  ,menuBtn_PVagt      ;
    // menu Open or Close State
    boolean drag_State;

    public CompositionMenuOperator(IMenuOperationsController context, View view){
        this.context = context;

        // Menu Buttons.
        menuBtnContainer     = view.findViewById(R.id.menu_btnContainer)           ;
        dragHandle           = view.findViewById(R.id.menuBtn_draggingHandle)      ;

        // Menu Category Buttons
        menuBtn_profile      = view.findViewById(R.id.menuBtn_profile)             ;
        menuBtn_FreePark     = view.findViewById(R.id.menuBtn_FreePark)            ;
        menuBtn_Contribute   = view.findViewById(R.id.menuBtn_Contribute)          ;
        menuBtn_Community    = view.findViewById(R.id.menuBtn_Community)           ;
        menuBtn_ParkAlarm    = view.findViewById(R.id.menuBtn_ParkAlarm)           ;
        menuBtn_PVagt        = view.findViewById(R.id.menuBtn_PVagt)               ;

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
                toggleMenu();
                break;
            // Menu Line 1.
            case (R.id.menuBtn_profile):
                context.menuBtn_profile();
                break;
            case (R.id.menuBtn_FreePark):
                context.menuBtn_FreePark();
                break;
            case (R.id.menuBtn_Contribute):
                context.menuBtn_Contribute();
                break;
            // Menu Line 2.
            case (R.id.menuBtn_Community):
                context.menuBtn_Community();
                break;
            case (R.id.menuBtn_ParkAlarm):
                context.menuBtn_ParkAlarm();
                break;
            case (R.id.menuBtn_PVagt):
                context.menuBtn_ParkAlarm();
                break;
        }
    }

    // Interface
    @Override
    public void toggleMenu( ){
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
    @Override
    public void closeMenu() {
        menuBtnContainer.setVisibility(View.GONE);
        drag_State = false;
    }
    @Override
    public void openMenu() {
        menuBtnContainer.setVisibility(View.VISIBLE);
        drag_State = true;
    }
    @Override
    public boolean isMenuOpen() {
        return drag_State;
    }
}


//InterFaces Map
interface IMapOperator{

    void updatePermissions();
    IState getCurrentState();

    void visibilityOfInteractBtns(int visibility);
    void onAcceptClick(View.OnClickListener onclick);
    void onCancelClick(View.OnClickListener onclick);

    void setStateSelection();
    void setStateStandby();
    void setStateParking();
    void centerOnUserLocation();
}
interface IMapOperatorController{
    void onTipClick(int index);
    void onCenterClick(View v);
}

// interfaces Menu
interface IMenuOperator{
    void toggleMenu();
    void closeMenu();
    void openMenu();

    boolean isMenuOpen();
}
interface IMenuOperationsController{
    void menuBtn_profile();
    void menuBtn_FreePark();
    void menuBtn_Contribute();
    void menuBtn_Community();
    void menuBtn_ParkAlarm();
    void menuBtn_PVagt();
}



