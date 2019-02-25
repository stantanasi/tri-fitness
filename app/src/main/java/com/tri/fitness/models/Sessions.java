package com.tri.fitness.models;

import android.graphics.Bitmap;

public class Sessions {

    private String sessionsName_String;
    private Bitmap sessionsImage_Bitmap;
    private String sessionsDurationTotal_String;
    private String sessionsNumberSeries_String;
    private String sessionsNumberExercising_String;


    public Sessions(String sessionsName_String, Bitmap sessionsImage_Bitmap, String sessionsDurationTotal_String, String sessionsNumberSeries_String, String sessionsNumberExercising_String) {
        this.sessionsName_String = sessionsName_String;
        this.sessionsImage_Bitmap = sessionsImage_Bitmap;
        this.sessionsDurationTotal_String = sessionsDurationTotal_String;
        this.sessionsNumberSeries_String = sessionsNumberSeries_String;
        this.sessionsNumberExercising_String = sessionsNumberExercising_String;
    }


    public String getSessionsName_String() {
        return sessionsName_String;
    }

    public Bitmap getSessionsImage_Bitmap() {
        return sessionsImage_Bitmap;
    }

    public String getSessionsDurationTotal_String() {
        return sessionsDurationTotal_String;
    }

    public String getSessionsNumberSeries_String() {
        return sessionsNumberSeries_String;
    }

    public String getSessionsNumberExercising_String() {
        return sessionsNumberExercising_String;
    }


    public void setSessionsName_String(String sessionsName_String) {
        this.sessionsName_String = sessionsName_String;
    }

    public void setSessionsImage_Bitmap(Bitmap sessionsImage_Bitmap) {
        this.sessionsImage_Bitmap = sessionsImage_Bitmap;
    }

    public void setSessionsDurationTotal_String(String sessionsDurationTotal_String) {
        this.sessionsDurationTotal_String = sessionsDurationTotal_String;
    }

    public void setSessionsNumberSeries_String(String sessionsNumberSeries_String) {
        this.sessionsNumberSeries_String = sessionsNumberSeries_String;
    }

    public void setSessionsNumberExercising_String(String sessionsNumberExercising_String) {
        this.sessionsNumberExercising_String = sessionsNumberExercising_String;
    }
}
