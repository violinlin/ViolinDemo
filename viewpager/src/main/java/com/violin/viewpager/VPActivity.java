package com.violin.viewpager;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
//NavigationTabStrip
public class VPActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vp);
        initView();
    }

    public static void start(Context context) {
        Intent starter = new Intent(context, VPActivity.class);
        context.startActivity(starter);
    }

    private void initView() {
        TabLayout tabLayout = findViewById(R.id.tablayout);
        Class tablayoutCla=tabLayout.getClass();

        try {
            Field field=tablayoutCla.getDeclaredField("mTabBackgroundResId");
            field.setAccessible(true);
            field.setInt(tabLayout,0);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        ViewPager viewPager = findViewById(R.id.viewpager);
        List<BlankFragment> fragments = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BlankFragment blankFragment = BlankFragment.newInstance("item" + i);
            blankFragment.setTitle("item"+i);
            fragments.add(blankFragment);
        }
        VPAdapter adapter = new VPAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        adapter.setData(fragments);
        tabLayout.setupWithViewPager(viewPager);
        ExTabLayout exTabLayout=new ExTabLayout(this);
        exTabLayout.setupWithViewPager(viewPager);

    }

    class VPAdapter extends FragmentPagerAdapter {
        private List<BlankFragment> fragmentList;

        public VPAdapter(FragmentManager fm) {
            super(fm);
            fragmentList = new ArrayList<>();
        }

        public void setData(List<BlankFragment> fragments) {

            fragmentList.clear();
            fragmentList.addAll(fragments);
            notifyDataSetChanged();
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentList.get(position).getTitle();
        }
    }
}
