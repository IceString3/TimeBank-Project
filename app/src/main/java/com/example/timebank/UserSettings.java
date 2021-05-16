package com.example.timebank;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

        getSupportActionBar().setTitle("Settings");
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.idFrameLayout, new SettingsFragment())
                .commit();
    }
}