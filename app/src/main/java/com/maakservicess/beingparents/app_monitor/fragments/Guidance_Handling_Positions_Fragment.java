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
 * Created by SAI PC on 3/27/2016.
 */
public class Guidance_Handling_Positions_Fragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.guidance_positions, container, false);
        String htmlAsString1 = getString(R.string.newborn_handling_postion_text_1);
        String htmlAsString2 = getString(R.string.newborn_handling_postion_text_2);
        String htmlAsString3 = getString(R.string.newborn_handling_postion_text_3);
        String htmlAsString4 = getString(R.string.newborn_handling_postion_text_4);
        String htmlAsString5 = getString(R.string.newborn_handling_postion_text_5);
        String htmlAsString6 = getString(R.string.newborn_handling_postion_text_6);
        String htmlAsString7 = getString(R.string.newborn_handling_postion_text_7);
        String htmlAsString8 = getString(R.string.newborn_handling_postion_text_8);
        String htmlAsString9 = getString(R.string.newborn_handling_postion_text_9);
        String htmlAsString10 = getString(R.string.newborn_handling_postion_text_10);
        String htmlAsString11 = getString(R.string.newborn_handling_postion_text_11);
        TextView position_textView1 = (TextView) rootView.findViewById(R.id.position_TextView1);
        TextView position_textView2 = (TextView) rootView.findViewById(R.id.position_TextView2);
        TextView position_textView3 = (TextView) rootView.findViewById(R.id.position_TextView3);
        TextView position_textView4 = (TextView) rootView.findViewById(R.id.position_TextView4);
        TextView position_textView5 = (TextView) rootView.findViewById(R.id.position_TextView5);
        TextView position_textView6 = (TextView) rootView.findViewById(R.id.position_TextView6);
        TextView position_textView7 = (TextView) rootView.findViewById(R.id.position_TextView7);
        TextView position_textView8 = (TextView) rootView.findViewById(R.id.position_TextView8);
        TextView position_textView9 = (TextView) rootView.findViewById(R.id.position_TextView9);
        TextView position_textView10 = (TextView) rootView.findViewById(R.id.position_TextView10);
        TextView position_textView11 = (TextView) rootView.findViewById(R.id.position_TextView11);
        TextView position_textView12 = (TextView) rootView.findViewById(R.id.position_TextView12);
        TextView position_textView13 = (TextView) rootView.findViewById(R.id.position_TextView13);
       // TextView position_textView14 = (TextView) rootView.findViewById(R.id.position_TextView14);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        position_textView1.setText("Snuggle Hold :");
        position_textView2.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        position_textView3.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        position_textView4.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        position_textView5.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        position_textView6.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        position_textView7.setText("Face-to-Face Hold :");
        position_textView8.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        position_textView9.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        position_textView10.setText("Cardle Hold :");
        position_textView11.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));
        position_textView12.setText(Html.fromHtml(htmlAsString9, null, htmlTagHandler));
        position_textView13.setText(Html.fromHtml(htmlAsString10, null, htmlTagHandler));
     //   position_textView14.setText(Html.fromHtml(htmlAsString11, null, htmlTagHandler));
        return rootView;
    }
}