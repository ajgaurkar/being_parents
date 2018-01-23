package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.CommonGrowthListData;

import java.util.List;

/**
 * Created by Amey on 5/1/2016.
 */
public class CommonGrowthAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<CommonGrowthListData> growthDataList;

    public CommonGrowthAdapter(Context context, List<CommonGrowthListData> growthDataList) {

        this.growthDataList = growthDataList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return growthDataList.size();
    }

    @Override
    public CommonGrowthListData getItem(int position) {
        return growthDataList.get(position);
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
            view = this.inflater.inflate(R.layout.growth_common_list, parent, false);
            viewHolder.userEnterDateTextView = (TextView) view.findViewById(R.id.enterdatetextview);
            viewHolder.entredHeightTextView = (TextView) view.findViewById(R.id.enterdatatextview);
            viewHolder.difference_btwn_data_TextView = (TextView) view.findViewById(R.id.difference_btwn_data);
            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();

        }

        CommonGrowthListData commonGrowthListData = growthDataList.get(position);
        System.out.println("Counttttt " + commonGrowthListData.getCounteLoop());
        System.out.println("getCategory " + commonGrowthListData.getCategory());
        String temp_category = commonGrowthListData.getCategory();
        temp_category = temp_category.substring(0, temp_category.length() - 1).trim();
        System.out.println("temp_category " + temp_category);
        float temp_diff = commonGrowthListData.getCalculateDiff();


        if (temp_category.equals("hc")) {
            if (commonGrowthListData.getCounteLoop() == 0) {

                viewHolder.entredHeightTextView.setText(commonGrowthListData.getEnterData());
                viewHolder.userEnterDateTextView.setText(commonGrowthListData.getEnterDate());
                viewHolder.difference_btwn_data_TextView.setText("");

            } else {
                if (temp_diff < 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Cm";
                    System.out.println("Hc < 0*****" + temp);
                    viewHolder.difference_btwn_data_TextView.setText("-" + temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.RED);
                } else if (temp_diff > 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Cm";
                    System.out.println("Hc > 0*****" + temp);
                    viewHolder.difference_btwn_data_TextView.setText("+" + temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.GREEN);
                } else if (temp_diff == 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Cm-hc";
                    viewHolder.difference_btwn_data_TextView.setText(temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.BLUE);

                }
            }


        } else if (temp_category.equals("w")) {

            if (commonGrowthListData.getCounteLoop() == 0) {

                viewHolder.entredHeightTextView.setText(commonGrowthListData.getEnterData());
                viewHolder.userEnterDateTextView.setText(commonGrowthListData.getEnterDate());
                viewHolder.difference_btwn_data_TextView.setText("");

            } else {
                if (temp_diff < 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Kg";
                    System.out.println("Weight < 0*****" + temp);
                    viewHolder.difference_btwn_data_TextView.setText("-" + temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.RED);
                } else if (temp_diff > 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Kg";
                    System.out.println("Weight > 0*****" + temp);
                    viewHolder.difference_btwn_data_TextView.setText("+" + temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.GREEN);

                } else if (temp_diff == 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Kg-w";
                    viewHolder.difference_btwn_data_TextView.setText(temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.BLUE);

                }
            }

        } else if (temp_category.equals("h")) {
            if (commonGrowthListData.getCounteLoop() == 0) {

                viewHolder.entredHeightTextView.setText(commonGrowthListData.getEnterData());
                viewHolder.userEnterDateTextView.setText(commonGrowthListData.getEnterDate());
                viewHolder.difference_btwn_data_TextView.setText("");

            } else {
                if (temp_diff < 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Cm";
                    System.out.println("Height <  0*****" + temp);
                    viewHolder.difference_btwn_data_TextView.setText("-" + temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.RED);
                } else if (temp_diff > 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Cm";
                    System.out.println("Height > 0 *****" + temp);
                    viewHolder.difference_btwn_data_TextView.setText("+" + temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.GREEN);
                } else if (temp_diff == 0) {
                    String temp = Float.toString(commonGrowthListData.getCalculateDiff());
                    temp = temp + "Cm-h";
                    viewHolder.difference_btwn_data_TextView.setText(temp);
                    viewHolder.difference_btwn_data_TextView.setTextColor(Color.BLUE);

                }


            }

        }

        viewHolder.entredHeightTextView.setText(commonGrowthListData.getEnterData());
        viewHolder.userEnterDateTextView.setText(commonGrowthListData.getEnterDate());


        return view;
    }

    public class ViewHolder {
        private TextView userEnterDateTextView;
        private TextView entredHeightTextView;
        private TextView difference_btwn_data_TextView;


    }
}
