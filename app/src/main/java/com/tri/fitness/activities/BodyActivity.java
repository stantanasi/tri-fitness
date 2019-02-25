package com.tri.fitness.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.GradientDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.utils.Function;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class BodyActivity extends AppCompatActivity {

    Function function;

    TextView bmi, size, weight;
    Dialog bodyDialog, left_bodyDialog, right_bodyDialog;
    Calendar calendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body);

        function = new Function(BodyActivity.this);

        setActivity();
        writeBodyInfo();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        SharedPreferences userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit_userInfo = userInfo.edit();
        edit_userInfo.apply();
        String firstNameSaved = userInfo.getString("firstName", "");
        String lastNameSaved = userInfo.getString("lastName", "");
        String nameSaved = firstNameSaved + " " + lastNameSaved;

        Objects.requireNonNull(getSupportActionBar()).setTitle(nameSaved);

        writeBodyInfo();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    public void setActivity() {
        SharedPreferences userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit_userInfo = userInfo.edit();
        edit_userInfo.apply();
        String firstNameSaved = userInfo.getString("firstName", "");
        String lastNameSaved = userInfo.getString("lastName", "");
        String nameSaved = firstNameSaved + " " + lastNameSaved;

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(nameSaved);
        getSupportActionBar().setSubtitle(R.string.body);

        calendar = Calendar.getInstance();

        bodyDialog = new Dialog(this);
        bodyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        bodyDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        left_bodyDialog = new Dialog(this);
        left_bodyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        left_bodyDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
        right_bodyDialog = new Dialog(this);
        right_bodyDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        right_bodyDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);




        bmi = findViewById(R.id.bmi);
        size = findViewById(R.id.size);
        weight = findViewById(R.id.weight);
    }

    public void writeBodyInfo() {
        try {
            String bodyPart_String = function.getStringFromFile("body.txt");
            Toast.makeText(this, bodyPart_String, Toast.LENGTH_LONG).show();

            String[] bodyPart_ArrayString = bodyPart_String.split("/b1");

            String[] bmi_ArrayString = bodyPart_ArrayString[0].split("/b2");
            setBMI(bmi_ArrayString);
            String[] size_ArrayString = bodyPart_ArrayString[1].split("/b2"); // Interieur diviser en 2 par /b3 avec date et value (x et y)
            setSize(size_ArrayString);
            String[] weight_ArrayString = bodyPart_ArrayString[2].split("/b2");
            setWeight(weight_ArrayString);

            String[] neck_ArrayString = bodyPart_ArrayString[3].split("/b2");
            setNeck(neck_ArrayString);
            String[] chest_ArrayString = bodyPart_ArrayString[4].split("/b2");
            setChest(chest_ArrayString);
            String[] waist_ArrayString = bodyPart_ArrayString[5].split("/b2");
            setWaist(waist_ArrayString);
            String[] hip_ArrayString = bodyPart_ArrayString[6].split("/b2");
            setHip(hip_ArrayString);

            String[] arm_ArrayString = bodyPart_ArrayString[7].split("/b2");
            setArm(arm_ArrayString);
            String[] forearm_ArrayString = bodyPart_ArrayString[8].split("/b2");
            setForearm(forearm_ArrayString);
            String[] thigh_ArrayString = bodyPart_ArrayString[9].split("/b2");
            setThigh(thigh_ArrayString);
            String[] calf_ArrayString = bodyPart_ArrayString[10].split("/b2");
            setCalf(calf_ArrayString);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();

                // Creer le fichier pour qu'il n'y ai pas d'erreur
                String newFile = 
                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +

                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b2" + "0"+"/b3"+ "0" + "/b1" +

                        "0"+"/b3"+ "0" + "/b4" + "0" + "/b2" + "0"+"/b3"+ "0" + "/b4" + "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b4" + "0" + "/b2" + "0"+"/b3"+ "0" + "/b4" + "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b4" + "0" + "/b2" + "0"+"/b3"+ "0" + "/b4" + "0" + "/b1" +
                        "0"+"/b3"+ "0" + "/b4" + "0" + "/b2" + "0"+"/b3"+ "0" + "/b4" + "0" + "/b1";

                Toast.makeText(this, "FileNotFoundException", Toast.LENGTH_LONG).show();

                try {
                    FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("body.txt", MODE_PRIVATE);
                    fileOutputStream.write(newFile.getBytes());
                    fileOutputStream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }

                writeBodyInfo();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }





     public void setBMI(String[] bmi_ArrayString) {
        // double bmiValue = (double) Math.round(bmiValueSaved * 100) / 100;

        String[] lastValueBMI_ArrayString = bmi_ArrayString[bmi_ArrayString.length-1].split("/b3");
        String dateBMI_String = lastValueBMI_ArrayString[0];
        String valueBMI_String = lastValueBMI_ArrayString[1];

        TextView dateBMI = findViewById(R.id.dateBMI);
        if (lastValueBMI_ArrayString[1].equals("0")) {
            bmi.setText("");

            dateBMI.setTextAppearance(R.style.default_date);
            dateBMI.setText(R.string.default_date);
        }
        else {
            dateBMI.setTextAppearance(R.style.subtitlesText_style);
            dateBMI.setText(dateBMI_String);
            bmi.setText(valueBMI_String);

            GradientDrawable ic_border_bmi = (GradientDrawable)bmi.getBackground();
            if (Double.parseDouble(valueBMI_String) < 16.5) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorMalnutrition));
            }
            else if (Double.parseDouble(valueBMI_String) >= 16.5 && Double.parseDouble(valueBMI_String) < 18.5) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorThinness));
            }
            else if (Double.parseDouble(valueBMI_String) >= 18.5 && Double.parseDouble(valueBMI_String) < 25) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorNormal));
            }
            else if (Double.parseDouble(valueBMI_String) >= 25 && Double.parseDouble(valueBMI_String) < 30) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorOverweight));
            }
            else if (Double.parseDouble(valueBMI_String) >= 30 && Double.parseDouble(valueBMI_String) < 35) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorObesity_moderate));
            }
            else if (Double.parseDouble(valueBMI_String) >= 35 && Double.parseDouble(valueBMI_String) < 40) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorObesity_severe));
            }
            else if (Double.parseDouble(valueBMI_String) >= 40) {
                ic_border_bmi.setStroke((int) getResources().getDimension(R.dimen.bmi_border_width), getResources().getColor(R.color.colorObesity_mass));
            }
        }
    }


    public void setSize(String[] size_ArrayString) {
        writeBody(size_ArrayString, (TextView) findViewById(R.id.dateSize), (TextView) findViewById(R.id.size), (TextView) findViewById(R.id.unitSize_TextView));

        final Dialog sizeDialog = new Dialog(this);
        sizeDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout size_LinearLayout = findViewById(R.id.size_LinearLayout);
        size_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sizeDialog.setContentView(R.layout.dialog_body_size);
                sizeDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
                sizeDialog.show();

                final NumberPicker sizePicker_NumberPicker = sizeDialog.findViewById(R.id.sizePicker);
                sizePicker_NumberPicker.setMinValue(50);
                sizePicker_NumberPicker.setMaxValue(280);
                sizePicker_NumberPicker.setWrapSelectorWheel(true);

                TextView confirmSize = sizeDialog.findViewById(R.id.confirmSize);
                confirmSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        String dateSize = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
                        int sizeValue = sizePicker_NumberPicker.getValue();

                        saveBody(1, dateSize, String.valueOf(sizeValue));
                        sizeDialog.dismiss();
                    }
                });


                TextView cancelSize = sizeDialog.findViewById(R.id.cancelSize);
                cancelSize.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sizeDialog.dismiss();
                    }
                });
            }
        });
    }


    public void setWeight(String[] weight_ArrayString) {
        writeBody(weight_ArrayString, (TextView) findViewById(R.id.dateWeight), (TextView) findViewById(R.id.weight), (TextView) findViewById(R.id.unitWeight));

        final Dialog weightDialog = new Dialog(this);
        weightDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        LinearLayout weight_LinearLayout = findViewById(R.id.weight_LinearLayout);
        weight_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weightDialog.setContentView(R.layout.dialog_body_weight);
                weightDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
                weightDialog.show();


                final NumberPicker weightPickerInt_NumberPicker = weightDialog.findViewById(R.id.weightPicker_int);
                weightPickerInt_NumberPicker.setMinValue(15);
                weightPickerInt_NumberPicker.setMaxValue(250);
                weightPickerInt_NumberPicker.setWrapSelectorWheel(true);

                final NumberPicker weightPickerFloat_NumberPicker = weightDialog.findViewById(R.id.weightPicker_float);
                weightPickerFloat_NumberPicker.setMinValue(0);
                weightPickerFloat_NumberPicker.setMaxValue(9);
                weightPickerFloat_NumberPicker.setWrapSelectorWheel(true);

                TextView confirmWeight = weightDialog.findViewById(R.id.confirmWeight);
                confirmWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Calendar calendar = Calendar.getInstance();
                        String dateWeight = DateFormat.getDateInstance(DateFormat.LONG).format(calendar.getTime());
                        double weightValue_int = weightPickerInt_NumberPicker.getValue();
                        double weightValue_float = weightPickerFloat_NumberPicker.getValue() * 0.1;
                        double weightValue = weightValue_int + weightValue_float;

                        saveBody(2, dateWeight, String.valueOf(weightValue));
                        weightDialog.dismiss();
                    }
                });

                TextView cancelWeight = weightDialog.findViewById(R.id.cancelWeight);
                cancelWeight.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        weightDialog.dismiss();
                    }
                });
            }
        });
    }



    public void writeBody(String[] body_ArrayString, TextView dateBody_TextView, TextView Body_TextView, TextView unitBody_TextView) {
        String[] lastValueBody_ArrayString = body_ArrayString[body_ArrayString.length-1].split("/b3");

        String valueBody_String = lastValueBody_ArrayString[1];

        if (valueBody_String.equals("0")){
            dateBody_TextView.setTextAppearance(R.style.default_date);
            dateBody_TextView.setText(R.string.default_date);

            Body_TextView.setVisibility(View.INVISIBLE);
            unitBody_TextView.setVisibility(View.INVISIBLE);
        }
        else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(lastValueBody_ArrayString[0]);
                SimpleDateFormat rdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
                String dateBody_String = rdf.format(date);
                dateBody_TextView.setTextAppearance(R.style.subtitlesText_style);
                dateBody_TextView.setText(dateBody_String);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            Body_TextView.setVisibility(View.VISIBLE);
            Body_TextView.setText(String.valueOf(valueBody_String));
            unitBody_TextView.setVisibility(View.VISIBLE);
        }
    }
    
    public void onClickBody(final int bodyPosition_int, String[] body_ArrayString, final String bodyPart_String, Integer MinValue_picker, Integer MaxValue_picker) {
        String[] lastValueBody_ArrayString = body_ArrayString[body_ArrayString.length-1].split("/b3");
        String dateBody_String = lastValueBody_ArrayString[0];
        String valueBody_String = lastValueBody_ArrayString[1];
        if (valueBody_String.equals("0")){
            bodyDialog.setContentView(R.layout.dialog_body_second_circumference);
            bodyDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
            bodyDialog.show();

            TextView bodyPart_Circumference = bodyDialog.findViewById(R.id.bodyPart_Circumference);
            bodyPart_Circumference.setText(bodyPart_String);

            final NumberPicker bodyPickerInt_NumberPicker = bodyDialog.findViewById(R.id.second_circumferencePicker_int);
            bodyPickerInt_NumberPicker.setMinValue(MinValue_picker);
            bodyPickerInt_NumberPicker.setMaxValue(MaxValue_picker);
            bodyPickerInt_NumberPicker.setWrapSelectorWheel(true);

            final NumberPicker bodyPickerFloat_NumberPicker = bodyDialog.findViewById(R.id.second_circumferencePicker_float);
            bodyPickerFloat_NumberPicker.setMinValue(0);
            bodyPickerFloat_NumberPicker.setMaxValue(9);
            bodyPickerFloat_NumberPicker.setWrapSelectorWheel(true);


            TextView confirmBody = bodyDialog.findViewById(R.id.confirmSecondCircumference);
            confirmBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    String dateBody = sdf.format(new Date());

                    double bodyValue_int = bodyPickerInt_NumberPicker.getValue();
                    double bodyValue_float = bodyPickerFloat_NumberPicker.getValue() * 0.1;
                    double bodyValue = bodyValue_int + bodyValue_float;

                    saveBody(bodyPosition_int, dateBody, String.valueOf(bodyValue));
                    bodyDialog.dismiss();
                }
            });

            TextView cancelBody = bodyDialog.findViewById(R.id.cancelSecondCircumference);
            cancelBody.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bodyDialog.dismiss();
                }
            });
        }

        else {
            Intent BodyGraphActivity = new Intent(BodyActivity.this, BodyGraphActivity.class);

            BodyGraphActivity.putExtra("number_bodyPart", 1);
            BodyGraphActivity.putExtra("bodyPart", bodyPart_String);

            BodyGraphActivity.putExtra("bodyPosition_int", bodyPosition_int);

            BodyGraphActivity.putExtra("unit_Value", getResources().getString(R.string.unitSize));

            BodyGraphActivity.putExtra("MinValue_picker", MinValue_picker);
            BodyGraphActivity.putExtra("MaxValue_picker", MaxValue_picker);

            startActivity(BodyGraphActivity);
        }
    }

    public void saveBody(int bodyPosition_int, String dateBody_String, String valueBody_String) {
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("body.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] bodyPart_ArrayString = stringBuilder.toString().split("/b1");

            bodyPart_ArrayString[bodyPosition_int] = dateBody_String + "/b3" + valueBody_String + "/b2";


            String goodString = function.join("/b1", bodyPart_ArrayString);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("body.txt", MODE_PRIVATE);
            fileOutputStream.write(goodString.getBytes());
            fileOutputStream.close();

            writeBodyInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setNeck(final String[] neck_ArrayString) {
        writeBody(neck_ArrayString, (TextView) findViewById(R.id.dateNeck_TV), (TextView) findViewById(R.id.neckValue_TV), (TextView) findViewById(R.id.unitNeck_TV));
        LinearLayout neck_LinearLayout = findViewById(R.id.neck_LinearLayout);
        neck_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBody(3, neck_ArrayString, getResources().getString(R.string.neck), 20, 60);
            }
        });
    }

    public void setChest(final String[] chest_ArrayString) {
        writeBody(chest_ArrayString, (TextView) findViewById(R.id.dateChest_TV), (TextView) findViewById(R.id.chestValue_TV), (TextView) findViewById(R.id.unitChest_TV));
        LinearLayout chest_LinearLayout = findViewById(R.id.chest_LinearLayout);
        chest_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBody(4, chest_ArrayString, getResources().getString(R.string.chest), 20, 200);
            }
        });
    }

    public void setWaist(final String[] waist_ArrayString) {
        writeBody(waist_ArrayString, (TextView) findViewById(R.id.dateWaist_TV), (TextView) findViewById(R.id.waistValue_TV), (TextView) findViewById(R.id.unitWaist_TV));
        LinearLayout waist_LinearLayout = findViewById(R.id.waist_LinearLayout);
        waist_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBody(5, waist_ArrayString, getResources().getString(R.string.waist), 20, 200);
            }
        });
    }

    public void setHip(final String[] hip_ArrayString) {
        writeBody(hip_ArrayString, (TextView) findViewById(R.id.dateHip_TV), (TextView) findViewById(R.id.hipValue_TV), (TextView) findViewById(R.id.unitHip_TV));
        LinearLayout hip_LinearLayout = findViewById(R.id.hip_LinearLayout);
        hip_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickBody(6, hip_ArrayString, getResources().getString(R.string.hip), 20, 200);
            }
        });
    }



    public void writeBody_leftright(String[] body_ArrayString, TextView dateBody_TextView,
                                    TextView leftBody_TextView, TextView unitLeftBody_TextView, TextView sideLeftBody_TextView,
                                    TextView rightBody_TextView, TextView unitRightBody_TextView, TextView sideRightBody_TextView) {

        String[] lastValueBody_ArrayString = body_ArrayString[body_ArrayString.length-1].split("/b3");
        String[] valueLeftRightBody_String = lastValueBody_ArrayString[1].split("/b4");
        String valueLeftBody_String = valueLeftRightBody_String[0];
        String valueRightBody_String = valueLeftRightBody_String[1];

        if (valueLeftBody_String.equals("0")){
            dateBody_TextView.setTextAppearance(R.style.default_date);
            dateBody_TextView.setText(R.string.default_date);


            leftBody_TextView.setVisibility(View.INVISIBLE);
            unitLeftBody_TextView.setVisibility(View.INVISIBLE);
            sideLeftBody_TextView.setVisibility(View.INVISIBLE);

            rightBody_TextView.setVisibility(View.INVISIBLE);
            unitRightBody_TextView.setVisibility(View.INVISIBLE);
            sideRightBody_TextView.setVisibility(View.INVISIBLE);
        }
        else {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                Date date = sdf.parse(lastValueBody_ArrayString[0]);
                SimpleDateFormat rdf = new SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE);
                String dateBody_String = rdf.format(date);

                dateBody_TextView.setTextAppearance(R.style.subtitlesText_style);
                dateBody_TextView.setText(dateBody_String);
            } catch (ParseException e) {
                e.printStackTrace();
            }


            leftBody_TextView.setVisibility(View.VISIBLE);
            leftBody_TextView.setText(valueLeftBody_String);
            unitLeftBody_TextView.setVisibility(View.VISIBLE);
            sideLeftBody_TextView.setVisibility(View.VISIBLE);

            rightBody_TextView.setVisibility(View.VISIBLE);
            rightBody_TextView.setText(valueRightBody_String);
            unitRightBody_TextView.setVisibility(View.VISIBLE);
            sideRightBody_TextView.setVisibility(View.VISIBLE);
        }
    }

    public void onClick2Body(final int bodyPosition_int, String[] body_ArrayString, final String bodyPart, final String leftBodyPart_String, final String rightBodyPart_String, final Integer MinValue_picker, final Integer MaxValue_picker) {
        String[] lastValueBody_ArrayString = body_ArrayString[body_ArrayString.length-1].split("/b3");
        String dateBody_String = lastValueBody_ArrayString[0];
        String[] valueLeftRightBody_String = lastValueBody_ArrayString[1].split("/b4");
        String valueLeftBody_String = valueLeftRightBody_String[0];
        String valueRightBody_String = valueLeftRightBody_String[1];
        if (valueLeftBody_String.equals("0")) {
            left_bodyDialog.setContentView(R.layout.dialog_body_first_circumference);
            left_bodyDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
            left_bodyDialog.show();

            TextView bodyPart_Circumference = left_bodyDialog.findViewById(R.id.bodyPart_Circumference);
            bodyPart_Circumference.setText(leftBodyPart_String);

            final NumberPicker leftBodyPickerInt_NumberPicker = left_bodyDialog.findViewById(R.id.first_circumferencePicker_int);
            leftBodyPickerInt_NumberPicker.setMinValue(MinValue_picker);
            leftBodyPickerInt_NumberPicker.setMaxValue(MaxValue_picker);
            leftBodyPickerInt_NumberPicker.setWrapSelectorWheel(true);

            final NumberPicker leftBodyPickerFloat_NumberPicker = left_bodyDialog.findViewById(R.id.first_circumferencePicker_float);
            leftBodyPickerFloat_NumberPicker.setMinValue(0);
            leftBodyPickerFloat_NumberPicker.setMaxValue(9);
            leftBodyPickerFloat_NumberPicker.setWrapSelectorWheel(true);

            TextView next_bodyPart = left_bodyDialog.findViewById(R.id.confirmFirstCircumference);
            next_bodyPart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                left_bodyDialog.dismiss();
                right_bodyDialog.setContentView(R.layout.dialog_body_second_circumference);
                right_bodyDialog.getWindow().setLayout((int) (getResources().getDisplayMetrics().widthPixels*0.90), WindowManager.LayoutParams.WRAP_CONTENT);
                right_bodyDialog.show();

                TextView bodyPart_Circumference = right_bodyDialog.findViewById(R.id.bodyPart_Circumference);
                bodyPart_Circumference.setText(rightBodyPart_String);


                final NumberPicker rightBodyPickerInt_NumberPicker = right_bodyDialog.findViewById(R.id.second_circumferencePicker_int);
                rightBodyPickerInt_NumberPicker.setMinValue(MinValue_picker);
                rightBodyPickerInt_NumberPicker.setMaxValue(MaxValue_picker);
                rightBodyPickerInt_NumberPicker.setWrapSelectorWheel(true);

                final NumberPicker rightBodyPickerFloat_NumberPicker = right_bodyDialog.findViewById(R.id.second_circumferencePicker_float);
                rightBodyPickerFloat_NumberPicker.setMinValue(0);
                rightBodyPickerFloat_NumberPicker.setMaxValue(9);
                rightBodyPickerFloat_NumberPicker.setWrapSelectorWheel(true);


                TextView confirmBody = right_bodyDialog.findViewById(R.id.confirmSecondCircumference);
                confirmBody.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                        String dateBody = sdf.format(new Date());

                        double left_bodyValue_int = leftBodyPickerInt_NumberPicker.getValue();
                        double left_bodyValue_float = leftBodyPickerFloat_NumberPicker.getValue() * 0.1;
                        double left_bodyValue = left_bodyValue_int + left_bodyValue_float;

                        double right_bodyValue_int = rightBodyPickerInt_NumberPicker.getValue();
                        double right_bodyValue_float = rightBodyPickerFloat_NumberPicker.getValue() * 0.1;
                        double right_bodyValue = right_bodyValue_int + right_bodyValue_float;

                        saveBody2(bodyPosition_int , dateBody, String.valueOf(left_bodyValue), String.valueOf(right_bodyValue));
                        right_bodyDialog.dismiss();
                    }
                });

                TextView cancelSecondCircumference = right_bodyDialog.findViewById(R.id.cancelSecondCircumference);
                    cancelSecondCircumference.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        right_bodyDialog.dismiss();
                    }
                });
                }
            });

            TextView cancelFirstCircumference = left_bodyDialog.findViewById(R.id.cancelFirstCircumference);
            cancelFirstCircumference.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    left_bodyDialog.dismiss();
                }
            });
        }

        else {
            Intent BodyGraphActivity = new Intent(BodyActivity.this, BodyGraphActivity.class);

            BodyGraphActivity.putExtra("number_bodyPart", 2);
            BodyGraphActivity.putExtra("bodyPart", bodyPart);
            BodyGraphActivity.putExtra("body_leftPart", leftBodyPart_String);
            BodyGraphActivity.putExtra("body_rightPart", rightBodyPart_String);

            BodyGraphActivity.putExtra("bodyPosition_int", bodyPosition_int);

            BodyGraphActivity.putExtra("unit_Value", getResources().getString(R.string.unitSize));

            BodyGraphActivity.putExtra("MinValue_picker", MinValue_picker);
            BodyGraphActivity.putExtra("MaxValue_picker", MaxValue_picker);

            startActivity(BodyGraphActivity);
        }
    }

    public void saveBody2(int bodyPosition_int, String dateBody_String, String valueLeftBody_String, String valueRightBody_String) {
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("body.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] bodyPart_ArrayString = stringBuilder.toString().split("/b1");

            bodyPart_ArrayString[bodyPosition_int] = dateBody_String + "/b3" + valueLeftBody_String + "/b4" + valueRightBody_String + "/b2";


            String goodString = function.join("/b1", bodyPart_ArrayString);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("body.txt", MODE_PRIVATE);
            fileOutputStream.write(goodString.getBytes());
            fileOutputStream.close();

            writeBodyInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setArm(final String[] arm_ArrayString) {
        writeBody_leftright(arm_ArrayString, (TextView) findViewById(R.id.dateArm),
                (TextView) findViewById(R.id.left_arm), (TextView) findViewById(R.id.left_unitArm), (TextView) findViewById(R.id.left_arm_side),
                (TextView) findViewById(R.id.right_arm), (TextView) findViewById(R.id.right_unitArm), (TextView) findViewById(R.id.right_arm_side));
        LinearLayout arm_LinearLayout = findViewById(R.id.arm_LinearLayout);
        arm_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick2Body(7, arm_ArrayString, getResources().getString(R.string.arm), getResources().getString(R.string.leftArm), getResources().getString(R.string.rightArm),10, 80);
            }
        });
    }

    public void setForearm(final String[] forearm_ArrayString) {
        writeBody_leftright(forearm_ArrayString, (TextView) findViewById(R.id.dateForearm),
                (TextView) findViewById(R.id.left_forearm), (TextView) findViewById(R.id.left_unitForearm), (TextView) findViewById(R.id.left_forearm_side),
                (TextView) findViewById(R.id.right_forearm), (TextView) findViewById(R.id.right_unitForearm), (TextView) findViewById(R.id.right_forearm_side));
        LinearLayout forearm_LinearLayout = findViewById(R.id.forearm_LinearLayout);
        forearm_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick2Body(8, forearm_ArrayString, getResources().getString(R.string.forearm), getResources().getString(R.string.leftForearm), getResources().getString(R.string.rightForearm), 10, 80);
            }
        });
    }

    public void setThigh(final String[] thigh_ArrayString) {
        writeBody_leftright(thigh_ArrayString, (TextView) findViewById(R.id.dateThigh),
                (TextView) findViewById(R.id.left_thigh), (TextView) findViewById(R.id.left_unitThigh), (TextView) findViewById(R.id.left_thigh_side),
                (TextView) findViewById(R.id.right_thigh), (TextView) findViewById(R.id.right_unitThigh), (TextView) findViewById(R.id.right_thigh_side));
        LinearLayout thigh_LinearLayout = findViewById(R.id.thigh_LinearLayout);
        thigh_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick2Body(9, thigh_ArrayString, getResources().getString(R.string.thigh), getResources().getString(R.string.leftThigh), getResources().getString(R.string.rightThigh), 10, 80);
            }
        });
    }

    public void setCalf(final String[] calf_ArrayString) {
        writeBody_leftright(calf_ArrayString, (TextView) findViewById(R.id.dateCalf),
                (TextView) findViewById(R.id.left_calf), (TextView) findViewById(R.id.left_unitCalf), (TextView) findViewById(R.id.left_calf_side),
                (TextView) findViewById(R.id.right_calf), (TextView) findViewById(R.id.right_unitCalf), (TextView) findViewById(R.id.right_calf_side));
        LinearLayout calf_LinearLayout = findViewById(R.id.calf_LinearLayout);
        calf_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClick2Body(10, calf_ArrayString, getResources().getString(R.string.calf), getResources().getString(R.string.leftCalf), getResources().getString(R.string.rightCalf), 10, 80);
            }
        });
    }
}