package com.tri.fitness.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.tri.fitness.R;

import java.util.Objects;

public class StatisticsActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        SharedPreferences userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit_userInfo = userInfo.edit();
        edit_userInfo.apply();
        String firstNameSaved = userInfo.getString("firstName", "");
        String lastNameSaved = userInfo.getString("lastName", "");
        String nameSaved = firstNameSaved + " " + lastNameSaved;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(nameSaved);
        getSupportActionBar().setSubtitle(R.string.statistic);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
