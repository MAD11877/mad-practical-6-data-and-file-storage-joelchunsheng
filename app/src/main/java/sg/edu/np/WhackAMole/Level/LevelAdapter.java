package sg.edu.np.WhackAMole.Level;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import sg.edu.np.WhackAMole.Game.Main2Activity;
import sg.edu.np.WhackAMole.Game.MainActivity;
import sg.edu.np.WhackAMole.R;

public class LevelAdapter extends RecyclerView.Adapter<LevelHolder> {

    ArrayList<LevelModel> highScoreList;
    Context context;

    public LevelAdapter(ArrayList<LevelModel> highScoreList, Context context) {
        this.highScoreList = highScoreList;
        this.context = context;
    }

    @NonNull
    @Override
    public LevelHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.levelrow, viewGroup, false);
        final LevelHolder holder = new LevelHolder(v);

        holder.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Clicked position", String.valueOf(holder.getAdapterPosition()));

                if (holder.getAdapterPosition() < 5){
                    //basic level
                    Intent basicActivity = new Intent(context, MainActivity.class);

                    if (holder.getAdapterPosition() == 0){
                        basicActivity.putExtra("timer", 10000);
                    }
                    else if (holder.getAdapterPosition() == 1){
                        basicActivity.putExtra("timer", 9000);
                    }
                    else if (holder.getAdapterPosition() == 2){
                        basicActivity.putExtra("timer", 8000);
                    }
                    else if (holder.getAdapterPosition() == 3){
                        basicActivity.putExtra("timer", 7000);
                    }
                    else if (holder.getAdapterPosition() == 4){
                        basicActivity.putExtra("timer", 6000);
                    }

                    basicActivity.putExtra("highschore", holder.highscore.getText());
                    basicActivity.putExtra("level", holder.level.getText());


                    context.startActivity(basicActivity);
                }
                else{
                    // advance level
                    Intent advanceActivity = new Intent(context, Main2Activity.class);

                    if (holder.getAdapterPosition() == 5){
                        advanceActivity.putExtra("timer", 5000);
                    }
                    else if (holder.getAdapterPosition() == 6){
                        advanceActivity.putExtra("timer", 4000);
                    }
                    else if (holder.getAdapterPosition() == 7){
                        advanceActivity.putExtra("timer", 3000);
                    }
                    else if (holder.getAdapterPosition() == 8){
                        advanceActivity.putExtra("timer", 2000);
                    }
                    else if (holder.getAdapterPosition() == 9){
                        advanceActivity.putExtra("timer", 1000);
                    }

                    advanceActivity.putExtra("highschore", holder.highscore.getText());
                    advanceActivity.putExtra("level", holder.level.getText());

                    context.startActivity(advanceActivity);
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LevelHolder levelHolder, int i) {
        levelHolder.level.setText(highScoreList.get(i).getLevel());
        levelHolder.highscore.setText(highScoreList.get(i).getHighscore());
    }

    @Override
    public int getItemCount() {
        return highScoreList.size();
    }
}
