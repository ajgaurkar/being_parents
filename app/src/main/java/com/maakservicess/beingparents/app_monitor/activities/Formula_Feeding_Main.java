package com.maakservicess.beingparents.app_monitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
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
public class Formula_Feeding_Main extends AppCompatActivity {

    private AdView mAdview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.formulafeeding);
        mAdview = (AdView) findViewById(R.id.formula_feeding_adView);
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

        TextView formula_Feeding_TextView_1 = (TextView) findViewById(R.id.fromula_feeding_textview_1);
        TextView formula_Feeding_TextView_2 = (TextView) findViewById(R.id.fromula_feeding_textview_2);
        TextView formula_Feeding_TextView_3 = (TextView) findViewById(R.id.fromula_feeding_textview_3);
        TextView formula_Feeding_TextView_4 = (TextView) findViewById(R.id.fromula_feeding_textview_4);
        TextView formula_Feeding_TextView_5 = (TextView) findViewById(R.id.fromula_feeding_textview_5);
        String htmlAsString1 = getString(R.string.formula_feeding_text_1);
        String htmlAsString2 = getString(R.string.formula_feeding_text_2);
        String htmlAsString3 = getString(R.string.formula_feeding_text_3);
        String htmlAsString4 = getString(R.string.formula_feeding_text_4);
        String htmlAsString5 = getString(R.string.formula_feeding_text_5);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        formula_Feeding_TextView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        formula_Feeding_TextView_2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        formula_Feeding_TextView_3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        formula_Feeding_TextView_4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        formula_Feeding_TextView_5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.formula_feeding_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Formula_Feeding_Main.this, Formula_Feeding_Stages.class);
                startActivity(intent);
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