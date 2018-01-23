package com.maakservicess.beingparents.app_monitor.activities;

import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by Amey on 3/1/2016.
 */
public class AboutActivity extends AppCompatActivity {
    private String app_ver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_activity);

        try {
            app_ver = this.getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            System.out.println(e);
        }

        TextView versionTextView = (TextView) findViewById(R.id.appVersionTextView);
        versionTextView.setText("Vesrion " + app_ver);
    }
}
