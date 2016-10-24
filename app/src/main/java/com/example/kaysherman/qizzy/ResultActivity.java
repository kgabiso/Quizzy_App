package com.example.kaysherman.qizzy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ResultActivity extends AppCompatActivity {

    TextView result, score;
    RatingBar star;
    boolean isOpen = false;
    FloatingActionButton fab_2, fab_1, fbPointSummary;
    Animation fabOpen, fabClose, fabClockwise, fabAntiClockwise;
    double percentage;
    int rate;
    int correct;
    int questions;
    String challenge;
    String currentDate,ResultSummary;
    SQLiteDatabase PointsBD = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        /****getting data from extras intent******/
        correct = Integer.parseInt(getIntent().getExtras().getString("correct"));
        questions = Integer.parseInt(getIntent().getExtras().getString("questions"));
        challenge = getIntent().getExtras().getString("Activity");

        /*********Animation*******/
        Animation firstText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_fade_out_from_bottom);
        Animation secondText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_fade_out_from_bottom);
        Animation ThirdStar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_fade_out_from_bottom);
        secondText.setDuration(1000);

        result = (TextView) findViewById(R.id.percentage);
        score = (TextView) findViewById(R.id.score);
        star = (RatingBar) findViewById(R.id.ratingBar);

        fbPointSummary = (FloatingActionButton) findViewById(R.id.fabPoints);
        fab_1 = (FloatingActionButton) findViewById(R.id.fab_1);
        fab_2 = (FloatingActionButton) findViewById(R.id.fab_2);

        fabOpen = AnimationUtils.loadAnimation(this, R.anim.anim_fab_open);
        fabClose = AnimationUtils.loadAnimation(this, R.anim.anim_fab_close);
        fabClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise);
        fabAntiClockwise = AnimationUtils.loadAnimation(this, R.anim.rotate_anticlockwise);

        score.setAnimation(firstText);
        result.setAnimation(secondText);
        star.setAnimation(ThirdStar);
        /********************************DateTime*****************************************/
        SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd_HH:mm:ss");//Date
        currentDate = sdf.format(new Date());

        percentage = ((double) correct / questions) * 100;
        rate = (int) ((Math.round(percentage) * 5) / 100);
        star.setRating(rate);
        star.setEnabled(false);
        score.setText("Your final score is : " + correct + "/" + questions);
        result.setText("Percentage: " + Math.round(percentage) + " %");

        createDB();
        addContent();

        fab_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ResultActivity.this, SummaryActivity.class);
                intent.putExtra("Summary", String.valueOf(getContent()));
                startActivity(intent);
                finish();
            }
        });
        fab_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
                String correctQ = String.valueOf(correct + "/" + questions);
                if(rate < 3)
                {
                    ResultSummary  = "Your results are bad !";
                    ResultSummary = ResultSummary + "\nChallenge:\t"+ challenge;
                    ResultSummary = ResultSummary + "\nRate:\t"+rate;
                    ResultSummary = ResultSummary + "\nPercentage:\t"+Math.round(percentage);
                    ResultSummary = ResultSummary + "\nCorrect Question:\t"+ correctQ;
                    ResultSummary = ResultSummary + "\nDate:\t"+currentDate;
                    builder.setIcon(R.drawable.ic_mood_bad_black_24dp);
                }
                else
                {
                    ResultSummary  = "Your results are Good !";
                    ResultSummary = ResultSummary + "\nChallenge:\t"+ challenge;
                    ResultSummary = ResultSummary + "\nRate:\t"+rate;
                    ResultSummary = ResultSummary + "\nPercentage:"+Math.round(percentage)+"%\t";
                    ResultSummary = ResultSummary + "\nCorrect Question:\t"+ correctQ;
                    ResultSummary = ResultSummary + "\nDate:\t"+currentDate;
                    builder.setIcon(R.drawable.ic_insert_emoticon_black_24dp);
                }

                builder.setTitle("Summary of Results");
                builder.setMessage(ResultSummary);
                builder.setNeutralButton("OK",null);
                builder.setCancelable(false);
                builder.show();
            }
        });
        fbPointSummary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (isOpen) {
                    fab_1.startAnimation(fabClose);
                    fab_2.startAnimation(fabClose);
                    fbPointSummary.startAnimation(fabAntiClockwise);
                    fab_1.setClickable(false);
                    fab_2.setClickable(false);
                    isOpen = false;
                } else {
                    fab_1.startAnimation(fabOpen);
                    fab_2.startAnimation(fabOpen);
                    fbPointSummary.startAnimation(fabClockwise);
                    fab_1.setClickable(true);
                    fab_2.setClickable(true);
                    isOpen = true;
                }
            }
        });

    }

    @Override
    public void onBackPressed() {

        Animation firstText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_fade_in_from_bottom);
        Animation secondText = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_fade_in_from_bottom);
        Animation ThirdStar = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_fade_in_from_bottom);

        score.startAnimation(firstText);
        result.startAnimation(secondText);
        star.startAnimation(ThirdStar);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 500);
    }

    public void createDB() {
        try {
            PointsBD = this.openOrCreateDatabase("MyPoint", MODE_PRIVATE, null);
            PointsBD.execSQL("CREATE TABLE IF NOT EXISTS Points" +
                    "(id integer primary key,challenge VARCHAR,percentage VARCHAR,rate VARCHAR,correct_Questions VARCHAR,date VARCHAR);");
            File database = getApplicationContext().getDatabasePath("MyPoint.db");
            if (!database.exists()) {
                Toast.makeText(this, "Results recorded", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Database not exist", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e("Contact Error", "Error Creating Database");
        }
    }

    public String getContent() {
        Cursor cursor = PointsBD.rawQuery("SELECT * FROM Points ORDER BY date DESC", null);
        int idColumn = cursor.getColumnIndex("id");
        int challengColumn = cursor.getColumnIndex("challenge");
        int percColumn = cursor.getColumnIndex("percentage");
        int ratelColumn = cursor.getColumnIndex("rate");
        int correctColumn = cursor.getColumnIndex("correct_Questions");
        int dateColumn = cursor.getColumnIndex("date");

        cursor.moveToFirst();

        String resultList = "";

        if (cursor != null && (cursor.getCount() > 0)) {
            do {
                String id = cursor.getString(idColumn);
                String percent = cursor.getString(percColumn);
                String ratestar = cursor.getString(ratelColumn);
                String correct_Q = cursor.getString(correctColumn);
                String challenge = cursor.getString(challengColumn);
                String date = cursor.getString(dateColumn);
                resultList = resultList + "Challenge:\t" + challenge + "\nPercentage:\t " + percent + "%\nStars:\t " + ratestar + "\nCorrect Questions:\t " + correct_Q + "\nDate:\t " + date + "\n\n";

            } while (cursor.moveToNext());

            return resultList;

        } else {
            Toast.makeText(ResultActivity.this, "No Results to Show", Toast.LENGTH_SHORT).show();

        }
        return resultList;
    }
    public void addContent() {
        String Percentage = String.valueOf(Math.round(percentage));
        String Rate = String.valueOf(rate);
        String correctQ = String.valueOf(correct + "/" + questions);

        PointsBD.execSQL("INSERT INTO Points(challenge,percentage,rate,correct_Questions,date) VALUES('" + challenge + "','" + Percentage + "','" + Rate + "','" + correctQ + "','" + currentDate + "');");
    }

    public void deleteContent() {

        PointsBD.execSQL("DELETE FROM Points;");
    }

}
