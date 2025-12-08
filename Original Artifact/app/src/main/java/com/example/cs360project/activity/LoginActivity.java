package com.example.cs360project.activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
import com.example.cs360project.utils.SessionManager;
import com.example.cs360project.utils.SnackbarUtils;

public class LoginActivity extends AppCompatActivity {

    Button login_button;
    Button register_button;
    EditText username;
    EditText password;
    UserCRUD user_crud;
    TextView alert;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // init the views and components
        initializeViews();

        // setup watcher for login button enable/disable
        setupInputStateWatcher();

        // Set click listener for login button
        setupLoginButton();

        // Set click listener for register button
        setupRegisterButton();
    }


    // Initialize components
    private void initializeViews() {
        login_button = findViewById(R.id.login_button);
        register_button = findViewById(R.id.register_button);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        alert = findViewById(R.id.alert);
        user_crud = new UserCRUD(this);

        // By default disable login button
        login_button.setEnabled(false);
    }


    // Setup input field text watcher to disable/enable login button
    private void setupInputStateWatcher() {
        // Add text watcher to input fields
        TextWatcher login_input_watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String username_input = username.getText().toString().trim();
                String password_input = password.getText().toString().trim();

                // Set login button enabled if both fields have a value
                login_button.setEnabled(!username_input.isEmpty() && !password_input.isEmpty());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        };

        // Add text watcher to both input fields
        username.addTextChangedListener(login_input_watcher);
        password.addTextChangedListener(login_input_watcher);
    }


    // Setup Login button
    private void setupLoginButton() {
        login_button.setOnClickListener(v -> {
            String username_input = username.getText().toString().trim();
            String password_input = password.getText().toString().trim();
            View anchor_view = findViewById(android.R.id.content);

            // if either of the inputs are empty display error message
            if (username_input.isEmpty() || password_input.isEmpty()) {
                SnackbarUtils.showSnackbar(anchor_view,"Pleas enter both username and password", Color.RED, Color.WHITE, Color.YELLOW);
            }

            // Determine if input for username password matches valid user
            boolean is_valid = user_crud.validateUser(username_input, password_input);
            if (is_valid) {
                // Set logged in session context of user_id
                SessionManager.setUserID(user_crud.getUserID(username_input));
                // Display login success message
                SnackbarUtils.showSnackbar(anchor_view,"Login successful", Color.GREEN, Color.BLACK, Color.YELLOW);
                // Determine new intended activity (database)
                Intent intent = new Intent(LoginActivity.this, DatabaseActivity.class);
                // Start the database activity
                startActivity(intent);
            }
            else {
                // Display bad login message
                SnackbarUtils.showSnackbar(anchor_view,"Login unsuccessful, please check credentials", Color.RED, Color.WHITE, Color.YELLOW);
            }

        });
    }


    // Setup Register button
    private void setupRegisterButton() {
        register_button.setOnClickListener(v -> {
            // Start the registration activity
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }
}
