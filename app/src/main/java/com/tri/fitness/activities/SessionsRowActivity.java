package com.tri.fitness.activities;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.adapters.SessionsAdapter;
import com.tri.fitness.models.Sessions;
import com.tri.fitness.R;
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
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class SessionsRowActivity extends AppCompatActivity {

    Function function;

    String stra;

    RecyclerView sessions_RecyclerView;
    List<Sessions> mySessions;
    SessionsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sessions_row);

        function = new Function(SessionsRowActivity.this);

        setActivity();
        writeSessions();

        setAddSession();
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
                Intent SearchActivity = new Intent(SessionsRowActivity.this, SearchActivity.class);
                startActivity(SearchActivity);
                return true;
            case R.id.reset:
                resetList();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        writeSessions();
    }

    public void setActivity() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.training);
        getSupportActionBar().setSubtitle(R.string.sessions);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        sessions_RecyclerView = findViewById(R.id.sessions_RecyclerView);
    }


    public void writeSessions() {
        mySessions = new ArrayList<>();
        try {
            stra = function.getStringFromFile("sessions.txt");

            LinearLayout anySessions_LinearLayout = findViewById(R.id.anySessions_LinearLayout);
            if (!(stra.equals(""))) {
                anySessions_LinearLayout.setVisibility(View.GONE);
                String[] list = stra.split("/s1");
                Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                for (String exercisingItem : list) {
                    String[] sessionst = exercisingItem.split("/s2");
                    String[] sessions = sessionst[0].split("/s3");
                    mySessions.add(new Sessions(sessions[0], getExercisingImage(sessions[1]), sessions[2], sessions[3], sessions[4]));
                }
            }
            else {
                anySessions_LinearLayout.setVisibility(View.VISIBLE);
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG).show();

            String newFile = "";
            try {
                function.saveStringInFile("sessions.txt", newFile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            writeSessions();
        } catch (IOException e) {
            e.printStackTrace();
        }

        myAdapter = new SessionsAdapter(mySessions);
        sessions_RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        sessions_RecyclerView.setAdapter(myAdapter);
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
            exercisingImage_Bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.default_image_sessions);
        }
        return exercisingImage_Bitmap;
    }


    public void resetList() {
        try {
            String line;
            InputStream defaultExercising_List_fr = getResources().openRawResource(R.raw.default_sessions);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(defaultExercising_List_fr, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((line=bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("sessions.txt", MODE_PRIVATE);
            fileOutputStream.write(stringBuilder.toString().getBytes());

            defaultExercising_List_fr.close();
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Une erreur lors de la reinitialisation a eu lieu", Toast.LENGTH_SHORT).show();
        }

        writeSessions();
    }


    public void setAddSession() {
        FloatingActionButton addSessions_FloatingActionButton = findViewById(R.id.addSessions_FloatingActionButton);
        addSessions_FloatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog sessionName_Dialog = new Dialog(SessionsRowActivity.this);
                Objects.requireNonNull(sessionName_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                sessionName_Dialog.setContentView(R.layout.dialog_training_name);
                sessionName_Dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
                sessionName_Dialog.show();

                final EditText sessionName_EditText = sessionName_Dialog.findViewById(R.id.name_EditText);
                TextView confirm = sessionName_Dialog.findViewById(R.id.confirmName);
                TextView cancel = sessionName_Dialog.findViewById(R.id.cancelName);

                // Save
                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionName_Dialog.dismiss();

                        try {
                            String name = sessionName_EditText.getText().toString().trim();
                            String nameImg = name.replaceAll(" ", "_") + ".png";
                            String timeTotal = "00:00";
                            String numberSeries = "0";
                            String numberExercices = "0";
                            String level = "-";
                            String goal = "-";
                            String muscles = " ";
                            String equipmentRequired = " ";
                            String exercicesUsed = " ";

                            String newSession = name + "/s3" +
                                    nameImg + "/s3" +
                                    timeTotal + "/s3" +
                                    numberSeries + "/s3" +
                                    numberExercices + "/s3" +
                                    level + "/s3" +
                                    goal + "/s3" +
                                    muscles + "/s2" +
                                    equipmentRequired + "/s2" +
                                    exercicesUsed;

                            stra = stra + newSession + "/s1";

                            String[] list = stra.split("/s1");
                            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);
                            String goodString = function.join("/s1", list);

                            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("sessions.txt", MODE_PRIVATE);
                            fileOutputStream.write(goodString.getBytes());
                            fileOutputStream.close();

                            Intent SessionActivity = new Intent(SessionsRowActivity.this, SessionActivity.class);
                            SessionActivity.putExtra("session_position", Arrays.asList(list).indexOf(newSession));
                            startActivity(SessionActivity);
                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionName_Dialog.dismiss();
                    }
                });
            }
        });
    }
}
