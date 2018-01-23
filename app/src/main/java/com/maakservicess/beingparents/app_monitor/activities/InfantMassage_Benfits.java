package com.maakservicess.beingparents.app_monitor.activities;

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
 * Created by Amey on 14-02-2017.
 */
public class InfantMassage_Benfits extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootView = inflater.inflate(R.layout.infant_massage_benfits, container, false);
        String htmlAsString1 = getString(R.string.guidance_infant_massagae_text1);
        String htmlAsString2 = getString(R.string.guidance_infant_massagae_text2);
        String htmlAsString3 = getString(R.string.guidance_infant_massagae_text3);
        String htmlAsString4 = getString(R.string.guidance_infant_massagae_text4);
        String htmlAsString5 = getString(R.string.guidance_infant_massagae_text5);
        String htmlAsString6 = getString(R.string.guidance_infant_massagae_text6);
        String htmlAsString7 = getString(R.string.guidance_infant_massagae_text7);
        String htmlAsString8 = getString(R.string.guidance_infant_massagae_text8);
        TextView infant_massage_textView1  = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView1);
        TextView infant_massage_textView2 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView2);
        TextView infant_massage_textView3 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView3);
        TextView infant_massage_textView4 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView4);
        TextView infant_massage_textView5 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView5);
        TextView infant_massage_textView6 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView6);
        TextView infant_massage_textView7 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView7);
        TextView infant_massage_textView8 = (TextView) rootView.findViewById(R.id.guidance_infnat_massage_textView8);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        infant_massage_textView1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        infant_massage_textView2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        infant_massage_textView3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        infant_massage_textView4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        infant_massage_textView5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        infant_massage_textView6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        infant_massage_textView7.setText(Html.fromHtml(htmlAsString7, null, htmlTagHandler));
        infant_massage_textView8.setText(Html.fromHtml(htmlAsString8, null, htmlTagHandler));
        return rootView;
    }
}
