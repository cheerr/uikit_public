package com.conglai.uikit.feature.base.adapter;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.conglai.uikit.feature.base.BaseRecyclerView;

/**
 * Created by chenwei on 16/4/6.
 */
public abstract class SpanRecyclerAdapter<VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    public static final int TYPE_HEADER = -0x1f;
    public static final int TYPE_FOOTER = -0x1d;

    private View headerContainer = null;
    private View footerContainer = null;

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        if (recyclerView instanceof BaseRecyclerView) {
            headerContainer = ((BaseRecyclerView) recyclerView).getHeaderContainer();
            footerContainer = ((BaseRecyclerView) recyclerView).getFooterContainer();
        }
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        this.headerContainer = null;
        this.footerContainer = null;
    }

    /**
     * 对于StaggeredGridLayoutManager，头尾setFullSpan(true)
     *
     * @param holder
     */
    @Override
    public void onViewAttachedToWindow(VH holder) {
        super.onViewAttachedToWindow(holder);
        if (isFooterPosition(holder.getLayoutPosition()) || isHeaderPosition(holder.getLayoutPosition())) {
            ViewGroup.LayoutParams lp = holder.itemView.getLayoutParams();
            if (lp != null
                    && lp instanceof StaggeredGridLayoutManager.LayoutParams) {
                ((StaggeredGridLayoutManager.LayoutParams) lp).setFullSpan(true);
            }
        }
    }

    /**
     * 对于GridLayoutManager，头尾SpanSize为1
     *
     * @param layoutManager
     * @param ssl
     * @param position
     * @return
     */
    public int getSpanSize(GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup ssl, int position) {
        if (isHeaderPosition(position) || isFooterPosition(position)) {
            return layoutManager.getSpanCount();
        }
        return ssl == null ? 1 : ssl.getSpanSize(position);
    }

    public final int getItemViewType(int pos) {
        if (pos == 0 && headerContainer != null) {
            return TYPE_HEADER;
        }
        if (pos == getItemCount() - 1 && footerContainer != null) {
            return TYPE_FOOTER;
        }
        return getRecyclerItemViewType(pos - (headerContainer != null ? 1 : 0));
    }

    public final int getItemCount() {
        int extra = (headerContainer == null ? 0 : 1) + (footerContainer == null ? 0 : 1);
        return extra + getRecyclerItemCount();
    }

    public final VH onCreateViewHolder(ViewGroup parent, int viewType) {
        if (isHeaderType(viewType)) {
            return onCreateHeaderFooterViewHolder(headerContainer, viewType);
        } else if (isFooterType(viewType)) {
            return onCreateHeaderFooterViewHolder(footerContainer, viewType);
        } else {
            return onCreateRecyclerViewHolder(parent, viewType);
        }
    }

    public final void onBindViewHolder(VH holder, int position) {
        if (!isHeaderPosition(position) && !isFooterPosition(position)) {
            onRecyclerBindViewHolder(holder, position - (headerContainer != null ? 1 : 0));
        }
    }


    public abstract int getRecyclerItemViewType(int pos);

    public abstract int getRecyclerItemCount();

    public abstract VH onCreateRecyclerViewHolder(ViewGroup parent, int viewType);

    public abstract VH onCreateHeaderFooterViewHolder(View view, int type);

    public abstract void onRecyclerBindViewHolder(VH holder, int position);

    protected boolean isHeaderPosition(int position) {
        return isHeaderType(getItemViewType(position));
    }

    protected boolean isFooterPosition(int position) {
        return isFooterType(getItemViewType(position));
    }

    protected boolean isHeaderType(int viewType) {
        return viewType == TYPE_HEADER;
    }

    protected boolean isFooterType(int viewType) {
        return viewType == TYPE_FOOTER;
    }
}

