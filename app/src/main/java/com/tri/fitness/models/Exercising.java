package com.tri.fitness.models;

import android.graphics.Bitmap;

public class Exercising {

    private String exercisingName_String;
    private Bitmap exercisingImage_Bitmap;

    public Exercising(String exercisingName_String, Bitmap exercisingImage_Bitmap) {
        this.exercisingName_String = exercisingName_String;
        this.exercisingImage_Bitmap = exercisingImage_Bitmap;
    }

    public Bitmap getExercisingImage_Bitmap() {
        return exercisingImage_Bitmap;
    }

    public String getExercisingName_String() {
        return exercisingName_String;
    }


    public void setExercisingImage_Bitmap(Bitmap exercisingImage_Bitmap) {
        this.exercisingImage_Bitmap = exercisingImage_Bitmap;
    }

    public void setExercisingName_String(String exercisingName_String) {
        this.exercisingName_String = exercisingName_String;
    }
}
