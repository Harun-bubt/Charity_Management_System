package com.example.charitymanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class NgoAdapter extends FirestoreRecyclerAdapter<RequestModel, NgoAdapter.MyViewHolder> {
//Adapter to show data in recycler view in admin dashboard

    //constructor
    public NgoAdapter(@NonNull FirestoreRecyclerOptions<RequestModel> options) {
        super(options);
    }



    //view holder to bind views of custom made layout file
    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder,int position, @NonNull RequestModel model) {
        holder.requestTv.setText(model.getRequestText());
        holder.requestDate.setText("Requested: "+model.getRequestDate());
        if(model.isStatus())
        {
            holder.statusTV.setText("Accepted");
            holder.statusTV.setTextColor(Color.parseColor("#00FF00"));
            holder.acceptedDate.setVisibility(View.VISIBLE);
            holder.acceptedDate.setText("Accepted:"+model.getAcceptDate());
        }else
        {
            if(model.getSponsorUid().equals("-1"))
            {
                holder.statusTV.setText("Not Accepted");
                holder.statusTV.setTextColor(Color.parseColor("#FF0000"));
                holder.acceptedDate.setVisibility(View.GONE);
            }else
            {
                holder.statusTV.setText("Not Accepted");
                holder.statusTV.setTextColor(Color.parseColor("#FF0000"));
                holder.acceptedDate.setVisibility(View.GONE);
            }

        }
        if(model.isStatus())
        {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.isNgo = true;
                    RequestDetailsActivity.requestModel = model;
                    RequestDetailsActivity.requestUserUid = model.getUserId();
                    Context context = holder.itemView.getContext();
                    context.startActivity(new Intent(context, RequestDetailsActivity.class));
                }
            });

        }

    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView requestTv, requestDate,acceptedDate;
        TextView statusTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            requestTv = itemView.findViewById(R.id.requestText);
            requestDate = itemView.findViewById(R.id.requestDateText);
            acceptedDate = itemView.findViewById(R.id.acceptedDateText);
           statusTV = itemView.findViewById(R.id.status);
        }


    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflating layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.show_request_item, parent, false);
        return new MyViewHolder(view);
    }


}
