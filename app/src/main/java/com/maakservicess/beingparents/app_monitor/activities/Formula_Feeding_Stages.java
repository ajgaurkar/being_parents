package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.maakservicess.beingparents.app_monitor.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by Amey on 6/5/2016.
 */
public class Formula_Feeding_Stages extends AppCompatActivity {
    private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formula_feeding_stages);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mAdview = (AdView) findViewById(R.id.formula_feeding_stages_adView);
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
