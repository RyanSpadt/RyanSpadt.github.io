package com.example.cs360project.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.cs360project.model.User;

public class UserCRUD {
    private SQLiteDatabase db;

    // Open database via UserDatabaseHelper
    public UserCRUD(Context context) {
        AppDatabaseHelper helper = new AppDatabaseHelper(context);
        db = helper.getWritableDatabase();
    }

    // Insert a new user into the database
    public void createUser(User user) {
        ContentValues values = new ContentValues();
        values.put("username", user.getUsername());
        values.put("password", user.getPassword());
        db.insert("users", null, values);
        Log.d("UserInsert", "Inserting for user ID: " + user.getUserID());
    }

    // Checks if user with matching username/password exists in database
    public boolean validateUser(String username, String password) {
        Cursor cursor = db.query("users", null, "username=? AND password=?",
                new String[]{username, password}, null, null, null);
        boolean exists = cursor.moveToFirst();
        cursor.close();
        return exists;
    }

    public User getUser(String username) {
        Cursor cursor = db.query("users", null, "username=?",
                new String[]{username}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String found_username = cursor.getString(cursor.getColumnIndexOrThrow("username"));
            String found_password = cursor.getString(cursor.getColumnIndexOrThrow("password"));
            cursor.close();
            return new User(found_username, found_password);
        }

        if (cursor != null) {
            cursor.close();
        }

        return null;
    }


    // Returns user id of user by username
    public int getUserID(String username) {
        Cursor cursor = db.query("users", null, "username=?",
                new String[]{username}, null, null, null);

        if (cursor.moveToFirst()) {
            int found_user_id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
            cursor.close();
            return found_user_id;
        }

        cursor.close();
        return -1;
    }
}
