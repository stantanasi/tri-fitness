package com.tri.fitness.adapters;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tri.fitness.R;
import com.tri.fitness.models.Programs;

import java.util.List;

public class ProgramsAdapter extends RecyclerView.Adapter<ProgramsAdapter.MyViewHolder> {

    private List<Programs> myPrograms;

    public ProgramsAdapter(List<Programs> myPrograms) {
        this.myPrograms = myPrograms;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_programs_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(myPrograms.get(position));
    }

    @Override
    public int getItemCount() {
        return myPrograms.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout programs_LinearLayout;
        private TextView programsName_TextView;
        private ImageView programsImage_ImageView;
        private TextView programsDurationPerSessions_TextView;
        private TextView programsNumberSessionsPerWeek_TextView;
        private TextView programsDurationTotal_TextView;
        
        MyViewHolder(View itemView) {
            super(itemView);

            programs_LinearLayout = itemView.findViewById(R.id.programs_LinearLayout);
            programs_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ProgramsActivity = new Intent(view.getContext(), com.tri.fitness.activities.ProgramsActivity.class);

                    ProgramsActivity.putExtra("programs_position", getAdapterPosition());

                    view.getContext().startActivity(ProgramsActivity);
                }
            });

            programsName_TextView = itemView.findViewById(R.id.programsName_TextView);
            programsImage_ImageView = itemView.findViewById(R.id.programsImage_ImageView);
            programsDurationPerSessions_TextView = itemView.findViewById(R.id.programsDurationPerSessions_TextView);
            programsNumberSessionsPerWeek_TextView = itemView.findViewById(R.id.programsNumberSessionsPerWeek_TextView);
            programsDurationTotal_TextView = itemView.findViewById(R.id.programsDurationTotal_TextView);
        }
        
        void display(Programs programs) {

            programsName_TextView.setText(programs.getProgramsName_String());
            programsImage_ImageView.setImageBitmap(programs.getProgramsImage_Bitmap());
            programsDurationPerSessions_TextView.setText(programs.getProgramsDurationPerSessions_String());
            programsNumberSessionsPerWeek_TextView.setText(programs.getProgramsNumberSessionsPerWeek_String());
            programsDurationTotal_TextView.setText(programs.getProgramsDurationTotal_String());
        }
    }
}
