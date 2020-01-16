package com.example.p_kontrol.UI.TopMessageBar;

import android.graphics.drawable.Drawable;

public interface IFragTopMessageBar {

    void show();
    void hide();
    void toogle();
    boolean isShown();

    void setHeader(String text);
    void setSubtitle(String text);
    void setImage(int imageId);
    void setImage(Drawable image);

}
