package com.example.p_kontrol.CustomViews;

import com.example.p_kontrol.R;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.ColorInt;

public class Ptip_CustomProgressBar extends View {

    private int progressValue       ;
    private int progressNumSteps    ;

    private int progressPointRadius ;
    private int progressBarHeight   ;
    private int progressPointMargin ;
    private int progressBorderThick ;

    private Paint colorProgress      = new Paint();
    private Paint colorNonProgress   = new Paint();
    private Paint colorBorder        = new Paint();


    // SRC : https://code.tutsplus.com/tutorials/android-sdk-creating-custom-views--mobile-14548
    public Ptip_CustomProgressBar(Context context , AttributeSet attrs) {
        super(context, attrs);

        //get the attributes specified in attrs.xml using the name we included
        TypedArray a = context.getTheme().obtainStyledAttributes(
                attrs,
                R.styleable.Ptip_CustomProgressBar,
                0,
                0
        );

        // now we try to retrieve the values
        try {

            //get the text and colors specified using the names in attrs.xml
            progressValue       = a.getInteger(R.styleable.Ptip_CustomProgressBar_ptip_progressValue, 0);
            progressNumSteps    = a.getInteger(R.styleable.Ptip_CustomProgressBar_ptip_progressNumSteps, 5);

            // get Attributes Color, or Set the Default.
            colorBorder.setColor(a.getColor(R.styleable.Ptip_CustomProgressBar_ptip_progress_ColorBorder        , Color.BLACK   ));
            colorNonProgress.setColor(a.getColor(R.styleable.Ptip_CustomProgressBar_ptip_progress_ColorNonProgress   , Color.GRAY    ));
            colorProgress.setColor(a.getColor(R.styleable.Ptip_CustomProgressBar_ptip_progress_ColorProgress      , Color.YELLOW  ));

            colorBorder.setStyle(Paint.Style.FILL_AND_STROKE);
            colorNonProgress.setStyle(Paint.Style.FILL_AND_STROKE);
            colorProgress.setStyle(Paint.Style.FILL_AND_STROKE);

            progressBarHeight   = a.getInteger(R.styleable.Ptip_CustomProgressBar_ptip_progressBarHeight,1);
            progressPointRadius = a.getInteger(R.styleable.Ptip_CustomProgressBar_ptip_progressPointdiagonal, 2);
            progressPointMargin = a.getInteger(R.styleable.Ptip_CustomProgressBar_ptip_progressPointMargin, 5);
            progressBorderThick = a.getInteger(R.styleable.Ptip_CustomProgressBar_ptip_progressBorderThickness, 0);

        } finally {
            // no idea, but the tutorial said i should have this.
            a.recycle();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {

        int marginPoint = progressBarHeight + progressPointMargin;
        int stepSize= getMeasuredWidth()/progressNumSteps;
        int drawAtX = stepSize/2;
        int drawAtY = getMeasuredHeight()/2;

        colorProgress.setStrokeWidth(progressBarHeight);
        colorNonProgress.setStrokeWidth(progressBarHeight);
        colorBorder.setStrokeWidth(progressBarHeight + (progressBorderThick*2));

        int i = 1;
// draw the part of the bar that's filled
        for( i = i ; i <= progressValue && i<=progressNumSteps; i++){
            canvas.drawCircle(drawAtX, drawAtY, (progressPointRadius + progressBorderThick), colorBorder );
            canvas.drawCircle(drawAtX, drawAtY, progressPointRadius, colorProgress );

    // IF the line is a Filled Line
            if( i < ( progressValue ) ){
        // Border Around
                canvas.drawLine(
                        // start X and start Y
                        drawAtX + marginPoint - progressBorderThick, drawAtY,
                        // End X and End Y
                        drawAtX + stepSize - marginPoint + progressBorderThick, drawAtY,
                        // Color
                        colorBorder
                );
        // color within
                canvas.drawLine(
                        // start X and start Y
                        drawAtX + marginPoint, drawAtY,
                        // End X and End Y
                        drawAtX + stepSize - marginPoint, drawAtY,
                        // Color
                        colorProgress
                );
            }
    // IF the line is Unfilled.
            else if (i == progressValue && progressValue != progressNumSteps) {
        // Border Around.
                canvas.drawLine(
                        // start X and start Y
                        drawAtX + marginPoint - progressBorderThick, drawAtY,
                        // End X and End Y
                        drawAtX + stepSize - marginPoint + progressBorderThick, drawAtY,
                        // Color
                        colorBorder
                );
        // color Within.
                canvas.drawLine(
                        // start X and start Y
                        drawAtX + marginPoint, drawAtY,
                        // End X and End Y
                        drawAtX + stepSize - marginPoint, drawAtY,
                        // Color
                        colorNonProgress
                );
            }
            drawAtX += stepSize;
        }

// draw the unfilled section
        for( i = i ; i <= progressNumSteps; i++) {
            if (i != progressNumSteps) {
                canvas.drawLine(
                        // start X and start Y
                        drawAtX + marginPoint - progressBorderThick, drawAtY,
                        // End X and End Y
                        drawAtX + stepSize - marginPoint + progressBorderThick, drawAtY,
                        // Color
                        colorBorder
                );
                canvas.drawLine(
                        // start X and start Y
                        drawAtX + marginPoint, drawAtY,
                        // End X and End Y
                        drawAtX + stepSize - marginPoint, drawAtY,
                        // Color
                        colorNonProgress
                );
            }
            canvas.drawCircle(drawAtX, drawAtY, (progressPointRadius + progressBorderThick), colorBorder);
            canvas.drawCircle(drawAtX, drawAtY, progressPointRadius, colorNonProgress);
            drawAtX += stepSize;

        }



    }
    public int getProgressValue() {
        return progressValue;
    }
    public void setProgressValue(int progressValue) {
        this.progressValue = progressValue;
        invalidate();
        requestLayout();
    }

    public int getProgressNumSteps() {
        return progressNumSteps;
    }
    public void setProgressNumSteps(int progressNumSteps) {
        this.progressNumSteps = progressNumSteps;
        invalidate();
        requestLayout();
    }

    public int getProgressPointRadius() {
        return progressPointRadius;
    }
    public void setProgressPointRadius(int progressPointRadius) {
        this.progressPointRadius = progressPointRadius;
        invalidate();
        requestLayout();
    }

    public int getProgressBarHeight() {
        return progressBarHeight;
    }
    public void setProgressBarHeight(int progressBarHeight) {
        this.progressBarHeight = progressBarHeight;
        invalidate();
        requestLayout();
    }

    public Paint getColorProgress() {
        return colorProgress;
    }
    public void setColorProgress(Paint colorProgress) {
        this.colorProgress = colorProgress;
        invalidate();
        requestLayout();
    }

    public Paint getColorNonProgress() {
        return colorNonProgress;
    }
    public void setColorNonProgress(Paint colorNonProgress) {
        this.colorNonProgress = colorNonProgress;
        invalidate();
        requestLayout();
    }

    public Paint getColorBorder() {
        return colorBorder;
    }
    public void setColorBorder(Paint colorBorder) {
        this.colorBorder = colorBorder;
        invalidate();
        requestLayout();
    }
}
