package com.maakservicess.beingparents.app_monitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.maakservicess.beingparents.app_monitor.Adapters.MainsListAdapter;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.MainsListData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * Created by ajinkya on 12/29/2015.
 */
public class Feeding_main extends AppCompatActivity {

    ArrayList<MainsListData> feedingList;
    MainsListAdapter feedingListAdapter;

    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feeding_main);

        mAdView = (AdView) findViewById(R.id.feedingMainAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView feedingMainListView = (ListView) findViewById(R.id.feedingMainListView);

        feedingList = new ArrayList<>();
        feedingList.add(new MainsListData("feed1", "Breast feeding", R.drawable.breastfeeding64));
        feedingList.add(new MainsListData("feed2", "Formula feeding", R.drawable.food48));
        feedingList.add(new MainsListData("feed3", "Bottle feeding", R.drawable.baby_bottle_64));
        feedingList.add(new MainsListData("feed4", "Complementary feeding", R.drawable.food48));

        feedingListAdapter = new MainsListAdapter(getApplicationContext(), feedingList);
        feedingMainListView.setAdapter(feedingListAdapter);

        feedingMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainsListData selectedMainsListData = feedingList.get(position);
                Intent guidanceNavigationIntent = null;

                switch (position) {
                    case 0:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Feeding_Main_Breast.class);
                        break;
                    case 1:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Formula_Feeding_Main.class);
                        break;
                    case 2:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Feeding_Main_Bottle_Feeding.class);
                        break;
                    case 3:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Feeding_Main_Complementary_Food.class);
                        break;
                }

                startActivity(guidanceNavigationIntent);

            }
        });

        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        //ad listener to set visibility of banner
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdView.setVisibility(View.GONE);
            }

        });

    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }

    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
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
