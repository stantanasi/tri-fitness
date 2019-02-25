package com.tri.fitness.models;

import android.graphics.Bitmap;

public class Programs {

    private String programsName_String;
    private Bitmap programsImage_Bitmap;
    private String programsDurationPerSessions_String;
    private String programsNumberSessionsPerWeek_String;
    private String programsDurationTotal_String;

    public Programs(String programsName_String, Bitmap programsImage_Bitmap, String programsDurationPerSessions_String, String programsNumberSessionsPerWeek_String, String programsDurationTotal_String) {
        this.programsName_String = programsName_String;
        this.programsImage_Bitmap = programsImage_Bitmap;
        this.programsDurationPerSessions_String = programsDurationPerSessions_String;
        this.programsNumberSessionsPerWeek_String = programsNumberSessionsPerWeek_String;
        this.programsDurationTotal_String = programsDurationTotal_String;
    }


    public String getProgramsName_String() {
        return programsName_String;
    }

    public Bitmap getProgramsImage_Bitmap() {
        return programsImage_Bitmap;
    }

    public String getProgramsDurationPerSessions_String() {
        return programsDurationPerSessions_String;
    }

    public String getProgramsNumberSessionsPerWeek_String() {
        return programsNumberSessionsPerWeek_String;
    }

    public String getProgramsDurationTotal_String() {
        return programsDurationTotal_String;
    }


    public void setProgramsName_String(String programsName_String) {
        this.programsName_String = programsName_String;
    }

    public void setProgramsImage_Bitmap(Bitmap programsImage_Bitmap) {
        this.programsImage_Bitmap = programsImage_Bitmap;
    }

    public void setProgramsDurationPerSessions_String(String programsDurationPerSessions_String) {
        this.programsDurationPerSessions_String = programsDurationPerSessions_String;
    }

    public void setProgramsNumberSessionsPerWeek_String(String programsNumberSessionsPerWeek_String) {
        this.programsNumberSessionsPerWeek_String = programsNumberSessionsPerWeek_String;
    }

    public void setProgramsDurationTotal_String(String programsDurationTotal_String) {
        this.programsDurationTotal_String = programsDurationTotal_String;
    }
}
