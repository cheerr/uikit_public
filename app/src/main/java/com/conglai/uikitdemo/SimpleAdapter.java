package com.conglai.uikitdemo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.conglai.uikit.feature.base.adapter.SpanRecyclerAdapter;

import java.util.List;

/**
 * Created by chenwei on 15/12/31.
 */
public class SimpleAdapter extends SpanRecyclerAdapter<SpanRecyclerAdapter.ViewHolder> {

    private List<String> dataList;
    private Context context;

    public SimpleAdapter(Context context) {
        this.context = context;
    }

    public void setDataList(List<String> dataList) {
        this.dataList = dataList;
        notifyDataSetChanged();
    }

    @Override
    public int getRecyclerItemViewType(int pos) {
        return 0;
    }

    @Override
    public int getRecyclerItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    @Override
    public SpanRecyclerAdapter.ViewHolder onCreateRecyclerViewHolder(ViewGroup parent, int viewType) {
        View convertView = new TextView(context);
        return new ViewHolder(convertView);
    }

    @Override
    public SpanRecyclerAdapter.ViewHolder onCreateHeaderFooterViewHolder(View view, int type) {
        return new ViewHolder(view);
    }

    @Override
    public void onRecyclerBindViewHolder(SpanRecyclerAdapter.ViewHolder holder, int position) {
        if (holder.itemView instanceof TextView) {
            ((TextView) holder.itemView).setText("position：" + position);
            holder.itemView.setPadding(40, 40, 40, 40);
        }
    }

    static class ViewHolder extends SpanRecyclerAdapter.ViewHolder {

        public ViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public SpanRecyclerAdapter.ViewHolder initView(View itemView, int type) {
            return null;
        }
    }
}
