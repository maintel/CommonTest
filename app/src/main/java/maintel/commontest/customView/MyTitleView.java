package maintel.commontest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import maintel.commontest.utils.DeviceUtils;

/**
 * 说明：像画卷一样展开收起的标题view
 * 作者：mainTel
 * 时间：2016/12/21 15:29
 * 备注：
 */
public class MyTitleView extends FrameLayout {

    Paint mClearPaint;
    Paint paint = new Paint();
    private RectF rect = new RectF(), mClearRect = new RectF();
    int mWidth;
    int mHeight;

    public MyTitleView(Context context) {
        super(context);
        init();
    }

    public MyTitleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTitleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mClearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mClearPaint.setColor(Color.BLUE);
        //画笔的混合模式,为清除像素
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        rect.set(canvas.getClipBounds());
//        // canvas.drawColor(0x00ffffff);
//
        int saveCount = canvas.saveLayer(rect, paint, Canvas.ALL_SAVE_FLAG);//这里null在xml预览报错,要不报错传个全局new paint吧.
        super.dispatchDraw(canvas);

        canvas.drawRect(mClearRect, mClearPaint);
        canvas.restoreToCount(saveCount);

//        if (showReel) {
//            canvas.drawBitmap(reel, srcReel, dstReel, null);
//        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mClearRect.set(0, DeviceUtils.dp2px(73), getMeasuredWidth(), getMeasuredHeight());
    }

    public void openList() {
        openFromCurrentPosition();
    }

    public void closeList() {
        closeFromCurrentPosition();
    }

    private ValueAnimator animator;
    private ValueAnimator.AnimatorUpdateListener updateListener = new ValueAnimator.AnimatorUpdateListener() {

        @Override
        public void onAnimationUpdate(ValueAnimator animator) {
            float v = (Float) animator.getAnimatedValue();
//            mClearRect.set(mProgress = v, 0, mWidth, getMeasuredHeight());
            /**
             * (float left,
             float top,
             float right,
             float bottom)
             */
            mClearRect.set(0, v, mWidth, mHeight);
//            if (observer != null) {
//                observer.onScroll(mProgress);
//            }
            invalidate();
        }
    };

    // 大于一半自动全部展开
    private void openFromCurrentPosition() {
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(DeviceUtils.dp2px(73), mHeight);
        animator.addUpdateListener(updateListener);
//        animator.addListener(new SimpleAnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (observer != null) {
//                    observer.onOpen();
//                }
//            }
//        });
//        animator.setDuration((long) ((mWidth - mProgress) / mWidth * completelyOpenTime));
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    // 小于一半自动全部收回
    private void closeFromCurrentPosition() {
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(mHeight, DeviceUtils.dp2px(73));
        animator.addUpdateListener(updateListener);
//        animator.addListener(new SimpleAnimatorListener() {
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animator animation) {
//                if (observer != null) {
//                    observer.onClose();
//                }
//            }
//        });
        animator.setDuration(1000);
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }
}
