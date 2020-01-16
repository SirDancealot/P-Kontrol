package com.example.p_kontrol.UI.MainMenuAcitvity;

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
import com.example.p_kontrol.UI.TopMessageBar.FragTopMessageBar;
import com.example.p_kontrol.UI.UserPersonalisation.ActivityProfile;
import com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter;
import com.example.p_kontrol.UI.Feedback.ActivityFeedback;
import com.example.p_kontrol.UI.Map.IMapFragmentListener;
import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.FragMessageWrite;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
import com.firebase.ui.auth.IdpResponse;
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






