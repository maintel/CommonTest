package maintel.commontest.list;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

/**
 * 说明：加载更多RecyclerView滑动监听
 * 作者：mainTel
 * 时间：2017/5/17 11:05
 * 备注：
 */

public abstract class LoadMoreScrollListener extends RecyclerView.OnScrollListener {

    private int previousTotal;
    private boolean isLoading = true;
    private LinearLayoutManager lm;
    private StaggeredGridLayoutManager sm;
    private int[] lastPositions;
    private int totalItemCount;
    private int lastVisibleItemPosition;

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
        if (recyclerView.getLayoutManager() instanceof LinearLayoutManager)
            lm = (LinearLayoutManager) recyclerView.getLayoutManager();
        else if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
            sm = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
            lastPositions = sm.findLastVisibleItemPositions(null);
        }

        int visibleItemCount = recyclerView.getChildCount();
        if (lm != null) {
            totalItemCount = lm.getItemCount();
            lastVisibleItemPosition = lm.findLastVisibleItemPosition();
        } else if (sm != null) {
            totalItemCount = sm.getItemCount();
            lastVisibleItemPosition = lastPositions[0];
        }

        if (isLoading) {
            if (totalItemCount > previousTotal) {//加载更多结束
                isLoading = false;
                previousTotal = totalItemCount;
            } else if (totalItemCount < previousTotal) {//用户刷新结束
                previousTotal = totalItemCount;
                isLoading = false;
            } else {//有可能是在第一页刷新也可能是加载完毕

            }
        }
        if (!isLoading && visibleItemCount > 0 && totalItemCount - 1 == lastVisibleItemPosition && recyclerView.getScrollState() == RecyclerView.SCROLL_STATE_IDLE) {
            loadMore();
        }
    }

    public abstract void loadMore();
}
