package com.maintel.customview.textview

import android.graphics.Color
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import com.maintel.customview.R

/**
 *
 * @author jieyu.chen
 * @date 2018/10/13
 */
class MyClickableSpan(start: Int, end: Int) : ClickableSpan() {

    var mStart = start
    var mEnd = end

    lateinit var onClickListener: (v: View, str: String, x: Int, y: Int) -> Unit
    var contentStr = ""
    var x = 0
    var y = 0

    constructor(start: Int, end: Int, onClickListener: (v: View, str: String, x: Int, y: Int) -> Unit) : this(start, end) {
        this.onClickListener = onClickListener
    }

    override fun onClick(p0: View?) {
        if (!::onClickListener.isInitialized) {
            return
        }
        p0?.let {
            onClickListener(p0, contentStr, x, y)
        }
    }

    override fun updateDrawState(ds: TextPaint?) {
        ds?.color = Color.RED
    }
}