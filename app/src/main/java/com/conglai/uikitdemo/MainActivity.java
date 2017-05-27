package com.conglai.uikitdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;

import com.conglai.common.Configs;
import com.conglai.common.Debug;
import com.conglai.uikit.feature.base.BaseRecyclerView;
import com.conglai.uikit.feature.features.pullrefresh.feature.CustomFeature;
import com.conglai.uikit.view.FeatureRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FeatureRecyclerView mListView;
    SimpleAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Configs.setDebug(true);
        mListView = (FeatureRecyclerView) findViewById(android.R.id.list);
//        mListView.setLayoutManager(new GridLayoutManager(this, 3));
//        mListView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));

        mListView.addFeature(new CustomFeature<BaseRecyclerView>(this));

        mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.image_item, mListView, false));
        mListView.addFooterView(LayoutInflater.from(this).inflate(R.layout.image_item, mListView, false));

        mAdapter = new SimpleAdapter(this);
        mListView.setAdapter(mAdapter);
        mAdapter.setDataList(getList(30));
    }


    private List<String> getList(int num) {
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= num; i++) {
            list.add("DATA   " + i);
        }
        return list;
    }

}
