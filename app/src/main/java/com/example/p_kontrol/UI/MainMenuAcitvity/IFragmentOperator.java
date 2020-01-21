package com.example.p_kontrol.UI.MainMenuAcitvity;

import com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel;
import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;
/**
 * @responsibilty to specify methods to operate the different Fragments that can be shown as overlay on the screen. .
 * */
public interface IFragmentOperator {

    // WriteTip Fragment
    /**
     * opens the WriteTip Fragment
     * creates a new fragment and opens i using a fragment transaction to do so.
     * @param writeListener is a listener that seperates Continue from Cancel.
     * @see {@link com.example.p_kontrol.UI.WriteTip.FragMessageWrite}
     * @See {@link com.example.p_kontrol.UI.WriteTip.ITipWriteListener}
     * */
    void openWriteTip(ITipWriteListener writeListener);
    /**
     * close the WriteTip Fragment
     * uses a fragment transaction to do so.
     * @see {@link com.example.p_kontrol.UI.WriteTip.FragMessageWrite}
     * */
    void closeWriteTip();

    // Tip Bobbles
    /**
     * show tip bobble fragment, uses an adapter to do so.
     * @param index the tip index to show, the LiveDataViewModel has a list of tips, this specifies wich to show.
     * @see {@link com.example.p_kontrol.UI.ReadTips.FragTipBobble}
     * @see {@link com.example.p_kontrol.UI.ReadTips.TipBobblesAdapter}
     *
     * data
     * @see {@link com.example.p_kontrol.UI.ViewModelLiveData.LiveDataViewModel}
     * */
    void showTipBobbles(int index);
    /**
     * closes the tipbobbles Fragment
     * sets the View.Visibility(GONE)
     * @see {@link com.example.p_kontrol.UI.ReadTips.FragTipBobble}
     * */
    void closeTipBobbles();

    //TopMessageBar
    /**
     * show topMessageBar
     * @param imageId   the id of the image to be displayed
     * @param header    the large upper text to be displayed
     * @param subTitle  the smaller lower text to be displayed
     *
     * @see {@link com.example.p_kontrol.UI.TopMessageBar.IFragTopMessageBar}
     * @see {@link com.example.p_kontrol.UI.TopMessageBar.FragTopMessageBar}
     * */
    void showTopMsgBar(int imageId, String header , String subTitle, int colorId, float alpha);
    /**
     * hide Top Message Bar
     * using Interface specified .hide() method
     * @see {@link com.example.p_kontrol.UI.TopMessageBar.IFragTopMessageBar}
     * */
    void hideTopMsgBar();

    /**
     @return true if open, false if closed;
      * */
    boolean isWriteTipOpen();
    /**
     @return true if open, false if closed;
      * */
    boolean isTipBobbleOpen();


}

