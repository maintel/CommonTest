package com.maintel.customview.textview

import android.app.ListActivity
import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.maintel.customview.R

/**
 *
 * @author jieyu.chen
 * @date 2018/9/13
 */
class ListActivity : ListActivity() {

    private val items = listOf("Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ",
            "Other MainOther MainOther MainOther MainOther MainOther MainOther MainOther MainOther Main",
            "Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin Other Kotlin ",
            "Text View Text View Text View Text View Text View Text View Text View Text View Text View Text View ")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        listAdapter = test(this, R.layout.item_text_view, items)
    }


    class test(context: Context, val layout: Int, list: List<String>) : ArrayAdapter<String>(context, layout, list) {

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val holder: Holder
            var rootView: View
            if (convertView == null) {
                rootView = LayoutInflater.from(context).inflate(layout, null)
                holder = Holder(rootView)
                rootView.tag = holder
            } else {
                rootView = convertView
                holder = rootView.tag as Holder
            }
            if (position % 2 == 0) {
                val lines = listOf<UnderLineOptions>(
                        UnderLineOptions(4, 6))
                holder.textView.setLines(lines)
            } else {
                val lines = listOf<UnderLineOptions>(
                        UnderLineOptions(21, 60))
                holder.textView.setLines(lines)
            }

            return rootView
        }

    }

    class Holder(view: View) {
        val textView: MyTextViewJ = view.findViewById(android.R.id.text1)
    }

}