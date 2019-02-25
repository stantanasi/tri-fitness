package com.tri.fitness.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.R;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.DataPointInterface;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.OnDataPointTapListener;
import com.jjoe64.graphview.series.Series;
import com.tri.fitness.utils.Function;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

public class BodyGraphActivity extends AppCompatActivity {

    Function function;

    String[] bodyPart_ArrayString;

    Integer number_bodyPart, MinValuePicker, MaxValue_picker, bodyPosition_int;
    String bodyPart, left_bodyPart, right_bodyPart, unit_Value;
    LineGraphSeries<DataPoint> series;

    TextView bodyPart_TextView, lastValue_Graph, unit_actualValue_Graph, deltaValue_Graph, unit_deltaValue_Graph;
    GraphView bodyPart_GraphView;
    Spinner bodyPart_Spinner;
    Dialog bodyDialog, left_bodyDialog, right_bodyDialog;

    FloatingActionButton addValue;
    Calendar calendar;
    Date today;

    SharedPreferences userBody;
    SharedPreferences.Editor edit_userBody;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_body_graph);

        function = new Function(BodyGraphActivity.this);

        setActivity();
        getInfo();
        writeInfo();
        setAddValue();
    }

    public void setActivity() {
        bodyPart = Objects.requireNonNull(getIntent().getExtras()).getString("bodyPart", "");
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.body);
        getSupportActionBar().setSubtitle(bodyPart);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        userBody = getSharedPreferences("userBody", Context.MODE_PRIVATE);
        edit_userBody = userBody.edit();
        edit_userBody.apply();


        calendar = Calendar.getInstance();
        today = new Date();

        bodyDialog = new Dialog(this);
        Objects.requireNonNull(bodyDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        left_bodyDialog = new Dialog(this);
        Objects.requireNonNull(left_bodyDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        right_bodyDialog = new Dialog(this);
        Objects.requireNonNull(right_bodyDialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        bodyPart_TextView = findViewById(R.id.bodyPart_TextView);
        bodyPart_Spinner = findViewById(R.id.bodyPart_Spinner);

        bodyPart_GraphView = findViewById(R.id.bodyPart_GraphView);
        bodyPart_GraphView.getGridLabelRenderer().setHorizontalLabelsVisible(false);

        bodyPart_GraphView.getViewport().setXAxisBoundsManual(true);
        bodyPart_GraphView.getViewport().setMinX(0);
        //bodyPart_GraphView.getViewport().setYAxisBoundsManual(true);
        //bodyPart_GraphView.getViewport().setMinY(0);

        lastValue_Graph = findViewById(R.id.lastValue_Graph);
        unit_actualValue_Graph = findViewById(R.id.unit_actualValue_Graph);

        deltaValue_Graph = findViewById(R.id.deltaValue_Graph);
        unit_deltaValue_Graph = findViewById(R.id.unit_deltaValue_Graph);

        addValue = findViewById(R.id.addValue);
    }

    public void getInfo() {
        number_bodyPart = Objects.requireNonNull(getIntent().getExtras()).getInt("number_bodyPart", 1);
        bodyPosition_int = getIntent().getIntExtra("bodyPosition_int", -1);
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("body.txt");
            InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
            StringBuilder stringBuilder = new StringBuilder();
            int content;
            while ((content=f.read()) != -1) {
                stringBuilder.append((char) content);
            }

            String[] body_ArrayString = stringBuilder.toString().split("/b1");

            bodyPart_ArrayString = body_ArrayString[bodyPosition_int].split("/b2");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        if (number_bodyPart == 1) {
            unit_Value = getIntent().getExtras().getString("unit_Value", "");

            MinValuePicker = getIntent().getExtras().getInt("MinValue_picker", 0);
            MaxValue_picker = getIntent().getExtras().getInt("MaxValue_picker", 0);
        }

        else if (number_bodyPart == 2) {
            left_bodyPart = getIntent().getExtras().getString("body_leftPart", "");
            right_bodyPart = getIntent().getExtras().getString("body_rightPart", "");

            unit_Value = getIntent().getExtras().getString("unit_Value", "");

            MinValuePicker = getIntent().getExtras().getInt("MinValue_picker", 0);
            MaxValue_picker = getIntent().getExtras().getInt("MaxValue_picker", 0);
        }
    }



    public void writeInfo() {
        unit_actualValue_Graph.setText(unit_Value);
        unit_deltaValue_Graph.setText(unit_Value);

        if (number_bodyPart == 1) {
            bodyPart_TextView.setVisibility(View.VISIBLE);
            bodyPart_Spinner.setVisibility(View.GONE);
            bodyPart_TextView.setText(bodyPart);

            series = new LineGraphSeries<>();

            String[] first = bodyPart_ArrayString[0].split("/b3");
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date2 = null;
            try {
                date2 = sdf.parse(first[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            for (String Point_GraphView : bodyPart_ArrayString) {
                String xyValue[] = Point_GraphView.split("/b3");
                Date date1 = null;
                try {
                    date1 = sdf.parse(xyValue[0]);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                series.appendData(new DataPoint(function.daysBetween(date1, date2), Double.valueOf(xyValue[1])), true, 1000);
                bodyPart_GraphView.getViewport().setMaxX(function.daysBetween(date1, date2));
            }
            bodyPart_GraphView.addSeries(series);
            series.setOnDataPointTapListener(new OnDataPointTapListener() {
                @Override
                public void onTap(Series series, DataPointInterface dataPoint) {
                    Toast.makeText(BodyGraphActivity.this, dataPoint.getX() + " / " + dataPoint.getY(), Toast.LENGTH_LONG).show();
                }
            });
            series.setDrawDataPoints(true);
            series.setDrawBackground(true);
            series.setBackgroundColor(Color.argb(130, 85,175,250));

            String[] lastValueBody_ArrayString = bodyPart_ArrayString[bodyPart_ArrayString.length-1].split("/b3");
            lastValue_Graph.setText(String.valueOf(lastValueBody_ArrayString[1]));

            String[] firstValueBody_ArrayString = bodyPart_ArrayString[0].split("/b3");
            double delta_bodyValue_double = Double.valueOf(lastValueBody_ArrayString[1]) - Double.valueOf(firstValueBody_ArrayString[1]);
            if (delta_bodyValue_double > 0) {
                deltaValue_Graph.setText(String.valueOf("+" + delta_bodyValue_double));
            }
            else {
                deltaValue_Graph.setText(String.valueOf(delta_bodyValue_double));
            }
        }



        else if (number_bodyPart == 2) {
            bodyPart_TextView.setVisibility(View.GONE);
            bodyPart_Spinner.setVisibility(View.VISIBLE);


            String[] firstValueBody_ArrayString = bodyPart_ArrayString[0].split("/b3");
            final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            Date date2 = null;
            try {
                date2 = sdf.parse(firstValueBody_ArrayString[0]);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            String[] firstValueLeftRightBody_String = firstValueBody_ArrayString[1].split("/b4");
            final String firstLeftValue_String = firstValueLeftRightBody_String[0];
            final String firstRightValue_String = firstValueLeftRightBody_String[1];


            String[] lastValueBody_ArrayString = bodyPart_ArrayString[bodyPart_ArrayString.length-1].split("/b3");
            String[] lastValueLeftRightBody_String = lastValueBody_ArrayString[1].split("/b4");
            final String lastLeftValue_String = lastValueLeftRightBody_String[0];
            final String lastRightValue_String = lastValueLeftRightBody_String[1];



            final String[] list_bodyPart = {left_bodyPart, right_bodyPart};
            ArrayAdapter<String> arrayAdapter =  new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, list_bodyPart);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            bodyPart_Spinner.setAdapter(arrayAdapter);

            final Date finalDate2 = date2;
            bodyPart_Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    if (list_bodyPart[position].equals(left_bodyPart)) {
                        series = new LineGraphSeries<>();
                        for (String Point_GraphView : bodyPart_ArrayString) {
                            String[] ValueBody_ArrayString = Point_GraphView.split("/b3");
                            String dateBody_String = ValueBody_ArrayString[0];

                            Date date1 = null;
                            try {
                                date1 = sdf.parse(dateBody_String);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            String[] valueLeftRightBody_String = ValueBody_ArrayString[1].split("/b4");
                            String valueLeftBody_String = valueLeftRightBody_String[0];
                            String valueRightBody_String = valueLeftRightBody_String[1];
                            series.appendData(new DataPoint(function.daysBetween(date1, finalDate2), Double.valueOf(valueLeftBody_String)), true, 1000);
                            bodyPart_GraphView.getViewport().setMaxX(function.daysBetween(date1, finalDate2));
                        }
                        bodyPart_GraphView.removeAllSeries();
                        bodyPart_GraphView.addSeries(series);
                        series.setOnDataPointTapListener(new OnDataPointTapListener() {
                            @Override
                            public void onTap(Series series, DataPointInterface dataPoint) {
                                Toast.makeText(BodyGraphActivity.this, dataPoint.getX() + " / " + dataPoint.getY(), Toast.LENGTH_LONG).show();
                            }
                        });
                        series.setDrawDataPoints(true);
                        series.setDrawBackground(true);
                        series.setBackgroundColor(Color.argb(130, 85,175,250));

                        lastValue_Graph.setText(lastLeftValue_String);

                        double delta_left_bodyValue_double = Double.valueOf(lastLeftValue_String) - Double.valueOf(firstLeftValue_String);
                        if (delta_left_bodyValue_double > 0) {
                            deltaValue_Graph.setText(String.valueOf("+" + delta_left_bodyValue_double));
                        }
                        else {
                            deltaValue_Graph.setText(String.valueOf(delta_left_bodyValue_double));
                        }
                    }
                    
                    else if (list_bodyPart[position].equals(right_bodyPart)) {
                        series = new LineGraphSeries<>();
                        for (String Point_GraphView : bodyPart_ArrayString) {
                            String[] lastValueBody_ArrayString = Point_GraphView.split("/b3");
                            String dateBody_String = lastValueBody_ArrayString[0];

                            Date date1 = null;
                            try {
                                date1 = sdf.parse(dateBody_String);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }


                            String[] valueLeftRightBody_String = lastValueBody_ArrayString[1].split("/b4");
                            String valueLeftBody_String = valueLeftRightBody_String[0];
                            String valueRightBody_String = valueLeftRightBody_String[1];
                            series.appendData(new DataPoint(function.daysBetween(date1, finalDate2), Double.valueOf(valueRightBody_String)), true, 1000);
                            bodyPart_GraphView.getViewport().setMaxX(function.daysBetween(date1, finalDate2));
                        }
                        bodyPart_GraphView.removeAllSeries();
                        bodyPart_GraphView.addSeries(series);
                        series.setOnDataPointTapListener(new OnDataPointTapListener() {
                            @Override
                            public void onTap(Series series, DataPointInterface dataPoint) {
                                Toast.makeText(BodyGraphActivity.this, dataPoint.getX() + " / " + dataPoint.getY(), Toast.LENGTH_LONG).show();
                            }
                        });
                        series.setDrawDataPoints(true);
                        series.setDrawBackground(true);
                        series.setBackgroundColor(Color.argb(130, 85,175,250));

                        lastValue_Graph.setText(lastRightValue_String);

                        double delta_right_bodyValue_double = Double.valueOf(lastRightValue_String) - Double.valueOf(firstRightValue_String);
                        if (delta_right_bodyValue_double > 0) {
                            deltaValue_Graph.setText(String.valueOf("+" + delta_right_bodyValue_double));
                        }
                        else {
                            deltaValue_Graph.setText(String.valueOf(delta_right_bodyValue_double));
                        }
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    series = new LineGraphSeries<>();
                    for (String Point_GraphView : bodyPart_ArrayString) {
                        String[] ValueBody_ArrayString = Point_GraphView.split("/b3");
                        String dateBody_String = ValueBody_ArrayString[0];

                        Date date1 = null;
                        try {
                            date1 = sdf.parse(dateBody_String);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }


                        String[] valueLeftRightBody_String = ValueBody_ArrayString[1].split("/b4");
                        String valueLeftBody_String = valueLeftRightBody_String[0];
                        String valueRightBody_String = valueLeftRightBody_String[1];
                        series.appendData(new DataPoint(function.daysBetween(date1, finalDate2), Double.valueOf(valueLeftBody_String)), true, 1000);
                        bodyPart_GraphView.getViewport().setMaxX(function.daysBetween(date1, finalDate2));
                    }
                    bodyPart_GraphView.removeAllSeries();
                    bodyPart_GraphView.addSeries(series);
                    series.setOnDataPointTapListener(new OnDataPointTapListener() {
                        @Override
                        public void onTap(Series series, DataPointInterface dataPoint) {
                            Toast.makeText(BodyGraphActivity.this, dataPoint.getX() + " / " + dataPoint.getY(), Toast.LENGTH_LONG).show();
                        }
                    });
                    series.setDrawDataPoints(true);
                    series.setDrawBackground(true);
                    series.setBackgroundColor(Color.argb(130, 85,175,250));

                    lastValue_Graph.setText(lastLeftValue_String);

                    double delta_left_bodyValue_double = Double.valueOf(lastLeftValue_String) - Double.valueOf(firstLeftValue_String);
                    if (delta_left_bodyValue_double > 0) {
                        deltaValue_Graph.setText(String.valueOf("+" + delta_left_bodyValue_double));
                    }
                    else {
                        deltaValue_Graph.setText(String.valueOf(delta_left_bodyValue_double));
                    }
                }
            });
        }
    }

    public void add_bodyValue(Integer MinValue_picker, Integer MaxValue_picker) {
        bodyDialog.setContentView(R.layout.dialog_body_second_circumference);
        bodyDialog.show();

        TextView bodyPart_Circumference = bodyDialog.findViewById(R.id.bodyPart_Circumference);
        bodyPart_Circumference.setText(bodyPart);

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

            bodyPart_ArrayString[bodyPosition_int] = bodyPart_ArrayString[bodyPosition_int] + dateBody_String + "/b3" + valueBody_String + "/b2";

            String goodString = function.join("/b1", bodyPart_ArrayString);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("body.txt", MODE_PRIVATE);
            fileOutputStream.write(goodString.getBytes());
            fileOutputStream.close();

            getInfo();
            writeInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void add_2bodyValue(final Integer MinValue_picker, final Integer MaxValue_picker) {
        left_bodyDialog.setContentView(R.layout.dialog_body_first_circumference);
        left_bodyDialog.show();

        TextView bodyPart_Circumference = left_bodyDialog.findViewById(R.id.bodyPart_Circumference);
        bodyPart_Circumference.setText(left_bodyPart);

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
                right_bodyDialog.show();

                TextView bodyPart_Circumference = right_bodyDialog.findViewById(R.id.bodyPart_Circumference);
                bodyPart_Circumference.setText(right_bodyPart);


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

            bodyPart_ArrayString[bodyPosition_int] = bodyPart_ArrayString[bodyPosition_int] + dateBody_String + "/b3" + valueLeftBody_String + "/b4" + valueRightBody_String + "/b2";


            String goodString = function.join("/b1", bodyPart_ArrayString);

            FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("body.txt", MODE_PRIVATE);
            fileOutputStream.write(goodString.getBytes());
            fileOutputStream.close();

            getInfo();
            writeInfo();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setAddValue() {
        addValue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (number_bodyPart == 1) {
                    add_bodyValue(MinValuePicker, MaxValue_picker);
                }

                else if (number_bodyPart == 2) {
                    add_2bodyValue(MinValuePicker, MaxValue_picker);
                }
            }
        });
    }
}
