package com.maakservicess.beingparents.app_monitor.activities;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * Created by SAI PC on 3/3/2016.
 */
public class TrivandrumChart extends Activity {

    ImageView leftBtn;
    ImageView rightBtn;
    View pointer;
    Handler m_handler;
    Runnable m_handlerTask;
    int currentPosition = 10;
    SessionManager sessionManager;
    private AdView mAdViewLeft;
    private AdView mAdViewRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trivandrum_chart);
        pointer = (View) findViewById(R.id.pointer);
        leftBtn = (ImageView) findViewById(R.id.trivendrumChartLeftNavigateBtn);
        rightBtn = (ImageView) findViewById(R.id.trivendrumChartRightNavigateBtn);
        sessionManager = new SessionManager(getApplicationContext());
        String babyDOB = sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB);

        mAdViewLeft = (AdView) findViewById(R.id.trivendrum_left_AdView);
        mAdViewRight = (AdView) findViewById(R.id.trivendrum_right_AdView);
        AdRequest adRequestLeft = new AdRequest.Builder().build();
        AdRequest adRequestRight = new AdRequest.Builder().build();
        mAdViewLeft.loadAd(adRequestLeft);
        mAdViewRight.loadAd(adRequestRight);

        getBabyAgeInMonths(babyDOB);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 260);
        lp.weight = currentPosition;
        pointer.setLayoutParams(lp);

        //ad listener to set visibility of banner
        mAdViewLeft.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdViewLeft.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdViewLeft.setVisibility(View.GONE);
            }

        });//ad listener to set visibility of banner
        mAdViewRight.setAdListener(new AdListener() {

            @Override
            public void onAdLoaded() {
                mAdViewRight.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAdFailedToLoad(int error) {
                mAdViewRight.setVisibility(View.GONE);
            }

        });

        leftBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Left clicked");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 260);
                currentPosition -= 10f;
                if (currentPosition <= 10) {
                    currentPosition = 10;
                }
                lp.weight = currentPosition;
                pointer.setLayoutParams(lp);
            }
        });
        rightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("Right clicked");
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(0, 260);
                currentPosition += 10f;
                if (currentPosition >= 250) {
                    currentPosition = 250;
                }
                lp.weight = currentPosition;
                pointer.setLayoutParams(lp);
            }
        });

    }

    private void getBabyAgeInMonths(String babyDob) {

        int years = 0;
        int months = 0;
        int days = 0;

        String babyAge = "";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
        Date dateDOB = null;

        try {
            dateDOB = dateFormat.parse(babyDob);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //create calendar object for birth day
        Calendar birthDay = Calendar.getInstance();
        birthDay.setTimeInMillis(dateDOB.getTime());

        //create calendar object for current day
        long currentTime = System.currentTimeMillis();
        Calendar now = Calendar.getInstance();
        now.setTimeInMillis(currentTime);

        //Get difference between years
        years = now.get(Calendar.YEAR) - birthDay.get(Calendar.YEAR);
        int currMonth = now.get(Calendar.MONTH) + 1;
        int birthMonth = birthDay.get(Calendar.MONTH) + 1;

        //Get difference between months
        months = currMonth - birthMonth;

        //get age in months
        currentPosition = (10 * ((years * 12) + months)) + 10;

        System.out.println("months : " + months);
        System.out.println("years : " + years);
        System.out.println("currentPosition : " + currentPosition);

        //validate age and maintain between 0-24 months
        if (currentPosition <= 10) {
            currentPosition = 10;
        }
        if (currentPosition >= 250) {
            currentPosition = 250;
        }
    }


}