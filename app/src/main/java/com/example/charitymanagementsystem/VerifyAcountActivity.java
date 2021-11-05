package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class VerifyAcountActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dashboard);
//View binding
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
getSupportActionBar().setTitle("Admin Panel");
        //query to load data
        Query query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("status", "under review");

        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setLifecycleOwner(this)
                .setQuery(query, Model.class)
                .build();
        myadapter=new MyAdapter(options);
        Log.d("Check55d","login"+options.toString());

        recyclerView.setAdapter(myadapter);
    }
    @Override
    public void onStart() {
        super.onStart();
        myadapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        myadapter.stopListening();
    }
}