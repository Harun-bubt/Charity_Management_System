package com.example.charitymanagementsystem;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyAdapter extends FirestoreRecyclerAdapter<Model, MyAdapter.MyViewHolder> {
//Adapter to show data in recycler view in admin dashboard

    //constructor
    public MyAdapter(@NonNull FirestoreRecyclerOptions<Model> options) {
        super(options);
    }

    //view holder to bind views of custom made layout file
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Model model) {
        holder.emailTV.setText(model.getEmail());
        holder.usernameTV.setText(model.getUsername());
        holder.statusTV.setText(model.getStatus());
        holder.typeTV.setText(model.getType());
        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("users").document(getSnapshots()
                        .getSnapshot(position).getId()).update("status", "Approve")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                MyAdapter.this.notifyItemRemoved(position);
                            }
                        });
            }
        });
        holder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection("users").document(getSnapshots()
                        .getSnapshot(position).getId()).update("status", "Decline")
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                MyAdapter.this.notifyItemRemoved(position);
                            }
                        });
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
