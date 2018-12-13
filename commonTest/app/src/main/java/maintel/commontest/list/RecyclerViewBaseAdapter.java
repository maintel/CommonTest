package maintel.commontest.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 说明：RecycleView适配器的基类
 * 作者：mainTel
 * 时间：2016/6/29 17:29
 * 备注：
 */
public abstract class RecyclerViewBaseAdapter<T> extends RecyclerView.Adapter<RVHolder> {

    public List<T> list;
    Context ctx;


    public RecyclerViewBaseAdapter(List<T> list, Context ctx) {
        this.list = list;
        this.ctx = ctx;
    }

    public void upDate(List<T> data) {
        this.list = data;
        notifyDataSetChanged();
    }

    public void upDateAdd(List<T> data) {
        this.list.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public RVHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(ctx).inflate(onCreateViewLayoutID(viewType), parent, false);
        return new RVHolder(view);
    }

    public abstract int onCreateViewLayoutID(int viewType);

    @Override
    public void onBindViewHolder(final RVHolder holder, final int position) {
        onBindViewHolder(holder.getViewHolder(), position,holder.getItemViewType());
        if (null != mOnItemClickListener) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(v, position);
                }
            });
        }
        if (null != mOnItemLongClickListener)
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemLongClickListener.onItemLongClick(v, position);
                    return true;
                }
            });
    }

    public abstract void onBindViewHolder(MyViewHolder holder, int poi,int viewType);

    @Override
    public int getItemCount() {
        return list.size();
    }

    public Object getItemByPosition(int poi) {
        return list.get(poi);
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int poi);
    }

    OnItemClickListener mOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener mOnItemClickListener) {
        this.mOnItemClickListener = mOnItemClickListener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View view, int poi);
    }

    OnItemLongClickListener mOnItemLongClickListener;

    public void setOnItemLongClickListener(OnItemLongClickListener mOnItemLongClickListener) {
        this.mOnItemLongClickListener = mOnItemLongClickListener;
    }
}
