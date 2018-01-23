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
public class Bathing_Special_Care extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.bathing_special_care, container, false);

        // get our html content
        String htmlAsString1 = getString(R.string.bathing_special_care_text);
        TextView bath_specialcare_TextView_1 = (TextView) rootView.findViewById(R.id.bath_specialcare_textView_1);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        bath_specialcare_TextView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        return rootView;
    }
}