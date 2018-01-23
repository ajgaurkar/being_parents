package com.maakservicess.beingparents.app_monitor.activities;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

import com.maakservicess.beingparents.app_monitor.Adapters.CalenderPagerAdapter;
import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.CalendarEntryListData;
import com.marcohc.robotocalendar.RobotoCalendarView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by ajinkya on 12/29/2015.
 */
public class Doctor_visit_main extends AppCompatActivity implements RobotoCalendarView.RobotoCalendarListener {

    private RobotoCalendarView robotoCalendarView;
    private int currentMonthIndex;
    private int actualMonthIndex;
    private int actualYearIndex;
    private Calendar currentCalendar;
    private FloatingActionButton docVisitFAB;
    ViewPager pager;
    TabLayout tabLayout;
    private DatabaseHandler databaseHandler;
    private Cursor calendarCursor;
    public static Map<String, List<CalendarEntryListData>> entriesVisitMap = new HashMap();
    public static Map<String, List<CalendarEntryListData>> entriesMomentsMap = new HashMap();
    List<CalendarEntryListData> entriesList = new ArrayList<>();
    Date selectedDate;
    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.doctor_visit_main);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        docVisitFAB = (FloatingActionButton) findViewById(R.id.doctorVisitMainEntryFAB);
        //hide by default. Show on date click
//        docVisitFAB.setVisibility(View.INVISIBLE);
        docVisitFAB.hide();
        // Gets the calendar from the view
        robotoCalendarView = (RobotoCalendarView) findViewById(R.id.robotoCalendarView);

        //tab components
        pager = (ViewPager) findViewById(R.id.calendar_view_pager);
        tabLayout = (TabLayout) findViewById(R.id.tab_layout);


        // Set listener, in this case, the same activity
        robotoCalendarView.setRobotoCalendarListener(this);

        // Initialize the RobotoCalendarPicker with the current index and date
        currentMonthIndex = 0;
        currentCalendar = Calendar.getInstance(Locale.getDefault());

        //set today's date as default to selected date
        // it will change when user clicks on other date
        selectedDate = currentCalendar.getTime();

        //months starts from 0. so need to bre incremented
        actualMonthIndex = currentCalendar.get(Calendar.MONTH);
        actualMonthIndex++;

        actualYearIndex = currentCalendar.get(Calendar.YEAR);

        // Mark current day
        robotoCalendarView.markDayAsSelectedDay(currentCalendar.getTime());

        docVisitFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                docVisitFAB.hide();
                openCalendarEntryDialog();
            }
        });


        tabLayout.addTab(tabLayout.newTab().setText("Visits"), 0);
        tabLayout.addTab(tabLayout.newTab().setText("Moments"), 1);

        databaseHandler = new DatabaseHandler(this);

        getDataForYearMonth(actualYearIndex, actualMonthIndex);

        setTabs();

    }

    private void getDataForYearMonth(int actualYearIndex, int actualMonthIndex) {

        //initialize map and list for fresh read
        entriesVisitMap = new HashMap();
        entriesMomentsMap = new HashMap();
        entriesList = new ArrayList<>();

        calendarCursor = databaseHandler.getCalendarMonthDataByCursor(actualYearIndex, actualMonthIndex);
        System.out.println("calendarCursor size : " + calendarCursor.getCount());

        if (calendarCursor.moveToFirst()) {
            do {
                DateFormat format = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
                Date date = null;
                try {
                    date = format.parse(calendarCursor.getInt(calendarCursor.getColumnIndex("calDay")) + " " +
                            calendarCursor.getInt(calendarCursor.getColumnIndex("calMonth")) + " " +
                            (calendarCursor.getInt(calendarCursor.getColumnIndex("calYear"))));
                    System.out.println("date for cursor : " + date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String entryDate = format.format(date);

                System.out.println("--------------------- " + "\n" + "Cursor data item " + " " + calendarCursor.getString(0) + " " +
                        calendarCursor.getString(1) + " " + calendarCursor.getString(2) + " " + calendarCursor.getString(3) + " " +
                        calendarCursor.getString(4) + " " + calendarCursor.getString(5) + " " + calendarCursor.getString(6) + " " +
                        calendarCursor.getString(7) + " " + calendarCursor.getString(8) + " " + calendarCursor.getString(9) + " " +
                        calendarCursor.getString(10));


                System.out.println("entriesVisitMap before put: " + entriesVisitMap);
                System.out.println("entriesMomentsMap before put: " + entriesMomentsMap);

                if (date != null) {
                    System.out.println("calendarCursor.getString(6) : " + calendarCursor.getString(6));
                    if (calendarCursor.getString(6).equals("Visit")) {
                        System.out.println("INSIDE IF ");

                        entriesList = new ArrayList<>();

                        //check it entry for date is present or not
                        //if present the get list from map and add more data
                        if (entriesVisitMap.containsKey(entryDate)) {
                            System.out.println("INSIDE IF IF");
                            entriesList = entriesVisitMap.get(entryDate);
                        }

                        entriesList.add(new CalendarEntryListData(calendarCursor.getInt(calendarCursor.getColumnIndex("id")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calDay")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calMonth")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calYear")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calTitle")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calDescription")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calImg1")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calImg2")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calImg3")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calImgCount")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calCategory"))));

                        System.out.println("entriesList size : " + entriesList.size());
                        entriesVisitMap.put(entryDate, entriesList);
                        System.out.println("entriesVisitMap : " + entriesVisitMap);

                    } else {

                        System.out.println("INSIDE ELSE");
                        entriesList = new ArrayList<>();

                        //check it entry for date is present or not
                        //if present the get list from map and add more data
                        if (entriesMomentsMap.containsKey(entryDate)) {
                            System.out.println("INSIDE ELSE IF");
                            entriesList = entriesMomentsMap.get(entryDate);
                        }

                        entriesList.add(new CalendarEntryListData(calendarCursor.getInt(calendarCursor.getColumnIndex("id")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calDay")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calMonth")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calYear")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calTitle")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calDescription")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calImg1")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calImg2")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calImg3")),
                                calendarCursor.getInt(calendarCursor.getColumnIndex("calImgCount")),
                                calendarCursor.getString(calendarCursor.getColumnIndex("calCategory"))));

                        System.out.println("entriesList size : " + entriesList.size());
                        entriesMomentsMap.put(entryDate, entriesList);
                        System.out.println("entriesMomentsMap : " + entriesMomentsMap);

                    }

                    System.out.println("entriesList size : " + entriesList.size());
                    System.out.println("entriesVisitMap size : " + entriesVisitMap.size());
                    System.out.println("entriesMomentsMap size : " + entriesMomentsMap.size());
                    System.out.println("--------------------- ");
                }
            }
            while (calendarCursor.moveToNext());
        }
        setIndicatorsToCalendar();
    }

    private void setIndicatorsToCalendar() {

        DateFormat format = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        Date date = null;

        //set indicators for moments
        for (String key : entriesMomentsMap.keySet()) {
            System.out.println("entriesMomentsMap.keySet() :" + key);
            try {
                date = format.parse(key);
                robotoCalendarView.markFirstUnderlineWithStyle(R.color.DarkBlue, date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        //set indicators for visits
        for (String key : entriesVisitMap.keySet()) {
            System.out.println("entriesVisitMap.keySet() :" + key);
            try {
                date = format.parse(key);
                robotoCalendarView.markSecondUnderlineWithStyle(R.color.DarkRed, date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


    }

    private void openCalendarEntryDialog() {

        final CharSequence[] items = {"Add doctor visit", "Add a moment"};

        DateFormat selectedDateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
        final String selectedDateString = selectedDateFormat.format(selectedDate);

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select an entry");
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("Cancel");
                docVisitFAB.show();
                dialog.dismiss();
            }
        });

        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Add doctor visit")) {

                    System.out.println("visit");
                    Intent addCalendarEntryIntent = new Intent(getApplicationContext(), AddCalendarEvent.class);
                    addCalendarEntryIntent.putExtra("Category", "Visit");
                    addCalendarEntryIntent.putExtra("PageMode", "Add");
                    addCalendarEntryIntent.putExtra("EntryDate", selectedDateString);
                    startActivity(addCalendarEntryIntent);

                } else if (items[item].equals("Add a moment")) {

                    System.out.println("Moment");
                    Intent addCalendarEntryIntent = new Intent(getApplicationContext(), AddCalendarEvent.class);
                    addCalendarEntryIntent.putExtra("Category", "Moment");
                    addCalendarEntryIntent.putExtra("PageMode", "Add");
                    addCalendarEntryIntent.putExtra("EntryDate", selectedDateString);
                    startActivity(addCalendarEntryIntent);

                }
            }
        });
        builder.setCancelable(false);
        builder.create().show();

    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onDateSelected(Date date) {
//        docVisitFAB.setVisibility(View.VISIBLE);
        docVisitFAB.show();

        // Mark calendar day
        robotoCalendarView.markDayAsSelectedDay(date);
        selectedDate = date;

    }

    @Override
    public void onRightButtonClick() {
        currentMonthIndex++;
        actualMonthIndex++;

        if (actualMonthIndex == 13) {
            actualMonthIndex = 1;
            actualYearIndex++;
        }
        System.out.println("actualYearIndex : " + actualYearIndex + " actualMonthIndex : " + actualMonthIndex);
        updateCalendar();
//        docVisitFAB.setVisibility(View.INVISIBLE);
        docVisitFAB.hide();

    }

    @Override
    public void onLeftButtonClick() {
        currentMonthIndex--;
        actualMonthIndex--;
        if (actualMonthIndex == 0) {
            actualMonthIndex = 12;
            actualYearIndex--;
        }
        System.out.println("actualYearIndex : " + actualYearIndex + " actualMonthIndex : " + actualMonthIndex);

        updateCalendar();
//        docVisitFAB.setVisibility(View.INVISIBLE);
        docVisitFAB.hide();

    }

    private void updateCalendar() {
        currentCalendar = Calendar.getInstance(Locale.getDefault());
        currentCalendar.add(Calendar.MONTH, currentMonthIndex);
        robotoCalendarView.initializeCalendar(currentCalendar);
        getDataForYearMonth(actualYearIndex, actualMonthIndex);
        currentTab = pager.getCurrentItem();
        setTabs();

    }

    private void setTabs() {
        // FragmentManager fragManager = getFragmentManager();
        FragmentManager manager = getSupportFragmentManager();
        CalenderPagerAdapter adapter = new CalenderPagerAdapter(manager, tabLayout.getTabCount());
        pager.setAdapter(adapter);

        tabLayout.setupWithViewPager(pager);
        // mTabLayout.setupWithViewPager(mPager1);
        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        tabLayout.setTabsFromPagerAdapter(adapter);
        pager.setCurrentItem(currentTab);

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateCalendar();
//        docVisitFAB.setVisibility(View.INVISIBLE);
        docVisitFAB.hide();

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}