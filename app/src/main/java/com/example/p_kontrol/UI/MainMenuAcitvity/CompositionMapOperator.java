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

class CompositionMapOperator        implements IMapOperator   {

    AppCompatActivity context;
    IMapOperatorController mapController;
    View view;
    private String TAG = this.getClass().getName();

    MapFragment mapFragment;
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
            public void onTipClick(int index) {
                mapController.onTipClick(index);
            }
        };
        mapFragment = new MapFragment(context, mapListener);

        FragmentManager fragmentManager = context.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        transaction.add( R.id.mainMenu_map , mapFragment);

        transaction.addToBackStack(null);
        transaction.commit();

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
    public void toggleStateParking() {
        boolean value = mapFragment.isFreeParkEnabled();
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