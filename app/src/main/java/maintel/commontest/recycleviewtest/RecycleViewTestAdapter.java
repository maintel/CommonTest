package maintel.commontest.recycleviewtest;

import android.content.Context;

import java.util.List;

import maintel.commontest.R;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/11/3 15:02
 * 备注：
 */
public class RecycleViewTestAdapter<String> extends RecyclerViewBaseAdapter<String> {

    public RecycleViewTestAdapter(List<String> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_recycle;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int poi) {
        holder.setTextView(R.id.tv_test, list.get(poi).toString());
    }
}
