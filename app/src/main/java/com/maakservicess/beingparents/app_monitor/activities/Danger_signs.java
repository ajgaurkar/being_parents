package com.maakservicess.beingparents.app_monitor.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.text.Spanned;
import android.view.MenuItem;
import android.webkit.WebView;

import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by SAI PC on 4/4/2016.
 */
public class Danger_signs extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danger_sign_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        textView = (TextView) findViewById(R.id.simpletextview);
//        textView.setText(Html.fromHtml("<h2>Ordered List</h2>\n" +
//                "<ol>\n" +
//                "<li> Coffee</li>\n" +
//                "<li> Tea</li>\n" +
//                "<li> Milk</li>\n" +
//                "</ol>"));



        // get our html content
        String htmlAsString = getString(R.string.html);      // used by WebView
        Spanned htmlAsSpanned = Html.fromHtml(htmlAsString); // used by TextView

        // set the html content on a TextView
//        TextView textView = (TextView) findViewById(R.id.textView229);
//        textView.setText(htmlAsSpanned);


        WebView webView = (WebView) findViewById(R.id.dangerSignsWebView);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);
        // webView.loadUrl("http://www.theworldsworstwebsiteever.com");
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