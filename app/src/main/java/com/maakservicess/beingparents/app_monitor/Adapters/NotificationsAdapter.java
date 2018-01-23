package com.maakservicess.beingparents.app_monitor.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.NotificationsListData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SAI PC on 4/3/2016.
 */
public class NotificationsAdapter extends BaseAdapter {
    LayoutInflater inflater;
    Context context;
    List<NotificationsListData> notificationList;
    DateFormat srcFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
    DateFormat trgtFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());

    public NotificationsAdapter(Context context, List<NotificationsListData> notificationList) {

        this.notificationList = notificationList;
        this.context = context;
        this.inflater = LayoutInflater.from(context);

    }

    @Override
    public int getCount() {
        return notificationList.size();
    }

    @Override
    public NotificationsListData getItem(int position) {
        return notificationList.get(position);
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
            view = this.inflater.inflate(R.layout.notification_list_item, parent, false);

            viewHolder.notificationReadStatusView = (View) view.findViewById(R.id.notificationItemReadStatusView);
            viewHolder.notificationHeaderTextView = (TextView) view.findViewById(R.id.notificationItemHeaderTextView);
            viewHolder.notificationTimestampTextView = (TextView) view.findViewById(R.id.notificationItemTimestampTextView);
            viewHolder.notificationDetailsTextView = (TextView) view.findViewById(R.id.notificationItemDesxriptionTextView);
            viewHolder.notificationTypeImageView = (ImageView) view.findViewById(R.id.notificationListItemTypeImageView);

            view.setTag(viewHolder);
        } else {

            viewHolder = (ViewHolder) view.getTag();

        }

        NotificationsListData notificationsListData = notificationList.get(position);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(notificationsListData.getNotifyTimeStamp());

        Date date = calendar.getTime();

        DateFormat format = new SimpleDateFormat("dd MMM yy HH:mm", Locale.getDefault());
        String timeStampString = format.format(date);

        viewHolder.notificationHeaderTextView.setText(notificationsListData.getNotificationHeader());
        viewHolder.notificationDetailsTextView.setText(notificationsListData.getNotificationDescription());
        viewHolder.notificationTimestampTextView.setText(timeStampString);
        viewHolder.notificationTypeImageView.setImageResource(notificationsListData.getImgId());

        System.out.println("notificationsListData.getReadStatus() : " + notificationsListData.getReadStatus());

//        if (notificationsListData.getReadStatus() == 1) {
//            System.out.println("inside getReadStatus 1 ");
//            viewHolder.notificationReadStatusView.setVisibility(View.INVISIBLE);
//        }
//        if (notificationsListData.getReadStatus() == 0) {
//            System.out.println("inside getReadStatus 0 ");
//            viewHolder.notificationReadStatusView.setVisibility(View.VISIBLE);
//        }
        //REMOVE THIS LINE LATER
//        viewHolder.notificationReadStatusView.setVisibility(View.VISIBLE);

        return view;
    }

    public class ViewHolder {
        private TextView notificationHeaderTextView;
        private TextView notificationDetailsTextView;
        private TextView notificationTimestampTextView;
        private ImageView notificationTypeImageView;
        private View notificationReadStatusView;

    }
}