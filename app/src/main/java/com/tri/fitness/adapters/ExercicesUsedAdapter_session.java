package com.tri.fitness.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tri.fitness.models.ExercisesUsed_session;
import com.tri.fitness.R;

import java.util.List;

public class ExercicesUsedAdapter_session extends RecyclerView.Adapter<ExercicesUsedAdapter_session.MyViewHolder> {

    private List<ExercisesUsed_session> myExercicesUsed;

    public ExercicesUsedAdapter_session(List<ExercisesUsed_session> myExercicesUsed) {
        this.myExercicesUsed = myExercicesUsed;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_exercice_used, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(myExercicesUsed.get(position));
    }

    @Override
    public int getItemCount() {
        return myExercicesUsed.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout exerciceUsedNumber_LinearLayout;
        private TextView exerciceUsedNumber_TextView;
        private LinearLayout exerciceUsed_LinearLayout;
        private ImageView exerciceUsedImage_ImageView;
        private TextView exerciceUsedNAme_TextView;
        private TextView exerciceUsedMuscles_TextView;
        private TextView exerciceUsedSeries_TextView;
        private TextView exerciceUsedRepetition_TextView;


        MyViewHolder(View itemView) {
            super(itemView);

            exerciceUsedNumber_LinearLayout = itemView.findViewById(R.id.exerciceUsedNumber_LinearLayout);
            exerciceUsedNumber_TextView = itemView.findViewById(R.id.exerciceUsedNumber_TextView);
            exerciceUsed_LinearLayout = itemView.findViewById(R.id.exerciceUsed_LinearLayout);
            exerciceUsedImage_ImageView = itemView.findViewById(R.id.exerciceUsedImage_ImageView);
            exerciceUsedNAme_TextView = itemView.findViewById(R.id.exerciceUsedNAme_TextView);
            exerciceUsedMuscles_TextView = itemView.findViewById(R.id.exerciceUsedMuscles_TextView);
            exerciceUsedSeries_TextView = itemView.findViewById(R.id.exerciceUsedSeries_TextView);
            exerciceUsedRepetition_TextView = itemView.findViewById(R.id.exerciceUsedRepetition_TextView);
        }

        void display(ExercisesUsed_session exercisesUsed_session) {
            exerciceUsedNumber_TextView.setText(exercisesUsed_session.getExercicesNumber_String());
            exerciceUsedImage_ImageView.setImageBitmap(exercisesUsed_session.getExercicesImage_Bitmap());
            exerciceUsedNAme_TextView.setText(exercisesUsed_session.getExercicesName_String());
            exerciceUsedMuscles_TextView.setText(exercisesUsed_session.getExercicesMuscles_String());
            exerciceUsedSeries_TextView.setText(exercisesUsed_session.getExercicesSeries_String());
            exerciceUsedRepetition_TextView.setText(exercisesUsed_session.getExercicesRepetition_String());
        }
    }
}
