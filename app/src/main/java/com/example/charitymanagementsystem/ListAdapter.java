package com.example.charitymanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

public class ListAdapter extends FirestoreRecyclerAdapter<Model, ListAdapter.MyViewHolder> {
//Adapter to show data in recycler view in admin dashboard

    //constructor
    public ListAdapter(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    //view holder to bind views of custom made layout file
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
        holder.emailTV.setText(model.getEmail());
        holder.usernameTV.setText(model.getUsername());
        holder.statusTV.setText(model.getStatus());
        holder.typeTV.setText(model.getType());

        if(model.getStatus().equals("Approve"))
        {
            holder.approveBtn.setVisibility(View.GONE);
            holder.declineBtn.setVisibility(View.VISIBLE);
            holder.declineBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore.getInstance().collection("users").document(getSnapshots()
                            .getSnapshot(position).getId()).update("status", "Decline")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ListAdapter.this.notifyItemRemoved(position);
                                }
                            });
                }
            });

        }else {
            holder.declineBtn.setVisibility(View.GONE);
            holder.approveBtn.setVisibility(View.VISIBLE);
            holder.approveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FirebaseFirestore.getInstance().collection("users").document(getSnapshots()
                            .getSnapshot(position).getId()).update("status", "Approve")
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    ListAdapter.this.notifyItemRemoved(position);
                                }
                            });
                }
            });
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserHistoryActivity.user = model;
                Log.d("Iddfdf",model.getId());
                Context context = holder.itemView.getContext();
                context.startActivity(new Intent(context, UserHistoryActivity.class));

            }
        });





    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.aditem, parent, false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView emailTV, usernameTV, statusTV, typeTV;
        Button approveBtn, declineBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            emailTV = itemView.findViewById(R.id.emailTV);
            typeTV = itemView.findViewById(R.id.typeTV);
            usernameTV = itemView.findViewById(R.id.userNameTV);
            statusTV = itemView.findViewById(R.id.statusTV);
            approveBtn = itemView.findViewById(R.id.approve);
            declineBtn = itemView.findViewById(R.id.decline);
        }

    }

}
