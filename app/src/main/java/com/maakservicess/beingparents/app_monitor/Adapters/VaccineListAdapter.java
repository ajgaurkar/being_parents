package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineListData;

import java.util.List;

/**
 * Created by SAI PC on 1/18/2016.
 */
public class VaccineListAdapter extends BaseAdapter {

    protected List<VaccineListData> vaccineList;
    Context context;
    LayoutInflater inflater;

    public VaccineListAdapter(Context context, List<VaccineListData> vaccineList) {
        this.vaccineList = vaccineList;
        this.inflater = LayoutInflater.from(context);
        this.context = context;
    }

    public int getCount() {
        return vaccineList.size();
    }

    public VaccineListData getItem(int position) {
        return vaccineList.get(position);
    }

    public long getItemId(int position) {
        return vaccineList.get(position).getNullId();
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
//        if (user.equals("Teacher")) {
//        }
//        if (user.equals("Student")) {
//        }
        if (convertView == null) {

            holder = new ViewHolder();
            convertView = this.inflater.inflate(R.layout.vaccine_list_item, parent, false);

            holder.vaccineDate = (TextView) convertView.findViewById(R.id.vaccineDateTextView);
            holder.vaccineText = (TextView) convertView.findViewById(R.id.vaccineTextTextView);
            holder.vaccineName = (TextView) convertView.findViewById(R.id.vaccineNameTextView);
//            holder.VaccineStatus = (TextView) convertView.findViewById(R.id.gallery_tag_field);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        VaccineListData vaccineListData = vaccineList.get(position);
        holder.vaccineDate.setText(vaccineListData.getVaccineText());
        holder.vaccineText.setText(vaccineListData.getVaccineText());
        holder.vaccineName.setText(vaccineListData.getVaccineName());
//        holder.VaccineStatus.setText(VaccineListData.getTag());

        return convertView;
    }

    private class ViewHolder {
        TextView vaccineDate;
        TextView vaccineText;
        TextView vaccineName;
        ImageView VaccineStatus;

    }

}
