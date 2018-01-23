package com.maakservicess.beingparents.app_monitor;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.TimeZone;

import static android.content.Context.ALARM_SERVICE;

/**
 * Created by SAI PC on 9/17/2016.
 */
public class AddNotificationDates {

    String babyDateOfBirth;
    Long timeOfDay;
    private Calendar calendar_timeOfDay;
    AlarmManager alarmManager;
    Context context;
    Intent intent;
    Bundle bundle;

    ArrayList<Long> datesArrayList = new ArrayList<>();

    PendingIntent pendingIntent;
    int i = 1;

    public void setNotifications(Context context, String dateOfBirth, long timeOfDay) {
        this.babyDateOfBirth = dateOfBirth;
        this.timeOfDay = timeOfDay;
        this.context = context;
        alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        intent = new Intent(context, AlarmReceiver.class);


        calendar_timeOfDay = Calendar.getInstance();
        calendar_timeOfDay.setTimeZone(TimeZone.getTimeZone("GMT"));
        calendar_timeOfDay.setTimeInMillis(timeOfDay);
        System.out.println("calendar_timeOfDay.get(Calendar.HOUR) : " + calendar_timeOfDay.get(Calendar.HOUR));
        System.out.println("calendar_timeOfDay.get(Calendar.MINUTE) : " + calendar_timeOfDay.get(Calendar.MINUTE));

//        //BIRTHDAYS
        datesArrayList.add(0, getMillisecondsForDateTime(0, 0, 0, 1));
        datesArrayList.add(1, getMillisecondsForDateTime(0, 0, 0, 2));
        datesArrayList.add(2, getMillisecondsForDateTime(0, 0, 0, 3));
        datesArrayList.add(3, getMillisecondsForDateTime(0, 0, 0, 4));
        datesArrayList.add(4, getMillisecondsForDateTime(0, 0, 0, 5));
        datesArrayList.add(5, getMillisecondsForDateTime(0, 0, 0, 6));
        datesArrayList.add(6, getMillisecondsForDateTime(0, 0, 0, 7));
        datesArrayList.add(7, getMillisecondsForDateTime(0, 0, 0, 8));
        datesArrayList.add(8, getMillisecondsForDateTime(0, 0, 0, 9));
        datesArrayList.add(9, getMillisecondsForDateTime(0, 0, 0, 10));
        datesArrayList.add(10, getMillisecondsForDateTime(0, 0, 0, 11));
        datesArrayList.add(11, getMillisecondsForDateTime(0, 0, 0, 12));
//
//        //GROWTH : EVERY MONTH
        datesArrayList.add(12, getMillisecondsForDateTime(1, 0, 1, 0));
        datesArrayList.add(13, getMillisecondsForDateTime(1, 0, 2, 0));
        datesArrayList.add(14, getMillisecondsForDateTime(1, 0, 3, 0));
        datesArrayList.add(15, getMillisecondsForDateTime(1, 0, 4, 0));
        datesArrayList.add(16, getMillisecondsForDateTime(1, 0, 5, 0));
        datesArrayList.add(17, getMillisecondsForDateTime(1, 0, 6, 0));
        datesArrayList.add(18, getMillisecondsForDateTime(1, 0, 7, 0));
        datesArrayList.add(19, getMillisecondsForDateTime(1, 0, 8, 0));
        datesArrayList.add(20, getMillisecondsForDateTime(1, 0, 9, 0));
        datesArrayList.add(21, getMillisecondsForDateTime(1, 0, 10, 0));
        datesArrayList.add(22, getMillisecondsForDateTime(1, 0, 11, 0));
        datesArrayList.add(23, getMillisecondsForDateTime(1, 0, 0, 1));

        datesArrayList.add(24, getMillisecondsForDateTime(1, 0, 1, 1));
        datesArrayList.add(25, getMillisecondsForDateTime(1, 0, 2, 1));
        datesArrayList.add(26, getMillisecondsForDateTime(1, 0, 3, 1));
        datesArrayList.add(27, getMillisecondsForDateTime(1, 0, 4, 1));
        datesArrayList.add(28, getMillisecondsForDateTime(1, 0, 5, 1));
        datesArrayList.add(29, getMillisecondsForDateTime(1, 0, 6, 1));
        datesArrayList.add(30, getMillisecondsForDateTime(1, 0, 7, 1));
        datesArrayList.add(31, getMillisecondsForDateTime(1, 0, 8, 1));
        datesArrayList.add(32, getMillisecondsForDateTime(1, 0, 9, 1));
        datesArrayList.add(33, getMillisecondsForDateTime(1, 0, 10, 1));
        datesArrayList.add(34, getMillisecondsForDateTime(1, 0, 11, 1));
        datesArrayList.add(35, getMillisecondsForDateTime(1, 0, 0, 2));

        datesArrayList.add(36, getMillisecondsForDateTime(1, 0, 1, 2));
        datesArrayList.add(37, getMillisecondsForDateTime(1, 0, 2, 2));
        datesArrayList.add(38, getMillisecondsForDateTime(1, 0, 3, 2));
        datesArrayList.add(39, getMillisecondsForDateTime(1, 0, 4, 2));
        datesArrayList.add(40, getMillisecondsForDateTime(1, 0, 5, 2));
        datesArrayList.add(41, getMillisecondsForDateTime(1, 0, 6, 2));
        datesArrayList.add(42, getMillisecondsForDateTime(1, 0, 7, 2));
        datesArrayList.add(42, getMillisecondsForDateTime(1, 0, 8, 2));
        datesArrayList.add(44, getMillisecondsForDateTime(1, 0, 9, 2));
        datesArrayList.add(45, getMillisecondsForDateTime(1, 0, 10, 2));
        datesArrayList.add(46, getMillisecondsForDateTime(1, 0, 11, 2));
        datesArrayList.add(47, getMillisecondsForDateTime(1, 0, 0, 3));

        datesArrayList.add(48, getMillisecondsForDateTime(1, 0, 1, 3));
        datesArrayList.add(49, getMillisecondsForDateTime(1, 0, 2, 3));
        datesArrayList.add(50, getMillisecondsForDateTime(1, 0, 3, 3));
        datesArrayList.add(51, getMillisecondsForDateTime(1, 0, 4, 3));
        datesArrayList.add(52, getMillisecondsForDateTime(1, 0, 5, 3));
        datesArrayList.add(53, getMillisecondsForDateTime(1, 0, 6, 3));
        datesArrayList.add(54, getMillisecondsForDateTime(1, 0, 7, 3));
        datesArrayList.add(55, getMillisecondsForDateTime(1, 0, 8, 3));
        datesArrayList.add(56, getMillisecondsForDateTime(1, 0, 9, 3));
        datesArrayList.add(57, getMillisecondsForDateTime(1, 0, 10, 3));
        datesArrayList.add(58, getMillisecondsForDateTime(1, 0, 11, 3));
        datesArrayList.add(59, getMillisecondsForDateTime(1, 0, 0, 4));

        datesArrayList.add(60, getMillisecondsForDateTime(1, 0, 1, 4));
        datesArrayList.add(61, getMillisecondsForDateTime(1, 0, 2, 4));
        datesArrayList.add(62, getMillisecondsForDateTime(1, 0, 3, 4));
        datesArrayList.add(63, getMillisecondsForDateTime(1, 0, 4, 4));
        datesArrayList.add(64, getMillisecondsForDateTime(1, 0, 5, 4));
        datesArrayList.add(65, getMillisecondsForDateTime(1, 0, 6, 4));
        datesArrayList.add(66, getMillisecondsForDateTime(1, 0, 7, 4));
        datesArrayList.add(67, getMillisecondsForDateTime(1, 0, 8, 4));
        datesArrayList.add(68, getMillisecondsForDateTime(1, 0, 9, 4));
        datesArrayList.add(69, getMillisecondsForDateTime(1, 0, 10, 4));
        datesArrayList.add(70, getMillisecondsForDateTime(1, 0, 11, 4));
        datesArrayList.add(71, getMillisecondsForDateTime(1, 0, 0, 5));
//
////        VACCINE : 14 ENTRIES ON MONTHS START AND SOME OTHER FWD DATES
        datesArrayList.add(72, getMillisecondsForDateTime(5, 0, 0, 0));
        datesArrayList.add(73, getMillisecondsForDateTime(0, 6, 0, 0));
        datesArrayList.add(74, getMillisecondsForDateTime(0, 10, 0, 0));
        datesArrayList.add(75, getMillisecondsForDateTime(0, 14, 0, 0));
        datesArrayList.add(76, getMillisecondsForDateTime(0, 0, 6, 0));
        datesArrayList.add(77, getMillisecondsForDateTime(0, 0, 9, 0));
        datesArrayList.add(78, getMillisecondsForDateTime(2, 0, 0, 1));
        datesArrayList.add(79, getMillisecondsForDateTime(2, 0, 3, 1));
        datesArrayList.add(80, getMillisecondsForDateTime(2, 0, 4, 1));
        datesArrayList.add(81, getMillisecondsForDateTime(2, 0, 6, 1));
        datesArrayList.add(82, getMillisecondsForDateTime(1, 0, 0, 2));
        datesArrayList.add(83, getMillisecondsForDateTime(1, 0, 0, 4));
        datesArrayList.add(84, getMillisecondsForDateTime(1, 0, 0, 10));

        //DEVELOPMENT : 17 ENTRIES FOR START TIME
        datesArrayList.add(85, getMillisecondsForDateTime(0, 1, 0, 0));
        datesArrayList.add(86, getMillisecondsForDateTime(0, 1, 1, 0));
        datesArrayList.add(87, getMillisecondsForDateTime(0, 1, 1, 0));
        datesArrayList.add(88, getMillisecondsForDateTime(0, 3, 2, 0));
        datesArrayList.add(89, getMillisecondsForDateTime(0, 0, 3, 0));
        datesArrayList.add(90, getMillisecondsForDateTime(0, 1, 4, 0));
        datesArrayList.add(91, getMillisecondsForDateTime(0, 0, 6, 0));
        datesArrayList.add(92, getMillisecondsForDateTime(0, 2, 6, 0));
        datesArrayList.add(93, getMillisecondsForDateTime(0, 0, 7, 0));
        datesArrayList.add(94, getMillisecondsForDateTime(0, 0, 7, 0));
        datesArrayList.add(95, getMillisecondsForDateTime(0, 0, 9, 0));
        datesArrayList.add(96, getMillisecondsForDateTime(0, 3, 9, 0));
        datesArrayList.add(97, getMillisecondsForDateTime(0, 0, 10, 0));
        datesArrayList.add(98, getMillisecondsForDateTime(0, 2, 11, 0));
        datesArrayList.add(99, getMillisecondsForDateTime(0, 2, 11, 0));
        datesArrayList.add(100, getMillisecondsForDateTime(0, 2, 12, 0));
        datesArrayList.add(101, getMillisecondsForDateTime(0, 2, 15, 0));

////        DEVELOPMENT: 17 ENTRIES FOR END TIME
//        datesArrayList.add(102, getMillisecondsForDateTime(1, 3, 2, 0));
//        datesArrayList.add(103, getMillisecondsForDateTime(1, 0, 3, 0));
//        datesArrayList.add(104, getMillisecondsForDateTime(1, 3, 3, 0));
//        datesArrayList.add(105, getMillisecondsForDateTime(1, 0, 11, 0));
//        datesArrayList.add(106, getMillisecondsForDateTime(1, 0, 6, 0));
//        datesArrayList.add(107, getMillisecondsForDateTime(1, 0, 7, 0));
//        datesArrayList.add(108, getMillisecondsForDateTime(1, 0, 11, 0));
//        datesArrayList.add(109, getMillisecondsForDateTime(1, 0, 11, 0));
//        datesArrayList.add(110, getMillisecondsForDateTime(1, 0, 11, 0));
//        datesArrayList.add(111, getMillisecondsForDateTime(1, 3, 12, 0));
//        datesArrayList.add(112, getMillisecondsForDateTime(1, 0, 16, 0));
//        datesArrayList.add(113, getMillisecondsForDateTime(1, 2, 18, 0));
//        datesArrayList.add(114, getMillisecondsForDateTime(1, 3, 18, 0));
//        datesArrayList.add(115, getMillisecondsForDateTime(1, 1, 19, 0));
//        datesArrayList.add(116, getMillisecondsForDateTime(1, 3, 19, 0));
//        datesArrayList.add(117, getMillisecondsForDateTime(1, 2, 24, 0));
//        datesArrayList.add(118, getMillisecondsForDateTime(1, 1, 24, 0));
//
//        COMPLEMENTARY FEEDING : 5 ENTRIES
        datesArrayList.add(102, getMillisecondsForDateTime(2, 0, 6, 0));
        datesArrayList.add(103, getMillisecondsForDateTime(2, 0, 7, 0));
        datesArrayList.add(104, getMillisecondsForDateTime(2, 0, 9, 0));
        datesArrayList.add(105, getMillisecondsForDateTime(2, 0, 0, 1));
        datesArrayList.add(106, getMillisecondsForDateTime(2, 0, 0, 2));
//
////        TEETH : 10 ENTRIES
        datesArrayList.add(107, getMillisecondsForDateTime(3, 0, 6, 0));
        datesArrayList.add(108, getMillisecondsForDateTime(3, 0, 8, 0));
        datesArrayList.add(109, getMillisecondsForDateTime(3, 0, 9, 0));
        datesArrayList.add(110, getMillisecondsForDateTime(3, 0, 10, 0));
        datesArrayList.add(111, getMillisecondsForDateTime(3, 0, 1, 1));
        datesArrayList.add(112, getMillisecondsForDateTime(3, 0, 2, 1));
        datesArrayList.add(113, getMillisecondsForDateTime(3, 0, 4, 1));
        datesArrayList.add(114, getMillisecondsForDateTime(3, 0, 5, 1));
        datesArrayList.add(115, getMillisecondsForDateTime(3, 0, 11, 1));
        datesArrayList.add(116, getMillisecondsForDateTime(3, 0, 1, 2));
//
//        BRUSH : 2 ENTRIES
        datesArrayList.add(117, getMillisecondsForDateTime(4, 0, 6, 0));
        datesArrayList.add(118, getMillisecondsForDateTime(4, 0, 0, 2));

//        RANDOM : 2 RANDOM ENTRIES
        datesArrayList.add(119, getMillisecondsForDateTimeWithoutDOB(6, 0, 0, 0));
        datesArrayList.add(120, getMillisecondsForDateTimeWithoutDOB(5, 0, 1, 0));

//        /*logic to remove 1000000 from alarm list
//        when inserting alarm time millisecs in list...if time is before current time then default
//        100000 is added.
//        then 100000 is removed from list.*/

//        ArrayList<Long> removeList = new ArrayList<>();
//        removeList.add((long) 10000000);
//        System.out.println("datesArrayList before " + datesArrayList);
//        datesArrayList.removeAll(removeList);
//        System.out.println("datesArrayList after 10000000 removal" + datesArrayList);

        //loop to set alarms for millisecs present in list
        for (int i = 0; i < datesArrayList.size(); i++) {

            if (!(datesArrayList.get(i) == 10000000)) {

                bundle = new Bundle();
                bundle.putInt("Id", i);
                intent.putExtras(bundle);
//            intent.putExtras(bundle);
                pendingIntent = PendingIntent.getBroadcast(context, i, intent, 0);
                alarmManager.set(AlarmManager.RTC_WAKEUP, datesArrayList.get(i), pendingIntent);
                System.out.println("Alram set---------" + i);
                System.out.println("Alram set for ---------" + datesArrayList.get(i));

                SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yy HH:MM:SS");
                Date resultdateTime = new Date(datesArrayList.get(i));
                String customdate = dateTimeFormat.format(resultdateTime);
                System.out.println("Alerm set for :  " + customdate);
            }
        }

    }

    private long getMillisecondsForDateTimeWithoutDOB(int day, int week, int month, int year) {

        Calendar c = null;
        String customdate = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yy HH:MM:SS");

        c = Calendar.getInstance();
        calendar_timeOfDay.setTimeZone(TimeZone.getTimeZone("GMT"));
        c.setTime(new Date());

        // setting custom values
        c.add(Calendar.YEAR, year);
        c.add(Calendar.MONTH, month);
        c.add(Calendar.DATE, (week * 7));
        c.add(Calendar.DATE, day);
        c.add(Calendar.HOUR, calendar_timeOfDay.get(Calendar.HOUR));
        c.add(Calendar.MINUTE, calendar_timeOfDay.get(Calendar.MINUTE));

//        c = Calendar.getInstance();
//        c.add(Calendar.MINUTE, 1);


        Date resultdateTime = new Date(c.getTimeInMillis());
        customdate = dateTimeFormat.format(resultdateTime);
        System.out.println("customdate time w/o DOB " + customdate);
        return c.getTimeInMillis();
    }

    public void cancelAllNotifications(Context context, String dateOfBirth, long timeOfDay) {
        System.out.println("INSIDE ALRAM CANCEL METHOD OF AddNotificationDates CLASS");
        this.context = context;
        for (int alarmCancel = 0; i < 121; alarmCancel++) {
            Intent intent = new Intent(context, AlarmReceiver.class);
            PendingIntent sender = PendingIntent.getBroadcast(context, alarmCancel, intent, 0);
            AlarmManager alarmManager = (AlarmManager)context.getSystemService(ALARM_SERVICE);
            alarmManager.cancel(sender);
        }
    }

    private long getMillisecondsForDateTime(int day, int week, int month, int year) {

        Calendar c = null;
        String customdate = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd-MM-yy HH:MM:SS");

        try {
            Date date = format.parse(babyDateOfBirth);

            c = Calendar.getInstance();
            calendar_timeOfDay.setTimeZone(TimeZone.getTimeZone("GMT"));
            c.setTime(date);
//
//            // setting custom values
            c.add(Calendar.YEAR, year);
            c.add(Calendar.MONTH, month);
            c.add(Calendar.DATE, (week * 7));
            c.add(Calendar.DATE, day);
            c.add(Calendar.HOUR, calendar_timeOfDay.get(Calendar.HOUR));
            c.add(Calendar.MINUTE, calendar_timeOfDay.get(Calendar.MINUTE));

//            c = Calendar.getInstance();
//            c.add(Calendar.MINUTE, month);

            Date resultdateTime = new Date(c.getTimeInMillis());
            customdate = dateTimeFormat.format(resultdateTime);
            System.out.println("customdate time " + customdate);
        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (c.getTimeInMillis() > System.currentTimeMillis()) {
            System.out.println("************************ Time is future");
            return c.getTimeInMillis();
        } else {
            System.out.println("************************ Time is in past");
            return 10000000;
        }
    }

}
