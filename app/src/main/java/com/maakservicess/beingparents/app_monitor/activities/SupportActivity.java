package com.maakservicess.beingparents.app_monitor.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;

import com.maakservicess.beingparents.app_monitor.R;

/**
 * Created by Amey on 3/1/2016.
 */
public class SupportActivity extends ActionBarActivity{
    View helpView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.support_activity);
        helpView=(View)findViewById(R.id.helpview);
        helpView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent helpIntent=new Intent(SupportActivity.this,Help.class);
                startActivity(helpIntent);
            }
        });

    }
}
