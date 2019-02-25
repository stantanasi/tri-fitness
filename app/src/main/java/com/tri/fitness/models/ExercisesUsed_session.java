package com.tri.fitness.models;

import android.graphics.Bitmap;

public class ExercisesUsed_session {

    private String exercicesNumber_String;
    private String exercicesName_String;
    private Bitmap exercicesImage_Bitmap;
    private String exercicesMuscles_String;
    private String exercicesSeries_String;
    private String exercicesRepetition_String;

    public ExercisesUsed_session(String exercicesNumber_String, String exercicesName_String, Bitmap exercicesImage_Bitmap, String exercicesMuscles_String, String exercicesSeries_String, String exercicesRepetition_String) {
        this.exercicesNumber_String = exercicesNumber_String;
        this.exercicesName_String = exercicesName_String;
        this.exercicesImage_Bitmap = exercicesImage_Bitmap;
        this.exercicesMuscles_String = exercicesMuscles_String;
        this.exercicesSeries_String = exercicesSeries_String;
        this.exercicesRepetition_String = exercicesRepetition_String;
    }


    public String getExercicesNumber_String() {
        return exercicesNumber_String;
    }

    public String getExercicesName_String() {
        return exercicesName_String;
    }

    public Bitmap getExercicesImage_Bitmap() {
        return exercicesImage_Bitmap;
    }

    public String getExercicesMuscles_String() {
        return exercicesMuscles_String;
    }

    public String getExercicesSeries_String() {
        return exercicesSeries_String;
    }

    public String getExercicesRepetition_String() {
        return exercicesRepetition_String;
    }


    public void setExercicesNumber_String(String exercicesNumber_String) {
        this.exercicesNumber_String = exercicesNumber_String;
    }

    public void setExercicesName_String(String exercicesName_String) {
        this.exercicesName_String = exercicesName_String;
    }

    public void setExercicesImage_Bitmap(Bitmap exercicesImage_Bitmap) {
        this.exercicesImage_Bitmap = exercicesImage_Bitmap;
    }

    public void setExercicesMuscles_String(String exercicesMuscles_String) {
        this.exercicesMuscles_String = exercicesMuscles_String;
    }

    public void setExercicesSeries_String(String exercicesSeries_String) {
        this.exercicesSeries_String = exercicesSeries_String;
    }

    public void setExercicesRepetition_String(String exercicesRepetition_String) {
        this.exercicesRepetition_String = exercicesRepetition_String;
    }
}
