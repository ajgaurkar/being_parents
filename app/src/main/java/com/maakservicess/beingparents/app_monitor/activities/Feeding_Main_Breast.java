package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.fragments.Feeding_breast_Basics;
import com.maakservicess.beingparents.app_monitor.fragments.Feeding_breast_Benefits;
import com.maakservicess.beingparents.app_monitor.fragments.Feeding_breast_Guide;
import com.maakservicess.beingparents.app_monitor.fragments.Feeding_breast_Miscellaneous;
import com.maakservicess.beingparents.app_monitor.fragments.Feeding_breast_Positions;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;
import java.util.List;

public class Feeding_Main_Breast extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feeding__main__breast);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mAdview = (AdView) findViewById(R.id.breast_feed_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
        //ad listener to set visibility of banner
        mAdview.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdview.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdview.setVisibility(View.GONE);
            }

        });


        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.breast_main_container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.breast_feed_tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());

        //custom method to add fragments to viewpager
        adapter.addFragment(new Feeding_breast_Basics(), "Basics");
        adapter.addFragment(new Feeding_breast_Benefits(), "Benefits");
        adapter.addFragment(new Feeding_breast_Positions(), "Positions");
        adapter.addFragment(new Feeding_breast_Guide(), "Guide");
        adapter.addFragment(new Feeding_breast_Miscellaneous(), "Miscellaneous");

        viewPager.setAdapter(adapter);
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);

        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

}
