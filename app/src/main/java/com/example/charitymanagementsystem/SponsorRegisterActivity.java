package com.example.charitymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SponsorRegisterActivity extends AppCompatActivity {
    //Views and variable declarations
    EditText userNameET, passwordET, emailET, phoneNoET, addressET;
    Button registerBtn;
    String userName, password, email, address, phone;

    ProgressBar progressBar;
    private FirebaseAuth mAuth;
    FirebaseFirestore firebaseFirestore;
    DocumentReference documentReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser_register);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Sponsor Registration");
        //Initiating firebase instances
        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        //view binding
        phoneNoET = findViewById(R.id.phoneNoET);
        addressET = findViewById(R.id.adresET);
        userNameET = findViewById(R.id.userNameET);
        passwordET = findViewById(R.id.passwordET);
        emailET = findViewById(R.id.emailET);
        registerBtn = findViewById(R.id.registerBtn);
        progressBar = findViewById(R.id.progressbar);
        //Method which has click listeners
        clickListener();

    }

    private void clickListener() {
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Getting strings from Edittext
                userName = userNameET.getText().toString();
                password = passwordET.getText().toString();
                email = emailET.getText().toString();
                phone = phoneNoET.getText().toString();
                address = addressET.getText().toString();

                //Check validations
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Provide Valid Email");
                    emailET.requestFocus();
                    return;
                }
                if (userName.isEmpty()) {
                    userNameET.setError("Username is Required");
                    userNameET.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passwordET.setError("Password is Required");
                    passwordET.requestFocus();
                    return;
                }
                if (phone.isEmpty()) {
                    phoneNoET.setError("Phone no is Required");
                    phoneNoET.requestFocus();
                    return;
                }
                if (address.isEmpty()) {
                    addressET.setError("Address is Required");
                    addressET.requestFocus();
                    return;
                }
                if (password.length() < 6) {
                    passwordET.setError("Minimum Password length must be 6 characters");
                    passwordET.requestFocus();
                    return;
                }
                //registering user with firebase
                mAuth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    //Storing user credentials in firebase firestorm
                                    documentReference = firebaseFirestore.collection("users").document(Objects.requireNonNull(mAuth.getUid()));
                                    Map<String, Object> users = new HashMap<>();
                                    users.put("username", userName);
                                    users.put("Email", email);
                                    users.put("password", password);
                                    users.put("status", "under review");
                                    users.put("type", "sponsor");
                                    users.put("phone", phone);
                                    users.put("address", address);
                                    users.put("id", mAuth.getUid());
                                    documentReference.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(SponsorRegisterActivity.this, "Success", Toast.LENGTH_LONG).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(SponsorRegisterActivity.this, "error " + e.getMessage(), Toast.LENGTH_LONG).show();

                                        }
                                    });
                                    if (task.isSuccessful()) {
                                        //if user is created the do this
                                        FirebaseUser user = mAuth.getCurrentUser();
                                        assert user != null;
                                        user.sendEmailVerification().addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                progressBar.setVisibility(View.GONE);
                                                userNameET.setText("");
                                                passwordET.setText("");
                                                emailET.setText("");
                                                Toast.makeText(SponsorRegisterActivity.this, "Verification link is send on your mail", Toast.LENGTH_LONG).show();


                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Toast.makeText(SponsorRegisterActivity.this, "error2 " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();

                                            }
                                        });


                                    }
                                } else {
                                    Toast.makeText(SponsorRegisterActivity.this, "error3" + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                }


                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SponsorRegisterActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
            }

        });
    }
}