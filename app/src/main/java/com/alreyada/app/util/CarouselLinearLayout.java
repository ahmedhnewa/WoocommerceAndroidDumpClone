package com.alreyada.app.util;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;


public class CarouselLinearLayout extends LinearLayout {
    //private float scale = AdapterHomeMedical.BIG_SCALE;

    public CarouselLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CarouselLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        //this.scale = scale;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // The main mechanism to display scale animation
        int w = this.getWidth();
        int h = this.getHeight();
        //canvas.scale(scale, scale, (float) (w/2), (float)(h/2));
    }
}
