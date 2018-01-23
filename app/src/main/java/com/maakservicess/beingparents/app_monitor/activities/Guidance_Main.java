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

public class Guidance_Main extends AppCompatActivity {

    ArrayList<MainsListData> guidanceList;
    MainsListAdapter mainsListAdapter;

    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.guidance);

        mAdView = (AdView) findViewById(R.id.guidanceMainAdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ListView guidanceListView = (ListView) findViewById(R.id.guidance_main_list_view);

        guidanceList = new ArrayList<>();
        guidanceList.add(new MainsListData("gui1", "Handling new born", R.drawable.baby_stand_64));
        guidanceList.add(new MainsListData("gui2", "Soothing", R.drawable.pacifier_64));
        guidanceList.add(new MainsListData("gui3", "Sleeping basics", R.drawable.crib_64));
        guidanceList.add(new MainsListData("gui4", "Washing your hands", R.drawable.handwash_64));
        guidanceList.add(new MainsListData("gui5", "Burping your baby", R.drawable.burp_64));
        guidanceList.add(new MainsListData("gui6", "Swaddle Your baby", R.drawable.swadle_64));
        guidanceList.add(new MainsListData("gui7", "Bathing your baby", R.drawable.bath_tub_64));
        guidanceList.add(new MainsListData("gui8", "Brushing your baby's teeth", R.drawable.tooth_brush_64));
        guidanceList.add(new MainsListData("gui9", "Infant Masssage", R.drawable.guidance_massage_64));
        guidanceList.add(new MainsListData("gui10", "Frequently asked questions", R.drawable.guidnace_faq_64));

        mainsListAdapter = new MainsListAdapter(getApplicationContext(), guidanceList);
        guidanceListView.setAdapter(mainsListAdapter);

        guidanceListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MainsListData selectedMainsListData = guidanceList.get(position);
                Intent guidanceNavigationIntent = null;

                switch (Integer.valueOf(position)) {
                    case 0:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Handling_NewBorn.class);
                        break;
                    case 1:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_soothing_main.class);
                        break;
                    case 2:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Sleeping_basics.class);
                        break;
                    case 3:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Washing_Hands.class);
                        break;
                    case 4:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Burping.class);
                        break;
                    case 5:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Swaddle.class);
                        break;
                    case 6:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Bathing_Main.class);
                        break;
                    case 7:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Brushing_Main.class);
                        break;

                    case 8:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_Infant_Massage.class);
                        break;
                    case 9:
                        guidanceNavigationIntent = new Intent(getApplicationContext(), Guidance_FAQ.class);
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
