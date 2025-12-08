package com.example.cs360project.utils;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.cs360project.model.Weight;


/*
 * Provides re-usable stateless methods for building table rows, headers, and dividers
 * A builder that knows how to construct the UI components of the table
 */
public class TableUtils {

    // Adds header row for weight db entries table
    public static void addHeaderRow(Activity activity, TableLayout table_layout) {
        TableRow header = new TableRow(activity);
        header.addView(createHeaderTextView(activity, "Date", 1f));
        header.addView(createHeaderTextView(activity, "Weight", 1f));
        header.addView(createHeaderTextView(activity, "Action", 1f));
        table_layout.addView(header);
    }


    // Used for adding a data row for a weight entry from db to the entries table
    public static void addDataRow(Activity activity, TableLayout table_layout, Weight weight, Runnable onDelete) {
        TableRow row = new TableRow(activity);

        row.addView(createCellTextView(activity, weight.getDate(), 1f));
        row.addView(createCellTextView(activity, weight.getWeight(), 1f));

        Button delete_button = new Button(activity);
        delete_button.setText("Delete");
        delete_button.setBackgroundColor(Color.parseColor("#D32F2F"));
        delete_button.setTextColor(Color.WHITE);
        delete_button.setPadding(32,24,32,24);
        delete_button.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        delete_button.setOnClickListener(v -> onDelete.run());

        row.addView(delete_button);
        table_layout.addView(row);
        addDivider(activity, table_layout);
    }


    // Creates a header text view component
    private static TextView createHeaderTextView(Activity activty, String text, float weight) {
        TextView text_view = new TextView(activty);
        text_view.setText(text);
        text_view.setTextColor(Color.BLACK);
        text_view.setTypeface(null, Typeface.BOLD);
        text_view.setTextSize(16);
        text_view.setPadding(32,24,32,24);
        text_view.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        return text_view;
    }


    // Creates a table data cell text view component
    private static TextView createCellTextView(Activity activity, String text, float weight) {
        TextView text_view = new TextView(activity);
        text_view.setText(text);
        text_view.setPadding(32,24,32,24);
        text_view.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, weight));
        return text_view;
    }


    // Adds divider between each table row
    private static void addDivider(Activity activity, TableLayout table_layout) {
        View divider = new View(activity);
        divider.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, 2));
        divider.setBackgroundColor(Color.parseColor("#CCCCCC"));
        table_layout.addView(divider);
    }
}
