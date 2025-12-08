package com.example.cs360project.utils;

import android.util.Log;

public class SessionManager {

    private static int user_id;

    // Sets the current session's logged in user's user_id
    public static void setUserID(int id) {
        user_id = id;
    }


    // Retrieves the session's logged in user's user_id
    public static int getUserID() {
        Log.d("WeightInsert", "Inserting for user ID: " + user_id);
        return user_id;
    }
}
