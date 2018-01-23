package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maakservicess.beingparents.HtmlTagHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

/**
 * Created by SAI PC on 4/5/2016.
 */
public class Guidance_Sleeping_basics extends AppCompatActivity {

    private AdView mAdview;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sleeping_basics_main);

        mAdview = (AdView) findViewById(R.id.guidnace_sleeping_basics_adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdview.loadAd(adRequest);
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

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String htmlAsString1 = getString(R.string.sleeping_basic_text1);
        String htmlAsString2 = getString(R.string.sleeping_basic_text2);
        String htmlAsString3 = getString(R.string.sleeping_basic_text3);
        String htmlAsString4 = getString(R.string.sleeping_basic_text4);
        String htmlAsString5 = getString(R.string.sleeping_basic_text5);
        TextView sleeping_basic_textView1 = (TextView) findViewById(R.id.sleeping_basic_TextView1);
        TextView sleeping_basic_textView2 = (TextView) findViewById(R.id.sleeping_basic_TextView2);
        TextView sleeping_basic_textView3 = (TextView) findViewById(R.id.sleeping_basic_TextView3);
        TextView sleeping_basic_textView4 = (TextView) findViewById(R.id.sleeping_basic_TextView4);
        TextView sleeping_basic_textView5 = (TextView) findViewById(R.id.sleeping_basic_TextView5);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        sleeping_basic_textView1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        sleeping_basic_textView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        sleeping_basic_textView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        sleeping_basic_textView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        sleeping_basic_textView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));


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