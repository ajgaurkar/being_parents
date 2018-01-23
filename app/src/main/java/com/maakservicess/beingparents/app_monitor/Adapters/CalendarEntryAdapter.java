package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.CalendarEntryListData;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SAI PC on 4/3/2016.
 */
public class CalendarEntryAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<CalendarEntryListData> calendarList;
    DateFormat srcFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    DateFormat trgtFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
    Date date = null;
    String entryDate = null;

    public CalendarEntryAdapter(Context context, List<CalendarEntryListData> calendarList) {

        this.calendarList = calendarList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return calendarList.size();
    }

    @Override
    public CalendarEntryListData getItem(int position) {
        return calendarList.get(position);
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
            view = this.inflater.inflate(R.layout.calendar_list_item, parent, false);

            viewHolder.categoryColorView = (View) view.findViewById(R.id.calendarListItemCategoryView);
            viewHolder.calendarDateTextView = (TextView) view.findViewById(R.id.calendarListItemDateTextView);
            viewHolder.calendarTitleTextView = (TextView) view.findViewById(R.id.calendarListItemTitleTextView);
            viewHolder.calendarAttachmentCountTextView = (TextView) view.findViewById(R.id.calendarListItemImgCountTextView);
            viewHolder.calendarAttachmentCountImageView = (ImageView) view.findViewById(R.id.calendarListItemImgCountImageView);

            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();

        }

        CalendarEntryListData calendarEntryListData = calendarList.get(position);

        date = null;
        entryDate = "";
        try {
            date = srcFormat.parse(calendarEntryListData.getEntryDateDay()
                    + " " + calendarEntryListData.getEntryDateMonth()
                    + " " + calendarEntryListData.getEntryDateYear());

            entryDate = trgtFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.calendarDateTextView.setText(entryDate);
        viewHolder.calendarTitleTextView.setText(calendarEntryListData.getTitle());


        System.out.println("adapter img count : " + String.valueOf(calendarEntryListData.getImgCount()));
        if (calendarEntryListData.getImgCount() == 0) {
            viewHolder.calendarAttachmentCountTextView.setVisibility(View.INVISIBLE);
            viewHolder.calendarAttachmentCountImageView.setVisibility(View.INVISIBLE);
        } else {
            viewHolder.calendarAttachmentCountTextView.setVisibility(View.VISIBLE);
            viewHolder.calendarAttachmentCountImageView.setVisibility(View.VISIBLE);
            viewHolder.calendarAttachmentCountTextView.setText(String.valueOf(calendarEntryListData.getImgCount()));

        }

        if (calendarEntryListData.getCategory().equals("Visit")) {
            viewHolder.categoryColorView.setBackgroundResource(R.color.normalRed);
        } else {
            viewHolder.categoryColorView.setBackgroundResource(R.color.normalBlue);
        }

        //setting type face dynamically. Issues in setting through XML
        viewHolder.calendarTitleTextView.setTypeface(viewHolder.calendarTitleTextView.getTypeface(), Typeface.BOLD);
        viewHolder.calendarAttachmentCountTextView.setTypeface(viewHolder.calendarAttachmentCountTextView.getTypeface(), Typeface.BOLD);

        return view;
    }

    public class ViewHolder {
        private View categoryColorView;
        private TextView calendarDateTextView;
        private TextView calendarTitleTextView;
        private TextView calendarAttachmentCountTextView;
        private ImageView calendarAttachmentCountImageView;

    }
}