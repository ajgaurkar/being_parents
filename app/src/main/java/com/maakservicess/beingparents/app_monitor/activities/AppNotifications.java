package com.maakservicess.beingparents.app_monitor.activities;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.maakservicess.beingparents.app_monitor.Adapters.NotificationsAdapter;
import com.maakservicess.beingparents.app_monitor.AddNotificationDates;
import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.NotificationsListData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.ArrayList;

/**
 * Created by SAI PC on 9/18/2016.
 */
public class AppNotifications extends AppCompatActivity {


    private SessionManager mainManager;
    private ListView notificationsListView;
    ArrayList<NotificationsListData> notificationsList;
    private DatabaseHandler databaseHandler;
    private Cursor notificationCursor;
    private Boolean notificationCheck;
    private int selectedHour;
    private int selectedMinute;
    private String notificationTimeString;
    private RelativeLayout notificationDisabledLayout;
    private NotificationsAdapter notificationsAdapter;
    private RelativeLayout no_notification_Layout;
    private AdView mAdView;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_notifications);

        mAdView = (AdView) findViewById(R.id.appNotificationAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        databaseHandler = new DatabaseHandler(getApplicationContext());
        mainManager = new SessionManager(getApplicationContext());
        notificationsListView = (ListView) findViewById(R.id.notifications_List_View);
        notificationDisabledLayout = (RelativeLayout) findViewById(R.id.notification_disabled_layout);
        no_notification_Layout = (RelativeLayout) findViewById(R.id.no_notification_layout);
        notificationDisabledLayout.setVisibility(View.INVISIBLE);
        no_notification_Layout.setVisibility(View.INVISIBLE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        notificationsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                NotificationsListData selectedNotificationListData = notificationsList.get(position);

                //update notification as raed
                //COMMENT BECAUSE READ/UNREAD STATUS NOT DISPLAYED PROPERLY
//                if (!(selectedNotificationListData.getReadStatus() == 1)) {
//                    databaseHandler.markNotificationAsRead(String.valueOf(selectedNotificationListData.getId()));
//
//                    selectedNotificationListData.setReadStatus(1);
//                    int index = notificationsListView.getFirstVisiblePosition();
//                    View v = notificationsListView.getChildAt(0);
//                    int top = (v == null) ? 0 : v.getTop();
//
//                    notificationsAdapter.notifyDataSetChanged();
//                    //attendanceListView.setAdapter(attendanceRefreshAdapter);
//                    notificationsListView.setSelectionFromTop(index, top);
//
//                }

                showNotificationDetailDialog(selectedNotificationListData);

            }
        });
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
    }

    private void showNotificationDetailDialog(NotificationsListData selectedNotificationListData) {


        Dialog alertDialog = new Dialog(this);
        LayoutInflater flater = this.getLayoutInflater();
        View view = flater.inflate(R.layout.notification_detail_dialog, null);

        alertDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        alertDialog.setContentView(view);
        alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView headerTextView = (TextView) view.findViewById(R.id.notificationDialogHeaderTextView);
        TextView descriptionTextView = (TextView) view.findViewById(R.id.notificationDialogDescriptionTextView);
        ImageView typeImageView = (ImageView) view.findViewById(R.id.notificationDialogImageView);

        headerTextView.setText(selectedNotificationListData.getNotificationHeader());
        descriptionTextView.setText(selectedNotificationListData.getNotificationDescription());
        typeImageView.setImageResource(selectedNotificationListData.getImgId());

        //line to prevent dimming effect behind dialog
        //alertDialog.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        alertDialog.show();


    }

    private void populateList() {

        notificationsList = new ArrayList<>();

        notificationCursor = databaseHandler.getAllNotification();
        if (notificationCursor.moveToFirst()) {
            do {

                System.out.println("-------------");
                System.out.println("id : " + notificationCursor.getString(notificationCursor.getColumnIndex("id")));
                System.out.println("notificationHeader : " + notificationCursor.getString(notificationCursor.getColumnIndex("notificationHeader")));
                System.out.println("notificationText : " + notificationCursor.getString(notificationCursor.getColumnIndex("notificationText")));
                System.out.println("notificationImgId : " + notificationCursor.getString(notificationCursor.getColumnIndex("notificationImgId")));
                System.out.println("notificationShowStatus : " + notificationCursor.getString(notificationCursor.getColumnIndex("notificationShowStatus")));
                System.out.println("notificationReadStatus : " + notificationCursor.getString(notificationCursor.getColumnIndex("notificationReadStatus")));
                System.out.println("notificationTimeStamp : " + notificationCursor.getString(notificationCursor.getColumnIndex("notificationTimeStamp")));
                System.out.println("");

                if (Integer.parseInt(notificationCursor.getString(notificationCursor.getColumnIndex("notificationShowStatus"))) == 1) {

                    System.out.println("SHOW NOTIFICATION TRUE ID : " + notificationCursor.getString(notificationCursor.getColumnIndex("id")));

                    notificationsList.add(new NotificationsListData(
                            Integer.parseInt(notificationCursor.getString(notificationCursor.getColumnIndex("id"))),
                            notificationCursor.getString(notificationCursor.getColumnIndex("notificationHeader")),
                            notificationCursor.getString(notificationCursor.getColumnIndex("notificationText")),
                            Integer.parseInt(notificationCursor.getString(notificationCursor.getColumnIndex("notificationImgId"))),
                            notificationCursor.getInt(notificationCursor.getColumnIndex("notificationShowStatus")),
                            notificationCursor.getInt(notificationCursor.getColumnIndex("notificationReadStatus")),
                            notificationCursor.getLong(notificationCursor.getColumnIndex("notificationTimeStamp"))));
                }
            } while (notificationCursor.moveToNext());
        }

        if (notificationsList.size() == 0) {
            System.out.println("SORRY NO NOTIFICATIONS MESSAGE");
            no_notification_Layout.setVisibility(View.VISIBLE);
        } else {
            notificationsAdapter = new NotificationsAdapter(getApplicationContext(), notificationsList);
            notificationsListView.setAdapter(notificationsAdapter);
            no_notification_Layout.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            case R.id.options_notification_settings:
                System.out.println("settings pressed");
                openNotificationSettingsDialog();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void openNotificationSettingsDialog() {

        notificationCheck = Boolean.valueOf(mainManager.getSpecificBabyDetail(SessionManager.KEY_ALLOW_NOTIFICATION_CHECK));
        System.out.println("notificationCheck : " + notificationCheck);
        notificationTimeString = mainManager.getSpecificBabyDetail(SessionManager.KEY_NOTIFICATION_TIME);
        System.out.println("notificationTimeString : " + notificationTimeString);

        progressDialog = new ProgressDialog(AppNotifications.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loding...");


        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(AppNotifications.this);

        LayoutInflater layoutInflater = this.getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.notification_settings_dialog, null);
        final SwitchCompat notificationSwitch = (SwitchCompat) view.findViewById(R.id.notificationSwitch);
        final TimePicker notificationTimePicker = (TimePicker) view.findViewById(R.id.notificationTimePicker);
        notificationTimePicker.setIs24HourView(true);

        alertDialog.setView(view);
        alertDialog.setTitle("Settings");
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                System.out.println("settings SAVED notificationCheck : " + notificationCheck);
                mainManager.setSpecificBabyDetail(SessionManager.KEY_ALLOW_NOTIFICATION_CHECK, String.valueOf(notificationCheck));

                AddNotificationDates addNotificationDates = new AddNotificationDates();
                long notificationTime = 0;

                //condition to check whether to set alarm (based on notificationCheck true or false)
                if (notificationCheck) {

                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        System.out.println("save btn M");
                        selectedMinute = notificationTimePicker.getMinute();
                        selectedHour = notificationTimePicker.getHour();
                    } else {
                        System.out.println("save btn LOWER");
                        selectedHour = notificationTimePicker.getCurrentHour();
                        selectedMinute = notificationTimePicker.getCurrentMinute();
                    }

                    mainManager.setSpecificBabyDetail(SessionManager.KEY_NOTIFICATION_TIME, selectedHour + ":" + selectedMinute);

                    notificationTime = notificationTime + (selectedHour * 60 * 60 * 1000) + (selectedMinute * 60 * 1000);
                    System.out.println("notificationTime " + notificationTime);

                    //change notification time and reset all alarms
                    addNotificationDates.setNotifications(AppNotifications.this, mainManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB), notificationTime);
                } else {
                    //NOTIFICATION CHECK IS FALSE. CANCEL ALL NOTIFICATIONS
                    addNotificationDates.cancelAllNotifications(AppNotifications.this, mainManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB), notificationTime);

                }
                checkNotificationStatusToSetMsg();

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //reset check to old value if save not pressed
                notificationCheck = Boolean.valueOf(mainManager.getSpecificBabyDetail(SessionManager.KEY_ALLOW_NOTIFICATION_CHECK));
                checkNotificationStatusToSetMsg();

            }
        });
        alertDialog.show();

        //set switch to shared pref value
        notificationSwitch.setChecked(notificationCheck);
        //set default time on time picker
//        notificationTimePicker.setIs24HourView(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            System.out.println("marshmallow houuuuuuuuuuuur " + Integer.parseInt(notificationTimeString.substring(0, notificationTimeString.indexOf(":"))));
            System.out.println("marshmallow minutttttttttttttte " + Integer.parseInt(notificationTimeString.substring(notificationTimeString.indexOf(":") + 1, notificationTimeString.length())));
            notificationTimePicker.setHour(Integer.parseInt(notificationTimeString.substring(0, notificationTimeString.indexOf(":"))));
            notificationTimePicker.setMinute(Integer.parseInt(notificationTimeString.substring(notificationTimeString.indexOf(":") + 1, notificationTimeString.length())));
        } else {
            System.out.println("lower houuuuuuuuuuuur " + Integer.parseInt(notificationTimeString.substring(0, notificationTimeString.indexOf(":"))));
            System.out.println("lower minutttttttttttttte " + Integer.parseInt(notificationTimeString.substring(notificationTimeString.indexOf(":") + 1, notificationTimeString.length())));
            notificationTimePicker.setCurrentHour(Integer.parseInt(notificationTimeString.substring(0, notificationTimeString.indexOf(":"))));
            notificationTimePicker.setCurrentMinute(Integer.parseInt(notificationTimeString.substring(notificationTimeString.indexOf(":") + 1, notificationTimeString.length())));
        }


        //show/hide time picker based on sharedPref value
        if (notificationCheck) {
            notificationTimePicker.setVisibility(View.VISIBLE);
            notificationSwitch.setBackgroundResource(R.drawable.notification_switch_normal_bg);

        } else {
            notificationTimePicker.setVisibility(View.GONE);
            notificationSwitch.setBackgroundResource(R.drawable.notification_switch_curved_bg);

        }

        notificationSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                System.out.println("isChecked : " + isChecked);

                if (isChecked) {
                    notificationTimePicker.setVisibility(View.VISIBLE);
                    notificationSwitch.setBackgroundResource(R.drawable.notification_switch_normal_bg);
                    notificationCheck = true;
                } else {
                    notificationTimePicker.setVisibility(View.GONE);
                    notificationSwitch.setBackgroundResource(R.drawable.notification_switch_curved_bg);
                    notificationCheck = false;
                }

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.notification_settings, menu);
        return true;
    }

    @Override
    public void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("ONRESUME CALLED");
        checkNotificationStatusToSetMsg();

    }

    //Method to check NotificationStatus and display main page msg
    private boolean checkNotificationStatusToSetMsg() {
        notificationCheck = Boolean.valueOf(mainManager.getSpecificBabyDetail(SessionManager.KEY_ALLOW_NOTIFICATION_CHECK));
        System.out.println("notificationCheck : " + notificationCheck);

        if (notificationCheck) {
            System.out.println("NOTIFICATIONS ARE ON : POPULATE LIST");
            notificationDisabledLayout.setVisibility(View.GONE);
            populateList();
        } else {
            System.out.println("NOTIFICATIONS ARE TURNED OFF : SHOW MESSAGE");
            notificationDisabledLayout.setVisibility(View.VISIBLE);

            notificationsList = new ArrayList<>();
            notificationsAdapter = new NotificationsAdapter(getApplicationContext(), notificationsList);
            notificationsListView.setAdapter(notificationsAdapter);
        }

        return notificationCheck;
    }
}
