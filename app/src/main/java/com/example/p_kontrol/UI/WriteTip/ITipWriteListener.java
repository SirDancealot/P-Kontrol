package com.example.p_kontrol.UI.WriteTip;

/**
 * @responsibilty responsibility to give multiple options for ending a tip, and imlementing different actions for each option
 *
 * */
public interface ITipWriteListener {
    /**
     * A listener call to say that the message has been created
     */
    void onMessageDone();
    /**
     * A listener call to say that we do not want to complete the create tip process.
     */
    void onCancelTip();
}
