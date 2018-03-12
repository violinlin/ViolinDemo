package com.violin.recyclerview;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class ReclyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reclycler_view);
        initView();
    }

    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tablayout);
        ViewPager viewPager = findViewById(R.id.viewpager);
        VPAdapter vpAdapter = new VPAdapter(getSupportFragmentManager());
        viewPager.setAdapter(vpAdapter);
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(LinearFragment.newInstance());
        fragments.add(GridFragment.newInstance());
        fragments.add(StaggeredFragment.newInstance());
        vpAdapter.setData(fragments);
        tabLayout.setupWithViewPager(viewPager);

    }


    class VPAdapter extends FragmentPagerAdapter {
        private List<BaseFragment> fragments;

        public VPAdapter(FragmentManager fm) {
            super(fm);
            fragments = new ArrayList<>();
        }

        public void setData(List<BaseFragment> fragments) {
            this.fragments = fragments;
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragments.get(position).getTitle();
        }
    }


    public static void start(Context context) {
        Intent starter = new Intent(context, ReclyclerViewActivity.class);
        context.startActivity(starter);
    }
}
