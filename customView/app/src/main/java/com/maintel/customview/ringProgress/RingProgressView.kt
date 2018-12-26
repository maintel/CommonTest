package com.maintel.customview.ringProgress

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.graphics.Paint
import com.maintel.customview.R


/**
 *
 * @author jieyu.chen
 * @date 2018/12/24
 */
class RingProgressView : View {
    private var bgRect: RectF = RectF() // 背景区域
    private var allArcPaint = Paint() // 背景圆弧画笔
    private var proPaint = Paint() //进度圆弧画笔
    private var proPaint2 = Paint() //覆盖进度圆弧的画笔，用来遮挡开始位置的圆角

    private var bgColor = Color.TRANSPARENT // 默认背景透明
    private var bgPaintColor = Color.parseColor("#f2f2f2") // 默认进度条背景色
    private var proPaintColor = Color.parseColor("#2c99ff") // 默认进度条颜色
    private var paintWidth = 10f // 默认画笔宽度
    private var startAngle = 180f // 开始位置
    private var sweepAngle = 180f // 圆弧弧度
    private var progress = 0f //进度

    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

        initPaint(getContext().obtainStyledAttributes(attrs, R.styleable.RingProgressView, defStyleAttr, 0))
    }

    /**
     * 初始化画笔
     */
    private fun initPaint(typedArray: TypedArray) {
        paintWidth = typedArray.getDimension(R.styleable.RingProgressView_paintWidth, 20f)
        proPaintColor = typedArray.getColor(R.styleable.RingProgressView_proPaintColor, proPaintColor)
        bgColor = typedArray.getColor(R.styleable.RingProgressView_bgColor, bgColor)

        allArcPaint.isAntiAlias = true
        allArcPaint.style = Paint.Style.STROKE
        allArcPaint.strokeWidth = paintWidth
        allArcPaint.color = typedArray.getColor(R.styleable.RingProgressView_bgPaintColor, bgPaintColor)
        allArcPaint.strokeCap = Paint.Cap.SQUARE
        proPaint.isAntiAlias = true
        proPaint.style = Paint.Style.STROKE
        proPaint.strokeWidth = paintWidth
        proPaint.color = proPaintColor
        proPaint.strokeCap = Paint.Cap.ROUND
        proPaint2.isAntiAlias = true
        proPaint2.style = Paint.Style.STROKE
        proPaint2.strokeWidth = paintWidth
        proPaint2.color = proPaintColor
        proPaint2.strokeCap = Paint.Cap.SQUARE
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
            height = 150
        }
        setMeasuredDimension(width, height)
    }

    /**
     * 初始化圆弧区域的大小
     */
    private fun initRect() {
        // 求得中心点
        // 因为是半圆 所以把中心点定位在最下面中间位置
        val x = width / 2 + paddingRight - paddingLeft
        val y = height - paddingBottom
        val width = width - paddingLeft - paddingRight - allArcPaint.strokeWidth
        val height = height - paddingBottom - paddingTop - allArcPaint.strokeWidth / 2

        var radius = height // 半径
        if (width / 2 < height) {
            radius = width / 2
        }

        bgRect.left = (x - radius).toFloat()
        bgRect.top = (y - radius).toFloat()
        bgRect.right = (x + radius).toFloat()
        bgRect.bottom = (y + radius).toFloat()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initRect()
    }


    override fun onDraw(canvas: Canvas?) {
        if (canvas == null) {
            return
        }
        canvas.drawColor(bgColor)
        canvas.drawArc(bgRect, startAngle, sweepAngle, false, allArcPaint)
        canvas.drawArc(bgRect, startAngle, progress, false, proPaint)
        if (progress > 1) {
            canvas.drawArc(bgRect, startAngle, 1f, false, proPaint2)
        }
    }

    fun setProgress(pro: Float) {
        this.progress = pro
        invalidate()
    }
}