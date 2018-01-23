package com.maakservicess.beingparents.app_monitor.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.GrowthSqliteData;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

//import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;
//import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Created by ajinkya on 12/29/2015.
 */
public class Growth_main extends Activity {

    private FloatingActionButton addEntryFAB;
    private ArcProgress growthArcBar;
    private Timer timer;
    TextView sectionIndicatorTextView;
    TextView valueIndicatorTextView;
    TextView dateIndicatorTextView;
    TextView unitIndicatorTextView;
    RelativeLayout weightTabLayoutBtn;
    RelativeLayout heightTabLayoutBtn;
    RelativeLayout headCircumferenceTabLayoutBtn;
    String currentSection = "Weight";
    DatabaseHandler database;
    ArrayList<String> queryParamsArrayList;
    float[] percentile3value;
    float[] percentile15value;
    float[] percentile30value;
    float[] percentile50value;
    float[] percentile85value;
    float[] percentile70value;
    float[] percentile97value;
    float[] userbabypercentile;
    //    Spinner rangeSpinner;
    public static TextView rangeSpinner;
    Spinner genralSpineer;

    HashMap<String, String> babyDetailsMap;
    private SessionManager mainManager;
    private Cursor cursorData;
    private LineData data;
    private LineData babydata;
    private int cursorlength;
    List<String> rangeList;
    List<String> genralSpinnerValueList;
    ArrayAdapter rangeAdapter;
    ArrayAdapter genralSpinnerValueAdapter;
    private String spinnerSelectedRange;
    private String spinnerSelectedGenralValue;
    private String current_date_of_taken_growth_data;
    private String range_Date;
    String gender_of_user = null;
    private String category;
    private ImageView growthMenuImageView;
    String dataFetecingPara1;
    String dataFetecingPara2;
    SimpleDateFormat currentDateFormat;
    SimpleDateFormat targetDateFormat;
    private String range_that_send_to_database_for_update;
    private List<Float> babyDataList;
    private List<Float> userValuesList;
    private List<String> userValuesIDList;
    private View headTabBar;
    private View weightTabBar;
    private View heightTabBar;
    //    String latestEntryDateString = null;
    //temp variable
    String latestEntryDateString = "28-5-2016";
    Float latestEntryValue = 0.0f;
    Float latestUserPercentile = 0.0f;
    Float latestEntryP3 = 0.0f;
    Float latestEntryP15 = 0.0f;
    Float latestEntryP50 = 0.0f;
    Float latestEntryP85 = 0.0f;
    Float latestEntryP97 = 0.0f;
    private ProgressDialog growthProgress;
    String currentBabyWeight = null;
    String currentBabyHeight = null;
    String currentBabyHeadCircumference = null;
    SessionManager sessionManager;
    private String getDateOfBirth;
    public static Date dateObjectOfDOB;

    private InterstitialAd mInterstitialAd;
    private AdView mAdView;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.growth_main);

        mAdView = (AdView) findViewById(R.id.growth_main_AdView);
//        AdRequest adRequest = new AdRequest.Builder().build();
//        mAdView.loadAd(adRequest);

//        loadInterstitialAd();

        addEntryFAB = (FloatingActionButton) findViewById(R.id.addGrowthEntryFAB);
        addEntryFAB.hide();
        sessionManager = new SessionManager(getApplicationContext());
        growthArcBar = (ArcProgress) findViewById(R.id.growth_main_arc_progress_bar);

        sectionIndicatorTextView = (TextView) findViewById(R.id.growth_main_section_indicator_textView);
        valueIndicatorTextView = (TextView) findViewById(R.id.growth_main_value_indicator_textView);
        dateIndicatorTextView = (TextView) findViewById(R.id.growth_main_date_textView);
        unitIndicatorTextView = (TextView) findViewById(R.id.growth_main_unit_indicator_textView);
        weightTabLayoutBtn = (RelativeLayout) findViewById(R.id.growth_main_weight_tab);
        heightTabLayoutBtn = (RelativeLayout) findViewById(R.id.growth_main_height_tab);
        headCircumferenceTabLayoutBtn = (RelativeLayout) findViewById(R.id.growth_main_head_circumference_tab);
        growthMenuImageView = (ImageView) findViewById(R.id.growth_pop_menu);

        //tabselector bars
        headTabBar = (View) findViewById(R.id.headTabSelectedBar);
        weightTabBar = (View) findViewById(R.id.weightTabSelectedBar);
        heightTabBar = (View) findViewById(R.id.heightTabSelectedBar);

        mainManager = new SessionManager(getApplicationContext());
        babyDetailsMap = mainManager.getBabyDetails();
        gender_of_user = babyDetailsMap.get(SessionManager.KEY_BABY_GENDER);

        growthProgress = new ProgressDialog(Growth_main.this);
        growthProgress.setMessage("Please wait");
        growthProgress.setCancelable(false);

        if (gender_of_user.equals("Baby Boy")) {
            gender_of_user = "b";
            dataFetecingPara2 = "boy";
            dataFetecingPara1 = "w" + gender_of_user;
            System.out.println("gender_of_user dataFetecingPara1" + dataFetecingPara1);
        } else if (gender_of_user.equals("Baby Girl")) {
            gender_of_user = "g";
            dataFetecingPara2 = "girl";
            dataFetecingPara1 = "w" + gender_of_user;
            System.out.println("gender_of_user" + gender_of_user);
        }

        getDateOfBirth = sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB);
        System.out.println("getDateOfBirth______________________________________________________" + getDateOfBirth);
        dateObjectOfDOB = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-M-yyyy", Locale.getDefault());
        try {
            dateObjectOfDOB = dateFormat.parse(getDateOfBirth);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        database = new DatabaseHandler(this);
        rangeList = new ArrayList<>();
        genralSpinnerValueList = new ArrayList<>();

        standardDatafetch(dataFetecingPara1, dataFetecingPara2);
        babyDatafetch(dataFetecingPara1, dataFetecingPara2);
        loadWeightData();
        sectionIndicatorTextView.setText("Weight");
        unitIndicatorTextView.setText("kg");

        setTabIndicators();
        addEntryFAB.show();

        dataFetecingPara1 = "w" + gender_of_user;
        currentBabyWeight = currentDataFetch(dataFetecingPara1, dataFetecingPara2);

        dataFetecingPara1 = "h" + gender_of_user;
        currentBabyHeight = currentDataFetch(dataFetecingPara1, dataFetecingPara2);

        dataFetecingPara1 = "hc" + gender_of_user;
        currentBabyHeadCircumference = currentDataFetch(dataFetecingPara1, dataFetecingPara2);

        addEntryFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Fab Pressed");
                rangeList.clear();
                addEntryFAB.hide();
                genralSpinnerValueList.clear();
                addGrowthEntry(currentSection);
            }
        });

        weightTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentSection.equals("Weight")) {
                    String dataFetecingPara = "w" + gender_of_user;
                    System.out.println("data fecting para " + dataFetecingPara);
                    standardDatafetch(dataFetecingPara, dataFetecingPara2);
                    babyDatafetch(dataFetecingPara, dataFetecingPara2);
                    currentSection = "Weight";
                    setTabIndicators();
                    loadWeightData();
                    sectionIndicatorTextView.setText("Weight");
                    unitIndicatorTextView.setText("kg");
                }
            }
        });
        heightTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentSection.equals("Height")) {
                    String dataFetecingPara = "h" + gender_of_user;
                    System.out.println("data fecting para " + dataFetecingPara);
                    standardDatafetch(dataFetecingPara, dataFetecingPara2);
                    babyDatafetch(dataFetecingPara, dataFetecingPara2);
                    currentSection = "Height";
                    setTabIndicators();
                    loadHeightData();
                    sectionIndicatorTextView.setText("Height");
                    unitIndicatorTextView.setText("cm");

                }
            }
        });
        headCircumferenceTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!currentSection.equals("Head")) {
                    String dataFetecingPara = "hc" + gender_of_user;
                    System.out.println("data fecting para " + dataFetecingPara);
                    standardDatafetch(dataFetecingPara, dataFetecingPara2);
                    babyDatafetch(dataFetecingPara, dataFetecingPara2);
                    currentSection = "Head";
                    setTabIndicators();
                    loadHeadData();
                    sectionIndicatorTextView.setText("Head circumference");
                    unitIndicatorTextView.setText("cm");
                }
            }
        });

        growthMenuImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("growthMenuImageView  growthMenuImageView  growthMenuImageView  growthMenuImageView");
                PopupMenu popup = new PopupMenu(Growth_main.this, growthMenuImageView);
                popup.getMenuInflater().inflate(R.menu.growth_popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {

                        if (item.getTitle().equals("Share")) {
                            String growthDataShareString = "Growth of *" +
                                    mainManager.getSpecificBabyDetail(SessionManager.KEY_BABY_NAME) + "*\n\n*Latest measures* " +
                                    "\nWeight : " + currentBabyWeight + " Kg" +
                                    "\nHeight : " + currentBabyHeight + " Cm" +
                                    "\nHead cir. : " + currentBabyHeadCircumference + " Cm \n\n";

                            Intent nav_share_Intent = new Intent(android.content.Intent.ACTION_SEND);
                            nav_share_Intent.setType("text/plain");
                            nav_share_Intent.putExtra(android.content.Intent.EXTRA_TEXT, growthDataShareString);
                            startActivity(Intent.createChooser(nav_share_Intent, "Share via :"));

                        } else if (item.getTitle().equals("Overview Data")) {
                            Intent sharewIntent = new Intent(Growth_main.this, Growth_overview_data.class);
                            startActivity(sharewIntent);
                        }
//                         else if (item.getTitle().equals("Screen Shot")) {
//                            Intent sharewIntent = new Intent(Growth_main.this, Growth_overview_data.class);
//                            startActivity(sharewIntent);
//                        }

                        return false;
                    }
                });
                popup.show();
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


//        new MaterialShowcaseView.Builder(Growth_main.this)
//                .setTarget(addEntryFAB)
//                .setMaskColour(R.color.colorOffWhite)
//                .setDismissText("GOT IT")
//                .setContentText("Try adding a growth value and see it on graph")
//                .setDelay(500) // optional but starting animations immediately in onCreate can make them choppy
//                .singleUse("GROWTH_FAB_SHOWCASE") // provide a unique ID used to ensure it is only shown once
//                .show();


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
        sequence.singleUse("GROWTH_SHOWCASE_SEQUENCE");
        sequence.addSequenceItem(addEntryFAB,
                "Try adding a growth value and see it on graph", "NEXT");

        sequence.addSequenceItem(growthMenuImageView,
                "Overview all saved entries with this option", "SURE");

//        sequence.addSequenceItem(mButtonThree,
//                "This is button three", "GOT IT");

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


    private String currentDataFetch(String dataFetecingPara1, String gender) {
        System.out.println("INSIDE currentDataFetch");
        ArrayList<String> queryParamsArrayList = new ArrayList<>();
        queryParamsArrayList.add(dataFetecingPara1);
        queryParamsArrayList.add(gender);
        /*COMMENT NEXT ONE LINE (for futrure optimization)(also need some code adjustments for this comment)
         BABY DATA FETCH MTD OR LATEST BABY ENTRY DATA BOTH MTD USE ALMOST SAME RESOURCES BUT HAS BEEN WRITTEN TWICE.
         SO TO AVOID MULTIPLE QUERIES FOR SAME DATA SET FETCH THE BELOW
          LINE IS CAN BE COMMENTED AND "cursor data(public)" CAN BEEN USED
           IF, AFTER COMMENT "cursorData" HERE CANNOT GET DESIRED DATA FROM PUBLIC "cursorData" IMMEDIATELY UNCOMMENT THE BELOW LINE AND GET DATA HERE ITSELF
        * */
        Cursor cursorData = database.getAllGrowthDataByCursor(queryParamsArrayList);

        System.out.println("queryParamsArrayList " + queryParamsArrayList);
        System.out.println("getCount " + cursorData.getCount());

        if (cursorData.moveToLast()) {
            do {
                System.out.println("cursorData.getString(11)" + cursorData.getString(11));
                if (!cursorData.getString(11).equals("0")) {
                    System.out.println("data check--------" + cursorData.getString(11));
                    break;
                }
            } while (cursorData.moveToPrevious());
            return cursorData.getString(11);
        } else {
            return "N/A";
        }
    }

    private void setTabIndicators() {
        switch (currentSection) {
            case "Head":
                headTabBar.setVisibility(View.VISIBLE);
                heightTabBar.setVisibility(View.INVISIBLE);
                weightTabBar.setVisibility(View.INVISIBLE);
                break;
            case "Weight":
                headTabBar.setVisibility(View.INVISIBLE);
                heightTabBar.setVisibility(View.INVISIBLE);
                weightTabBar.setVisibility(View.VISIBLE);
                break;
            case "Height":
                headTabBar.setVisibility(View.INVISIBLE);
                heightTabBar.setVisibility(View.VISIBLE);
                weightTabBar.setVisibility(View.INVISIBLE);
                break;
        }
    }

    public void standardDatafetch(String para, String gender) {

        queryParamsArrayList = new ArrayList<>();
        queryParamsArrayList.add(para);
        queryParamsArrayList.add(gender);
        cursorData = database.getAllGrowthDataByCursor(queryParamsArrayList);
        System.out.println("getCount" + cursorData.getCount());

        cursorlength = cursorData.getCount();
        percentile3value = new float[cursorlength];
        percentile15value = new float[cursorlength];
        percentile30value = new float[cursorlength];
        percentile50value = new float[cursorlength];
        percentile70value = new float[cursorlength];
        percentile85value = new float[cursorlength];
        percentile97value = new float[cursorlength];

        if (cursorData.moveToFirst()) {
            int i = 0;
            do {
                System.out.println("IIIIIIIIIIIIIIIIIIIII DDDDDDDDDDDDDDDDDDDD : " + cursorData.getString(cursorData.getColumnIndex("id")));

                String percentile3 = cursorData.getString(3) + "f";
                String percentile15 = cursorData.getString(4) + "f";
                String percentile30 = cursorData.getString(5) + "f";
                String percentile50 = cursorData.getString(6) + "f";
                String percentile70 = cursorData.getString(7) + "f";
                String percentile85 = cursorData.getString(8) + "f";
                String percentile97 = cursorData.getString(9) + "f";

                if (i < cursorlength) {
                    percentile3value[i] = Float.valueOf(percentile3);
                    percentile15value[i] = Float.valueOf(percentile15);
                    percentile30value[i] = Float.valueOf(percentile30);
                    percentile50value[i] = Float.valueOf(percentile50);
                    percentile70value[i] = Float.valueOf(percentile70);
                    percentile85value[i] = Float.valueOf(percentile85);
                    percentile97value[i] = Float.valueOf(percentile97);
                    i++;
                }
            } while (cursorData.moveToNext());
        }
    }

    public void babyDatafetch(String para, String gender) {

        babyDataList = new ArrayList<>();

        queryParamsArrayList = new ArrayList<>();
        queryParamsArrayList.add(para);
        queryParamsArrayList.add(gender);
        cursorData = database.getAllGrowthDataByCursor(queryParamsArrayList);
        System.out.println("getCount" + cursorData.getCount());
        cursorlength = cursorData.getCount();

        if (cursorData.moveToFirst()) {
            do {
                String percentileuserbaby = cursorData.getString(11) + "f";
                if (!percentileuserbaby.equals("0f")) {
                    babyDataList.add(Float.valueOf(percentileuserbaby));
                    System.out.println("Float.valueOf(percentileuserbaby) " + Float.valueOf(percentileuserbaby));
                    latestEntryDateString = cursorData.getString(cursorData.getColumnIndex("entry_date"));
                    System.out.println("latestEntryDateString #########################################################################   " + latestEntryDateString);
                    latestEntryValue = Float.valueOf(cursorData.getString(cursorData.getColumnIndex("userBaby_1")));
                    latestEntryP3 = Float.valueOf(cursorData.getString(cursorData.getColumnIndex("percentile3")));
                    latestEntryP15 = Float.valueOf(cursorData.getString(cursorData.getColumnIndex("percentile15")));
                    latestEntryP50 = Float.valueOf(cursorData.getString(cursorData.getColumnIndex("percentile50")));
                    latestEntryP85 = Float.valueOf(cursorData.getString(cursorData.getColumnIndex("percentile85")));
                    latestEntryP97 = Float.valueOf(cursorData.getString(cursorData.getColumnIndex("percentile97")));

                }
            } while (cursorData.moveToNext());
            System.out.println("latest data " + latestEntryDateString + " " + latestEntryValue + " " +
                    latestEntryP3 + " " + latestEntryP15 + " " + latestEntryP50 + " " +
                    latestEntryP85 + " " + latestEntryP97);


            //logic to calculate percentle of user value
            if (latestEntryValue > 0 && latestEntryValue <= latestEntryP3) {
                System.out.println("IN 0 -3 ");
                latestUserPercentile = 3 * (latestEntryValue / latestEntryP3);

            } else if (latestEntryValue > latestEntryP3 && latestEntryValue <= latestEntryP15) {
                System.out.println("IN 3 - 15");
                latestUserPercentile = 3 + 12 * ((latestEntryValue - latestEntryP3) / (latestEntryP15 - latestEntryP3));

            } else if (latestEntryValue > latestEntryP15 && latestEntryValue <= latestEntryP50) {
                System.out.println("IN 15 - 50 ");
                latestUserPercentile = 15 + 35 * ((latestEntryValue - latestEntryP15) / (latestEntryP50 - latestEntryP15));

            } else if (latestEntryValue > latestEntryP50 && latestEntryValue <= latestEntryP85) {
                System.out.println("IN 50 - 85 ");
                latestUserPercentile = 50 + 35 * ((latestEntryValue - latestEntryP50) / (latestEntryP85 - latestEntryP50));

            } else if (latestEntryValue > latestEntryP85 && latestEntryValue <= latestEntryP97) {
                System.out.println("IN 85 - 97");
                latestUserPercentile = 85 + 12 * ((latestEntryValue - latestEntryP85) / (latestEntryP97 - latestEntryP85));

            } else {
                System.out.println("IN 97 - ");
                latestUserPercentile = 98.0f;
            }
            //logic to get latest data
            // DateFormat srcDateFormat = new SimpleDateFormat("dd-MM-yyyyy", Locale.getDefault());
            DateFormat srcDateFormat = new SimpleDateFormat("dd MMM yyyyy", Locale.getDefault());
            DateFormat targetDateFormat = new SimpleDateFormat("dd MMM", Locale.getDefault());
            try {
                Date measurementDate = srcDateFormat.parse(latestEntryDateString);

                dateIndicatorTextView.setText(targetDateFormat.format(measurementDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

        }
        userbabypercentile = new float[babyDataList.size()];

        int i = 0;
        for (Float value : babyDataList) {
            userbabypercentile[i] = value;
            i++;
        }

    }

    private ArrayList<LineDataSet> getDataSet() {
        ArrayList<LineDataSet> dataSets = new ArrayList<>();

        ArrayList<Entry> valueSetPercentile3 = new ArrayList<>();
        ArrayList<Entry> valueSetPercentile15 = new ArrayList<>();
        ArrayList<Entry> valueSetPercentile30 = new ArrayList<>();
        ArrayList<Entry> valueSetPercentile50 = new ArrayList<>();
        ArrayList<Entry> valueSetPercentile70 = new ArrayList<>();
        ArrayList<Entry> valueSetPercentile85 = new ArrayList<>();
        ArrayList<Entry> valueSetPercentile97 = new ArrayList<>();

        for (int loop = 0; loop < percentile3value.length; loop++) {

            Entry vep3 = new Entry(percentile3value[loop], loop);
            Entry vep15 = new Entry(percentile15value[loop], loop);
            Entry vep30 = new Entry(percentile30value[loop], loop);
            Entry vep50 = new Entry(percentile50value[loop], loop);
            Entry vep70 = new Entry(percentile70value[loop], loop);
            Entry vep85 = new Entry(percentile85value[loop], loop);
            Entry vep97 = new Entry(percentile97value[loop], loop);

            valueSetPercentile3.add(vep3);
            valueSetPercentile15.add(vep15);
            valueSetPercentile30.add(vep30);
            valueSetPercentile50.add(vep50);
            valueSetPercentile70.add(vep70);
            valueSetPercentile85.add(vep85);
            valueSetPercentile97.add(vep97);

        }

        LineDataSet lineDataSetObjP3 = new LineDataSet(valueSetPercentile3, "3% ");
        lineDataSetObjP3.setColor(Color.RED);
        lineDataSetObjP3.setDrawCubic(true);
        lineDataSetObjP3.setCircleSize(0);
        lineDataSetObjP3.setDrawValues(false);
        lineDataSetObjP3.setCubicIntensity(0.3f);
        lineDataSetObjP3.setLineWidth(0.4f);

        LineDataSet lineDataSetObjP15 = new LineDataSet(valueSetPercentile15, "15% ");
        lineDataSetObjP15.setColor(Color.parseColor("#F77721"));
        lineDataSetObjP15.setDrawCubic(true);
        lineDataSetObjP15.setCircleSize(0);
        lineDataSetObjP15.setDrawValues(false);
        lineDataSetObjP15.setCubicIntensity(0.3f);
        lineDataSetObjP15.setLineWidth(0.4f);

        LineDataSet lineDataSetObjP50 = new LineDataSet(valueSetPercentile50, "50% ");
        lineDataSetObjP50.setColor(Color.GREEN);
        lineDataSetObjP50.setDrawCubic(true);
        lineDataSetObjP50.setCircleSize(0);
        lineDataSetObjP50.setDrawValues(false);
        lineDataSetObjP50.setCubicIntensity(0.3f);
        lineDataSetObjP50.setLineWidth(0.4f);

        LineDataSet lineDataSetObjP85 = new LineDataSet(valueSetPercentile85, "85% ");
        lineDataSetObjP85.setColor(Color.parseColor("#F77721"));
        lineDataSetObjP85.setDrawCubic(true);
        lineDataSetObjP85.setCircleSize(0);
        lineDataSetObjP85.setDrawValues(false);
        lineDataSetObjP85.setCubicIntensity(0.3f);
        lineDataSetObjP85.setLineWidth(0.4f);

        LineDataSet lineDataSetObjP97 = new LineDataSet(valueSetPercentile97, "97% ");
        lineDataSetObjP97.setColor(Color.RED);
        lineDataSetObjP97.setDrawCubic(true);
        lineDataSetObjP97.setCircleSize(0);
        lineDataSetObjP97.setDrawValues(false);
        lineDataSetObjP97.setCubicIntensity(0.3f);
        lineDataSetObjP97.setLineWidth(0.4f);

        dataSets.add(lineDataSetObjP3);
        dataSets.add(lineDataSetObjP15);
        dataSets.add(lineDataSetObjP50);
        dataSets.add(lineDataSetObjP85);
        dataSets.add(lineDataSetObjP97);

        ArrayList<Entry> valueSetPercentileuserbaby = new ArrayList<>();

        for (int loop = 0; loop < babyDataList.size(); loop++) {

            Entry vepbabyuser = new Entry(userbabypercentile[loop], loop);

            valueSetPercentileuserbaby.add(vepbabyuser);
        }

        LineDataSet lineDataSetObjPUB = new LineDataSet(valueSetPercentileuserbaby, "Baby %");
        lineDataSetObjPUB.setColor(Color.BLACK);
        lineDataSetObjPUB.setCircleColor(Color.BLACK);
        lineDataSetObjPUB.setDrawCubic(true);
        lineDataSetObjPUB.setCircleSize(1.5f);
        lineDataSetObjPUB.setDrawValues(false);
        lineDataSetObjPUB.setCubicIntensity(0.2f);
        lineDataSetObjPUB.setLineWidth(0.8f);

        dataSets.add(lineDataSetObjPUB);

        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        for (int i = 0; i < cursorlength; i++) {
            xAxis.add(String.valueOf(i));
        }
        return xAxis;
    }

    void addGrowthEntry(final String section) {

        LayoutInflater flater = this.getLayoutInflater();
        View view = flater.inflate(R.layout.growth_main_entry_dialog_spinner, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setCancelable(false);
//        rangeSpinner = (Spinner) view.findViewById(R.id.range_spinner);
        rangeSpinner = (TextView) view.findViewById(R.id.range_spinner);
        genralSpineer = (Spinner) view.findViewById(R.id.weight_spinner);
        System.out.println("rangeSpinner 2: " + rangeSpinner);
        rangeSpinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment newFragment = new SelectDateFragment();
                newFragment.show(getFragmentManager(), "DatePicker");
            }
        });

        builder.setTitle("New " + section + " entry");


        switch (section) {
            case "Head":
                for (int loophe = 32; loophe <= 52; loophe++) {
                    String HeadCircumference;
                    int int_Height;
                    int_Height = (int) loophe;
                    HeadCircumference = Integer.toString(int_Height);
                    genralSpinnerValueList.add(HeadCircumference + " Cm");
                }
                genralSpinnerValueList.add("Select HeadCirum");
                genralSpinnerValueAdapter = new ArrayAdapter<String>(Growth_main.this, R.layout.range_spinner_item, genralSpinnerValueList);
                genralSpinnerValueAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
                genralSpineer.setAdapter(genralSpinnerValueAdapter);
                genralSpineer.setSelection(genralSpinnerValueAdapter.getCount() - 1);

                break;

            case "Height":

                for (int looph = 45; looph <= 120; looph++) {
                    String Height;
                    int int_Height;

                    int_Height = (int) looph;
                    Height = Integer.toString(int_Height);
                    genralSpinnerValueList.add(Height + " Cm");
                }
                genralSpinnerValueList.add("Select Height");
                genralSpinnerValueAdapter = new ArrayAdapter<String>(Growth_main.this, R.layout.range_spinner_item, genralSpinnerValueList);
                genralSpinnerValueAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
                genralSpineer.setAdapter(genralSpinnerValueAdapter);
                genralSpineer.setSelection(genralSpinnerValueAdapter.getCount() - 1);

                break;

            case "Weight":

                for (double loopw = 1; loopw <= 30; loopw = loopw + 0.5) {
                    String Weight;
                    float float_Weight;
                    float_Weight = (float) (loopw);
                    Weight = Float.toString(float_Weight);
                    genralSpinnerValueList.add(Weight + " Kg");
                }
                genralSpinnerValueList.add("Select Weight");
                genralSpinnerValueAdapter = new ArrayAdapter<String>(Growth_main.this, R.layout.range_spinner_item, genralSpinnerValueList);
                genralSpinnerValueAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
                genralSpineer.setAdapter(genralSpinnerValueAdapter);
                genralSpineer.setSelection(genralSpinnerValueAdapter.getCount() - 1);
                break;
        }

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("add pressed from : " + section);
                growthProgress.show();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        addUserEntry();
                        showInterstitial();
                    }
                }, 1000);
//                addEntryFAB.show();

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                addEntryFAB.show();
                showInterstitial();
            }
        });
        builder.create().show();

    }

    private void loadHeadData() {

        LineChart chart = (LineChart) findViewById(R.id.chart);
        data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(800, 1);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.setGridBackgroundColor(Color.TRANSPARENT);

        XAxis leftAxis = chart.getXAxis();

        chart.setScaleYEnabled(true);
        chart.getAxisLeft().setStartAtZero(false);
        chart.getAxisRight().setStartAtZero(false);
        YAxis yAxis = new YAxis();
        yAxis.setSpaceBottom(50);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        growthArcBar.setProgress(Math.round(latestUserPercentile));
        dataFetecingPara1 = "hc" + gender_of_user;
        currentBabyHeadCircumference = currentDataFetch(dataFetecingPara1, dataFetecingPara2);
        valueIndicatorTextView.setText(currentBabyHeadCircumference);

    }

    private void loadWeightData() {
        LineChart chart = (LineChart) findViewById(R.id.chart);
        data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(800, 1);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.setGridBackgroundColor(Color.TRANSPARENT);

        XAxis leftAxis = chart.getXAxis();

        chart.setScaleYEnabled(true);
        chart.getAxisLeft().setStartAtZero(false);
        chart.getAxisRight().setStartAtZero(false);

        YAxis yAxis = new YAxis();
        yAxis.setSpaceBottom(50);

        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        growthArcBar.setProgress(Math.round(latestUserPercentile));
        dataFetecingPara1 = "w" + gender_of_user;
        currentBabyWeight = currentDataFetch(dataFetecingPara1, dataFetecingPara2);
        valueIndicatorTextView.setText(currentBabyWeight);

    }

    private void loadHeightData() {

        LineChart chart = (LineChart) findViewById(R.id.chart);
        data = new LineData(getXAxisValues(), getDataSet());
        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(800, 1);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.setGridBackgroundColor(Color.TRANSPARENT);


        XAxis leftAxis = chart.getXAxis();

        chart.setScaleYEnabled(true);
        chart.getAxisLeft().setStartAtZero(false);
        chart.getAxisRight().setStartAtZero(false);
        YAxis yAxis = new YAxis();
        yAxis.setSpaceBottom(50);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

        growthArcBar.setProgress(Math.round(latestUserPercentile));
        dataFetecingPara1 = "h" + gender_of_user;
        currentBabyHeight = currentDataFetch(dataFetecingPara1, dataFetecingPara2);
        valueIndicatorTextView.setText(currentBabyHeight);
    }

    public void addUserEntry() {

        //  spinnerSelectedRange = rangeSpinner.getSelectedItem().toString();
        spinnerSelectedRange = rangeSpinner.getText().toString();
        spinnerSelectedGenralValue = genralSpineer.getSelectedItem().toString();

        spinnerSelectedGenralValue = spinnerSelectedGenralValue.substring(0, spinnerSelectedGenralValue.length() - 2).trim();
        Date currentDateToEnter = new Date();
        Date rangespinnerDate = null;
        int count = 0;
        int Monthcount = 0;
        currentDateToEnter.getTime();
        currentDateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());
        targetDateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            rangespinnerDate = currentDateFormat.parse(spinnerSelectedRange);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        current_date_of_taken_growth_data = spinnerSelectedRange;
        range_Date = targetDateFormat.format(rangespinnerDate);
        System.out.println("current_data_type   " + current_date_of_taken_growth_data);
        System.out.println("range_Date   " + range_Date);

        Calendar rangespinnerCalendarObject = Calendar.getInstance();
        Calendar startDateCalndarObject = Calendar.getInstance();
        Calendar endDateCandarObject = Calendar.getInstance();
        rangespinnerCalendarObject.setTime(rangespinnerDate);


        if (cursorData.moveToFirst()) {
            do {
                String rangeData = cursorData.getString(cursorData.getColumnIndex("range"));
                Pattern pattern = Pattern.compile("- *");
                Matcher matcher = pattern.matcher(rangeData);
                if (matcher.find()) {
                    String startDateString = rangeData.substring(0, matcher.start());
                    String endDateString = rangeData.substring(matcher.end());
                    SimpleDateFormat targetdateFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
                    try {
                        Date startDate = targetdateFormat.parse(startDateString);
                        Date endDate = targetdateFormat.parse(endDateString);
                        startDateCalndarObject.setTime(startDate);
                        endDateCandarObject.setTime(endDate);
                        int rangeDateMonth = (rangespinnerCalendarObject.get(Calendar.MONTH) + 1);
                        int startingDateMonth = (startDateCalndarObject.get(Calendar.MONTH) + 1);
                        int endingDateMonth = (endDateCandarObject.get(Calendar.MONTH) + 1);

                        if (rangespinnerCalendarObject.get(Calendar.YEAR) == startDateCalndarObject.get(Calendar.YEAR)) {

                            if (rangeDateMonth == startingDateMonth || rangeDateMonth == endingDateMonth) {
                                System.out.println("Month   Match");
                                if (rangespinnerDate.compareTo(startDate) > 0 && rangespinnerDate.compareTo(endDate) < 0) {
                                    System.out.println("Date Match");
                                    System.out.println("Range that send to database update" + rangeData);
                                    range_that_send_to_database_for_update = rangeData;

                                } else if (rangespinnerDate.compareTo(startDate) == 0) {
                                    range_that_send_to_database_for_update = rangeData;
                                    System.out.println("Date =========== Match");
                                    System.out.println("Range that send to database update" + rangeData);
                                } else {
                                    System.out.println("Date not Match");
                                }
                            } else {
                                System.out.println("Month not  Match");
                            }

                        } else {
                            System.out.println("Yesss Year  not Match");
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }

            } while (cursorData.moveToNext());
        }


        if (currentSection.equals("Height")) {
            category = "h" + gender_of_user;
            //System.out.println("current section with categaory " + category);

        } else if (currentSection.equals("Weight")) {
            category = "w" + gender_of_user;
            // System.out.println("current section with categaory " + category);

        } else if (currentSection.equals("Head")) {
            category = "hc" + gender_of_user;
            //  System.out.println("current section with categaory " + category);
        }
        System.out.println("1. " + range_that_send_to_database_for_update);
        System.out.println("2. " + spinnerSelectedGenralValue);
        System.out.println("4. " + range_Date);
        System.out.println("5. " + category);


        GrowthSqliteData addUserEntryGrowthData = new GrowthSqliteData(range_that_send_to_database_for_update, spinnerSelectedGenralValue, null, range_Date, category, null);
        int updtedrecord = database.updateUserGrowthData(addUserEntryGrowthData);
        System.out.println("updated Record @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ " + updtedrecord);
        if (updtedrecord == 1) {
            setAverageOnMissingValues();

        }
    }

    private void setAverageOnMissingValues() {
        Cursor averageCursorData = database.getAllGrowthDataByCursor(queryParamsArrayList);

        Boolean upperLimitOfListFoundStatus = false;
        userValuesList = new ArrayList<>();
        userValuesIDList = new ArrayList<>();
        if (averageCursorData.moveToLast()) {
            do {
                String userPercentileIdForUpdate = averageCursorData.getString(averageCursorData.getColumnIndex("id"));
                String userPercentilesForUpdate = averageCursorData.getString(averageCursorData.getColumnIndex("userBaby_1"));
                System.out.println("userPercentilesForUpdate : " + userPercentileIdForUpdate + " " + userPercentilesForUpdate);

                if (!upperLimitOfListFoundStatus) {
                    if (!averageCursorData.getString(averageCursorData.getColumnIndex("userBaby_1")).equals("0")) {
                        upperLimitOfListFoundStatus = true;
                    }
                }
                if (upperLimitOfListFoundStatus) {
                    if (averageCursorData.getString(averageCursorData.getColumnIndex("entry_date")) == null) {
                        System.out.println("INSIDE ENRTY DATE NULL");
                        userValuesList.add(0, 0.0f);
                    } else {
                        System.out.println("INSIDE user value");
                        userValuesList.add(0, Float.valueOf(averageCursorData.getString(averageCursorData.getColumnIndex("userBaby_1")) + "f"));
                    }
                    userValuesIDList.add(0, userPercentileIdForUpdate);
                }

            } while (averageCursorData.moveToPrevious());
        }
        System.out.println("------------------------------------------------------------------");
        System.out.println("userValuesList.size() : " + userValuesList.size());
        System.out.println("userValuesList : " + userValuesList);
//        userValuesList.set(0, Float.valueOf("5.0f"));
//        System.out.println("userValuesList after 5.0f add : " + userValuesList);

        int lowerLimit = 0;
        int upperLimit = 0;

        for (int avgLoop = 0; avgLoop < userValuesList.size(); avgLoop++) {
            System.out.println("looping uservalue" + userValuesList.get(avgLoop));

            if (userValuesList.get(avgLoop) == 0.0) {
                lowerLimit = avgLoop;
                System.out.println("IF");

                for (int secondAvgLoop = avgLoop; secondAvgLoop <= userValuesList.size(); secondAvgLoop++) {
                    if (userValuesList.get(secondAvgLoop) != 0.0) {
                        System.out.println("IF FOR");
                        upperLimit = secondAvgLoop;
                        setMissingValues(--lowerLimit, upperLimit);
                        break;
                    }
                    avgLoop++;
                }
            }
        }

        System.out.println("BEFORE UPDATE userValuesList : " + userValuesList);
        System.out.println("BEFORE UPDATE userValuesIDList : " + userValuesIDList);

        updateAverageValuesTodatabase();

    }

    private void updateAverageValuesTodatabase() {
        //Logic to update average values in database
        for (int insertNo = 0; insertNo < userValuesList.size(); insertNo++) {
            GrowthSqliteData userDeletedGrowthData = new GrowthSqliteData(null, String.valueOf(userValuesList.get(insertNo)), null, null, null, userValuesIDList.get(insertNo));
            int misssingValuesUpdatedRecord = database.userUpdatedGrowthRecord(userDeletedGrowthData);
            System.out.println("misssingValuesUpdatedRecord : " + misssingValuesUpdatedRecord);
        }

//        growthProgress.dismiss();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                growthProgress.dismiss();
            }
        }, 200);
        String dataFetecingPara = null;
        switch (currentSection) {
            case "Head":
                dataFetecingPara = "hc" + gender_of_user;
                System.out.println("data fecting para " + dataFetecingPara);
                standardDatafetch(dataFetecingPara, dataFetecingPara2);
                babyDatafetch(dataFetecingPara, dataFetecingPara2);
                loadHeadData();
                break;
            case "Weight":
                dataFetecingPara = "w" + gender_of_user;
                System.out.println("data fecting para " + dataFetecingPara);
                standardDatafetch(dataFetecingPara, dataFetecingPara2);
                babyDatafetch(dataFetecingPara, dataFetecingPara2);
                loadWeightData();
                break;
            case "Height":
                dataFetecingPara = "h" + gender_of_user;
                System.out.println("data fecting para " + dataFetecingPara);
                standardDatafetch(dataFetecingPara, dataFetecingPara2);
                babyDatafetch(dataFetecingPara, dataFetecingPara2);
                loadHeightData();
                break;
        }
        addEntryFAB.show();
    }

    private void setMissingValues(int lowerLimit, int upperLimit) {
        System.out.println("lowerLimit : " + lowerLimit + " upperLimit : " + upperLimit);
        float valueDiff;
        float stepValue;
        int positionDiff = 0;
        positionDiff = upperLimit - lowerLimit;
        valueDiff = userValuesList.get(upperLimit) - userValuesList.get(lowerLimit);
        stepValue = valueDiff / positionDiff;
        System.out.println("stepValue : " + stepValue);

        //condition to check if upper and lower values diff 0.0(if yes, then values to be added becomes zero which is not desired)
        //instead add the same (upper OE lower) value as average
        if (stepValue == 0.0) {
            float valueToAdd = userValuesList.get(lowerLimit);
            for (int i = ++lowerLimit; i < upperLimit; i++) {
                userValuesList.set(i, valueToAdd);
            }
        } else {
            //decimal precision to get values upto 2 decimal only
            //because missing values calculation gives #.########## formatted values
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.CEILING);

            float previousValue = userValuesList.get(lowerLimit);
            for (int i = ++lowerLimit; i < upperLimit; i++) {
                float valueToAdd = previousValue + stepValue;
                System.out.println("valueToAdd : " + valueToAdd);

                userValuesList.set(i, Float.parseFloat(df.format(valueToAdd)));
                previousValue = userValuesList.get(i);
            }
        }
        System.out.println("userValuesList AFTER MISSING VALUE ADDITION : " + userValuesList);
        System.out.println("userValuesIDList AFTER MISSING VALUE ADDITION : " + userValuesIDList);

//        //Logic to update average values in database
//        for (int insertNo = 0; insertNo < userValuesList.size(); insertNo++) {
//            GrowthSqliteData userDeletedGrowthData = new GrowthSqliteData(null, String.valueOf(userValuesList.get(insertNo)), null, null, null, userValuesIDList.get(insertNo));
//            int misssingValuesUpdatedRecord = database.userUpdatedGrowthRecord(userDeletedGrowthData);
//            System.out.println("misssingValuesUpdatedRecord : " + misssingValuesUpdatedRecord);
//        }
//
////        growthProgress.dismiss();
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                growthProgress.dismiss();
//            }
//        }, 200);
//        String dataFetecingPara = null;
//        switch (currentSection) {
//            case "Head":
//                dataFetecingPara = "hc" + gender_of_user;
//                System.out.println("data fecting para " + dataFetecingPara);
//                standardDatafetch(dataFetecingPara, dataFetecingPara2);
//                babyDatafetch(dataFetecingPara, dataFetecingPara2);
//                loadHeadData();
//                break;
//            case "Weight":
//                dataFetecingPara = "w" + gender_of_user;
//                System.out.println("data fecting para " + dataFetecingPara);
//                standardDatafetch(dataFetecingPara, dataFetecingPara2);
//                babyDatafetch(dataFetecingPara, dataFetecingPara2);
//                loadWeightData();
//                break;
//            case "Height":
//                dataFetecingPara = "h" + gender_of_user;
//                System.out.println("data fecting para " + dataFetecingPara);
//                standardDatafetch(dataFetecingPara, dataFetecingPara2);
//                babyDatafetch(dataFetecingPara, dataFetecingPara2);
//                loadHeightData();
//                break;
//        }
//        addEntryFAB.show();
    }

    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private static String date;
        String AM_PM;
        private int yy, mm, dd;
        private int mHour, mMinute;
        String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
        Date DOB = Growth_main.dateObjectOfDOB;
        Calendar calendarObjectOfDOB;
        private int yy_DOB, mm_DOB, dd_DOB;


        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            calendarObjectOfDOB = java.util.Calendar.getInstance();
            yy = calendar.get(java.util.Calendar.YEAR);
            mm = calendar.get(java.util.Calendar.MONTH);
            dd = calendar.get(java.util.Calendar.DAY_OF_MONTH);
            mHour = calendar.get(Calendar.HOUR);
            mMinute = calendar.get(Calendar.MINUTE);
            calendarObjectOfDOB.setTime(DOB);
            yy_DOB = calendarObjectOfDOB.get(Calendar.YEAR);
            mm_DOB = calendarObjectOfDOB.get(Calendar.MONTH);
            dd_DOB = calendarObjectOfDOB.get(Calendar.DAY_OF_MONTH);

            System.out.println("DOB year-------------- " + calendarObjectOfDOB.get(Calendar.YEAR));
            System.out.println("DOB month-------------- " + calendarObjectOfDOB.get(Calendar.MONTH));
            System.out.println("DOB dd-------------- " + calendarObjectOfDOB.get(Calendar.DAY_OF_MONTH));

            System.out.println(" year-------------- " + yy);
            System.out.println(" month-------------- " + mm);
            System.out.println(" dd-------------- " + dd);

            return new DatePickerDialog(getActivity(), this, yy, mm, dd) {
                @Override
                public void onDateChanged(DatePicker view, int year, int month, int day) {
                    super.onDateChanged(view, year, month, day);

                    if ((year < yy_DOB) || (year > yy)) {
                        System.out.println("Year Check contion");

                        view.updateDate(yy, mm, dd);
                    }

                    if ((month < mm_DOB && year == yy_DOB) || (month > mm && year == yy)) {
                        System.out.println("Month Check contion");
                        view.updateDate(yy, mm, dd);
                    } else {
                        System.out.println("Month else Check contion");
                    }

                    if ((day < dd_DOB && year == yy_DOB && month == mm_DOB) || (day > dd && year == yy && month == mm)) {
                        System.out.println("Date if Check contion");
                        view.updateDate(yy, mm, dd);
                    } else {
                        System.out.println("Date else Check contion");
                    }

                }

                @Override
                public void onClick(DialogInterface dialog, int doneBtn) {
                    if (doneBtn == BUTTON_POSITIVE) {
                        int year = getDatePicker().getYear();
                        int month = getDatePicker().getMonth() + 1;
                        int day = getDatePicker().getDayOfMonth();


                        SelectDateFragment.date = day + "-" + monthArray[--month] + "-" + year;
                        month++;
                        Growth_main.rangeSpinner.setText("" + date);


                    }
                    super.onClick(dialog, doneBtn);
                }
            };
        }

        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        setAverageOnMissingValues();

    }
}