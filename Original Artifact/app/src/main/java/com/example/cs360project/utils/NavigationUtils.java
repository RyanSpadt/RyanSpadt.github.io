package com.example.cs360project.utils;

import android.app.Activity;
import android.content.Intent;

import com.example.cs360project.R;
import com.example.cs360project.activity.DatabaseActivity;
import com.example.cs360project.activity.SMSPermissionsActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class NavigationUtils {

    // Sets up bottom navigation menu on the layout
    public static void setupBottomNavigation(Activity activity, int selected_item_id) {
        BottomNavigationView nav_view = activity.findViewById(R.id.bottomNavigationView);
        nav_view.setSelectedItemId(selected_item_id);

        nav_view.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.miDatabase) {
                activity.startActivity(new Intent(activity, DatabaseActivity.class));
            } else if (item.getItemId() == R.id.miSMS) {
                activity.startActivity(new Intent(activity, SMSPermissionsActivity.class));
            }

            return true;
        });
    }
}
