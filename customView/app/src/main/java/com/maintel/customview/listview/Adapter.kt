package com.maintel.customview.listview

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.maintel.customview.R

/**
 *
 * @author jieyu.chen
 * @date 2018/10/17
 */
class Adapter(var data: MutableList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_list_view_test, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.let {
            holder.cardList.removeAllViews()
            println("111::${holder.cardList.width}")
            for (i in 1..data[position].length) {
                val childView = TextView(context)
                childView.text = "child$i::${data[position]}"
                holder.cardList.addView(childView)
            }

            if (position == 0) {
                holder.lineLeft.visibility = View.INVISIBLE
                holder.lineRight.visibility = View.VISIBLE
            } else if (position == data.size - 1) {
                holder.lineLeft.visibility = View.VISIBLE
                holder.lineRight.visibility = View.INVISIBLE
            } else {
                holder.lineLeft.visibility = View.VISIBLE
                holder.lineRight.visibility = View.VISIBLE
            }
        }
    }


}


class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    val cardList = view.findViewById<LinearLayout>(R.id.cardList)
    val lineLeft = view.findViewById<TextView>(R.id.lineLeft)
    val lineRight = view.findViewById<TextView>(R.id.lineRight)

}