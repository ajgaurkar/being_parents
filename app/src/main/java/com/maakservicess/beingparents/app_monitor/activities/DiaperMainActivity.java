package com.maakservicess.beingparents.app_monitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.maakservicess.beingparents.HtmlTagHandler;
import com.maakservicess.beingparents.app_monitor.R;

public class DiaperMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diaper_testing);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        String htmlAsString1 = getString(R.string.diaper_main_text_1);
        String htmlAsString2 = getString(R.string.diaper_main_text_2);
        String htmlAsString3 = getString(R.string.diaper_main_text_3);
        String htmlAsString4 = getString(R.string.diaper_main_text_4);
        String htmlAsString5 = getString(R.string.diaper_main_text_5);
        String htmlAsString6 = getString(R.string.diaper_main_text_6);
        String htmlAsString7 = getString(R.string.diaper_main_text_7);
        String htmlAsString8 = getString(R.string.diaper_main_text_8);

        TextView diaper_textView1 = (TextView) findViewById(R.id.diaper_TextView1);
        TextView diaper_textView2 = (TextView) findViewById(R.id.diaper_TextView2);
        TextView diaper_textView3 = (TextView) findViewById(R.id.diaper_TextView3);
        TextView diaper_textView4 = (TextView) findViewById(R.id.diaper_TextView4);
        TextView diaper_textView5 = (TextView) findViewById(R.id.diaper_TextView5);
        TextView diaper_textView6 = (TextView) findViewById(R.id.diaper_TextView6);
        TextView diaper_textView7 = (TextView) findViewById(R.id.diaper_TextView7);
        TextView diaper_textView8 = (TextView) findViewById(R.id.diaper_TextView8);

        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        diaper_textView1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        diaper_textView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        diaper_textView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        diaper_textView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        diaper_textView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        diaper_textView6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        diaper_textView7.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        diaper_textView8.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DiaperMainActivity.this, DiaperSizeActivtiy.class);
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

