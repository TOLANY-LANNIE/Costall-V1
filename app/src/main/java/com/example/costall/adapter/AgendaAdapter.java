package com.example.costall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.costall.AgendaList;
import com.example.costall.models.AgendaItem;
import com.example.costall.R;

import java.util.List;

public class AgendaAdapter extends RecyclerView.Adapter<AgendaAdapter.MyViewHolder> {
    private Context mContext;
    private List<AgendaItem> mAgendaItems;

    public AgendaAdapter(Context context, List<AgendaItem> agendaItems) {
        mContext = context;
        mAgendaItems = agendaItems;
    }

    public AgendaAdapter(AgendaList context, List<AgendaItem> mAgendaItems) {
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.agenda_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.agendaDescription.setText(mAgendaItems.get(position).getAgendaDescription());
        holder.leadParticipant.setText(mAgendaItems.get(position).getName()+" "+mAgendaItems.get(position).getSurname());
        holder.allocatedTime.setText(mAgendaItems.get(position).getAllocatedTime());
    }

    @Override
    public int getItemCount() {
        return mAgendaItems.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView agendaDescription;
        TextView leadParticipant;
        TextView allocatedTime;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            agendaDescription = itemView.findViewById(R.id.agendaD_text);
            leadParticipant = itemView.findViewById(R.id.lead_participant);
            allocatedTime= itemView.findViewById(R.id.al_time_txt);
        }
    }
}
