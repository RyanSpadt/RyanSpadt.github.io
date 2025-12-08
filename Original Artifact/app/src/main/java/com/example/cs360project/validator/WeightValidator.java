package com.example.cs360project.validator;

import android.app.Activity;
import android.graphics.Color;
import android.text.Editable;
import android.view.View;
import android.text.TextWatcher;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;

import com.example.cs360project.utils.SnackbarUtils;

public class WeightValidator implements TextWatcher {

    private final Activity activity;
    private final EditText weight_input;
    private final Button add_button;



    public WeightValidator(Activity activity, EditText weight_input, Button add_button) {
        this.activity = activity;
        this.weight_input = weight_input;
        this.add_button = add_button;
    }


    private void validate() {
        String weight = weight_input.getText().toString().trim();

        // Set button enabled false by default
        add_button.setEnabled(false);

        // Don't enable positive button if weight input is empty
        if (weight.isEmpty()) {
            showError("Goal and current weight cannot be empty");
            return;
        }

        try {
            float weight_value = Float.parseFloat(weight);

            // Ensure the weight value input is a positive number
            if (weight_value <= 0) {
                showError("Weight must be a positive number");
                return;
            }

            add_button.setEnabled(true);
        } catch (Exception e) {
            showError("Invalid input");
        }
    }


    // Display error to user
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

}
