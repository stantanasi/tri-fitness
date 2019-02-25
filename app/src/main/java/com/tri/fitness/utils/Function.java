package com.tri.fitness.utils;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.activities.HomeActivity;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import static android.support.v4.content.ContextCompat.checkSelfPermission;

public class Function {

    private final static String DOMAIN_NAME = "http://192.168.0.41";

    public final static int MY_PERMISSIONS_REQUEST_WRITE_STORAGE = 2;
    public final static int MY_PERMISSIONS_REQUEST_READ_STORAGE = 3;

    public final static String TYPE_CBZ = "cbz";
    public final static String TYPE_CBR = "cbr";
    public final static String TYPE_PDF = "pdf";

    private Context context;

    public Function(Context context) {
        this.context = context;
    }



    public String getStringFromFile(String filePath) throws IOException {
        FileInputStream fileInputStream = context.openFileInput(filePath);
        InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
        StringBuilder stringBuilder = new StringBuilder();
        int content;
        while ((content=f.read()) != -1) {
            stringBuilder.append((char) content);
        }
        return stringBuilder.toString();
    }

    public void saveStringInFile(String filePath, String s) throws IOException {
        FileOutputStream fileOutputStream = context.openFileOutput(filePath, Context.MODE_PRIVATE);
        fileOutputStream.write(s.getBytes());
        fileOutputStream.close();

    }

    public Bitmap getImageFromFile(String imagePath, Bitmap default_Bitmap) {
        try {
            FileInputStream fileInputStream = context.openFileInput(imagePath);
            return BitmapFactory.decodeStream(fileInputStream);
        }
        catch (Exception e) {
            e.printStackTrace();
            return default_Bitmap;
        }
    }

    public String join(String delimiter, String... elements) {
        String string = "";
        for (String item : elements) {
            string = string + item + delimiter ;
        }
        return string;
    }

    public long daysBetween(Date date1, Date date2) {
        long diff = date1.getTime() - date2.getTime();
        return diff / (1000 * 60 * 60 * 24);
    }

    public class GetJSONArray extends AsyncTask<String,Void,JSONArray> {

        protected void onPreExecute() {
            //display progress dialog.
        }
        protected JSONArray doInBackground(String... params) {
            String url = params[0];
            String variables = params[1];
            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(DOMAIN_NAME + url + variables).openConnection();
                conn.setReadTimeout(1000 /* milliseconds */);
                conn.setConnectTimeout(1500 /* milliseconds */);
                conn.setRequestMethod("GET");
                // Starts the query
                InputStream is = conn.getInputStream();
                // Read the InputStream and save it in a string
                BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line).append('\n');
                }
                is.close();
                return new JSONArray(response.toString());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        protected void onPostExecute(JSONArray result) {
            // dismiss progress dialog and update ui
        }
    }

    public class ExecuteJSON extends AsyncTask<String,Void,Boolean> {

        protected void onPreExecute() {
            //display progress dialog.
        }
        protected Boolean doInBackground(String... params) {
            String url = params[0];
            String variables = params[1];

            try {
                HttpURLConnection conn = (HttpURLConnection) new URL(DOMAIN_NAME + url + variables).openConnection();
                conn.setRequestMethod("GET");
                conn.setReadTimeout(10000 /* milliseconds */);
                conn.setConnectTimeout(15000 /* milliseconds */);
                conn.setDoInput(true);
                // Starts the query
                conn.getInputStream();
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
        protected void onPostExecute(Boolean result) {
            // dismiss progress dialog and update ui
        }
    }


    public void showDatePickerDialog(final EditText editText, final String pattern) {
        Calendar newCalendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                newDate.set(year, month, dayOfMonth);
                DateFormat dateFormat = new SimpleDateFormat(pattern);
                editText.setText(dateFormat.format(newDate.getTime()));
            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));

        datePickerDialog.show();
    }

    public void showRadioGroupDialog(double dialog_width, boolean showCancelButton, boolean showConfirmButton, final TextView[] textView, final String... stringArray) {
        final Dialog dialog = new Dialog(context);
        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setContentView(R.layout.dialog_radio_group);
        dialog.getWindow().setLayout((int) (context.getResources().getDisplayMetrics().widthPixels*dialog_width), WindowManager.LayoutParams.WRAP_CONTENT);
        dialog.show();

        if (!showCancelButton) dialog.findViewById(R.id.cancel_Button).setVisibility(View.GONE);
        else {
            dialog.findViewById(R.id.cancel_Button).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }
        if (!showConfirmButton) dialog.findViewById(R.id.confirm_Button).setVisibility(View.GONE);

        RadioGroup radioGroup = dialog.findViewById(R.id.dialog_radioGroup);
        for (int i=0; i<stringArray.length; i++) {
            RadioButton radioButton = new RadioButton(context);
            radioButton.setId(i);
            radioButton.setText(stringArray[i]);
            radioButton.setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            radioGroup.addView(radioButton);
        }

        String valueSave = textView[0].getText().toString().trim();
        for (int i=0; i<stringArray.length; i++) {
            if (valueSave.equals(stringArray[i])) radioGroup.check(i);
        }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int id) {
                for (TextView aTextView : textView) {
                    aTextView.setText(stringArray[id]);
                }
                dialog.dismiss();
            }
        });
    }

    public String convertDateFormat(String Date, String pattern_initial, String pattern_final) {
        try {
            SimpleDateFormat format_initial = new SimpleDateFormat(pattern_initial, Locale.FRANCE);
            Date date = format_initial.parse(Date);

            SimpleDateFormat format_final = new SimpleDateFormat(pattern_final, Locale.FRANCE);
            Date = format_final.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return Date;
    }

    public String calculateAge(String birthday, String pattern) {
        String birthday_yyyyMMdd = convertDateFormat(birthday, pattern, "yyyyMMdd");
        int birthday_int = Integer.parseInt(birthday_yyyyMMdd);

        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        int today = Integer.parseInt(dateFormat.format(new Date()));

        return String.valueOf((today-birthday_int) / 10000);
    }

    public boolean isNetworkAvailable() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        return haveConnectedWifi || haveConnectedMobile;
    }

    public boolean isReadStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(((AppCompatActivity)context), new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_READ_STORAGE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    public boolean isWriteStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(((HomeActivity)context), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MY_PERMISSIONS_REQUEST_WRITE_STORAGE);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }


    public void incrementEditText (EditText editText) {
        {
            if (editText.getText().toString().trim().equals("")) editText.setText("1");
            else {
                int i = Integer.parseInt(editText.getText().toString().trim());
                i = i + 1;
                editText.setText(String.valueOf(i));
            }
        }
    }

    public int getProgress(String i, String j) {
        if (i.equals("-")) i="0";
        if (j.equals("-")) j="0";

        double i_double = Double.parseDouble(i);
        double j_double = Double.parseDouble(j);

        try {
            return (int) ((i_double / j_double)  * 100);
        }
        catch (Exception e) {
            return 0;
        }
    }

    public Bitmap getThumbnail(File file) {
        try {
            File cacheDir = context.getCacheDir();
            File f = new File(cacheDir, file.getName());
            FileInputStream fis = new FileInputStream(f);
            return BitmapFactory.decodeStream(fis);
        }
        catch (IOException e){
            e.printStackTrace();

            switch (getFileExtension(file)) {
                case TYPE_CBZ:
                    return getThumbnailCBA(file);
                case TYPE_CBR:
                    return getThumbnailCBA(file);
                case TYPE_PDF:
                    return null;
                default:
                    return null;
            }
        }
    }

    private Bitmap getThumbnailCBA(File file) {
        Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.side_nav_bar);
        try {
            String filePath = file.getAbsolutePath();

            ZipFile zipFile = new ZipFile(filePath);
            Enumeration<? extends ZipEntry> entries = zipFile.entries();
            ArrayList<String> arrayList = new ArrayList<>();

            while(entries.hasMoreElements()){
                ZipEntry entry = entries.nextElement();
                arrayList.add(entry.getName());
            }

            ZipEntry entry = zipFile.getEntry(arrayList.get(0));
            InputStream in = zipFile.getInputStream(entry);

            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inSampleSize = 9;
            bitmap = BitmapFactory.decodeStream(in, null, options);
            saveThumbnail(file.getName(), bitmap);
            in.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private void saveThumbnail(String bookName, Bitmap thumbnail) {
        File cacheDir = context.getCacheDir();
        File f = new File(cacheDir, bookName);

        try {
            FileOutputStream out = new FileOutputStream(f);
            thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, out);
            out.flush();
            out.close();
        }
        catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(context, "Une erreur est survenue", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean checkFileType(File file, String type) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        String fileExtension = (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);

        return fileExtension.equals(type);
    }


    public String getFileExtension(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? "" : fileName.substring(dotIndex + 1);
    }

    /* Checks if external storage is available for read and write */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) || Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }
}
