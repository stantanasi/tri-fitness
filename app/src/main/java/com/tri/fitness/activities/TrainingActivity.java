package com.tri.fitness.activities;

import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;


import com.tri.fitness.R;

import java.util.Objects;

public class TrainingActivity extends AppCompatActivity {

    LinearLayout programs, sessions, exercising, equipment;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_training);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.training);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        programs = findViewById(R.id.programs);
        programs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ProgramsRowActivity = new Intent(TrainingActivity.this,
                        ProgramsRowActivity.class);
                startActivity(ProgramsRowActivity);
            }
        });


        sessions = findViewById(R.id.sessions);
        sessions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent SessionsRowActivity = new Intent(TrainingActivity.this,
                        SessionsRowActivity.class);
                startActivity(SessionsRowActivity);
            }
        });


        exercising = findViewById(R.id.exercising);
        exercising.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ExercisingRowActivity = new Intent(TrainingActivity.this,
                        ExercisingRowActivity.class);
                startActivity(ExercisingRowActivity);
            }
        });
    }
}
