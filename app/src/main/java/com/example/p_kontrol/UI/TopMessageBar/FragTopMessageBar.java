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

public class FragTopMessageBar extends Fragment implements IFragTopMessageBar , View.OnClickListener{

    // Views from Fragment
    int ALPHA = 255;
    View view,layout;
    ImageView image;
    TextView header, subtitle;

    boolean isShown;

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

        layout.setOnClickListener(this);
        hide();
        return view;
    }


    // Interface

    // open Close.
    @Override
    public void show() {
        isShown = true;
        layout.setVisibility(View.VISIBLE);
    }
    @Override
    public void hide() {
        isShown = false;
        layout.setVisibility(View.GONE);
    }
    @Override
    public void toogle() {
        if(isShown){
            hide();
        }else{
            show();
        }
    }
    @Override
    public boolean isShown() {
        return isShown;
    }


    // data
    @Override
    public void setHeader(String text) {
        header.setText(text);
    }
    @Override
    public void setSubtitle(String text) {
        subtitle.setText(text);
    }
    @Override
    public void setImage(int imageId) {
        image.setImageResource(imageId);
    }
    @Override
    public void setImage(Drawable newImage) {
        image.setImageDrawable(newImage);
    }
    @Override
    public void setBackgroundColor(int color, float alpha ) {

        int newAlpha = Math.round(Color.alpha(color) * alpha);
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);

        int newColor = Color.argb(newAlpha, red, green, blue);
        layout.setBackgroundColor(newColor);
    }

    @Override
    public void onClick(View v) {
        layout.setVisibility(View.GONE);
    }
}
