package com.tri.fitness.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.tri.fitness.models.MySingleton;
import com.tri.fitness.R;
import com.tri.fitness.utils.MyRequests;
import com.tri.fitness.utils.SessionManager;

public class LoginActivity extends AppCompatActivity {

    LinearLayout login_LinearLayout, signUp_LinearLayout;
    EditText pseudo_login_EditText, password_login_EditText;
    CheckBox rememberMe_CheckBox;

    private RequestQueue queue;
    private MyRequests request;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_login);

        setActivity();
        setLogin();
        setRegister();
    }

    public void setActivity() {
        pseudo_login_EditText = findViewById(R.id.pseudo_login_EditText);
        password_login_EditText = findViewById(R.id.password_login_EditText);
        rememberMe_CheckBox = findViewById(R.id.rememberMe_CheckBox);
        login_LinearLayout = findViewById(R.id.login_LinearLayout);
        signUp_LinearLayout = findViewById(R.id.signUp_LinearLayout);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new MyRequests(this, queue);
        sessionManager = new SessionManager(this);
    }

    public void setLogin() {
        if (true) {
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
            finish();
        }

        /*if (sessionManager.isLogout()) { // Si il vient de se déconnecter
            if (sessionManager.hasRememberIsInfo()) { // Et que il avait retenu ses infos
                // Alors écrire les info
                pseudo_login_EditText.setText(sessionManager.getPseudo());
                password_login_EditText.setText(sessionManager.getPassword());
                rememberMe_CheckBox.setChecked(sessionManager.hasRememberIsInfo());
            }
        }
        else if(sessionManager.isLogged()) { // Si il vient de se connecter comme un premier lancement
            if (sessionManager.hasRememberIsInfo()) {
                pseudo_login_EditText.setText(sessionManager.getPseudo());
                password_login_EditText.setText(sessionManager.getPassword());
                rememberMe_CheckBox.setChecked(sessionManager.hasRememberIsInfo());
                login();
            }
        }*/

        login_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
    }

    public void login() {
        String pseudo_String = pseudo_login_EditText.getText().toString().trim();
        String password_String = password_login_EditText.getText().toString().trim();

        Boolean error = false;

        if (pseudo_String.length() <= 0) {
            error = true;
        }
        if (password_String.length() <= 0) {
            error = true;
        }
        if (pseudo_String.contains("/") || pseudo_String.contains("&")) {
            error = true;
        }

        if (!error) {
            if (rememberMe_CheckBox.isChecked()) {
                sessionManager.connectionAuto(pseudo_String, password_String);
            }
            else {
                sessionManager.deleteRememberInfo();
            }

            request.connection(pseudo_String, password_String, new MyRequests.LoginCallback() {
                @Override
                public void onSuccess(String id, String pseudo) {
                    sessionManager.insertUser(id, pseudo);
                    startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    finish();
                }

                @Override
                public void onError(String message) {
                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    public void setRegister() {
        signUp_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
    }
}
