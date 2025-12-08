package com.example.cs360project.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.telephony.SmsManager;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.cs360project.R;
import com.example.cs360project.database.WeightCRUD;
import com.example.cs360project.database.WeightGoalCRUD;
import com.example.cs360project.manager.GoalManager;
import com.example.cs360project.manager.TableManager;
import com.example.cs360project.manager.WeightUpdateListener;
import com.example.cs360project.model.Weight;
import com.example.cs360project.model.WeightGoal;
import com.example.cs360project.utils.NavigationUtils;
import com.example.cs360project.utils.SessionManager;
import com.example.cs360project.utils.SnackbarUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class DatabaseActivity extends AppCompatActivity implements WeightUpdateListener {

    private TableManager table_manager;
    private GoalManager goal_manager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_grid);

        initializeManagers();
        table_manager.loadWeightEntries();
        goal_manager.setupGoalSection();
        NavigationUtils.setupBottomNavigation(this, R.id.miDatabase);
    }


    private void initializeManagers() {
        table_manager = new TableManager(this, this);
        goal_manager = new GoalManager(this, table_manager);
    }


    // When the listener procs, update the goal section
    @Override
    public void onWeightUpdated() {
        goal_manager.setupGoalSection();
    }
}
