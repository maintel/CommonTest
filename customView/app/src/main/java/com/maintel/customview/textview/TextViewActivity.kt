package com.maintel.customview.textview

import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.SpannableString
import android.text.Spanned
import android.text.method.LinkMovementMethod
import android.view.Gravity
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.maintel.customview.R
import kotlinx.android.synthetic.main.activity_text_view.*

/**
 *
 * @author jieyu.chen
 * @date 2018/9/13
 */
@Route(path = "/text/textViewActivity")
class TextViewActivity : AppCompatActivity() {

    private val onClickListener: (v: View, str: String, x: Int, y: Int) -> Unit = { v, str, x, y ->
        println(str)
        showPopUp(v, str, x, y)
    }


    private fun showPopUp(v: View, content: String, x: Int, y: Int) {
        val layout = LinearLayout(this)
        layout.setBackgroundColor(Color.GRAY)
        val tv = TextView(this)
        tv.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        tv.text = content
        tv.setTextColor(Color.WHITE)
        layout.addView(tv)

        val popupWindow = PopupWindow(layout, 300, 120)

        popupWindow.isFocusable = true
        popupWindow.isOutsideTouchable = true
        popupWindow.setBackgroundDrawable(BitmapDrawable())

        val location = IntArray(2)
        v.getLocationOnScreen(location)

        popupWindow.showAtLocation(v, Gravity.NO_GRAVITY,
                location[0] + x - popupWindow.width / 2,
                location[1] - popupWindow.height + y)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_text_view)
        val spanableInfo = SpannableString("这是一个测试文本，点击我看看！")
        val span = MyClickableSpan(9, 11)
        println(span)
        span.onClickListener = onClickListener
        spanableInfo.setSpan(span, 9, 11, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        tv_test2.text = spanableInfo
        tv_test2.movementMethod = LinkMovementMethod.getInstance()
        tv_test2.setOnClickListener {
            println("setOnClickListener")
        }

        val lines = mutableListOf<UnderLineOptions>()
        val lineOptions = UnderLineOptions(span.mStart, span.mEnd, span)
        lines.add(lineOptions)
        tv_test2.setLines(lines)

        val spanableInfoLong = SpannableString(tv_test_long.text)
        val spanLong = MyClickableSpan(29, 32, onClickListener)
        spanableInfoLong.setSpan(spanLong, 29, 32, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        val lineOptionsLong = listOf<UnderLineOptions>(UnderLineOptions(3, 10, UnderLineOptions.Style.LINE_STYLE_STROKE),
                UnderLineOptions(12, 26, Color.BLUE),
                UnderLineOptions(29, 32, spanLong))
        tv_test_long.text = spanableInfoLong
        tv_test_long.movementMethod = LinkMovementMethod.getInstance()
        tv_test_long.setLines(lineOptionsLong)
    }

}