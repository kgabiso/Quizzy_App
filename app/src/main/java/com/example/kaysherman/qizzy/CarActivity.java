package com.example.kaysherman.qizzy;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CarActivity extends AppCompatActivity {

    RadioGroup group ;
    RadioButton option1,option2,option3;
    TextView Quest;
    int Exitcount = 0;
    Animation animUp;
    Animation animDown;
    Animation animFade,slideLeft;
    FloatingActionButton fab;

    static String questions[] ={"What year was the first speeding ticket issued in the U.S.?",
            "When did it become common to own a car?",
            "What Was The First Car?",
            "What Was The First Muscle car?",
            "Who Designed The Volkswagen Beetle?",
            "What Does the \"SS\" on the Chevrolet Impala Stand For?",
            "Did The First Corvette Have A V8?",
            "What was the first American car to feature a turbocharged engine?",
            "What option pack was not available on the 1967 Camaro, Chevrolet’s answer to the Mustang?",
            "What year did Dodge stop building the Charger?",
            "What nickname was given to Chrysler 426 Hemi?",
            "Which automobile was the infamous John Dillinger's favourite car to steal?"};

    String answer[] = {"1902",
            "1920",
            "Benz Patent-Motorwagen",
            "1964 Pontiac GTO",
            "Adolf Hitler",
            "Super Sport",
            "No",
            "1962 Oldsmobile Jetfire",
            "R/T",
            "1987",
            "Elephant",
            "Ford"};

    String opt[] = {"1902","1920","1968",
            "1902","1920","1968",
            "Benz Patent-Motorwagen","Ford-Motorwagen","Corvette",
            "1964 Ford Mustang"," 1964 Porsche 911","1964 Pontiac GTO",
            "Adolf Hitler"," Ferdinand \"Butzi\" Porsche"," Ferdinand \"Ferry\" Porsche.",
            "Steam Ship","Stainless steel","Super Sport",
            "Yes","No","I don't know",
            "1962 Oldsmobile Jetfire","1970 Corvette","2005 Mustang Saleen S7",
            "R/T","RS","SS",
            "1994","1974","1987",
            "Turbo Fire","Wildcat","Elephant",
            "Chevrolet","Ford","Buick"};

    public static int pos,correct = 0;
    int num = 1;
   public static String activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /** Animations**/
        slideLeft = AnimationUtils.loadAnimation(this,R.anim.slide_in_left);
        animUp = AnimationUtils.loadAnimation(this,R.anim.move_up);
        animDown = AnimationUtils.loadAnimation(this,R.anim.move_down);
        animFade = AnimationUtils.loadAnimation(this,R.anim.grow_fade_in);
        toolbar.setAnimation(animUp);

        group = (RadioGroup)findViewById(R.id.gr) ;
        Quest = (TextView)findViewById(R.id.Question);
        Quest.setAnimation(slideLeft);
        option1 = (RadioButton)findViewById(R.id.option1);
        option1.setAnimation(animUp);
        option2 = (RadioButton)findViewById(R.id.option2);
        option2.setAnimation(animFade);
        option3 = (RadioButton)findViewById(R.id.option3);
        option3.setAnimation(animDown);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setAnimation(animDown);
        /**
         *
         *
         */
        correct=0;
        pos = 0;

        Quest.setText(num +": "+questions[pos]);
        option1.setText(opt[pos]);
        option2.setText(opt[pos + 1]);
        option3.setText(opt[pos + 2]);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int id = group.getCheckedRadioButtonId();
                if (id > 0)
                {

                    /****** Animation*****/
                    slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
                    animUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up_rev);
                    animDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down_rev);
                    animFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in_rev);

                    Quest.startAnimation(slideLeft);
                    option1.startAnimation(animUp);
                    option2.startAnimation(animFade);
                    option3.startAnimation(animDown);
                    /************************************************************************/

                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {

                            int nextPos = 0;
                            nextPos = nextPos + 3;
                            RadioButton selectedAns = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                            String selected = selectedAns.getText().toString();
                            if (selected == answer[pos]) {
                                correct++;
                            }
                            pos++;
                            num++;

                            if (pos < questions.length) {

                                Quest.setText(num+": "+questions[pos]);
                                option1.setText(opt[pos * nextPos]);
                                option2.setText(opt[pos * nextPos + 1]);
                                option3.setText(opt[pos * nextPos + 2]);

                            } else {
                                pos = 0;
                                activity = "Cars Challenge";
                                Intent intent = new Intent(CarActivity.this, ResultActivity.class);
                                intent.putExtra("correct", String.valueOf(correct));
                                intent.putExtra("questions", String.valueOf(questions.length));
                                intent.putExtra("Activity",activity);
                                startActivity(intent);
                                finish();
                            }
                            slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
                            animUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);
                            animDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
                            animFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in);

                            Quest.startAnimation(slideLeft);
                            option1.startAnimation(animUp);
                            option2.startAnimation(animFade);
                            option3.startAnimation(animDown);
                            group.clearCheck();
                        }
                    },1000);

                }
                else
                {
                    Snackbar.make(view, "Choose one of the Options", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {

        Exitcount++;
        if(Exitcount > 1)
        {
            Intent intent = new Intent(CarActivity.this,HomeActivity.class);
            startActivity(intent);
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"Press back again to quit", Toast.LENGTH_SHORT).show();
            slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_left);
            animUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up_rev);
            animDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down_rev);
            animFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in_rev);


            Quest.startAnimation(slideLeft);
            option1.startAnimation(animUp);
            option2.startAnimation(animFade);
            option3.startAnimation(animDown);
            fab.startAnimation(animDown);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Exitcount = 0;
                    slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_left);
                    animUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up);
                    animDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down);
                    animFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in);


                    Quest.startAnimation(slideLeft);
                    option1.setAnimation(animUp);
                    option2.setAnimation(animFade);
                    option3.startAnimation(animDown);
                    fab.setAnimation(animDown);
                }
            },2000);
        }
    }

}
