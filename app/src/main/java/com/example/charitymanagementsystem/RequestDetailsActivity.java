package com.example.charitymanagementsystem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class RequestDetailsActivity extends AppCompatActivity {

    TextView requestTv,requestedDate;
    TextView requetedUserName,requestedUserEmail,levelTv,historyLevel;
    FirebaseFirestore firebaseFirestore;
    Model user;
    public static String requestUserUid;
    public static RequestModel requestModel;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_request);

        requestTv = findViewById(R.id.requestTv);
        requestedDate = findViewById(R.id.dateTV);
        requestedDate = findViewById(R.id.dateTV);
        requetedUserName = findViewById(R.id.requestUserName);
        requestedUserEmail = findViewById(R.id.requestEmail);
        levelTv = findViewById(R.id.levelTv);
        historyLevel = findViewById(R.id.levelHistoryTv);
        recyclerView = findViewById(R.id.requestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();

        requestTv.setText(requestModel.getRequestText());
        requestedDate.setText(requestModel.getRequestDate());

        if(MainActivity.isNgo)
        {
            levelTv.setText("Sponsor details :");
            historyLevel.setText("Sponsor Previous History : ");
            firebaseFirestore.collection("users").document(requestModel.getSponsorUid()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Model model = documentSnapshot.toObject(Model.class);
                    requetedUserName.setText("Name: " + model.getUsername());
                    requestedUserEmail.setText("Email: " + model.getEmail());

                }
            });
            //Get sponsor all history.............
            Query query = FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).whereEqualTo(Constants.SPONSOR_ID,requestModel.getSponsorUid());
            FirestoreRecyclerOptions<RequestModel> options = new FirestoreRecyclerOptions.Builder<RequestModel>()
                    .setLifecycleOwner(this)
                    .setQuery(query, RequestModel.class)
                    .build();
            historyAdapter=new HistoryAdapter(options);
            recyclerView.setAdapter(historyAdapter);

        }else
        {
            levelTv.setText("Requested User Details:");
            historyLevel.setText("NGO Previous History : ");
            firebaseFirestore.collection("users").document(requestUserUid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Model model = documentSnapshot.toObject(Model.class);
                    requetedUserName.setText("Name: " + model.getUsername());
                    requestedUserEmail.setText("Email: " + model.getEmail());
                }
            });
            //Get sponsor all history.............
            Query query = FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).whereEqualTo(Constants.USER_ID,requestModel.getUserId());
            FirestoreRecyclerOptions<RequestModel> options = new FirestoreRecyclerOptions.Builder<RequestModel>()
                    .setLifecycleOwner(this)
                    .setQuery(query, RequestModel.class)
                    .build();
            historyAdapter=new HistoryAdapter(options);
            recyclerView.setAdapter(historyAdapter);
        }



    }

    @Override
    protected void onStop() {
        super.onStop();
        historyAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        historyAdapter.startListening();
    }
}