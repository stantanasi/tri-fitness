package com.tri.fitness.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;
    private final static String PREFS_NAME = "login_prefs";
    private final static int PRIVATE_MODE = 0;
    private final static String IS_LOGGED = "isLogged";
    private final static String IS_LOGOUT = "isLogout";
    private final static String REMEMBER_INFO = "connectionAuto";
    private final static String ID = "id";
    private final static String PSEUDO = "pseudo";
    private final static String PASSWORD = "password";
    private Context context;


    public SessionManager(Context context) {
        this.context = context;
        prefs = this.context.getSharedPreferences(PREFS_NAME, PRIVATE_MODE);
        editor = prefs.edit();
    }



    public void insertUser(String id, String pseudo) {
        editor.putBoolean(IS_LOGGED, true);
        editor.putBoolean(IS_LOGOUT, false);
        editor.putString(ID, id);
        editor.putString(PSEUDO, pseudo);
        editor.commit();
    }

    public boolean isLogged() {
        return prefs.getBoolean(IS_LOGGED, false);
    }


    public void logout() {
        editor.putBoolean(IS_LOGOUT, true);
        editor.putBoolean(IS_LOGGED, false);
        editor.remove(ID);
        editor.commit();
    }

    public boolean isLogout() {
        return prefs.getBoolean(IS_LOGOUT, false);
    }



    public void connectionAuto(String pseudo, String password) {
        editor.putBoolean(REMEMBER_INFO, true);
        editor.putString(PSEUDO, pseudo);
        editor.putString(PASSWORD, password);
        editor.commit();
    }

    public void deleteRememberInfo() {
        editor.remove(REMEMBER_INFO);
        editor.remove(PSEUDO);
        editor.remove(PASSWORD);
        editor.commit();
    }

    public boolean hasRememberIsInfo() {
        return prefs.getBoolean(REMEMBER_INFO, false);
    }



    public String getId() {
        return prefs.getString(ID, null);
    }

    public String getPseudo() {
        return prefs.getString(PSEUDO, null);
    }

    public String getPassword() {
        return prefs.getString(PASSWORD, null);
    }
}
