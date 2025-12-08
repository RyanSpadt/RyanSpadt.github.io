package com.example.cs360project.validator;

import android.graphics.Color;
import android.view.View;

import com.example.cs360project.database.UserCRUD;
import com.example.cs360project.utils.SnackbarUtils;

public class RegistrationValidator {

    // Validate the registration input values
    public static boolean validate(String username_input, String password_input, UserCRUD user_crud, View anchor) {
        // if username or password is empty display error
        if (username_input.isEmpty() || password_input.isEmpty()) {
            showError(anchor,"Please enter both a username and password");
            return false;
        }

        // Validate username length and enforce standard
        if (username_input.length() < 3) {
            showError(anchor,"Username must be a minimum of 3 characters");
            return false;
        }

        // Validate password length and enforce standard
        if (password_input.length() < 8) {
            showError(anchor,"Password must be a minimum of 8 characters");
            return false;
        }

        // Check is someone with the input username already exists
        if (user_crud.getUser(username_input) != null) {
            showError(anchor,"Someone with this username already exists");
            return false;
        }

        return true;
    }


    // Display error to user
    private static void showError(View anchor, String message) {
        SnackbarUtils.showSnackbar(anchor, message, Color.RED, Color.WHITE, Color.YELLOW);
    }
}
