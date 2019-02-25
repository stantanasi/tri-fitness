package com.tri.fitness.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tri.fitness.R;
import com.tri.fitness.utils.SessionManager;

import java.io.FileInputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    LinearLayout startDailyExercising_LinearLayout;
    TextView nav_name_TextView, nav_pseudo_TextView;

    CircleImageView nav_profilePic_CircleImageView;

    NavigationView navigationView = null;
    Toolbar toolbar = null;
    View headerView;

    private SessionManager sessionManager;


    @Override
    protected void onStart() {
        super.onStart();

        writeInfo();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        sessionManager = new SessionManager(this);

        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        nav_name_TextView = headerView.findViewById(R.id.nav_name_TextView);
        nav_pseudo_TextView = headerView.findViewById(R.id.nav_pseudo_TextView);
        nav_profilePic_CircleImageView = headerView.findViewById(R.id.nav_profilePic_CircleImageView);

        navigationView.getMenu().getItem(0).setChecked(false);

        writeInfo();
        startDailyExercising();
    }

    @Override
    protected void onRestart() {
        super.onRestart();

        for (int i = 0; i < navigationView.getMenu().size(); i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();

            finish();
            moveTaskToBack(true);
        }
    }


    public void writeInfo() {
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("profilePic.png");
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();

            nav_profilePic_CircleImageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
            nav_profilePic_CircleImageView.setImageResource(R.drawable.default_profile_pic);
        }

        SharedPreferences userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        String firstNameSaved = userInfo.getString("firstName", "");
        String lastNameSaved = userInfo.getString("lastName", "");
        String nameSaved = firstNameSaved + " " + lastNameSaved;

        String pseudo_String = sessionManager.getPseudo();

        nav_name_TextView.setText(nameSaved);
        nav_pseudo_TextView.setText(pseudo_String);
    }

    public void startDailyExercising() {
        startDailyExercising_LinearLayout = findViewById(R.id.startDailyExercising_LinearLayout);
        startDailyExercising_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent CalendarActivity = new Intent(HomeActivity.this, com.tri.fitness.activities.CalendarActivity.class);
                startActivity(CalendarActivity);
            }
        });
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_calendar) {
            Intent CalendarActivity = new Intent(HomeActivity.this,
                    com.tri.fitness.activities.CalendarActivity.class);
            startActivity(CalendarActivity);
        }

        else if (id == R.id.nav_training) {
            Intent TrainingActivity = new Intent(HomeActivity.this,
                    TrainingActivity.class);
            startActivity(TrainingActivity);
        }

        else if (id == R.id.nav_profil) {
            Intent ProfilActivity = new Intent(HomeActivity.this,
                    com.tri.fitness.activities.ProfilActivity.class);
            startActivity(ProfilActivity);
        }

        else if (id == R.id.nav_settings) {
            Intent SettingsActivity = new Intent(HomeActivity.this,
                    SettingsActivity.class);
            startActivity(SettingsActivity);
        }

        else if (id == R.id.nav_about) {
            Intent AboutActivity = new Intent(HomeActivity.this,
                    AboutActivity.class);
            startActivity(AboutActivity);
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
