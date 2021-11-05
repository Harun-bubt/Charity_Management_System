package com.example.charitymanagementsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class SponsorLoginActivity extends AppCompatActivity {
    TextView signUpTv;
    private FirebaseAuth mAuth;
    EditText emailET, passET;
    TextView forgetPass;
    Button loginBtn;
    ProgressBar progressBar;
    FirebaseUser user;
    String type;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sponser_login);
        getSupportActionBar().setTitle("Sponsor Login");
        mAuth = FirebaseAuth.getInstance();
        findById();
        clickListener();
    }

    private void findById() {
        signUpTv=findViewById(R.id.signUpTv);
        progressBar = findViewById(R.id.progressbar);
        emailET = findViewById(R.id.emailET);
        passET = findViewById(R.id.passwordET);
        forgetPass = findViewById(R.id.forgetpass);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void clickListener() {
        signUpTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SponsorLoginActivity.this, SponsorRegisterActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = emailET.getText().toString();
                String password = passET.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    emailET.setError("Provide a Valid Email");
                    emailET.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    passET.setError("Password is Required");
                    passET.requestFocus();
                    return;
                }
                if (password.length() < 6) {

                    passET.setError("Min Password length should be 6 characters");
                    passET.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    user = mAuth.getCurrentUser();
                                    {
                                        FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
                                        firebaseFirestore.collection("users").document(mAuth.getCurrentUser().getUid())
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                type = documentSnapshot.getString("type");
                                                status = documentSnapshot.getString("status");

                                                user = mAuth.getCurrentUser();
                                                {

                                                    if (user.isEmailVerified()) {
                                                        if (type.equals("sponsor")) {
                                                            if(status.equals("Approve"))
                                                            {
                                                                progressBar.setVisibility(View.GONE);
                                                                Intent intent = new Intent(SponsorLoginActivity.this, SponsorDashboard.class);
                                                                startActivity(intent);
                                                                finish();
                                                            }else if(status.equals("under review"))
                                                            {
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(SponsorLoginActivity.this, "Your account not approved yet!!", Toast.LENGTH_SHORT).show();
                                                            }else
                                                            {
                                                                progressBar.setVisibility(View.GONE);
                                                                Toast.makeText(SponsorLoginActivity.this, "Your account rejected!!", Toast.LENGTH_SHORT).show();
                                                            }

                                                        } else {
                                                            Toast.makeText(SponsorLoginActivity.this, "This is not a NGO type Account", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        Toast.makeText(SponsorLoginActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                                                    }


                                                }
                                            }
                                        });

                                    }
                                } else {
                                    Toast.makeText(SponsorLoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(SponsorLoginActivity.this, "Login failed due to" + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder passResetDialog = new AlertDialog.Builder(v.getContext());
                passResetDialog.setTitle("Reset Password");
                passResetDialog.setMessage("Enter your email to receive a reset link");
                passResetDialog.setView(resetMail);
                passResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(SponsorLoginActivity.this, "Reset link sent to your email Address", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SponsorLoginActivity.this, "Error !Reset link not sent" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
                passResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                passResetDialog.create().show();
            }
        });

    }
}