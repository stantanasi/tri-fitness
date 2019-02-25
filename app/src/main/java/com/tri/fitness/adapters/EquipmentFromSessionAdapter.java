package com.tri.fitness.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.tri.fitness.models.EquipmentFromSession;
import com.tri.fitness.R;

import java.util.List;

public class EquipmentFromSessionAdapter extends RecyclerView.Adapter<EquipmentFromSessionAdapter.MyViewHolder> {

    private List<EquipmentFromSession> myEquipment;

    public EquipmentFromSessionAdapter(List<EquipmentFromSession> myEquipment) {
        this.myEquipment = myEquipment;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.item_equipment_from_session, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.display(myEquipment.get(position));
    }

    @Override
    public int getItemCount() {
        return myEquipment.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        private View session_equipmentCheck_View;
        private ImageView session_equipmentImage_ImageView;
        private TextView session_equipmentName_TextView;

        MyViewHolder(View itemView) {
            super(itemView);

            session_equipmentCheck_View = itemView.findViewById(R.id.session_equipmentCheck_View);
            session_equipmentImage_ImageView = itemView.findViewById(R.id.session_equipmentImage_ImageView);
            session_equipmentName_TextView = itemView.findViewById(R.id.session_equipmentName_TextView);
        }

        void display(EquipmentFromSession equipmentFromSession) {
            if (equipmentFromSession.getEquipmentFromSessionCheck() == 1) {
                session_equipmentCheck_View.setBackgroundResource(R.drawable.equipment_from_session_background);
            }
            session_equipmentImage_ImageView.setImageBitmap(equipmentFromSession.getEquipmentFromSessionImage_Bitmap());
            session_equipmentName_TextView.setText(equipmentFromSession.getEquipmentFromSessionName_String());
        }
    }
}
