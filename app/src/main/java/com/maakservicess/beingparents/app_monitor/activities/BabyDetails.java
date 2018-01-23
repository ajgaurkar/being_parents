package com.maakservicess.beingparents.app_monitor.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.PermissionChecker;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.SessionManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SAI PC on 5/17/2016.
 */
public class BabyDetails extends Activity {

    SessionManager sessionManager;
    private HashMap<String, String> detailsMap;
    private ImageView editPencil;
    private TextView babyNameTextView;
    private TextView babyAgeTextView;
    private TextView babyBirthHeightTextView;
    private TextView babyBirthWeightTextView;
    private TextView babyBirthHeadTextView;
    private TextView babyCurrentHeadTextView;
    private TextView babyCurrentWeightTextView;
    private TextView babyCurrentHeightTextView;
    private TextView babyDoctorNameTextView;
    private TextView babyDoctorContactTextView;
    private TextView babyBloodGroupTextView;
    private String dataFetecingPara2;
    private String dataFetecingPara1;
    private String gender_of_user;
    private DatabaseHandler database;
    private CircleImageView profilePic;

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    public static File file;
    public static File myDir;
    private String babyProfileFileName;
    private String uristring = "/storage/emulated/0/App_Baby/";
    private EditText dialogBabyNameEditText;
    private EditText dialogDoctorNameEditText;
    private EditText dialogDoctorContactEditText;
    private Spinner dialogBloodGroupSpinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.baby_details_main);



        sessionManager = new SessionManager(BabyDetails.this);

        database = new DatabaseHandler(this);

        editPencil = (ImageView) findViewById(R.id.profileEditImageView);
        babyNameTextView = (TextView) findViewById(R.id.babyNameEditText);
        babyAgeTextView = (TextView) findViewById(R.id.babyAgeEditText);
        babyBirthHeightTextView = (TextView) findViewById(R.id.babyBirthHeightTextView);
        babyBirthWeightTextView = (TextView) findViewById(R.id.babyBirthWeightTextView);
        babyBirthHeadTextView = (TextView) findViewById(R.id.babyBirthHeadTextView);
        babyCurrentWeightTextView = (TextView) findViewById(R.id.babyCurrentWeightTextView);
        babyCurrentHeightTextView = (TextView) findViewById(R.id.babyCurrentHeightTextView);
        babyCurrentHeadTextView = (TextView) findViewById(R.id.babyCurrentHeadTextView);
        babyBloodGroupTextView = (TextView) findViewById(R.id.bloodGroupTextView);
        babyDoctorNameTextView = (TextView) findViewById(R.id.doctorNameTextView);
        babyDoctorContactTextView = (TextView) findViewById(R.id.doctorContactTextView);
        profilePic = (CircleImageView) findViewById(R.id.babyDetailsProfileImageView);

        setDataToTextViews();

        System.out.println(detailsMap.get(SessionManager.KEY_BABY_NAME) + " \n " +
                detailsMap.get(SessionManager.KEY_BABY_BLOOD_GROUP) + " \n " +
                detailsMap.get(SessionManager.KEY_BABY_DOCTOR_NAME) + " \n " +
                detailsMap.get(SessionManager.KEY_BABY_DOCTOR_CONTACT) + " \n " +
                detailsMap.get(SessionManager.KEY_BABY_GENDER) + " \n " +
                detailsMap.get(SessionManager.KEY_BABY_DOB) + " \n " +
                detailsMap.get(SessionManager.KEY_BABY_PROFILE_FILE_NAME));

        babyAgeTextView.setText(getBabyAge(detailsMap.get(SessionManager.KEY_BABY_DOB)));

        babyBirthHeightTextView.setText(detailsMap.get(SessionManager.KEY_BABY_HEIGHT) + " Cm");
        babyBirthWeightTextView.setText(detailsMap.get(SessionManager.KEY_BABY_WEIGHT) + " Kg");
        babyBirthHeadTextView.setText(detailsMap.get(SessionManager.KEY_BABY_HEAD_CIRCUMFERENCE) + " Cm");


//        babyDoctorContactTextView.setText(detailsMap.get(SessionManager.KEY_BABY_DOCTOR_CONTACT));
//        babyDoctorNameTextView.setText(detailsMap.get(SessionManager.KEY_BABY_DOCTOR_NAME));


        if (detailsMap.get(SessionManager.KEY_BABY_GENDER).equals("Baby Boy")) {
            System.out.println("IN gender BOY");
            gender_of_user = "b";
            dataFetecingPara2 = "boy";
            dataFetecingPara1 = "w" + gender_of_user;
            System.out.println("dataFetecingPara1" + dataFetecingPara1 + " dataFetecingPara2 " + dataFetecingPara2);

        } else if (detailsMap.get(SessionManager.KEY_BABY_GENDER).equals("Baby Girl")) {
            System.out.println("IN gender GIRL");
            gender_of_user = "g";
            dataFetecingPara2 = "girl";
            dataFetecingPara1 = "w" + gender_of_user;
            System.out.println("dataFetecingPara1" + dataFetecingPara1 + " dataFetecingPara2 " + dataFetecingPara2);
        }

        dataFetecingPara1 = "w" + gender_of_user;
        babyCurrentWeightTextView.setText(currentDataFetch(dataFetecingPara1, dataFetecingPara2) + " Kg");

        dataFetecingPara1 = "h" + gender_of_user;
        babyCurrentHeightTextView.setText(currentDataFetch(dataFetecingPara1, dataFetecingPara2) + " Cm");

        dataFetecingPara1 = "hc" + gender_of_user;
        babyCurrentHeadTextView.setText(currentDataFetch(dataFetecingPara1, dataFetecingPara2) + " Cm");

//        System.out.println("currentDataFetch(dataFetecingPara1, dataFetecingPara2) : " + currentDataFetch(dataFetecingPara1, dataFetecingPara2));

        editPencil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditor();
            }
        });

        profilePic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        if (detailsMap.get(SessionManager.KEY_BABY_PROFILE_FILE_NAME) != null) {
            String profile_file_name = detailsMap.get(SessionManager.KEY_BABY_PROFILE_FILE_NAME);
            String string_file_path = uristring + profile_file_name;
            System.out.print(string_file_path);
            System.out.print(profile_file_name);
            File file_path = new File(string_file_path);
            Bitmap myBitmap = BitmapFactory.decodeFile(file_path.getAbsolutePath());
            profilePic.setImageBitmap(myBitmap);
        } else {
            profilePic.setImageResource(R.drawable.static_image_to_be_displayed_on_add_calendar_entry_xml_when_no_image_is_set);
        }
    }

    private void setDataToTextViews() {

        detailsMap = sessionManager.getBabyDetails();

        babyNameTextView.setText(detailsMap.get(SessionManager.KEY_BABY_NAME));
        babyBloodGroupTextView.setText(detailsMap.get(SessionManager.KEY_BABY_BLOOD_GROUP));

        if (detailsMap.get(SessionManager.KEY_BABY_DOCTOR_NAME).equals("")) {
            babyDoctorNameTextView.setText("N/A");
        } else {
            babyDoctorNameTextView.setText(detailsMap.get(SessionManager.KEY_BABY_DOCTOR_NAME));
        }

        if (detailsMap.get(SessionManager.KEY_BABY_DOCTOR_CONTACT).equals("")) {
            babyDoctorContactTextView.setText("N/A");
        } else {
            babyDoctorContactTextView.setText(detailsMap.get(SessionManager.KEY_BABY_DOCTOR_CONTACT));
        }
    }

//    private void selectImage() {
//
//        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
//        AlertDialog.Builder builder = new AlertDialog.Builder(BabyDetails.this);
//        builder.setTitle("Add Photo!");
//        builder.setItems(items, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                if (items[item].equals("Take Photo")) {
//                    //if(PermissionChecker.checkSelfPermission(BabyDetails.this,  Manifest.permission.CAMERA))
//                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                    startActivityForResult(intent, REQUEST_CAMERA);
//                } else if (items[item].equals("Choose from Library")) {
//                    Intent intent = new Intent(
//                            Intent.ACTION_PICK,
//                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
//                    intent.setType("image/*");
//                    startActivityForResult(
//                            Intent.createChooser(intent, "Select File"), SELECT_FILE);
//                } else if (items[item].equals("Cancel")) {
//                    dialog.dismiss();
//                }
//            }
//        });
//        builder.show();
//
//    }



    private void selectImage() {

        final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
        AlertDialog.Builder builder = new AlertDialog.Builder(BabyDetails.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    if (PermissionChecker.checkSelfPermission(BabyDetails.this, android.Manifest.permission.CAMERA) == PermissionChecker.PERMISSION_GRANTED) {
                        System.out.println("Self Permission checker");
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, REQUEST_CAMERA);

                    } else {
                        final AlertDialog.Builder checkPermissionAlertDialog;
                        checkPermissionAlertDialog = new AlertDialog.Builder(BabyDetails.this);
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


                } else if (items[item].equals("Choose from Library")) {

                    if (PermissionChecker.checkSelfPermission(BabyDetails.this, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
                        if (PermissionChecker.checkSelfPermission(BabyDetails.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PermissionChecker.PERMISSION_GRANTED) {
                            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            intent.setType("image/*");
                            startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                        } else {
                            final AlertDialog.Builder checkPermissionAlertDialog;
                            checkPermissionAlertDialog = new AlertDialog.Builder(BabyDetails.this);
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
                        checkPermissionAlertDialog = new AlertDialog.Builder(BabyDetails.this);
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
        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_PROFILE_FILE_NAME, babyProfileFileName);

    }

    private String currentDataFetch(String dataFetecingPara1, String gender) {
        System.out.println("INSIDE currentDataFetch");
        ArrayList<String> queryParamsArrayList = new ArrayList<>();
        queryParamsArrayList.add(dataFetecingPara1);
        queryParamsArrayList.add(gender);
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

    private String getBabyAge(String babyDob) {

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
        //if month difference is in negative then reduce years by one and calculate the number of months.
        if (months < 0) {
            years--;
            months = 12 - birthMonth + currMonth;
            if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE))
                months--;
        } else if (months == 0 && now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            years--;
            months = 11;
        }
        //Calculate the days
        if (now.get(Calendar.DATE) > birthDay.get(Calendar.DATE))
            days = now.get(Calendar.DATE) - birthDay.get(Calendar.DATE);
        else if (now.get(Calendar.DATE) < birthDay.get(Calendar.DATE)) {
            int today = now.get(Calendar.DAY_OF_MONTH);
            now.add(Calendar.MONTH, -1);
            days = now.getActualMaximum(Calendar.DAY_OF_MONTH) - birthDay.get(Calendar.DAY_OF_MONTH) + today;
        } else {
            days = 0;
            if (months == 12) {
                years++;
                months = 0;
            }
        }
//        babyAge = years + " Years " + months + " Months " + days + " Days";
        int weeks = days / 7;

        if (years >= 1) {
            if (months >= 1) {
                babyAge = years + " Years " + months + " Months";
            } else {
                babyAge = years + " Years " + weeks + " Weeks";
            }
        } else if (months >= 1) {
            if (weeks >= 1) {
                babyAge = months + " Months " + weeks + " Weeks";
            } else {
                babyAge = months + " Months " + days + " Days";
            }
        } else if (weeks >= 1) {
            int remainingDays = days % weeks;
            if (days >= 1) {
                babyAge = weeks + " Weeks" + remainingDays + " Days";
            } else {
                babyAge = weeks + " Weeks";
            }
        } else {
            if (days >= 1) {
                babyAge = days + " Days";
            }
        }
        return babyAge;
    }

    private void openEditor() {
        LayoutInflater flater = this.getLayoutInflater();
        View view = flater.inflate(R.layout.edit_details_editor_page, null);
        dialogBabyNameEditText = (EditText) view.findViewById(R.id.editBabyDetailsNameEditText);
        dialogDoctorNameEditText = (EditText) view.findViewById(R.id.editBabyDetailsDoctorNameEditText);
        dialogDoctorContactEditText = (EditText) view.findViewById(R.id.editBabyDetailsDoctorContactEditText);
        dialogBloodGroupSpinner = (Spinner) view.findViewById(R.id.editBabyDetailsBloodGroupSppinner);


        ArrayList<String> bloodGrpList = new ArrayList<>();
        bloodGrpList.add("Select blood group");
        bloodGrpList.add("A+");
        bloodGrpList.add("A-");
        bloodGrpList.add("B+");
        bloodGrpList.add("B-");
        bloodGrpList.add("O+");
        bloodGrpList.add("O-");
        bloodGrpList.add("AB+");
        bloodGrpList.add("AB-");

        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(BabyDetails.this, android.R.layout.simple_spinner_item, bloodGrpList);
        spinnerAdapter.setDropDownViewResource(R.layout.range_dropdownspinner_item);
        dialogBloodGroupSpinner.setAdapter(spinnerAdapter);
        dialogBloodGroupSpinner.setSelection(bloodGrpList.indexOf(detailsMap.get(SessionManager.KEY_BABY_BLOOD_GROUP)));

        dialogBabyNameEditText.setText(detailsMap.get(SessionManager.KEY_BABY_NAME));
        dialogDoctorNameEditText.setText(detailsMap.get(SessionManager.KEY_BABY_DOCTOR_NAME));
        dialogDoctorContactEditText.setText(detailsMap.get(SessionManager.KEY_BABY_DOCTOR_CONTACT));

        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(this);
        builder.setView(view);
        builder.setTitle("Edit details");
        builder.setCancelable(false);
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (!dialogBabyNameEditText.getText().toString().trim().equals("")) {
                    if (!dialogBloodGroupSpinner.getSelectedItem().toString().trim().equals("Select blood group")) {
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_NAME, dialogBabyNameEditText.getText().toString());
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_DOCTOR_NAME, dialogDoctorNameEditText.getText().toString());
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_DOCTOR_CONTACT, dialogDoctorContactEditText.getText().toString());
                        sessionManager.setSpecificBabyDetail(SessionManager.KEY_BABY_BLOOD_GROUP, dialogBloodGroupSpinner.getSelectedItem().toString());

                        setDataToTextViews();

                    } else {
                        Toast.makeText(getApplicationContext(), "Data not saved : Invalid inputs", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Data not saved : Invalid inputs", Toast.LENGTH_SHORT).show();
                }

            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

    }


}
