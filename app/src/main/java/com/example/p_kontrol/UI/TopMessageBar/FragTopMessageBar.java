package com.example.p_kontrol.UI.TopMessageBar;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.p_kontrol.R;
/**
 * @responsibility to display information in a bar in the top
 * */
public class FragTopMessageBar extends Fragment implements IFragTopMessageBar{

    // Views from Fragment
    View view,layout;
    ImageView image;
    TextView header, subtitle;

    boolean isShown;
    /**
     * @responsibility to display information in a bar in the top
     * */
    public FragTopMessageBar() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_top_message_bar2, container, false);

        layout  = view.findViewById(R.id.topMsgBar_Layout   );
        image   = view.findViewById(R.id.topMsgBar_Image    );
        header  = view.findViewById(R.id.topMsgBar_header   );
        subtitle= view.findViewById(R.id.topMsgBar_subTitle );

        hide();
        return view;
    }


    // Interface

    // open Close.
    /**     @inheritDoc     */
    @Override
    public void show() {
        isShown = true;
        layout.setVisibility(View.VISIBLE);
    }
    /**     @inheritDoc     */
    @Override
    public void hide() {
        isShown = false;
        layout.setVisibility(View.GONE);
    }
    /**     @inheritDoc     */
    @Override
    public void toogle() {
        if(isShown){
            hide();
        }else{
            show();
        }
    }
    /**     @inheritDoc     */
    @Override
    public boolean isShown() {
        return isShown;
    }


    // data
    /**     @inheritDoc     */
    @Override
    public void setHeader(String text) {
        header.setText(text);
    }
    /**     @inheritDoc     */
    @Override
    public void setSubtitle(String text) {
        subtitle.setText(text);
    }
    /**     @inheritDoc     */
    @Override
    public void setImage(int imageId) {
        image.setImageResource(imageId);
    }
    /**     @inheritDoc     */
    @Override
    public void setImage(Drawable newImage) {
        image.setImageDrawable(newImage);
    }
    /**     @inheritDoc     */
    @Override
    public void setBackgroundColor(int color, float alpha ) {

        int newAlpha = Math.round(Color.alpha(color) * alpha);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        int newColor = Color.argb(newAlpha, red, green, blue);
        layout.setBackgroundColor(newColor);
    }


}
