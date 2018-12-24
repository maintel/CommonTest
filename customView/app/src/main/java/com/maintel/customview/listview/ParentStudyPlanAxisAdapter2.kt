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
import com.maintel.customview.utils.DeviceUtils

/**
 * 学习规划时间轴
 * @author jieyu.chen
 * @date 2018/10/22
 */
class ParentStudyPlanAxisAdapter2(var dataList: List<ParentStudyPlanAxisBean>, val context: Context) : RecyclerView.Adapter<ParentStudyPlanAxisAdapter2.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ParentStudyPlanAxisAdapter2.ViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.item_study_plan_axis2, parent, false)
        return ParentStudyPlanAxisAdapter2.ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {

        holder?.let {
            if (position != 0) {
                holder.itemView.post {
                    val layoutParams = holder.itemView.layoutParams as RecyclerView.LayoutParams
                    layoutParams.leftMargin = 0
                    holder.itemView.layoutParams = layoutParams
                }
            }
            holder.ll_mission_card.removeAllViews()
            println("111::${holder.ll_mission_card.width}")
            for (i in 1..dataList[position].i) {
                holder.ll_mission_card.addView(ParentStudyPlanAxisCardView(context))
//                if (position == dataList.size - 1 && i == dataList[position].i) {
//                    println("最后一个。。。。")
                //这里需要增加最大宽度
                // 剩下的就是判断 和计算宽度的问题了
//                    holder.ll_mission_card.post {
//                        val xy: IntArray = IntArray(2)
//                        holder.ll_mission_card.getLocationOnScreen(xy)
//                        println(xy[0])
//                        if (xy[0] + holder.ll_mission_card.width < DeviceUtils.getDeviceWidth()) {
//                            val layoutParams = holder.ll_mission_card.layoutParams
//                            layoutParams.width = DeviceUtils.getDeviceWidth() - xy[0]
//                            holder.ll_mission_card.layoutParams = layoutParams
//                        }
//
//                    }
//                }



            }


        }

    }


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val ll_mission_card = view.findViewById<LinearLayout>(R.id.ll_mission_card)
    }

}




