package com.maakservicess.beingparents.app_monitor;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.maakservicess.beingparents.app_monitor.activities.Login_main;

import java.util.HashMap;

/**
 * Created by ajinkya on 10/20/2015.
 */
public class SessionManager {

    // Sharedpref file name
    private static final String PREF_NAME = "appPrefrences";

    // All Shared Preferences Keys
    // keys should be strings
    //data should be specific data type
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_BABY_GENDER = "gender";
    public static final String KEY_BABY_NAME = "name";
    public static final String KEY_BABY_DOB = "dateOfBirth";
    public static final String KEY_BABY_WEIGHT = "babyWeight";
    public static final String KEY_BABY_HEIGHT = "babyHeight";
    public static final String KEY_BABY_HEAD_CIRCUMFERENCE = "babyHeadCircumference";
    public static final String KEY_BABY_PROFILE_FILE_NAME = "profileFileName";
    public static final String KEY_BABY_DOCTOR_NAME = "doctorName";
    public static final String KEY_BABY_DOCTOR_CONTACT = "doctorContact";
    public static final String KEY_BABY_BLOOD_GROUP = "babyBloodGroup";
    public static final String KEY_ALLOW_NOTIFICATION_CHECK = "allowNotificationCheck";
    public static final String KEY_NOTIFICATION_TIME = "notificationTime";

    SharedPreferences pref;
    // Editor for Shared preferences
    SharedPreferences.Editor editor;
    // Context
    Context _context;
    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Constructor
    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public Boolean createLoginSession(String name, String gender, String dOB, float weight, float height, float headCircumference, String babyProfileFileName) {
        Log.d("INTO shapref CREAT SESS", "INTO shapref CREAT SESS");
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);
        // Storing name in pref
        editor.putString(KEY_BABY_NAME, name);
        editor.putString(KEY_BABY_PROFILE_FILE_NAME, babyProfileFileName);
        System.out.println("_SESSION KEY_BABY_PROFILE_FILE_NAME" + babyProfileFileName);

        editor.putString(KEY_BABY_GENDER, gender);
        Log.d("KEY_BABY_GENDER", "" + gender);

        editor.putString(KEY_BABY_DOB, dOB);
        Log.d("KEY_BABY_DOB", "" + dOB);

        editor.putFloat(KEY_BABY_WEIGHT, weight);
        Log.d("KEY_BABY_WEIGHT", "" + weight);
        editor.putFloat(KEY_BABY_HEIGHT, height);
        Log.d("KEY_BABY_HEIGHT", "" + height);
        editor.putFloat(KEY_BABY_HEAD_CIRCUMFERENCE, headCircumference);
        Log.d("KEY_HEAD_CIRCUMFERENCE", "" + headCircumference);
        editor.putString(KEY_ALLOW_NOTIFICATION_CHECK, "true");
        System.out.println("KEY_ALLOW_NOTIFICATION_CHECK : null");
        editor.putString(KEY_NOTIFICATION_TIME, "10:00");
        System.out.println("KEY_NOTIFICATION_TIME : 10:00");

        // commit changes
        editor.commit();

        return true;
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     */
    public void checkLogin() {
        // Check login status
        if (!this.isLoggedIn()) {
            Log.d("data null", "data null");

            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login_main.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        } else {
            Log.d("data present", "data present");

        }
    }

    /**
     * Get stored session data
     */

    public HashMap<String, String> getBabyDetails() {
        HashMap<String, String> baby = new HashMap<String, String>();


        baby.put(KEY_BABY_NAME, pref.getString(KEY_BABY_NAME, null));
        baby.put(KEY_BABY_GENDER, pref.getString(KEY_BABY_GENDER, null));
        baby.put(KEY_BABY_DOB, pref.getString(KEY_BABY_DOB, null));
        baby.put(KEY_BABY_PROFILE_FILE_NAME, pref.getString(KEY_BABY_PROFILE_FILE_NAME, null));
        baby.put(KEY_BABY_DOCTOR_NAME, pref.getString(KEY_BABY_DOCTOR_NAME, null));
        baby.put(KEY_BABY_DOCTOR_CONTACT, pref.getString(KEY_BABY_DOCTOR_CONTACT, null));
        baby.put(KEY_BABY_BLOOD_GROUP, pref.getString(KEY_BABY_BLOOD_GROUP, null));

        baby.put(KEY_BABY_WEIGHT, String.valueOf(pref.getFloat(KEY_BABY_WEIGHT, 0.0f)));
        baby.put(KEY_BABY_HEIGHT, String.valueOf(pref.getFloat(KEY_BABY_HEIGHT, 0.0f)));
        baby.put(KEY_BABY_HEAD_CIRCUMFERENCE, String.valueOf(pref.getFloat(KEY_BABY_HEAD_CIRCUMFERENCE, 0.0f)));
        baby.put(KEY_ALLOW_NOTIFICATION_CHECK, pref.getString(KEY_ALLOW_NOTIFICATION_CHECK, null));
        baby.put(KEY_NOTIFICATION_TIME, pref.getString(KEY_NOTIFICATION_TIME, null));


        // return user
        return baby;
    }

    public String getSpecificBabyDetail(String dataKEY) {

        String itemValue = pref.getString(dataKEY, null);

        return itemValue;
    }

    //Inserts single STRING value into shared pref
    public void setSpecificBabyDetail(String dataKEY, String dataValue) {
        editor.putString(dataKEY, dataValue);
        editor.commit();
    }

    //Inserts single FLOAT value into shared pref
    public void setSpecificBabyDetail(String dataKEY, Float dataValue) {
        editor.putFloat(dataKEY, dataValue);
        editor.commit();
    }

    /**
     * Clear session details
     */
    public void logoutUser() {
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login_main.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     **/
    // Get Login State
    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }
}