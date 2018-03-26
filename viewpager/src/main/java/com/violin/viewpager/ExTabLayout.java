package com.violin.viewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.HorizontalScrollView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static android.support.v4.view.ViewPager.SCROLL_STATE_DRAGGING;
import static android.support.v4.view.ViewPager.SCROLL_STATE_IDLE;
import static android.support.v4.view.ViewPager.SCROLL_STATE_SETTLING;

/**
 * Created by wanghuilin on 2018/3/22.
 * <p>
 * email:violinlin@yeah.net
 */

public class ExTabLayout extends HorizontalScrollView {

    private ViewPager mViewPager;
    private final ArrayList<Tab> mTabs = new ArrayList<>();
    private TabLayout.Tab mSelectedTab;
    private TabLayoutOnPageChangeListener mPageChangeListener;
    public ExTabLayout(Context context) {
        super(context);
    }

    public ExTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setupWithViewPager(ViewPager viewPager) {

        if (viewPager!=null){
            mViewPager=viewPager;
            if (mPageChangeListener==null){
                mPageChangeListener=new TabLayoutOnPageChangeListener(this);
            }
            mPageChangeListener.reset();

            viewPager.addOnPageChangeListener(mPageChangeListener);


        }

    }

    /**
     * @param position                current scroll position
     * @param positionOffset          Value from [0, 1) indicating the offset from {@code position}.
     * @param updateSelectedText      Whether to update the text's selected state.
     * @param updateIndicatorPosition
     */
    public void setScrollPosition(int position, float positionOffset, boolean updateSelectedText,
                                  boolean updateIndicatorPosition) {

        Log.d("whl", "position " + position + " offset " + positionOffset
                + " upText " + updateSelectedText + " upindicator " + updateIndicatorPosition);

    }
    /**
     * Returns the number of tabs currently registered with the action bar.
     *
     * @return Tab count
     */
    public int getTabCount() {
        return mTabs.size();
    }


    /**
     * Returns the position of the current selected tab.
     *
     * @return selected tab position, or {@code -1} if there isn't a selected tab.
     */
    public int getSelectedTabPosition() {
        return mSelectedTab != null ? mSelectedTab.getPosition() : -1;
    }

    void selectTab(final Tab tab, boolean updateIndicator){


    }
    /**
     * Returns the tab at the specified index.
     */
    @Nullable
    public Tab getTabAt(int index) {
        return (index < 0 || index >= getTabCount()) ? null : mTabs.get(index);
    }

    public static class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private final WeakReference<ExTabLayout> mTabLayoutRef;
        private int mPreviousScrollState;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(ExTabLayout tabLayout) {
            mTabLayoutRef = new WeakReference<>(tabLayout);
        }

        @Override
        public void onPageScrollStateChanged(final int state) {
            mPreviousScrollState = mScrollState;
            mScrollState = state;
        }

        @Override
        public void onPageScrolled(final int position, final float positionOffset,
                                   final int positionOffsetPixels) {
            final ExTabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null) {
                // Only update the text selection if we're not settling, or we are settling after
                // being dragged
                final boolean updateText = mScrollState != SCROLL_STATE_SETTLING ||
                        mPreviousScrollState == SCROLL_STATE_DRAGGING;
                // Update the indicator if we're not settling after being idle. This is caused
                // from a setCurrentItem() call and will be handled by an animation from
                // onPageSelected() instead.
                final boolean updateIndicator = !(mScrollState == SCROLL_STATE_SETTLING
                        && mPreviousScrollState == SCROLL_STATE_IDLE);
                tabLayout.setScrollPosition(position, positionOffset, updateText, updateIndicator);
            }
        }

        @Override
        public void onPageSelected(final int position) {
            final ExTabLayout tabLayout = mTabLayoutRef.get();
            if (tabLayout != null && tabLayout.getSelectedTabPosition() != position
                    && position < tabLayout.getTabCount()) {
                // Select the tab, only updating the indicator if we're not being dragged/settled
                // (since onPageScrolled will handle that).
                final boolean updateIndicator = mScrollState == SCROLL_STATE_IDLE
                        || (mScrollState == SCROLL_STATE_SETTLING
                        && mPreviousScrollState == SCROLL_STATE_IDLE);
                tabLayout.selectTab(tabLayout.getTabAt(position), updateIndicator);
            }
        }

        void reset() {
            mPreviousScrollState = mScrollState = SCROLL_STATE_IDLE;
        }
    }

    private class AdapterChangeListener implements ViewPager.OnAdapterChangeListener {
        private boolean mAutoRefresh;

        AdapterChangeListener() {
        }

        @Override
        public void onAdapterChanged(@NonNull ViewPager viewPager,
                                     @Nullable PagerAdapter oldAdapter, @Nullable PagerAdapter newAdapter) {
            if (mViewPager == viewPager) {
//                setPagerAdapter(newAdapter, mAutoRefresh);
            }
        }

        void setAutoRefresh(boolean autoRefresh) {
            mAutoRefresh = autoRefresh;
        }
    }

    public static final class Tab {
        public static final int INVALID_POSITION = -1;
        private int mPosition = INVALID_POSITION;

        public int getPosition() {
            return mPosition;
        }

        void setPosition(int position) {
            mPosition = position;
        }


    }


}
