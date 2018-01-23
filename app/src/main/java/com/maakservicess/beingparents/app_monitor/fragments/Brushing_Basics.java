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
public class Brushing_Basics extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.brushing_basics, container, false);
        String htmlAsString1 = getString(R.string.bursh_basic_text_1);
        String htmlAsString2 = getString(R.string.bursh_basic_text_2);
        String htmlAsString3 = getString(R.string.bursh_basic_text_3);
        String htmlAsString4 = getString(R.string.bursh_basic_text_4);
        String htmlAsString5 = getString(R.string.bursh_basic_text_5);
        String htmlAsString6 = getString(R.string.bursh_basic_text_6);
        TextView brushing_textView1 = (TextView) rootView.findViewById(R.id.brushing_TextView1);
        TextView brushing_textView2 = (TextView) rootView.findViewById(R.id.brushing_TextView2);
        TextView brushing_textView3 = (TextView) rootView.findViewById(R.id.brushing_TextView3);
        TextView brushing_textView4 = (TextView) rootView.findViewById(R.id.brushing_TextView4);
        TextView brushing_textView5 = (TextView) rootView.findViewById(R.id.brushing_TextView5);
        TextView brushing_textView6 = (TextView) rootView.findViewById(R.id.brushing_TextView6);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        brushing_textView1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        brushing_textView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        brushing_textView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        brushing_textView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        brushing_textView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        brushing_textView6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        return rootView;
    }
}