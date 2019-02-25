package com.tri.fitness.activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.utils.Function;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Arrays;
import java.util.Objects;

public class EditExercisingActivity extends AppCompatActivity {

    Function function;

    int exercisingPosition;
    String azer;

    EditText editExercisingName_EditText;
    ImageView editExercisingImage_ImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_exercising);

        function = new Function(EditExercisingActivity.this);

        exercisingPosition = Objects.requireNonNull(getIntent().getExtras()).getInt("exercisingPosition");

        setActivity();
        getInfo();
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
                if (exercisingPosition == -1) {
                    addExercising();
                }
                else {
                    modifyExercising();
                }
                return true;
        }

        return super.onOptionsItemSelected(item);
    }



    public void setActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        editExercisingName_EditText = findViewById(R.id.editExercisingName_EditText);
        editExercisingImage_ImageView = findViewById(R.id.editExercising_image_ImageView);
    }


    public void getInfo() {
        try {
            azer = function.getStringFromFile( "exercices.txt");
            Toast.makeText(this, azer, Toast.LENGTH_LONG).show();

            if (exercisingPosition != -1) {
                String[] list = azer.split("/e1");
                String[] t = list[exercisingPosition].split("/e2");

                writeInfo(t[0], t[1], t[2]);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void writeInfo(String exercisingName_String, String exercisingImage_String, String exercisingMuscles_String) {
        editExercisingName_EditText.setText(exercisingName_String);

        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput(exercisingImage_String);
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();
            editExercisingImage_ImageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
            editExercisingImage_ImageView.setImageResource(R.drawable.default_image_exercising);
        }
    }


    public void addExercising() {
        try {
            String name = editExercisingName_EditText.getText().toString().trim();
            String nameImg = name.replaceAll(" ", "_") + ".png";
            String muscles = " ";

            Toast.makeText(this, azer, Toast.LENGTH_LONG).show();

            String newExercices = name + "/e2" + nameImg + "/e2" + muscles;

            azer = azer + newExercices + "/e1";

            String[] list = azer.split("/e1");
            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
            String goodString = "";
            for (String s : list) {
                goodString = goodString + s + "/e1" ;
            }

            Toast.makeText(this, goodString, Toast.LENGTH_LONG).show();

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("exercices.txt", MODE_PRIVATE);
            fileOutputStream.write(goodString.getBytes());
            fileOutputStream.close();
            finish();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void modifyExercising() {}
}
