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
public class Feeding_Complementary_7_8_months extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.complementary_feeding_7_8month, container, false);
        String htmlAsString1 = getString(R.string.complementary_feeding_7_8month_text_1);
        String htmlAsString2 = getString(R.string.complementary_feeding_7_8month_text_2);
        String htmlAsString3 = getString(R.string.complementary_feeding_7_8month_text_3);
        String htmlAsString4 = getString(R.string.complementary_feeding_7_8month_text_4);
        String htmlAsString5 = getString(R.string.complementary_feeding_7_8month_text_5);
        String htmlAsString6 = getString(R.string.complementary_feeding_7_8month_text_6);
        TextView comp_feeding_6month_textView_1 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_1);
        TextView comp_feeding_6month_textView_2 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_2);
        TextView comp_feeding_6month_textView_3 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_3);
        TextView comp_feeding_6month_textView_4 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_4);
        TextView comp_feeding_6month_textView_5 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_5);
        TextView comp_feeding_6month_textView_6 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_6);
        TextView comp_feeding_6month_textView_7 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_7);
        TextView comp_feeding_6month_textView_8 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_8);
        TextView comp_feeding_6month_textView_9 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_9);
        TextView comp_feeding_6month_textView_10 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_10);
        TextView comp_feeding_6month_textView_11 = (TextView) rootView.findViewById(R.id.complementary_feeding_7_8month_textview_11);
        HtmlTagHandler htmlTagHandler = new HtmlTagHandler();
        comp_feeding_6month_textView_1.setText(Html.fromHtml(htmlAsString1, null, htmlTagHandler));
        comp_feeding_6month_textView_2.setText(Html.fromHtml(htmlAsString2, null, htmlTagHandler));
        comp_feeding_6month_textView_3.setText(Html.fromHtml(htmlAsString3, null, htmlTagHandler));
        comp_feeding_6month_textView_4.setText(Html.fromHtml(htmlAsString4, null, htmlTagHandler));
        comp_feeding_6month_textView_5.setText(Html.fromHtml(htmlAsString5, null, htmlTagHandler));
        comp_feeding_6month_textView_6.setText(Html.fromHtml(htmlAsString6, null, htmlTagHandler));
        comp_feeding_6month_textView_7.setText("Increase feeding to 3 times per day upto half bowl at each meal");
        comp_feeding_6month_textView_8.setText("Start with 1 teaspoon and work up to ¼ to ½ cup- split into 3 feedings.");
        comp_feeding_6month_textView_9.setText("Start with 1 teaspoon and work up to ¼ to ½ cup- split into 3 feedings.");
        comp_feeding_6month_textView_10.setText("Start with 3 tablespoons per day and work up to 7 to 8 tablespoons -split into 3 feedings.");
        comp_feeding_6month_textView_11.setText("Continue to introduce only one new food every time and feed only that food over a few days to watch for food intolerances or allergies.");
        return rootView;
    }
}
