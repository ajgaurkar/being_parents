package com.maakservicess.beingparents.app_monitor.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.maakservicess.beingparents.app_monitor.MainActivity;
import com.maakservicess.beingparents.app_monitor.R;


/**
 * Created by ajinkya on 3/8/2016.
 */
//USED TO SHOW SPLASH SCREEN
//JUST FOR SHOW/BRANDING

public class SplashScreen extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen_main);

        StartAnimations();

        new Handler().postDelayed(new Runnable() {
            // Using handler with postDelayed called runnable run method
            @Override
            public void run() {
                Intent mainActivityIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(mainActivityIntent);
                // close this activity
                finish();

            }
        }, 3500); // wait for n seconds

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        RelativeLayout l = (RelativeLayout) findViewById(R.id.splash_layout);
        l.clearAnimation();
        l.startAnimation(anim);

        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.splash_image_view);
        ImageView ivtextview = (ImageView) findViewById(R.id.splash_text_view);
        ivtextview.clearAnimation();
        ivtextview.startAnimation(anim);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }
}
