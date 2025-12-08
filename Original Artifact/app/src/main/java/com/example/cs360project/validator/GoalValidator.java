package com.example.cs360project.validator;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.cs360project.utils.SnackbarUtils;

public class GoalValidator implements TextWatcher, AdapterView.OnItemSelectedListener {
    private final Activity activity;
    private final EditText goal_input;
    private final EditText current_input;
    private final Spinner goal_type_spinner;
    private final Button save_button;


    public GoalValidator(Activity activity, EditText goal_input, EditText current_input, Spinner goal_type_spinner, Button save_button) {
        this.activity = activity;
        this.goal_input = goal_input;
        this.current_input = current_input;
        this.goal_type_spinner = goal_type_spinner;
        this.save_button = save_button;
    }


    private void validate() {
        String goal = goal_input.getText().toString().trim();
        String current = current_input.getText().toString().trim();
        String goal_type = goal_type_spinner.getSelectedItem().toString().trim();

        // Set button enabled false by default
        save_button.setEnabled(false);

        // Do not enable positive button if current or goal is empty
        if (goal.isEmpty() || current.isEmpty()) {
            showError("Goal and current weight cannot be empty");
            return;
        }

        try {
            float goal_value = Float.parseFloat(goal);
            float current_value = Float.parseFloat(current);

            // ENsure that the current value is greater than goal on lose goals
            if (goal_type.equals("Lose") && current_value <= goal_value) {
                showError("Goal must be lower than current weight for 'Lose' goal");
                return;
            }

            // Ensure that the current value is less than goal on gain goals
            if (goal_type.equals("Gain") && current_value >= goal_value) {
                showError("Goal must be higher than current weight for 'Gain' goal");
                return;
            }

            save_button.setEnabled(true);
        } catch (Exception e) {
            showError("Invalid input");
        }
    }


    // Show error message to user
    private void showError(String message) {
        SnackbarUtils.showSnackbar(activity.findViewById(android.R.id.content), message, Color.RED, Color.WHITE, Color.YELLOW);
    }


    // After the editable's input is changed perform validation
    @Override
    public void afterTextChanged(Editable s) {
        validate();
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}


    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}


    // After selection is made perform validation
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        validate();
    }


    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}
