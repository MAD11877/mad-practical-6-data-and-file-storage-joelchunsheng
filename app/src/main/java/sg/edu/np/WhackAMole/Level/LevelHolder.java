package sg.edu.np.WhackAMole.Level;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import sg.edu.np.WhackAMole.R;

public class LevelHolder extends RecyclerView.ViewHolder {
    TextView level, highscore;
    LinearLayout item;

    public LevelHolder(@NonNull View itemView) {
        super(itemView);
        level = itemView.findViewById(R.id.levelNumberText);
        highscore = itemView.findViewById(R.id.levelScoreText);
        item = itemView.findViewById(R.id.levelLayout);
    }
}
