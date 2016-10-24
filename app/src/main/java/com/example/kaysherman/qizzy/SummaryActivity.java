package com.example.kaysherman.qizzy;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;

public class SummaryActivity extends AppCompatActivity {

    TextView summaryResults;
    Button reset;
    ImageView back;
    SQLiteDatabase PointsBD = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summary);
        summaryResults =(TextView)findViewById(R.id.SummaryResults);
        createDB();
        back = (ImageView) findViewById(R.id.backArrow);
        reset =(Button)findViewById(R.id.btnreset);

        summaryResults.setText(getContent());

        summaryResults.setMovementMethod(new ScrollingMovementMethod());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new  Intent(SummaryActivity.this,HomeActivity.class);
                startActivity(intent);
                finish();
            }
        });
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               deleteContent();
                summaryResults.setText("");
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent intent = new  Intent(SummaryActivity.this,HomeActivity.class);
        startActivity(intent);
        finish();
    }
    public void createDB() {
        try {
            PointsBD = this.openOrCreateDatabase("MyPoint", MODE_PRIVATE, null);
            PointsBD.execSQL("CREATE TABLE IF NOT EXISTS Points" +
                    "(id integer primary key,challenge VARCHAR,percentage VARCHAR,rate VARCHAR,correct_Questions VARCHAR,date VARCHAR);");
            File database = getApplicationContext().getDatabasePath("MyPoint.db");
            if (!database.exists()) {

            } else {
                Toast.makeText(this, "Database not exist", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            Log.e("Contact Error", "Error Creating Database");
        }
    }
    public void deleteContent() {

        PointsBD.execSQL("DELETE FROM Points;");
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
            Toast.makeText(SummaryActivity.this, "No Results to Show", Toast.LENGTH_SHORT).show();

        }
        return resultList;
    }
}
