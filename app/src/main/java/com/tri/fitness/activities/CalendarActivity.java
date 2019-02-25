package com.tri.fitness.activities;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;

import com.tri.fitness.R;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Objects;

public class CalendarActivity extends AppCompatActivity {

    DatePickerDialog dayPicker_Dialog;

    SharedPreferences userCalendar;
    SharedPreferences.Editor edit_userCalendar;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.calendar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        setActivity();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_calendar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.dayPicker_item:
                dayPicker();

                return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void setActivity() {
        userCalendar = getSharedPreferences("userCalendar", Context.MODE_PRIVATE);
        edit_userCalendar = userCalendar.edit();
        edit_userCalendar.apply();
    }

    public void dayPicker() {
        Calendar day = Calendar.getInstance();
        dayPicker_Dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar dayCalendar = Calendar.getInstance();
                dayCalendar.set(year, month, dayOfMonth);

                String day = DateFormat.getDateInstance(DateFormat.SHORT).format(dayCalendar.getTime());

                writeExercice(day);
            }
        }, day.get(Calendar.YEAR), day.get(Calendar.MONTH), day.get(Calendar.DAY_OF_MONTH));

        dayPicker_Dialog.show();
    }

    public void writeExercice(String day) {
        String exerciceList = userCalendar.getString(day+"exercices", "");
    }
}
