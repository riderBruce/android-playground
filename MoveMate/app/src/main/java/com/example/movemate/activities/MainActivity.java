package com.example.movemate.activities;

import android.os.Bundle;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.movemate.R;
import com.example.movemate.adapters.MoveLogDB;
import com.example.movemate.models.LogRecord;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        HomeFragment.HomeFragmentListener,
        ProfileFragment.ProfileFragmentListener,
        UpdateProfileFragment.UpdateProfileFragmentListener,
        AddLogFragment.AddLogFragmentListener,
        LogListFragment.LogListFragmentListener
{

    private FragmentManager manager;
    private FloatingActionButton fabAddLog;
    private BottomNavigationView bottomNavigationView;
    private MoveLogDB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        db = new MoveLogDB(this);

        manager = getSupportFragmentManager();

        fabAddLog = findViewById(R.id.fabAddLog);
        bottomNavigationView = findViewById(R.id.bottomNavigationView);

        if (savedInstanceState == null) {
            showFragment(new HomeFragment(), false);
        }

        fabAddLog.setOnClickListener(v->{
            showFragment(new AddLogFragment(), true);
        });

        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();

            if (itemId == R.id.navHome) {
                showFragment(new HomeFragment(), false);
                return true;
            } else if (itemId == R.id.navLogs) {
                showFragment(new LogListFragment(), false);
                return true;
            } else if (itemId == R.id.navProfile) {
                showFragment(new ProfileFragment(), false);
                return true;
            }
            return false;
        });

    }

    private void showFragment(Fragment fragment, boolean addToBackStack) {
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment);

        if (addToBackStack) {
            transaction.addToBackStack(null);
        }
        transaction.commit();
    }

    @Override
    public void onUpdateProfileRequested() {
        showFragment(new UpdateProfileFragment(), true);
    }

    @Override
    public void onQuickSearchRequested(String activity) {
        showFragment(LogListFragment.newInstance(activity), true);
    }

    @Override
    public void onSaveLogRequested(LogRecord record) {
        long rowId = db.addRecord(record);

        if (rowId == -1) {
            Toast.makeText(
                    this,
                    "Unable to save movement log",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }

        Toast.makeText(
                this,
                "Movement log saved",
                Toast.LENGTH_SHORT
        ).show();
        showFragment(new HomeFragment(), false);
    }

    @Override
    public void onSaveProfileRequested() {
        // save data

        // redirection
        showFragment(new ProfileFragment(), true);
    }

    @Override
    public List<LogRecord> onRequestLogRecords() {
        return db.getAllRecordsAsList();
    }

    @Override
    public void onViewDetailRequested(int id) {
        LogRecord record = db.getRecordById(id);
        if (record != null) {
            showFragment(LogDetailFragment.newInstance(record), true);
        }
    }
}
