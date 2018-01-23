package com.maakservicess.beingparents.app_monitor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by SAI PC on 4/1/2016.
 */
public class Feeding_breast_Guide extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feeding_breast_guide, container, false);

        // get our html content
        String htmlAsString = getString(R.string.breast_feed_guide_main_text);
        // used by WebView

        WebView webView = (WebView) rootView.findViewById(R.id.breastFeedGuideWebView);
        webView.loadDataWithBaseURL(null, htmlAsString, "text/html", "utf-8", null);


        return rootView;
    }
}