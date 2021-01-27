package com.example.itiswhereitis;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private Context context;
    private Activity activity;
    private ArrayList itemIds, itemNames, itemTypes, itemLocations;

    public ItemAdapter(Context context, Activity activity, ArrayList itemIds, ArrayList itemNames, ArrayList itemTypes, ArrayList itemLocations) {
        this.context = context;
        this.activity = activity;
        this.itemIds = itemIds;
        this.itemNames = itemNames;
        this.itemTypes = itemTypes;
        this.itemLocations = itemLocations;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item_cardview, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        holder.name.setText(String.valueOf(itemNames.get(position)));
        holder.type.setText(String.valueOf(itemTypes.get(position)));
        holder.location.setText(String.valueOf(itemLocations.get(position)));

        //for intents to send data from selected cardview
        holder.cardView.setOnClickListener(v ->{
            Intent intent = new Intent(context, UpdateActivity.class);
            intent.putExtra("id", String.valueOf(itemIds.get(position)));
            intent.putExtra("name", String.valueOf(itemNames.get(position)));
            intent.putExtra("type", String.valueOf(itemTypes.get(position)));
            intent.putExtra("location", String.valueOf(itemLocations.get(position)));
            activity.startActivityForResult(intent, 1);
        });
    }

    @Override
    public int getItemCount() {
        return itemIds.size();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {

        private TextView id, name, type, location;
        LinearLayout cardView;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            //id = itemView.findViewById(R.id.textView_id);
            name = itemView.findViewById(R.id.textView_name);
            type = itemView.findViewById(R.id.textView_type);
            location = itemView.findViewById(R.id.textView_location);
            cardView = itemView.findViewById(R.id.cardView_item);
        }
    }
}
