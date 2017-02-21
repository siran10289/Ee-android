package com.eeyuva.screens.home.infiniteCoverFlow;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.eeyuva.R;
import com.eeyuva.screens.home.HomeActivity;

public class InfinitePagerAdapter extends FragmentPagerAdapter implements ViewPager.PageTransformer {
    public final static float BIG_SCALE = 1.0f;
    public final static float SMALL_SCALE = 0.7f;
    public final static float DIFF_SCALE = BIG_SCALE - SMALL_SCALE;

    private InfiniteLinearLayout cur = null;
    private InfiniteLinearLayout next = null;
    private HomeActivity context;
    private FragmentManager fm;
    private float scale;

    public InfinitePagerAdapter(HomeActivity context, FragmentManager fm) {
        super(fm);
        this.fm = fm;
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        // make the first pager bigger than others
        if (position == HomeActivity.FIRST_PAGE)
            scale = BIG_SCALE;
        else
            scale = SMALL_SCALE;

        position = position % HomeActivity.PAGES;
        return InfiniteFragment.newInstance(context, position, scale);
    }

    @Override
    public int getCount() {
        return HomeActivity.PAGES * HomeActivity.LOOPS;
    }

    @Override
    public void transformPage(View page, float position) {
        InfiniteLinearLayout myLinearLayout = (InfiniteLinearLayout) page.findViewById(R.id.root);
        float scale = BIG_SCALE;
        if (position > 0) {
            scale = scale - position * DIFF_SCALE;
        } else {
            scale = scale + position * DIFF_SCALE;
        }
        if (scale < 0) scale = 0;
        myLinearLayout.setScaleBoth(scale);
    }
}
