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
public class Feeding_Complementary_Upto_6months extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complementary_feeding_upto_6month, container, false);
        String htmlAsString1 = getString(R.string.complementary_feeding_upto6_text_1);
        String htmlAsString2 = getString(R.string.complementary_feeding_upto6_text_2);
        String htmlAsString3 = getString(R.string.complementary_feeding_upto6_text_3);
        String htmlAsString4 = getString(R.string.complementary_feeding_upto6_text_4);
        String htmlAsString5 = getString(R.string.complementary_feeding_upto6_text_5);
        String htmlAsString6 = getString(R.string.complementary_feeding_upto6_text_6);
        String htmlAsString7 = getString(R.string.complementary_feeding_upto6_text_7);
        String htmlAsString8 = getString(R.string.complementary_feeding_upto6_text_8);
        String htmlAsString9 = getString(R.string.complementary_feeding_upto6_text_9);
        String htmlAsString10 = getString(R.string.complementary_feeding_upto6_text_10);
        String htmlAsString11 = getString(R.string.complementary_feeding_upto6_text_11);
        String htmlAsString12 = getString(R.string.complementary_feeding_upto6_text_12);
        String htmlAsString13 = getString(R.string.complementary_feeding_upto6_text_13);
        String htmlAsString14 = getString(R.string.complementary_feeding_upto6_text_14);
        String htmlAsString15 = getString(R.string.complementary_feeding_upto6_text_15);
        TextView comp_feeding_upto6_textView_1 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_1);
        TextView comp_feeding_upto6_textView_2 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_2);
        TextView comp_feeding_upto6_textView_3 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_3);
        TextView comp_feeding_upto6_textView_4 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_4);
        TextView comp_feeding_upto6_textView_5 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_5);
        TextView comp_feeding_upto6_textView_6 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_6);
        TextView comp_feeding_upto6_textView_7 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_7);
        TextView comp_feeding_upto6_textView_8 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_8);
        TextView comp_feeding_upto6_textView_9 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_9);
        TextView comp_feeding_upto6_textView_10 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_10);
        TextView comp_feeding_upto6_textView_11 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_11);
        TextView comp_feeding_upto6_textView_12 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_12);
        TextView comp_feeding_upto6_textView_13 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_13);
        TextView comp_feeding_upto6_textView_14 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_14);
        TextView comp_feeding_upto6_textView_15 = (TextView) rootView.findViewById(R.id.complementary_feeding_upto6_textview_15);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        comp_feeding_upto6_textView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        comp_feeding_upto6_textView_2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        comp_feeding_upto6_textView_3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        comp_feeding_upto6_textView_4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        comp_feeding_upto6_textView_5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        comp_feeding_upto6_textView_6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        comp_feeding_upto6_textView_7.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        comp_feeding_upto6_textView_8.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));
        comp_feeding_upto6_textView_9.setText(Html.fromHtml(htmlAsString9, null, htmlTagHandler));
        comp_feeding_upto6_textView_10.setText(Html.fromHtml(htmlAsString10, null, htmlTagHandler));
        comp_feeding_upto6_textView_11.setText(Html.fromHtml(htmlAsString11, null, htmlTagHandler));
        comp_feeding_upto6_textView_12.setText(Html.fromHtml(htmlAsString12, null, htmlTagHandler));
        comp_feeding_upto6_textView_13.setText(Html.fromHtml(htmlAsString13, null, htmlTagHandler));
        comp_feeding_upto6_textView_14.setText(Html.fromHtml(htmlAsString14, null, htmlTagHandler));
        comp_feeding_upto6_textView_15.setText(Html.fromHtml(htmlAsString15, null, htmlTagHandler));
        return rootView;
    }
}
