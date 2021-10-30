package com.example.costall;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 5000;
    //variable used for the animation
    Animation topAnim;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //to disable the toolbar in splash screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        //Animation
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        //Hooks
        image = findViewById(R.id.logo);
        //set animation
        image.setAnimation(topAnim);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent mainActivity = new Intent(SplashActivity.this
                        ,LoginActivity.class);
                startActivity(mainActivity);
            }
        },SPLASH_TIME_OUT);
    }
}