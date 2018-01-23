package com.maakservicess.beingparents.app_monitor.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maakservicess.beingparents.HtmlTagHandler;
import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by SAI PC on 4/1/2016.
 */
public class Feeding_breast_Miscellaneous extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.feeding_breast_miscellaneous, container, false);

        // get our html content
        // String htmlAsString1 = getString(R.string.breast_feed_miscellaneous_main_text1);      // used by WebView
        String htmlAsString2 = getString(R.string.breast_feed_miscellaneous_maintext2);      // used by WebView
        String htmlAsString3 = getString(R.string.breast_feed_miscellaneous_maintext3);      // used by WebView
        String htmlAsString4 = getString(R.string.breast_feed_miscellaneous_maintext4);      // used by WebView
        String htmlAsString5 = getString(R.string.breast_feed_miscellaneous_maintext5);      // used by WebView
        String htmlAsString6 = getString(R.string.breast_feed_miscellaneous_maintext6);      // used by WebView
        String htmlAsString7 = getString(R.string.breast_feed_miscellaneous_maintext7);      // used by WebView
        String htmlAsString8 = getString(R.string.breast_feed_miscellaneous_maintext8);      // used by WebView
        String htmlAsString9 = getString(R.string.breast_feed_miscellaneous_maintext9);      // used by WebView
        String htmlAsString10 = getString(R.string.breast_feed_miscellaneous_maintext10);      // used by WebView
        String htmlAsString11 = getString(R.string.breast_feed_miscellaneous_maintext11);      // used by WebView
        String htmlAsString12 = getString(R.string.breast_feed_miscellaneous_maintext12);      // used by WebView
        String htmlAsString13 = getString(R.string.breast_feed_miscellaneous_maintext13);      // used by WebView
        String htmlAsString14 = getString(R.string.breast_feed_miscellaneous_maintext14);      // used by WebView
        String htmlAsString15 = getString(R.string.breast_feed_miscellaneous_maintext15);
        String htmlAsString16 = getString(R.string.breast_feed_miscellaneous_maintext16);


        TextView breastFeedMiscellaneousTextView1 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView1);
        //  breastFeedMiscellaneousTextView1.setText(Html.fromHtml(getString(R.string.breast_feed_miscellaneous_main_text1)));

        TextView breastFeedMiscellaneousTextView2 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView2);
        TextView breastFeedMiscellaneousTextView3 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView3);
        TextView breastFeedMiscellaneousTextView4 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView4);
        TextView breastFeedMiscellaneousTextView5 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView5);
        TextView breastFeedMiscellaneousTextView6 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView6);
        TextView breastFeedMiscellaneousTextView7 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView7);
        TextView breastFeedMiscellaneousTextView8 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView8);
        TextView breastFeedMiscellaneousTextView9 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView9);
        TextView breastFeedMiscellaneousTextView10 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView10);
        TextView breastFeedMiscellaneousTextView11 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView11);
        TextView breastFeedMiscellaneousTextView12 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView12);
        TextView breastFeedMiscellaneousTextView13 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView13);
        TextView breastFeedMiscellaneousTextView14 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView14);
        TextView breastFeedMiscellaneousTextView15 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView15);
        TextView breastFeedMiscellaneousTextView16 = (TextView) rootView.findViewById(R.id.breastFeedMiscellaneousTextView16);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        breastFeedMiscellaneousTextView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        breastFeedMiscellaneousTextView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        breastFeedMiscellaneousTextView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        breastFeedMiscellaneousTextView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        breastFeedMiscellaneousTextView6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        breastFeedMiscellaneousTextView7.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        breastFeedMiscellaneousTextView8.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));
        breastFeedMiscellaneousTextView9.setText(Html.fromHtml(htmlAsString9, null, htmlTagHandler));
        breastFeedMiscellaneousTextView10.setText(Html.fromHtml(htmlAsString10, null, htmlTagHandler));
        breastFeedMiscellaneousTextView11.setText(Html.fromHtml(htmlAsString11, null, htmlTagHandler));
        breastFeedMiscellaneousTextView12.setText(Html.fromHtml(htmlAsString12, null, htmlTagHandler));
        breastFeedMiscellaneousTextView13.setText(Html.fromHtml(htmlAsString13, null, htmlTagHandler));
        breastFeedMiscellaneousTextView14.setText(Html.fromHtml(htmlAsString14, null, htmlTagHandler));
        breastFeedMiscellaneousTextView15.setText(Html.fromHtml(htmlAsString15, null, htmlTagHandler));
        breastFeedMiscellaneousTextView16.setText(Html.fromHtml(htmlAsString16, null, htmlTagHandler));


        return rootView;
    }
}