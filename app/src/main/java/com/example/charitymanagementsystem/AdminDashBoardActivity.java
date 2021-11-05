package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminDashBoardActivity extends AppCompatActivity {

    Button btnVerify,btnSponsor,btnNgo;
    public static  boolean isSponsor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_dash_board2);
        getSupportActionBar().hide();
        btnVerify = findViewById(R.id.btnVerify);
        btnSponsor = findViewById(R.id.btnManageSponsor);
        btnNgo = findViewById(R.id.btnManageNgo);
        btnVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminDashBoardActivity.this,VerifyAcountActivity.class);
                startActivity(intent);
            }
        });
        btnSponsor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSponsor = true;
                Intent intent = new Intent(AdminDashBoardActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
        btnNgo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isSponsor = false;
                Intent intent = new Intent(AdminDashBoardActivity.this, UserListActivity.class);
                startActivity(intent);
            }
        });
    }
}