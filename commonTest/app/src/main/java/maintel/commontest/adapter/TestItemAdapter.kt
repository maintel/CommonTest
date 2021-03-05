package maintel.commontest.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import maintel.commontest.R

/**
 *
 * @author jieyu.chen
 * @date 2021/3/5
 */
class TestItemAdapter(val dataList: MutableList<String>, val context: Context) : RecyclerView.Adapter<BaseRecyclerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseRecyclerViewHolder {
        return BaseRecyclerViewHolder(LayoutInflater.from(context).inflate(R.layout.item_test, parent, false))
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: BaseRecyclerViewHolder, position: Int) {
        if (position % 2 == 0) {
            holder.getItemView().setBackgroundColor(Color.LTGRAY)
        } else {
            holder.getItemView().setBackgroundColor(Color.GRAY)
        }
        holder.setText(R.id.text, dataList[position])
    }
}