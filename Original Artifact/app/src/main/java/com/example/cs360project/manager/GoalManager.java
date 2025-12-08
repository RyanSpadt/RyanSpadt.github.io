package com.example.cs360project.manager;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.widget.Button;

import com.example.cs360project.R;
import com.example.cs360project.database.WeightGoalCRUD;
import com.example.cs360project.utils.DialogFactory;
import com.example.cs360project.utils.GoalUtils;

public class GoalManager {

    private final Activity activity;
    private final TableManager table_manager;
    private final WeightGoalCRUD goal_crud;


    public GoalManager(Activity activity, TableManager table_manager) {
        this.activity = activity;
        this.table_manager = table_manager;
        this.goal_crud = new WeightGoalCRUD(activity);
        setupGoalButton();
    }


    // Setup set new goal button listener
    private void setupGoalButton() {
        Button goal_button = activity.findViewById(R.id.set_goal_button);
        goal_button.setOnClickListener(v -> DialogFactory.showGoalDialog(activity, table_manager.getWeightCRUD(), goal_crud, this::setupGoalSection));
    }


    // Called for updating goal section data, run in main thread if called in background
    public void setupGoalSection() {
        new Handler(Looper.getMainLooper()).post(() -> {
            GoalUtils.updateGoalUI(activity, table_manager.getWeightCRUD(), goal_crud);
        });
    }
}
