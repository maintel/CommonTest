package com.maintel.customview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.maintel.customview.utils.DeviceUtils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.V;
import static org.joda.time.DateTimeFieldType.dayOfMonth;

/**
 * <p>name: 月份日历</p>
 * <p>describe: 日期计算使用<a href="https://github.com/JodaOrg/joda-time">Joda-Time</a></p>
 *
 * @author Maintel
 * @time 2017/7/27 22:55
 */

public class MonthCalenderView extends View {

    private static final int DEFAULT_WIDTH = 560;
    private static final int DEFAULT_HEIGHT = 480;
    private static final int TEXTSIZE_DEFAULT = 12;
    private static final String[] WEEK = new String[]{"日", "一", "二", "三", "四", "五", "六"};

    public MonthCalenderView(Context context) {
        this(context, null);
    }

    public MonthCalenderView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MonthCalenderView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        textPaint = new Paint();
        textPaint.setColor(Color.BLUE);
        textSize = DeviceUtils.sp2px(context, TEXTSIZE_DEFAULT);
        textPaint.setTextSize(textSize);
        textPaint.setTextAlign(Paint.Align.CENTER);
        dateNow = new DateTime();
//        setTime(dateNow); //开始的时候不初始化，由其他构造函数初始化内容
    }

    public MonthCalenderView(Context context, DateTime dateTime, int a) {
        this(context);
        setTime(dateTime);
//        invalidate();
    }

    private void setTime(DateTime dateTime) {
        int maxDay = dateTime.dayOfMonth().withMaximumValue().getDayOfMonth();
        int firstDayWeek = dateTime.dayOfMonth().withMinimumValue().getDayOfWeek();
        DateTime lastMoth = dateTime.plusMonths(-1);
        int lastMothDay = lastMoth.dayOfMonth().withMaximumValue().getDayOfMonth();
        DateTime nextMonth = dateTime.plusMonths(1);
        dateList = new ArrayList<>();
        int j = 1;
        for (int i = 0; i < 42; i++) {  // 为方便起见，日历为一页六周共42天
            if (i < firstDayWeek) {  //说明是上一个月的
                int t = lastMothDay - firstDayWeek + i + 1;
                dateList.add(lastMoth.withDayOfMonth(t));
            } else if (i - firstDayWeek + 1 > maxDay) {
                dateList.add(nextMonth.withDayOfMonth(i - maxDay - firstDayWeek + 1));
            } else {
                dateList.add(dateTime.withDayOfMonth(j));
                j++;
            }
        }
    }

    private Context context;

    private Paint mPaint;
    private int width;
    private int height;
    private Paint textPaint;
    private List<DateTime> dateList;

    private int textSize;

    private DateTime dateClick;
    private DateTime dateNow;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
        int wideSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if (widthMode == MeasureSpec.EXACTLY) {
//            width = wideSize;
//        } else {
//            width = DEFAULT_WIDTH;
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            height = heightSize;
//        } else {
//            height = DEFAULT_HEIGHT;
//        }
//        setMeasuredDimension(width, height);
        setMeasuredDimension(wideSize, heightSize);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d("MonthCalenderView", "left:" + left);
        Log.d("MonthCalenderView", "top:" + top);
        Log.d("MonthCalenderView", "right:" + right);
        Log.d("MonthCalenderView", "bottom:" + bottom);
        Log.d("MonthCalenderView", "getWidth():" + getWidth());
        Log.d("MonthCalenderView", "getHeight():" + getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = this.width / 7;
        int width2 = this.width / 14;
        int height = this.height / 6;
        int height2 = this.height / 12;

//        for (int i = 0; i < 7; i++) {
//            canvas.drawText(WEEK[i], (width * i) + width2, height2 + textSize / 2, textPaint);
//        }

        if (dateList == null)
            return;

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                Rect rect = new Rect(width * j,
                        height * i,
                        width * (j + 1),
                        height * (i + 1));
                canvas.drawRect(rect, mPaint);

                DateTime dateTime = dateList.get(i * 7 + j);

                if (isToday(dateTime)) {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.GREEN);
                    canvas.drawCircle(width * (j + 1) - width2, height * (i + 1) - height2, width / 3, paint);
                }

                if (isCheckDay(dateTime)) {
                    Paint paint = new Paint();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setColor(Color.BLUE);
                    canvas.drawCircle(width * (j + 1) - width2, height * (i + 1) - height2, width / 3, paint);
                }
                canvas.drawText(dateTime.getDayOfMonth() + "",
                        width * j + width2,
                        height * i + height2 + textSize / 2,
                        textPaint);
            }
        }
    }

    /**
     * 是否是选择的那一天
     *
     * @param dateTime
     * @return
     */
    private boolean isCheckDay(DateTime dateTime) {
        return dateClick != null && (dateTime.toString("yyyy-MM-dd")).equals(dateClick.toString("yyyy-MM-dd"));
    }

    /**
     * 是否是当天
     *
     * @param dateTime
     * @return
     */
    private boolean isToday(DateTime dateTime) {
        return (dateTime.toString("yyyy-MM-dd")).equals(dateNow.toString("yyyy-MM-dd"));
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gestureDetector.onTouchEvent(event);
        return true;

    }

    private GestureDetector gestureDetector = new GestureDetector(context,
            new GestureDetector.SimpleOnGestureListener() {

                float xDown;
                float yDown;

                @Override
                public boolean onContextClick(MotionEvent e) {
                    return super.onContextClick(e);
                }

                @Override
                public boolean onDoubleTap(MotionEvent e) {
                    return super.onDoubleTap(e);
                }

                @Override
                public boolean onDoubleTapEvent(MotionEvent e) {
                    return super.onDoubleTapEvent(e);
                }

                @Override
                public boolean onDown(MotionEvent e) {
                    xDown = getX();
                    yDown = yDown;
                    return super.onDown(e);
                }

                @Override
                public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                    return super.onFling(e1, e2, velocityX, velocityY);
                }

                @Override
                public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                    Log.d("MonthCalenderView", "distanceX:" + distanceX);
                    Log.d("MonthCalenderView", "distanceY:" + distanceY);
                    return super.onScroll(e1, e2, distanceX, distanceY);
                }

                @Override
                public boolean onSingleTapConfirmed(MotionEvent e) {
                    return super.onSingleTapConfirmed(e);
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    computeLocal(e);
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    super.onLongPress(e);
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    super.onShowPress(e);
                }
            });

    /**
     * 计算点击的位置
     *
     * @param e
     */
    private void computeLocal(MotionEvent e) {
        int x = (int) Math.floor(e.getX() / (width / 7));
        int y = (int) Math.floor(e.getY() / (height / 6));
        int i = y * 7 + x;
        if (i >= 0) {
            dateClick = dateList.get(i);
            if (onCalenderClickListener != null) {
                onCalenderClickListener.onCalenderClick(dateClick);
            }
            invalidate();
        }
    }

    private OnCalenderClickListener onCalenderClickListener;

    public void setOnCalenderClickListener(OnCalenderClickListener onCalenderClickListener) {
        this.onCalenderClickListener = onCalenderClickListener;
    }

    public interface OnCalenderClickListener {
        void onCalenderClick(DateTime dateTime);
    }
}
