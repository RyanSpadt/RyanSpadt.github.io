package com.example.cs360project.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.cs360project.R;
import com.example.cs360project.database.UserCRUD;
import com.example.cs360project.model.User;
import com.example.cs360project.utils.SnackbarUtils;
import com.example.cs360project.validator.RegistrationValidator;

public class RegisterActivity extends AppCompatActivity {

    Button register_button;
    EditText password;
    EditText username;
    TextView alert;
    UserCRUD user_crud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // init views and components
        initializeViews();

        // Set text watcher for input fields
        setupInputStateWatcher();

        // Set click listener for register button
        setupRegisterButton();
    }


    // Initialize views and componenets
    private void initializeViews() {
        register_button = findViewById(R.id.completeRegisterButton);
        username = findViewById(R.id.registerUsername);
        password = findViewById(R.id.registerPassword);
        alert = findViewById(R.id.registerAlert);
        user_crud = new UserCRUD(this);

        // set register button to disabled unless input for username/password received
        register_button.setEnabled(false);
    }


    private void setupInputStateWatcher() {
        // Add text watcher to input fields
        TextWatcher register_input_watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username_input = username.getText().toString().trim();
                String password_input = password.getText().toString().trim();

                // Set login button enabled if both fields have a value
                register_button.setEnabled(!username_input.isEmpty() && !password_input.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Add text watcher to both input fields
        username.addTextChangedListener(register_input_watcher);
        password.addTextChangedListener(register_input_watcher);
    }


    // Setup click listener for Register button
    private void setupRegisterButton() {
        register_button.setOnClickListener(v -> {
            String username_input = username.getText().toString().trim();
            String password_input = password.getText().toString().trim();
            View anchor = findViewById(android.R.id.content);

            // Validate input from user
            if (!RegistrationValidator.validate(username_input, password_input, user_crud, anchor)) return;

            // Create new user
            User new_user = new User(username_input, password_input);
            user_crud.createUser(new_user);
            SnackbarUtils.showSnackbar(findViewById(android.R.id.content),"Registration successful", Color.GREEN, Color.BLACK, Color.YELLOW);

            // Send back to login page
            startActivity(new Intent(this, LoginActivity.class));
        });
    }
}
