package com.maintel.customview.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;

import com.maintel.customview.adapter.MonthCalenderAdapter;

import org.joda.time.DateTime;

import static android.R.attr.width;

/**
 * <p>name:</p>
 * <p>describe:</p>
 *
 * @author Maintel
 * @time 2017/7/30 23:02
 */

public class XCalenderViewPager extends ViewPager {

    private static final int DEFAULT_WIDTH = 560;
    private static final int DEFAULT_HEIGHT = 480;

    public XCalenderViewPager(Context context) {
        this(context, null);
    }

    public XCalenderViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        DateTime dateTime = new DateTime();
        MonthCalenderAdapter adapter = new MonthCalenderAdapter(context, dateTime);
        setAdapter(adapter);
        setCurrentItem((dateTime.getYear() - 1970) * 12 + dateTime.getMonthOfYear());
    }

}
