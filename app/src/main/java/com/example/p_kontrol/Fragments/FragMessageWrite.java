package com.example.p_kontrol.Fragments;

import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;

import com.example.p_kontrol.CustomViews.Ptip_CustomProgressBar;
import com.example.p_kontrol.R;

public class FragMessageWrite extends Fragment  implements View.OnTouchListener, GestureDetector.OnGestureListener {

    // Containers
    private View view                       ;
    private View mainContainer              ;

    // Views to change
    int numViews                            ;
    int stepCounter                         ;
    private View stepContainer[]            ;
    private View navContainer               ;
    private Ptip_CustomProgressBar navBar   ;

    // Swiping Variables
    int bigestViewHeight                    ;
    private GestureDetector gestureDetector ;
    Animation   anim_slideIn_left           ,
                anim_slideIn_right          ,
                anim_slideOut_left          ,
                anim_slideOut_right         ;

    public FragMessageWrite() {}

/* -- FRAGMENT IMPLEMENTATION -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- -- */

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_message_write, container, false);
        mainContainer = view.findViewById(R.id.WriteTip_ContentContainer);

        // Making a container of all the Views
        numViews = 4;
        stepContainer = new View[numViews];
        stepContainer[0] = view.findViewById(R.id.WriteTip_StepContainer_1);
        stepContainer[1] = view.findViewById(R.id.WriteTip_StepContainer_2);
        stepContainer[2] = view.findViewById(R.id.WriteTip_StepContainer_3);
        stepContainer[3] = view.findViewById(R.id.WriteTip_StepContainer_4);

        // Start by set View i to Visible.
        stepContainer[0].setVisibility(View.VISIBLE);
        bigestViewHeight = stepContainer[0].getHeight();
        stepCounter = 0;

        // Navigation View
        navContainer = view.findViewById(R.id.WriteTip_NavigationContainer);
        navBar = view.findViewById(R.id.WriteTip_NavBar);
        navBar.setProgressValue(1 + stepCounter);
        navBar.setProgressNumSteps(numViews);

        // Swiping Variables
        view.setOnTouchListener(this);
        gestureDetector = new GestureDetector(getContext(),this );

        // setting Swiping Animations
        anim_slideIn_left   = AnimationUtils.loadAnimation( getContext() , R.anim.slidein_left      );
        anim_slideIn_right  = AnimationUtils.loadAnimation( getContext() , R.anim.slidein_right     );
        anim_slideOut_left  = AnimationUtils.loadAnimation( getContext() , R.anim.slideout_left     );
        anim_slideOut_right = AnimationUtils.loadAnimation( getContext() , R.anim.slideout_right    );

        return view;
    }

    // onTouchListeners
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        // true means the touch event is consumed. if false it will fire again.
        return true;
    }

    /* Gesture Detector */
    @Override
    public boolean onDown(MotionEvent e) {
       // Log.i("touch", "onDown");
        return false;
    }
    @Override
    public void onShowPress(MotionEvent e) {
        //Log.i("touch", "onShowPress");
    }
    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        //Log.i("touch", "onSingleTapUp");
        return false;
    }
    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        //Log.i("touch", "onScroll");
        return false;
    }
    @Override
    public void onLongPress(MotionEvent e) {
        //Log.i("touch", "onLongPress");
    }
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {

        Animation animIn;
        Animation animOut;

        float diff_X = e1.getX() - e2.getX();

        if(diff_X>0){
            animIn = anim_slideIn_right;
            animOut= anim_slideOut_left;
        }else{
            animIn = anim_slideIn_left;
            animOut= anim_slideOut_right;
        }

        // X direction Navigation
        if(Math.abs(velocityX) >= Math.abs(velocityY)) {
            if (diff_X < 0) {
                swipeChangeViewLevel(-1,animIn,animOut);
                Log.v("touch", "Right :" + stepCounter);
                return true;
            } else if (diff_X > 0) {
                swipeChangeViewLevel(1,animIn,animOut);
                Log.v("touch", "left :" + stepCounter);
                return true;
            }
        }

        closeKeyboard();
        return true;
    }
        // Belongs to onFling.
        private boolean swipeChangeViewLevel(int directionInt, Animation animIn, Animation animOut){

                if( stepCounter == numViews){

                    //todo kill the fragment
                    Log.e("not implemented", "kill fragment here ");
                    return true;

                }else {

                    int newStepCounter = stepCounter + directionInt;
                    if( newStepCounter < 0 || newStepCounter == (numViews) ){
                        //Out Of Bounds
                        directionInt = 0;
                    }else {
                        animOutThenAnimIn animateThis = new animOutThenAnimIn(
                                stepContainer[newStepCounter]   ,
                                stepContainer[stepCounter]      ,
                                animIn                          ,
                                animOut                         );
                        navBar.setProgressValue(newStepCounter+1);
                    }
                    stepCounter += directionInt;
                    closeKeyboard();

                }
                return false;
            }
        private void closeKeyboard() {
                // src https://stackoverflow.com/questions/3400028/close-virtual-keyboard-on-button-press
                final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(getContext().INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(getView().getWindowToken(), 0);
            }

}

class animOutThenAnimIn implements Animation.AnimationListener{

    View viewIN, viewOUT;
    Animation animIN, animOUT;
    animOutThenAnimIn(View viewIN, View viewOUT, Animation animIN, Animation animOUT){
        this.viewIN  = viewIN    ;
        this.viewOUT = viewOUT   ;
        this.animIN  = animIN    ;
        this.animOUT = animOUT   ;

        animOUT.setAnimationListener(this);
        viewOUT.startAnimation(animOUT);
    }
    @Override
    public void onAnimationStart(Animation animation) {
    }
    @Override
    public void onAnimationEnd(Animation animation) {
        Log.e("View ID = ", "" + viewOUT.getTransitionName() );
        viewOUT.setVisibility(View.GONE);
        animIn startINanimation = new animIn(viewIN, animIN);
    }
    @Override
    public void onAnimationRepeat(Animation animation) {
    }
}
class animIn implements Animation.AnimationListener{
    View viewIN;
    Animation animIN;
    public animIn(View viewIN, Animation animIN){
        this.viewIN = viewIN;
        this.animIN = animIN;
        animIN.setAnimationListener(this);
        viewIN.startAnimation(animIN);
    }
    @Override
    public void onAnimationStart(Animation animation) {
        viewIN.setVisibility(View.VISIBLE);
    }

    @Override
    public void onAnimationEnd(Animation animation) {

    }

    @Override
    public void onAnimationRepeat(Animation animation) {

    }
}