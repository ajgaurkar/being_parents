package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.MilestoneListData;

import java.util.List;

/**
 * Created by SAI PC on 4/3/2016.
 */
public class MilestonesAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<MilestoneListData> milestoneList;

    public MilestonesAdapter(Context context, List<MilestoneListData> milestoneList) {

        this.milestoneList = milestoneList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return milestoneList.size();
    }

    @Override
    public MilestoneListData getItem(int position) {
        return milestoneList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        ViewHolder viewHolder;
        if (view == null) {
            viewHolder = new ViewHolder();
            view = this.inflater.inflate(R.layout.milestone_list_item, parent, false);

            viewHolder.ageTextView = (TextView) view.findViewById(R.id.milestone_listitem_age_text_view);
            viewHolder.motorTextView = (TextView) view.findViewById(R.id.milestone_listitem_motor_text_view);
            viewHolder.adaptiveTextView = (TextView) view.findViewById(R.id.milestone_listitem_adaptive_text_view);
            viewHolder.socialTextView = (TextView) view.findViewById(R.id.milestone_listitem_social_text_view);
            viewHolder.languageTextView = (TextView) view.findViewById(R.id.milestone_listitem_language_text_view);
            viewHolder.visionAndHearingTextView = (TextView) view.findViewById(R.id.milestone_listitem_vision_hearing_text_view);
            viewHolder.currentAgeTextView = (TextView) view.findViewById(R.id.milestone_current_age_textView);
            viewHolder.expandStatusImageView = (ImageView) view.findViewById(R.id.milestone_listitem_expand_status_image_view);


            viewHolder.motorTextViewHeader = (TextView) view.findViewById(R.id.milestone_listitem_motor_text_view_header);
            viewHolder.adaptiveTextViewHeader = (TextView) view.findViewById(R.id.milestone_listitem_adaptive_text_view_header);
            viewHolder.socialTextViewHeader = (TextView) view.findViewById(R.id.milestone_listitem_social_text_view_header);
            viewHolder.languageTextViewHeader = (TextView) view.findViewById(R.id.milestone_listitem_language_text_view_header);
            viewHolder.visionAndHearingTextViewHeader = (TextView) view.findViewById(R.id.milestone_listitem_vision_hearing_text_view_header);

            viewHolder.headerLayout = (RelativeLayout) view.findViewById(R.id.milestone_listitem_header_layout);
            viewHolder.bodyLayout = (RelativeLayout) view.findViewById(R.id.milestone_listitem_body_layout);

            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();

        }

        MilestoneListData milestoneListData = milestoneList.get(position);

//        showing currrent range item status
        if (milestoneListData.getMileCurrentRangeStatus()) {
            viewHolder.currentAgeTextView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.currentAgeTextView.setVisibility(View.INVISIBLE);
        }

        if (milestoneListData.getExpandStatus()) {
            viewHolder.bodyLayout.setVisibility(View.VISIBLE);
            viewHolder.expandStatusImageView.setImageResource(R.drawable.minus_24);
            viewHolder.headerLayout.setBackgroundResource(R.drawable.milestone_listitem_title_expanded);
        } else {
            viewHolder.expandStatusImageView.setImageResource(R.drawable.add_24);
            viewHolder.bodyLayout.setVisibility(View.GONE);
            viewHolder.headerLayout.setBackgroundResource(R.drawable.milestone_listitem_title_collapsed);
        }

        if (milestoneListData.getMileAge() == 1) {
            viewHolder.ageTextView.setText(String.valueOf(milestoneListData.getMileAge()) + " Month");
        } else {
            viewHolder.ageTextView.setText(String.valueOf(milestoneListData.getMileAge()) + " Months");
        }
        if (milestoneListData.getMileMotorDev() == null) {
            viewHolder.motorTextView.setVisibility(View.GONE);
            viewHolder.motorTextViewHeader.setVisibility(View.GONE);
        } else {
            viewHolder.motorTextView.setVisibility(View.VISIBLE);
            viewHolder.motorTextViewHeader.setVisibility(View.VISIBLE);
            viewHolder.motorTextView.setText(milestoneListData.getMileMotorDev());
        }

        if (milestoneListData.getMileAdaptive() == null) {
            viewHolder.adaptiveTextView.setVisibility(View.GONE);
            viewHolder.adaptiveTextViewHeader.setVisibility(View.GONE);
        } else {
            viewHolder.adaptiveTextView.setVisibility(View.VISIBLE);
            viewHolder.adaptiveTextViewHeader.setVisibility(View.VISIBLE);
            viewHolder.adaptiveTextView.setText(milestoneListData.getMileAdaptive());
        }

        if (milestoneListData.getMileSocial() == null) {
            viewHolder.socialTextView.setVisibility(View.GONE);
            viewHolder.socialTextViewHeader.setVisibility(View.GONE);
        } else {
            viewHolder.socialTextView.setVisibility(View.VISIBLE);
            viewHolder.socialTextViewHeader.setVisibility(View.VISIBLE);
            viewHolder.socialTextView.setText(milestoneListData.getMileSocial());
        }

        if (milestoneListData.getMileLanguage() == null) {
            viewHolder.languageTextView.setVisibility(View.GONE);
            viewHolder.languageTextViewHeader.setVisibility(View.GONE);
        } else {
            viewHolder.languageTextView.setVisibility(View.VISIBLE);
            viewHolder.languageTextViewHeader.setVisibility(View.VISIBLE);
            viewHolder.languageTextView.setText(milestoneListData.getMileLanguage());
        }

        if (milestoneListData.getMileVisionHearing() == null) {
            viewHolder.visionAndHearingTextView.setVisibility(View.GONE);
            viewHolder.visionAndHearingTextViewHeader.setVisibility(View.GONE);
        } else {
            viewHolder.visionAndHearingTextView.setVisibility(View.VISIBLE);
            viewHolder.visionAndHearingTextViewHeader.setVisibility(View.VISIBLE);
            viewHolder.visionAndHearingTextView.setText(milestoneListData.getMileVisionHearing());
        }

        return view;
    }

    public class ViewHolder {
        private TextView ageTextView;

        private TextView motorTextView;
        private TextView motorTextViewHeader;
        private TextView adaptiveTextView;
        private TextView adaptiveTextViewHeader;
        private TextView languageTextView;
        private TextView languageTextViewHeader;
        private TextView socialTextView;
        private TextView socialTextViewHeader;
        private TextView visionAndHearingTextView;
        private TextView visionAndHearingTextViewHeader;
        private TextView currentAgeTextView;

        private ImageView expandStatusImageView;

        private RelativeLayout headerLayout;
        private RelativeLayout bodyLayout;


    }
}