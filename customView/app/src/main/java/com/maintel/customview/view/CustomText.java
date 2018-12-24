package com.maintel.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.maintel.customview.R;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/7/21 10:19
 * 备注：
 */

public class CustomText extends View {
    public CustomText(Context context) {
        this(context, null);
    }

    public CustomText(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        context.obtainStyledAttributes(attrs, R.styleable.CustomText);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomText, defStyleAttr, 0);

        title = typedArray.getString(R.styleable.CustomText_text);
        color = typedArray.getColor(R.styleable.CustomText_color, Color.BLUE);
        size = typedArray.getDimensionPixelSize(R.styleable.CustomText_size, (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP, 16, getResources().getDisplayMetrics()));
        typedArray.recycle();

        mPaint = new Paint();
        mPaint.setTextSize(size);
        mPaint.setColor(color);
        mRect = new Rect();
        mPaint.getTextBounds(title, 0, title.length(), mRect);
    }


    private String title;
    private int color;
    private int size;

    private Paint mPaint;
    private Rect mRect;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            float textWidth = mRect.width();
            width = (int) (getPaddingLeft() + textWidth + getPaddingRight());
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            float textHeight = mRect.height();
            height = (int) (getPaddingTop() + getPaddingBottom() + textHeight);
        }

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setColor(Color.RED);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);

        mPaint.setColor(color);
        canvas.drawText(title, getWidth() / 2 - mRect.width() / 2, getHeight() / 2 + mRect.height() / 2, mPaint);

    }
}
