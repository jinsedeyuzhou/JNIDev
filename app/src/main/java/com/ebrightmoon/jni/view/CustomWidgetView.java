package com.ebrightmoon.jni.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 作者：create by  Administrator on 2018/12/24
 * 邮箱：2315813288@qq.com
 */
public class CustomWidgetView extends View {

    private Paint paint;

    public CustomWidgetView(Context context) {
        super(context);
        init();
    }

    public CustomWidgetView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomWidgetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public CustomWidgetView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
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
//        float[] points = {0, 0, 50, 50, 50, 100, 100, 50, 100, 100, 150, 50, 150, 100};
        float[] points = {40, 20, 100, 20, 70, 20, 70, 120, 20, 120, 120, 120, 150, 20, 250, 20, 150, 20, 150, 120, 250, 20, 250, 120, 150, 120, 250, 120};
//        canvas.drawPoint(100, 100, paint);
        Path path = new Path();
        path.addArc(200, 200, 400, 400, -225, 225);
        path.arcTo(400, 200, 600, 400, -180, 225, false);
        path.lineTo(400, 542);
        canvas.drawPath(path,paint);


    }
}
