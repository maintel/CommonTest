package com.maintel.customview.view;

import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.widget.ImageView;

public class MoveImageView extends ImageView {
    public MoveImageView(Context context) {
        super(context);
    }
    public MoveImageView(Context context, AttributeSet attrs) {
        super(context,attrs);
    }



    public void setMPointF(PointF pointF) {
        setX(pointF.x);
        setY(pointF.y);
    }
}