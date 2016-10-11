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

public class SocialActivity extends AppCompatActivity {
    RadioGroup group ;
    RadioButton option1,option2,option3;
    TextView Quest;
    int  Exitcount = 0;
    Animation animUp;
    Animation animDown;
    Animation animFade,slideLeft;
    FloatingActionButton fab;

    static String questions[] ={"Which demographic was Facebook originally open to?",
            "What was the largest social network prior to Facebook?",
            "Which social network does not have ‘followers’?",
            "Which social network is ‘The Social Network’ movie about?",
            "On what platform was the hashtag born?",
            "Which social media platform does not allow you to send direct messages?",
            "What social media platform has the most active users?",
            "What is the character limit for Twitter posts?",
            "Facebook, MySpace and Bebo are all examples of what type of service?",
            "Twitter is an example of what type of service?",
            "In relation to mobile digital devices, what does the term 'app' mean?",
            "What is the hashtag (#) used for when posting Tweets to Twitter?",
            "What do Google Drive, EtherPad and Zoho have in common?"};
    String answer[] = {"College Students",
            "MySpace",
            "Google+",
            "Facebook",
            "Twitter",
            "None of the above",
            "Facebook",
            "140",
            "Social networking",
            "Microblogging",
            "An application designed to run on a mobile device.",
            "It is used to mark keywords. This helps when searching for Tweets on particular topics.",
            "They all enable several users to collaborate in creating and editing documents."};
    String opt[] = {"Everyone","College Students","High school students",
            "MySpace","Friendster","Google+",
            "Facebook","SoundCloud","Google+",
            "MySpace","Twitter","Facebook",
            "Mxit","Whatsapp","Twitter",
            "Facebook","Instagram","None of the above",
            "Google+","MySpace","Facebook",
            "300","140","Unlimited",
            "E-learning","Web portal","Social networking",
            "Microblogging","Blogging"," Programming",
            "An abbreviation of 'Apple', the technology corporation.","An application designed to run on a mobile device.","An application designed to run on the Apple iPhone.",
            "It is used to indicate the username of the account from which the Tweet is being made.","It is used to mark keywords. This helps when searching for Tweets on particular topics."," It is used for replies or mentions, to indicate the name of another user.",
            "They all enable several users to collaborate in creating and editing documents.","They are all video sharing services.","They are all owned by Rupert Murdoch’s News Corporation."};

    public static int pos,correct = 0;
    int num = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /**********Animaton*******/
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
        /****************************************************/
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
                                Intent intent = new Intent(SocialActivity.this, ResultActivity.class);
                                intent.putExtra("correct", String.valueOf(correct));//PUTTING EXTRAS TO INTENT
                                intent.putExtra("questions", String.valueOf(questions.length));
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
        if( Exitcount > 1)
        {
            Intent intent = new Intent(SocialActivity.this,HomeActivity.class);
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
