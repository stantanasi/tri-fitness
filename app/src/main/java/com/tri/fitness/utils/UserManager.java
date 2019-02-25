package com.tri.fitness.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "login_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "isLogged";
    private final static String ID = "id";
    private final static String PSEUDO = "pseudo";
    private final static String FIRST_NAME = "firstName";
    private final static String LAST_NAME = "lastName";
    private Context context;


    public UserManager(Context context) {
        this.context = context;
        prefs = context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }



    public void insertUser(String id, String pseudo, String firstName, String lastName) {
        editor.putBoolean(IS_LOGGED, true);
        editor.putString(ID, id);
        editor.putString(PSEUDO, pseudo);
        editor.putString(FIRST_NAME, firstName);
        editor.putString(LAST_NAME, lastName);
        editor.commit();
    }

    public boolean isLogged() {
        return prefs.getBoolean(IS_LOGGED, false);
    }


    public void logout() {
        editor.putBoolean(IS_LOGGED, false);
        editor.remove(ID);
        editor.commit();
    }


    public String getId() {
        return prefs.getString(ID, null);
    }

    public String getPseudo() {
        return prefs.getString(PSEUDO, null);
    }
}
