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
public class Brushing_0_2_Year extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.brushing_0_2_year, container, false);
        String htmlAsString1 = getString(R.string.bursh_upto2_text_1);
        String htmlAsString2 = getString(R.string.bursh_upto2_text_2);
        String htmlAsString3 = getString(R.string.bursh_upto2_text_3);
        String htmlAsString4 = getString(R.string.bursh_upto2_text_4);
        String htmlAsString5 = getString(R.string.bursh_upto2_text_5);
        TextView brushing_upto2year_textView1 = (TextView) rootView.findViewById(R.id.brushing_upto2year_TextView1);
        TextView brushing_upto2year_textView2 = (TextView) rootView.findViewById(R.id.brushing_upto2year_TextView2);
        TextView brushing_upto2year_textView3 = (TextView) rootView.findViewById(R.id.brushing_upto2year_TextView3);
        TextView brushing_upto2year_textView4 = (TextView) rootView.findViewById(R.id.brushing_upto2year_TextView4);
        TextView brushing_upto2year_textView5 = (TextView) rootView.findViewById(R.id.brushing_upto2year_TextView5);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        brushing_upto2year_textView1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        brushing_upto2year_textView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        brushing_upto2year_textView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        brushing_upto2year_textView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        brushing_upto2year_textView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        return rootView;
    }
}