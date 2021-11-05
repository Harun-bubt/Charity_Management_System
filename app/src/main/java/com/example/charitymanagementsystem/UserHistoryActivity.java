package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class UserHistoryActivity extends AppCompatActivity {
    TextView requetedUserName,requestedUserEmail,levelTv,historyLevel;
    FirebaseFirestore firebaseFirestore;
    public  static  Model user;
    RecyclerView recyclerView;
    HistoryAdapter historyAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        requetedUserName = findViewById(R.id.requestUserName);
        requestedUserEmail = findViewById(R.id.requestEmail);
        levelTv = findViewById(R.id.levelTv);
        historyLevel = findViewById(R.id.levelHistoryTv);
        recyclerView = findViewById(R.id.requestRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseFirestore = FirebaseFirestore.getInstance();


        if(AdminDashBoardActivity.isSponsor)
        {
            levelTv.setText("Sponsor details :");
            requetedUserName.setText("Name: " + user.getUsername());
            requestedUserEmail.setText("Email: " + user.getEmail());
            historyLevel.setText("Sponsor Previous History : ");
            //Get sponsor all history.............
            Query query = FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).whereEqualTo(Constants.SPONSOR_ID,user.getId());
            FirestoreRecyclerOptions<RequestModel> options = new FirestoreRecyclerOptions.Builder<RequestModel>()
                    .setLifecycleOwner(this)
                    .setQuery(query, RequestModel.class)
                    .build();
            historyAdapter=new HistoryAdapter(options);
            recyclerView.setAdapter(historyAdapter);

        }else
        {
            levelTv.setText("NGO Details:");
            requetedUserName.setText("Name: " + user.getUsername());
            requestedUserEmail.setText("Email: " + user.getEmail());
            historyLevel.setText("NGO Previous History : ");
            //Get sponsor all history.............
            Query query = FirebaseFirestore.getInstance().collection(Constants.FUND_REQUEST).whereEqualTo(Constants.USER_ID,user.getId());
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