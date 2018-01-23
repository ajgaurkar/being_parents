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
 * Created by SAI PC on 4/5/2016.
 */
public class Bathing_Basics extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bathing_basics_basics, container, false);
        String htmlAsString1 = getString(R.string.bath_basic_text_1);
        String htmlAsString2 = getString(R.string.bath_basic_text_2);
        String htmlAsString3 = getString(R.string.bath_basic_text_3);
        String htmlAsString4 = getString(R.string.bath_basic_text_4);
        String htmlAsString5 = getString(R.string.bath_basic_text_5);
        String htmlAsString6 = getString(R.string.bath_basic_text_6);
        String htmlAsString7 = getString(R.string.bath_basic_text_7);
        String htmlAsString8 = getString(R.string.bath_basic_text_8);
        String htmlAsString9 = getString(R.string.bath_basic_text_9);
        String htmlAsString10 = getString(R.string.bath_basic_text_10);
        String htmlAsString11 = getString(R.string.bath_basic_text_11);
        String htmlAsString12 = getString(R.string.bath_basic_text_12);
        String htmlAsString13 = getString(R.string.bath_basic_text_13);
        String htmlAsString14 = getString(R.string.bath_basic_text_14);
        String htmlAsString15 = getString(R.string.bath_basic_text_15);
        String htmlAsString16 = getString(R.string.bath_basic_text_16);
        String htmlAsString17 = getString(R.string.bath_basic_text_17);
        String htmlAsString18 = getString(R.string.bath_basic_text_18);
        String htmlAsString19 = getString(R.string.bath_basic_text_19);
        String htmlAsString20 = getString(R.string.bath_basic_text_20);
        String htmlAsString21 = getString(R.string.bath_basic_text_21);
        String htmlAsString22 = getString(R.string.bath_basic_text_23);
        String htmlAsString23 = getString(R.string.bath_basic_text_24);
        TextView bath_Basic_TextView_1 = (TextView) rootView.findViewById(R.id.bath_basic_textView_1);
        TextView bath_Basic_TextView_2 = (TextView) rootView.findViewById(R.id.bath_basic_textView_2);
        TextView bath_Basic_TextView_3 = (TextView) rootView.findViewById(R.id.bath_basic_textView_3);
        TextView bath_Basic_TextView_4 = (TextView) rootView.findViewById(R.id.bath_basic_textView_4);
        TextView bath_Basic_TextView_5 = (TextView) rootView.findViewById(R.id.bath_basic_textView_5);
        TextView bath_Basic_TextView_6 = (TextView) rootView.findViewById(R.id.bath_basic_textView_6);
        TextView bath_Basic_TextView_7 = (TextView) rootView.findViewById(R.id.bath_basic_textView_7);
        TextView bath_Basic_TextView_8 = (TextView) rootView.findViewById(R.id.bath_basic_textView_8);
        TextView bath_Basic_TextView_9 = (TextView) rootView.findViewById(R.id.bath_basic_textView_9);
        TextView bath_Basic_TextView_10 = (TextView) rootView.findViewById(R.id.bath_basic_textView_10);
        TextView bath_Basic_TextView_11 = (TextView) rootView.findViewById(R.id.bath_basic_textView_11);
        TextView bath_Basic_TextView_12 = (TextView) rootView.findViewById(R.id.bath_basic_textView_12);
        TextView bath_Basic_TextView_13 = (TextView) rootView.findViewById(R.id.bath_basic_textView_13);
        TextView bath_Basic_TextView_14 = (TextView) rootView.findViewById(R.id.bath_basic_textView_14);
        TextView bath_Basic_TextView_15 = (TextView) rootView.findViewById(R.id.bath_basic_textView_15);
        TextView bath_Basic_TextView_16 = (TextView) rootView.findViewById(R.id.bath_basic_textView_16);
        TextView bath_Basic_TextView_17 = (TextView) rootView.findViewById(R.id.bath_basic_textView_17);
        TextView bath_Basic_TextView_18 = (TextView) rootView.findViewById(R.id.bath_basic_textView_18);
        TextView bath_Basic_TextView_19 = (TextView) rootView.findViewById(R.id.bath_basic_textView_19);
        TextView bath_Basic_TextView_20 = (TextView) rootView.findViewById(R.id.bath_basic_textView_20);
        TextView bath_Basic_TextView_21 = (TextView) rootView.findViewById(R.id.bath_basic_textView_21);
        TextView bath_Basic_TextView_23 = (TextView) rootView.findViewById(R.id.bath_basic_textView_22);
        TextView bath_Basic_TextView_24 = (TextView) rootView.findViewById(R.id.bath_basic_textView_24);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        bath_Basic_TextView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        bath_Basic_TextView_2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        bath_Basic_TextView_3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        bath_Basic_TextView_4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        bath_Basic_TextView_5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        bath_Basic_TextView_6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        bath_Basic_TextView_7.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        bath_Basic_TextView_8.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));
        bath_Basic_TextView_9.setText(Html.fromHtml(htmlAsString9, null, htmlTagHandler));
        bath_Basic_TextView_10.setText(Html.fromHtml(htmlAsString10, null, htmlTagHandler));
        bath_Basic_TextView_11.setText(Html.fromHtml(htmlAsString11, null, htmlTagHandler));
        bath_Basic_TextView_12.setText(Html.fromHtml(htmlAsString12, null, htmlTagHandler));
        bath_Basic_TextView_13.setText(Html.fromHtml(htmlAsString13, null, htmlTagHandler));
        bath_Basic_TextView_14.setText(Html.fromHtml(htmlAsString14, null, htmlTagHandler));
        bath_Basic_TextView_15.setText(Html.fromHtml(htmlAsString15, null, htmlTagHandler));
        bath_Basic_TextView_16.setText(Html.fromHtml(htmlAsString16, null, htmlTagHandler));
        bath_Basic_TextView_17.setText(Html.fromHtml(htmlAsString17, null, htmlTagHandler));
        bath_Basic_TextView_18.setText(Html.fromHtml(htmlAsString18, null, htmlTagHandler));
        bath_Basic_TextView_19.setText(Html.fromHtml(htmlAsString19, null, htmlTagHandler));
        bath_Basic_TextView_20.setText(Html.fromHtml(htmlAsString20, null, htmlTagHandler));
        bath_Basic_TextView_21.setText(Html.fromHtml(htmlAsString21, null, htmlTagHandler));
        bath_Basic_TextView_23.setText(Html.fromHtml(htmlAsString22, null, htmlTagHandler));
        bath_Basic_TextView_24.setText(Html.fromHtml(htmlAsString23, null, htmlTagHandler));



        return rootView;
    }
}