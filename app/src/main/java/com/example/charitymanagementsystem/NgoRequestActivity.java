package com.example.charitymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class NgoRequestActivity extends AppCompatActivity {
    EditText fundEditTExt;
    Button btnRequest;
    String fundText;
    String requestDate;

    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;
    RecyclerView recyclerView;
    SponsorAdapter sponsorAdapter;
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ngo_request);

        fundEditTExt = findViewById(R.id.fundEditText);
        btnRequest = findViewById(R.id.btnRequest);
        progressBar = findViewById(R.id.progressbar);
        //Inititating firebase instances
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();


        String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String currentTime = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        requestDate = currentTime+" "+currentDate;
        Log.d("requestDAte",currentDate+" "+currentTime);
        //.....................................................

        btnRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                fundText = fundEditTExt.getText().toString();
                final int id = new Random().nextInt(10000)+new Random().nextInt(10000);
                storeRequest(id,fundText,false,0,requestDate);
            }
        });
    }

    public void storeRequest(int id, String requestText,boolean status,int count,String requestDate)
    {
        documentReference = firebaseFirestore.collection(Constants.FUND_REQUEST).document(String.valueOf(id));
        Map<String, Object> request = new HashMap<>();
        request.put(Constants.ID, String.valueOf(id));
        request.put(Constants.REQUEST_TEXT, requestText);
        request.put(Constants.STATUS, status);
        request.put(Constants.COUNT, count);
        request.put(Constants.REQUEST_DATE, requestDate);
        request.put(Constants.ACCEPT_DATE, "-1");
        request.put(Constants.USER_ID, mAuth.getUid());
        request.put(Constants.SPONSOR_ID, "-1");

        documentReference.set(request).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                progressBar.setVisibility(View.GONE);
                fundEditTExt.setText("");
                Toast.makeText(NgoRequestActivity.this,"Succeed", Toast.LENGTH_LONG).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(NgoRequestActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();

            }
        });
    }

}