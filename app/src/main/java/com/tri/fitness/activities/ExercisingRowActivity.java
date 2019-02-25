package com.tri.fitness.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.models.Exercising;
import com.tri.fitness.adapters.ExercisingAdapter;
import com.tri.fitness.utils.Function;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExercisingRowActivity extends AppCompatActivity {

    Function function;

    RecyclerView exercising_RecyclerView;
    List<Exercising> myExercising;
    ExercisingAdapter myAdapter;

    FloatingActionButton add_exercising_FloatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercising_row);

        function = new Function(ExercisingRowActivity.this);

        setActivity();
        writeExercices();
        setAddExercising();


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.training);
        getSupportActionBar().setSubtitle(R.string.exercising);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        writeExercices();
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
                return true;
            case R.id.reset:
                resetList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void setActivity() {
        exercising_RecyclerView = findViewById(R.id.exercising_RecyclerView);

        add_exercising_FloatingActionButton = findViewById(R.id.add_exercising_FloatingActionButton);
    }


    public void writeExercices() {
        myExercising = new ArrayList<>();
        try {
            String exercices = function.getStringFromFile( "exercices.txt");
            LinearLayout anyExercices_LinearLayout = findViewById(R.id.anyExercices_LinearLayout);

            if (exercices.equals("")) {
                anyExercices_LinearLayout.setVisibility(View.VISIBLE);
            }
            else {
                anyExercices_LinearLayout.setVisibility(View.GONE);
                String[] list = exercices.split("/e1");
                for (String exercisingItem : list) {
                    String[] exercising2 = exercisingItem.split("/e2");
                    myExercising.add(new Exercising(exercising2[0], getExercisingImage(exercising2[1]) ));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG).show();

            String newFile = "";
            try {
                function.saveStringInFile("exercices.txt", newFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            writeExercices();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myAdapter = new ExercisingAdapter(myExercising, ExercisingRowActivity.this);
        exercising_RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        exercising_RecyclerView.setNestedScrollingEnabled(false);
        exercising_RecyclerView.setAdapter(myAdapter);
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
            exercisingImage_Bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_image_exercising);
        }
        return exercisingImage_Bitmap;
    }


    public void resetList() {
        try {
            InputStream defaultExercising_List_fr = getResources().openRawResource(R.raw.default_exercising);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(defaultExercising_List_fr, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=bufferedReader.read()) != -1) {
                stringBuilder.append((char) content);
            }

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("exercices.txt", MODE_PRIVATE);
            fileOutputStream.write(stringBuilder.toString().getBytes());

            defaultExercising_List_fr.close();
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Une erreur lors de la reinitialisation a eu lieu", Toast.LENGTH_SHORT).show();
        }

        writeExercices();
    }


    public void setAddExercising() {
        add_exercising_FloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent EditExercisingActivity = new Intent(ExercisingRowActivity.this,
                        EditExercisingActivity.class);

                EditExercisingActivity.putExtra("exercisingPosition", -1);

                startActivity(EditExercisingActivity);
            }
        });
    }
}
