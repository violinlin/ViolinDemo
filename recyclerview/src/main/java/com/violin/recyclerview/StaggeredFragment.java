package com.violin.recyclerview;


import android.graphics.Rect;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
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
import java.util.Random;


/**
 * A simple {@link Fragment} subclass.
 */
public class StaggeredFragment extends BaseFragment {

    public static final String TAG = StaggeredFragment.class.getName();
    private NextPageControl nextPageControl;
    private long mPageNO;
    private HFRecyclerControl hfRecyclerControl;
    private RecyclerAdapter mAdapter;

    public StaggeredFragment() {
        // Required empty public constructor
    }

    public static StaggeredFragment newInstance() {
        StaggeredFragment fragment = new StaggeredFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_staggered, container, false);
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
        StaggeredGridLayoutManager manager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        mAdapter = new RecyclerAdapter();

        recyclerView.setAdapter(mAdapter);
        hfRecyclerControl = new HFRecyclerControl();
        hfRecyclerControl.setAdapter(recyclerView, mAdapter);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int space= Util.dp2px(view.getContext(),6);
                outRect.left=outRect.right=outRect.top=outRect.bottom=space;
            }
        });

        nextPageControl = new NextPageControl();
        nextPageControl.setUpWithRecyclerView(recyclerView);
        nextPageControl.linkHFRecycler(hfRecyclerControl);
        nextPageControl.setListener(new NextPageControl.Listener() {
            @Override
            public void requestNextPageData() {
                Log.d(TAG, "requestNext");
                loadNextData();
            }
        });
        List<String> dataList = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            StringBuilder sb = new StringBuilder();
            int r = new Random().nextInt(10);
            for (int j = 0; j < r; j++) {
                sb.append("\n");
            }
            dataList.add("Item" + i + sb);

        }

        mAdapter.setDatas(dataList);


    }

    public void loadNextData() {
        Log.d(TAG, "加载数据中...");
        mPageNO++;
        getView().postDelayed(new Runnable() {
            @Override
            public void run() {
                nextPageControl.setRequestNext(false);
                if (mPageNO > 2) {
                    nextPageControl.setHasMore(false);
                    Log.d(TAG, "加载完毕");
                }

                List<String> dataList = new ArrayList<>();
                for (int i = 0; i < 40; i++) {
                    StringBuilder sb = new StringBuilder();
                    int r = new Random().nextInt(10);
                    for (int j = 0; j < r; j++) {
                        sb.append("\n");
                    }
                    dataList.add("Item" + i + sb);

                }

                mAdapter.addDatas(dataList);

            }
        }, 5000);
    }


    @Override
    String getTitle() {
        return "Staggered";
    }
}
