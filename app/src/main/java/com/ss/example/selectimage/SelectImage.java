package com.ss.example.selectimage;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class SelectImage extends View {

    private Paint paint;
    private int mStartX;
    private int mStartY;
    private int mEndX;
    private int mEndY;
    private List<int[]> rectList = new ArrayList<>();

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
        paint.setColor(Color.YELLOW);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        RectF rectF = new RectF(mStartX,mStartY,mEndX,mEndY);
        canvas.drawRect(rectF, paint);
        for (int i = 0; i < rectList.size(); i++) {
            int[] ints = rectList.get(i);
            RectF rect = new RectF(ints[0],ints[1],ints[2],ints[3]);
            canvas.drawRect(rect, paint);
        }
    }

    public void getWz(int startX, int startY, int endX, int endY) {
        mStartX = startX;
        mStartY = startY;
        mEndX = endX;
        mEndY = endY;
        invalidate();
        int[] rectData = {mStartX,mStartY,mEndX,mEndY};
        rectList.add(rectData);
    }
}
