package com.maintel.customview.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import com.maintel.customview.view.MonthCalenderView;

import org.joda.time.DateTime;

/**
 * <p>name:</p>
 * <p>describe:</p>
 *
 * @author Maintel
 * @time 2017/7/30 23:20
 */

public class MonthCalenderAdapter extends PagerAdapter {

    private Context context;
    private SparseArray<MonthCalenderView> calenderList;

    private DateTime dateTimeNow;
    private int currentPage;


    public MonthCalenderAdapter(Context context, DateTime dateTime) {
        this.context = context;
        this.calenderList = new SparseArray();
        this.dateTimeNow = dateTime;
        currentPage = (dateTimeNow.getYear() - 1970) * 12 + dateTimeNow.getMonthOfYear();
    }

    @Override
    public int getCount() {  //总共有多少个月？
        return (2099 - 1970) * 12;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//        super.destroyItem(container, position, object);
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        MonthCalenderView monthCalenderView = calenderList.get(position);
        if (monthCalenderView == null) {
            int i = position - currentPage;
            DateTime dateTime = dateTimeNow.plusMonths(i);
            monthCalenderView = new MonthCalenderView(context, dateTime, 0);
            calenderList.put(position, monthCalenderView);
        }
        container.addView(monthCalenderView);
        return monthCalenderView;
    }
}
