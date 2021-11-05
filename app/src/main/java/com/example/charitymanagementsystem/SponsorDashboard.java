package com.example.charitymanagementsystem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SponsorDashboard extends AppCompatActivity {

    private FirebaseAuth mAuth;
    public  FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    SponsorAdapter sponsorAdapter;
    public static String uid;
    public static  String acceptedDate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser_dashboard);
        getSupportActionBar().setTitle("Sponsor Dashboard");
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        acceptedDate = currentTime+" "+currentDate;
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.sponsorRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //query to load data
        Query query = FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).whereNotEqualTo("sponsorUid",uid);

        FirestoreRecyclerOptions<RequestModel> options = new FirestoreRecyclerOptions.Builder<RequestModel>()
                .setLifecycleOwner(this)
                .setQuery(query, RequestModel.class)
                .build();
        sponsorAdapter=new SponsorAdapter(options);


        recyclerView.setAdapter(sponsorAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        sponsorAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        sponsorAdapter.stopListening();
    }





}