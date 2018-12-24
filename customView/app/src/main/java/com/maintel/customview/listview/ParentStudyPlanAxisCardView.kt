package com.maintel.customview.listview

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import com.maintel.customview.R

/**
 *
 * @author jieyu.chen
 * @date 2018/10/22
 */
class ParentStudyPlanAxisCardView : FrameLayout {
    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context?) : this(context, null, 0)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        LayoutInflater.from(context).inflate(R.layout.parent_view_study_plan_axis_card, this)
    }


}