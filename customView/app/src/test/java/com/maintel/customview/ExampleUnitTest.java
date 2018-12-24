package com.maintel.customview;

import android.util.Log;

import org.joda.time.DateTime;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void sanjiaoTest() {
        System.out.println(Math.sin(Math.PI / 4) * 8);

        System.out.println(Math.sin(Math.PI * 23 / 180) * 50);

//        Log.d("ExampleUnitTest", "Math.sin(5):" + Math.sin(5));
//        Log.d("ExampleUnitTest", "Math.cos(5):" + Math.cos(5));
    }

    @Test
    public void math() {
        int a = 4;
        float b = 0.2f;
//        System.out.println(a / b);


        for (int i = 1; i <= 6; i++) {
            for (int j = 1; j <= 7; j++) {
                System.out.println(i * j);
            }
        }


    }

    @Test
    public void dateTest() {
        DateTime dateTime = new DateTime();
//        Log.d("MonthCalenderView", "dateTime.getDayOfWeek():" + dateTime.getDayOfWeek());
//        Log.d("MonthCalenderView", "dateTime.getDayOfMonth():" + dateTime.getDayOfMonth());
//        Log.d("MonthCalenderView", "dateTime.millisOfDay():" + dateTime.millisOfDay());
//        Log.d("MonthCalenderView", "dateTime.dayOfMonth().withMinimumValue():" + dateTime.dayOfMonth().withMinimumValue());
        DateTime dateTime2 = new DateTime(dateTime.dayOfMonth().withMinimumValue());
        int lastMonth = dateTime2.getDayOfWeek();
        System.out.println(dateTime2.plusMonths(-1).getMonthOfYear());
        System.out.println(dateTime2.plusMonths(-1).dayOfMonth().withMaximumValue().getDayOfMonth());

        DateTime dateTime1 = new DateTime();
        System.out.println(dateTime.toString("yyyy-MM-dd"));
        System.out.println(dateTime.toString());
        System.out.println(dateTime1.toString());


        System.out.println((dateTime.toString("yyyy-MM-dd")).equals(dateTime1.toString("yyyy-MM-dd")));
    }


}