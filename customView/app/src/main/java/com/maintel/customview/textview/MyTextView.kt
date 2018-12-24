package com.maintel.customview.textview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.TextView
import android.text.SpannableString


/**
 *
 * @author jieyu.chen
 * @date 2018/9/14
 */
class MyTextView : TextView {

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {

    }

    private var lineColor: Int = 0
    private var paint: Paint = Paint()
    private val effects = DashPathEffect(floatArrayOf(5f, 5f, 5f, 5f), 1f)
    val path = Path()

    private var lineList = mutableListOf<Map<Int, List<Float>>>()


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        for (item in lineList) {
            paint.style = Paint.Style.STROKE
            paint.color = Color.RED
            // 移动到开始位置的左下角

            path.moveTo(item[0]?.get(0)!!, item[0]?.get(3)!!)
            path.lineTo(item[1]?.get(0)!!, item[1]?.get(3)!!)

            paint.pathEffect = effects
            canvas?.drawPath(path, paint)
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        println("onDetachedFromWindow")
    }

    /**
     * 计算坐标
     * 返回一个list依次为 左 上 右 下
     */
    private fun measureXY(poi: Int): List<Float> {
        val tx = mutableListOf<Float>(0f, 0f, 0f, 0f)
        val layout = layout
        val bound = Rect()
        val line = layout.getLineForOffset(poi)
        layout.getLineBounds(line, bound)
        tx[0] = layout.getPrimaryHorizontal(poi) + paddingLeft
        tx[1] = bound.top.toFloat() + paddingTop
        tx[2] = layout.getSecondaryHorizontal(poi) + paddingLeft
        tx[3] = bound.bottom.toFloat() + paddingTop
        return tx
    }


    fun setLine(s: Int, e: Int) {
        post {
            if (s > text.toString().length) {
                return@post
            }
            if (e < 0) {
                return@post
            }
            val start = if (s < 0) 0 else s
            val end = if (e > text.toString().length) text.toString().length else e
            val startXY = measureXY(start)
            val endXY = measureXY(end)
            // 处理折行的问题
            if (startXY[1] == endXY[1]) {
                lineList.add(hashMapOf(0 to startXY, 1 to endXY))
            } else {
                // 如果不在同一行 需要判断跨过了几行
                val lineStart = layout.getLineForOffset(start)
                val lineEnd = layout.getLineForOffset(end)
                for (i in lineStart..lineEnd) {
                    println("行数：：$i")
                    println("第${i}行最后一个字符poi=${layout.getLineEnd(i)}值${text.toString()[layout.getLineEnd(i) - 1]}")
                    println("第${i}行第一个字符poi=${layout.getLineStart(i)}值${text.toString()[layout.getLineStart(i)]}")
                    println("第${i}行宽度${layout.getLineMax(i)}")
                    when (i) {
                        lineStart -> {
                            //开始坐标为 startXY 结束坐标为 最右边
//
                            val endXY = listOf<Float>(layout.getLineMax(i) + paddingLeft, startXY[0],
                                    layout.getLineMax(i) + paddingLeft, startXY[3])
                            lineList.add(hashMapOf(0 to startXY, 1 to endXY))
                        }
                        lineEnd -> {
                            val startXY = listOf<Float>(paddingLeft.toFloat(), endXY[0], paddingLeft.toFloat(), endXY[3])
                            lineList.add(hashMapOf(0 to startXY, 1 to endXY))
                        }
                        else -> {
                            val bound = Rect()
                            layout.getLineBounds(i, bound)
                            val startXY = listOf<Float>(paddingLeft.toFloat(), bound.top.toFloat() + paddingTop, paddingLeft.toFloat(), bound.bottom.toFloat() + paddingTop)
                            val endXY = listOf<Float>(layout.getLineMax(i) + paddingLeft, bound.top.toFloat() + paddingTop, layout.getLineMax(i) + paddingLeft, bound.bottom.toFloat() + paddingTop)
                            lineList.add(hashMapOf(0 to measureXY(layout.getLineStart(i)), 1 to endXY))
                        }
                    }
                }
            }
            invalidate()
        }
    }

    fun setLineColor() {

    }

    fun setLineStyle() {

    }

    fun setText(sp: SpannableString) {
        super.setText(sp)
    }


}