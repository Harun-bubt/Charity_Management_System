package com.example.charitymanagementsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AdminLoginActivity extends AppCompatActivity{
    //View initialization
    EditText userNameET,passwordET;
    String userName,password;
    Button loginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().setTitle("Admin Panel");
        getSupportActionBar().hide();
        //view binding
        userNameET=findViewById(R.id.userNameET);
        passwordET=findViewById(R.id.passwordET);
        loginBtn=findViewById(R.id.loginBtn);
        //login button click listener
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName=userNameET.getText().toString();
                password=passwordET.getText().toString();
                if(userName.equals("Admin512")&&password.equals("512512")){
                    Intent intent=new Intent(AdminLoginActivity.this, AdminDashBoardActivity.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(AdminLoginActivity.this, "Username or Password is wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}