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
public class Feeding_Complementary_6th_months extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complementary_feeding_6month, container, false);
        String htmlAsString1 = getString(R.string.complementary_feeding_6month_text_1);
        String htmlAsString2 = getString(R.string.complementary_feeding_6month_text_2);
        String htmlAsString3 = getString(R.string.complementary_feeding_6month_text_3);
        String htmlAsString4 = getString(R.string.complementary_feeding_6month_text_4);
        String htmlAsString5 = getString(R.string.complementary_feeding_6month_text_5);
        TextView comp_feeding_6month_textView_1 = (TextView) rootView.findViewById(R.id.complementary_feeding_6_month_textview_1);
        TextView comp_feeding_6month_textView_2 = (TextView) rootView.findViewById(R.id.complementary_feeding_6_month_textview_2);
        TextView comp_feeding_6month_textView_3 = (TextView) rootView.findViewById(R.id.complementary_feeding_6_month_textview_3);
        TextView comp_feeding_6month_textView_4 = (TextView) rootView.findViewById(R.id.complementary_feeding_6_month_textview_4);
        TextView comp_feeding_6month_textView_5 = (TextView) rootView.findViewById(R.id.complementary_feeding_6_month_textview_5);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        comp_feeding_6month_textView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        comp_feeding_6month_textView_2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        comp_feeding_6month_textView_3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        comp_feeding_6month_textView_4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        comp_feeding_6month_textView_5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));

        return rootView;
    }
}
