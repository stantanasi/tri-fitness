package com.tri.fitness.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.adapters.EquipmentFromSessionAdapter;
import com.tri.fitness.adapters.ExercicesUsedAdapter_session;
import com.tri.fitness.models.EquipmentFromSession;
import com.tri.fitness.models.ExercisesUsed_session;
import com.tri.fitness.R;
import com.tri.fitness.utils.Function;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SessionActivity extends AppCompatActivity {

    Function function;
    
    int session_position;
    String sessionName_String, sessionLevel_String, sessionGoal_String, sessionsDurationTotal_String, sessionsNumberSeries_String, sessionsNumberExercising_String,
            equipmentRequired,
            exercicesUsed,
            Session;
    Bitmap sessionImage_Bitmap;

    LinearLayout session_addFirstExerciceUsed_LinearLayout;
    TextView sessionName_TextView, sessionLevel_TextView, sessionGoal_TextView, sessionsDurationTotal_TextView, sessionsNumberSeries_TextView, sessionsNumberExercising_TextView,
            session_exerciceUsedTitle_TextView;
    ImageView sessionImage_ImageView;
    RadioGroup goal_RadioGroup, level_RadioGroup;

    Dialog sessionGoal_Dialog, sessionLevel_Dialog;

    RecyclerView session_equipmentRequired_RecyclerView;
    List<EquipmentFromSession> myEquipmentRequired;
    EquipmentFromSessionAdapter myAdapter;

    RecyclerView session_exerciceUsed_RecyclerView;
    List<ExercisesUsed_session> myExerciceUsed;
    ExercicesUsedAdapter_session myExercicesUsedAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_session);

        function = new Function(SessionActivity.this);

        session_position = Objects.requireNonNull(getIntent().getExtras()).getInt("session_position");

        setActivity();
        getSessionInfo();
        writeSessionInfo();

        setSessionName();
        setSessionLevel();
        setSessionGoal();

        setSessionDuration();

        setExercicesUsed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_delete, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.delete_menu_item:
                deleteSession();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }




    public void setActivity() {
        Toolbar sessions_toolbar = findViewById(R.id.sessions_Toolbar);
        setSupportActionBar(sessions_toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        sessionName_TextView = findViewById(R.id.sessionsName_TextView);
        sessionImage_ImageView = findViewById(R.id.sessionsImage_ImageView);

        sessionLevel_TextView = findViewById(R.id.sessionLevel_TextView);
        sessionGoal_TextView = findViewById(R.id.sessionGoal_TextView);

        sessionsDurationTotal_TextView = findViewById(R.id.sessionsDurationTotal_TextView);
        sessionsNumberSeries_TextView = findViewById(R.id.sessionsNumberSeries_TextView);
        sessionsNumberExercising_TextView = findViewById(R.id.sessionsNumberExercising_TextView);

        session_exerciceUsedTitle_TextView = findViewById(R.id.session_exerciceUsedTitle_TextView);
        session_addFirstExerciceUsed_LinearLayout = findViewById(R.id.session_addFirstExerciceUsed_LinearLayout);
        session_exerciceUsed_RecyclerView = findViewById(R.id.session_exerciceUsed_RecyclerView);
    }

    public void getSessionInfo() {
        try {
            FileInputStream fileInputStream = openFileInput("sessions.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }
            String[] list = stringBuilder.toString().split("/s1");

            Session = list[session_position];
            String[] part = Session.split("/s2");
            String sessionInfo = part[0];
            equipmentRequired = part[1];
            exercicesUsed = part[2];


            String[] Info = sessionInfo.split("/s3");
            sessionName_String = Info[0].trim();
            sessionImage_Bitmap = function.getImageFromFile(Info[1].trim(), BitmapFactory.decodeResource(this.getResources(), R.drawable.default_image_sessions));
            sessionsDurationTotal_String = Info[2].trim();
            sessionsNumberSeries_String = Info[3].trim();
            sessionsNumberExercising_String = Info[4].trim();
            sessionLevel_String = Info[5];
            sessionGoal_String = Info[6];
        }
        catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "erreur", Toast.LENGTH_LONG).show();
        }
    }

    public void writeSessionInfo() {
        sessionName_TextView.setText(sessionName_String);
        sessionImage_ImageView.setImageBitmap(sessionImage_Bitmap);

        sessionLevel_TextView.setText(sessionLevel_String);
        sessionGoal_TextView.setText(sessionGoal_String);

        sessionsDurationTotal_TextView.setText(sessionsDurationTotal_String);
        sessionsNumberSeries_TextView.setText(sessionsNumberSeries_String);
        sessionsNumberExercising_TextView.setText(sessionsNumberExercising_String);

        Toast.makeText(this, Session, Toast.LENGTH_LONG).show();
    }

    public void deleteSession() {
        try {
            FileInputStream fileInputStream = openFileInput("sessions.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] list = stringBuilder.toString().split("/s1");
            List<String> fg = new ArrayList<>();
            Collections.addAll(fg, list);
            fg.remove(session_position);
            list = fg.toArray(new String[fg.size()]);
            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("sessions.txt", MODE_PRIVATE);
            fileOutputStream.write(function.join("/s1", list).getBytes());
            fileOutputStream.close();

            finish();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void setSessionName() {
        /*final Dialog sessionName_Dialog = new Dialog(this);
        Objects.requireNonNull(sessionName_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sessionName_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionName_Dialog.setContentView(R.layout.dialog_training_name);
                sessionName_Dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
                sessionName_Dialog.show();

                final EditText sessionName_EditText = sessionName_Dialog.findViewById(R.id.name_EditText);
                TextView confirm = sessionName_Dialog.findViewById(R.id.confirmName);
                TextView cancel = sessionName_Dialog.findViewById(R.id.cancelName);

                sessionName_EditText.setText(sessionName_String);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!(sessionName_EditText.getText().toString().trim().equals(""))) {
                            save(0, sessionName_EditText.getText().toString().trim());
                            sessionName_Dialog.dismiss();
                        }

                        else {
                            Toast.makeText(SessionActivity.this, "nom invalide", Toast.LENGTH_SHORT).show();
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
        });*/
    }

    public void setSessionLevel() {
        sessionLevel_Dialog = new Dialog(this);
        Objects.requireNonNull(sessionLevel_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sessionLevel_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionLevel_Dialog.setContentView(R.layout.dialog_sportinglevel_picker);
                sessionLevel_Dialog.show();

                level_RadioGroup = sessionLevel_Dialog.findViewById(R.id.sportingLevel_RadioGroup);
                if (sessionLevel_String.equals(getResources().getString(R.string.level_beginner))) {
                    level_RadioGroup.check(R.id.sportingLevel_beginner_RadioButton);
                }
                else if (sessionLevel_String.equals(getResources().getString(R.string.level_intermediate))) {
                    level_RadioGroup.check(R.id.sportingLevel_intermediate_RadioButton);
                }
                else if (sessionLevel_String.equals(getResources().getString(R.string.level_advanced))) {
                    level_RadioGroup.check(R.id.sportingLevel_advanced_RadioButton);
                }
                else if (sessionLevel_String.equals(getResources().getString(R.string.level_expert))) {
                    level_RadioGroup.check(R.id.sportingLevel_expert_RadioButton);
                }
                else if (sessionLevel_String.equals(getResources().getString(R.string.level_all))) {
                    level_RadioGroup.check(R.id.sportingLevel_all_RadioButton);
                }
            }
        });
    }
    public void levelChecked(View view) {
        RadioButton radioButton = sessionLevel_Dialog.findViewById(level_RadioGroup.getCheckedRadioButtonId());

        save(5, String.valueOf(radioButton.getText()));
        sessionLevel_Dialog.dismiss();
    }

    public void setSessionGoal() {
        sessionGoal_Dialog = new Dialog(this);
        Objects.requireNonNull(sessionGoal_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sessionGoal_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionGoal_Dialog.setContentView(R.layout.dialog_goal_picker);
                sessionGoal_Dialog.show();

                goal_RadioGroup = sessionGoal_Dialog.findViewById(R.id.goal_RadioGroup);
                if (sessionGoal_String.equals(getResources().getString(R.string.goal_muscleGain))) {
                    goal_RadioGroup.check(R.id.goal_muscleGain_RadioButton);
                }
                else if (sessionGoal_String.equals(getResources().getString(R.string.goal_improveCardio))) {
                    goal_RadioGroup.check(R.id.goal_improveCardio_RadioButton);
                }
                else if (sessionGoal_String.equals(getResources().getString(R.string.goal_fit))) {
                    goal_RadioGroup.check(R.id.goal_fit_RadioButton);
                }
                else if (sessionGoal_String.equals(getResources().getString(R.string.goal_all))) {
                    goal_RadioGroup.check(R.id.goal_all_RadioButton);
                }
            }
        });
    }
    public void goalChecked(View view) {
        RadioButton radioButton = sessionGoal_Dialog.findViewById(goal_RadioGroup.getCheckedRadioButtonId());

        save(6, String.valueOf(radioButton.getText()));
        sessionGoal_Dialog.dismiss();
    }


    public void setSessionDuration() {
        final Dialog sessionDurationTotal_Dialog = new Dialog(this);
        Objects.requireNonNull(sessionDurationTotal_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        sessionsDurationTotal_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionDurationTotal_Dialog.setContentView(R.layout.dialog_duration);
                sessionDurationTotal_Dialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
                sessionDurationTotal_Dialog.show();

                LinearLayout hours_LinearLayout = sessionDurationTotal_Dialog.findViewById(R.id.hours_LinearLayout);
                hours_LinearLayout.setVisibility(View.VISIBLE);
                LinearLayout minutes_LinearLayout = sessionDurationTotal_Dialog.findViewById(R.id.minutes_LinearLayout);
                minutes_LinearLayout.setVisibility(View.VISIBLE);
                LinearLayout seconds_LinearLayout = sessionDurationTotal_Dialog.findViewById(R.id.seconds_LinearLayout);
                seconds_LinearLayout.setVisibility(View.GONE);

                final NumberPicker sessionHours_NumberPicker = sessionDurationTotal_Dialog.findViewById(R.id.hours_NumberPicker);
                final NumberPicker sessionMinutes_NumberPicker = sessionDurationTotal_Dialog.findViewById(R.id.minutes_NumberPicker);
                TextView confirm = sessionDurationTotal_Dialog.findViewById(R.id.confirmDuration_TextView);
                TextView cancel = sessionDurationTotal_Dialog.findViewById(R.id.cancelDuration_TextView);

                String[] t = sessionsDurationTotal_String.split(":");

                sessionHours_NumberPicker.setMinValue(0);
                sessionHours_NumberPicker.setMaxValue(23);
                sessionHours_NumberPicker.setValue(Integer.parseInt(t[0]));
                sessionHours_NumberPicker.setWrapSelectorWheel(true);

                sessionMinutes_NumberPicker.setMinValue(0);
                sessionMinutes_NumberPicker.setMaxValue(59);
                sessionMinutes_NumberPicker.setValue(Integer.parseInt(t[1]));
                sessionMinutes_NumberPicker.setWrapSelectorWheel(true);

                confirm.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String duration = String.valueOf(sessionHours_NumberPicker.getValue()) + ":" + String.valueOf(sessionMinutes_NumberPicker.getValue());
                        save(2 , duration);
                        sessionDurationTotal_Dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sessionDurationTotal_Dialog.dismiss();
                    }
                });
            }
        });
    }


    public void save(int editPosition_int, String editValue_String) {
        try {
            FileInputStream fileInputStream = openFileInput("sessions.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }
            String[] list = stringBuilder.toString().split("/s1");
            String[] session = list[session_position].split("/s3");

            session[editPosition_int] = editValue_String;
            list[session_position] = function.join("/s3", session);
            Arrays.sort(list, String.CASE_INSENSITIVE_ORDER);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("sessions.txt", MODE_PRIVATE);
            fileOutputStream.write(function.join("/s1", list).getBytes());
            fileOutputStream.close();


            getSessionInfo();
            writeSessionInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }




    /*
    public void writeEquipmentRequired() {
        myEquipmentRequired = new ArrayList<>();
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("equipment_required.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] list = stringBuilder.toString().split(";");
            for (String exercisingItem : list) {
                String[] sessions = exercisingItem.split("/");
                myEquipmentRequired.add(new EquipmentFromSession(sessions[0], getImage(sessions[1]), Integer.parseInt(sessions[2])));
            }
        }
        catch (Exception e) {
            e.printStackTrace();

            resetList();
        }

        myAdapter = new EquipmentFromSessionAdapter(myEquipmentRequired);
        session_equipmentRequired_RecyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 5));
        session_equipmentRequired_RecyclerView.setAdapter(myAdapter);
    }

    public Bitmap getImage(String image) {
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
            InputStream defaultExercising_List_fr = getResources().openRawResource(R.raw.default_equipment_required);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(defaultExercising_List_fr, "UTF-8"));
            StringBuilder stringBuilder = new StringBuilder();
            while ((line=bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("equipment_required.txt", MODE_PRIVATE);
            fileOutputStream.write(stringBuilder.toString().getBytes());

            defaultExercising_List_fr.close();
            fileOutputStream.close();
        }
        catch (Exception e) {
            e.printStackTrace();

            Toast.makeText(this, "Une erreur lors de la reinitialisation a eu lieu", Toast.LENGTH_SHORT).show();
        }

        writeEquipmentRequired();
    }
    */


    public void setExercicesUsed() {
        session_exerciceUsedTitle_TextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent ExerciceUsedActivity = new Intent(SessionActivity.this, ExerciceUsedActivity.class);
                startActivity(ExerciceUsedActivity);
            }
        });

        if (exercicesUsed.equals(" ")) {
            session_addFirstExerciceUsed_LinearLayout.setVisibility(View.VISIBLE);
            session_addFirstExerciceUsed_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ExercisingRowActivity = new Intent(SessionActivity.this, ExercisingRowActivity.class);
                    startActivityForResult(ExercisingRowActivity, 1);
                }
            });
        }
        else {
            // session_addFirstExerciceUsed_LinearLayout.setVisibility(View.GONE);
            myExerciceUsed = new ArrayList<>();

            int i = 1;
            String[] list = exercicesUsed.split("/s3");
            for (String exercicesUsedItem : list) {
                String[] ty = exercicesUsedItem.split("/s4");

                for (String ert : ty) {
                    String[] exercices = ert.split("/e2");
                    myExerciceUsed.add(new ExercisesUsed_session(String.valueOf(i), exercices[0], function.getImageFromFile(exercices[1], BitmapFactory.decodeResource(this.getResources(), R.drawable.default_image_sessions)), exercices[2], "0", "0"));
                }

                i = i + 1;
            }

            myExercicesUsedAdapter = new ExercicesUsedAdapter_session(myExerciceUsed);
            session_exerciceUsed_RecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            session_exerciceUsed_RecyclerView.setAdapter(myExercicesUsedAdapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                exercicesUsed = data.getStringExtra("result");
                savePart(2, exercicesUsed);
                setExercicesUsed();
            }

        }
    }


    public void savePart(int par_int, String editValue_String) {
        try {
            String good = function.getStringFromFile("sessions.txt");
            String[] list = good.split("/s1");

            Session = list[session_position];
            String[] part = Session.split("/s2");
            part[par_int] = part[par_int] + editValue_String + "/s3";

            list[session_position] = function.join("/s2", part);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("sessions.txt", MODE_PRIVATE);
            fileOutputStream.write(function.join("/s1", list).getBytes());
            fileOutputStream.close();


            getSessionInfo();
            writeSessionInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
