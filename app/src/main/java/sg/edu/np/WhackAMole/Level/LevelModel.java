package sg.edu.np.WhackAMole.Level;

public class LevelModel {
    private String username;
    private String level;
    private String highscore;

    public LevelModel() {

    }

    public LevelModel(String username, String level, String highscore) {
        this.username = username;
        this.level = level;
        this.highscore = highscore;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHighscore() {
        return highscore;
    }

    public void setHighscore(String highscore) {
        this.highscore = highscore;
    }
}
