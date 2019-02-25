package com.tri.fitness.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.tri.fitness.models.MySingleton;
import com.tri.fitness.R;
import com.tri.fitness.utils.MyRequests;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    EditText pseudo_EditText, email_EditText, password_EditText, password2_EditText;
    LinearLayout register_LinearLayout;

    private RequestQueue queue;
    private MyRequests request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        register_LinearLayout = findViewById(R.id.register_LinearLayout);
        pseudo_EditText = findViewById(R.id.pseudo_register_EditText);
        email_EditText = findViewById(R.id.email_register_EditText);
        password_EditText = findViewById(R.id.password_register_EditText);
        password2_EditText = findViewById(R.id.password2_register_EditText);

        queue = MySingleton.getInstance(this).getRequestQueue();
        request = new MyRequests(this, queue);

        register_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pseudo_String = pseudo_EditText.getText().toString().trim();
                String email_String = email_EditText.getText().toString().trim();
                String password_String = password_EditText.getText().toString().trim();
                String password2_String = password2_EditText.getText().toString().trim();

                request.register(pseudo_String, email_String, password_String, password2_String, new MyRequests.RegisterCallback() {
                    @Override
                    public void onSuccess(String message) {
                        // Lancer activity pour initialiser le profil (genre + date de naissance + objectif... )

                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void inputErrors(Map<String, String> errors) {
                        // TextInputLayout.setError pour afficher un message d'erreur en dessous de l'EditText

                        if (errors.get("pseudo") != null) {
                            Toast.makeText(getApplicationContext(), errors.get("pseudo"), Toast.LENGTH_SHORT).show();
                        }
                        if (errors.get("email") != null) {
                            Toast.makeText(getApplicationContext(), errors.get("email"), Toast.LENGTH_SHORT).show();
                        }
                        if (errors.get("password") != null) {
                            Toast.makeText(getApplicationContext(), errors.get("password"), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
