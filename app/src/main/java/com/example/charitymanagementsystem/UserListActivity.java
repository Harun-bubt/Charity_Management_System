package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponsor_list);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //query to load data

        Query query;
        if(AdminDashBoardActivity.isSponsor)
        {
            getSupportActionBar().setTitle("All sponsors");
            query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("type", "sponsor");

        }else
        {
            getSupportActionBar().setTitle("All NGO");
            query = FirebaseFirestore.getInstance().collection("users").whereEqualTo("type", "NGO");
        }

        FirestoreRecyclerOptions<Model> options = new FirestoreRecyclerOptions.Builder<Model>()
                .setLifecycleOwner(this)
                .setQuery(query, Model.class)
                .build();
        listAdapter = new ListAdapter(options);
        recyclerView.setAdapter(listAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();
        listAdapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();

        listAdapter.stopListening();
    }

}