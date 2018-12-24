package com.maintel.customview.anim;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

public class PointEvaluator implements TypeEvaluator<PointF> {

    private float controlX;
    private float controlY;

    private int direction;
    //0 表示左边 1 表示右边

    public PointEvaluator(int direction) {
        this.direction = direction;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF startPoint = (PointF) startValue;
        PointF endPoint = (PointF) endValue;
//        int x = (int) (startPoint.x + fraction * (endPoint.x - startPoint.x));
//        int y = (int) (startPoint.y + fraction * (endPoint.y - startPoint.y));
        float t = 1 - fraction;
        if (controlX == 0) {
            controlX = startValue.x - (direction == 0 ? 20 : -20);
            controlY = (startValue.y - endValue.y) / 2 + endPoint.y;
        }
        int x = (int) (startPoint.x * t * t + 2 * fraction * t * controlX + endPoint.x * fraction * fraction);
        int y = (int) (startPoint.y * t * t + 2 * fraction * t * controlY + endPoint.y * fraction * fraction);

        PointF point = new PointF(x, y);
        return point;
    }

}