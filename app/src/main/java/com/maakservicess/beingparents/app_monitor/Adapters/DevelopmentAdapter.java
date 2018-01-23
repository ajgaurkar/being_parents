package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.DevelopmentListData;

import java.util.List;

/**
 * Created by SAI PC on 3/5/2016.
 */
public class DevelopmentAdapter extends BaseAdapter {

    LayoutInflater inflater;
    Context context;
    List<DevelopmentListData> devList;

    public DevelopmentAdapter(Context context, List<DevelopmentListData> devList) {

        this.devList = devList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return devList.size();
    }

    @Override
    public DevelopmentListData getItem(int position) {
        return devList.get(position);
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
            view = this.inflater.inflate(R.layout.dev_list_item, parent, false);

            viewHolder.taskNameTextView = (TextView) view.findViewById(R.id.taskNameTexView);
            viewHolder.taskDateTextView = (TextView) view.findViewById(R.id.taskDateTextView);
            viewHolder.taskStatusTextView = (TextView) view.findViewById(R.id.taskStatusTextView);
            viewHolder.taskImgImgView = (ImageView) view.findViewById(R.id.taskImageView);

            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();

        }

        DevelopmentListData developmentListData = devList.get(position);

        viewHolder.taskNameTextView.setText(developmentListData.getTask());
        viewHolder.taskImgImgView.setImageResource(developmentListData.getImgId());

        if (developmentListData.getStatus().equals("Pending")) {
            viewHolder.taskDateTextView.setText(developmentListData.getRangeFrom() + " To " + developmentListData.getRangeTo());
            viewHolder.taskStatusTextView.setText("");
        }
        if (developmentListData.getStatus().equals("Late")) {
            viewHolder.taskDateTextView.setText("Completed on " + developmentListData.getCompletedOn());
            viewHolder.taskStatusTextView.setText(developmentListData.getStatus());
            viewHolder.taskStatusTextView.setTextColor(Color.parseColor("#009688"));
        }
        if (developmentListData.getStatus().equals("On time")) {
            viewHolder.taskDateTextView.setText("Completed on " + developmentListData.getCompletedOn());
            viewHolder.taskStatusTextView.setText(developmentListData.getStatus());
            viewHolder.taskStatusTextView.setTextColor(Color.parseColor("#009688"));
        }
        if (developmentListData.getStatus().equals("Early")) {
            viewHolder.taskDateTextView.setText("Completed on " + developmentListData.getCompletedOn());
            viewHolder.taskStatusTextView.setText(developmentListData.getStatus());
            viewHolder.taskStatusTextView.setTextColor(Color.parseColor("#009688"));
        }
        return view;
    }

    public class ViewHolder {
        private TextView taskDateTextView;
        private TextView taskNameTextView;
        private TextView taskStatusTextView;
        private ImageView taskImgImgView;

    }
}