package com.maintel.customview;

import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.maintel.customview.textview.MyClickableSpan;
import com.maintel.customview.textview.MyTextViewJ;
import com.maintel.customview.utils.LogUtils;

import java.util.Date;

@Route(path = "/test/mainActivity")
public class MainActivity extends AppCompatActivity {

    boolean isShow = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindowManager();

        SpannableString spanableInfo = new SpannableString("这是一个测试" + ": " + "点击我" + "这是一个测试" + ": " + "点击我");
        MyTextViewJ textView = (MyTextViewJ) findViewById(R.id.tv_textview_test);
        spanableInfo.setSpan(new Clickable(clickListener, 8, 13), 8, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new Clickable(clickListener, 2, 4), 2, 4, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spanableInfo.setSpan(new MyClickableSpan(16, 19), 16, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        textView.setText(spanableInfo);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    System.out.println(motionEvent.getX() + ":::::::" + motionEvent.getY());
                }
                return false;
            }
        });

//        FanChart fanChart = new FanChart(this);
//        setContentView(fanChart);
//        Map<String, Integer> map = new HashMap<>();
//        map.put("test", 1);
//        map.put("test1", 2);
//        map.put("test2", 3);
//        map.put("test33214242", 1);
//        map.put("test4", 4);
//        map.put("test5", 5);
//        fanChart.setContentMap(map);
//        fanChart.setColors(new int[]{getResources().getColor(R.color.color_1),
//                getResources().getColor(R.color.color_5),
//                getResources().getColor(R.color.color_4),
//                getResources().getColor(R.color.color_3),
//                getResources().getColor(R.color.color_2)});
//        fanChart.startDraw();


        findViewById(R.id.tv_add).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("aaaaaaaaaaaaaa");
            }
        });
        findViewById(R.id.iv_111).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("11111111111111");
            }
        });
        findViewById(R.id.iv_222).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LogUtils.d("22222222222222222222");
            }
        });


        findViewById(R.id.ll_hor_sliding_hide_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startAnim(view);
//

            }
        });
    }


    public interface MyOnClickListener {
        void onClick(View v, int start, int end);
    }

    private MyOnClickListener clickListener = new MyOnClickListener() {

        @Override
        public void onClick(View v, int start, int end) {
            System.out.println(v);
            Toast.makeText(MainActivity.this, "start:" + start + "  end:" + end, Toast.LENGTH_SHORT).show();
            int[] startLocation = textViewTest(start);
            int[] endLocation = textViewTest(end);
            int x = startLocation[1] + (endLocation[1] - startLocation[1]) / 2;
            // 需要处理折行的情况！！！
            // 这个就按照各自的需求来做了
            showPopUp(v, x, startLocation[0]);
        }
    };

    class Clickable extends ClickableSpan {
        private final MyOnClickListener mListener;

        private int start;
        private int end;

        public Clickable(MyOnClickListener l, int start, int end) {
            mListener = l;
            this.start = start;
            this.end = end;
        }

        /**
         * 重写父类点击事件
         */
        @Override
        public void onClick(View v) {
            mListener.onClick(v, start, end);
        }

        /**
         * 重写父类updateDrawState方法  我们可以给TextView设置字体颜色,背景颜色等等...
         */
        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setColor(getResources().getColor(R.color.color_1));
        }
    }


    private int[] textViewTest(int position) {
        int[] tx = new int[2];
        TextView textView = (TextView) findViewById(R.id.tv_textview_test);
        Layout layout = textView.getLayout();
        Rect bound = new Rect();
        int line = layout.getLineForOffset(position);
        layout.getLineBounds(line, bound);
        int yAxisTop = bound.top;//字符顶部y坐标
        int yAxisBottom = bound.bottom;//字符底部y坐标
        float xAxisLeft = layout.getPrimaryHorizontal(position);//字符左边x坐标
        float xAxisRight = layout.getSecondaryHorizontal(position);//字符右边x坐标

        System.out.println("yAxisTop::" + yAxisTop);
        System.out.println("yAxisBottom::" + yAxisBottom);
        System.out.println("xAxisLeft::" + xAxisLeft);
        System.out.println("xAxisRight::" + xAxisRight);
        tx[0] = yAxisTop;
        tx[1] = (int) xAxisLeft;
        return tx;
    }


    private PopupWindow popupWindow;

    private void showPopUp(View v, int x, int y) {
        LinearLayout layout = new LinearLayout(this);
        layout.setBackgroundColor(Color.GRAY);
        TextView tv = new TextView(this);
        tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv.setText("I'm a pop -----------------------------!");
        tv.setTextColor(Color.WHITE);
        layout.addView(tv);

        popupWindow = new PopupWindow(layout, 300, 120);

        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());

        int[] location = new int[2];
        v.getLocationOnScreen(location);
        System.out.println("location::" + location[0] + "::" + location[1]);

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                location[0] + x - popupWindow.getWidth() / 2,
                location[1] - popupWindow.getHeight() + y);
    }


    private void startAnim(View view) {

        LogUtils.d(view.getTranslationX() + "");

        if (isShow) {
            ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", 0);

            animation.setDuration(1000);
            animation.start();
        } else {
            ObjectAnimator animation = ObjectAnimator.ofFloat(view, "translationX", -findViewById(R.id.ll_button).getWidth());

            animation.setDuration(1000);
            animation.start();
//            Animation animation = new TranslateAnimation(150, 0, 0, 0);
//            animation.setFillAfter(true);
//            animation.setDuration(1000);
//            view.startAnimation(animation);
        }
        isShow = !isShow;
    }
}
