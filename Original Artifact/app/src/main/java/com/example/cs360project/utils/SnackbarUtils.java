package com.example.cs360project.utils;

import android.view.View;

import com.google.android.material.snackbar.Snackbar;

public class SnackbarUtils {

    public static void showSnackbar(View anchor_view, String message, int background_color, int text_color, int action_text_color) {
        Snackbar snackbar = Snackbar.make(anchor_view, message, Snackbar.LENGTH_INDEFINITE);

        // Set listener for dismiss button
        snackbar.setAction("Dismiss", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                snackbar.dismiss();
            }
        });

        // Set customisations onto snackbar
        snackbar.setBackgroundTint(background_color);
        snackbar.setTextColor(text_color);
        snackbar.setActionTextColor(action_text_color);
        snackbar.show();
    }
}
