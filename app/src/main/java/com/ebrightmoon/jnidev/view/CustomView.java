package com.ebrightmoon.jnidev.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DebugUtils;
import android.view.View;

import com.ebrightmoon.jnidev.utils.DensityUtils;

/**
 *
 */
public class CustomView extends View {


    private Paint paint;

    public CustomView(Context context) {
        super(context);
        init();
    }



    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /**
     * 初始化参数信息
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(false);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStyle(Paint.Style.STROKE );
        paint.setStrokeWidth(20);
        paint.setColor(Color.RED);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.parseColor("#88880000"));
//        canvas.drawCircle(300,300, DensityUtils.dip2px(30),paint);
        canvas.drawRect(100, 100, 500, 500,paint);


    }
}
