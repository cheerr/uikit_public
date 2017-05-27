package com.conglai.uikit.feature.base;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.conglai.uikit.R;
import com.conglai.uikit.feature.base.adapter.SpanRecyclerAdapter;
import com.conglai.uikit.feature.features.pullrefresh.builders.HeaderFooterBuilder;

/**
 * Created by chenwei on 16/4/6.
 */
public class BaseRecyclerView extends RecyclerView implements HeaderFooterBuilder {

    private LayoutManager mLayoutManager;
    private View headerContainer, footerContainer;

    public BaseRecyclerView(Context context) {
        super(context);
        initSelf();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initSelf();
    }

    public BaseRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initSelf();
    }

    private void initSelf() {
        headerContainer = LayoutInflater.from(getContext()).inflate(R.layout.ui_custom_header_container, null);
        footerContainer = LayoutInflater.from(getContext()).inflate(R.layout.ui_custom_footer_container, null);
        setLayoutManager(new LinearLayoutManager(getContext()));
    }

    public View getHeaderContainer() {
        return headerContainer;
    }

    public View getFooterContainer() {
        return footerContainer;
    }

    @Override
    public void setLayoutManager(LayoutManager layoutManager) {
        if (layoutManager instanceof GridLayoutManager) {
            GridLayoutManager.SpanSizeLookup ssl = ((GridLayoutManager) layoutManager).getSpanSizeLookup();
            if (ssl == null || !(ssl instanceof SpanSizeLookupCompat)) {
                ((GridLayoutManager) layoutManager).setSpanSizeLookup(new SpanSizeLookupCompat(this, ((GridLayoutManager) layoutManager), ssl));
            }
        }
        this.mLayoutManager = layoutManager;
        super.setLayoutManager(layoutManager);
    }


    public boolean arrivedTop() {
        if (mLayoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition() <= 1;
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPosition() <= 1;
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] pos = ((StaggeredGridLayoutManager) mLayoutManager).findFirstCompletelyVisibleItemPositions(new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()]);
            int min = pos[0];
            for (int i = 1; i < pos.length; i++) {
                min = min < pos[i] ? min : pos[i];
            }
            return min <= 1;
        }
        return false;
    }

    public boolean arrivedBottom() {
        if (mLayoutManager instanceof GridLayoutManager) {
            return ((GridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition() >= getAdapter().getItemCount() - 2;
        } else if (mLayoutManager instanceof LinearLayoutManager) {
            return ((LinearLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPosition() >= getAdapter().getItemCount() - 2;
        } else if (mLayoutManager instanceof StaggeredGridLayoutManager) {
            int[] pos = ((StaggeredGridLayoutManager) mLayoutManager).findLastCompletelyVisibleItemPositions(new int[((StaggeredGridLayoutManager) mLayoutManager).getSpanCount()]);
            int max = pos[0];
            for (int i = 1; i < pos.length; i++) {
                max = max > pos[i] ? max : pos[i];
            }
            return max >= getAdapter().getItemCount() - 2;
        }
        return false;
    }

    @Override
    public void addHeaderView(View view) {
        if (view.getId() == R.id.top_header_footer_container) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            FrameLayout frameLayout = ((FrameLayout) headerContainer.findViewById(R.id.custom_frame_container));
            frameLayout.addView(view);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            ((LinearLayout) headerContainer.findViewById(R.id.custom_header_container)).addView(view);
        }
    }

    /**
     * adapter必须继承HeaderFooterRecyclerAdapter才有用
     *
     * @param view
     */
    @Override
    public void addFooterView(View view) {
        if (view.getId() == R.id.top_header_footer_container) {
            if (view.getParent() != null) {
                ((ViewGroup) view.getParent()).removeView(view);
            }
            FrameLayout frameLayout = ((FrameLayout) footerContainer.findViewById(R.id.custom_frame_container));
            frameLayout.addView(view);
            frameLayout.setVisibility(View.VISIBLE);
        } else {
            ((LinearLayout) footerContainer.findViewById(R.id.custom_footer_container)).addView(view);
        }
    }

    @Override
    public boolean removeHeaderView(View view) {
        if (view.getId() == R.id.top_header_footer_container) {
            FrameLayout frameLayout = ((FrameLayout) headerContainer.findViewById(R.id.custom_frame_container));
            frameLayout.removeView(view);
        } else {
            ((LinearLayout) headerContainer.findViewById(R.id.custom_header_container)).removeView(view);
        }
        return false;
    }

    @Override
    public boolean removeFooterView(View view) {
        if (view.getId() == R.id.top_header_footer_container) {
            FrameLayout frameLayout = ((FrameLayout) footerContainer.findViewById(R.id.custom_frame_container));
            frameLayout.removeView(view);
        } else {
            ((LinearLayout) footerContainer.findViewById(R.id.custom_footer_container)).removeView(view);
        }
        return false;
    }

    @Override
    public int getHeaderViewsCount() {
        return getFirstHeader() == null ? 0 : 1;
    }

    @Override
    public int getFooterViewsCount() {
        return getLastFooter() == null ? 0 : 1;
    }

    @Override
    public View getFirstHeader() {
        return headerContainer.findViewById(R.id.custom_frame_container);
    }

    @Override
    public View getLastFooter() {
        return footerContainer.findViewById(R.id.custom_frame_container);
    }

    /**
     * 实现单元格的自定义宽度
     */
    private static class SpanSizeLookupCompat extends GridLayoutManager.SpanSizeLookup {

        private GridLayoutManager.SpanSizeLookup ssl;
        private GridLayoutManager lm;
        private RecyclerView recyclerView;

        public SpanSizeLookupCompat(BaseRecyclerView recyclerView, GridLayoutManager layoutManager, GridLayoutManager.SpanSizeLookup ssl) {
            this.ssl = ssl;
            this.lm = layoutManager;
            this.recyclerView = recyclerView;
        }

        @Override
        public int getSpanSize(int i) {
            if ((recyclerView.getAdapter() instanceof SpanRecyclerAdapter)) {
                return ((SpanRecyclerAdapter) recyclerView.getAdapter()).getSpanSize(lm, ssl, i);
            }
            return ssl == null ? 1 : ssl.getSpanSize(i);
        }
    }

    public void superDispatchTouchEvent(MotionEvent ev) {
        super.dispatchTouchEvent(ev);
    }

    public void superOnInterceptTouchEvent(MotionEvent ev) {
        super.onInterceptTouchEvent(ev);
    }

    public void superOnTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
    }
}
