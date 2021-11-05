package com.example.charitymanagementsystem;

import android.content.Context;
import android.content.Intent;
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

public class SponsorAdapter extends FirestoreRecyclerAdapter<RequestModel, SponsorAdapter.MyViewHolder> {
//Adapter to show data in recycler view in admin dashboard

    //constructor
    public SponsorAdapter(@NonNull FirestoreRecyclerOptions<RequestModel> options) {
        super(options);
    }



    //view holder to bind views of custom made layout file
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder,int position, @NonNull RequestModel model) {
        holder.requestTv.setText(model.getRequestText());
        holder.dateTV.setText(model.getRequestDate());
        holder.approveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).document(getSnapshots()
                        .getSnapshot(position).getId()).update("status", true,"count",
                        model.getCount()+1,"sponsorUid",SponsorDashboard.uid,"acceptDate",SponsorDashboard.acceptedDate)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                SponsorAdapter.this.notifyItemRemoved(position);
                            }
                        });
            }
        });
        holder.declineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).document(getSnapshots()
                        .getSnapshot(position).getId()).update("status", false,"sponsorUid",
                        SponsorDashboard.uid)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                SponsorAdapter.this.notifyItemRemoved(position);
                            }
                        });
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.isNgo = false;
                RequestDetailsActivity.requestModel = model;
                RequestDetailsActivity.requestUserUid = model.getUserId();
                Context context = holder.itemView.getContext();
                context.startActivity(new Intent(context, RequestDetailsActivity.class));
            }
        });

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView requestTv, dateTV;
        Button approveBtn, declineBtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            requestTv = itemView.findViewById(R.id.requestText);
            dateTV = itemView.findViewById(R.id.dateText);
            approveBtn = itemView.findViewById(R.id.approve);
            declineBtn = itemView.findViewById(R.id.decline);
        }


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.all_request_item, parent, false);
        return new MyViewHolder(view);
    }


}
