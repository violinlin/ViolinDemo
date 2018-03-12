package com.violin.recyclerview.kit.rvextension;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by whl on 2017/8/8.
 * <p>
 * 为RecyclerView 添加Header和Footer的adapter
 */

public class HFRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_HEADER_VIEW = Integer.MIN_VALUE;
    private final int MAX_COUNT = 10;

    private final int TYPE_FOOTER_VIEW = TYPE_HEADER_VIEW + MAX_COUNT;

    private ArrayList<View> mHeaderViews = new ArrayList<>();
    private ArrayList<View> mFooterViews = new ArrayList<>();
    private RecyclerView.Adapter<RecyclerView.ViewHolder> mInnerAdapter;

    private RecyclerView.AdapterDataObserver mDataObserver = new RecyclerView.AdapterDataObserver() {
        @Override
        public void onChanged() {
            super.onChanged();
            notifyDataSetChanged();
        }

        @Override
        public void onItemRangeChanged(int positionStart, int itemCount) {
            super.onItemRangeChanged(positionStart, itemCount);
            notifyItemRangeChanged(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeInserted(int positionStart, int itemCount) {
            super.onItemRangeInserted(positionStart, itemCount);
            notifyItemRangeInserted(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeRemoved(int positionStart, int itemCount) {
            super.onItemRangeRemoved(positionStart, itemCount);
            notifyItemRangeRemoved(positionStart + getHeaderViewsCount(), itemCount);
        }

        @Override
        public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
            super.onItemRangeMoved(fromPosition, toPosition, itemCount);
            int headerViewsCountCount = getHeaderViewsCount();
            notifyItemRangeChanged(fromPosition + headerViewsCountCount, toPosition + headerViewsCountCount + itemCount);
        }

    };

    protected void setAdapter(RecyclerView.Adapter<RecyclerView.ViewHolder> adapter) {

        if (adapter != null) {
            if (!(adapter instanceof RecyclerView.Adapter))
                throw new RuntimeException("your adapter must be a RecyclerView.Adapter");
        }

        if (mInnerAdapter != null) {
            notifyItemRangeRemoved(getHeaderViewsCount(), mInnerAdapter.getItemCount());
            mInnerAdapter.unregisterAdapterDataObserver(mDataObserver);
        }

        this.mInnerAdapter = adapter;
        mInnerAdapter.registerAdapterDataObserver(mDataObserver);
        notifyItemRangeInserted(getHeaderViewsCount(), mInnerAdapter.getItemCount());


    }

    protected void addHeaderView(View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        if (getHeaderViewsCount() > MAX_COUNT - 1) {
            Log.e("HFRecyclerview", "header is enough");
            return;
        }
        if (!mHeaderViews.contains(header)) {
            mHeaderViews.add(header);
            this.notifyDataSetChanged();
        }
    }


    protected void addHeaderView(int index, View header) {
        if (header == null) {
            throw new RuntimeException("header is null");
        }
        if (getHeaderViewsCount() > MAX_COUNT - 1) {
            Log.e("HFRecyclerview", "header is enough");
            return;
        }
        if (!mHeaderViews.contains(header)) {
            mHeaderViews.add(index > mHeaderViews.size() ? mHeaderViews.size() : index, header);
            this.notifyDataSetChanged();
        }
    }

    /**
     * @return header个数
     */
    protected int getHeaderViewsCount() {
        return mHeaderViews.size();
    }


    protected void addFooterView(View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }

        if (getFooterViewsCount() > MAX_COUNT - 1) {
            Log.e("HFRecyclerview", "footer is enough");
            return;
        }

        if (!mFooterViews.contains(footer)) {
            mFooterViews.add(footer);
            this.notifyDataSetChanged();
        }


    }

    protected void addFooterView(int index, View footer) {
        if (footer == null) {
            throw new RuntimeException("footer is null");
        }

        if (getFooterViewsCount() > MAX_COUNT - 1) {
            throw new RuntimeException("footer is enough");
        }

        if (!mFooterViews.contains(footer)) {
            mFooterViews.add(index > mFooterViews.size() ? mFooterViews.size() : index, footer);
            this.notifyDataSetChanged();
        }


    }

    /**
     * footer 个数
     *
     * @return
     */
    protected int getFooterViewsCount() {
        return mFooterViews.size();
    }


    private boolean isHeader(int position) {
        return getHeaderViewsCount() > 0 && position < getHeaderViewsCount();
    }

    private boolean isFooter(int position) {
        return getFooterViewsCount() > 0 && position >= getItemCount() - getFooterViewsCount();
    }

    @Override
    public int getItemCount() {
        return getHeaderViewsCount() + mInnerAdapter.getItemCount() + getFooterViewsCount();
    }

    @Override
    public int getItemViewType(int position) {
        int innerCount = mInnerAdapter.getItemCount();
        if (position < getHeaderViewsCount()) {
            return TYPE_HEADER_VIEW + position;
        } else if (position >= getHeaderViewsCount() && position < getHeaderViewsCount() + innerCount) {
            return mInnerAdapter.getItemViewType(position - getHeaderViewsCount());
        } else {
            return TYPE_FOOTER_VIEW + position - getHeaderViewsCount() - mInnerAdapter.getItemCount();
        }

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType >= TYPE_HEADER_VIEW && viewType < TYPE_HEADER_VIEW + MAX_COUNT) {
            return new ViewHolder(mHeaderViews.get(viewType - TYPE_HEADER_VIEW));

        } else if (viewType >= TYPE_FOOTER_VIEW && viewType < TYPE_FOOTER_VIEW + MAX_COUNT) {
            return new ViewHolder(mFooterViews.get(viewType - TYPE_FOOTER_VIEW));
        } else {
            return mInnerAdapter.createViewHolder(parent, viewType);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int headerViewsCountCount = getHeaderViewsCount();
        if (position >= headerViewsCountCount && position < headerViewsCountCount + mInnerAdapter.getItemCount()) {
            mInnerAdapter.onBindViewHolder(holder, position - headerViewsCountCount);
        }

    }

    @Override
    public void onViewAttachedToWindow(RecyclerView.ViewHolder holder) {
        mInnerAdapter.onViewAttachedToWindow(holder);
        // 处理Staggered模式下添加header/footer问题

        int position = holder.getLayoutPosition();
        if (isHeader(position) || isFooter(position)) {
            ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
            if (layoutParams != null && layoutParams instanceof StaggeredGridLayoutManager.LayoutParams) {
                StaggeredGridLayoutManager.LayoutParams p = (StaggeredGridLayoutManager.LayoutParams) layoutParams;
                p.setFullSpan(true);
            }

        }


    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
//        处理GridLayoutManager 模式下添加header/footer的问题
        RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            final GridLayoutManager gridLayoutManager = (GridLayoutManager) layoutManager;
            final GridLayoutManager.SpanSizeLookup lookup = gridLayoutManager.getSpanSizeLookup();
            gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    if (isHeader(position) || isFooter(position)) {
                        return gridLayoutManager.getSpanCount();
                    }
                    return lookup.getSpanSize(position - getHeaderViewsCount());
                }
            });
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(View itemView) {
            super(itemView);
        }
    }
}
