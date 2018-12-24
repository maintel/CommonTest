package com.maintel.customview.textview

import android.graphics.Color
import android.widget.TextView

/**
 * 下划线配置类
 * @author jieyu.chen
 * @date 2018/9/14
 */
const val LINE_STYLE_STROKE = 0 // 实线类型
const val LINE_STYLE_DOTTED = 1 // 虚线类型

class UnderLineOptions {

    enum class Style {
        LINE_STYLE_DOTTED, LINE_STYLE_STROKE
    }


    // 线宽
    var lineHeight = -1
    // 线类型
    var lineStyle = Style.LINE_STYLE_DOTTED
    // 颜色
    var lineColor = Color.RED
    var lineStart = 0
    var lineEnd = 0
    var contentStr: String = ""
    //存储下划线起始坐标的列表，0个偶数位表示开始，奇数位表示结束，坐标用一个数组来存储分别为左，上，右，下
    var linesXY: List<FloatArray>? = null

    var myClickableSpan: MyClickableSpan? = null

    constructor(lineStart: Int, lineEnd: Int, lineStyle: Style, lineColor: Int, myClickableSpan: MyClickableSpan?) {
        this.lineStyle = lineStyle
        this.lineColor = lineColor
        this.lineStart = lineStart
        this.lineEnd = lineEnd
        this.myClickableSpan = myClickableSpan
    }

    constructor(lineStart: Int, lineEnd: Int) : this(lineStart, lineEnd, Style.LINE_STYLE_DOTTED, Color.RED, null)

    constructor(lineStart: Int, lineEnd: Int, lineColor: Int) : this(lineStart, lineEnd, Style.LINE_STYLE_DOTTED, lineColor, null)
    constructor(lineStart: Int, lineEnd: Int, lineStyle: Style) : this(lineStart, lineEnd, lineStyle, Color.RED, null)
    constructor(lineStart: Int, lineEnd: Int, lineStyle: Style, lineColor: Int) : this(lineStart, lineEnd, lineStyle, lineColor, null)

    constructor(lineStart: Int, lineEnd: Int, myClickableSpan: MyClickableSpan?) : this(lineStart, lineEnd, Style.LINE_STYLE_DOTTED, Color.RED, myClickableSpan)


}