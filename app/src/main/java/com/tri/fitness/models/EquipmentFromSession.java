package com.tri.fitness.models;

import android.graphics.Bitmap;

public class EquipmentFromSession {

    private String equipmentFromSessionName_String;
    private Bitmap equipmentFromSessionImage_Bitmap;
    private int equipmentFromSessionCheck;

    public EquipmentFromSession(String equipmentFromSessionName_String, Bitmap equipmentFromSessionImage_Bitmap, int equipmentFromSessionCheck) {
        this.equipmentFromSessionName_String = equipmentFromSessionName_String;
        this.equipmentFromSessionImage_Bitmap = equipmentFromSessionImage_Bitmap;
        this.equipmentFromSessionCheck = equipmentFromSessionCheck;
    }


    public String getEquipmentFromSessionName_String() {
        return equipmentFromSessionName_String;
    }

    public Bitmap getEquipmentFromSessionImage_Bitmap() {
        return equipmentFromSessionImage_Bitmap;
    }

    public int getEquipmentFromSessionCheck() {
        return equipmentFromSessionCheck;
    }



    public void setEquipmentFromSessionName_String(String equipmentFromSessionName_String) {
        this.equipmentFromSessionName_String = equipmentFromSessionName_String;
    }

    public void setEquipmentFromSessionImage_Bitmap(Bitmap equipmentFromSessionImage_Bitmap) {
        this.equipmentFromSessionImage_Bitmap = equipmentFromSessionImage_Bitmap;
    }

    public void setEquipmentFromSessionCheck(int equipmentFromSessionCheck) {
        this.equipmentFromSessionCheck = equipmentFromSessionCheck;
    }
}
