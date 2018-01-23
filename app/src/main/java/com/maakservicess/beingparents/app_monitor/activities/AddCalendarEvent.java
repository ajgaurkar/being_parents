package com.maakservicess.beingparents.app_monitor.activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.CursorLoader;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maakservicess.beingparents.app_monitor.DatabaseHandler;
import com.maakservicess.beingparents.app_monitor.R;
import com.maakservicess.beingparents.app_monitor.controllers.CalendarEntryListData;
import com.maakservicess.beingparents.app_monitor.fragments.Calendar_Moments_Fragment;
import com.maakservicess.beingparents.app_monitor.fragments.Calendar_Visits_Fragment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SAI PC on 4/3/2016.
 */
public class AddCalendarEvent extends AppCompatActivity {

    private CircleImageView calendarPic1;
    private CircleImageView calendarPic2;
    private CircleImageView calendarPic3;
    int SELECTED_IMAGE = 1;

    private static final int REQUEST_CAMERA = 1;
    private static final int SELECT_FILE = 2;
    public static File file;
    public static File myDir;
    private String calAttachmentFileName;

    //components for on create option item
    public static final int MENU_SAVE = Menu.FIRST;
    public static final int MENU_EDIT = Menu.FIRST + 1;
    int PAGE_MODE = 0; //   0 - add/edit,      1 - read
    boolean EDITING_STARTED_FLAG = false; //   false - edit disabled,      true - edit disabled

    DatabaseHandler databaseHandler;
    HashMap<Integer, String> filePathMap = new HashMap<>();
    List<String> attachmentList = new ArrayList<String>();
    private EditText titleEditText;
    private EditText descriptionEditText;
    private TextView dateTextView;

    CalendarEntryListData selectedCalendarEntryListData;

    String entryDate = null;
    private boolean IMG_EXIST_FLAG;
    private String directory;
    public File mPhotoFile = null;
    public Uri mPhotoFileUri = null;
    private Button deleteEntryBtn;
    private LinearLayout imageLinLayout;
    private int calDataInsertOrUpdateFlag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_calendar_entry);

        //creating directory(if not exist) to store images.
        String root = Environment.getExternalStorageDirectory().toString();
        myDir = new File(root + "/App_Baby");
        System.out.println("myDir" + myDir);
        if (!myDir.exists()) {
            System.out.println("storageDir.mkdir(); " + myDir.mkdir());
            myDir.mkdir();
        }

        databaseHandler = new DatabaseHandler(this);

        titleEditText = (EditText) findViewById(R.id.calendarTitleEditText);
        imageLinLayout = (LinearLayout) findViewById(R.id.addCalEntryImageLinearLayout);
        descriptionEditText = (EditText) findViewById(R.id.calendarDescriptionEditText);

        dateTextView = (TextView) findViewById(R.id.calendarDateTextView);
        deleteEntryBtn = (Button) findViewById(R.id.deleteCalendarEntryButton);
        deleteEntryBtn.setVisibility(View.INVISIBLE);

        calendarPic1 = (CircleImageView) findViewById(R.id.addCalendarEventPic1);
        calendarPic2 = (CircleImageView) findViewById(R.id.addCalendarEventPic2);
        calendarPic3 = (CircleImageView) findViewById(R.id.addCalendarEventPic3);
//        filePathMap.put(1, null);
//        filePathMap.put(2, null);
//        filePathMap.put(3, null);

        entryDate = getIntent().getStringExtra("EntryDate");
        dateTextView.setText(entryDate);

        if (getIntent().getStringExtra("PageMode").equals("Read")) {
            PAGE_MODE = 1;
            setData();
            setEditable(false);
            deleteEntryBtn.setVisibility(View.INVISIBLE);

            System.out.println("PageMode : " + getIntent().getStringExtra("PageMode"));
            System.out.println("Category : " + getIntent().getStringExtra("Category"));
            System.out.println("SelectedCalId : " + getIntent().getStringExtra("SelectedCalId"));

        }
        //set default blank image view for addition
        setImgFromList();

        //conditions for setting title to actiobar
        String actionBarTitle = null;
        if (getIntent().getStringExtra("Category").equals("Visit")) {
            if (getIntent().getStringExtra("PageMode").equals("Read")) {
                actionBarTitle = "Visit";
            } else {
                actionBarTitle = "Add visit";
            }
        } else {
            if (getIntent().getStringExtra("PageMode").equals("Read")) {
                actionBarTitle = "Moment";
            } else {
                actionBarTitle = "Add moment";
            }
        }
        try {
            getSupportActionBar().setTitle(actionBarTitle);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        calendarPic1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("start : selectImage();");
                SELECTED_IMAGE = 1;
                //check if img is present on imageview or not
                //if present then show option to view img on dialogbox
//                IMG_EXIST_FLAG = filePathMap.get(1) != null;
                IMG_EXIST_FLAG = attachmentList.size() >= SELECTED_IMAGE;
                if (PAGE_MODE == 1) {
                    viewImgFromGallery();
                } else {
                    selectImage();
                }
            }
        });
        calendarPic2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("start : selectImage();");
                SELECTED_IMAGE = 2;
                //check if img is present on imageview or not
                //if present then show option to view img on dialogbox
//                IMG_EXIST_FLAG = filePathMap.get(2) != null;
                IMG_EXIST_FLAG = attachmentList.size() >= SELECTED_IMAGE;
                if (PAGE_MODE == 1) {
                    viewImgFromGallery();
                } else {
                    selectImage();
                }
            }
        });
        calendarPic3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("start : selectImage();");
                SELECTED_IMAGE = 3;
                //check if img is present on imageview or not
                //if present then show option to view img on dialogbox
//                IMG_EXIST_FLAG = filePathMap.get(3) != null;
                IMG_EXIST_FLAG = attachmentList.size() >= SELECTED_IMAGE;
                if (PAGE_MODE == 1) {
                    viewImgFromGallery();
                } else {
                    selectImage();
                }
            }
        });
        deleteEntryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder confirmDeleteDialog = new AlertDialog.Builder(AddCalendarEvent.this);
//                confirmDeleteDialog.setTitle("Delete " + getIntent().getStringExtra("Category"));
                System.out.println("entryDate : " + entryDate);
                confirmDeleteDialog.setTitle("Delete " + getIntent().getStringExtra("Category") + " for " + dateTextView.getText().toString());
                confirmDeleteDialog.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("selectedCalendarEntryListData.getId() : " + selectedCalendarEntryListData.getId());
                        int deleteResponse = databaseHandler.deleteCalendarRecord(selectedCalendarEntryListData.getId());
                        System.out.println("deleteResponse : " + deleteResponse);
                        AddCalendarEvent.this.finish();
                    }
                });
                confirmDeleteDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                confirmDeleteDialog.create().show();
            }
        });
    }

    private void setEditable(boolean editStatus) {
        if (editStatus) {

            titleEditText.setFocusable(true);
            titleEditText.setFocusableInTouchMode(true);
            titleEditText.setClickable(true);
            titleEditText.setLongClickable(true);
            titleEditText.setInputType(InputType.TYPE_CLASS_TEXT);

            descriptionEditText.setFocusable(true);
            descriptionEditText.setFocusableInTouchMode(true);
            descriptionEditText.setClickable(true);
            descriptionEditText.setLongClickable(true);
            descriptionEditText.setInputType(InputType.TYPE_CLASS_TEXT);

//            titleEditText.setEnabled(true);
//            descriptionEditText.setEnabled(true);

        } else {

            titleEditText.setFocusable(false);
            titleEditText.setFocusableInTouchMode(false);
            titleEditText.setClickable(false);
            titleEditText.setLongClickable(false);
            titleEditText.setInputType(InputType.TYPE_NULL);

            descriptionEditText.setFocusable(false);
            descriptionEditText.setFocusableInTouchMode(false);
            descriptionEditText.setClickable(false);
            descriptionEditText.setLongClickable(false);
            descriptionEditText.setInputType(InputType.TYPE_NULL);

//            titleEditText.setEnabled(false);
            //setEnabled() disables all features. didn't need that much

        }
    }

    private void viewImgFromGallery() {

//        String imgName = filePathMap.get(SELECTED_IMAGE);
        String imgName = attachmentList.get(--SELECTED_IMAGE);
        //regain value after operation therefore SELECTED_IMAGE++
        SELECTED_IMAGE++;
        String filePath = "file://" + myDir + "/" + imgName;
        System.out.println("filePath for img : " + imgName);
        Uri uri = Uri.parse(filePath);
        System.out.println(uri);

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        Log.d("jpeg and png content", "");
        intent.setDataAndType(uri, "image/*");
        try {
            startActivity(intent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "Problem opening image", Toast.LENGTH_SHORT).show();
        }

    }

    //Method to set data on screen when calendar item is selected
    private void setData() {

        if (getIntent().getStringExtra("Category").equals("Visit")) {
            selectedCalendarEntryListData = Calendar_Visits_Fragment.selectedVisitData;
        } else {
            selectedCalendarEntryListData = Calendar_Moments_Fragment.selectedMomentData;
        }

        if (selectedCalendarEntryListData.getImgCount() == 0) {
            imageLinLayout.setVisibility(View.GONE);
        }

        DateFormat srcFormat = new SimpleDateFormat("dd MM yyyy", Locale.getDefault());
        DateFormat trgtFormat = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
        Date date = null;
        String entryDate = null;

        try {
            date = srcFormat.parse(selectedCalendarEntryListData.getEntryDateDay() + " " + selectedCalendarEntryListData.getEntryDateMonth() + " " + selectedCalendarEntryListData.getEntryDateYear());
            entryDate = trgtFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        descriptionEditText.setText(selectedCalendarEntryListData.getDescription());
        titleEditText.setText(selectedCalendarEntryListData.getTitle());
        dateTextView.setText(entryDate);


        //setting filenbames to LIST for further use(check if null present then do not add)
        if (selectedCalendarEntryListData.getImgFirstLink() != null) {
            attachmentList.add(selectedCalendarEntryListData.getImgFirstLink());
        }
        if (selectedCalendarEntryListData.getImgSecondLink() != null) {
            attachmentList.add(selectedCalendarEntryListData.getImgSecondLink());
        }
        if (selectedCalendarEntryListData.getImgThirdLink() != null) {
            attachmentList.add(selectedCalendarEntryListData.getImgThirdLink());
        }
        System.out.println("attachmentList.size() : " + attachmentList.size());

        setImgFromList();

        //setting filenbames to map for further use
//        filePathMap.put(1, selectedCalendarEntryListData.getImgFirstLink());
//        filePathMap.put(2, selectedCalendarEntryListData.getImgSecondLink());
//        filePathMap.put(3, selectedCalendarEntryListData.getImgThirdLink());

        //setting images to imgview
//        calendarPic1.setImageURI(fileNameToURI(filePathMap.get(1)));
//        calendarPic2.setImageURI(fileNameToURI(filePathMap.get(2)));
//        calendarPic3.setImageURI(fileNameToURI(filePathMap.get(3)));

    }

    private void setImgFromList() {

        switch (attachmentList.size()) {
            case 0:
                if (PAGE_MODE == 0) {
                    System.out.println("1 demo");
                    calendarPic1.setVisibility(View.VISIBLE);
                    calendarPic1.setImageResource(R.drawable.static_image_to_be_displayed_on_add_calendar_entry_xml_when_no_image_is_set);

                    calendarPic2.setVisibility(View.GONE);
                    calendarPic3.setVisibility(View.GONE);
                }
                break;
            case 1:
                if (PAGE_MODE == 0) {
                    System.out.println("1 fill 2 demo");
                    calendarPic1.setVisibility(View.VISIBLE);
                    calendarPic1.setImageURI(fileNameToURI(attachmentList.get(0)));

                    calendarPic2.setVisibility(View.VISIBLE);
                    calendarPic2.setImageResource(R.drawable.static_image_to_be_displayed_on_add_calendar_entry_xml_when_no_image_is_set);
                    calendarPic3.setVisibility(View.GONE);
                } else {
                    System.out.println("1 fill 2 3 gone");
                    calendarPic1.setVisibility(View.VISIBLE);
                    calendarPic1.setImageURI(fileNameToURI(attachmentList.get(0)));

                    calendarPic2.setVisibility(View.GONE);
                    calendarPic3.setVisibility(View.GONE);
                }
                break;
            case 2:
                if (PAGE_MODE == 0) {
                    System.out.println("1 fill 2 fill 3 demo");
                    calendarPic1.setVisibility(View.VISIBLE);
                    calendarPic2.setVisibility(View.VISIBLE);
                    calendarPic3.setVisibility(View.VISIBLE);

                    calendarPic1.setImageURI(fileNameToURI(attachmentList.get(0)));
                    calendarPic2.setImageURI(fileNameToURI(attachmentList.get(1)));
                    calendarPic3.setImageResource(R.drawable.static_image_to_be_displayed_on_add_calendar_entry_xml_when_no_image_is_set);
                } else {
                    System.out.println("1 fill 2 fill 3 demo");
                    calendarPic1.setVisibility(View.VISIBLE);
                    calendarPic2.setVisibility(View.VISIBLE);
                    calendarPic3.setVisibility(View.GONE);

                    calendarPic1.setImageURI(fileNameToURI(attachmentList.get(0)));
                    calendarPic2.setImageURI(fileNameToURI(attachmentList.get(1)));
                }
                break;
            case 3:
                System.out.println("1 fill 2 fill 3 fill");
                calendarPic1.setVisibility(View.VISIBLE);
                calendarPic2.setVisibility(View.VISIBLE);
                calendarPic3.setVisibility(View.VISIBLE);

                calendarPic1.setImageURI(fileNameToURI(attachmentList.get(0)));
                calendarPic2.setImageURI(fileNameToURI(attachmentList.get(1)));
                calendarPic3.setImageURI(fileNameToURI(attachmentList.get(2)));

                break;

        }

    }

    private void selectImage() {
        System.out.println("IMG_EXIST_FLAG : " + IMG_EXIST_FLAG);
        CharSequence[] listItems = {"Open camera", "Open gallery"};
        if (IMG_EXIST_FLAG) {
            listItems = new CharSequence[4];
            listItems[0] = "View";
            listItems[1] = "Open camera";
            listItems[2] = "Open gallery";
            listItems[3] = "Remove";
        }

        //reassigning because "items" is final and cant be changed according to condtn
        //hence do all changes ito a temp "listItem" and finnally set into final "item"
        final CharSequence[] items = listItems;

        AlertDialog.Builder builder = new AlertDialog.Builder(AddCalendarEvent.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Open camera")) {
                    System.out.println("take photo clicked");
                    takePicture();

                } else if (items[item].equals("Open gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);

                } else if (items[item].equals("Remove")) {

//                    File file = new File(myDir, filePathMap.get(SELECTED_IMAGE));
                    File file = new File(myDir, attachmentList.get(--SELECTED_IMAGE));
                    //regain value after operation therefore SELECTED_IMAGE++
                    SELECTED_IMAGE++;
                    boolean deleted = file.delete();
                    System.out.println("file deleted status : " + deleted);
                    if (deleted) {
//                        filePathMap.put(SELECTED_IMAGE, null);
                        attachmentList.remove(--SELECTED_IMAGE);
                        //regain value after operation therefore SELECTED_IMAGE++
                        SELECTED_IMAGE++;
                        setImgFromList();
                        System.out.println("attachmentList : " + attachmentList);
                    }
                } else if (items[item].equals("View")) {

                    viewImgFromGallery();
                }
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.show();

    }

    public void takePicture() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        System.out.println("INSIDE takePicture");

        //Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            try {
                mPhotoFile = createImageFile();
                System.out.println("file path " + mPhotoFile);

            } catch (IOException ex) {
                // Error occurred while creating the File
                System.out.println("Error occurred while creating the File");
            }
            // Continue only if the File was successfully created
            if (mPhotoFile != null) {
                mPhotoFileUri = Uri.fromFile(mPhotoFile);
                System.out.println("file mPhotoFileUri " + mPhotoFileUri);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mPhotoFileUri);
                startActivityForResult(takePictureIntent, REQUEST_CAMERA);
                Log.d("Photo size after intent", "" + mPhotoFile.length());

            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        return File.createTempFile(imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                myDir      /* directory */
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {

            if (requestCode == REQUEST_CAMERA) {
//                switch (SELECTED_IMAGE) {
//                    case 1:
//                        calendarPic1.setImageURI(mPhotoFileUri);
//                        //set file name to map
//                        filePathMap.put(1, uriToFileName(mPhotoFileUri));
//                        break;
//                    case 2:
//                        calendarPic2.setImageURI(mPhotoFileUri);
//                        //set file name to map
//                        filePathMap.put(2, uriToFileName(mPhotoFileUri));
//                        break;
//                    case 3:
//                        calendarPic3.setImageURI(mPhotoFileUri);
//                        //set file name to map
//                        filePathMap.put(3, uriToFileName(mPhotoFileUri));
//                        break;
//                }
                attachmentList.add(uriToFileName(mPhotoFileUri));
                setImgFromList();

            } else if (requestCode == SELECT_FILE) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};
                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null, null);
                Cursor cursor = cursorLoader.loadInBackground();
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();
                String selectedImagePath = cursor.getString(column_index);
                System.out.println("selectedImagePath" + selectedImagePath);
                Bitmap bm;
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
                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                switch (SELECTED_IMAGE) {
                    case 1:
                        calendarPic1.setImageBitmap(bm);
                        break;
                    case 2:
                        calendarPic2.setImageBitmap(bm);
                        break;
                    case 3:
                        calendarPic3.setImageBitmap(bm);
                        break;

                }
                SaveImage(bm);
            }
        }

    }

    private void SaveImage(Bitmap finalBitmap) {

        String root = Environment.getExternalStorageDirectory().toString();
        myDir = new File(root + "/App_Baby");
        System.out.println("myDir" + myDir);
        myDir.mkdirs();
        Random generator = new Random();
        int n = 10000;
        n = generator.nextInt(n);
        calAttachmentFileName = "Image-" + n + ".jpg";
        file = new File(myDir, calAttachmentFileName);
        System.out.println("myDir : " + file);
        System.out.println("baby_Profile_File_Name : " + calAttachmentFileName);

        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            System.out.println("out" + out);
            finalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        attachmentList.add(calAttachmentFileName);
//        switch (SELECTED_IMAGE) {
//            case 1:
//                filePathMap.put(1, calAttachmentFileName);
//                break;
//            case 2:
//                filePathMap.put(2, calAttachmentFileName);
//                break;
//            case 3:
//                filePathMap.put(3, calAttachmentFileName);
//                break;
//
//        }
        System.out.println("attachmentList size after gallery add : " + attachmentList.size());
        setImgFromList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        if (PAGE_MODE == 0) {
            menu.add(Menu.NONE, MENU_SAVE, Menu.NONE, "Save ").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);

        } else if (PAGE_MODE == 1) {
            menu.add(Menu.NONE, MENU_EDIT, Menu.NONE, "Edit ").setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
//            setEditable(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_SAVE:
                System.out.println("add");
                addDataToCalendar();
                if (calDataInsertOrUpdateFlag == 1) {
                    PAGE_MODE = 1;
                    invalidateOptionsMenu();
                    EDITING_STARTED_FLAG = false;
                    setImgFromList();
                }

                return true;
            case MENU_EDIT:
                System.out.println("delete");
                PAGE_MODE = 0;
                EDITING_STARTED_FLAG = true;
                setEditable(true);
                imageLinLayout.setVisibility(View.VISIBLE);
                deleteEntryBtn.setVisibility(View.VISIBLE);
                invalidateOptionsMenu();
                setImgFromList();

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addDataToCalendar() {

        DateFormat format = new SimpleDateFormat("dd MMM yy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        Date date = null;
        try {
            date = format.parse(dateTextView.getText().toString());
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        calendar.get(Calendar.YEAR);

        //months starts from 0. so need to bre incremented
        int incremented_month = calendar.get(Calendar.MONTH);
        incremented_month++;

        calendar.get(Calendar.DATE);
        if (titleEditText.getText().toString().equals("")) {
            System.out.println("INSIDE IF");
            Toast.makeText(getApplicationContext(), " Add title", Toast.LENGTH_SHORT).show();
        } else {
//            setEditable(true);
            System.out.println("INSIDE ELSE");
            if (!EDITING_STARTED_FLAG) {
                System.out.println("entry ADDED");
                calDataInsertOrUpdateFlag = databaseHandler.addCalendar(new CalendarEntryListData(0, calendar.get(Calendar.DATE), incremented_month, calendar.get(Calendar.YEAR), titleEditText.getText().toString(), descriptionEditText.getText().toString(), getFileFromListIfExist(1), getFileFromListIfExist(2), getFileFromListIfExist(3), attachmentList.size(), getIntent().getStringExtra("Category")));
            } else {
                System.out.println("entry UPDATED");
                calDataInsertOrUpdateFlag = databaseHandler.updateCalendar(new CalendarEntryListData(selectedCalendarEntryListData.getId(), calendar.get(Calendar.DATE), incremented_month, calendar.get(Calendar.YEAR), titleEditText.getText().toString(), descriptionEditText.getText().toString(), getFileFromListIfExist(1), getFileFromListIfExist(2), getFileFromListIfExist(3), attachmentList.size(), getIntent().getStringExtra("Category")));
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(getIntent().getStringExtra("Category") + " saved");
            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.setCancelable(false);
            builder.create().show();

            Cursor testCursor = databaseHandler.getAllCalendarDataByCursor();
            System.out.println("testCursor.getCount() : " + testCursor.getCount());

            if (testCursor.moveToFirst()) {
                do {
                    System.out.println("--------------------- " + "\n" + "testCursor data item " + " " + testCursor.getString(0) + " " +
                            testCursor.getString(1) + " " + testCursor.getString(2) + " " + testCursor.getString(3) + " " +
                            testCursor.getString(4) + " " + testCursor.getString(5) + " " + testCursor.getString(6) + " " +
                            testCursor.getString(7) + " " + testCursor.getString(8) + " " + testCursor.getString(9) + " " + testCursor.getString(10));
                } while (testCursor.moveToNext());
            }
        }
    }

    private String getFileFromListIfExist(int position) {
        if (attachmentList.size() >= position) {
            return attachmentList.get(--position);
        } else {
            return null;
        }
    }

//    private int getImageCount() {
//
//        int imgCount = 0;
//        for (int i = 1; i <= filePathMap.size(); i++) {
//            if (filePathMap.get(i) != null) {
//                imgCount++;
//            }
//        }
//
////        return attachmentList.size();
//        return imgCount;
//
//    }

    //method to get file name from uri
    private String uriToFileName(Uri mPhotoFileUri) {

        String uriString = mPhotoFileUri.toString();
        URI uri = null;
        try {
            uri = new URI(uriString);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        URL videoUrl = null;
        try {
            if (uri != null) {
                videoUrl = uri.toURL();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        File tempFile = null;
        if (videoUrl != null) {
            tempFile = new File(videoUrl.getFile());
        }

        return tempFile.getName();
    }

    //method to get URI from file name
    private Uri fileNameToURI(String imageName) {
        String filePath = "file://" + myDir + "/" + imageName;
        System.out.println("filePath for FETCHED img : " + imageName);

        return Uri.parse(filePath);
    }

}