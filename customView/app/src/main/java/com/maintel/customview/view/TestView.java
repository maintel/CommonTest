package com.maintel.customview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;

import com.maintel.customview.R;

/**
 * @author jieyu.chen
 * @date 2018/7/6
 */
public class TestView extends View {
    public TestView(Context context) {
        this(context,null);
    }

    public TestView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public TestView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CustomText, defStyleAttr, 0);
        System.out.println("aaaa" + typedArray.getString(R.styleable.CustomText_text));
    }
}
