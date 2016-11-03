package maintel.commontest.recycleviewtest;

import android.support.v7.widget.RecyclerView;
import android.view.View;

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
