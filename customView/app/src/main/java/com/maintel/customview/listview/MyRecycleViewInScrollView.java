package com.maintel.customview.listview;

import android.content.Context;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.TranslateAnimation;

/**
 * @author jieyu.chen
 * @date 2018/11/1
 */
public class MyRecycleViewInScrollView extends RecyclerView {
    public MyRecycleViewInScrollView(Context context) {
        super(context);
    }

    public MyRecycleViewInScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyRecycleViewInScrollView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
//        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
//                MeasureSpec.AT_MOST);
        super.onMeasure(widthSpec, heightSpec);
    }


    @Override
    public boolean onTouchEvent(MotionEvent e) {

        commOnTouchEvent(e);

        return super.onTouchEvent(e);
    }


    private float downX;
    private static final int DEFAULT_DEVIDE = 4;
    private Rect normal = new Rect();

    private boolean commOnTouchEvent(MotionEvent ev) {
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
                if (isIntercept) {
                    if (normal.isEmpty()) {
                        // 保存正常的布局位置
                        normal.set(this.getLeft(), this.getTop(),
                                this.getRight(), this.getBottom());
                    }
                    this.layout(this.getLeft() - deltX, this.getTop(), this.getRight() - deltX, this.getBottom());
                }

//                }
                break;
            case MotionEvent.ACTION_UP:
                if (isNeedAnimation()) {
                    // Log.v("mlguitar", "will up and animation");
                    animation();
                    isIntercept = false;
                    return true;
                }
                break;
        }
        return false;
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
                return true;
//            break;
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
                        isIntercept = true;
                        return true;
                    }
                }

                if (ev.getX() - downxInter <= -30) {
                    //不能滚动就直接移动布局 并且是左右滑动
                    if (isNeedRight()) {
                        // 这个时候应该拦截掉事件
                        if (getParent() != null)
                            getParent().requestDisallowInterceptTouchEvent(true);
                        isIntercept = true;
                        return true;
                    }
                }
            case MotionEvent.ACTION_UP:
                return true;
//                break;
        }
        System.out.println("onInterceptTouchEvent------------------------------");

        return true;
    }


    private boolean isNeedRight() {
        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.getLayoutManager();


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
        System.out.println(lastPoi == this.getAdapter().getItemCount() - 1);
        System.out.println(isRight);

        return (lastPoi == 0 || lastPoi == this.getAdapter().getItemCount() - 1) && isRight;


//        //头和尾
//        if (scrollX == offset) {
//            return true;
//        }
    }

    private boolean isNeedMove = false;

    private boolean isNeedMove() {

        LinearLayoutManager linearLayoutManager = (LinearLayoutManager) this.getLayoutManager();
        int position = linearLayoutManager.findFirstVisibleItemPosition();
        View v = linearLayoutManager.findViewByPosition(position);
        System.out.println("v.getX()::" + v.getX());
        System.out.println("v.getScrollX()::" + v.getScrollX());
        //头  这里应该加上 mergin 值
        return position == 0 && v.getX() == 0;
    }

    // 开启动画移动

    public void animation() {
        // 开启移动动画
        TranslateAnimation ta = new TranslateAnimation(getLeft(), normal.left, 0,
                0);
        ta.setDuration(300);
        this.startAnimation(ta);
//        // 设置回到正常的布局位置
        this.layout(normal.left, normal.top, normal.right, normal.bottom);
        normal.setEmpty();
    }

    // 是否需要开启动画
    public boolean isNeedAnimation() {
        return !normal.isEmpty();
    }
}
