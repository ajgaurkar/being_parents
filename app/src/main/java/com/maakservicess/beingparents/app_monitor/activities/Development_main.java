package com.maakservicess.beingparents.app_monitor.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.Adapters.DevelopmentAdapter;
import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.DevelopmentListData;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by ajinkya on 12/29/2015.
 */
public class Development_main extends Activity {

    private FloatingActionButton gotoChartFAB;
    ListView devMainListView;
    ArrayList<DevelopmentListData> developmentPendingStatusList;
    ArrayList<DevelopmentListData> developmentCompletedStatusList;
    private DatabaseHandler databaseHandler;
    RelativeLayout taskstab;
    RelativeLayout completedtab;
    DevelopmentAdapter developmentAdapter;
    String currentSection = "Tasks";
    int completedTaskCount = 0;
    private ArcProgress devArcBar;
    private TextView totalTasksTextView;
    private TextView tasksCompletedTextView;
    private int year;
    private int month;
    private int day;
    String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private TextView taskDateTextView;
    private StringBuilder selectedTakenDate;
    private View devMainLaytout;
    private ProgressDialog developmentMainDialog;
    private TextView congratulationsTilteTextView;
    private TextView congratulationsBodyTextView;
    private TextView mileStoneBtn;
    private ImageView shareDevDataIcon;
    SessionManager sessionManager;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.development_main);

        mAdView = (AdView) findViewById(R.id.dev_main_AdView);
//        loadInterstitialAd();
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        sessionManager = new SessionManager(getApplicationContext());
        devMainLaytout = (View) findViewById(R.id.developmentMainLayout);
        databaseHandler = new DatabaseHandler(this);
        gotoChartFAB = (FloatingActionButton) findViewById(R.id.gotoTrivendrumChartFAB);
        gotoChartFAB.hide();

        mileStoneBtn = (TextView) findViewById(R.id.develpmentMilestonesButton);
        devMainListView = (ListView) findViewById(R.id.developemt_mainListView);
        developmentPendingStatusList = new ArrayList<DevelopmentListData>();
        developmentCompletedStatusList = new ArrayList<DevelopmentListData>();
        taskstab = (RelativeLayout) findViewById(R.id.development_main_tasks_tab);
        completedtab = (RelativeLayout) findViewById(R.id.development_main_completed_tab);
        devArcBar = (ArcProgress) findViewById(R.id.development_main_arc_progress_bar);
        shareDevDataIcon = (ImageView) findViewById(R.id.dev_share_image_view);
        tasksCompletedTextView = (TextView) findViewById(R.id.devTasksCompletedTextView);
        totalTasksTextView = (TextView) findViewById(R.id.totalDevTasksTextView);
        congratulationsTilteTextView = (TextView) findViewById(R.id.congratulationsTitleTextView);
        congratulationsBodyTextView = (TextView) findViewById(R.id.congratulationsBodyTextView);
        developmentMainDialog = new ProgressDialog(this);
        populateData();

        // initializing date values for dateEditText
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        gotoChartFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoTrivendramChartIntent = new Intent(getApplicationContext(), TrivandrumChart.class);
                startActivity(gotoTrivendramChartIntent);
            }
        });

        mileStoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gotoMilestoneIntent = new Intent(getApplicationContext(), DevelopmentMilestone.class);
                startActivity(gotoMilestoneIntent);
            }
        });

        taskstab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentSection.equals("Completed")) {
                            currentSection = "Tasks";
                            developmentAdapter = new DevelopmentAdapter(getApplicationContext(), developmentPendingStatusList);
                            devMainListView.setAdapter(developmentAdapter);

                            //conditions to show
                            if (completedTaskCount == 17) {
                                congratulationsTilteTextView.setText(R.string.congratulations_text);
                                congratulationsBodyTextView.setText(R.string.development_task_completed_body);
                            } else {
                                congratulationsTilteTextView.setText(R.string.blank_string);
                                congratulationsBodyTextView.setText(R.string.blank_string);
                            }
                        }
                    }
                }
        );

        completedtab.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentSection.equals("Tasks")) {
                            currentSection = "Completed";
                            developmentAdapter = new DevelopmentAdapter(getApplicationContext(), developmentCompletedStatusList);
                            devMainListView.setAdapter(developmentAdapter);

                            //conditions to show
                            if (completedTaskCount == 0) {
                                congratulationsTilteTextView.setText(R.string.pending_task_head);
                                congratulationsBodyTextView.setText(R.string.pending_task_incomplete_body);
                            } else {
                                congratulationsTilteTextView.setText(R.string.blank_string);
                                congratulationsBodyTextView.setText(R.string.blank_string);
                            }
                        }
                    }
                }
        );

        devMainListView.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        devListUpdate(position);
                    }
                }
        );

        shareDevDataIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String devShareString = "Trivendram development screening for *" +
                        sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_NAME)
                        + "*\n\n*" + developmentCompletedStatusList.size() +
                        "/17* Tasks completed \n\n";
                for (int i = 0; i < developmentCompletedStatusList.size(); i++) {
                    int srNo = i;
                    srNo++;
                    devShareString = devShareString + srNo + " " + developmentCompletedStatusList.get(i).getTask() + "\nExpected : " +
                            developmentCompletedStatusList.get(i).getRangeFrom() + " To " + developmentCompletedStatusList.get(i).getRangeTo() + "\nActual : " +
                            developmentCompletedStatusList.get(i).getCompletedOn() + " (" + developmentCompletedStatusList.get(i).getStatus() + ")\n\n";

                }
                Intent nav_share_Intent = new Intent(android.content.Intent.ACTION_SEND);
                nav_share_Intent.setType("text/plain");
                nav_share_Intent.putExtra(android.content.Intent.EXTRA_TEXT, devShareString);
                startActivity(Intent.createChooser(nav_share_Intent, "Share via :"));
            }
        });


        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        //ad listener to set visibility of banner
        mAdView.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdView.setVisibility(View.GONE);
            }

        });

        loadInterstitialAd();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                showShowcase();
            }
        }, 1500);
    }

    private void showShowcase() {
        // sequence example
        ShowcaseConfig config = new ShowcaseConfig();
        config.setDelay(1000); // half second between each showcase view

        MaterialShowcaseSequence sequence = new MaterialShowcaseSequence(this);

        sequence.setConfig(config);
        sequence.addSequenceItem(gotoChartFAB,
                "Overview the basic screening tasks here", "NEXT");

        sequence.addSequenceItem(mileStoneBtn,
                "Get detailed development milestones with this option", "NEXT");

        sequence.addSequenceItem(shareDevDataIcon,
                "Share development record with this option", "NEXT");

        sequence.addSequenceItem(devMainListView,
                "Basic tasks to be completed with their schedule. Click on any task to mark its completion with date", "DONE");

        sequence.start();
    }

    //AD functions...load AND show
    private void loadInterstitialAd() {
        mInterstitialAd = new InterstitialAd(this);

        // set the ad unit ID
        mInterstitialAd.setAdUnitId(getString(R.string.appbabyinterstitial));

        AdRequest adRequest = new AdRequest.Builder().build();

        // Load ads into Interstitial Ads
        mInterstitialAd.loadAd(adRequest);

        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                System.out.println("ad pressed");
//                showInterstitial();

            }
        });
    }

    private void showInterstitial() {
        if (mInterstitialAd.isLoaded()) {
            System.out.println("ad pressed INSIDE SHOW");
            mInterstitialAd.show();
            loadInterstitialAd();

        }
    }

    private void populateData() {

        developmentMainDialog.setMessage("Please wait");
        developmentMainDialog.show();

        //fetch development data
        Cursor developmentCursor = databaseHandler.getAllDevelopmentDataByCursor();
        System.out.println("developmentCursor.getCount() : " + developmentCursor.getCount());

        developmentPendingStatusList.clear();
        developmentCompletedStatusList.clear();
        completedTaskCount = 0;

        if (developmentCursor.moveToFirst()) {
            developmentCursor.moveToFirst();
            do {

//                System.out.println(" inside condition checking");
//                System.out.println("developmentCursor.getString(7) : " + developmentCursor.getString(7));
                if (developmentCursor.getString(7).equals("Pending")) {
//                    System.out.println("cursor data : " + developmentCursor.getString(0) + " " + developmentCursor.getString(1) + " " +
//                            developmentCursor.getString(2) + " " + developmentCursor.getString(3) + " " + developmentCursor.getString(4) + " " +
//                            developmentCursor.getInt(5) + " " + developmentCursor.getString(6) + " " + developmentCursor.getString(7));
                    System.out.println("cursor data : " + developmentCursor.getString(6));
                    developmentPendingStatusList.add(new DevelopmentListData(developmentCursor.getString(0), developmentCursor.getString(1),
                            developmentCursor.getString(2), developmentCursor.getString(3), developmentCursor.getString(4), developmentCursor.getString(5),
                            developmentCursor.getInt(6), developmentCursor.getString(7)));
                } else {
//                    System.out.println("cursor data : " + developmentCursor.getString(0) + " " + developmentCursor.getString(1) + " " +
//                            developmentCursor.getString(2) + " " + developmentCursor.getString(3) + " " + developmentCursor.getString(4) + " " +
//                            developmentCursor.getInt(5) + " " + developmentCursor.getString(6) + " " + developmentCursor.getString(7));
                    System.out.println("cursor data : " + developmentCursor.getString(6));
                    developmentCompletedStatusList.add(new DevelopmentListData(developmentCursor.getString(0), developmentCursor.getString(1),
                            developmentCursor.getString(2), developmentCursor.getString(3), developmentCursor.getString(4), developmentCursor.getString(5),
                            developmentCursor.getInt(6), developmentCursor.getString(7)));
                    completedTaskCount++;
                }
            }
            while (developmentCursor.moveToNext());
        }

        if (currentSection.equals("Tasks")) {

            //set data to list view
            developmentAdapter = new DevelopmentAdapter(getApplicationContext(), developmentPendingStatusList);
            devMainListView.setAdapter(developmentAdapter);

            //conditions to show
            if (completedTaskCount == 17) {
                congratulationsTilteTextView.setText(R.string.congratulations_text);
                congratulationsBodyTextView.setText(R.string.development_task_completed_body);
            } else {
                congratulationsTilteTextView.setText(R.string.blank_string);
                congratulationsBodyTextView.setText(R.string.blank_string);
            }
        } else {

            //set data to list view
            developmentAdapter = new DevelopmentAdapter(getApplicationContext(), developmentCompletedStatusList);
            devMainListView.setAdapter(developmentAdapter);

            //conditions to show
            if (completedTaskCount == 0) {
                congratulationsTilteTextView.setText(R.string.pending_task_head);
                congratulationsBodyTextView.setText(R.string.pending_task_incomplete_body);
            } else {
                congratulationsTilteTextView.setText(R.string.blank_string);
                congratulationsBodyTextView.setText(R.string.blank_string);
            }
        }
        devArcBar.setProgress(completedTaskCount);
        tasksCompletedTextView.setText(String.valueOf(completedTaskCount));

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                developmentMainDialog.dismiss();
                gotoChartFAB.show();
            }
        }, 1000);

    }

    private void devListUpdate(int position) {

        final DevelopmentListData selectedItem;
        if (currentSection.equals("Tasks")) {
            selectedItem = developmentPendingStatusList.get(position);
        } else {
            selectedItem = developmentCompletedStatusList.get(position);
        }

        LayoutInflater flater = this.getLayoutInflater();
        View view = flater.inflate(R.layout.development_main_entry_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle(selectedItem.getTask());
        final TextView taskDescriptionTextView = (TextView) view.findViewById(R.id.devTaskDescriptionTextView);
        taskDateTextView = (TextView) view.findViewById(R.id.devDateAddEditText);
        TextView expectedDateTextView = (TextView) view.findViewById(R.id.devExpectedTextView);
        expectedDateTextView.setText(selectedItem.getRangeFrom() + " To " + selectedItem.getRangeTo());
        taskDescriptionTextView.setText(selectedItem.getTaskDescription());

        if (currentSection.equals("Tasks")) {
            builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //check wether date is entered or field is blank
                    if (!taskDateTextView.getText().toString().equals("")) {
                        DevelopmentListData updateDevelopmentListData = new DevelopmentListData(selectedItem.getId(),
                                selectedItem.getTask(),
                                selectedItem.getTaskDescription(),
                                selectedItem.getRangeFrom(),
                                selectedItem.getRangeTo(),
                                taskDateTextView.getText().toString(),
                                selectedItem.getImgId(), getDevStatusByDateCompare(selectedItem.getRangeFrom(), selectedItem.getRangeTo(), taskDateTextView.getText().toString()));

                        int updateDevInt = databaseHandler.updateDevelopment(updateDevelopmentListData);

                        showInterstitial();

                        populateData();
                    } else {
                        Snackbar.make(devMainLaytout, "Select a Date", Snackbar.LENGTH_SHORT).show();
                    }
                }
            });
        } else {
            taskDateTextView.setText(selectedItem.getCompletedOn());
            builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                System.out.println("UPDATE pressed from : " + section);
                    DevelopmentListData updateDevelopmentListData = new DevelopmentListData(selectedItem.getId(),
                            selectedItem.getTask(),
                            selectedItem.getTaskDescription(),
                            selectedItem.getRangeFrom(),
                            selectedItem.getRangeTo(),
                            taskDateTextView.getText().toString(),
                            selectedItem.getImgId(), getDevStatusByDateCompare(selectedItem.getRangeFrom(), selectedItem.getRangeTo(), taskDateTextView.getText().toString()));

                    int updateDevInt = databaseHandler.updateDevelopment(updateDevelopmentListData);

                    showInterstitial();

                    populateData();
                }
            });
            builder.setNeutralButton("Reset", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
//                System.out.println("UPDATE pressed from : " + section);
                    DevelopmentListData updateDevelopmentListData = new DevelopmentListData(selectedItem.getId(),
                            selectedItem.getTask(),
                            selectedItem.getTaskDescription(),
                            selectedItem.getRangeFrom(),
                            selectedItem.getRangeTo(), "",
                            selectedItem.getImgId(), "Pending");

                    int updateDevInt = databaseHandler.updateDevelopment(updateDevelopmentListData);

                    showInterstitial();

                    populateData();
                }
            });
        }

        taskDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        taskDateTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        builder.create().show();
    }

    private String getDevStatusByDateCompare(String rangeFrom, String rangeTo, String enteredDate) {

        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
//        DateFormat dateFormat2 = new SimpleDateFormat("dd-MMM-yy");
        String returnStatus = null;

        try {
            Date dateFrom = dateFormat.parse(rangeFrom);
            Date dateTo = dateFormat.parse(rangeTo);
            Date dateEntered = dateFormat.parse(enteredDate);

            if (dateEntered.compareTo(dateFrom) < 0) {
                returnStatus = "Early";
            } else if (dateEntered.compareTo(dateTo) > 0) {
                returnStatus = "Late";
            } else {
                returnStatus = "On time";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return returnStatus;
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this, myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker arg0, int arg1, int arg2, int arg3) {
            // TODO Auto-generated method stub
            // arg1 = year
            // arg2 = month
            // arg3 = day

            showDate(arg1, arg2 + 1, arg3);
        }
    };

    private void showDate(int year, int month, int day) {

        /* trimming logic to convert 2016 to 16 for short date string */
        String yearTrimmed = Integer.toString(year);
        yearTrimmed = yearTrimmed.substring(2);
        /**/
//        selectedTakenDate = new StringBuilder().append(day).append("-")
//                .append(monthArray[month - 1]).append("-").append(yearTrimmed);

        selectedTakenDate = new StringBuilder().append(day).append("-")
                .append(month).append("-").append(yearTrimmed);
        taskDateTextView.setText(selectedTakenDate);

    }

}

