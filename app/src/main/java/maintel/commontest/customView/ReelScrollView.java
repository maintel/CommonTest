package maintel.commontest.customView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;

import maintel.commontest.R;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/12/21 13:35
 * 备注：
 */
public class ReelScrollView extends FrameLayout {

    Paint mClearPaint;
    Bitmap reel;
    // 控制卷轴的宽高
    private float mReelWidth, mReelHeight;
    //卷轴绘制到画板的区域
    private RectF dstReel = new RectF();
    //卷轴图片源,即reel的矩形区域
    private Rect srcReel;
    private int mWidth;
    private int mHeight;
    private float mProgress; //像素

    public ReelScrollView(Context context) {
        super(context);
        init();
    }

    public ReelScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReelScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mClearPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mClearPaint.setColor(Color.BLUE);
        //画笔的混合模式,为清除像素
        mClearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));

        reel = BitmapFactory.decodeResource(getResources(), R.drawable.ic_camera_black_48dp);
        srcReel = new Rect(0, 0, reel.getWidth(), reel.getHeight());
        Log.d("px", "srcReel-->" + srcReel.toString());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mClearRect.set(mProgress, 0, mWidth, getMeasuredHeight());

        // 设置卷轴的宽高
        if (mReelWidth == 0) {
            // 按图片的实际宽高
            // mReelWidth = reel.getWidth();
            // mReelHeight = reel.getHeight();

            // 或者应该指定按控件的百分比,而不是完全按卷轴图片的宽高来
            mReelHeight = mHeight / 10;
            mReelWidth = reel.getWidth() * mReelHeight / reel.getHeight();
        }
        //设置卷轴绘画到的区域,应位于当前展开进度的正中心
        /**
         * t(float left,
         float top,
         float right,
         float bottom)
         */
//        dstReel.set(mProgress - mReelWidth / 2,
//                mHeight / 2 - mReelHeight / 2,
//                mReelWidth / 2 + mProgress,
//                mHeight / 2 + mReelHeight / 2);

        dstReel.set(mWidth / 2 - mReelWidth / 2,
                mProgress - mReelHeight / 2,
                mWidth / 2 + mReelWidth / 2,
                mProgress + mReelHeight / 2);
        Log.d("px", "dstReel-->" + dstReel.toString());
    }

    private RectF rect = new RectF(), mClearRect = new RectF();

    @Override
    protected void dispatchDraw(Canvas canvas) {
        rect.set(canvas.getClipBounds());
        // canvas.drawColor(0x00ffffff);

        int saveCount = canvas.saveLayer(rect, null, Canvas.ALL_SAVE_FLAG);//这里null在xml预览报错,要不报错传个全局new paint吧.
        super.dispatchDraw(canvas);

        canvas.drawRect(mClearRect, mClearPaint);
        canvas.restoreToCount(saveCount);

        if (showReel) {
            canvas.drawBitmap(reel, srcReel, dstReel, null);
        }
    }

    // 启动画卷模式
    private boolean startDrawProgress = false;

    // 从0到完全展开的时间
    private long completelyOpenTime = 2000;

    //是否显示引导卷轴
    private boolean showReel = true;

    public void setProgress(float progress) {
        this.mProgress = progress;
        mClearRect.set(progress, 0, mWidth, mHeight);
        invalidate();
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent e) {
        switch (e.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                Log.d("px", "ACTION_DOWN");
                // 点到卷轴,则启动画卷模式
//                if (startDrawProgress = checkEventAtReel(e)) {
//                    mProgress = e.getX();
//                    mClearRect.set(mProgress, 0, mWidth, mHeight);
////                    mClearRect.set(mProgress, 0, mWidth, mHeight);
//                    dstReel.left = mProgress - mReelWidth / 2;
//                    dstReel.right = mProgress + mReelWidth / 2;
//
//                    if (observer != null) {
//                        observer.onScroll(mProgress);
//                    }
//                    invalidate();
//                }
//                // 点击到进度外看不见的地方,拒绝该事件,也不向下传递事件
//                else if (e.getX() > mProgress) {
//                    return false;
//                }

                if (startDrawProgress = checkEventAtReel(e)) {
                    mProgress = e.getY();
                    mClearRect.set(0, mProgress, mWidth, mHeight);
//                    mClearRect.set(mProgress, 0, mWidth, mHeight);
                    dstReel.top = mProgress - mReelHeight / 2;
                    dstReel.bottom = mProgress + mReelHeight / 2;

                    if (observer != null) {
                        observer.onScroll(mProgress);
                    }
                    invalidate();
                }
                // 点击到进度外看不见的地方,拒绝该事件,也不向下传递事件
                else if (e.getX() > mProgress) {
                    return false;
                }
                break;
            case MotionEvent.ACTION_MOVE:
//                Log.d("px", "ACTION_MOVE");
//                if (startDrawProgress) {
//                    mProgress = e.getX();
//                    mClearRect.set(mProgress, 0, mWidth, mHeight);
//                    dstReel.left = mProgress - mReelWidth / 2;
//                    dstReel.right = mProgress + mReelWidth / 2;
//
//                    if (observer != null) {
//                        observer.onScroll(mProgress);
//                    }
//                    invalidate();
//                }

                Log.d("px", "ACTION_MOVE");
                if (startDrawProgress) {
                    mProgress = e.getY();
                    mClearRect.set(0,  mProgress, mWidth, mHeight);
                    dstReel.top = mProgress - mReelHeight / 2;
                    dstReel.bottom = mProgress + mReelHeight / 2;

                    if (observer != null) {
                        observer.onScroll(mProgress);
                    }
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (startDrawProgress) {
                    if (mProgress > mWidth / 2) {
                        // 大于一半自动全部展开
                        openFromCurrentPosition();
                    } else {
                        // 小于一半自动全部收回
                        closeFromCurrentPosition();
                    }
                }

//                if (startDrawProgress) {
//                    if (mProgress > mWidth / 2) {
//                        // 大于一半自动全部展开
//                        openFromCurrentPosition();
//                    } else {
//                        // 小于一半自动全部收回
//                        closeFromCurrentPosition();
//                    }
//                }
                break;
            default:
                break;
        }
        super.dispatchTouchEvent(e);
        return true;
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
            mClearRect.set(0, mProgress = v, mWidth, mHeight);
            dstReel.top = mProgress - mReelHeight / 2;
            dstReel.bottom = mProgress + mReelHeight / 2;
            if (observer != null) {
                observer.onScroll(mProgress);
            }
            invalidate();
        }
    };

    // 大于一半自动全部展开
    private void openFromCurrentPosition() {
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(mProgress, mWidth);
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
        animator.setDuration((long) ((mWidth - mProgress) / mWidth * completelyOpenTime));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }

    // 小于一半自动全部收回
    private void closeFromCurrentPosition() {
        if (animator != null) {
            animator.cancel();
        }
        animator = ValueAnimator.ofFloat(mProgress, 0);
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
        animator.setDuration((long) (mProgress / mWidth * completelyOpenTime));
        animator.setInterpolator(new DecelerateInterpolator());
        animator.start();
    }


    public boolean isShowReel() {
        return showReel;
    }

    public void setShowReel(boolean showReel) {
        this.showReel = showReel;
    }

    /**
     * 检查事件是否在可点击的卷轴上
     *
     * @param e
     * @return
     */
    private boolean checkEventAtReel(MotionEvent e) {
        // 卷轴不可见,则非
        if (!showReel) {
            return false;
        }
        if (e.getX() < dstReel.left) {
            return false;
        }
        if (e.getX() > dstReel.right) {
            return false;
        }
        if (e.getY() < dstReel.top) {
            return false;
        }
        if (e.getY() > dstReel.bottom) {
            return false;
        }
        return true;
    }

    /**
     * 观察者,监控展开与收缩的过程
     *
     * @author user
     */
    public interface Observer {
        // 完全展开
        void onOpen();

        // 完全关闭
        void onClose();

        // 滚动中
        void onScroll(float dx);
    }

    // 观察者,监听view的展开和收拢过程
    private Observer observer;

    public Observer getObserver() {
        return observer;
    }

    public void setObserver(Observer observer) {
        this.observer = observer;
    }
}
