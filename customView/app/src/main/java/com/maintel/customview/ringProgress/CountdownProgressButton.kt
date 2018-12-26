package com.maintel.customview.ringProgress

import android.animation.Animator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.accessibility.AccessibilityNodeInfo
import com.maintel.customview.listener.IOnTimeOutListener

/**
 * <p>name:</p>
 * <p>describe:</p>
 * @author Maintel
 * @time 2018/6/28 0:17
 */
class CountdownProgressButton : View {

    private var mPaint: Paint = Paint()
    private lateinit var rectf: RectF
    private var outerRadius = -1f // 外圆的半径
    private var innerRadius = 0f // 内圆半径
    private var rectWidth = 0f // 停止按钮的宽度
    private var outerWidth = 3f
    private var progressWidth = 20f
    private lateinit var progressRectf: RectF
    private var centerX = 0f
    private var centerY = 0f
    private var isRecording = false
    private var progress = 1f
    private var timeOutListener: IOnTimeOutListener? = null

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        mPaint.isAntiAlias = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var width = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        var height = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode == MeasureSpec.AT_MOST) {
            width = 150
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height == 150
        }
        val size = if (width > height) height else width
        setMeasuredDimension(size, size)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (outerRadius == -1f) {
            outerRadius = (width - paddingLeft - paddingRight - outerWidth).toFloat() / 2
            innerRadius = (outerRadius * 0.8).toFloat()
            rectWidth = outerRadius
            centerX = (width / 2).toFloat()
            centerY = (height / 2).toFloat()
            rectf = RectF(centerX - outerRadius / 2, centerY - outerRadius / 2, centerX + outerRadius / 2, centerY + outerRadius / 2)
            progressRectf = RectF(centerX - outerRadius + outerWidth / 2 + progressWidth / 2,
                    centerY - outerRadius + outerWidth / 2 + progressWidth / 2,
                    centerX + outerRadius - progressWidth / 2 - outerWidth / 2,
                    centerY + outerRadius - progressWidth / 2 - outerWidth / 2)
        }
        // 先画一个外环的圆
        mPaint.color = Color.GRAY
        mPaint.strokeWidth = outerWidth
        mPaint.style = Paint.Style.STROKE
        canvas?.drawCircle(centerX, centerY, outerRadius, mPaint)
        if (isRecording) {
            // 开始状态下画内部的矩形
            mPaint.color = Color.RED
            mPaint.style = Paint.Style.FILL
            canvas?.drawRoundRect(rectf, 15f, 15f, mPaint)
            // 开始状态下画倒计时进度条
            mPaint.color = Color.BLUE
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeWidth = progressWidth
            mPaint.strokeCap = Paint.Cap.ROUND
            // 正计时
//            canvas?.drawArc(progressRectf, 0f, 360 * progress, false, mPaint)
            // 倒计时
            canvas?.drawArc(progressRectf, 0f, 360 - 360 * progress, false, mPaint)
        } else {
            //未开始状态下画内圆
            mPaint.color = Color.RED
            mPaint.style = Paint.Style.FILL
            canvas?.drawCircle(centerX, centerY, innerRadius, mPaint)
        }
    }

    fun isRecording(): Boolean {
        return isRecording
    }

    fun stop() {
        if (isRecording) {
            isRecording = false
            invalidate()
        }
    }


    fun start() {
        if (isRecording) {
            return
        } else {
            isRecording = true
            invalidate()
            startAnimation()
        }
    }

    fun setOnTimeOutListener(e: IOnTimeOutListener) {
        timeOutListener = e
    }

    override fun onWindowVisibilityChanged(visibility: Int) {
        super.onWindowVisibilityChanged(visibility)
        Log.e("CountdownProgressButton","visibility::$visibility")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        Log.e("CountdownProgressButton","onAttachedToWindow")

    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        Log.e("CountdownProgressButton","onDetachedFromWindow")
    }

    private fun startAnimation() {
        val valueAnimation = ValueAnimator.ofInt(0, 10000)
        valueAnimation.duration = 15000
        valueAnimation.repeatCount = 0
        valueAnimation.addUpdateListener {
            progress = (it.animatedValue as Int).toFloat() / 10000
            println(progress)
            invalidate()
        }
        valueAnimation.addListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {

            }

            override fun onAnimationEnd(p0: Animator?) {
                timeOutListener?.onTimeOut()
            }

            override fun onAnimationCancel(p0: Animator?) {

            }

            override fun onAnimationStart(p0: Animator?) {

            }
        })
        valueAnimation.start()
    }
}