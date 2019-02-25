package com.tri.fitness.activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.models.Profil;
import com.tri.fitness.utils.Function;
import com.tri.fitness.utils.UserManager;

import org.json.JSONArray;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfilActivity extends AppCompatActivity {

    Function function;
    UserManager userManager;

    Profil profil;

    private static int PICK_IMAGE_FROM_GALLERY = 2;
    private static int CROP_IMAGE = 0;
    Uri profilePic_Uri;

    TextView size_TextView, weight_TextView,
            editTtwitter_TextView, editInstagram_TextView, editSnapchat_TextView, editFacebook_TextView, editYoutube_TextView;
    EditText twitter_EditText, instagram_EditText, snapchat_EditText, facebook_EditText, youtube_EditText;
    LinearLayout socialNetworks_LinearLayout;
    CircleImageView profilePic_CircleImageView;

    Dialog pickImage_Dialog, socialNetworks_Dialog;

    SharedPreferences userInfo;
    SharedPreferences.Editor edit_userInfo;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        function = new Function(ProfilActivity.this);
        userManager = new UserManager(ProfilActivity.this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        userInfo = getSharedPreferences("userInfo", Context.MODE_PRIVATE);
        edit_userInfo = userInfo.edit();
        edit_userInfo.apply();

        /*
        setActivity();

        // Edit Profil
        setProfilePic();
        setSocialNetworks();
        */
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(this, "sauvegarder", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        setActivity();
    }


    public void setActivity() {
        profil = getProfilInfo();

        userManager.insertUser(profil.getUserId_String(), profil.getUserPseudo_String(), profil.getUserFirstName_String(), profil.getUserLastName_String());

        setName();
        setGender();
        setBirthday();
        setGoal();
        setSportingLevel();


        profilePic_CircleImageView = findViewById(R.id.profilePic_CircleImageView);
        size_TextView = findViewById(R.id.size_TextView);
        weight_TextView = findViewById(R.id.weight_TextView);

        socialNetworks_LinearLayout = findViewById(R.id.socialNetworks_LinearLayout);
        editTtwitter_TextView = findViewById(R.id.editTtwitter_TextView);
        editInstagram_TextView = findViewById(R.id.editInstagram_TextView);
        editSnapchat_TextView = findViewById(R.id.editSnapchat_TextView);
        editFacebook_TextView = findViewById(R.id.editFacebook_TextView);
        editYoutube_TextView = findViewById(R.id.editYoutube_TextView);


        writeSizeWeight();
        // Ne pourra etre changer que depuis Tri+
        writeProfilePic();
        writeSocialNetworks();
    }

    public Profil getProfilInfo() {
        String id_user = "?id_user=1" /*+ userManager.getId()*/;

        String url = "/tri+/tri+_fitness/get_profil.php";
        String variables = id_user;
        Function.GetJSONArray getJSONArray = function.new GetJSONArray();
        getJSONArray.execute(url, variables);

        try {
            JSONArray jsonArray = getJSONArray.get();
            return new Profil(ProfilActivity.this, jsonArray.optJSONObject(0));
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void bodyActivity(View view) {
        Intent BodyActivity = new Intent(ProfilActivity.this,
                com.tri.fitness.activities.BodyActivity.class);
        startActivity(BodyActivity);
    }

    public void statisticsActivity(View view) {
        Intent StatisticsActivity = new Intent(ProfilActivity.this,
                StatisticsActivity.class);
        startActivity(StatisticsActivity);
    }






        // Heaader

    // ProfilePic
    public void setProfilePic() {
        pickImage_Dialog = new Dialog(this);
        Objects.requireNonNull(pickImage_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        profilePic_CircleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pickImage_Dialog.setContentView(R.layout.dialog_pick_image);
                pickImage_Dialog.show();

                LinearLayout pickImage_fromCamera_LinearLayout = pickImage_Dialog.findViewById(R.id.pickImage_fromCamera_LinearLayout);
                pickImage_fromCamera_LinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Caméra
                    }
                });

                LinearLayout pickImage_fromGallery_LinearLayout = pickImage_Dialog.findViewById(R.id.pickImage_fromGallery_LinearLayout);
                pickImage_fromGallery_LinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent pickImage_Intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        pickImage_Intent.setType("image/*");

                        startActivityForResult(Intent.createChooser(pickImage_Intent, "Complete action using"), PICK_IMAGE_FROM_GALLERY);

                        pickImage_Dialog.dismiss();
                    }
                });

                LinearLayout delete_image_LinearLayout = pickImage_Dialog.findViewById(R.id.delete_image_LinearLayout);
                delete_image_LinearLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        File profilePic_File = getApplicationContext().getFileStreamPath("profilePic.png");
                        profilePic_File.delete();

                        writeProfilePic();

                        pickImage_Dialog.dismiss();
                    }
                });
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==PICK_IMAGE_FROM_GALLERY && resultCode== Activity.RESULT_OK && data!=null) {
            profilePic_Uri = data.getData();
            cropImage();
        }

        else if (requestCode == CROP_IMAGE && resultCode== Activity.RESULT_OK && data!=null) {
            try {
                Bundle bundle = data.getExtras();
                if (bundle != null) {
                    Bitmap profilePic_Bitmap = bundle.getParcelable("data");

                    FileOutputStream fileOutputStream = getApplicationContext().openFileOutput("profilePic.png", Context.MODE_PRIVATE);
                    assert profilePic_Bitmap != null;
                    profilePic_Bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream);
                    fileOutputStream.close();
                }
            }
            catch (Exception e) {
                e.printStackTrace();

                Toast.makeText(this, "erreur lors de la sauvegarde de l'image", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void cropImage() {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(profilePic_Uri, "image/*");

            cropIntent.putExtra("crop", "true");
            cropIntent.putExtra("outputX", 580);
            cropIntent.putExtra("outputY", 580);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("scaleUpIfNeeded", true);
            cropIntent.putExtra("return-data", true);

            startActivityForResult(cropIntent, CROP_IMAGE);
        }
        catch (ActivityNotFoundException e) {
            Toast.makeText(this, "your device doesn't support the crop action", Toast.LENGTH_LONG).show();
        }
    }
    public void writeProfilePic() {
        try {
            FileInputStream fileInputStream = getApplicationContext().openFileInput("profilePic.png");
            Bitmap bitmap = BitmapFactory.decodeStream(fileInputStream);
            fileInputStream.close();

            profilePic_CircleImageView.setImageBitmap(bitmap);
        }
        catch (Exception e) {
            e.printStackTrace();
            profilePic_CircleImageView.setImageResource(R.drawable.default_profile_pic);
        }
    }


    // Size / Weight
    public void writeSizeWeight() {
        SharedPreferences userBody = getSharedPreferences("userBody", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit_userBody = userBody.edit();
        edit_userBody.apply();

        String size_saved = userBody.getString("size", "");
        String weight_saved = userBody.getString("weight", "");
        String unitSize = getResources().getString(R.string.unitSize);
        String unitWeight = getResources().getString(R.string.unitWeight);

        if (!size_saved.equals("") && weight_saved.equals("")) {
            size_TextView.setText(" -- " + size_saved + unitSize);
            weight_TextView.setText("");
        }

        else if (!weight_saved.equals("") && size_saved.equals("")) {
            size_TextView.setText("");
            weight_TextView.setText(" -- " + weight_saved + unitWeight);
        }

        else if (!size_saved.equals("") && !weight_saved.equals("")) {
            size_TextView.setText(" -- " + size_saved + unitSize);
            weight_TextView.setText(" / " + weight_saved + unitWeight);
        }
        else {
            size_TextView.setText("");
            weight_TextView.setText("");
        }
    }


    public void setName() {
        TextView name_TextView = findViewById(R.id.name_TextView);
        TextView pseudo_TextView = findViewById(R.id.pseudo_TextView);
        TextView editName_TextView = findViewById(R.id.editName_TextView);


        String nameSaved = profil.getUserFirstName_String() + " " + profil.getUserLastName_String();

        Objects.requireNonNull(getSupportActionBar()).setTitle(nameSaved);
        name_TextView.setText(nameSaved);
        pseudo_TextView.setText(profil.getUserPseudo_String());
        editName_TextView.setText(nameSaved);
    }

    public void setGender() {
        TextView gender_TextView = findViewById(R.id.gender_TextView);
        TextView editGender_TextView = findViewById(R.id.editGender_TextView);

        editGender_TextView.setText(profil.getUserGender_String());
        gender_TextView.setText(profil.getUserGender_String());
    }

    public void setBirthday() {
        TextView age_TextView = findViewById(R.id.age_TextView);
        TextView editBirthday_TextView = findViewById(R.id.editBirthday_TextView);

        String birthday = profil.getUserBirthday();
        editBirthday_TextView.setText(birthday);
        String string = ", " + function.calculateAge(birthday, "dd MMMM yyyy") + getResources().getString(R.string.years_old);
        age_TextView.setText(string);

    }

    public void setGoal() {
        final TextView goal_TextView = findViewById(R.id.goal_TextView);
        final TextView editGoal_TextView = findViewById(R.id.editGoal_TextView);
        LinearLayout goal_LinearLayout = findViewById(R.id.goal_LinearLayout);

        goal_TextView.setText(profil.getProfilGoal_String());
        editGoal_TextView.setText(profil.getProfilGoal_String());

        goal_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.showRadioGroupDialog(0.90, true, false, new TextView[]{goal_TextView, editGoal_TextView}, getResources().getStringArray(R.array.goal));
            }
        });
    }

    public void setSportingLevel() {
        final TextView sportingLevel_TextView = findViewById(R.id.sportingLevel_TextView);
        LinearLayout sportingLevel_LinearLayout = findViewById(R.id.sportingLevel_LinearLayout);

        sportingLevel_TextView.setText(profil.getProfilSportingLevel_String());

        sportingLevel_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                function.showRadioGroupDialog(0.90, true, false, new TextView[]{sportingLevel_TextView}, getResources().getStringArray(R.array.sportingLevel));
            }
        });
    }



    // About
    public void setAbout(String aboutMe_String) { // Peut etre mettre un dialog
        TextView aboutMe_TextView =  findViewById(R.id.AboutMe_TextView);
        TextView editAbout_TextView = findViewById(R.id.editAbout_TextView);
        LinearLayout AboutMe_LinearLayout = findViewById(R.id.AboutMe_LinearLayout);

        aboutMe_TextView.setText(aboutMe_String);
        if (aboutMe_String.equals(" ")) {
            editAbout_TextView.setText(R.string.aboutExplanation);
        }
        else {
            editAbout_TextView.setText(aboutMe_String);
        }

        AboutMe_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent EditAboutActivity = new Intent(ProfilActivity.this,
                        com.tri.fitness.activities.EditAboutActivity.class);
                startActivity(EditAboutActivity);
            }
        });
    }


    // Social Networks
    public void setSocialNetworks() {
        socialNetworks_Dialog = new Dialog(this);
        Objects.requireNonNull(socialNetworks_Dialog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        socialNetworks_LinearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                socialNetworks_Dialog.setContentView(R.layout.dialog_editprofil_socialnetworks);
                socialNetworks_Dialog.show();

                twitter_EditText = socialNetworks_Dialog.findViewById(R.id.twitterInput);
                instagram_EditText = socialNetworks_Dialog.findViewById(R.id.instagramInput);
                snapchat_EditText = socialNetworks_Dialog.findViewById(R.id.snapchatInput);
                facebook_EditText = socialNetworks_Dialog.findViewById(R.id.facebookInput);
                youtube_EditText = socialNetworks_Dialog.findViewById(R.id.youtubeInput);

                String twitterSaved = userInfo.getString("twitter", "");
                String instagramSaved = userInfo.getString("instagram", "");
                String snapchatSaved = userInfo.getString("snapchat", "");
                String facebookSaved = userInfo.getString("facebook", "");
                String youtubeSaved = userInfo.getString("youtube", "");

                twitter_EditText.setText(twitterSaved);
                instagram_EditText.setText(instagramSaved);
                snapchat_EditText.setText(snapchatSaved);
                facebook_EditText.setText(facebookSaved);
                youtube_EditText.setText(youtubeSaved);
            }
        });
    }
    public void confirmSocialNetworks(View view) {
        edit_userInfo.putString("twitter", twitter_EditText.getText().toString());
        edit_userInfo.putString("instagram", instagram_EditText.getText().toString());
        edit_userInfo.putString("snapchat", snapchat_EditText.getText().toString());
        edit_userInfo.putString("facebook", facebook_EditText.getText().toString());
        edit_userInfo.putString("youtube", youtube_EditText.getText().toString());
        edit_userInfo.apply();

        socialNetworks_Dialog.dismiss();

        writeSocialNetworks();
    }
    public void writeSocialNetworks() {
        String twitterSaved = userInfo.getString("twitter", "-");
        String instagramSaved = userInfo.getString("instagram", "-");
        String snapchatSaved = userInfo.getString("snapchat", "-");
        String facebookSaved = userInfo.getString("facebook", "-");
        String youtubeSaved = userInfo.getString("youtube", "-");

        if (twitterSaved.equals("")) {
            twitterSaved = "-";
            editTtwitter_TextView.setText(twitterSaved);
        }

        if (instagramSaved.equals("")) {
            instagramSaved = "-";
            editInstagram_TextView.setText(instagramSaved);
        }

        if (snapchatSaved.equals("")) {
            snapchatSaved = "-";
            editSnapchat_TextView.setText(snapchatSaved);
        }

        if (facebookSaved.equals("")) {
            facebookSaved = "-";
            editFacebook_TextView.setText(facebookSaved);
        }

        if (youtubeSaved.equals("")) {
            youtubeSaved = "-";
            editYoutube_TextView.setText(youtubeSaved);
        }

        editTtwitter_TextView.setText(twitterSaved);
        editInstagram_TextView.setText(instagramSaved);
        editSnapchat_TextView.setText(snapchatSaved);
        editFacebook_TextView.setText(facebookSaved);
        editYoutube_TextView.setText(youtubeSaved);
    }


    public void cancel(View view) {
        socialNetworks_Dialog.dismiss();
    }
}