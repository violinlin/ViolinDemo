package com.violin.recyclerview.kit.nextpage;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;

/**
 * Created by wanghuilin on 2018/3/6.
 * <p>
 * email:violinlin@yeah.net
 */

public class NextPageControl {

    public static final String TAG = NextPageControl.class.getName();

    private boolean hasMore = true;//是否有更多数据
    private boolean isRequestNext;//是否正在加载下一页

    RecyclerView mRecyclerView;

    public void setUpWithRecyclerView(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            //用来标记是否正在向最后一个滑动
            boolean isSlidingToLast = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                /**
                 * 只在recyclerview滑动状态发生改变时调用，例如从静止到滑动，recyclerview的状态如下：
                 * @see RecyclerView.SCROLL_STATE_IDLE 静止状态
                 * @see RecyclerView.SCROLL_STATE_DRAGGING 滑动状态
                 * @see RecyclerView.SCROLL_STATE_SETTLING 惯性滑动状态
                 */
                if (!hasMore || isRequestNext) {
//                    如果正在加载下一页，或者已经没有更多数据则不触发监听
                    return;
                }
                if (RecyclerView.SCROLL_STATE_IDLE == newState) {
                    parseNextPage(isSlidingToLast);
                }

            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                /**
                 * 从recyclerview活动开始不断被调用
                 * dx>0(dy>0)表示向右(向下)滑动
                 * dx<0(dy<0)表示向左(向上)滑动
                 */
                if (dy > 0) {
                    isSlidingToLast = true;
                } else {
                    isSlidingToLast = false;
                }

            }
        });
    }

    private void parseNextPage(boolean isSlidingToLast) {
        RecyclerView.LayoutManager layoutManager = mRecyclerView.getLayoutManager();
        int lastVisibleItem = 0, totalItemCount = 0;
        totalItemCount = layoutManager.getItemCount();

        if (layoutManager instanceof LinearLayoutManager) {
            //LinearLayoutManager和GridLayoutManager是否滑到底部处理
            LinearLayoutManager manager = (LinearLayoutManager) layoutManager;

            lastVisibleItem = manager.findLastCompletelyVisibleItemPosition();//获取最后完全显示的view的位置

        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            StaggeredGridLayoutManager manager = (StaggeredGridLayoutManager) layoutManager;
            /**
             * 返回当前屏幕每列最后显示的view的位置
             */
            int[] posi = manager.findLastVisibleItemPositions(null);
            /**
             * 返回当前屏幕每列最后完全显示的view的位置信息（每个view完全显示）
             */
            int[] cposi = manager.findLastCompletelyVisibleItemPositions(null);

            for (int i = 0; i < posi.length; i++) {
//                如果当前屏幕每列存在显示不完全显示的view,不触发监听
                if (posi[i] != cposi[i]) {
                    return;
                }
                lastVisibleItem = posi[i] > lastVisibleItem ? posi[i] : lastVisibleItem;
            }

        }
        // 判断是否滚动到底部，并且是向下滚动
        if (lastVisibleItem == (totalItemCount - 1) && isSlidingToLast) {
            if (listener != null) {
                listener.requestNextPageData();
            }
        }


    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public void setRequestNext(boolean requestNext) {
        isRequestNext = requestNext;
    }

    public interface Listener {
        public void requestNextPageData();
    }

    private Listener listener;

    public void setListener(Listener listener) {
        this.listener = listener;
    }

}
