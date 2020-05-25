package sg.edu.np.WhackAMole;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.logging.Level;

import sg.edu.np.WhackAMole.Level.LevelModel;

public class LevelDataHandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "level.db";
    public static final String TABLE_LEVELS = "levels";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_LEVEL = "level";
    public static final String COLUMN_HIGHSCORE = "highscore";

    public LevelDataHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version)
    {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(" CREATE TABLE " + TABLE_LEVELS + "(" +
                COLUMN_USERNAME + " TEXT NOT NULL , " +
                COLUMN_LEVEL + " TEXT NOT NULL , " +
                COLUMN_HIGHSCORE + " TEXT NOT NULL );"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_LEVELS);
        onCreate(db);
    }

    public ArrayList<LevelModel> findLevels(String username){
        ArrayList<LevelModel> levelList = new ArrayList<>();

        String query = "SELECT * FROM " + TABLE_LEVELS + " WHERE " + COLUMN_USERNAME + " = \"" + username + "\"";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()){
            LevelModel levelModel = new LevelModel();
            levelModel.setLevel(cursor.getString(0));
            levelModel.setLevel(cursor.getString(1));
            levelModel.setHighscore(cursor.getString(2));
            levelList.add(levelModel);
        }
        cursor.close();

        db.close();

        return levelList;
    }

    public void addLevel(LevelModel levelModel){
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, levelModel.getUsername());
        values.put(COLUMN_LEVEL, levelModel.getLevel());
        values.put(COLUMN_HIGHSCORE, levelModel.getHighscore());
        SQLiteDatabase db = this.getWritableDatabase();

        db.insert(TABLE_LEVELS,null,values);
        db.close();
    }

    public void updateLevel(String username, String level, String highscore){

        ContentValues values = new ContentValues();
        values.put(COLUMN_HIGHSCORE,highscore);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_LEVELS,values,"username = \"" + username + "\" AND level = \"" + level +"\"",null);
        db.close();
    }
}
