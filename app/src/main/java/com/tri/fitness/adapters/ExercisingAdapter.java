package com.tri.fitness.adapters;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tri.fitness.R;
import com.tri.fitness.models.Exercising;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.List;

public class ExercisingAdapter extends RecyclerView.Adapter<ExercisingAdapter.MyViewHolder> {

    private List<Exercising> myExercising;
    private Activity mActivity;

    public ExercisingAdapter(List<Exercising> myExercising, Activity mActivity) {
        this.myExercising = myExercising;
        this.mActivity = mActivity;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull final ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_exercising_row, parent, false);
        return new MyViewHolder(view, mActivity);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(myExercising.get(position));
    }

    @Override
    public int getItemCount() {
        return myExercising.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout exercising_LinearLayout, exercicesAdd_LinearLayout;
        private ImageView exercisingImage_ImageView;
        private TextView exercisingName_TextView;

        private Activity mActivity;


        MyViewHolder(View itemView, final Activity mActivity) {
            super(itemView);

            this.mActivity = mActivity;

            exercising_LinearLayout = itemView.findViewById(R.id.exercising_LinearLayout);
            exercising_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ExercisingActivity = new Intent(view.getContext(), com.tri.fitness.activities.ExercisingActivity.class);

                    ExercisingActivity.putExtra("exercising_position", getAdapterPosition());

                    view.getContext().startActivity(ExercisingActivity);
                }
            });
            exercisingImage_ImageView = itemView.findViewById(R.id.exercisingImage_ImageView);
            exercisingName_TextView = itemView.findViewById(R.id.exercisingName_TextView);
            exercicesAdd_LinearLayout = itemView.findViewById(R.id.exercicesAdd_LinearLayout);
            exercicesAdd_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent returnIntent = new Intent();
                    try {
                        FileInputStream fileInputStream = view.getContext().openFileInput("exercising.txt");
                        InputStreamReader f = new InputStreamReader(fileInputStream, "UTF-8");
                        StringBuilder stringBuilder = new StringBuilder();
                        int content;
                        while ((content=f.read()) != -1) {
                            stringBuilder.append((char) content);
                        }
                        String[] list = stringBuilder.toString().split("/e1");

                        returnIntent.putExtra("result", list[getAdapterPosition()]);
                        mActivity.setResult(Activity.RESULT_OK, returnIntent);
                        mActivity.finish();
                    }
                    catch (Exception e) {
                        e.printStackTrace();

                        Toast.makeText(view.getContext(), "erreur lors de l'adapter", Toast.LENGTH_LONG).show();
                    }
                }
            });
        }

        void display(Exercising exercising) {
            exercisingImage_ImageView.setImageBitmap(exercising.getExercisingImage_Bitmap());
            exercisingName_TextView.setText(exercising.getExercisingName_String());
        }
    }
}
