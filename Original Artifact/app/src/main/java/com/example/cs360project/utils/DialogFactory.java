package com.example.cs360project.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Handler;
import android.os.Looper;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.example.cs360project.R;
import com.example.cs360project.database.WeightCRUD;
import com.example.cs360project.database.WeightGoalCRUD;
import com.example.cs360project.model.Weight;
import com.example.cs360project.model.WeightGoal;
import com.example.cs360project.validator.GoalValidator;
import com.example.cs360project.validator.WeightValidator;

import java.util.Calendar;

public class DialogFactory {

    // Displays the dialog for adding weight entries
    public static void showAddWeightDialog(Activity activity, Runnable onWeightAdded) {
        View view = LayoutInflater.from(activity).inflate(R.layout.add_weight_row, null);
        EditText date_input = view.findViewById(R.id.date_input);
        EditText weight_input = view.findViewById(R.id.weight_input);

        prefillDate(date_input);
        setupDatePicker(activity, date_input);

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Add Weight")
                .setView(view)
                .setPositiveButton("Add", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button add_button = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            styleDialogButtons(dialog);
            add_button.setEnabled(false);

            WeightValidator validator = new WeightValidator(activity, weight_input, add_button);
            weight_input.addTextChangedListener(validator);

            add_button.setOnClickListener(v -> {
                String date = date_input.getText().toString().trim();
                String weight = weight_input.getText().toString().trim();
                Weight new_weight = new Weight(date, weight, SessionManager.getUserID());
                new WeightCRUD(activity).createWeight(new_weight);
                onWeightAdded.run();
                dialog.dismiss();
            });
        });

        dialog.show();
    }


    // Displays the dialog for setting new goals
    public static void showGoalDialog(Activity activity, WeightCRUD weight_crud, WeightGoalCRUD goal_crud, Runnable onGoalSet) {
        EditText goal_input = createEditText(activity, "Enter Goal Weight");
        EditText current_input = createEditText(activity, "Enter Current Weight");

        // If there is a latest weight entry, pull it from the db and default it into the input field
        String latest = weight_crud.getLatestWeight(SessionManager.getUserID());
        if (latest != null) current_input.setText(latest);

        Spinner goal_type_spinner = createGoalTypeSpinner(activity);

        LinearLayout layout = new LinearLayout(activity);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(32,16,32,16);
        layout.addView(goal_input);
        layout.addView(current_input);
        layout.addView(goal_type_spinner);

        AlertDialog dialog = new AlertDialog.Builder(activity)
                .setTitle("Set Weight Goal")
                .setView(layout)
                .setPositiveButton("Save", null)
                .setNegativeButton("Cancel", null)
                .create();

        dialog.setOnShowListener(d -> {
            Button save = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
            Button cancel = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
            styleDialogButtons(dialog);
            save.setEnabled(false);

            // Add Goal validator onto fields
            GoalValidator validator = new GoalValidator(activity, goal_input, current_input, goal_type_spinner, save);
            goal_input.addTextChangedListener(validator);
            current_input.addTextChangedListener(validator);
            goal_type_spinner.setOnItemSelectedListener(validator);

            save.setOnClickListener(v -> {
                String goal = goal_input.getText().toString().trim();
                String current = current_input.getText().toString().trim();
                String goal_type = goal_type_spinner.getSelectedItem().toString().trim();

                WeightGoal new_goal = new WeightGoal(goal, SessionManager.getUserID(), current, goal_type);
                goal_crud.setGoalWeight(new_goal);

                // Set delay so that the database action can take place before updating goal section
                new Handler(Looper.getMainLooper()).postDelayed(onGoalSet, 100);
                dialog.dismiss();
            });
        });

        dialog.show();
    }


    // Set the date input field to today's date by default
    private static void prefillDate(EditText date_input) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String today = DateUtils.getFormattedDate(year, month, day);
        date_input.setText(today);
    }


    // Sets up the date picker so that when the user clicks the field it appears
    private static void setupDatePicker(Activity activity, EditText date_input) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        date_input.setOnClickListener(view -> {
            DatePickerDialog date_picker_dialog = new DatePickerDialog(activity,
                    (date_picker, selected_year, selected_month, selected_day) -> {
                        // Format the selected date from date picker
                        String formatted_date = DateUtils.getFormattedDate(selected_year, selected_month, selected_day);
                        date_input.setText(formatted_date);
                    }, year, month, day);

            // Prevent future dates
            date_picker_dialog.getDatePicker().setMaxDate(System.currentTimeMillis());
            date_picker_dialog.show();
        });
    }


    // Creates a number input field
    private static EditText createEditText(Activity activity, String hint) {
        EditText input = new EditText(activity);
        input.setHint(hint);
        input.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);
        return input;
    }


    // Creates a choice selection field
    private static Spinner createGoalTypeSpinner(Activity activity) {
        Spinner spinner = new Spinner(activity);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(activity, android.R.layout.simple_spinner_dropdown_item, new String[]{"Lose", "Gain"});
        spinner.setAdapter(adapter);
        return spinner;
    }


    // Styles the positive/negative buttons of dialogs
    private static void styleDialogButtons(AlertDialog dialog) {
        Button positive = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        Button negative = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        if (positive != null && negative != null) {
            positive.setTextSize(18);
            negative.setTextSize(18);
            positive.setPadding(32, 24, 32, 24);
            negative.setPadding(32, 24, 32, 24);
        }
    }
}
