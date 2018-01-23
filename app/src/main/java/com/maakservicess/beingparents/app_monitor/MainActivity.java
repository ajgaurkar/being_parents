package com.maakservicess.beingparents.app_monitor;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maakservicess.beingparents.app_monitor.activities.AboutActivity;
import com.maakservicess.beingparents.app_monitor.activities.AppNotifications;
import com.maakservicess.beingparents.app_monitor.activities.BabyDetails;
import com.maakservicess.beingparents.app_monitor.activities.BirthDataCollection;
import com.maakservicess.beingparents.app_monitor.activities.Danger_Signs_Main;
import com.maakservicess.beingparents.app_monitor.activities.Development_main;
import com.maakservicess.beingparents.app_monitor.activities.DiaperMainActivity;
import com.maakservicess.beingparents.app_monitor.activities.Doctor_visit_main;
import com.maakservicess.beingparents.app_monitor.activities.Feeding_main;
import com.maakservicess.beingparents.app_monitor.activities.Growth_main;
import com.maakservicess.beingparents.app_monitor.activities.Guidance_Main;
import com.maakservicess.beingparents.app_monitor.activities.Teeth_main;
import com.maakservicess.beingparents.app_monitor.activities.Vaccination_main;
import com.maakservicess.beingparents.app_monitor.controllers.VaccineSqliteData;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.ShowcaseConfig;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    DisplayMetrics metrics;
    int width = 0, height = 0;
    View view;
    SessionManager mainManager;
    private long back_pressed;
    ImageView growthImgBtn;
    ImageView developmentImgBtn;
    ImageView feedingImgBtn;
    ImageView immunizationImgBtn;
    ImageView teethImgBtn;
    ImageView doctorVisitBtn;
    ImageView diaperImgBtn;
    ImageView guidanceImgBtn;

    private RelativeLayout immunizationLayoutBtn;
    private RelativeLayout growthLayoutBtn;
    private RelativeLayout diaperayoutBtn;
    private RelativeLayout feedingLayoutBtn;
    private RelativeLayout developmentLayoutBtn;
    private RelativeLayout guidanceLayoutBtn;
    private RelativeLayout doctorVisitLayoutBtn;
    private RelativeLayout teethLayoutBtn;
    private RelativeLayout dangerSignsLayoutBtn;
    DatabaseHandler databaseHandler;
    private List<VaccineSqliteData> readVaccineData;
    private Workbook wb = null;
    DatabaseHandler database;
    private Cursor getsize;
    private InputStream resources;
    private Fragment fragment = null;

    HashMap<String, String> babyDetailsMap;
    private TextView babyNameTextView;
    private TextView babyAgeTextView;
//    private TextView babySummaryTextView;
    private CircleImageView baby_profile_CircleImageView;
    private String uristring = "/storage/emulated/0/App_Baby/";
    private String shareBody = "Baby App is very useful tool for monitoring your baby development, https://play.google.com/store/apps/details?id=Appbaby_id";
    //    private ImageView notificationUnreadImageView;
//    private ImageView notificationBellImageView;
    private AdView mAdView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAdView = (AdView) findViewById(R.id.contentMainAdView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mainManager = new SessionManager(getApplicationContext());
        babyNameTextView = (TextView) findViewById(R.id.babyNameTextView);
        babyAgeTextView = (TextView) findViewById(R.id.babyAgeTextView);
//        babySummaryTextView = (TextView) findViewById(R.id.babySummaryTextView);
        baby_profile_CircleImageView = (CircleImageView) findViewById(R.id.editUserDetailDisplayPic);
//        notificationBellImageView = (ImageView) findViewById(R.id.notificationBellImageView);
//        notificationUnreadImageView = (ImageView) findViewById(R.id.notificationBellImageView);

        database = new DatabaseHandler(this);
        mainManager.checkLogin();
        Boolean checkLoginStatus = mainManager.isLoggedIn();
        Log.d("checkLoginStatus", "" + checkLoginStatus);

        babyDetailsMap = mainManager.getBabyDetails();
        System.out.println("KEY_BABY_NAME :" + babyDetailsMap.get(SessionManager.KEY_BABY_NAME));
        System.out.println("KEY_BABY_GENDER :" + babyDetailsMap.get(SessionManager.KEY_BABY_GENDER));
        System.out.println("KEY_BABY_DOB :" + babyDetailsMap.get(SessionManager.KEY_BABY_DOB));
        System.out.println("KEY_BABY_WEIGHT :" + babyDetailsMap.get(SessionManager.KEY_BABY_WEIGHT));
        System.out.println("KEY_BABY_HEIGHT :" + babyDetailsMap.get(SessionManager.KEY_BABY_HEIGHT));
        System.out.println("KEY_BABY_HEAD_CIRCUMFERENCE :" + babyDetailsMap.get(SessionManager.KEY_BABY_HEAD_CIRCUMFERENCE));

        if (checkLoginStatus) {

            if (babyDetailsMap.get(SessionManager.KEY_BABY_WEIGHT).equals("0.0") &&
                    babyDetailsMap.get(SessionManager.KEY_BABY_HEIGHT).equals("0.0") &&
                    babyDetailsMap.get(SessionManager.KEY_BABY_HEAD_CIRCUMFERENCE).equals("0.0")) {


                System.out.println("REDIRECT TO ENTER DETAILS");

                Intent getBirthDataIntent = new Intent(getApplicationContext(), BirthDataCollection.class);
                startActivity(getBirthDataIntent);

            } else {

                setbabyProfilePic();
                babyNameTextView.setText(babyDetailsMap.get(SessionManager.KEY_BABY_NAME));

//            method call to set age of baby
//            babyAgeTextView.setText(babyDetailsMap.get(SessionManager.KEY_BABY_DOB));
                babyAgeTextView.setText(getBabyAge(babyDetailsMap.get(SessionManager.KEY_BABY_DOB)));


//            babySummaryTextView.setText(babyDetailsMap.get(SessionManager.KEY_BABY_HEIGHT) + " " + babyDetailsMap.get(SessionManager.KEY_BABY_WEIGHT) + " " + babyDetailsMap.get(SessionManager.KEY_BABY_HEAD_CIRCUMFERENCE));
                //  babySummaryTextView.setText("Baby is growing healty. Expected to walk within few weeks");
            }
        }
        view = (View) findViewById(R.id.testView2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        databaseHandler = new DatabaseHandler(this);

        immunizationLayoutBtn = (RelativeLayout) findViewById(R.id.immunization_grid);
        growthLayoutBtn = (RelativeLayout) findViewById(R.id.growth_grid);
        diaperayoutBtn = (RelativeLayout) findViewById(R.id.diaper_grid);
        feedingLayoutBtn = (RelativeLayout) findViewById(R.id.feeding_grid);
        developmentLayoutBtn = (RelativeLayout) findViewById(R.id.development_grid);
        guidanceLayoutBtn = (RelativeLayout) findViewById(R.id.guidance_grid);
        doctorVisitLayoutBtn = (RelativeLayout) findViewById(R.id.doctor_visit_grid);
        teethLayoutBtn = (RelativeLayout) findViewById(R.id.teeth_grid);
        dangerSignsLayoutBtn = (RelativeLayout) findViewById(R.id.danger_signs_grid);

        immunizationLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent immunizationIntent = new Intent(getApplicationContext(), Vaccination_main.class);
                startActivity(immunizationIntent);
            }
        });
        growthLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent growthIntent = new Intent(getApplicationContext(), Growth_main.class);
                startActivity(growthIntent);
            }
        });
        developmentLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent developmentIntent = new Intent(getApplicationContext(), Development_main.class);
                startActivity(developmentIntent);
            }
        });
        teethLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent teethIntent = new Intent(getApplicationContext(), Teeth_main.class);
                startActivity(teethIntent);
            }
        });
        feedingLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent feedingIntent = new Intent(getApplicationContext(), Feeding_main.class);
                startActivity(feedingIntent);
            }
        });
        doctorVisitLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent doctorVisitIntent = new Intent(getApplicationContext(), Doctor_visit_main.class);
                startActivity(doctorVisitIntent);

            }
        });
        diaperayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent diaperIntent = new Intent(getApplicationContext(), DiaperMainActivity.class);
                startActivity(diaperIntent);
            }
        });
        guidanceLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guidanceIntent = new Intent(getApplicationContext(), Guidance_Main.class);
                startActivity(guidanceIntent);
            }
        });
        dangerSignsLayoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent guidanceIntent = new Intent(getApplicationContext(), Danger_Signs_Main.class);
                startActivity(guidanceIntent);
            }
        });
        baby_profile_CircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent babyDetailsIntent = new Intent(getApplicationContext(), BabyDetails.class);
                startActivity(babyDetailsIntent);
            }
        });
//        notificationBellImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent notificationIntent = new Intent(getApplicationContext(), AppNotifications.class);
//                startActivity(notificationIntent);
//            }
//        });

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
        sequence.singleUse("MAIN_SHOWCASE_SEQUENCE");
        sequence.addSequenceItem(baby_profile_CircleImageView,
                "Baby pic appears here. Click here to view baby details", "NEXT");

        sequence.addSequenceItem(findViewById(R.id.options_notifications),
                "App notifications appear here", "NEXT");

        sequence.addSequenceItem(findViewById(R.id.options_drawer),
                "Click here OR swipe from right to explore more options", "DONE");

//        sequence.addSequenceItem(mButtonThree,
//                "This is button three", "GOT IT");

        sequence.start();

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

    private void setbabyProfilePic() {

        if (mainManager.getSpecificBabyDetail(SessionManager.KEY_BABY_PROFILE_FILE_NAME) != null) {
            System.out.println("INSIDE ON setbabyProfilePic if");

            String profile_file_name = mainManager.getSpecificBabyDetail(SessionManager.KEY_BABY_PROFILE_FILE_NAME);
            String string_file_path = uristring + profile_file_name;
            System.out.print(string_file_path);
            System.out.print(profile_file_name);
            File file_path = new File(string_file_path);
            Bitmap myBitmap = BitmapFactory.decodeFile(file_path.getAbsolutePath());
            baby_profile_CircleImageView.setImageBitmap(myBitmap);
        } else {
            System.out.println("INSIDE ON setbabyProfilePic else");

            baby_profile_CircleImageView.setImageResource(R.drawable.static_image_to_be_displayed_on_add_calendar_entry_xml_when_no_image_is_set);
        }
    }

    private String getBabyAge(String babyDob) {

        int years = 0;
        int months = 0;
        int days = 0;

        String babyAge = "";
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yy", Locale.getDefault());
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

        System.out.println("calculate age method  total years count : " + years);
        System.out.println("calculate age method  total months count : " + months);
        System.out.println("calculate age method  total weeks count : " + weeks);
        System.out.println("calculate age method  total day count : " + days);

        if (years >= 1) {
            if (months >= 1) {
                if (months == 1) {
                    System.out.println("calculate age method  1");
                    babyAge = years + " Years " + months + " Month";
                } else {
                    System.out.println("calculate age method  2");
                    babyAge = years + " Years " + months + " Months";
                }
            } else {
                if (weeks == 1) {
                    System.out.println("calculate age method  3");
                    babyAge = years + " Years " + weeks + " Week";
                } else {
                    System.out.println("calculate age method  4");
                    babyAge = years + " Years " + weeks + " Weeks";
                }
            }
        } else if (months >= 1) {
            if (months == 1) {
                if (weeks >= 1) {
                    if (weeks == 1) {
                        System.out.println("calculate age method  5");
                        babyAge = months + " Month " + weeks + " Week";
                    } else {
                        System.out.println("calculate age method  6");
                        babyAge = months + " Month " + weeks + " Weeks";
                    }
                } else {
                    if (days == 1) {
                        System.out.println("calculate age method  7");
                        babyAge = months + " Month " + days + " Day";
                    } else {
                        System.out.println("calculate age method  8");
                        babyAge = months + " Month " + days + " Days";
                    }
                }
            } else {
                if (weeks >= 1) {
                    if (weeks == 1) {
                        System.out.println("calculate age method  9");
                        babyAge = months + " Months " + weeks + " Week";
                    } else {
                        System.out.println("calculate age method  10");
                        babyAge = months + " Months " + weeks + " Weeks";
                    }
                } else {
                    System.out.println("calculate age method  11");
                    babyAge = months + " Months " + days + " Days";
                }
            }
        } else if (weeks >= 1) {
            if (weeks == 1) {
                int remainingDays = days % weeks;
                System.out.println("calculate age method  total remainingDays count : " + remainingDays);

                if (remainingDays >= 1) {
                    if (remainingDays == 1) {
                        System.out.println("calculate age method  12");
                        babyAge = weeks + " Week " + remainingDays + " Day";
                    } else {
                        System.out.println("calculate age method  13");
                        babyAge = weeks + " Week " + remainingDays + " Days";
                    }
                } else {
                    System.out.println("calculate age method  14");
                    babyAge = weeks + " Week ";
                }
            } else {
                int remainingDays = days % weeks;
                System.out.println("calculate age method  total remainingDays count : " + remainingDays);

                if (days >= 1) {
                    if (days == 1) {
                        System.out.println("calculate age method  15");
                        babyAge = weeks + " Weeks " + remainingDays + " Day";
                    } else {
                        System.out.println("calculate age method  16");
                        babyAge = weeks + " Weeks " + remainingDays + " Days";
                    }
                } else {
                    System.out.println("calculate age method  17");
                    babyAge = weeks + " Weeks ";
                }
            }
        } else if (days >= 1) {
            if (days == 1) {
                System.out.println("calculate age method  18");
                babyAge = days + " Day ";
            } else {
                System.out.println("calculate age method  19");
                babyAge = days + " Days ";
            }
        } else {
            System.out.println("calculate age method  20");
            babyAge = "New born";
        }


        return babyAge;
    }

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("INSIDE ON RESUME OF MAIN ACTIVITY");
        setbabyProfilePic();
        babyDetailsMap = mainManager.getBabyDetails();
        babyNameTextView.setText(babyDetailsMap.get(SessionManager.KEY_BABY_NAME));

        if (mAdView != null) {
            mAdView.resume();
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.END)) {
            drawer.closeDrawer(GravityCompat.END);
        } else {
            if (back_pressed + 2000 > System.currentTimeMillis()) {
                Intent intentfinish = new Intent(Intent.ACTION_MAIN);
                intentfinish.addCategory(Intent.CATEGORY_HOME);
                intentfinish.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentfinish);
//                finish();
                System.exit(1);
            } else {
                Toast.makeText(this, "Press again to exit", Toast.LENGTH_SHORT).show();
                back_pressed = System.currentTimeMillis();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so as long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.options_drawer) {
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.openDrawer(GravityCompat.END);
        }
        if (id == R.id.options_notifications) {
            Intent notificationIntent = new Intent(getApplicationContext(), AppNotifications.class);
            startActivity(notificationIntent);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.

        int id = item.getItemId();

        if (id == R.id.feedback) {

            Intent email = new Intent(Intent.ACTION_SENDTO);
            email.setType("message/rfc822");
            email.putExtra(Intent.EXTRA_EMAIL, new String[]{"care.babyapp@gmail.com"});
            email.putExtra(Intent.EXTRA_SUBJECT, "Feedback");
            email.setData(Uri.parse("mailto:"));
            email.putExtra(Intent.EXTRA_TEXT, "Dear ...," + "");
            startActivity(Intent.createChooser(email, "Send Feedback :"));

//        } else if (id == R.id.nav_support) {
//            Intent nav_support_Intent = new Intent(this, SupportActivity.class);
//            startActivity(nav_support_Intent);
        } else if (id == R.id.nav_share) {
            Intent nav_share_Intent = new Intent(android.content.Intent.ACTION_SEND);
            nav_share_Intent.setType("text/plain");
            nav_share_Intent.putExtra(android.content.Intent.EXTRA_SUBJECT, "App Baby");
            nav_share_Intent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
            nav_share_Intent.putExtra(Intent.EXTRA_EMAIL, new String[]{"care.babyapp@gmail.com"});
            startActivity(Intent.createChooser(nav_share_Intent, "Share via :"));

        } else if (id == R.id.nav_rate_us) {

            Uri uri = Uri.parse("market://details?id=" + getPackageName());
            Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
            startActivity(goToMarket);

        } else if (id == R.id.nav_about) {
            Intent about_us_Intent = new Intent(this, AboutActivity.class);
            startActivity(about_us_Intent);

        } else if (id == R.id.nav_disclaimer) {
            AlertDialog.Builder desclaimerAlertDiloag = new AlertDialog.Builder(MainActivity.this);
            desclaimerAlertDiloag.setTitle("Desclaimer");
            desclaimerAlertDiloag.setMessage("Being parents is made for general guidelines for parents. We don't claim that the information provided is applicable to all children, individual variations may occur. Consult your pediatrician before implementing any guideline. Some babies need special care and guidance by expert.\n" +
                    "Reference : \n" +
                    "1. IMNCI guidelines\n" +
                    "2. IYCF training module\n" +
                    "3. Trivendrum developmental screening test. (TDST).\n" +
                    "4. IAP guidelines\n" +
                    "5. WHO growth charts");
            desclaimerAlertDiloag.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });

            desclaimerAlertDiloag.create().show();

        } else if (id == R.id.nav_sign_out) {
            Log.d("B  sign_out", "");
            new AlertDialog.Builder(MainActivity.this)
                    .setMessage("This will clear all the saved data. Are you sure you want to exit")
                    .setTitle("Clear and Exit")
                    .setPositiveButton(R.string.Exit, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            mainManager.logoutUser();
                            databaseHandler.deleteDatabaseTables();

                        }
                    })
                    .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).create().show();
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.END);
        return true;
    }

}
