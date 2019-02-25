package com.tri.fitness.models;

import android.content.Context;

import com.tri.fitness.utils.Function;

import org.json.JSONObject;

public class Profil {

    private Context context;
    private Function function;


    private String userId_String;
    private String userPseudo_String;
    private String userEmail_String;
    private String userFirstName_String;
    private String userLastName_String;
    private String userGender_String;
    private String userBirthday;

    private String profilId_String;
    private String profilGoal_String;
    private String profilSportingLevel_String;


    public Profil(Context context, JSONObject jsonObject) {
        this.context = context;
        function = new Function(context);

        userId_String = jsonObject.optString("id_user");
        userPseudo_String = jsonObject.optString("pseudo_user");
        userEmail_String = jsonObject.optString("email_user");
        userFirstName_String = jsonObject.optString("first_name_user");
        userLastName_String = jsonObject.optString("last_name_user");
        userGender_String = jsonObject.optString("gender_user");
        userBirthday = jsonObject.optString("birthday_user");

        profilId_String = jsonObject.optString("id_fitness_profil");
        profilGoal_String = jsonObject.optString("goal_fitness_profil");
        profilSportingLevel_String = jsonObject.optString("sporting_level_fitness_profil");
    }


    public String getUserId_String() {
        return userId_String;
    }

    public String getUserPseudo_String() {
        return userPseudo_String;
    }

    public String getUserEmail_String() {
        return userEmail_String;
    }

    public String getUserFirstName_String() {
        return userFirstName_String;
    }

    public String getUserLastName_String() {
        return userLastName_String;
    }

    public String getUserGender_String() {
        return userGender_String;
    }

    public String getUserBirthday() {
        switch (userBirthday) {
            case "1000-01-01":
                return "-";
            case "null":
                return "-";
            default:
                return function.convertDateFormat(userBirthday, "yyyy-MM-dd", "dd MMMM yyyy");
        }
    }


    public String getProfilId_String() {
        return profilId_String;
    }

    public String getProfilGoal_String() {
        return profilGoal_String;
    }

    public String getProfilSportingLevel_String() {
        return profilSportingLevel_String;
    }
}
