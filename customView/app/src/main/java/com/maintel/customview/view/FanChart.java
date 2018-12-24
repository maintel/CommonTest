package com.maintel.customview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

import com.maintel.customview.R;

import java.util.Iterator;
import java.util.Map;
import java.util.Random;

import static android.R.attr.textSize;

/**
 * 说明：先画一个扇形图
 * 作者：mainTel  //http://blog.csdn.net/u012209506/article/details/53320815
 * 时间：2017/7/21 14:24
 * 备注：
 */

public class FanChart extends View {

    private static final int QUADRANT_4 = 324;
    private static final int QUADRANT_3 = 426;
    private static final int QUADRANT_2 = 946;
    private static final int QUADRANT_1 = 858;

    private static final int SWEEP_ANGLE_MAX = 20; //允许写入文字的最小角度，这个应该和半径有关 待计算
    private static final int DEFAULT_RADIUS = 200;
    private static final int TEXT_COLOR_DEFAULT = Color.BLUE;


    private int[] colors = new int[]{Color.rgb(255,255, 150),
            Color.rgb(255, 201, 122),
            Color.rgb(255,242,111),
            Color.rgb(255,190,135),
            Color.rgb(255,252,178)};

    public FanChart(Context context) {
        this(context, null);
    }

    public FanChart(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FanChart(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        context.obtainStyledAttributes(attrs, R.styleable.FanChart);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FanChart, defStyleAttr, 0);
        init(typedArray);

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mTextPaint = new Paint();
        mTextPaint.setColor(innerTextColor);
        mTextPaint.setAntiAlias(true); //抗锯齿
        mTextPaint.setTextAlign(Paint.Align.CENTER);
    }

    /**
     * 初始化各种内容
     *
     * @param typedArray
     */
    private void init(TypedArray typedArray) {
        for (int i = 0; i < typedArray.getIndexCount(); i++) {
            int type = typedArray.getIndex(i);
            switch (type) {
                case R.styleable.FanChart_isShowInnerText:
                    isShowInnerText = typedArray.getBoolean(type, true);
                    break;
                case R.styleable.FanChart_innerCircleRadius:
                    innerCircleRadius = typedArray.getFloat(type, 0.3f);
                    break;
                case R.styleable.FanChart_innerTextColor:
                    innerTextColor = typedArray.getColor(type, TEXT_COLOR_DEFAULT);
                    break;
                case R.styleable.FanChart_innerCircleColor:
                    innerCircleColor = typedArray.getColor(type, Color.TRANSPARENT);
                    break;
                case R.styleable.FanChart_radius:
                    radius = typedArray.getDimensionPixelSize(type, DEFAULT_RADIUS);
                    Log.e("FanChart", "FanChart_radius:" + radius);
                    break;
                case R.styleable.FanChart_innerText:
                    innerText = typedArray.getString(type);
                    break;
                case R.styleable.FanChart_minAngle:
                    minAngle = typedArray.getInt(type, 10);
                    break;
                case R.styleable.FanChart_textColor:
                    textColor = typedArray.getColor(type, TEXT_COLOR_DEFAULT);
                    break;
                case R.styleable.FanChart_textSize:
                    textSize = typedArray.getDimensionPixelSize(type, 20);
                    break;
                case R.styleable.FanChart_innerTextSize:
                    innerTextSize = typedArray.getDimensionPixelSize(type, 40);
                    break;
            }
        }
    }

    private Paint mPaint;
    private Paint mTextPaint;
    private RectF mRectf;

    /**
     * 设置扇形颜色组
     *
     * @param colors
     */
    public void setColors(int[] colors) {
        this.colors = colors;
    }

    /**
     * 设置数据源
     *
     * @param contentMap
     */
    public void setContentMap(Map<String, Integer> contentMap) {
        this.contentMap = contentMap;
        for (Map.Entry<String, Integer> map :
                contentMap.entrySet()) {
            total += map.getValue();
        }
    }

    public Map<String, Integer> getContentMap() {
        return contentMap;
    }

    public String getInnerText() {
        return innerText;
    }

    /**
     * 设置中心的文字
     *
     * @param innerText
     */
    public void setInnerText(String innerText) {
        this.innerText = innerText;
    }

    public float getInnerCircleRadius() {
        return innerCircleRadius;
    }

    /**
     * 设置内圆的半径 和外圆半径的比例 如一半就是0.5
     *
     * @param innerCircleRadius
     */
    public void setInnerCircleRadius(float innerCircleRadius) {
        this.innerCircleRadius = innerCircleRadius;
    }

    public float getMinAngle() {
        return minAngle;
    }

    /**
     * 设置写入文字的最小角度
     *
     * @param minAngle
     */
    public void setMinAngle(float minAngle) {
        this.minAngle = minAngle;
    }

    /**
     * 设置中心文字的颜色
     *
     * @param innerTextColor
     */
    public void setInnerTextColor(int innerTextColor) {
        this.innerTextColor = innerTextColor;
    }

    /**
     * 设置中心圆的颜色
     *
     * @param innerCircleColor
     */
    public void setInnerCircleColor(int innerCircleColor) {
        this.innerCircleColor = innerCircleColor;
    }

    /**
     * 设置扇形文字的颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
    }

    /**
     * 设置扇形文字的大小sp
     *
     * @param textSize
     */
    public void setTextSize(int textSize) {
        this.textSize = sp2px(textSize);
    }

    /**
     * 设置内部文字的大小 sp值
     *
     * @param innerTextSize
     */
    public void setInnerTextSize(int innerTextSize) {
        this.innerTextSize = sp2px(innerTextSize);
    }

    private boolean isShowInnerText = false;
    private float innerCircleRadius;
    private int innerTextColor = TEXT_COLOR_DEFAULT;
    private int innerCircleColor = Color.TRANSPARENT;
    private Map<String, Integer> contentMap;
    private int radius = DEFAULT_RADIUS;
    private String innerText;
    private float minAngle = 10;  //最小角度10°
    private int textColor = innerTextColor;
    private int textSize = 20;
    private int innerTextSize = 40;

    private int total;
    private int width;
    private int height;
    Context context;
    private float animatedValue;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width;
        int height;

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = radius * 2 + getPaddingLeft() + getPaddingRight();
            if (widthMode == MeasureSpec.AT_MOST) {
                width = Math.min(width, widthSize);
            }
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = radius * 2 + getPaddingBottom() + getPaddingTop();
            if (heightMode == MeasureSpec.AT_MOST) {
                height = Math.min(height, heightSize);
            }
        }
        setMeasuredDimension(width, height);
        this.width = width;
        this.height = height;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int r = (int) (Math.min(width - getPaddingLeft() - getPaddingRight(),
                height - getPaddingTop() - getPaddingBottom()) * 1.0f / 2);
        if (this.radius > r || radius == -1) {
            this.radius = r;
        }
        if (mRectf == null) {
            mRectf = new RectF((width - 2 * radius) / 2 + 20,
                    (height - 2 * radius) / 2 + 20,
                    width - (width - 2 * radius) / 2 - 20,
                    height - (height - 2 * radius) / 2 - 20);
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (contentMap == null) {
            return;
        }
        Iterator<Map.Entry<String, Integer>> iterator = contentMap.entrySet().iterator();
        float lastAngle = 0;
        int color = 0;
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> map = iterator.next();
            mPaint.setColor(getColor(color));
            color++;
            int size = map.getValue();
            float sweepAngle = countPro(size) * 360;
            if (Math.min(sweepAngle, animatedValue - lastAngle) >= 0) {
                canvas.drawArc(mRectf, lastAngle, Math.min(sweepAngle, animatedValue - lastAngle) - 1, true, mPaint);
                drawText(lastAngle, sweepAngle, map, canvas);
            }
            lastAngle += sweepAngle;
        }
        if (isShowInnerText) {
            mPaint.setColor(innerCircleColor);
            canvas.drawCircle(getWidth() / 2, getHeight() / 2, radius * innerCircleRadius, mPaint);
            mTextPaint.setTextSize(innerTextSize);
            canvas.drawText(innerText, getWidth() / 2, getHeight() / 2 + 10, mTextPaint);
        }
    }

    /**
     * 对每一部分写上名称
     *
     * @param lastAngle
     * @param map
     * @param canvas
     */
    private void drawText(float lastAngle, float sweepAngle, Map.Entry<String, Integer> map, Canvas canvas) {
        //文字写在当前部分所占角度的中线上 内部0.75处或者延长线上
        float textAngele = lastAngle + sweepAngle / 2;
        mTextPaint.setTextSize(textSize);
        mTextPaint.setColor(textColor);
        if (sweepAngle > SWEEP_ANGLE_MAX) {
            canvas.drawText(map.getKey(), (float) (Math.cos(textAngele / 180 * Math.PI) * radius * 0.75) + width / 2 - map.getKey().length() / 2, (float) (Math.sin(textAngele / 180 * Math.PI) * 0.75 * radius) + height / 2 + sp2px(5), mTextPaint);
        } else {
            canvas.drawText(map.getKey(), (float) (Math.cos(textAngele / 180 * Math.PI) * radius * 1.0) + width / 2 - map.getKey().length() / 2, (float) (Math.sin(textAngele / 180 * Math.PI) * 1.0 * radius + height / 2) + sp2px(5), mTextPaint);
        }
    }

    private float countPro(int size) {
        return (float) size / (float) total;
    }

    private int getColor(Integer value) {
        return colors[value % colors.length];
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public int dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }

    private void initAnimator() {
        ValueAnimator anim = ValueAnimator.ofFloat(0, 360);
        anim.setDuration(1000);
        anim.setInterpolator(new AccelerateInterpolator());
        anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                animatedValue = (float) valueAnimator.getAnimatedValue();
                invalidate();
            }
        });
        anim.start();
    }

    public void startDraw() {
        initAnimator();
    }
}
