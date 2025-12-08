package com.example.cs360project.utils;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.telephony.SmsManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import com.example.cs360project.R;
import com.example.cs360project.database.WeightCRUD;
import com.example.cs360project.database.WeightGoalCRUD;
import com.example.cs360project.model.WeightGoal;

public class GoalUtils {

    // Updates the goal section
    public static void updateGoalUI(Activity activity, WeightCRUD weight_crud, WeightGoalCRUD goal_crud) {
        TextView goal_text = activity.findViewById(R.id.goal_text);
        TextView diff_text = activity.findViewById(R.id.goal_diff_text);
        TextView progress_text = activity.findViewById(R.id.goal_progress_text);
        ProgressBar progress_bar = activity.findViewById(R.id.goal_progress_bar);

        WeightGoal goal = goal_crud.getGoal(SessionManager.getUserID());

        // if there is no goal, set default
        if (goal == null) {
            goal_text.setText("Goal: Not set");
            diff_text.setText("Difference: -- lbs");
            progress_text.setText("Progress: --%");
            progress_bar.setProgress(0);
            return;
        }

        String goal_weight = goal.getWeightGoal();
        String goal_type = goal.getGoalType();
        String start_weight = goal.getWeightWhenSet();
        String latest_weight = weight_crud.getLatestWeight(SessionManager.getUserID());

        // if one of the values is null set default
        if (goal_weight == null && latest_weight == null && goal_type == null && start_weight == null) {
            goal_text.setText("Goal: Not set");
            diff_text.setText("Difference: -- lbs");
            progress_text.setText("Progress: --%");
            progress_bar.setProgress(0);
            return;
        }

        goal_text.setText("Goal: " + goal_weight + " lbs");

        try {
            // Determine difference from latest and goal
            float goal_value = Float.parseFloat(goal_weight);
            float latest_value = Float.parseFloat(latest_weight);
            float start_value = Float.parseFloat(start_weight);

            // Calculations for progress on goal
            float total_change = Math.abs(goal_value - start_value);
            float current_change = 0;

            // Check direction of movement away from starting weight to ensure progress doesn't overshoot in wrong direction
            if (goal_type.equals("Lose")) {
                if (latest_value < start_value && latest_value >= goal_value) {
                    current_change = start_value - latest_value;
                }
            } else if (goal_type.equals("Gain")) {
                if (latest_value > start_value && latest_value <= goal_value) {
                    current_change = latest_value - start_value;
                }
            }

            float progress = total_change == 0 ? 0 : (current_change / total_change) * 100;
            // clamp between 0-100
            progress = Math.max(0, Math.min(progress, 100));

            // Determine if user has met their goal
            if ((int) progress == 100) sendGoalAchievedSMS(activity);

            // Determine difference and direction of that difference based on goal type
            float difference = Math.abs(latest_value - goal_value);
            String direction = goal_type.equals("Lose") ? (latest_value > goal_value ? "above" : "below") : (latest_value < goal_value ? "below" : "above");


            // Update difference text with difference and direction
            diff_text.setText("You are " + Math.abs(difference) + " lbs " + direction + " your goal");
            // Update progress bar and progress text
            progress_text.setText("Progress: " + String.format("%.1f", progress) + "%");
            // Smooth progress bar change
            progress_bar.setProgress(0);
            ObjectAnimator.ofInt(progress_bar, "progress", (int) progress)
                    .setDuration(500)
                    .start();

        } catch (Exception e) {
            diff_text.setText("Unable to calcualte");
            progress_text.setText("Progress: --%");
        }
    }


    // Sends a text to a user when they meet their goal
    private static void sendGoalAchievedSMS(Activity activity) {
        // Get preference on if they have sms permission enabled
        SharedPreferences prefs = activity.getSharedPreferences("AppPrefs", Activity.MODE_PRIVATE);
        boolean sms_enabled = prefs.getBoolean("sms_enabled", false);

        // if they have not granted permission for sms or do not have sms enabled do nothing
        if (!sms_enabled || ContextCompat.checkSelfPermission(activity, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            return;
        }

        // Set default phone number for testing
        String phone_number = "+16104281227";
        String message = "\uD83C\uDF89 Congratulations! You have achieved your weight goal! \uD83C\uDF89";

        try {
            SmsManager sms_manager = activity.getSystemService(SmsManager.class);
            sms_manager.sendTextMessage(phone_number, null, message, null, null);
            SnackbarUtils.showSnackbar(activity.findViewById(android.R.id.content), "SMS sent", Color.GREEN, Color.BLACK, Color.YELLOW);
        } catch (Exception e) {
            SnackbarUtils.showSnackbar(activity.findViewById(android.R.id.content), "Failed to send SMS", Color.RED, Color.WHITE, Color.YELLOW);
        }
    }
}
