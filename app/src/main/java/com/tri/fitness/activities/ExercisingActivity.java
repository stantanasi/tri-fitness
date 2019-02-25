package com.tri.fitness.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.tri.fitness.R;
import com.tri.fitness.utils.Function;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ExercisingActivity extends AppCompatActivity {

    Function function;

    int exercising_position;
    String exercisingName_String, exercisingImage_String, exercisingMuscles_String;
    Bitmap exercisingImage_Bitmap;

    TextView exercisingName_TextView;
    ImageView exercisingImage_ImageView;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercising);

        function = new Function(ExercisingActivity.this);

        exercising_position = Objects.requireNonNull(getIntent().getExtras()).getInt("exercising_position");

        setActivity();
        getExercisingInfo(exercising_position);
        writeExercisingInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_equipment, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.edit_equipment:
                editExercising();
                return true;
            case R.id.delete_equipment:
                deleteExercising();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void setActivity() {
        Toolbar exercising_toolbar = findViewById(R.id.exercising_Toolbar);
        setSupportActionBar(exercising_toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        exercisingImage_ImageView = findViewById(R.id.exercisingImage_ImageView);
        exercisingName_TextView = findViewById(R.id.exercisingName_TextView);
    }


    public void getExercisingInfo(int exercising_position) {
        try {
            FileInputStream fileInputStream = openFileInput("exercices.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }
            String[] list = stringBuilder.toString().split("/e1");
            String[] t = list[exercising_position].split("/e2");

            exercisingName_String = t[0].trim();
            exercisingImage_String = t[1].trim();
            exercisingMuscles_String = t[2].trim();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        try {
            FileInputStream exercisingImage_FileInputStream = getApplicationContext().openFileInput(exercisingImage_String);
            exercisingImage_Bitmap = BitmapFactory.decodeStream(exercisingImage_FileInputStream);
            exercisingImage_FileInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            exercisingImage_Bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_image_exercising);
        }
    }


    public void writeExercisingInfo() {
        exercisingImage_ImageView.setImageBitmap(exercisingImage_Bitmap);
        exercisingName_TextView.setText(exercisingName_String);
    }


    public void editExercising() {
        Intent EditExercisingActivity = new Intent(ExercisingActivity.this,
                EditExercisingActivity.class);

        EditExercisingActivity.putExtra("exercisingPosition", exercising_position);

        startActivity(EditExercisingActivity);
    }


    public void deleteExercising() {
        try {
            FileInputStream fileInputStream = openFileInput("exercices.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] list = stringBuilder.toString().split("/e1");
            List<String> fg = new ArrayList<>();
            Collections.addAll(fg, list);
            fg.remove(exercising_position);
            list = fg.toArray(new String[fg.size()]);
            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("exercices.txt", MODE_PRIVATE);
            fileOutputStream.write(function.join("/e1", list).getBytes());
            fileOutputStream.close();

            finish();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
