package com.maakservicess.beingparents.app_monitor;

/**
 * Created by amey on 2/22/2016.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.maakservicess.beingparents.app_monitor.controllers.CalendarEntryListData;
import com.maakservicess.beingparents.app_monitor.controllers.DevelopmentListData;
import com.maakservicess.beingparents.app_monitor.controllers.GrowthSqliteData;
import com.maakservicess.beingparents.app_monitor.controllers.NotificationsListData;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineSqliteData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Amey on 2/21/2016.
 */
public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "gobalBabyAppDatabase";
    private static final String TABLE_VACCINE = "vaccine";
    private static final String TABLE_GROWTH = "growth";
    private static final String TABLE_DEVELOPMENT = "development";
    private static final String TABLE_CALENDAR = "calendar";
    private static final String TABLE_NOTIFICATIONS = "notifications";

    //VACCINE TABLE COLUMNS
    private static final String KEY_VACCINE_ID = "id";
    private static final String KEY_GROUP = "vaccineGroup";
    private static final String KEY_VACCINE_NAME = "vaccineName";
    private static final String KEY_VACCINE_DUEDATE = "vaccinedueDate";
    private static final String KEY_VACCINE_TAKENDATE = "vaccineTakenDate";
    private static final String KEY_VACCINE_STATUS = "vaccineStatus";

    //NOTIFICATIONS TABLE COLUMNS
    private static final String KEY_NOTIFICATIONS_ID = "id";
    private static final String KEY_NOTIFICATIONS_HEADER = "notificationHeader";
    private static final String KEY_NOTIFICATIONS_TEXT = "notificationText";
    private static final String KEY_NOTIFICATIONS_IMG_ID = "notificationImgId";
    private static final String KEY_NOTIFICATIONS_SHOW_STATUS = "notificationShowStatus";
    private static final String KEY_NOTIFICATIONS_READ_STATUS = "notificationReadStatus";
    private static final String KEY_NOTIFICATIONS_TIMESTAMP = "notificationTimeStamp";


    //DEVELOPMENT TABLE COLUMNS
    private static final String KEY_DEV_ID = "id";
    private static final String KEY_DEV_TASK = "devTask";
    private static final String KEY_DEV_TASK_DESCRIPTION = "devTaskDescription";
    private static final String KEY_DEV_STATUS = "devStatus";
    private static final String KEY_DEV_COMPLETED_ON = "devCompletedOn";
    private static final String KEY_DEV_RANGE_TO = "devRangeTo";
    private static final String KEY_DEV_RANGE_FROM = "devRangeFrom";
    private static final String KEY_DEV_IMG_ID = "devImgId";

    //CALENDAR TABLE COLUMNS
    private static final String KEY_CAL_ID = "id";
    private static final String KEY_CAL_DAY = "calDay";
    private static final String KEY_CAL_MONTH = "calMonth";
    private static final String KEY_CAL_YEAR = "calYear";
    private static final String KEY_CAL_TITLE = "calTitle";
    private static final String KEY_CAL_DESCRIPTION = "calDescription";
    private static final String KEY_CAL_CATEGORY = "calCategory";
    private static final String KEY_CAL_IMG_1 = "calImg1";
    private static final String KEY_CAL_IMG_2 = "calImg2";
    private static final String KEY_CAL_IMG_3 = "calImg3";
    private static final String KEY_CAL_IMG_COUNT = "calImgCount";


    private static final String KEY_GROWTH_ID = "id";
    private static final String KEY_MONTH_MONTH = "month";
    private static final String KEY_RANGE = "range";
    private static final String KEY_PERCENTILE_3 = "percentile3";
    private static final String KEY_PERCENTILE_15 = "percentile15";
    private static final String KEY_PERCENTILE_30 = "percentile30";
    private static final String KEY_PERCENTILE_50 = "percentile50";
    private static final String KEY_PERCENTILE_70 = "percentile70";
    private static final String KEY_PERCENTILE_85 = "percentile85";
    private static final String KEY_PERCENTILE_97 = "percentile97";
    private static final String KEY_GENDER = "gender";
    private static final String KEY_ENTRY_DATE = "entry_date";
    private static final String KEY_CATEGEORY = "category";
    private static final String KEY_USER_BABY_1 = "userBaby_1";
    private static final String KEY_USER_BABY_2 = "userBaby_2";


    SQLiteDatabase db;
    Cursor cursor;

    public DatabaseHandler(Context context) {

        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

//        String CREATE_TABLE_GROWTH = "CREATE TABLE IF NOT EXISTS " + TABLE_GROWTH + "("
//                + KEY_GROWTH_ID + " TEXT," + KEY_MONTH_MONTH + " TEXT," +
//                KEY_RANGE + " TEXT," + KEY_PERCENTILE_3
//                + " TEXT," + KEY_PERCENTILE_15 + " TEXT," + KEY_PERCENTILE_30 + " TEXT," + KEY_PERCENTILE_50 + " TEXT," + KEY_PERCENTILE_70 + " TEXT," + KEY_PERCENTILE_85 + " TEXT," + KEY_PERCENTILE_97 + " TEXT," + KEY_GENDER + " TEXT," + KEY_USER_BABY_1 + " TEXT," + KEY_USER_BABY_2 + " TEXT,"
//                + KEY_CATEGEORY + " TEXT" + ")";

        String CREATE_TABLE_GROWTH = "CREATE TABLE IF NOT EXISTS " + TABLE_GROWTH + "("
                + KEY_GROWTH_ID + " INTEGER PRIMARY KEY   AUTOINCREMENT," + KEY_MONTH_MONTH + " TEXT," +
                KEY_RANGE + " TEXT," + KEY_PERCENTILE_3
                + " TEXT," + KEY_PERCENTILE_15 + " TEXT," + KEY_PERCENTILE_30 + " TEXT," + KEY_PERCENTILE_50 + " TEXT," + KEY_PERCENTILE_70 + " TEXT," + KEY_PERCENTILE_85 + " TEXT," + KEY_PERCENTILE_97 + " TEXT," + KEY_GENDER + " TEXT," + KEY_USER_BABY_1 + " TEXT," + KEY_USER_BABY_2 + " TEXT,"
                + KEY_CATEGEORY + " TEXT," + KEY_ENTRY_DATE + " TEXT" + ")";

        String CREATE_TABLE_VACCINE = "CREATE TABLE IF NOT EXISTS " + TABLE_VACCINE + "("
                + KEY_VACCINE_ID + " TEXT, " + KEY_GROUP + " TEXT, " + KEY_VACCINE_NAME + " TEXT, " + KEY_VACCINE_DUEDATE + " TEXT, " + KEY_VACCINE_TAKENDATE + " TEXT, "
                + KEY_VACCINE_STATUS + " TEXT" + ")";


        String CREATE_TABLE_DEVELOPMENT = "CREATE TABLE IF NOT EXISTS " + TABLE_DEVELOPMENT + "("
                + KEY_DEV_ID + " TEXT, " + KEY_DEV_TASK + " TEXT, " + KEY_DEV_TASK_DESCRIPTION + " TEXT, " + KEY_DEV_RANGE_FROM + " TEXT, " + KEY_DEV_RANGE_TO + " TEXT, " + KEY_DEV_COMPLETED_ON + " TEXT, "
                + KEY_DEV_IMG_ID + " INTEGER, " + KEY_DEV_STATUS + " TEXT " + ")";

        String CREATE_TABLE_NOTIFICATIONS = "CREATE TABLE IF NOT EXISTS " + TABLE_NOTIFICATIONS + "("
                + KEY_NOTIFICATIONS_ID + " INTEGER, " + KEY_NOTIFICATIONS_HEADER + " TEXT, " + KEY_NOTIFICATIONS_TEXT + " TEXT, " +
                KEY_NOTIFICATIONS_IMG_ID + " TEXT, " + KEY_NOTIFICATIONS_SHOW_STATUS + " INTEGER, " + KEY_NOTIFICATIONS_READ_STATUS +
                " INTEGER, " + KEY_NOTIFICATIONS_TIMESTAMP + " TEXT " + ")";


        String CREATE_TABLE_CALENDAR = "CREATE TABLE IF NOT EXISTS " + TABLE_CALENDAR + "("
                + KEY_CAL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_CAL_DAY + " INTEGER, " + KEY_CAL_MONTH + " INTEGER, " + KEY_CAL_YEAR + " INTEGER, " +
                KEY_CAL_TITLE + " TEXT, " + KEY_CAL_DESCRIPTION + " TEXT, "
                + KEY_CAL_CATEGORY + " TEXT, " + KEY_CAL_IMG_1 + " TEXT, " + KEY_CAL_IMG_2 + " TEXT, " +
                KEY_CAL_IMG_3 + " TEXT, " + KEY_CAL_IMG_COUNT + " INTEGER" + ")";

        System.out.println("CREATE_TABLE_DEVELOPMENT : " + CREATE_TABLE_DEVELOPMENT);
        Log.d("qqqqqqq", "create table" + CREATE_TABLE_GROWTH);
        Log.d("qqqqqqq", "create table" + CREATE_TABLE_NOTIFICATIONS);
        Log.d("qqqqqqq", "create table" + CREATE_TABLE_VACCINE);
        Log.d("qqqqqqq", "create table" + CREATE_TABLE_CALENDAR);

        db.execSQL(CREATE_TABLE_GROWTH);
        db.execSQL(CREATE_TABLE_VACCINE);
        db.execSQL(CREATE_TABLE_DEVELOPMENT);
        db.execSQL(CREATE_TABLE_CALENDAR);
        db.execSQL(CREATE_TABLE_NOTIFICATIONS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_VACCINE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Vaccine
    public void addVaccine(VaccineSqliteData vaccineData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_VACCINE_ID, vaccineData.getId()); // Vaccine Name
        values.put(KEY_GROUP, vaccineData.getGroup()); // Vaccine grup
        values.put(KEY_VACCINE_NAME, vaccineData.getVaccine()); // Vaccine name
        values.put(KEY_VACCINE_DUEDATE, vaccineData.getDueDate()); // Vaccine duedate
        values.put(KEY_VACCINE_TAKENDATE, vaccineData.getTakenDate()); // Vaccine taken date
        values.put(KEY_VACCINE_STATUS, vaccineData.getStatus()); // Vaccine status

        // Inserting Row
        db.insert(TABLE_VACCINE, null, values);
        db.close(); // Closing database connection
    }

    public void addDevelopment(DevelopmentListData developmentListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_DEV_ID, developmentListData.getId());
        values.put(KEY_DEV_TASK, developmentListData.getTask());
        values.put(KEY_DEV_TASK_DESCRIPTION, developmentListData.getTaskDescription());
        values.put(KEY_DEV_RANGE_FROM, developmentListData.getRangeFrom());
        values.put(KEY_DEV_RANGE_TO, developmentListData.getRangeTo());
        values.put(KEY_DEV_COMPLETED_ON, developmentListData.getCompletedOn());
        values.put(KEY_DEV_IMG_ID, developmentListData.getImgId());
        values.put(KEY_DEV_STATUS, developmentListData.getStatus());

        // Inserting Row
        db.insert(TABLE_DEVELOPMENT, null, values);
        db.close(); // Closing database connection
    }

    public int addCalendar(CalendarEntryListData calendarEntryListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_CAL_DAY, calendarEntryListData.getEntryDateDay());
        values.put(KEY_CAL_MONTH, calendarEntryListData.getEntryDateMonth());
        values.put(KEY_CAL_YEAR, calendarEntryListData.getEntryDateYear());
        values.put(KEY_CAL_TITLE, calendarEntryListData.getTitle());
        values.put(KEY_CAL_DESCRIPTION, calendarEntryListData.getDescription());
        values.put(KEY_CAL_CATEGORY, calendarEntryListData.getCategory());
        values.put(KEY_CAL_IMG_1, calendarEntryListData.getImgFirstLink());
        values.put(KEY_CAL_IMG_2, calendarEntryListData.getImgSecondLink());
        values.put(KEY_CAL_IMG_3, calendarEntryListData.getImgThirdLink());
        values.put(KEY_CAL_IMG_COUNT, calendarEntryListData.getImgCount());

        // Inserting Row
        db.insert(TABLE_CALENDAR, null, values);
        db.close(); // Closing database connection
        return 1;
    }

    public Cursor getAllDevelopmentDataByCursor() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_DEVELOPMENT;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return vaccine data cursor
        return cursor;
    }

    public Cursor getAllCalendarDataByCursor() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CALENDAR;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return vaccine data cursor
        return cursor;
    }

    public Cursor getCalendarRecord(int id) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CALENDAR + " WHERE id  = " + id;
//        String selectQuery = "SELECT  * FROM " + TABLE_CALENDAR + " WHERE id  = 2";
        System.out.println("selectQuery : " + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return vaccine data cursor
        return cursor;
    }

    public Cursor getCalendarMonthDataByCursor(int year, int month) {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_CALENDAR + " WHERE " +
                KEY_CAL_YEAR + " = " + year + " AND " + KEY_CAL_MONTH + " = " + month;

        System.out.println("selectQuery : " + selectQuery);

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return vaccine data cursor
        return cursor;
    }

    // Updating Vaccine Tables
    public int updateCalendar(CalendarEntryListData calendarEntryListData) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_CAL_TITLE, calendarEntryListData.getTitle());
        values.put(KEY_CAL_DESCRIPTION, calendarEntryListData.getDescription());
        values.put(KEY_CAL_IMG_1, calendarEntryListData.getImgFirstLink());
        values.put(KEY_CAL_IMG_2, calendarEntryListData.getImgSecondLink());
        values.put(KEY_CAL_IMG_3, calendarEntryListData.getImgThirdLink());
        values.put(KEY_CAL_IMG_COUNT, calendarEntryListData.getImgCount());


        // updating row
        return db.update(TABLE_CALENDAR, values, KEY_CAL_ID + " = ?",
                new String[]{String.valueOf(calendarEntryListData.getId())});
    }

    //    public int deleteCalendarRecord(CalendarEntryListData calendarEntryListData) {
    public int deleteCalendarRecord(int calId) {
        db = this.getWritableDatabase();

        System.out.println("DatabaseHandler : " + calId);


        // deleting record
//        return db.delete(TABLE_CALENDAR, KEY_CAL_ID, new String[]{String.valueOf(calendarEntryListData.getId())});
        return db.delete(TABLE_CALENDAR, KEY_CAL_ID + "= ?", new String[]{String.valueOf(calId)});
//        return db.delete(DATABASE_TABLE, KEY_NAME + "=" + name, null) > 0;

//db.dele
    }

    // Getting All Contacts
    public List<VaccineSqliteData> getAllVaccineDataByList() {
        List<VaccineSqliteData> vaccineList = new ArrayList<VaccineSqliteData>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VACCINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                VaccineSqliteData vaccine = new VaccineSqliteData();
                vaccine.setId(cursor.getString(0));
                vaccine.setGroup(cursor.getString(1));
                vaccine.setVaccine(cursor.getString(2));
                vaccine.setDueDate(cursor.getString(3));
                vaccine.setTakenDate(cursor.getString(4));
                vaccine.setStatus(cursor.getString(5));
                // Adding contact to list
                vaccineList.add(vaccine);
            } while (cursor.moveToNext());
        }

        // return vaccine list
        return vaccineList;
    } // Getting All Contacts

    public Cursor getAllVaccineDataByCursor() {
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_VACCINE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // return vaccine data cursor
        return cursor;
    }


    public Cursor getAllGrowthDataByCursor(ArrayList<String> growthParams) {
        Log.d("param", "size" + growthParams.size());
        if (growthParams.size() == 0) {

        } else {
            // Select All Query
            String selectQuery = "SELECT  * FROM " + TABLE_GROWTH + " WHERE " + KEY_CATEGEORY + " = '" + growthParams.get(0) + "'";
            Log.d("select query", "" + selectQuery);
            SQLiteDatabase db = this.getWritableDatabase();
            System.out.print("selecy query 111111");
            cursor = db.rawQuery(selectQuery, null);
            System.out.print("selecy query 222222");
            System.out.print("cursor" + cursor);
            System.out.print("selecy query 3333333");
        }
        // return vaccine data cursor
        return cursor;
    }

    /*   This method is used in Head,HeadCircum,Weight Fragment to UPDATE Data.*/
    public int userUpdatedGrowthRecord(GrowthSqliteData growthSqliteData) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USER_BABY_1, growthSqliteData.getGrowth_userBaby_Data());
        System.out.println("userDataGrowthSqliteData  getUserBaby_1  " + growthSqliteData.getGrowth_userBaby_Data());
        //return db.update(TABLE_GROWTH, contentValues, KEY_GROWTH_ID + " = ? AND " + KEY_ENTRY_DATE + " =?", new String[]{String.valueOf(growthSqliteData.getId()), String.valueOf(growthSqliteData.getUserEnterGrowthValue())});
        return db.update(TABLE_GROWTH, contentValues, KEY_GROWTH_ID + " = ?", new String[]{String.valueOf(growthSqliteData.getGrowth_Id())});
    }


    /*   This method is used in Head,HeadCircum,Weight Fragment to DELETE data from  GrowthOverView.*/
    public int updateUserDeletedGrowth(GrowthSqliteData growthSqliteData) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        System.out.println("Call Zala re bava @@@@@@@@@@@@@@@@@@@@  updateUserDeletedGrowth");
        contentValues.put(KEY_ENTRY_DATE, growthSqliteData.getGrowth_UserEnter_Date());
        contentValues.put(KEY_USER_BABY_1, growthSqliteData.getGrowth_userBaby_Data());
        System.out.println("userDataGrowthSqliteData  getUserEnterGrowthValue  " + growthSqliteData.getGrowth_UserEnter_Date());
        System.out.println("userDataGrowthSqliteData  getUserBaby_1  " + growthSqliteData.getGrowth_userBaby_Data());
        return db.update(TABLE_GROWTH, contentValues, KEY_GROWTH_ID + " = ?", new String[]{String.valueOf(growthSqliteData.getGrowth_Id())});

    }

    /*   This method is used in Head,HeadCircum,Weight Fragment  to get data from dataBase .   */
    public Cursor getUserEnteredGrowthData(String growthParams) {
        // Select All Query
        String selectQuery = "SELECT id,entry_date,userBaby_1,category FROM " + TABLE_GROWTH + " WHERE " + KEY_CATEGEORY + " = '" + growthParams + "'" + " AND " + KEY_ENTRY_DATE + " IS NOT NULL";
        Log.d("select query", "" + selectQuery);
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.print("selecy query 111111");
        cursor = db.rawQuery(selectQuery, null);
        System.out.println("selecy query 222222");
        System.out.println("cursor " + cursor);
        System.out.println("selecy query 3333333");

        // return growth user enterd data data cursor
        return cursor;
    }

    /*
      This method is used in Vaccination_main Class to  update Development data .
       */
    public int updateVaccine(VaccineSqliteData vaccineSqliteData) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_VACCINE_ID, vaccineSqliteData.getId()); // Vaccine Name
        values.put(KEY_GROUP, vaccineSqliteData.getGroup()); // Vaccine grup
        values.put(KEY_VACCINE_NAME, vaccineSqliteData.getVaccine()); // Vaccine name
        values.put(KEY_VACCINE_DUEDATE, vaccineSqliteData.getDueDate()); // Vaccine duedate
        System.out.println("update function added date: " + vaccineSqliteData.getTakenDate());
        values.put(KEY_VACCINE_TAKENDATE, vaccineSqliteData.getTakenDate()); // Vaccine taken date
        values.put(KEY_VACCINE_STATUS, vaccineSqliteData.getStatus()); // Vaccine status

        // updating row
        return db.update(TABLE_VACCINE, values, KEY_VACCINE_ID + " = ?",
                new String[]{String.valueOf(vaccineSqliteData.getId())});
    }

    /*
      This method is used in Development_main Class to  update Development data .
       */
    public int updateDevelopment(DevelopmentListData updateDevData) {
        db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_DEV_ID, updateDevData.getId());
        values.put(KEY_DEV_TASK, updateDevData.getTask());
        values.put(KEY_DEV_TASK_DESCRIPTION, updateDevData.getTaskDescription());
        values.put(KEY_DEV_COMPLETED_ON, updateDevData.getCompletedOn());
        values.put(KEY_DEV_RANGE_FROM, updateDevData.getRangeFrom());
        values.put(KEY_DEV_RANGE_TO, updateDevData.getRangeTo());
        values.put(KEY_DEV_IMG_ID, updateDevData.getImgId());
        values.put(KEY_DEV_STATUS, updateDevData.getStatus());

        // updating row
        return db.update(TABLE_DEVELOPMENT, values, KEY_DEV_ID + " = ?",
                new String[]{String.valueOf(updateDevData.getId())});
    }

    /*
      This method is used in MainActivtiy  Class to  delete all table from database at time of sign out .
       */
    public void deleteDatabaseTables() {
        System.out.println("INSIDE DROP QUERY");

        SQLiteDatabase deleteObj = this.getReadableDatabase();
        deleteObj.delete(TABLE_VACCINE, null, null);
        deleteObj.delete(TABLE_GROWTH, null, null);
        deleteObj.delete(TABLE_DEVELOPMENT, null, null);
        deleteObj.delete(TABLE_CALENDAR, null, null);
        deleteObj.close();

    }

    /*
      This method is used in AppToExcelData Class to insert Excel Sheet in Global DataBase.
       */
    public int insert(String table, ContentValues values) {

        db = this.getWritableDatabase();
        return (int) db.insert(table, null, values);
    }


    /*
   This method is used in LoginMain Class to update Range(1 Jan-1 Feb) in DataBase.
    */
    public int updateGrowth(GrowthSqliteData growthSqliteData) {
        db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RANGE, growthSqliteData.getGrowth_Range());
        return db.update(TABLE_GROWTH, contentValues, KEY_MONTH_MONTH + " = ?", new String[]{String.valueOf(growthSqliteData.getGrowth_Month())});

    }


    /*
    This method is used in GrowthMain Class to update addUserEntry data in DataBase.
     */

    public int updateUserGrowthData(GrowthSqliteData userDataGrowthSqliteData) {
        System.out.println("userDataGrowthSqliteData  getUserBaby_1  " + userDataGrowthSqliteData.getGrowth_userBaby_Data());
        System.out.println("userDataGrowthSqliteData  getUserEnterGrowthValue  " + userDataGrowthSqliteData.getGrowth_UserEnter_Date());
        System.out.println("userDataGrowthSqliteData  getRange  " + userDataGrowthSqliteData.getGrowth_Range());
        System.out.println("userDataGrowthSqliteData  getCategory  " + userDataGrowthSqliteData.getGrowth_Category());
        db = this.getWritableDatabase();
        ContentValues userGrowthDaaContentValue = new ContentValues();
        userGrowthDaaContentValue.put(KEY_USER_BABY_1, userDataGrowthSqliteData.getGrowth_userBaby_Data());
        userGrowthDaaContentValue.put(KEY_ENTRY_DATE, userDataGrowthSqliteData.getGrowth_UserEnter_Date());
        return db.update(TABLE_GROWTH, userGrowthDaaContentValue, KEY_RANGE + " =? AND " + KEY_CATEGEORY + " =?", new String[]{String.valueOf(userDataGrowthSqliteData.getGrowth_Range()), String.valueOf(userDataGrowthSqliteData.getGrowth_Category())});
    }

    public void addNotificationData(NotificationsListData notificationsListData) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOTIFICATIONS_ID, notificationsListData.getId());
        values.put(KEY_NOTIFICATIONS_HEADER, notificationsListData.getNotificationHeader());
        values.put(KEY_NOTIFICATIONS_TEXT, notificationsListData.getNotificationDescription());
        values.put(KEY_NOTIFICATIONS_IMG_ID, notificationsListData.getImgId());
        values.put(KEY_NOTIFICATIONS_READ_STATUS, notificationsListData.getReadStatus());
        values.put(KEY_NOTIFICATIONS_SHOW_STATUS, notificationsListData.getShowStatus());

        // Inserting Row
        db.insert(TABLE_NOTIFICATIONS, null, values);
        db.close(); // Closing database connection
    }

    public Cursor updateNotification(String timeStamp, int notificationStatus, String notificationID) {
        System.out.println("TimeSatamp-----" + timeStamp);
        System.out.println("NotificationStatus-----" + notificationStatus);
        System.out.println("NotificationID-----" + notificationID);
        db = this.getWritableDatabase();
        ContentValues notificationContentValue = new ContentValues();
        notificationContentValue.put(KEY_NOTIFICATIONS_TIMESTAMP, timeStamp);
        notificationContentValue.put(KEY_NOTIFICATIONS_SHOW_STATUS, notificationStatus);
        db.update(TABLE_NOTIFICATIONS, notificationContentValue, KEY_NOTIFICATIONS_ID + "=?", new String[]{notificationID});

        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS + " WHERE " + KEY_NOTIFICATIONS_ID + " = '" + notificationID + "'";
        return db.rawQuery(selectQuery, null);
    }

    public int markNotificationAsRead(String notificationID) {
        System.out.println("NotificationID-----" + notificationID);
        db = this.getWritableDatabase();
        ContentValues notificationContentValue = new ContentValues();
        notificationContentValue.put(KEY_NOTIFICATIONS_READ_STATUS, 1);
        return db.update(TABLE_NOTIFICATIONS, notificationContentValue, KEY_NOTIFICATIONS_ID + "=?", new String[]{notificationID});

    }

    public Cursor getNotification(int notificationID) {
        System.out.println("NotificationID-----" + notificationID);

        db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS + " WHERE " + KEY_NOTIFICATIONS_ID + " = '" + notificationID + "'";
        return cursor = db.rawQuery(selectQuery, null);

    }

    public Cursor getAllNotification() {
        db = this.getWritableDatabase();
        String selectQuery = "SELECT * FROM " + TABLE_NOTIFICATIONS;
        return cursor = db.rawQuery(selectQuery, null);

    }
}

