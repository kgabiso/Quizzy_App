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

public class ProgrammingActivity extends AppCompatActivity {

    RadioGroup group ;
    RadioButton option1,option2,option3;
    FloatingActionButton fab;
    TextView Quest;
    int count = 0;
    Animation animUp;
    Animation animDown;
    Animation animFade,slideLeft;
    //****Questions ,Answer,options array******/
    static  String questions[] ={"Which of the following special symbol allowed in a variable name?",
            "If the two strings are identical, then strcmp() function returns",
            "What does the following declaration mean? int (*ptr)[10];",
            "In C, if you pass an array as an argument to a function, what actually gets passed?",
            "The keyword used to transfer control from a function back to the calling function is",
            "What is the size of float variable?",
            "What is the default value of long variable?",
            "Which of the following is false about String?",
            "What is the size of long variable?",
            "What is the default value of double variable?"};
    String answer[] = {"_ (underscore)",
            "0",
            "ptr is a pointer to an array of 10 integers",
            "Base address of the array",
            "return",
            "32 bit",
            "0L",
            "String is a primary data type.",
            "64 bit",
            " 0.0d"};
    String opt[] = {"* (asterisk)","_ (underscore)","- (hyphen)",
            "0","-1","1",
            "ptr is an array of 10 integers","ptr is an pointer to array","ptr is a pointer to an array of 10 integers",
            "Value of elements in array","Base address of the array","First element of the array",
            "return","goto","switch",
            "8 bit","16 bit","32 bit",
            "0.0","0L","0",
            "String is a primary data type.","String can be created using new operator.","String is immutable.",
            "64 bit","8 bit","32 bit",
            " 0.0f"," 0.0d","0"};

    public static int pos,correct = 0;
    int num = 1;
    public static String activity;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programming);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
        //===============================================================
        correct=0;
        pos = 0;


        Quest.setText(num+": "+questions[pos]);
        option1.setText(opt[pos]);
        option2.setText(opt[pos + 1]);
        option3.setText(opt[pos + 2]);


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                int id = group.getCheckedRadioButtonId();
                if (id > 0)
                {

                    slideLeft = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
                    animUp = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_up_rev);
                    animDown = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.move_down_rev);
                    animFade = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.grow_fade_in_rev);

                    Quest.startAnimation(slideLeft);
                    option1.startAnimation(animUp);
                    option2.startAnimation(animFade);
                    option3.startAnimation(animDown);

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
                                activity = "Programming Challenge";
                                Intent intent = new Intent(ProgrammingActivity.this, ResultActivity.class);
                                intent.putExtra("correct", String.valueOf(correct));//PUTTING EXTRAS TO INTENT
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

        count++;
        if(count > 1)
        {
            Intent intent = new Intent(ProgrammingActivity.this,HomeActivity.class);
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
                    count= 0;
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
