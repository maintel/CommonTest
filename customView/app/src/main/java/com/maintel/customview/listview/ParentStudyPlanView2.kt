package com.maintel.customview.listview

import android.content.Context
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.widget.RelativeLayout
import android.widget.TextView
import com.maintel.customview.R
import kotlinx.android.synthetic.main.parent_view_study_plan.view.*

/**
 * 学习规划
 * @version 0.1
 * @app-version 2.4.5
 * @author jieyu.chen
 * @date 2018/10/22
 */
class ParentStudyPlanView2(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : RelativeLayout(context, attrs, defStyleAttr) {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)

    override fun onFinishInflate() {
        super.onFinishInflate()
    }

    var mCurrentPosition = 0
    var mVieDistance = 0

    fun refreshView() {
        println("mVieDistance == ${mVieDistance}")
        val hLayoutManager = LinearLayoutManager(context)
        hLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        recy_study_plan.layoutManager = hLayoutManager
        recy_study_plan.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                println("dx::$dx     dy::$dy")
                println("scrollX::$scrollX")
                val viewNow = hLayoutManager.findViewByPosition(hLayoutManager.findFirstVisibleItemPosition())
                val title = viewNow.findViewById<TextView>(R.id.tv_time)
                val layout = title.layoutParams as RelativeLayout.LayoutParams
                val marginLeft = layout.leftMargin
                val view = hLayoutManager.findViewByPosition(hLayoutManager.findFirstVisibleItemPosition() + 1)
                if (view != null) {
                    val title2 = view.findViewById<TextView>(R.id.tv_time)
                    mVieDistance = title.width
                    if (view.left <= mVieDistance) {
                        title.x = (-(mVieDistance - view.left) - viewNow.x + 10).toFloat()
                        title2.x = 0f
                    } else {
                        title.x = -viewNow.x + 10
                    }
                } else {
                    title.x = -viewNow.x + 10
                }

                if (mCurrentPosition != hLayoutManager.findFirstVisibleItemPosition()) {
                    mCurrentPosition = hLayoutManager.findFirstVisibleItemPosition()
                }
                println(recy_study_plan.x)
            }
        })
        recy_study_plan.adapter = ParentStudyPlanAxisAdapter2(
                mutableListOf<ParentStudyPlanAxisBean>(ParentStudyPlanAxisBean(2), ParentStudyPlanAxisBean(3)), context)
//                mutableListOf<ParentStudyPlanAxisBean>(ParentStudyPlanAxisBean(2)), context)
//        recy_study_plan.smoothScrollBy(500, 0)

        post {
            println("recy_study_plan::${recy_study_plan.width}")
            println("recy_study_plan.measuredWidth::${recy_study_plan.measuredWidth}")
        }

//        mhs_scrollview.dispatchNestedScroll()
    }
}