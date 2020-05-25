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

import java.util.Random;

import sg.edu.np.WhackAMole.Level.Levels;
import sg.edu.np.WhackAMole.LevelDataHandler;
import sg.edu.np.WhackAMole.R;

public class MainActivity extends AppCompatActivity {
    public  Button ButtonLeft, ButtonMiddle, ButtonRight;
    public TextView scoreTxtView;
    public Button backBtn;
    CountDownTimer myCountDown;
    private static final String TAG = "Whack-A-Mole 1.0";
    public int previousScore, timer;
    String username, level;

    String selectedValue;
    int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v(TAG, "Finished Pre-Initialisation!");

        SharedPreferences sharedPreferences = getSharedPreferences("sg.edu.np.WhackAMole.User", Context.MODE_PRIVATE);
        username = sharedPreferences.getString("username", "");

        ButtonLeft = (Button) findViewById(R.id.button1);
        ButtonMiddle = (Button) findViewById(R.id.button2);
        ButtonRight = (Button) findViewById(R.id.button3);
        scoreTxtView = (TextView) findViewById(R.id.scoreTxtView);
        backBtn = (Button) findViewById(R.id.bBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent levelActivity = new Intent(MainActivity.this, Levels.class);
                startActivity(levelActivity);
                finish();
            }
        });

        scoreTxtView.setText(String.valueOf(score));

        ButtonLeft.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "ButtonLeft click");
                //Validate btn
                doCheck(ButtonLeft);
            }
        });

        ButtonMiddle.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "ButtonMiddle click");
                //Validate btn
                doCheck(ButtonMiddle);
            }
        });

        ButtonRight.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Log.v(TAG, "ButtonRight click");
                //Validate btn
                doCheck(ButtonRight);
            }
        });

    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");

        Intent receivingEnd = getIntent();
        timer = receivingEnd.getIntExtra("timer", 1000);
        previousScore = receivingEnd.getIntExtra("highscore", 0);
        level = receivingEnd.getStringExtra("level");

        placeMoleTimer(timer);
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }

    private void doCheck(Button checkButton) {
        selectedValue = checkButton.getText().toString();

        if(selectedValue == "*"){
            Log.v(TAG, "Hit, score added!");
            score += 1;
            scoreTxtView.setText(String.valueOf(score));
            setNewMole();

            if (score > previousScore){
                //update score
                LevelDataHandler levelDataHandler = new LevelDataHandler(this, null, null, 1);
                levelDataHandler.updateLevel(username, level, String.valueOf(score));
            }
        }
        // If Incorrect button pressed
        else{
            if (score >0){
                score -= 1;
                scoreTxtView.setText(String.valueOf(score));
            }
            Log.v(TAG, "Missed, score deducted!");
        }

    }

    private void setNewMole() {
        Random ran = new Random();
        int randomLocation = ran.nextInt(3)+1;

        if (randomLocation == 1){
            ButtonLeft.setText("*");
            ButtonMiddle.setText("O");
            ButtonRight.setText("O");
        }
        else if (randomLocation == 2){
            ButtonLeft.setText("O");
            ButtonMiddle.setText("*");
            ButtonRight.setText("O");
        }
        else{
            ButtonLeft.setText("O");
            ButtonMiddle.setText("O");
            ButtonRight.setText("*");
        }
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

}