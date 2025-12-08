package com.example.cs360project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AppDatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "weight_app.db";
    private static final int DATABASE_VERSION = 7;


    // init SQLiteOpenHelper with my database context
    public AppDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    // Define table scheme and create initial tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE ="CREATE TABLE users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "username TEXT UNIQUE, " +
                "password TEXT)";

        db.execSQL(CREATE_USERS_TABLE);

        String CREATE_WEIGHTS_TABLE ="CREATE TABLE weights (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "weight TEXT, " +
                "date DATE, " +
                "user_id INTEGER, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)";

        db.execSQL(CREATE_WEIGHTS_TABLE);

        String CREATE_GOAL_TABLE = "CREATE TABLE weight_goal(" +
                "user_id INTEGER PRIMARY KEY, " +
                "goal TEXT, " +
                "weight_when_set TEXT, " +
                "type TEXT, " +
                "FOREIGN KEY(user_id) REFERENCES users(id) ON DELETE CASCADE)";

        db.execSQL(CREATE_GOAL_TABLE);
    }


    // Handle schema change between versions
    @Override
    public void onUpgrade(SQLiteDatabase db, int old_version, int new_version) {
        db.execSQL("DROP TABLE IF EXISTS users");
        db.execSQL("DROP TABLE IF EXISTS weights");
        db.execSQL("DROP TABLE IF EXISTS weight_goal");
        onCreate(db);
    }
}
