package com.tri.fitness.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.models.Programs;
import com.tri.fitness.adapters.ProgramsAdapter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ProgramsRowActivity extends AppCompatActivity {

    RecyclerView programs_RecyclerView;
    List<Programs> myPrograms;
    ProgramsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_programs_row);

        setActivity();
        writePrograms();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_training_row, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:
                Toast.makeText(this, "soon", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.reset:
                resetList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void setActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.training);
        getSupportActionBar().setSubtitle(R.string.programs);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        programs_RecyclerView = findViewById(R.id.programs_RecyclerView);
    }


    public void writePrograms() {
        myPrograms = new ArrayList<>();
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("programs.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] list = stringBuilder.toString().split(";");
            for (String exercisingItem : list) {
                String[] programs = exercisingItem.split("/");
                myPrograms.add(new Programs(programs[0], getExercisingImage(programs[1]), programs[2], programs[3], programs[4]));
            }
        }
        catch (Exception e) {
            e.printStackTrace();

            resetList();
        }

        myAdapter = new ProgramsAdapter(myPrograms);
        programs_RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        programs_RecyclerView.setAdapter(myAdapter);
    }

    public Bitmap getExercisingImage(String image) {
        Bitmap exercisingImage_Bitmap;
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput(image);
            exercisingImage_Bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();
            exercisingImage_Bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_image_programs);
        }
        return exercisingImage_Bitmap;
    }


    public void resetList() {
        try {
            String line;
            InputStream defaultExercising_List_fr = getResources().openRawResource(R.raw.default_programs);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(defaultExercising_List_fr, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((line=bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("programs.txt", MODE_PRIVATE);
            fileOutputStream.write(stringBuilder.toString().getBytes());

            defaultExercising_List_fr.close();
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Une erreur lors de la reinitialisation a eu lieu", Toast.LENGTH_SHORT).show();
        }

        writePrograms();
    }
}
