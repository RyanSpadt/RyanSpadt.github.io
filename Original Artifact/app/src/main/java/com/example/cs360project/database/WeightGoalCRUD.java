package com.example.cs360project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cs360project.model.WeightGoal;
import com.example.cs360project.utils.SessionManager;

public class WeightGoalCRUD {

    private SQLiteDatabase db;

    // Open database via WeightDatabaseHelper
    public WeightGoalCRUD(Context context) {
        AppDatabaseHelper helper = new AppDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Singular goal weight per user
    public void setGoalWeight(WeightGoal weight_goal) {
        ContentValues values = new ContentValues();
        values.put("user_id", SessionManager.getUserID());
        values.put("goal", weight_goal.getWeightGoal());
        values.put("weight_when_set", weight_goal.getWeightWhenSet());
        values.put("type", weight_goal.getGoalType());
        db.insertWithOnConflict("weight_goal", null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    // Get the user's goal weight
    public String getGoalWeight(int user_id) {
        Cursor cursor = db.query("weight_goal", null, "user_id=?",
                new String[]{String.valueOf(user_id)}, null, null, null);

        if (cursor.moveToFirst()) {
            String goal_found = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            cursor.close();
            return goal_found;
        }

        cursor.close();
        return null;
    }


    // Retrieves the user's goal data from the db and returns a new WeightGoal object with that data
    public WeightGoal getGoal(int user_id) {
        Cursor cursor = db.query("weight_goal", null, "user_id=?",
                new String[]{String.valueOf(user_id)}, null, null, null);

        if (cursor.moveToFirst()) {
            String weight_when_set = cursor.getString(cursor.getColumnIndexOrThrow("weight_when_set"));
            String goal = cursor.getString(cursor.getColumnIndexOrThrow("goal"));
            String type = cursor.getString(cursor.getColumnIndexOrThrow("type"));
            cursor.close();
            return new WeightGoal(goal, user_id, weight_when_set, type);
        }

        cursor.close();
        return null;
    }
}
