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

    private val items = listOf("Other Main", "Other Kotlin", "Text View", "Text View List", "List View Test")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        tv_test.setText(R.string.app_name)
        listAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
    }

    override fun onListItemClick(l: ListView?, v: View?, position: Int, id: Long) {
        when (position) {
            0 -> {
//                startActivity(Intent(this, MainActivity::class.java))
                ARouter.getInstance().build("/test/mainActivity").navigation(this)
            }
            1 -> {
                startActivity(Intent(this, TestActivity::class.java))
            }
            2 -> {
//                startActivity(Intent(this, TextViewActivity::class.java))
                ARouter.getInstance().build("/text/textViewActivity").navigation(this)
            }
            3 -> {
                startActivity(Intent(this, com.maintel.customview.textview.ListActivity::class.java))
            }
            4 -> {
                val intent = Intent(Intent(this, ListViewTestActivity::class.java))
                intent.putExtra("222", "adadadd")
                intent.putExtra("111", "1")
                startActivity(intent)
            }
        }
    }
}