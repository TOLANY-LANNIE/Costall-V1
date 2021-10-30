package com.example.costall.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.costall.models.ResourceObject;
import com.example.costall.R;

import java.util.List;

public class ResourceAdapter extends RecyclerView.Adapter<ResourceAdapter.MyViewHolder> {
    private Context mContext;
    private List<ResourceObject> mList;

    public ResourceAdapter(Context context, List<ResourceObject> objects) {
        mContext = context;
        mList = objects;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        view = inflater.inflate(R.layout.resource_list_item,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.resourceName.setText(mList.get(position).getResourceName());
       holder.purpose.setText(mList.get(position).getPurpose());
       holder.cost.setText(mList.get(position).getCost());
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView resourceName;
        TextView purpose;
        TextView cost;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            resourceName = itemView.findViewById(R.id.resource_text);
            purpose = itemView.findViewById(R.id.purpose_txt);
            cost= itemView.findViewById(R.id.res_cost_txt);
        }
    }
}
