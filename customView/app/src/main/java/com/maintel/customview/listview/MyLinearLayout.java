package com.maintel.customview.listview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.LinearLayout;

/**
 * @author jieyu.chen
 * @date 2018/11/1
 */
public class MyLinearLayout extends LinearLayout {
    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private RecyclerView inner;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 0)
            inner = (RecyclerView) getChildAt(0);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (inner != null) {
            commOnTouchEvent(ev);
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_CANCEL) {
            if (isNeedAnimation()) {
                // Log.v("mlguitar", "will up and animation");
                animation();
                isIntercept = false;
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    private float downX;
    private static final int DEFAULT_DEVIDE = 4;
    private Rect normal = new Rect();


    private void commOnTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downX = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                int deltX = (int) (downX - moveX) / DEFAULT_DEVIDE;
                downX = moveX;

                //不能滚动就直接移动布局
//                if (isNeedMove()) {
                // 这个时候应该拦截掉事件
                if (normal.isEmpty()) {
                    // 保存正常的布局位置
                    normal.set(inner.getLeft(), inner.getTop(),
                            inner.getRight(), inner.getBottom());
                    return;
                }
                inner.layout(inner.getLeft() - deltX, inner.getTop(), inner.getRight() - deltX, inner.getBottom());
                isIntercept = true;
//                }
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    // Log.v("mlguitar", "will up and animation");
                    animation();
                    isIntercept = false;
                }
                break;
        }
    }

    private float downxInter = 0;

    private boolean isIntercept = false;


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        System.out.println("onInterceptTouchEvent++++++++++++++++++++++++++++++++++++++++++");
        if (isIntercept) {
            return true;
        }
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downxInter = ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = ev.getX();
                int deltX = (int) (downX - moveX) / DEFAULT_DEVIDE;
                downX = moveX;

                // 如果是到最左端 并且向右滑动 > 30 则拦截
                // 如果是到最右端 并且向左滑动 > 30 则拦截


                if (ev.getX() - downxInter >= 30) {
                    //不能滚动就直接移动布局 并且是左右滑动
                    if (isNeedMove()) {
                        // 这个时候应该拦截掉事件
                        if (getParent() != null)
                            getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }

                if (ev.getX() - downxInter <= -30) {
                    //不能滚动就直接移动布局 并且是左右滑动
                    if (isNeedRight()) {
                        // 这个时候应该拦截掉事件
                        if (getParent() != null)
                            getParent().requestDisallowInterceptTouchEvent(true);
                        return true;
                    }
                }

                return false;
            case MotionEvent.ACTION_UP:
                return false;
        }
        return super.onInterceptTouchEvent(ev);
    }

    private boolean isNeedMove() {
        int offset = inner.getMeasuredWidth() - getWidth();
        int scrollX = getScrollX();

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) inner.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        View v = linearLayoutManager.findViewByPosition(position);
        System.out.println("v.getX()::" + v.getX());
        System.out.println("v.getScrollX()::" + v.getScrollX());

        int lastPoi = linearLayoutManager.findLastVisibleItemPosition();

        View vLast = linearLayoutManager.findViewByPosition(position);
        //头  这里应该加上 mergin 值
        return position == 0 && v.getX() == 0;
    }


    private boolean isNeedRight() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) inner.getLayoutManager();


        int lastPoi = linearLayoutManager.findLastVisibleItemPosition();

        View vLast = linearLayoutManager.findViewByPosition(lastPoi);
        System.out.println("vLast.getX() :: " + vLast.getX());


        // 计算宽度是否已经在最右侧

        /*
         是否是在最右侧的标志应该是
         */
        System.out.println("vLast.getWindowVisibility()::" + vLast.getWindowVisibility());

        System.out.println("vLast.getWidth()::" + vLast.getWidth());

        System.out.println("Device width :: " + this.getResources().getDisplayMetrics().widthPixels);

        boolean isRight = this.getResources().getDisplayMetrics().widthPixels - vLast.getX() - vLast.getWidth() >= 0;

        System.out.println(lastPoi == 0);
        System.out.println(lastPoi == inner.getAdapter().getItemCount() - 1);
        System.out.println(isRight);

        return (lastPoi == 0 || lastPoi == inner.getAdapter().getItemCount() - 1) && isRight;


//        //头和尾
//        if (scrollX == offset) {
//            return true;
//        }
    }

    // 开启动画移动

    public void animation() {
        // 开启移动动画
//        TranslateAnimation ta = new TranslateAnimation(getLeft(), normal.left, 0,
//                0);
//        ta.setDuration(1000);
//        inner.startAnimation(ta);
//        ta.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });
        // 设置回到正常的布局位置
        inner.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }
}
