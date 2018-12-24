package com.maintel.customview.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author jieyu.chen
 * @date 2018/7/16
 */
public class XFermodeTest extends View {
    public XFermodeTest(Context context) {
        this(context, null);
    }

    public XFermodeTest(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XFermodeTest(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint();
    }

    private Paint mPaint;


    @Override
    protected void onDraw(Canvas canvas) {
        thirdExample(canvas);

    }

    private void thirdExample(Canvas canvas) {
        //创建一个Bitmap
        Bitmap out = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        //创建该Bitmap的画布
        Canvas bitmapCanvas = new Canvas(out);
        //绘制一个正方型
        mPaint.setColor(Color.WHITE);
        bitmapCanvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
        //设置相交时候，图层显示的模式(表示当相交的时候，圆形为先绘制的图形)
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.XOR));
        //绘制一个圆
        mPaint.setColor(Color.RED);
        bitmapCanvas.drawCircle(300, 300, 200, mPaint);
        //最后，将完成的图片绘制在View上
        canvas.drawBitmap(out, 0, 0, null);
    }

}
