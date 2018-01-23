package com.maakservicess.beingparents.app_monitor.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class Feeding_Complementary_12_23_months extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complementary_feeding_12_23month, container, false);
        String htmlAsString1 = getString(R.string.complementary_feeding_12_23month_text_1);
        String htmlAsString2 = getString(R.string.complementary_feeding_12_23month_text_2);
        String htmlAsString3 = getString(R.string.complementary_feeding_12_23month_text_3);
        String htmlAsString4 = getString(R.string.complementary_feeding_12_23month_text_4);
        String htmlAsString5 = getString(R.string.complementary_feeding_12_23month_text_5);
        String htmlAsString6 = getString(R.string.complementary_feeding_12_23month_text_6);
        String htmlAsString7 = getString(R.string.complementary_feeding_12_23month_text_7);
        String htmlAsString8 = getString(R.string.complementary_feeding_12_23month_text_8);
        String htmlAsString9 = getString(R.string.complementary_feeding_12_23month_text_9);
        String htmlAsString10 = getString(R.string.complementary_feeding_12_23month_text_10);
        TextView comp_feeding_12_23month_textView_1 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_1);
        TextView comp_feeding_12_23month_textView_2 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_2);
        TextView comp_feeding_12_23month_textView_3 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_3);
        TextView comp_feeding_12_23month_textView_4 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_4);
        TextView comp_feeding_12_23month_textView_5 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_5);
        TextView comp_feeding_12_23month_textView_6 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_6);
        TextView comp_feeding_12_23month_textView_7 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_7);
        TextView comp_feeding_12_23month_textView_8 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_8);
        TextView comp_feeding_12_23month_textView_9 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_9);
        TextView comp_feeding_12_23month_textView_10 = (TextView) rootView.findViewById(R.id.complementary_feeding_12_23month_textview_10);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        comp_feeding_12_23month_textView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        comp_feeding_12_23month_textView_2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        comp_feeding_12_23month_textView_3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        comp_feeding_12_23month_textView_4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        comp_feeding_12_23month_textView_5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        comp_feeding_12_23month_textView_6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        comp_feeding_12_23month_textView_7.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        comp_feeding_12_23month_textView_8.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));
        comp_feeding_12_23month_textView_9.setText(Html.fromHtml(htmlAsString9, null, htmlTagHandler));
        comp_feeding_12_23month_textView_10.setText(Html.fromHtml(htmlAsString10, null, htmlTagHandler));

        return rootView;
    }
}
