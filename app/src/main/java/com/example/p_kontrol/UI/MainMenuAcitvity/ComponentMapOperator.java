package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Map.IMapFragmentListener;
import com.example.p_kontrol.UI.Map.MapFragment;
import com.example.p_kontrol.UI.Map.State;
import com.example.p_kontrol.UI.Map.StateSelectLocation;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
/**
 * @responsibilty to contain all the responsibility for initiating, knowing, and using the map.
 * */
class ComponentMapOperator implements IMapOperator   {

    // Android Specifics
    private AppCompatActivity context;
    private IMapOperatorController mapController;
    private String TAG = this.getClass().getName();

    // Views
    private View view,  mapView_btnContainerAceptCancel;;
    private MapFragment mapFragment;
    private IMapFragmentListener mapListener     ;

    private Button mapView_centerBtn;
    private Button mapView_acceptBtn;
    private Button mapView_cancelBtn;


    /**
     * @responsibilty to contain all the responsibility for initiating, knowing, and using the map.
     *
     *  ComponentMapOperator is the Component which has the Delegated responsibility to Manage the map, it uses the IMapFragment to do this.
     *  @see {@link com.example.p_kontrol.UI.Map.IMapFragment}
     *
     *  @param context       the Parent Activity, such that the reference can be passed on to the Fragment.
     *  @param view          the layout view, needed to search for xml views in the layout.
     *  @param mapController is an interface to determine what happens when clicked on a tip.
     *
     *  the MapFragment implementation is a StateController relevant Classes are
     *  @see {@link com.example.p_kontrol.UI.Map.State}
     *  @see {@link com.example.p_kontrol.UI.Map.StateFreePark}
     *  @see {@link com.example.p_kontrol.UI.Map.StateSelectLocation}
     *  @see {@link com.example.p_kontrol.UI.Map.StateStandby}
     *  @see {@link com.example.p_kontrol.UI.Map.MapFragment}
     *
     * */
    ComponentMapOperator(AppCompatActivity context, View view, IMapOperatorController mapController ){

        // getting variables from Constructor
        this.context = context;
        this.view = view;
        this.mapController = mapController;

        // Setting Views
        mapView_centerBtn = view.findViewById(R.id.mainMenu_Map_centerBtn);
        mapView_acceptBtn = view.findViewById(R.id.mainMenu_map_acceptBtn);
        mapView_cancelBtn = view.findViewById(R.id.mainMenu_map_cancelBtn);
        mapView_btnContainerAceptCancel = view.findViewById(R.id.mainMenu_acceptCancelContainer);
        mapView_btnContainerAceptCancel.setVisibility(View.GONE); // todo move this into the individual state.

        //map listener for clicking on a tip
        mapListener = new IMapFragmentListener() {
            @Override
            public void onTipClick(String index) {
                mapController.onTipClick(index);
            }
        };


        // Setting up the map Fragment
        mapFragment = new MapFragment(context, mapListener);
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Setting the map on the layout,
        transaction.add( R.id.mainMenu_map , mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();

    }

    //GoogleMap Specifiks
    /**
     * @inheritDoc
     * */
    @Override
    public void updatePermissions(){
        mapFragment.updatePermissions();
    }

    // Button Clicks
    @Override
    public void onAcceptClick(View.OnClickListener onclick){
      mapView_acceptBtn.setOnClickListener(onclick);
  }
    /**
     * @inheritDoc
     * */
    @Override
    public void onCancelClick(View.OnClickListener onclick){
        mapView_cancelBtn.setOnClickListener(onclick);
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void onCenterClick(View.OnClickListener onclick){
        mapView_centerBtn.setOnClickListener(onclick);
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void centerOnUserLocation(){
        mapFragment.centerMap();
    }

    //States
    /**
     * @inheritDoc
     * */
    @Override
    public void setStateSelection() {
        mapView_btnContainerAceptCancel.setVisibility(View.VISIBLE);
        mapFragment.setStateSelectLocation();
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void setStateStandby() {
        mapFragment.setStateStandby();
        mapView_btnContainerAceptCancel.setVisibility(View.GONE);
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void toggleStateParking() {
        if(mapFragment.isParkingEnabled()){
            mapFragment.setStateStandby();
        }else{
            mapFragment.setStateParking();
        }
    }
    /**
     * @inheritDoc
     * */
    @Override
    public void toggleStateFreePark() {
        if(mapFragment.isFreeParkEnabled()){
            mapFragment.setStateStandby();
        }else{
            mapFragment.setStateFreePark();
        }
    }
    /**
     * @inheritDoc
     * */
    @Override
    public State getCurrentState() {
        return mapFragment.getCurrentState();
    }

    /**
     * @inheritDoc
     */
    @Override
    public void onCenterClick(View v) {
        centerOnUserLocation();
    }


    //todo move into States. Obs! its called in MainMenuActivity
    // also remove from interface.
    @Override
    public void visibilityOfInteractBtns(int visibility){
        mapView_btnContainerAceptCancel.setVisibility(visibility);
    }

}