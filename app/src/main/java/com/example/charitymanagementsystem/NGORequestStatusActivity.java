package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class NGORequestStatusActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    NgoAdapter ngoAdapter;
    public static String uid;
    public static  String acceptedDate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngorequest_status);
        mAuth = FirebaseAuth.getInstance();
        uid = mAuth.getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        recyclerView = findViewById(R.id.ngoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //query to load data
        Query query = FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).whereEqualTo("userId",uid);

        FirestoreRecyclerOptions<RequestModel> options = new FirestoreRecyclerOptions.Builder<RequestModel>()
                .setLifecycleOwner(this)
                .setQuery(query, RequestModel.class)
                .build();
        ngoAdapter=new NgoAdapter(options);


        recyclerView.setAdapter(ngoAdapter);

    }

    @Override
    protected void onStart() {
        super.onStart();
        ngoAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        ngoAdapter.stopListening();
    }





}