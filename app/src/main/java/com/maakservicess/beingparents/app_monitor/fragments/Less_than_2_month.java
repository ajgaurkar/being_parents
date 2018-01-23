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
 * Created by Amey on 5/22/2016.
 */
public class Less_than_2_month extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.danger_sign_less_than_two_month_fragment, container, false);
        String htmlAsString1 = getString(R.string.less_than_2Months_danger_sign_Text1);
        String htmlAsString2 = getString(R.string.less_than_2Months_danger_sign_with_diarrhea_Text2);
        String htmlAsString3 = getString(R.string.less_than_2Months_danger_sign_with_diarrhea_Text3);
        String htmlAsString4 = getString(R.string.less_than_2Months_danger_sign_with_diarrhea_Text4);
        String htmlAsString5 = getString(R.string.less_than_2Months_danger_sign_with_diarrhea_Text5);
        String htmlAsString6 = getString(R.string.less_than_2Months_danger_sign_with_diarrhea_Text6);
        TextView danger_textView1 = (TextView) rootView.findViewById(R.id.danger_TextView1);
        TextView danger_textView2 = (TextView) rootView.findViewById(R.id.danger_TextView2);
        TextView danger_textView3 = (TextView) rootView.findViewById(R.id.danger_TextView3);
        TextView danger_textView4 = (TextView) rootView.findViewById(R.id.danger_TextView4);
        TextView danger_textView5 = (TextView) rootView.findViewById(R.id.danger_TextView5);
        TextView danger_textView6 = (TextView) rootView.findViewById(R.id.danger_TextView6);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        danger_textView1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        danger_textView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        danger_textView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        danger_textView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        danger_textView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        danger_textView6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));

        return rootView;
    }

}