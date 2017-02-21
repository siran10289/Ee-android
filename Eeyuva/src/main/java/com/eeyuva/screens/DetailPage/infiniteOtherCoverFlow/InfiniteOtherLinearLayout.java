package com.eeyuva.screens.DetailPage.infiniteOtherCoverFlow;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.eeyuva.screens.home.infiniteHotCoverFlow.InfiniteHotPagerAdapter;

public class InfiniteOtherLinearLayout extends LinearLayout {
    private float scale = InfiniteHotPagerAdapter.BIG_SCALE;

    public InfiniteOtherLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InfiniteOtherLinearLayout(Context context) {
        super(context);
    }

    public void setScaleBoth(float scale) {
        this.scale = scale;
        this.invalidate();    // If you want to see the scale every time you set
        // scale you need to have this line here,
        // invalidate() function will call onDraw(Canvas)
        // to redraw the view for you
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // The main mechanism to display scale animation, you can customize it
        // as your needs
        int w = this.getWidth();
        int h = this.getHeight();
        canvas.scale(scale, scale, w / 2, h / 2);

        super.onDraw(canvas);
    }
}
