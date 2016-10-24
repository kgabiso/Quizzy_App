package com.example.kaysherman.qizzy;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenMainActivity extends AppCompatActivity {

    String loading = "Loading";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen_main);
        Animation animationUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in);
        Animation animationDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);

        ImageView imageView = (ImageView)findViewById(R.id.animation);
        imageView.setAnimation(animationUp);
        final TextView textView = (TextView)findViewById(R.id.text_animation);
       // textView.setAnimation(animationDown);
        ProgressBar progressBar =(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setAnimation(animationDown);

        //textView.setTextSize(35);
        new CountDownTimer(4000, 900) {
            @Override
            public void onTick(long l) {
                loading = loading+".";
                textView.setText(loading);
            }

            @Override
            public void onFinish() {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }.start();

    }
}
