package com.maakservicess.beingparents.app_monitor.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.MainActivity;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;
import com.maakservicess.beingparents.app_monitor.controllers.GrowthSqliteData;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by SAI PC on 5/31/2016.
 */
public class BirthDataCollection extends Activity {

    Button enterDataBtn;
    Spinner weightSpinner;
    Spinner heightSpinner;
    Spinner headSpinner;
    Spinner bloodGrpSpinner;
    ArrayAdapter spinnerAdapter;
    private long back_pressed;
    private DatabaseHandler database;
    private SessionManager sessionManager;
    private String dOBToInsert;
    private String babyGender;
    SQLiteDatabase globalBabydatabase;
    private TextView doctorNameEditText;
    private TextView doctorContactEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.birth_data_collection);

        database = new DatabaseHandler(this);

        globalBabydatabase = openOrCreateDatabase("gobalBabyAppDatabase", MODE_PRIVATE, null);

        sessionManager = new SessionManager(getApplicationContext());
        String babyDOB = sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_DOB);
        babyGender = sessionManager.getSpecificBabyDetail(SessionManager.KEY_BABY_GENDER);

        List<String> weightList = new ArrayList<>();
        List<String> bloodGrpList = new ArrayList<>();
        List<String> heightList = new ArrayList<>();
        List<String> headList = new ArrayList<>();
        enterDataBtn = (Button) findViewById(R.id.enterBirthDataBtn);
        weightSpinner = (Spinner) findViewById(R.id.birthWeightSpinner);
        heightSpinner = (Spinner) findViewById(R.id.birthHeightSpinner);
        headSpinner = (Spinner) findViewById(R.id.birthHeadSpinner);
        bloodGrpSpinner = (Spinner) findViewById(R.id.bloodGroupSpinner);
        doctorNameEditText = (TextView) findViewById(R.id.doctorNameEditText);
        doctorContactEditText = (TextView) findViewById(R.id.doctorContactEditText);


        weightList.add("Select Weight");
        for (double loophValue = 1; loophValue <= 30; loophValue += 0.5) {
            weightList.add(loophValue + " Kg");
        }

        headList.add("Select Head Cirumference");
        for (int loophValue = 32; loophValue <= 52; loophValue++) {
            headList.add(loophValue + " Cm");
        }

        heightList.add("Select Height");
        for (int loophValue = 45; loophValue <= 120; loophValue++) {
            heightList.add(loophValue + " Cm");
        }

        bloodGrpList.add("Select blood group");
        bloodGrpList.add("A+");
        bloodGrpList.add("A-");
        bloodGrpList.add("B+");
        bloodGrpList.add("B-");
        bloodGrpList.add("O+");
        bloodGrpList.add("O-");
        bloodGrpList.add("AB+");
        bloodGrpList.add("AB-");

        spinnerAdapter = new ArrayAdapter<String>(BirthDataCollection.this, R.layout.login_spinner_item, weightList);
        spinnerAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        weightSpinner.setAdapter(spinnerAdapter);
        weightSpinner.setSelection(0);

        spinnerAdapter = new ArrayAdapter<String>(BirthDataCollection.this, R.layout.login_spinner_item, heightList);
        spinnerAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        heightSpinner.setAdapter(spinnerAdapter);
        heightSpinner.setSelection(0);

        spinnerAdapter = new ArrayAdapter<String>(BirthDataCollection.this, R.layout.login_spinner_item, headList);
        spinnerAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        headSpinner.setAdapter(spinnerAdapter);
        headSpinner.setSelection(0);

        spinnerAdapter = new ArrayAdapter<String>(BirthDataCollection.this, R.layout.login_spinner_item, bloodGrpList);
        spinnerAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        bloodGrpSpinner.setAdapter(spinnerAdapter);
        bloodGrpSpinner.setSelection(0);


        DateFormat sourceFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        DateFormat targetFormat = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
        try {
            Date srcDate = sourceFormat.parse(babyDOB);
            dOBToInsert = targetFormat.format(srcDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        enterDataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (weightSpinner.getSelectedItemPosition() != 0 &&
                        heightSpinner.getSelectedItemPosition() != 0 &&
                        bloodGrpSpinner.getSelectedItemPosition() != 0 &&
                        headSpinner.getSelectedItemPosition() != 0) {
//                    if (!doctorContactEditText.getText().toString().equals("")
//                            && !doctorNameEditText.getText().toString().equals("")) {

                    String fetchId;
                    String category;

                    GrowthSqliteData birthValueUpdateData;

                    String weight = weightSpinner.getSelectedItem().toString();
                    String height = heightSpinner.getSelectedItem().toString();
                    String head = headSpinner.getSelectedItem().toString();

                    //***************************weight addition***********************
                    if (babyGender.equals("Baby Boy")) {
                        category = "wb";
                    } else {
                        category = "wg";
                    }
                    globalBabydatabase.execSQL("UPDATE growth SET userBaby_1 = " + weight.substring(0, weight.length() - 2).trim() +
                            ", entry_date = '" + dOBToInsert + "' WHERE month = '0' and category = '" + category + "'");

//                    birthValueUpdateData = new GrowthSqliteData(null,
//                            weight.substring(0, weight.length() - 2).trim(),
//                            null, dOBToInsert, null, fetchId);

                    int birthWeightUpdateFlag = 0;
//                    int birthWeightUpdateFlag = database.updateUserDeletedGrowth(birthValueUpdateData);
//                    System.out.println("birthWeightUpdateFlag : " + birthWeightUpdateFlag);

                    //***************************height addition***********************
                    if (babyGender.equals("Baby Boy")) {
                        category = "hb";
                    } else {
                        category = "hg";
                    }
                    globalBabydatabase.execSQL("UPDATE growth SET userBaby_1 = " + height.substring(0, height.length() - 2).trim() +
                            ", entry_date = '" + dOBToInsert + "' WHERE month = '0' and category = '" + category + "'");

//                    birthValueUpdateData = new GrowthSqliteData(null,
//                            height.substring(0, height.length() - 2).trim(),
//                            null, dOBToInsert, null, fetchId);

//                    int birthHeightUpdateFlag = database.updateUserDeletedGrowth(birthValueUpdateData);
//                    System.out.println("birthHeightUpdateFlag : " + birthHeightUpdateFlag);

                    //***************************head addition***********************
                    if (babyGender.equals("Baby Boy")) {
                        category = "hcb";
                    } else {
                        category = "hcg";
                    }
                    globalBabydatabase.execSQL("UPDATE growth SET userBaby_1 = " + head.substring(0, head.length() - 2).trim() +
                            ", entry_date = '" + dOBToInsert + "' WHERE month = '0' and category = '" + category + "'");

//                    birthValueUpdateData = new GrowthSqliteData(null,
//                            head.substring(0, head.length() - 2).trim(),
//                            null, dOBToInsert, null, fetchId);

//                    int birthHeadUpdateFlag = database.updateUserDeletedGrowth(birthValueUpdateData);
//                    System.out.println("birthHeadUpdateFlag : " + birthHeadUpdateFlag);


//                    if (birthWeightUpdateFlag == 1 && birthHeadUpdateFlag == 1 && birthHeightUpdateFlag == 1) {
//                        System.out.println("DATABASE INSERTION SUCCESS (BIRTH VALUES)");
                    if (true) {

                        System.out.println("bloodGrpSpinner.getSelectedItem().toString() " + bloodGrpSpinner.getSelectedItem().toString());


                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_HEAD_CIRCUMFERENCE, Float.parseFloat(head.substring(0, head.length() - 2).trim()));
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_HEIGHT, Float.parseFloat(height.substring(0, height.length() - 2).trim()));
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_WEIGHT, Float.parseFloat(weight.substring(0, weight.length() - 2).trim()));
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_DOCTOR_NAME, doctorNameEditText.getText().toString());
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_BLOOD_GROUP, bloodGrpSpinner.getSelectedItem().toString());
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_DOCTOR_CONTACT, doctorContactEditText.getText().toString());
                        Intent gotoMainIntent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(gotoMainIntent);
                    } else {
                        System.out.println("something went wrong");

                    }
//                    } else {
//                        System.out.println("Add doctor data");
//                        Toast.makeText(BirthDataCollection.this, "Add doctor details", Toast.LENGTH_SHORT).show();
//                    }
                } else {
                    System.out.println("SELECT VALUES");
                    Toast.makeText(BirthDataCollection.this, "Add baby details", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder alertDilalog = new AlertDialog.Builder(this);
        alertDilalog.setMessage("Do you want to exit");
        alertDilalog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                Intent intentfinish = new Intent(Intent.ACTION_MAIN);
                intentfinish.addCategory(Intent.CATEGORY_HOME);
                intentfinish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentfinish);
                finish();
                System.exit(1);

            }
        });
        alertDilalog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alertDilalog.setCancelable(false);
        alertDilalog.create().show();

    }
}
