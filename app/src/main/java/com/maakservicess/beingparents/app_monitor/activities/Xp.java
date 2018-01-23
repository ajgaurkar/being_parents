package com.maakservicess.beingparents.app_monitor.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;
import java.util.Timer;

/**
 * Created by ajinkya on 12/29/2015.
 */
public class Xp extends Activity {

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
    String currentSection = "Head";
    DatabaseHandler database;

    float[] value = {32f, 35f, 36.9f, 38.5f, 39.4f, 40.25f, 41f, 41.6f, 42.25f, 42.6f, 43f, 43.4f, 43.7f, 43.9f, 44.2f, 44.4f,
            44.5f, 44.7f, 44.9f, 45f, 45.2f, 45.3f, 45.45f, 45.6f, 45.7f, 45.8f, 45.95f, 46f, 46.1f, 46.25f, 46.3f,
            46.4f, 46.5f, 46.55f, 46.6f, 46.7f, 46.8f, 46.85f, 46.9f, 47f, 47.05f, 47.15f, 47.2f, 47.2f, 47.25f, 47.3f,
            47.4f, 47.45f, 47.45f, 47.5f, 47.5f, 47.6f, 47.65f, 47.7f, 47.7f, 47.75f, 47.8f, 47.8f, 47.9f, 47.95f, 48f};

    float[] valueTwo ={2.4f,3.2f,4.0f,4.6f,5.1f,5.5f,5.85f,6.1f,6.3f,6.55f,.8f,6.95f,7.15f,7.3f,7.5f,7.65f,7.85f,8.0f,8.2f,8.35f,8.5f,8.65f,8.8f,
            9.0f,9.2f,9.35f,9.45f,9.65f,9.8f,9.95f,10.1f,10.25f,10.4f,10.55f,10.7f,10.8f,10.95f,11.1f,11.25f,11.35f,11.5f,11.65f,11.8f,
            11.9f,12.0f,12.15f,12.25f,12.35f,12.5f,12.7f,12.8f,12.9f,13.0f,13.1f,13.25f,13.4f,13.55f,13.65f,13.75f,13.85f,14.0f};
    private SessionManager mainManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.growth_main);

        addEntryFAB = (FloatingActionButton) findViewById(R.id.addGrowthEntryFAB);
        growthArcBar = (ArcProgress) findViewById(R.id.growth_main_arc_progress_bar);

        sectionIndicatorTextView = (TextView) findViewById(R.id.growth_main_section_indicator_textView);
        valueIndicatorTextView = (TextView) findViewById(R.id.growth_main_value_indicator_textView);
        dateIndicatorTextView = (TextView) findViewById(R.id.growth_main_date_textView);
        unitIndicatorTextView = (TextView) findViewById(R.id.growth_main_unit_indicator_textView);

        weightTabLayoutBtn = (RelativeLayout) findViewById(R.id.growth_main_weight_tab);
        heightTabLayoutBtn = (RelativeLayout) findViewById(R.id.growth_main_height_tab);
        headCircumferenceTabLayoutBtn = (RelativeLayout) findViewById(R.id.growth_main_head_circumference_tab);

        mainManager = new SessionManager(getApplicationContext());

        database = new DatabaseHandler(this);

        loadHeadData();
        LineChart chart = (LineChart) findViewById(R.id.chart);

        LineData data = new LineData(getXAxisValues(), getDataSet());

        chart.setData(data);
        chart.setDescription("My Chart");
        chart.animateXY(1000, 1000);
        chart.invalidate();
        chart.getXAxis().setDrawGridLines(false);
        chart.setGridBackgroundColor(Color.TRANSPARENT);


        XAxis leftAxis = chart.getXAxis();


        LimitLine ll = new LimitLine(40f, "Limit line");
        ll.setLineColor(Color.RED);
        ll.setLineWidth(1f);
        ll.setTextColor(Color.BLACK);
        ll.setTextSize(12f);
        leftAxis.addLimitLine(ll);

        chart.setScaleYEnabled(true);
        chart.getAxisLeft().setStartAtZero(false);
        chart.getAxisRight().setStartAtZero(false);
        YAxis yAxis = new YAxis();
        yAxis.setSpaceBottom(50);
        XAxis xAxis = chart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);

//        timer = new Timer();
//        timer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        growthArcBar.setProgress(growthArcBar.getProgress() + 1);
//                    }
//                });
//            }
//        }, 5, 10);

        addEntryFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Fab Pressed");
                addGrowthEntry(currentSection);
            }
        });

        weightTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadWeightData();
                currentSection = "Weight";
                sectionIndicatorTextView.setText("Weight");
                valueIndicatorTextView.setText("7");
                dateIndicatorTextView.setText("30 Sep 16");
                unitIndicatorTextView.setText("kg");
            }
        });
        heightTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHeightData();
                currentSection = "Height";
                sectionIndicatorTextView.setText("Height");
                valueIndicatorTextView.setText("145");
                dateIndicatorTextView.setText("12 Aug 16");
                unitIndicatorTextView.setText("cm");
            }
        });
        headCircumferenceTabLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadHeadData();
                currentSection = "Head";
                sectionIndicatorTextView.setText("Head circumference");
                valueIndicatorTextView.setText("32");
                dateIndicatorTextView.setText("19 Oct 16");
                unitIndicatorTextView.setText("cm");
            }
        });

    }

    private ArrayList<LineDataSet> getDataSet() {
        ArrayList<LineDataSet> dataSets = null;


        ArrayList<Entry> valueSet1 = new ArrayList<>();
        ArrayList<Entry> valueSetTwo = new ArrayList<>();

        for (int loop = 1; loop < value.length; loop++) {

            Entry v1e = new Entry(value[loop], loop); // Jan
            Entry v1eTwo = new Entry(valueTwo[loop], loop); // Jan
            valueSet1.add(v1e);
            valueSetTwo.add(v1eTwo);
        }

        LineDataSet lineDataSetObj = new LineDataSet(valueSet1, "50 percentile");
        lineDataSetObj.setColor(Color.rgb(0, 155, 0));
        lineDataSetObj.setDrawCubic(true);
        lineDataSetObj.setCircleSize(0);
        lineDataSetObj.setDrawValues(false);
        lineDataSetObj.setCubicIntensity(0f);
        lineDataSetObj.setLineWidth(0.3f);
        dataSets = new ArrayList<>();

        LineDataSet lineDataSetObjTwo = new LineDataSet(valueSetTwo, "50 percentile");
        lineDataSetObjTwo.setColor(Color.rgb(0, 155, 0));
        lineDataSetObjTwo.setDrawCubic(true);
        lineDataSetObjTwo.setCircleSize(0);
        lineDataSetObjTwo.setDrawValues(false);
        lineDataSetObjTwo.setCubicIntensity(0f);
        lineDataSetObjTwo.setLineWidth(0.3f);

        dataSets.add(lineDataSetObj);
        dataSets.add(lineDataSetObjTwo);
        return dataSets;
    }

    private ArrayList<String> getXAxisValues() {
        ArrayList<String> xAxis = new ArrayList<>();
        xAxis.add("1");
        xAxis.add("2");
        xAxis.add("3");
        xAxis.add("4");
        xAxis.add("5");
        xAxis.add("6");
        xAxis.add("7");
        xAxis.add("8");
        xAxis.add("9");
        xAxis.add("10");
        xAxis.add("11");
        xAxis.add("12");
        xAxis.add("13");
        xAxis.add("14");
        xAxis.add("15");
        xAxis.add("16");
        xAxis.add("17");
        xAxis.add("18");
        xAxis.add("19");
        xAxis.add("20");
        xAxis.add("21");
        xAxis.add("22");
        xAxis.add("23");
        xAxis.add("24");
        xAxis.add("25");
        xAxis.add("26");
        xAxis.add("27");
        xAxis.add("28");
        xAxis.add("29");
        xAxis.add("30");
        xAxis.add("31");
        xAxis.add("32");
        xAxis.add("34");
        xAxis.add("35");
        xAxis.add("36");
        xAxis.add("37");
        xAxis.add("38");
        xAxis.add("39");
        xAxis.add("36");
        xAxis.add("37");
        xAxis.add("38");
        xAxis.add("39");
        xAxis.add("40");
        xAxis.add("41");
        xAxis.add("42");
        xAxis.add("43");
        xAxis.add("44");
        xAxis.add("45");
        xAxis.add("46");
        xAxis.add("47");
        xAxis.add("48");
        xAxis.add("49");
        xAxis.add("50");
        xAxis.add("51");
        xAxis.add("52");
        xAxis.add("53");
        xAxis.add("54");
        xAxis.add("55");
        xAxis.add("56");
        xAxis.add("57");
        xAxis.add("58");
        xAxis.add("59");
        xAxis.add("60");
        return xAxis;
    }

    void addGrowthEntry(final String section) {

        LayoutInflater flater = this.getLayoutInflater();
        View view = flater.inflate(R.layout.growth_main_entry_dialog_spinner, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("New " + section + " entry");

        switch (section) {
            case "Head":
                break;

            case "Height":
                break;

            case "Weight":
                break;
        }

        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("add pressed from : " + section);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.create().show();

    }

    private void loadHeadData() {
        growthArcBar.setProgress(60);
    }

    private void loadWeightData() {
        growthArcBar.setProgress(38);
    }

    private void loadHeightData() {
        growthArcBar.setProgress(90);
    }

}