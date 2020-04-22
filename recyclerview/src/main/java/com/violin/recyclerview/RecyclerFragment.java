package com.violin.recyclerview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wanghuilin on 2018/5/23.
 * <p>
 * email:wanghuilin@zshiliu.com
 */

public class RecyclerFragment extends Fragment {

    public RecyclerFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_reclycler_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        TabLayout tabLayout = getView().findViewById(R.id.tablayout);
        ViewPager viewPager = getView().findViewById(R.id.viewpager);
        VPAdapter vpAdapter = new VPAdapter(getChildFragmentManager());
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
}
