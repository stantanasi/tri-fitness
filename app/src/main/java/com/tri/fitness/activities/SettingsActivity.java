package com.tri.fitness.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.tri.fitness.R;
import com.tri.fitness.utils.SessionManager;

public class SettingsActivity extends AppCompatActivity {

    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.settings);

        sessionManager = new SessionManager(this);
    }

    public void logout(View view) {
        sessionManager.logout();
        Intent LoginActivity = new Intent(SettingsActivity.this, LoginActivity.class);
        startActivity(LoginActivity);
        finish();
    }
}
