package com.example.cs360project.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Switch;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cs360project.R;
import com.example.cs360project.utils.NavigationUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SMSPermissionsActivity extends AppCompatActivity {

    Switch sms_switch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms_permission);

        // initialize components and views
        initializeViews();

        // setup sms permission switch component
        setupSMSPermissionSwitch();

        // setup bottom navigation
        NavigationUtils.setupBottomNavigation(this, R.id.miSMS);
    }


    // init views and components
    private void initializeViews() {
        sms_switch = findViewById(R.id.sms_perm_status);
    }


    // Setup permission switch component
    private void setupSMSPermissionSwitch() {
        // Get current user preferences and set switch to current setting
        SharedPreferences prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE);
        boolean sms_enabled = prefs.getBoolean("sms_enabled", false);
        sms_switch.setChecked(sms_enabled);

        // Listener for when switch changes set new preference
        sms_switch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            prefs.edit().putBoolean("sms_enabled", isChecked).apply();

            if (isChecked && !hasSMSPermission()) {
                requestSMSPermission();
            }
        });
    }


    // Checks if user already has granted permission in the past
    private boolean hasSMSPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }


    // Requests SMS permission
    private void requestSMSPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.SEND_SMS}, 100);
    }
}
