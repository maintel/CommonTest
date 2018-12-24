package com.maintel.customview.listview

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.LinearLayout
import com.maintel.customview.R
import kotlinx.android.synthetic.main.list_view_test_activity.*

/**
 *
 * @author jieyu.chen
 * @date 2018/10/17
 */
class ListViewTestActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.list_view_test_activity)
        val hLayoutManager = LinearLayoutManager(this)
        hLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mRecyclerHor.layoutManager = hLayoutManager
        mRecyclerHor.adapter = Adapter(mutableListOf<String>("a", "b"), this)
        (parent_view_study_plan as ParentStudyPlanView).refreshView()
        (parent_view_study_plan2 as ParentStudyPlanView2).refreshView()
    }

}