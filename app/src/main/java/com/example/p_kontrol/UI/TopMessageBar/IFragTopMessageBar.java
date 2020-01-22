package com.example.p_kontrol.UI.TopMessageBar;

import android.graphics.drawable.Drawable;
/**
 * @responsibility to display information in a bar in the top
 * */
public interface IFragTopMessageBar {
    /**
     * shows the top bar
     * */
    void show();
    /**
     * hides the top bar
     * */
    void hide();
    /**
     * hides  if shown and reversed
     * */
    void toogle();
    /**
     * @return  true if is shown
     * */
    boolean isShown();

    /**
     * @param text headerText to be displayed
     * */
    void setHeader(String text);
    /**
     * @param text subtitle to be displayed
     * */
    void setSubtitle(String text);
    /**
     * @param imageId the id of an image to show
     * */
    void setImage(int imageId);
    /**
     * @param image the image to display
     * */
    void setImage(Drawable image);
    /**
     * @param colorId the color of the background
     * @param alpha the alpha it needs to show, a value between 0 and 1.
     * */
    void setBackgroundColor(int colorId, float alpha);

}
