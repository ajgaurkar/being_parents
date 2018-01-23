package com.maakservicess.beingparents.app_monitor.Adapters;

/**
 * Created by SAI PC on 4/20/2016.
 */
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.maakservicess.beingparents.app_monitor.fragments.Calendar_Moments_Fragment;
import com.maakservicess.beingparents.app_monitor.fragments.Calendar_Visits_Fragment;

/**
 * Created by amey on 4/5/2016.
 */
public class CalenderPagerAdapter extends FragmentStatePagerAdapter {
    int mTabNo;

//    public CalenderPagerAdapter(FragmentManager fm, int TabNo) {
        public CalenderPagerAdapter(FragmentManager fm, int TabNo) {
            super(fm);
        mTabNo = TabNo;
    }

//    public CalenderPagerAdapter(android.app.FragmentManager manager, int tabCount) {
//        super(manager, tabCount);
//    }

    @Override
    public Fragment getItem(int position) {

        // Fragment frag = null;
        switch (position) {
            case 0:
                Calendar_Visits_Fragment calendar_visits_fragment = new Calendar_Visits_Fragment();
                return calendar_visits_fragment;

            case 1:
                Calendar_Moments_Fragment calendar_moments_fragment = new Calendar_Moments_Fragment();
                return calendar_moments_fragment;
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return mTabNo;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String title = " ";
        switch (position) {
            case 0:
                title = "Visits";
                break;
            case 1:
                title = "Moments";
                break;
        }

        return title;
    }
}
