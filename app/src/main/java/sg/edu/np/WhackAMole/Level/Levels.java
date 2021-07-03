package sg.edu.np.WhackAMole.Level;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import sg.edu.np.WhackAMole.LevelDataHandler;
import sg.edu.np.WhackAMole.R;
import sg.edu.np.WhackAMole.User.Login;

public class Levels extends AppCompatActivity {

    ArrayList<LevelModel> highScoreList;
    Button signOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_levels);

        signOut = (Button) findViewById(R.id.logOutButton);

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent(Levels.this, Login.class);
                startActivity(loginActivity);
            }
        });


        highScoreList = new ArrayList<>();
        findLevels();

        RecyclerView recyclerView = findViewById(R.id.levelsRecycler);
        final LevelAdapter mAdapter = new LevelAdapter(highScoreList, this);

        LinearLayoutManager mLayoutManager =  new LinearLayoutManager(this);

        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);

    }

    public void findLevels(){
        SharedPreferences sharedPreferences = getSharedPreferences("sg.edu.np.WhackAMole.User", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "");

        LevelDataHandler levelDataHandler = new LevelDataHandler(this, null, null, 1);

        for (LevelModel levelModel: levelDataHandler.findLevels(username)){
            highScoreList.add(levelModel);
            highScoreList.size();
        }

    }
}
