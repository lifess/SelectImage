package com.ss.example.selectimage;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SelectImage extends View {

    private Paint paint;
    private int mCircleX;
    private int mCircleY;
    private int mRadius;
    private List<int[]> list = new ArrayList<>();
    private Paint paint1;

    public SelectImage(Context context) {
        super(context);
        init();
    }

    public SelectImage(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(getResources().getColor(R.color.light_yellow));
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(30);
        paint1 = new Paint();
        paint1.setAntiAlias(true);
        paint1.setColor(Color.YELLOW);
        paint1.setStyle(Paint.Style.STROKE);
        paint1.setStrokeWidth(20);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawCircle(mCircleX, mCircleY, mRadius, paint);
        canvas.drawCircle(mCircleX, mCircleY, mRadius - 25, paint1);
        for (int i = 0; i < list.size(); i++) {
            int[] ints = list.get(i);
            canvas.drawCircle(ints[0], ints[1], ints[2], paint);
            canvas.drawCircle(ints[0], ints[1], ints[2] - 25, paint1);
        }
    }

    public void getWz(int circleX, int circleY, int radius) {
        mCircleX = circleX;
        mCircleY = circleY;
        mRadius = radius;
        invalidate();
        int[] data = {mCircleX, mCircleY, mRadius};
        list.add(data);
    }
}
