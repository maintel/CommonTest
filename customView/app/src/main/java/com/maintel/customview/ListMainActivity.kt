package com.maintel.customview

import android.app.ListActivity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.maintel.customview.listview.ListViewTestActivity
import com.maintel.customview.textview.TextViewActivity
import kotlinx.android.synthetic.main.main_activity.*

/**
 *
 * @author jieyu.chen
 * @date 2018/9/13
 */
@Route(path = "/main/main")
class ListMainActivity : ListActivity() {

    private val items = listOf("Other Main", "Other Kotlin", "Text View", "Text View List", "List View Test", "Ring Progress Activity")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        tv_test.setText(R.string.app_name)
        listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        when (position) {
            0 -> {
                navigationActivity("/test/mainActivity")
            }
            1 -> {
                navigationActivity("/test/testActivity")
            }
            2 -> {
                navigationActivity("/test/ringProgress")
            }
            3 -> {
                navigationActivity("/test/textListActivity")
            }
            4 -> {
                val intent = Intent(Intent(this, ListViewTestActivity::class.java))
                intent.putExtra("222", "adadadd")
                intent.putExtra("111", "1")
                startActivity(intent)
            }
            5 -> {
                navigationActivity("/test/ringProgress")
            }
        }
    }


    private fun navigationActivity(path: String) {
        ARouter.getInstance().build(path).navigation(this)

    }
}