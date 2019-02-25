package com.tri.fitness.utils;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MyRequests {

    private Context context;
    private RequestQueue queue;

    public MyRequests(Context context, RequestQueue queue) {
        this.context = context;
        this.queue = queue;
    }


    public void register(final String pseudo, final String email, final String password, final String password2, final RegisterCallback callback) {
        String url = "http://192.168.0.42/tri+/register.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Map<String, String> errors = new HashMap<>();

                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {
                        callback.onSuccess("Vous êtes bien inscrit");
                    }
                    else {
                        JSONObject messages = json.getJSONObject("message");

                        if (messages.has("pseudo")) {
                            errors.put("pseudo", messages.getString("pseudo"));
                        }
                        if (messages.has("email")) {
                            errors.put("email", messages.getString("email"));
                        }
                        if (messages.has("password")) {
                            errors.put("password", messages.getString("password"));
                        }

                        callback.inputErrors(errors);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("Impossible de se connecter,\nVeuillez vérifiez votre connexion internet");
                }
                else if (error instanceof VolleyError) {
                    callback.onError(String.valueOf(error));
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("pseudo", pseudo); // $_POST['pseudo']
                map.put("email", email);
                map.put("password", password);
                map.put("password2", password2);

                return map;
            }
        };


        queue.add(request);
    }

    public interface RegisterCallback{
        void onSuccess(String message);
        void inputErrors(Map<String, String> errors);
        void onError(String error);
    }



    public void connection(final String pseudo, final String password, final LoginCallback callback) {
        String url = "http://192.168.0.42/tri+/login.php";

        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject json = new JSONObject(response);
                    Boolean error = json.getBoolean("error");

                    if (!error) {
                        String id = json.getString("id");
                        String pseudo = json.getString("pseudo");
                        callback.onSuccess(id, pseudo);
                    }

                    else {
                        callback.onError(json.getString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    callback.onError("Une erreur s'est produite");
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                if (error instanceof NetworkError) {
                    callback.onError("Impossible de se connecter,\nVeuillez vérifiez votre connexion internet");
                }
                else if (error instanceof VolleyError) {
                    callback.onError(String.valueOf(error));
                }
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();

                map.put("pseudo", pseudo); // $_POST['pseudo']
                map.put("password", password);

                return map;
            }
        };


        queue.add(request);
    }

    public interface LoginCallback {
        void onSuccess(String id, String pseudo);
        void onError(String message);

    }
}
