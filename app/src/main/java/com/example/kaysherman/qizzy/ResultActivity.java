package com.example.kaysherman.qizzy;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {

    TextView result,score;
    RatingBar star;
    double percentage;
    int rate;
    int correct;
    int questions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        /****getting data from extras intent******/
        correct = Integer.parseInt( getIntent().getExtras().getString("correct")) ;
        questions = Integer.parseInt(getIntent().getExtras().getString("questions"));
        /*********Animation*******/
        Animation firstText = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_fade_out_from_bottom);
        Animation secondText = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_fade_out_from_bottom);
        Animation ThirdStar = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_fade_out_from_bottom);
        secondText.setDuration(1000);

        result =(TextView)findViewById(R.id.percentage);
        score =(TextView)findViewById(R.id.score);
        star =(RatingBar)findViewById(R.id.ratingBar);

        score.setAnimation(firstText);
        result.setAnimation(secondText);
        star.setAnimation(ThirdStar);
        /*************************************************************************/
        percentage = ((double) correct / questions) * 100;
        rate = (int) ((Math.round(percentage) * 5 )/ 100);
        star.setRating(rate);

        score.setText("Your final score is : " + correct + "/" + questions);
        result.setText("Percentage: " + Math.round(percentage)  + " %");
    }

    @Override
    public void onBackPressed() {

        Animation firstText = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_fade_in_from_bottom);
        Animation secondText = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_fade_in_from_bottom);
        Animation ThirdStar = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.shrink_fade_in_from_bottom);

        score.startAnimation(firstText);
        result.startAnimation(secondText);
        star.startAnimation(ThirdStar);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                startActivity(intent);
                finish();
            }
        },500);
    }
}
