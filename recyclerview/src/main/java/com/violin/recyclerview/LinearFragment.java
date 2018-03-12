package com.violin.recyclerview;


import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.violin.recyclerview.kit.nextpage.NextPageControl;
import com.violin.recyclerview.kit.rvextension.HFRecyclerControl;
import com.violin.util.Util;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class LinearFragment extends BaseFragment {

    private int mPageNO;
    public static final String TAG = LinearFragment.class.getName();
    private NextPageControl nextPageControl;
    private HFRecyclerControl hfRecyclerControl;

    public LinearFragment() {
        // Required empty public constructor
    }

    public static LinearFragment newInstance() {


        LinearFragment fragment = new LinearFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_linear, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {

        Button addHeader = (Button) getView().findViewById(R.id.btn_header);
        addHeader.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(v.getContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                int count = hfRecyclerControl.getHeaderCount() + 1;
                textView.setText("headerview" + count);
                hfRecyclerControl.addHeaderView(textView);
            }
        });

        Button addFooter = (Button) getView().findViewById(R.id.btn_footer);
        addFooter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = new TextView(v.getContext());
                textView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                textView.setGravity(Gravity.CENTER);
                int count = hfRecyclerControl.getFooterCount() + 1;
                textView.setText("footerView" + count);
                hfRecyclerControl.addFooterView(textView);
            }
        });

        RecyclerView recyclerView = getView().findViewById(R.id.recyclerview);
        LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(manager);
        RecyclerAdapter adapter = new RecyclerAdapter();

        recyclerView.setAdapter(adapter);
        hfRecyclerControl = new HFRecyclerControl();
        hfRecyclerControl.setAdapter(recyclerView,adapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int space= Util.dp2px(view.getContext(),5);
                outRect.left=outRect.right=outRect.top=outRect.bottom=space;
            }
        });
        nextPageControl = new NextPageControl();
        nextPageControl.setUpWithRecyclerView(recyclerView);
        nextPageControl.setListener(new NextPageControl.Listener() {
            @Override
            public void requestNextPageData() {
                nextPageControl.setRequestNext(true);
                Log.d(TAG, "requestNext");
                loadNextData();
            }
        });
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {

            dataList.add("Item" + i);

        }

        adapter.setDatas(dataList);


    }

    public void loadNextData() {
        Log.d(TAG, "加载数据中...");
        mPageNO++;
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextPageControl.setRequestNext(false);
                if (mPageNO > 3) {
                    nextPageControl.setHasMore(false);
                    Log.d(TAG, "加载完毕");
                }

            }
        }, 5000);
    }

    @Override
    String getTitle() {
        return "Linear";
    }
}
