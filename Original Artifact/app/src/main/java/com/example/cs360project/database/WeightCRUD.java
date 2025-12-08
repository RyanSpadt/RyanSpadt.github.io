package com.example.cs360project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.cs360project.model.User;
import com.example.cs360project.model.Weight;
import com.example.cs360project.utils.SessionManager;

import java.util.ArrayList;
import java.util.List;

public class WeightCRUD {

    private SQLiteDatabase db;


    // Open database via WeightDatabaseHelper
    public WeightCRUD(Context context) {
        AppDatabaseHelper helper = new AppDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }


    // Insert a new weight entry into the database
    public void createWeight(Weight weight) {
        ContentValues values = new ContentValues();
        values.put("weight", weight.getWeight());
        values.put("date", weight.getDate());
        values.put("user_id", SessionManager.getUserID());
        db.insert("weights", null, values);
    }


    // Deletes a weight entry in the database
    public void deleteWeight(int id) {
        db.delete("weights", "id=?", new String[]{String.valueOf(id)});
    }


    // Return a list of all weights for a specific user
    public List<Weight> getWeightsForUser(int user_id) {
        List<Weight> weight_entries = new ArrayList<>();
        Cursor cursor = db.query("weights", null, "user_id=?",
                new String[]{String.valueOf(user_id)}, null, null, "date DESC");

        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            String date = cursor.getString(cursor.getColumnIndexOrThrow("date"));
            String weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"));
            weight_entries.add(new Weight(id, date, weight, user_id));
        }

        cursor.close();
        return weight_entries;
    }


    // Returns the most recent weight entry for the user
    public String getLatestWeight(int user_id) {
        Cursor cursor = db.query("weights", null, "user_id=?",
                new String[]{String.valueOf(user_id)}, null, null, "date DESC");

        if (cursor.moveToFirst()) {
            String latest_weight = cursor.getString(cursor.getColumnIndexOrThrow("weight"));
            cursor.close();
            return latest_weight;
        }

        cursor.close();
        return null;
    }
}
