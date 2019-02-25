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

import com.tri.fitness.activities.SessionActivity;
import com.tri.fitness.models.Sessions;
import com.tri.fitness.R;

import java.util.List;

public class SessionsAdapter extends RecyclerView.Adapter<SessionsAdapter.MyViewHolder> {

    private List<Sessions> mySessions;

    public SessionsAdapter(List<Sessions> mySessions) {
        this.mySessions = mySessions;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_sessions_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(mySessions.get(position));
    }

    @Override
    public int getItemCount() {
        return mySessions.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout sessions_LinearLayout;
        private TextView sessionsName_TextView;
        private ImageView sessionsImage_ImageView;
        private TextView sessionsDurationTotal_TextView;
        private TextView sessionsNumberSeries_TextView;
        private TextView sessionsNumberExercising_TextView;

        MyViewHolder(View itemView) {
            super(itemView);

            sessions_LinearLayout = itemView.findViewById(R.id.sessions_LinearLayout);
            sessions_LinearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent SessionsActivity = new Intent(view.getContext(), SessionActivity.class);

                    SessionsActivity.putExtra("session_position", getAdapterPosition());

                    view.getContext().startActivity(SessionsActivity);
                }
            });

            sessionsName_TextView = itemView.findViewById(R.id.sessionsName_TextView);
            sessionsImage_ImageView = itemView.findViewById(R.id.sessionsImage_ImageView);
            sessionsDurationTotal_TextView = itemView.findViewById(R.id.sessionsDurationTotal_TextView);
            sessionsNumberSeries_TextView = itemView.findViewById(R.id.sessionsNumberSeries_TextView);
            sessionsNumberExercising_TextView = itemView.findViewById(R.id.sessionsNumberExercising_TextView);
        }

        void display(Sessions sessions) {
            sessionsName_TextView.setText(sessions.getSessionsName_String());
            sessionsImage_ImageView.setImageBitmap(sessions.getSessionsImage_Bitmap());
            sessionsDurationTotal_TextView.setText(sessions.getSessionsDurationTotal_String());
            sessionsNumberSeries_TextView.setText(sessions.getSessionsNumberSeries_String());
            sessionsNumberExercising_TextView.setText(sessions.getSessionsNumberExercising_String());
        }
    }
}
