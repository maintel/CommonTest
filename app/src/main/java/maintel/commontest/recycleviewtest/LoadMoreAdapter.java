package maintel.commontest.recycleviewtest;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import java.util.List;

import maintel.commontest.R;

/**
 * 说明：
 * 作者：mainTel
 * 时间：2017/5/17 11:02
 * 备注：
 */

public class LoadMoreAdapter extends RecyclerViewBaseAdapter<String> {

    public static final int ITEM_TYPE_LOAD_FAILED_VIEW = Integer.MAX_VALUE - 1; //加载失败
    public static final int ITEM_TYPE_NO_MORE_VIEW = Integer.MAX_VALUE - 2;  // 没有更多
    public static final int ITEM_TYPE_LOAD_MORE_VIEW = Integer.MAX_VALUE - 3; // 正在加载
    public static final int ITEM_TYPE_NO_VIEW = Integer.MAX_VALUE - 4;//不展示

    private int mCurrentItemType = ITEM_TYPE_NO_MORE_VIEW;
    private LoadMoreScrollListener mLoadMoreScrollListener;

    private boolean isLoadError = false;//标记是否加载出错
    private boolean isHaveStatesView = true;

    public LoadMoreAdapter(List<String> list, Context ctx) {
        super(list, ctx);
        mLoadMoreScrollListener = new LoadMoreScrollListener() {
            @Override
            public void loadMore() {
                if (loadMoreListener != null && isHaveStatesView) {
                    if (!isLoadError) {
                        showLoadMore();
                        loadMoreListener.loadMore();
                    }
                }
            }
        };
    }

    @Override
    public int onCreateViewLayoutID(int viewType) {
        if (viewType == ITEM_TYPE_LOAD_MORE_VIEW) {
            return R.layout.item_recycle_load;
        } else if (viewType == ITEM_TYPE_NO_VIEW) {
            return R.layout.item_recycle;
        } else if (viewType == ITEM_TYPE_LOAD_FAILED_VIEW) {
            return R.layout.item_recycle_error;
        } else if (viewType == ITEM_TYPE_NO_MORE_VIEW) {
            return R.layout.item_recycle_nomore;
        }
        return R.layout.item_recycle;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int poi, int viewType) {
        if (viewType == ITEM_TYPE_LOAD_FAILED_VIEW) {
            holder.getConvertView().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d("LoadMoreAdapter", "loadError");
                    loadMoreListener.loadError();
                }
            });
            return;
        }
        if (!isFootType(viewType))
            holder.setTextView(R.id.tv_test, list.get(poi));
    }

    private boolean isFootType(int viewType) {
        if (viewType != 0) {
            return true;
        }
        return false;
    }

    public void showLoadMore() {
        mCurrentItemType = ITEM_TYPE_LOAD_MORE_VIEW;
        isLoadError = false;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadError() {
        mCurrentItemType = ITEM_TYPE_LOAD_FAILED_VIEW;
        isLoadError = true;
        isHaveStatesView = true;
        notifyItemChanged(getItemCount());
    }

    public void showLoadNoMore() {
        mCurrentItemType = ITEM_TYPE_NO_MORE_VIEW;
        isLoadError = false;
        isHaveStatesView = false;
        notifyItemChanged(getItemCount());
    }

    public void disableLoadMore() {
        mCurrentItemType = ITEM_TYPE_NO_VIEW;
        isHaveStatesView = false;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == getItemCount() - 1) {
            return mCurrentItemType;
        }
        return 0;
    }

    @Override
    public int getItemCount() {
        return list.size() + 1;
    }

    LoadMoreListener loadMoreListener;

    public void setLoadMoreListener(LoadMoreListener loadMoreListener) {
        this.loadMoreListener = loadMoreListener;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.addOnScrollListener(mLoadMoreScrollListener);
    }
}
