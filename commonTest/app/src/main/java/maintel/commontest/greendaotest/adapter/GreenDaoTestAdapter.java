package maintel.commontest.greendaotest.adapter;

import android.content.Context;

import java.util.List;

import maintel.commontest.R;
import maintel.commontest.entity.User;
import maintel.commontest.list.MyViewHolder;
import maintel.commontest.list.RecyclerViewBaseAdapter;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2016/11/4 11:04
 * 备注：
 */
public class GreenDaoTestAdapter extends RecyclerViewBaseAdapter<User> {

    public GreenDaoTestAdapter(List<User> list, Context ctx) {
        super(list, ctx);
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        return R.layout.item_greendao;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int poi, int viewType) {
        User user = list.get(poi);
        holder.setTextView(R.id.tv_user_Name, user.getName());
        holder.setTextView(R.id.tv_user_id, user.getId() + "");
        holder.setTextView(R.id.tv_user_age, user.getAge());
    }
}
