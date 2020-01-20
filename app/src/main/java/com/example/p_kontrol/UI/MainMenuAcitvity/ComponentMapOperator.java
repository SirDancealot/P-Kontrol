package com.example.p_kontrol.UI.MainMenuAcitvity;

import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.p_kontrol.R;
import com.example.p_kontrol.UI.Map.IMapFragmentListener;
import com.example.p_kontrol.UI.Map.IState;
import com.example.p_kontrol.UI.Map.MapFragment;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;

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
            public void onTipClick(int index) {
                mapController.onTipClick(index);
            }
        };
        // map CenterButton Listener.
        mapView_centerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapController.onCenterClick(v);
                //mapFragment.centerMap();
            }
        });

        // Setting up the map Fragment
        mapFragment = new MapFragment(context, mapListener);
        FragmentManager fragmentManager = context.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        // Setting the map on the layout,
        transaction.add( R.id.mainMenu_map , mapFragment);
        transaction.addToBackStack(null);
        transaction.commit();

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
        mapView_btnContainerAceptCancel.setVisibility(View.GONE);
    }
    @Override
    public void toggleStateParking() {
        if(mapFragment.isParkingEnabled()){
            mapFragment.setStateStandby();
        }else{
            mapFragment.setStateParking();
        }
    }
    @Override
    public void toggleStateFreePark() {
        if(mapFragment.isFreeParkEnabled()){
            mapFragment.setStateStandby();
        }else{
            mapFragment.setStateFreePark();
        }
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