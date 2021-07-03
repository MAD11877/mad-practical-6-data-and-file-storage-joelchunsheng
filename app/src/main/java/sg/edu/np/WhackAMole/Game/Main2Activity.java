package sg.edu.np.WhackAMole.Game;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

import sg.edu.np.WhackAMole.Level.Levels;
import sg.edu.np.WhackAMole.LevelDataHandler;
import sg.edu.np.WhackAMole.R;

public class Main2Activity extends AppCompatActivity {
    int advancedScore = 0;
    CountDownTimer myCountDown;
    TextView scoreTxtView;
    String selectedValue;
    Button backBtn;
    public int previousScore, timer;
    String username, level;

    private static final String TAG = "Whack-A-Mole 2.0";

    private void readyTimer(final int timer){
        myCountDown = new CountDownTimer(10000, 1000){
            public void onTick(long millisUntilFinished){
                int seconds = (int) millisUntilFinished/1000;
                Log.v(TAG, String.format("Ready CountDown! %d", seconds));
                Toast.makeText(getApplicationContext(), String.format("Get ready in %d seconds", seconds) ,Toast.LENGTH_SHORT).show();
            }

            public void onFinish(){
                Toast.makeText(getApplicationContext(), "GO!" ,Toast.LENGTH_SHORT).show();
                myCountDown.cancel();
                placeMoleTimer(timer);
            }
        };
        myCountDown.start();

    }

    private void placeMoleTimer(final int timer){
        myCountDown = new CountDownTimer(10000, timer){
            public void onTick(long millisUntilFinished){
                int seconds = (int) millisUntilFinished/1000;
                setNewMole();
                Log.v(TAG, "New Mole Location");
            }

            public void onFinish(){
                placeMoleTimer(timer);
            }
        };
        myCountDown.start();

    }

    private static final int[] BUTTON_IDS = {
            R.id.button1,
            R.id.button2,
            R.id.button3,
            R.id.button4,
            R.id.button5,
            R.id.button6,
            R.id.button7,
            R.id.button8,
            R.id.button9,
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        SharedPreferences sharedPreferences = getSharedPreferences("sg.edu.np.WhackAMole.User", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        backBtn = (Button) findViewById(R.id.aBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent levelActivity = new Intent(Main2Activity.this, Levels.class);
                startActivity(levelActivity);
                finish();
            }
        });


        scoreTxtView = (TextView) findViewById(R.id.advanceTextView);
        scoreTxtView.setText(String.valueOf(advancedScore));

        for(final int id : BUTTON_IDS){

            final Button button = (Button) findViewById(id);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    doCheck(button);
                    setNewMole();
                }
            });
        }


    }

    @Override
    protected void onStart(){
        super.onStart();
        Intent receivingEnd = getIntent();
        timer = receivingEnd.getIntExtra("timer", 1);
        previousScore = receivingEnd.getIntExtra("highscore", 0);
        level = receivingEnd.getStringExtra("level");
        readyTimer(timer);
    }

    private void doCheck(Button checkButton)
    {
        selectedValue = checkButton.getText().toString();

        if(selectedValue == "*"){
            Log.v(TAG, "Hit, score added!");
            advancedScore += 1;
            scoreTxtView.setText(String.valueOf(advancedScore));

            if (advancedScore > previousScore){
                //update score
                Log.v(TAG, String.valueOf(advancedScore));
                Log.v(TAG, String.valueOf(username));
                Log.v(TAG, String.valueOf(level));
                LevelDataHandler levelDataHandler = new LevelDataHandler(this, null, null, 1);
                levelDataHandler.updateLevel(username, level, String.valueOf(advancedScore));
            }

        }
        // If Incorrect button pressed
        else{
            if (advancedScore >0){
                advancedScore -= 1;
                scoreTxtView.setText(String.valueOf(advancedScore));
            }
            Log.v(TAG, "Missed, score deducted!");
        }
    }

    public void setNewMole()
    {
        Random ran = new Random();
        int randomLocation1 = ran.nextInt(9);
        int randomLocation2 = ran.nextInt(9);

        while (randomLocation1 == randomLocation2){
            randomLocation2 = ran.nextInt(9);
        }

        int index = 1;
        for (final int id : BUTTON_IDS){
            Button button = (Button) findViewById(id);
            if (randomLocation1+1 == index || randomLocation2+1 == index){
                button.setText("*");
            }
            else{
                button.setText("0");
            }
            index++;
        }
        index=1;
    }


}

