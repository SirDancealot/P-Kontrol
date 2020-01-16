package com.example.p_kontrol.UI;

import com.example.p_kontrol.UI.WriteTip.ITipWriteListener;

public interface IFragmentOperator {

    void openWriteTip(ITipWriteListener writeListener);
    void closeWriteTip();

    void showTipBobbles(int index);
    void closeTipBobbles();

    void showTopMsgBar(int imageId, String header , String subTitle);
    void hideTopMsgBar();

    boolean isWriteTipOpen();
    boolean isTipBobbleOpen();
    boolean isTopBarOpen();



}

