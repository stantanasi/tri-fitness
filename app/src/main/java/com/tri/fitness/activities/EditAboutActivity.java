package com.tri.fitness.activities;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.tri.fitness.R;

import java.util.Objects;

public class EditAboutActivity extends AppCompatActivity {

    EditText aboutInput;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_about);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.AboutMe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        aboutInput = findViewById(R.id.aboutInput);

        sharedPreferences = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.apply();

        String aboutSaved = sharedPreferences.getString("about", "");
        aboutInput.setText(aboutSaved);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_validate, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.validateActivity:
                editor.putString("about", aboutInput.getText().toString());
                editor.apply();
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
