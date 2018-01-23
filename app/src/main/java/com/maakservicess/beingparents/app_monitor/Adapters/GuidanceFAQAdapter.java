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
import com.maakservicess.beingparents.app_monitor.controllers.GuidanceFAQListData;

import java.util.List;

/**
 * Created by amey on 1/2/2017.
 */
public class GuidanceFAQAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<GuidanceFAQListData> guidanceFAQListDataList;


    public GuidanceFAQAdapter(Context context, List<GuidanceFAQListData> guidanceFAQListDataList) {
        this.inflater = LayoutInflater.from(context);
        this.context = context;
        this.guidanceFAQListDataList = guidanceFAQListDataList;
    }

    @Override
    public int getCount() {
        return guidanceFAQListDataList.size();
    }

    @Override
    public Object getItem(int position) {
        return guidanceFAQListDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.guidance_faq_list_item, parent, false);
            viewHolder.guidance_answer_answer_layout = (RelativeLayout) convertView.findViewById(R.id.guidance_faq_listitem_answer_layout);
            viewHolder.guidance_question_question_layout = (RelativeLayout) convertView.findViewById(R.id.guidance_faq_listitem_question_layout);
            viewHolder.guidance_faq_expandlist_status = (ImageView) convertView.findViewById(R.id.guidance_faq_expand_status_image_view);
            viewHolder.questionTextView = (TextView) convertView.findViewById(R.id.guidance_faq_questions_text_view);
            viewHolder.answerTextView = (TextView) convertView.findViewById(R.id.guidance_faq_listitem_answer_text_view);
            viewHolder.questinNoTextView = (TextView) convertView.findViewById(R.id.guidance_faq_questions_no_text_view);

            convertView.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GuidanceFAQListData guidanceFAQListData = guidanceFAQListDataList.get(position);

        if (guidanceFAQListData.getExpandStatus()) {
            viewHolder.guidance_answer_answer_layout.setVisibility(View.VISIBLE);
            viewHolder.guidance_faq_expandlist_status.setImageResource(R.drawable.minus_24);
            viewHolder.guidance_question_question_layout.setBackgroundResource(R.drawable.milestone_listitem_title_expanded);
        } else {
            viewHolder.guidance_answer_answer_layout.setVisibility(View.GONE);
            viewHolder.guidance_faq_expandlist_status.setImageResource(R.drawable.add_24);
            viewHolder.guidance_question_question_layout.setBackgroundResource(R.drawable.milestone_listitem_title_collapsed);

        }
        viewHolder.questinNoTextView.setText(String.valueOf(guidanceFAQListData.getQuestionNumber()) + " .");
        viewHolder.questionTextView.setText(" " + guidanceFAQListData.getGuidanceQuestions());
        viewHolder.answerTextView.setText(guidanceFAQListData.getGuidanceAnswers());
        return convertView;
    }


    public class ViewHolder {
        RelativeLayout guidance_question_question_layout;
        RelativeLayout guidance_answer_answer_layout;
        TextView questionTextView;
        TextView answerTextView;
        TextView questinNoTextView;
        ImageView guidance_faq_expandlist_status;


    }
}
