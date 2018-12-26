package maintel.commontest.list;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

/**
 * 说明：自定义的viewHolder
 * 作者：mainTel
 * 时间：2016/6/30 13:55
 * 备注：
 */
public class RVHolder extends RecyclerView.ViewHolder {

    MyViewHolder viewHolder;

    public RVHolder(View itemView) {
        super(itemView);
        viewHolder = MyViewHolder.getHolder(itemView);
    }

    public MyViewHolder getViewHolder() {
        return viewHolder;
    }
}
