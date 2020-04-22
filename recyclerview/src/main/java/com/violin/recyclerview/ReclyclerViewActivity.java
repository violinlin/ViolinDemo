package com.violin.recyclerview;

import android.content.Context;
import android.content.Intent;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

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
