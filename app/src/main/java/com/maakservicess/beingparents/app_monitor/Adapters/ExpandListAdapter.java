package com.maakservicess.beingparents.app_monitor.Adapters;

/**
 * Created by SAI PC on 1/26/2016.
 */

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineChildItem;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineGroupItem;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<VaccineGroupItem> groups;

    public ExpandListAdapter(Context context, ArrayList<VaccineGroupItem> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<VaccineChildItem> chList = groups.get(groupPosition).getItems();
        return chList.get(childPosition);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        VaccineChildItem child = (VaccineChildItem) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.vaccine_child_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.vaccineNameTextView);
        TextView taken = (TextView) convertView.findViewById(R.id.vaccineDueDateTextView);
        TextView due = (TextView) convertView.findViewById(R.id.vaccineTakendateTextView);
        ImageView iv = (ImageView) convertView.findViewById(R.id.vaccineStatusIconImageView);

        tv.setText(child.getName());
        iv.setImageResource(child.getImage());
        due.setText(child.getDuedt());
        if (child.getVaccineStatus().equals("Pending")) {
            taken.setText("Pending");
        } else {
            taken.setText(child.getTakendate());
        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        ArrayList<VaccineChildItem> chList = groups.get(groupPosition).getItems();
        return chList.size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        VaccineGroupItem group = (VaccineGroupItem) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = (LayoutInflater) context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = inf.inflate(R.layout.vaccine_group_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.group_name);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

}