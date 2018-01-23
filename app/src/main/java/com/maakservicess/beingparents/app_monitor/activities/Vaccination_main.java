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
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.Adapters.ExpandListAdapter;
import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineChildItem;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineGroupItem;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineSqliteData;
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
import java.util.List;
import java.util.Locale;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

/**
 * Created by ajinkya on 12/29/2015.
 * //
 */

public class Vaccination_main extends Activity {

    int vaccineCompletedBtnStatus = 0;
    int vaccineScheduleBtnStatus = 1;
    //    TextView vaccineScheduleBtn;
//    TextView vaccineCompletedBtn;
    TextView vaccine_upcoming_1;
    TextView vaccine_upcoming_4;
    TextView vaccine_upcoming_3;
    TextView vaccine_upcoming_2;
    TextView vaccine_upcoming_5;
    TextView vaccine_completed_count_text_view;
    ExpandableListView vaccineExpListView;
    private ExpandListAdapter ExpAdapter;
    private ArrayList<VaccineGroupItem> ExpListItems;
    private DatabaseHandler databaseHandler;
    private List<VaccineSqliteData> vaccineTableDataList;
    private ArrayList<String> groupNames;
    private Cursor vaccinTableDataCursor;
    private VaccineChildItem selectedChildItem;
    private int year;
    private int month;
    private int day;
    private EditText dialogTakenDateEditText;
    String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    private List<String> groupNameList;
    ProgressDialog vaccineMainDialog;
    private ArcProgress completedProgressArc;
    Calendar calendar;
    private Date currentDate;
    private String currentGroup;
    private int currentGroupIndex;
    private int vaccineCompletedCount = 0;
    private ListView upcomingVaccineListView;
    private ImageView vaccine_share_btn;
    SessionManager sessionManager;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vaccination_main);
        databaseHandler = new DatabaseHandler(this);

        mAdView = (AdView) findViewById(R.id.vaccin_main_AdView);
//        loadInterstitialAd();
//
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

        sessionManager = new SessionManager(getApplicationContext());

        vaccine_upcoming_1 = (TextView) findViewById(R.id.upVaccine1TextView);
        vaccine_share_btn = (ImageView) findViewById(R.id.shareVaccineImageView);
        upcomingVaccineListView = (ListView) findViewById(R.id.upcomingVaccinesListView);

        vaccine_completed_count_text_view = (TextView) findViewById(R.id.totalVaccinesCompletedTextView);
        vaccineExpListView = (ExpandableListView) findViewById(R.id.vaccineExpandableListView);
        completedProgressArc = (ArcProgress) findViewById(R.id.vaccineMainProgress);
        vaccineMainDialog = new ProgressDialog(this);

        //load all data into list
        startActivityMethod();

        // Get current date by calender
        calendar = Calendar.getInstance();
        currentDate = calendar.getTime();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);

        vaccineExpListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                //getting selected gruop with arraylist of corresponding child
                //to get the selectd vaccine item
//                System.out.println("groupPosition : " + groupPosition + "  childPosition : " + childPosition);
                VaccineGroupItem selectedGroupItem = ExpListItems.get(groupPosition);
                ArrayList<VaccineChildItem> selectedChildItemList = selectedGroupItem.getItems();
                selectedChildItem = selectedChildItemList.get(childPosition);

                vaccineClickedAction(selectedGroupItem, selectedChildItem);
                return false;
            }
        });
        vaccine_share_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String immunizationShareString = " Immunization record of *" +
                        sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_NAME) + "*\n\n*" +
                        vaccineCompletedCount + "/37* Vaccines administered \n\n";

                for (int i = 0; i < ExpListItems.size(); i++) {
                    VaccineGroupItem group = ExpListItems.get(i);
                    immunizationShareString = "\n" + immunizationShareString + "*" + group.getName() + "*" + " (Due dt, Received on)" + "\n";
                    ArrayList<VaccineChildItem> groupItems = group.getItems();

                    String innerTempString = "";
                    for (int j = 0; j < groupItems.size(); j++) {
                        VaccineChildItem childItem = groupItems.get(j);


                        innerTempString = innerTempString + childItem.getName() + "\n" + childItem.getDuedt() + "  " + childItem.getTakendate();

                        if (childItem.getTakendate().equals("")) {
                            innerTempString = innerTempString + "  Pending \n";
                        } else {
                            innerTempString = innerTempString + "\n";
                        }
                    }

                    immunizationShareString = immunizationShareString + innerTempString + "\n";
                }

                Intent nav_share_Intent = new Intent(android.content.Intent.ACTION_SEND);
                nav_share_Intent.setType("text/plain");
                nav_share_Intent.putExtra(android.content.Intent.EXTRA_TEXT, immunizationShareString);
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
//        sequence.singleUse("VACCINES_SHOWCASE_SEQUENCE");

        sequence.addSequenceItem(vaccine_completed_count_text_view,
                "Total number of vaccines received", "NEXT");

        sequence.addSequenceItem(upcomingVaccineListView,
                "Upcoming vaccines for current schedule appears here", "NEXT");

        sequence.addSequenceItem(vaccine_share_btn,
                "Share immunization record with this option", "NEXT");

        sequence.addSequenceItem(vaccineExpListView,
                "Click on any vaccine to add date when completed", "DONE");

//
//        sequence.addSequenceItem(findViewById(R.id.options_drawer),
//                "Click here OR swipe from right to explore more options", "DONE");

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

    private void startActivityMethod() {

        vaccineMainDialog.show();
        vaccineMainDialog.setMessage("Loading...");

        vaccineTableDataList = databaseHandler.getAllVaccineDataByList();
        vaccinTableDataCursor = databaseHandler.getAllVaccineDataByCursor();
        groupNameList = getGroupsNamesFromCursor(vaccinTableDataCursor);
        ExpListItems = createImmunizationList();
        ExpAdapter = new ExpandListAdapter(this, ExpListItems);
        vaccineExpListView.setAdapter(ExpAdapter);
        vaccineExpListView.expandGroup(currentGroupIndex);
        vaccineExpListView.setSelectedGroup(currentGroupIndex);
        completedProgressArc.setProgress(vaccineCompletedCount);
        vaccine_completed_count_text_view.setText(String.valueOf(vaccineCompletedCount));

        setUpcomingVaccines();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                vaccineMainDialog.dismiss();
            }
        }, 1000);

    }

    private void setUpcomingVaccines() {
        ArrayList<String> upcomingList = new ArrayList<>();
        VaccineGroupItem currentGroupItem = ExpListItems.get(currentGroupIndex);
        ArrayList<VaccineChildItem> currentChildItems = currentGroupItem.getItems();
        System.out.println("currentChildItems : " + currentChildItems.size());
        for (int i = 0; i < currentChildItems.size(); i++) {
            System.out.println("i : " + i);
            VaccineChildItem loopingChildItem = currentChildItems.get(i);
            System.out.println("loopingChildItem : " + loopingChildItem);

            if (loopingChildItem.getVaccineStatus().equals("Pending") && upcomingList.size() <= 3) {
                upcomingList.add("--> " + loopingChildItem.getName());
            }

        }
        if (upcomingList.size() == 0) {
            vaccine_upcoming_1.setText("None" + System.getProperty("line.separator") + "(All vaccines scheduled for " + System.getProperty("line.separator") + currentGroupItem.getName() + " have been administered)");
        } else {
            ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.upcomming_vaccine_list_item_1, upcomingList);
            upcomingVaccineListView.setAdapter(adapter);
        }
    }

    private void vaccineClickedAction(VaccineGroupItem groupItem, final VaccineChildItem childItem) {
        LayoutInflater flater = this.getLayoutInflater();
        View view = flater.inflate(R.layout.vaccine_main_entry_dialog, null);
        EditText dialogDueDateEditText = (EditText) view.findViewById(R.id.vaccineDialogDueEditText);
        dialogTakenDateEditText = (EditText) view.findViewById(R.id.vaccineDialogTakenDtEditText);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        dialogDueDateEditText.setText(childItem.getDuedt());
        dialogTakenDateEditText.setText(childItem.getTakendate());
        String positiveBtnText = "Change";
        builder.setTitle(childItem.getName() + " (" + childItem.getVaccineStatus() + ")");

        if (childItem.getVaccineStatus().equals("Pending")) {
            positiveBtnText = "Add";
        }

        builder.setPositiveButton(positiveBtnText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String addedDate = dialogTakenDateEditText.getText().toString();
                System.out.println("addedDate  : " + addedDate);
                VaccineSqliteData updateVaccineData = new VaccineSqliteData(childItem.getVaccineId(),
                        childItem.getVaccineGroup(), childItem.getName(),
                        childItem.getDuedt(), addedDate, "Taken");
                int updateInt = databaseHandler.updateVaccine(updateVaccineData);
                System.out.println("check vccine update status : " + updateInt);

                showInterstitial();

                startActivityMethod();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        dialogTakenDateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog(999);
            }
        });
        builder.create().show();

    }

    private List<String> getGroupsNamesFromCursor(Cursor vaccinTableDataCursor) {
        groupNames = new ArrayList<String>();

        if (vaccinTableDataCursor.moveToFirst()) {
            do {
                String groupName = null;
                groupName = vaccinTableDataCursor.getString(1);

                if (!groupNames.contains(groupName)) {
                    groupNames.add(groupName);
                }
            } while (vaccinTableDataCursor.moveToNext());
        }

        return groupNames;
    }


    private ArrayList<VaccineGroupItem> createImmunizationList() {
        ArrayList<VaccineGroupItem> parentList = new ArrayList<VaccineGroupItem>();
        Date vaccineDate = new Date();
        currentDate = new Date();
        vaccineCompletedCount = 0;
        DateFormat vaccineDateFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());

        for (int i = 0; i < groupNames.size(); i++) {

            VaccineGroupItem parentItem = new VaccineGroupItem();

            parentItem.setName(groupNames.get(i));
            ArrayList<VaccineChildItem> childList = new ArrayList<VaccineChildItem>();

            if (vaccinTableDataCursor.moveToFirst()) {
                do {
                    if (vaccinTableDataCursor.getString(1).equals(groupNames.get(i))) {
                        System.out.println("vaccinTableDataCursor.getString(3) :" + vaccinTableDataCursor.getString(3));
                        VaccineChildItem childItem = new VaccineChildItem();

                        if (vaccinTableDataCursor.getString(5).equals("Pending")) {
                            childItem.setImage(R.drawable.blank_bg);
                            childItem.setVaccineStatus("Pending");
                        }
                        if (vaccinTableDataCursor.getString(5).equals("Taken")) {
                            childItem.setImage(R.drawable.ok_48);
                            childItem.setVaccineStatus(vaccinTableDataCursor.getString(5));
                            vaccineCompletedCount++;
                        }
                        childItem.setVaccineId(vaccinTableDataCursor.getString(0));
                        childItem.setVaccineGroup(vaccinTableDataCursor.getString(1));
                        childItem.setName(vaccinTableDataCursor.getString(2));
                        childItem.setDuedt(vaccinTableDataCursor.getString(3));
                        //getting date for upcoming vaccine operation
                        try {
                            vaccineDate = vaccineDateFormat.parse(vaccinTableDataCursor.getString(3));
//                            System.out.println("vaccineDate : " + vaccineDate);
//                            System.out.println("currentDate : " + currentDate);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        if (vaccineDate.compareTo(currentDate) <= 0) {
                            currentGroup = groupNames.get(i);
                            currentGroupIndex = i;
                            System.out.println("currentGroup : " + currentGroup);
                        }
                        childItem.setTakendate(vaccinTableDataCursor.getString(4));
                        childList.add(childItem);

                        parentItem.setItems(childList);
                    }
                } while (vaccinTableDataCursor.moveToNext());

            }
            parentList.add(parentItem);

        }
        return parentList;
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
        StringBuilder selectedTakenDate = new StringBuilder().append(day).append("-")
                .append(monthArray[month - 1]).append("-").append(yearTrimmed);
        dialogTakenDateEditText.setText(selectedTakenDate);

    }

}