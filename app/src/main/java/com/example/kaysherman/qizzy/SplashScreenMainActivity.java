package com.example.kaysherman.qizzy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class SplashScreenMainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_sreen_main);
        Animation animationUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in);
        Animation animationDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);

        ImageView imageView = (ImageView)findViewById(R.id.animation);
        imageView.setAnimation(animationUp);
        TextView textView = (TextView)findViewById(R.id.text_animation);
        textView.setAnimation(animationDown);
        ProgressBar progressBar =(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setAnimation(animationDown);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },3000);
    }
}
