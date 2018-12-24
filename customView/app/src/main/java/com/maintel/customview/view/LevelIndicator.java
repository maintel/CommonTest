package com.maintel.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * <p>name:</p>
 * <p>describe:等级指示条</p>
 *
 * @author Maintel
 * @time 2017/12/27 21:57
 */

public class LevelIndicator extends View {

    private static final int TEXTSIZE_DEFAULT = 12;
    public static final int DEFAULT_WIDTH = 400;
    public static final int DEFAULT_HEIGHT = 60;

    private Context context;
    private Paint mPaint;
    private Paint mTextPaint;
    private int textSize;
    private int indicatorWidth; // 每格计数的长度
    private int[] colors = {Color.BLUE, Color.RED, Color.DKGRAY, Color.YELLOW};

    private int width;
    private int height;

    public LevelIndicator(Context context) {
        this(context, null);
    }

    public LevelIndicator(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LevelIndicator(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        initView();
    }

    /**
     * 初始化
     */
    private void initView() {
        mPaint = new Paint();
        mTextPaint = new Paint();
        mTextPaint.setColor(Color.BLUE);
        textSize = TEXTSIZE_DEFAULT;
        mTextPaint.setTextSize(textSize);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = DEFAULT_WIDTH;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = DEFAULT_HEIGHT;
        }
        setMeasuredDimension(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        indicatorWidth = (getWidth() - getPaddingLeft() - getPaddingRight() - textSize) / 4;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < 4; i++) {
            mPaint.setColor(colors[i]);
            int left = i * indicatorWidth + getPaddingLeft();
            canvas.drawRect(left, getPaddingTop(), left + indicatorWidth, getPaddingTop() + 10, mPaint);
            canvas.drawText(i + "", left-2, getPaddingTop() + 23, mTextPaint);
        }
        canvas.drawText("mg/L", indicatorWidth * 4 + getPaddingLeft(), getPaddingTop() + 5, mTextPaint);
    }
}
