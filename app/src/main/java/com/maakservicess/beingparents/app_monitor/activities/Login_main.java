package com.maakservicess.beingparents.app_monitor.activities;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.maakservicess.beingparents.app_monitor.AddNotificationDates;
import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.MainActivity;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.AppToExcelData;
import com.maakservicess.beingparents.app_monitor.controllers.DevelopmentListData;
import com.maakservicess.beingparents.app_monitor.controllers.GrowthSqliteData;
import com.maakservicess.beingparents.app_monitor.controllers.NotificationsListData;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineSqliteData;
import com.github.siyamed.shapeimageview.CircularImageView;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by ajinkya on 12/29/2015.
 */
public class Login_main extends Activity {

    CircularImageView profilePic;
    EditText babyLoginNameEdittText;
    public static EditText babyDateOfBirthEdittText;
    Spinner babyGenderSpinner;
    Button enterBtn;
    String babyName;
    int babyBirthYear;
    int babyBirthMonth;
    int babyBirthDay;
    String babyGender;
    List<String> genderList;
    ArrayAdapter adapter;
    static final int DATE_DIALOG_ID = 1;
    private int year;
    private int month;
    private int day;
    SessionManager sessionManager;
    private Boolean babyInserted;
    private SQLiteDatabase babyAppDatabase;
    private boolean createDBStatus;
    private Workbook wb = null;
    private Cursor getsize;
    private InputStream resources;
    private static String babyDOB;
    Float babyInitialBlankWeight = 0.0f;
    Float babyInitialBlankHeight = 0.0f;
    Float babyInitialBlankHeadCircumference = 0.0f;
    ProgressDialog enterProgressDialog;
    private long back_pressed;
    DatabaseHandler databaseHandler;

    //amey part
    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    public static File file;
    public static File myDir;
    private String babyProfileFileName;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);

        profilePic = (CircularImageView) findViewById(R.id.addBabyPicImageView);
        babyLoginNameEdittText = (EditText) findViewById(R.id.babyNameEditText);
        babyDateOfBirthEdittText = (EditText) findViewById(R.id.dateOfBirthEditText);
        babyGenderSpinner = (Spinner) findViewById(R.id.genderSpinner);
        enterBtn = (Button) findViewById(R.id.enterButton);
        //enterProgressDialog = new ProgressDialog(this);

        databaseHandler = new DatabaseHandler(this);

        sessionManager = new SessionManager(getApplicationContext());

        genderList = new ArrayList<>();
        genderList.add("Baby Girl");
        genderList.add("Baby Boy");
        genderList.add("Select gender");
        final Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.login_spinner_item, genderList);
        adapter.setDropDownViewResource(R.layout.login_spinner_dropdown_item);
        babyGenderSpinner.setAdapter(adapter);
        babyGenderSpinner.setSelection(adapter.getCount() - 1);

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // System.out.println("start : selectImage();");
                selectImage();
                //  System.out.println("End : selectImage();");
            }
        });

        babyDateOfBirthEdittText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                showDialog(999);
                DialogFragment selectDateDialog = new SelectDateFragment();
                selectDateDialog.show(getFragmentManager(), "Select Date");
            }
        });


        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                babyName = babyLoginNameEdittText.getText().toString();
                babyGender = babyGenderSpinner.getSelectedItem().toString();

                if (babyName.trim().length() > 0) {
                    if (babyDateOfBirthEdittText.getText().length() > 0) {
                        if (!babyGender.equals("Select gender")) {
//                            enterProgressDialog = ProgressDialog.show(Login_main.this, "please wait", "sddssds", true);

                            enterProgressDialog = new ProgressDialog(Login_main.this);
                            enterProgressDialog.show();
                            enterProgressDialog.setMessage("Please wait...");

                            // Log.d("session create started", "session create started");
                            babyInserted = sessionManager.createLoginSession(babyName, babyGender, babyDOB, babyInitialBlankWeight, babyInitialBlankHeight, babyInitialBlankHeadCircumference, babyProfileFileName);
                            //  Log.d("session create ended", "session create ended");
                            if (babyInserted) {

                                new InsertBabyData().execute();


                            }
                        } else {
                            Toast.makeText(getApplicationContext(), "Select Gender", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Select Date Of Birth", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Provide Name", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from gallery", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(Login_main.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (PermissionChecker.checkSelfPermission(Login_main.this, Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
                        System.out.println("Self Permission checker");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);

                    } else {
                        final AlertDialog.Builder checkPermissionAlertDialog;
                        checkPermissionAlertDialog = new AlertDialog.Builder(Login_main.this);
                        checkPermissionAlertDialog.setMessage("Being Parents App Requried Camera Access");
                        checkPermissionAlertDialog.setIcon(R.drawable.camera_48);
                        checkPermissionAlertDialog.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent checkPermissionIntent = new Intent();
                                checkPermissionIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                checkPermissionIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                checkPermissionIntent.setData(Uri.parse("package:" + getPackageName()));
                                checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(checkPermissionIntent);

                            }
                        });
                        checkPermissionAlertDialog.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        checkPermissionAlertDialog.create().show();
                    }


                } else if (items[item].equals("Choose from gallery")) {

                    if (PermissionChecker.checkSelfPermission(Login_main.this, Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
                        if (PermissionChecker.checkSelfPermission(Login_main.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                        } else {
                            final AlertDialog.Builder checkPermissionAlertDialog;
                            checkPermissionAlertDialog = new AlertDialog.Builder(Login_main.this);
                            checkPermissionAlertDialog.setMessage("Being Parents App Requried Camera Access");
                            checkPermissionAlertDialog.setIcon(R.drawable.camera_48);
                            checkPermissionAlertDialog.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Intent checkPermissionIntent = new Intent();
                                    checkPermissionIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                    checkPermissionIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                    checkPermissionIntent.setData(Uri.parse("package:" + getPackageName()));
                                    checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                    checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                    startActivity(checkPermissionIntent);

                                }
                            });
                            checkPermissionAlertDialog.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                            checkPermissionAlertDialog.create().show();

                        }
                    } else {
                        final AlertDialog.Builder checkPermissionAlertDialog;
                        checkPermissionAlertDialog = new AlertDialog.Builder(Login_main.this);
                        checkPermissionAlertDialog.setMessage("Being Parents App Requried Camera Access");
                        checkPermissionAlertDialog.setIcon(R.drawable.camera_48);
                        checkPermissionAlertDialog.setPositiveButton("Allow", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                Intent checkPermissionIntent = new Intent();
                                checkPermissionIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                checkPermissionIntent.addCategory(Intent.CATEGORY_DEFAULT);
                                checkPermissionIntent.setData(Uri.parse("package:" + getPackageName()));
                                checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                                checkPermissionIntent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
                                startActivity(checkPermissionIntent);

                            }
                        });
                        checkPermissionAlertDialog.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                        checkPermissionAlertDialog.create().show();
                    }
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
                Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");
                //System.out.println("destination" + destination);
                FileOutputStream fo;
                try {
                    destination.createNewFile();
                    fo = new FileOutputStream(destination);
                    fo.write(bytes.toByteArray());
                    fo.close();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                profilePic.setImageBitmap(thumbnail);
                SaveImage(thumbnail);

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                System.out.println("selectedImagePath" + selectedImagePath);

                //************************compress logic start****************
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;
                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;
                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;
                Bitmap bm = BitmapFactory.decodeFile(selectedImagePath, options);
                //************************compress logic start****************

                profilePic.setImageBitmap(bm);
                SaveImage(bm);
            }
        }

    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        myDir = new File(root + "/App_Baby");
        //  System.out.println("myDir" + myDir);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        babyProfileFileName = "Image-" + n + ".jpg";
        file = new File(myDir, babyProfileFileName);
        // System.out.println("myDir : " + file);
        // System.out.println("baby_Profile_File_Name : " + babyProfileFileName);

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            // System.out.println("out" + out);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//

    public class InsertBabyData extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            insertGlobalData();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {

            enterProgressDialog.dismiss();
            Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(mainActivityIntent);
            super.onPostExecute(aVoid);

        }

    }

    private void insertGlobalData() {
        //  System.out.println("INSERT GLOBAL DATA METHOD STARTED");

        // INSERTING GROWTH DATA
        resources = getResources().openRawResource(R.raw.baby_app_global_database_excel);
        try {
            Log.d("before database ", "try");
            wb = new HSSFWorkbook(resources);
            Sheet sheet1 = wb.getSheetAt(0);
            System.out.println("inside growth data insertion : i.e. login Completed and data is ready to insert");
            AppToExcelData.insertExcelToSqlite(databaseHandler, sheet1);
            System.out.println("AFTER growth data insertion : DATA INSERTED");

            for (int loopi = 0; loopi <= 60; loopi++) {
                String month = String.valueOf(loopi);
                String updateRangeDate1 = customizeDate(0, loopi, 0);
                loopi++;
                String updateRangeDate2 = customizeDate(0, loopi, 0);
                loopi--;
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yy");
                Date date1 = dateFormat.parse(updateRangeDate1);
                Date date2 = dateFormat.parse(updateRangeDate2);
                SimpleDateFormat targetformat = new SimpleDateFormat("dd MMM yy");
                targetformat.format(date1);
                String rangeOfDate = targetformat.format(date1) + "  -  " + targetformat.format(date2);
                System.out.println(" ######################## Final date to be insert " + rangeOfDate);
                GrowthSqliteData growthSqliteData = new GrowthSqliteData(rangeOfDate, null, month, null, null, null);
                int updategrwothresult = databaseHandler.updateGrowth(growthSqliteData);
                System.out.println("updategrwothresult  " + updategrwothresult);
            }


        } catch (IOException e) {
            e.printStackTrace();
            Log.d("before database ", "catch");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // INSERTING VACCINE DATA
        databaseHandler.addVaccine(new VaccineSqliteData("A1", "Birth", "BCG", customizeDate(0, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("A2", "Birth", "OPV 0", customizeDate(0, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("A3", "Birth", "Hep-B 1", customizeDate(0, 0, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("B1", "6 Weeks", "DTwP 1", customizeDate(6, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("B2", "6 Weeks", "IPV 1", customizeDate(6, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("B3", "6 Weeks", "Hep-B 2", customizeDate(6, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("B4", "6 Weeks", "Hib 1", customizeDate(6, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("B5", "6 Weeks", "Rotavirus 1", customizeDate(6, 0, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("C1", "10 weeks", "DTwP 2", customizeDate(10, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("C2", "10 weeks", "IPV 2", customizeDate(10, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("C3", "10 weeks", "Hib 2", customizeDate(10, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("C4", "10 weeks", "Rotavirus 2", customizeDate(10, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("C5", "10 weeks", "PCV 2", customizeDate(10, 0, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("D1", "14 Weeks", "DTwP 3", customizeDate(14, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("D2", "14 Weeks", "IPV 3", customizeDate(14, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("D3", "14 Weeks", "Hib 3", customizeDate(14, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("D4", "14 Weeks", "Rotavirus 3", customizeDate(14, 0, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("D5", "14 Weeks", "PCV 3", customizeDate(14, 0, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("E1", "6 Months", "OPV 1", customizeDate(0, 6, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("E2", "6 Months", "Hep-B 3", customizeDate(0, 6, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("F1", "9 Months", "OPV 2", customizeDate(0, 9, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("F2", "9 Months", "MMR-1", customizeDate(0, 9, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("G1", "9-12 Months", "Typhoid", customizeDate(0, 9, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("G2", "9-12 Months", "Conjugate Vaccine", customizeDate(0, 9, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("H1", "12 Months", "Hep-A 1", customizeDate(0, 12, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("I1", "15 Months", "MMR 2", customizeDate(0, 15, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("I2", "15 Months", "Varicella 1", customizeDate(0, 15, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("I3", "15 Months", "PCV booster", customizeDate(0, 15, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("J1", "16-18 Months", "DTwP B1/DTaP B1", customizeDate(0, 16, 0), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("J2", "16-18 Months", "IPV B1,Hib B1", customizeDate(0, 16, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("K1", "18 Months", "Hep-A 2", customizeDate(0, 18, 0), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("L1", "2 years", "Typhoid booster", customizeDate(0, 0, 2), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("M1", "4-6 Years", "DTwP B2/DTaP B2", customizeDate(0, 0, 4), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("M2", "4-6 Years", "OPV 3 Varicella 2", customizeDate(0, 0, 4), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("M3", "4-6 Years", "Typhoid booster", customizeDate(0, 0, 4), "", "Pending"));

        databaseHandler.addVaccine(new VaccineSqliteData("N1", "10-12 Years", "Tdap/Td", customizeDate(0, 0, 10), "", "Pending"));
        databaseHandler.addVaccine(new VaccineSqliteData("N2", "10-12 Years", "HPV", customizeDate(0, 0, 10), "", "Pending"));


        //System.out.println("AFTER VACCINE data insertion : DATA INSERTED");


        // INSERTING DEVELOPMENT DATA
        databaseHandler.addDevelopment(new DevelopmentListData("D1", "Social smile", "development description", customizeDate(0, 0, 0), customizeDate(3, 2, 0), "", R.drawable.smile_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D2", "Eyes follow pen/pencil", "development description", customizeDate(1, 1, 0), customizeDate(0, 3, 0), "", R.drawable.eye_lollipop_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D3", "Holds hand steady", "development description", customizeDate(1, 1, 0), customizeDate(3, 3, 0), "", R.drawable.steady_hand_64, "Pending"));

        databaseHandler.addDevelopment(new DevelopmentListData("D4", "Rolls from back to stomach", "development description", customizeDate(3, 2, 0), customizeDate(0, 6, 0), "", R.drawable.crwaling_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D5", "Turns head to sound of bell rattle", "development description", customizeDate(0, 3, 0), customizeDate(0, 6, 0), "", R.drawable.rattle_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D6", "Transfer objects hand to hand", "development description", customizeDate(1, 4, 0), customizeDate(0, 7, 0), "", R.drawable.transfer_hand_to_hand_64, "Pending"));

        databaseHandler.addDevelopment(new DevelopmentListData("D7", "Raises self to sitting position", "development description", customizeDate(0, 6, 0), customizeDate(0, 11, 0), "", R.drawable.crwaling_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D8", "Standing up by furniture", "development description", customizeDate(2, 6, 0), customizeDate(0, 11, 0), "", R.drawable.furniture_stand_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D9", "Fine prehension pellet", "development description", customizeDate(0, 7, 0), customizeDate(0, 11, 0), "", R.drawable.prehension_pellet_64, "Pending"));

        databaseHandler.addDevelopment(new DevelopmentListData("D10", "Pat a cake", "development description", customizeDate(0, 7, 0), customizeDate(3, 12, 0), "", R.drawable.pat_a_cake_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D11", "Walk with help", "development description", customizeDate(0, 9, 0), customizeDate(0, 16, 0), "", R.drawable.baby_walk_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D12", "Throws ball", "development description", customizeDate(3, 9, 0), customizeDate(2, 18, 0), "", R.drawable.ball_64, "Pending"));

        databaseHandler.addDevelopment(new DevelopmentListData("D13", "Walks alone", "development description", customizeDate(0, 10, 0), customizeDate(3, 18, 0), "", R.drawable.baby_stand_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D14", "Says two words", "development description", customizeDate(2, 11, 0), customizeDate(1, 19, 0), "", R.drawable.speak_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D15", "Walks backwards", "development description", customizeDate(2, 11, 0), customizeDate(3, 19, 0), "", R.drawable.baby_walk_back_64, "Pending"));

        databaseHandler.addDevelopment(new DevelopmentListData("D16", "Walks upstairs with help", "development description", customizeDate(2, 12, 0), customizeDate(2, 24, 0), "", R.drawable.stairs_64, "Pending"));
        databaseHandler.addDevelopment(new DevelopmentListData("D17", "Points to parts of doll(3 parts)", "development description", customizeDate(2, 15, 0), customizeDate(1, 24, 0), "", R.drawable.finger_64, "Pending"));

//      addNotificationData to database
        databaseHandler.addNotificationData(new NotificationsListData(0, "Happy birthday", "Its $#$#$# first birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(1, "Happy birthday", "Its $#$#$# second birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(2, "Happy birthday", "Its $#$#$# third birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(3, "Happy birthday", "Its $#$#$# fourth birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(4, "Happy birthday", "Its $#$#$# fifth birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(5, "Happy birthday", "Its $#$#$# sixth birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(6, "Happy birthday", "Its $#$#$# seventh birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(7, "Happy birthday", "Its $#$#$# eighth birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(8, "Happy birthday", "Its $#$#$# ninth birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(9, "Happy birthday", "Its $#$#$# tenth birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(10, "Happy birthday", "Its $#$#$# eleventh birthday.", R.drawable.birthday_cake_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(11, "Happy birthday", "Its $#$#$# twelfth birthday.", R.drawable.birthday_cake_48, 0, 0, null));

        databaseHandler.addNotificationData(new NotificationsListData(12, "Measurement Time!", "Its been a month your baby has stepped into this beautiful world. Start monitoring her growth. Growth section is now fully accesible to you. Don’t forget to chek that out!!", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(13, "Measurement Time!", "Two months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(14, "Measurement Time!", "Three months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(15, "Measurement Time!", "Four months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(16, "Measurement Time!", "Five months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(17, "Measurement Time!", "Six months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(18, "Measurement Time!", "Seven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(19, "Measurement Time!", "Eight months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(20, "Measurement Time!", "Nine months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(21, "Measurement Time!", "Ten months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(22, "Measurement Time!", "Eleven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(23, "Measurement Time!", "Its been an year. Continue adding growth figures to accurately monitor $%$%$% physical growth. Head on to growth section now!!", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(24, "Measurement Time!", "One year one month complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(25, "Measurement Time!", "One year two months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(26, "Measurement Time!", "One year three months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(27, "Measurement Time!", "One year four  months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(28, "Measurement Time!", "One year five months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(29, "Measurement Time!", "One year six months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(30, "Measurement Time!", "One year seven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(31, "Measurement Time!", "One year eight months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(32, "Measurement Time!", "One year nine months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(33, "Measurement Time!", "One year ten months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(34, "Measurement Time!", "One year eleven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(35, "Measurement Time!", "Its been two years. Continue adding growth figures to accurately monitor $%$%$% physical growth. Head on to growth section now!!", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(36, "Measurement Time!", "Two year one month complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(37, "Measurement Time!", "Two year two months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(38, "Measurement Time!", "Two year three months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(39, "Measurement Time!", "Two year four months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(40, "Measurement Time!", "Two year five months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(41, "Measurement Time!", "Two year six months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(42, "Measurement Time!", "Two year seven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(43, "Measurement Time!", "Two year eight months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(44, "Measurement Time!", "Two year nine months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(45, "Measurement Time!", "Two year ten months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(46, "Measurement Time!", "Two year eleven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(47, "Measurement Time!", "Its been three years. Continue adding growth figures to accurately monitor $%$%$% physical growth. Head on to growth section now!!", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(48, "Measurement Time!", "Three year one month complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(49, "Measurement Time!", "Three year two months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(50, "Measurement Time!", "Three year three months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(51, "Measurement Time!", "Three year four months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(52, "Measurement Time!", "Three year five months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(53, "Measurement Time!", "Three year six months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(54, "Measurement Time!", "Three year seven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(55, "Measurement Time!", "Three year eight months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(56, "Measurement Time!", "Three year nine months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(57, "Measurement Time!", "Three year ten months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(58, "Measurement Time!", "Three year eleven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(59, "Measurement Time!", "Its been four years. Continue adding growth figures to accurately monitor $%$%$% physical growth. Head on to growth section now!!", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(60, "Measurement Time!", "Four year one month complete. Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(61, "Measurement Time!", "Four year two months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(62, "Measurement Time!", "Four year three months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(63, "Measurement Time!", "Four year four months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(64, "Measurement Time!", "Four year five months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(65, "Measurement Time!", "Four year six months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(66, "Measurement Time!", "Four year seven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(67, "Measurement Time!", "Four year eight months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(68, "Measurement Time!", "Four year nine months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(69, "Measurement Time!", "Four year ten months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(70, "Measurement Time!", "Four year eleven months complete.Measure your baby and track the growth chart.", R.drawable.tape_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(71, "Measurement Time!", "Its been five years. Continue adding growth figures to accurately monitor $%$%$% physical growth. Head on to growth section now!!", R.drawable.tape_48, 0, 0, null));


        databaseHandler.addNotificationData(new NotificationsListData(72, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled on birth. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(73, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 6 weeks. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(74, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 10 weeks. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(75, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 14 weeks. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(76, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 6 months. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(77, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 9 months and between 9-12 months. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(78, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 12 months. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(79, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled 15 months. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(80, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled between 16-18 months. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(81, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 18 months. Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(82, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled after 2 years Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(83, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled between 4-6 years Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(84, "Immunization reminder", "Vaccines lined up! \n Please administer all the required vaccines scheduled between 10-12 years Check out immunization record for more details. Do not forget to enter the date on which vaccine is administered to maintain timely record.", R.drawable.syringe_50, 0, 0, null));

        //Development feeding start notifications
        databaseHandler.addNotificationData(new NotificationsListData(85, "Social smile", "It’s the time babies start smiling. Have you noticed your baby smilling or responding with a smile? Head on to development section to enter the details as your baby progress.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(86, "Eyes follow pen/pencil", "Does your baby  follow anything when moved in front of eyes? If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(87, "Holds hand steady", "Is your baby holding own hands steadily?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(88, "Rolls from back to stomach", "Is your baby rolling from back to stomach?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(89, "Turn head to sound of bell rattle", "Is your baby turning head after listening to a bell or music?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(90, "Transfer objects hand to hand", "Is your baby transfering objects from one hand to another? If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(91, "Raises self to sitting position", "Is your baby trying to raise from lying position to sitting position without your help?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(92, "Standing up by furniture", "Is your baby trying to stand by furniture?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(93, "Fine prehension pellete", "Is your baby trying to grasp anything with hands? If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(94, "Pat a cake", "Is your baby clapping gently and making hand movements on music or rhyme? If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(95, "Walk with help", "Is your baby trying to walk with your help?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(96, "Throw a ball", "Does your baby throw a ball? If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(97, "Walk alone", "Is your baby  trying to walk alone?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(98, "Says two words", "Did you hear your baby saying at least two continous words? If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(99, "Walk backwards", "Does your baby walk backwards?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(100, "Walk upstairs with help", "Has your baby started climbing stairs with help?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(101, "Points to parts of doll", "Does your baby points to parts of a doll or a soft toy?  If so , mark an entry in the development section. If not, keep an eye on this development task and make an entry whenever its achieved.", R.drawable.development_48, 0, 0, null));

        //Development feeding END notifications
        //not added as of now.

        //complementary feeding
        databaseHandler.addNotificationData(new NotificationsListData(102, "Breast feeding isn't enough", "Its been six months your baby has been fed on breast milk. Its tome to add some complementary items to the feed. Checkout complementary feeding schedule under feeding section.", R.drawable.breastfeeding48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(103, "Liked complementary food?", "Its been a month your baby had more than just breast milk. Try enhancing complementary food by as suitable. For more details, head on to complementary feeding scheduke under feeding section.", R.drawable.food48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(104, "Add something new!!", "If your baby responds good to existing diet. Try adding some more items as advised in the complementary feeding schedule.", R.drawable.food48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(105, "Cook your meal for your baby too.", "Your baby has now grown up to accept family meal. Refer complementary feeding guide to help you feed your baby a perfect family meal.", R.drawable.food48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(106, "Try some experiments on food.", "You can now look for various kind of meal that your baby likes. Try feeding some totally new items to see the response. Refer complementary feeding guide for more information.", R.drawable.food48, 0, 0, null));

        //Teeth notification data
        databaseHandler.addNotificationData(new NotificationsListData(107, "Peep peep! Tooth arriving!", "	Its been six months your baby has been giving gorgeous smile without teeth. This the time most of the babies experience their first ever set of teeth(Lower central incisor). Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(108, "Welcome upper Teeth!", "Hope your baby is happy with lower central teeth. Its time to expect upper central teeth(Upper central incisor). 8-12 months  is the schedule for upper central incisors.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(109, "Welcome new teeth!", "	Your baby must be having a great smile!!. Its time to add few more teeth to his smile(Upper Lateral incisors). They start erupting from 9-13 months of age. Have a great smile ahead.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(110, "Welcome new teeth!", "	Lower lateral incisors start arriving at this time. Scheduled between 10-16 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(111, "Welcome new teeth!", "	First molar(Upper) start arriving at this time. Scheduled between 13-19 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(112, "Welcome new teeth!", "	First molar (Lower) start arriving at this time. Scheduled between 14-18 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(113, "Welcome new teeth!", "	Cannine (Cuspid Upper) start arriving at this time. Scheduled between 16-22 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(114, "Welcome new teeth!", "	Cannine (Cuspid Lower) start arriving at this time. Scheduled between 17-23 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(115, "Welcome new teeth!", "	Second molar (lower) start arriving at this time. Scheduled between 23-31 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(116, "Welcome new teeth!", "	Second molar (Upper) start arriving at this time. Scheduled between 25-33 months. Check out teeth eruption schedule in teeth section. Happy smiling!.", R.drawable.tooth_48, 0, 0, null));


        //brushing
        databaseHandler.addNotificationData(new NotificationsListData(117, "Brushy brushy!!", "You can start brushing your baby's teeth as soon as they arrive. Checkout brushing tips under guidance section", R.drawable.syringe_50, 0, 0, null));
        databaseHandler.addNotificationData(new NotificationsListData(118, "Brush 2 times a day!", "Now when your baby has many teeth, brushing twice a day helps keep teeth healthy and clean. Checkout brushing tips under guidance section", R.drawable.syringe_50, 0, 0, null));

        //random
        databaseHandler.addNotificationData(new NotificationsListData(119, "Undesired symptoms?", "Watch out for danger signs!\nDanger signs section has been design top provide you with possible conditions when you might want to take your baby to the doctor. Check out danger signs.", R.drawable.siren48, 0, 0, null));

        databaseHandler.addNotificationData(new NotificationsListData(120, "Got memories to preserve?", "Calendar provides you with the ability to add and preserve your baby's memories.\nSnap a picture of first smile or capture your baby's first steps. Everything preserved!. You can even add doctor's visit or capture prescriptions to have a quick access.\nEverything at your finger tips.Check the calendar section now!", R.drawable.camera_48, 0, 0, null));


        //Setting NOTIFICATIONS ALARMS
        AddNotificationDates addNotificationDates = new AddNotificationDates();
        addNotificationDates.setNotifications(this, sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB), 36000000);

    }

    //LOGIC FOR CUSTOM DATE INSERTION
    private String customizeDate(int week, int month, int year) {

        //  System.out.println("babyDOB for custom date entry : " + babyDOB);
        String customdate = null;

        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yy");
        try {
            Date date = format.parse(babyDOB);
            //System.out.println("regular : " + date);

            Calendar c = Calendar.getInstance();
            c.setTime(date);

// setting custom values
            c.add(Calendar.DATE, (week * 7));
            c.add(Calendar.MONTH, month);
            c.add(Calendar.YEAR, year);

            Date resultdate = new Date(c.getTimeInMillis());
            //  System.out.println("resultdate : " + resultdate);
            customdate = format.format(resultdate);

        } catch (ParseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return customdate;
    }

    @Override
    public void onBackPressed() {
        if (back_pressed + 2000 > System.currentTimeMillis()) {
            System.exit(1);
            super.onBackPressed();
        } else {
            Toast.makeText(this, "Press Once again to exit", Toast.LENGTH_SHORT).show();
            back_pressed = System.currentTimeMillis();
        }

    }


    public static class SelectDateFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
        private static String date;
        private int yy, mm, dd;
        public String[] monthArray = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            final java.util.Calendar calendar = java.util.Calendar.getInstance();
            yy = calendar.get(java.util.Calendar.YEAR);
            mm = calendar.get(java.util.Calendar.MONTH);
            dd = calendar.get(java.util.Calendar.DAY_OF_MONTH);

            return new DatePickerDialog(getActivity(), this, yy, mm, dd) {
                @Override
                public void onDateChanged(DatePicker view, int year, int month, int day) {
                    super.onDateChanged(view, year, month, day);
                    if (year > yy) {
                        view.updateDate(yy, mm, dd);

                    }
                    if (month > mm && year == yy) {
                        view.updateDate(yy, mm, dd);
                    }
                    if (day > dd && month == mm && year == yy) {
                        view.updateDate(yy, mm, dd);
                    }
                }

                @Override
                public void onClick(DialogInterface dialog, int doneBtn) {
                    if (doneBtn == BUTTON_POSITIVE) {

                        int year = getDatePicker().getYear();
                        int month = getDatePicker().getMonth() + 1;
                        int day = getDatePicker().getDayOfMonth();

                        SelectDateFragment.date = day + "/" + month + "/" + year;
                        babyDOB = day + "-" + month + "-" + year;
                        System.out.println("babyDOB----------" + babyDOB);
                        babyDateOfBirthEdittText.setText(day + "-" + monthArray[month - 1] + "-" + year);


                    }
                    super.onClick(dialog, doneBtn);
                }
            };


        }


        @Override
        public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

        }

    }

}