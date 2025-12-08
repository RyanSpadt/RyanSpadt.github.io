package com.example.cs360project.manager;

import android.app.Activity;
import android.widget.Button;
import android.widget.TableLayout;

import com.example.cs360project.R;
import com.example.cs360project.database.WeightCRUD;
import com.example.cs360project.model.Weight;
import com.example.cs360project.utils.DialogFactory;
import com.example.cs360project.utils.SessionManager;
import com.example.cs360project.utils.TableUtils;

import java.util.List;

/*
 * Handles UI rendering and CRUD operations for weight entries in table
 * Coordinates table-related logic
 * loading data, handling user actions, and refreshing UI
 * The orchestrator that knows when and why to update the table
 */
public class TableManager {

    private final Activity activity;
    private final TableLayout table_layout;
    private final WeightCRUD weight_crud;
    private final WeightUpdateListener update_listener;


    public TableManager(Activity activity, WeightUpdateListener update_listener) {
        this.activity = activity;
        this.table_layout = activity.findViewById(R.id.data_table);
        this.weight_crud = new WeightCRUD(activity);
        this.update_listener = update_listener;
        setupAddWeightButton();
    }


    // Loads the weight entries for the user from the db
    public void loadWeightEntries() {
        table_layout.removeAllViews();
        List<Weight> entries = weight_crud.getWeightsForUser(SessionManager.getUserID());
        TableUtils.addHeaderRow(activity, table_layout);

        for (Weight weight : entries) {
            TableUtils.addDataRow(activity, table_layout, weight, () -> {
                weight_crud.deleteWeight(weight.getID());
                loadWeightEntries();
                update_listener.onWeightUpdated(); // Refresh goal section after deletion
            });
        }
    }


    // Setup the add weight button listener
    private void setupAddWeightButton() {
        Button add_weight_button = activity.findViewById(R.id.addWeightButton);
        add_weight_button.setOnClickListener(v ->
                DialogFactory.showAddWeightDialog(activity, () -> {
                    loadWeightEntries();           // Refresh table
                    update_listener.onWeightUpdated(); // Notify activity to refresh goal section
                })
        );
    }


    public WeightCRUD getWeightCRUD() {
        return weight_crud;
    }
}
