package com.maintel.customview.textview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.Spannable;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.MotionEvent;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author jieyu.chen
 * @date 2018/9/17
 */
public class MyTextViewJ extends TextView {
    public MyTextViewJ(Context context) {
        this(context, null);
    }

    public MyTextViewJ(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyTextViewJ(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        path = new Path();
    }

    private List<UnderLineOptions> lineOptions = new ArrayList<>();

    private Paint paint;

    private DashPathEffect effects = new DashPathEffect(new float[]{5f, 5f, 5f, 5f}, 3f);
    private Path path;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (UnderLineOptions options: lineOptions) {
            if (options.getLinesXY() != null) {
                if (options.getLineStyle() == UnderLineOptions.Style.LINE_STYLE_DOTTED) {
                    paint.setPathEffect(effects);
                } else {
                    paint.setPathEffect(null);
                }
                int color = options.getLineColor();
                paint.setColor(color);
                for (int i = 0; i < options.getLinesXY().size(); i++) {
                    if (i % 2 == 0) {
                        // 用下标的奇偶来表示开始还是结束
                        path.moveTo(options.getLinesXY().get(i)[0], options.getLinesXY().get(i)[3]);
                    } else {
                        path.lineTo(options.getLinesXY().get(i)[0], options.getLinesXY().get(i)[3]);
                        canvas.drawPath(path, paint);
                        path.reset();
                    }
                }
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        System.out.println("onDetachedFromWindow");
        lineOptions.clear();
        super.onDetachedFromWindow();
    }

    /**
     * 计算坐标
     *
     * @param poi
     * @return
     */
    private float[] measureXY(int poi) {
        float[] floats = new float[4];
        Layout layout = getLayout();
        int line = layout.getLineForOffset(poi);
        Rect rect = new Rect();
        layout.getLineBounds(line, rect);
        // 原因不明，获取到的坐标左右相同
        //左
        floats[0] = layout.getPrimaryHorizontal(poi) + getPaddingLeft();
        //上
        floats[1] = rect.top + getPaddingTop();
        //右
        floats[2] = layout.getSecondaryHorizontal(poi) + getPaddingLeft();
        //下
        floats[3] = rect.bottom + getPaddingTop();
        return floats;
    }

    public void setLine(@NotNull final UnderLineOptions option) {
        post(new Runnable() {
            @Override
            public void run() {
                if (!addOptions(option)) {
                    return;
                }
                invalidate();
            }
        });
    }

    public void setLines(@NotNull final List<UnderLineOptions> lines) {
        lineOptions.clear();
        post(new Runnable() {
            @Override
            public void run() {
                for (UnderLineOptions option: lines) {
                    if (!addOptions(option)) {
                        break;
                    }
                }
                invalidate();
            }
        });
    }

    /**
     * 将配置内容添加到列表中
     *
     * @param option
     * @return
     */
    private boolean addOptions(UnderLineOptions option) {
        int s = option.getLineStart();
        int e = option.getLineEnd();
        if (s > getText().toString().length()) {
            return false;
        }
        if (e < 0) {
            return false;
        }
        int start = s < 0 ? 0 : s;
        int end = e > getText().toString().length() ? getText().toString().length() : e;
        option.setContentStr(getText().toString().substring(start, end));
        if (option.getMyClickableSpan() != null) {
            option.getMyClickableSpan().setMStart(start);
            option.getMyClickableSpan().setMEnd(end);
            option.getMyClickableSpan().setContentStr(getText().toString().subSequence(start, end).toString());
        }
        // 可以通过这种方法获取被这一部分是否可以被点击
//        ClickableSpan[] links = ((Spannable) getText()).getSpans(start,
//                end, ClickableSpan.class);
//        System.out.println(getSelectionStart());
//        System.out.println(getSelectionEnd());
//        System.out.println(links.length > 0 ? links[0] : links);
        float[] startXY = measureXY(start);
        float[] endXY = measureXY(end);
        List<float[]> listXY = new ArrayList<>();
        if (startXY[1] == endXY[1]) {
            listXY.add(startXY);
            listXY.add(endXY);
            //找到弹出框的中间点
            if (option.getMyClickableSpan() != null) {
                int x = (int) (startXY[0] + (endXY[0] - startXY[0]) / 2);
                option.getMyClickableSpan().setX(x);
                option.getMyClickableSpan().setY((int) endXY[1]);
            }
            option.setLinesXY(listXY);
        } else {
            // 这里处理折行的情况
            // 对于折行的弹窗，只能根据需求来做了。
            int lineStart = getLayout().getLineForOffset(start);
            int lineEnd = getLayout().getLineForOffset(end);
            int lineNum = lineStart;

            while (lineNum <= lineEnd) {
                Rect rect1 = new Rect();
                getLayout().getLineBounds(lineNum, rect1);
                System.out.println(rect1);
                System.out.println(getLayout().getLineMax(lineNum));
                if (lineNum == lineStart) {
                    float[] endXYN = new float[]{getLayout().getLineMax(lineNum) + measureXY(getLayout().getLineStart(lineNum))[0],
                            startXY[0],
                            getLayout().getLineMax(lineNum) + measureXY(getLayout().getLineStart(lineNum))[0],
                            startXY[3]};
                    listXY.add(startXY);
                    listXY.add(endXYN);
                } else if (lineNum == lineEnd) {
                    float[] startXYN = new float[]{measureXY(getLayout().getLineStart(lineNum))[0],
                            endXY[0],
                            measureXY(getLayout().getLineStart(lineNum))[0],
                            endXY[3]};
                    listXY.add(startXYN);
                    listXY.add(endXY);
                } else {
                    Rect rect = new Rect();
                    getLayout().getLineBounds(lineNum, rect);
                    float[] startXYN = new float[]{measureXY(getLayout().getLineStart(lineNum))[0],
                            rect.top + getPaddingTop(),
                            measureXY(getLayout().getLineStart(lineNum))[0],
                            rect.bottom + getPaddingTop()};
                    float[] endXYN = new float[]{getLayout().getLineMax(lineNum) + measureXY(getLayout().getLineStart(lineNum))[0],
                            rect.top + getPaddingTop(),
                            getLayout().getLineMax(lineNum) + measureXY(getLayout().getLineStart(lineNum))[0],
                            rect.bottom + getPaddingTop()};
                    listXY.add(startXYN);
                    listXY.add(endXYN);
                }
                lineNum++;
            }
            option.setLinesXY(listXY);
        }
        lineOptions.add(option);
        return true;
    }

    @Override
    public boolean performClick() {
        ClickableSpan[] links = ((Spannable) getText()).getSpans(getSelectionStart(),
                getSelectionEnd(), ClickableSpan.class);
        if (links.length > 0) {
            return false;
        }
        return super.performClick();
    }

}
