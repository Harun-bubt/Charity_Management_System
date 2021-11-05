package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    //view intialization
Button adminBtn,sponserBtn,ngoBtn;
public  static boolean isNgo = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        //view binding
        adminBtn=findViewById(R.id.adminBtn);
        sponserBtn=findViewById(R.id.sponserBtn);
        ngoBtn=findViewById(R.id.ngoBtn);
        //button clicklistner method
        clicklistner();
    }

    private void clicklistner() {
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,AdminLoginActivity.class);
                startActivity(intent);
            }
        });
        sponserBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this, SponsorLoginActivity.class);
                startActivity(intent);
            }
        });
        ngoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,NGOLoginActivity.class);
                startActivity(intent);
            }
        });


    }
}