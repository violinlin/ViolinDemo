package com.violin.recyclerview.kit.rvextension;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

/**
 * Created by whl on 2017/8/9.
 */

public class HFRecyclerControl {
    private RecyclerView recyclerView;
    private  HFRecyclerAdapter hfAdapter;

    public HFRecyclerControl(){
        hfAdapter=new HFRecyclerAdapter();
    }

    public void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter adapter){
        this.recyclerView=recyclerView;
        hfAdapter.setAdapter(adapter);
        recyclerView.setAdapter(hfAdapter);

    }

    public void addHeaderView(View header){
        hfAdapter.addHeaderView(header);
    }

    public void addFooterView(View footer){
        hfAdapter.addFooterView(footer);
    }

    public int getHeaderCount(){
        return hfAdapter.getHeaderViewsCount();
    }

    public int getFooterCount(){
        return hfAdapter.getFooterViewsCount();
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }
}
