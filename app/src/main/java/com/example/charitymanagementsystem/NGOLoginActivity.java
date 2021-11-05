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

public class NGOLoginActivity extends AppCompatActivity {
    TextView signUp;
    private FirebaseAuth mAuth;
    EditText email, pass;
    TextView forgetPass;
    Button loginBtn;
    ProgressBar progressBar;
    FirebaseUser user;
    String type;
    String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_n_g_o_login);
        getSupportActionBar().setTitle("NGO Login");
        mAuth = FirebaseAuth.getInstance();
        findById();
        clickListener();
    }

    private void findById() {
        signUp = findViewById(R.id.signUpTv);
        progressBar = findViewById(R.id.progressbar);
        email = findViewById(R.id.emailET);
        pass = findViewById(R.id.passwordET);
        forgetPass = findViewById(R.id.forgetpass);
        loginBtn = findViewById(R.id.loginBtn);
    }

    private void clickListener() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NGOLoginActivity.this, NGORegisterActivity.class);
                startActivity(intent);
            }
        });
        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = NGOLoginActivity.this.email.getText().toString();
                String password = pass.getText().toString();
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                    NGOLoginActivity.this.email.setError("Provide Valid Email");
                    NGOLoginActivity.this.email.requestFocus();
                    return;
                }
                if (password.isEmpty()) {
                    pass.setError("Password is Required");
                    pass.requestFocus();
                    return;
                }
                if (password.length() < 6) {

                    pass.setError("Min Password length should be 6 characters");
                    pass.requestFocus();
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                mAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
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
                                                    if (type.equals("NGO")) {
                                                        if(status.equals("Approve"))
                                                        {
                                                            progressBar.setVisibility(View.GONE);
                                                            Intent intent = new Intent(NGOLoginActivity.this, NGODashboardActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                        }else if(status.equals("under review"))
                                                        {
                                                            progressBar.setVisibility(View.GONE);
                                                            Toast.makeText(NGOLoginActivity.this, "Your account not approved yet!!", Toast.LENGTH_SHORT).show();
                                                        }else
                                                        {
                                                            progressBar.setVisibility(View.GONE);
                                                            Toast.makeText(NGOLoginActivity.this, "Your account rejected!!", Toast.LENGTH_SHORT).show();
                                                        }

                                                    } else {
                                                        Toast.makeText(NGOLoginActivity.this, "This is not a NGO type Account", Toast.LENGTH_SHORT).show();
                                                    }

                                                } else {
                                                    Toast.makeText(NGOLoginActivity.this, "Please verify your email", Toast.LENGTH_LONG).show();
                                                }


                                            }
                                        }
                                    });

                                } else {
                                    Toast.makeText(NGOLoginActivity.this, "Login failed", Toast.LENGTH_LONG).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(NGOLoginActivity.this, "Login failed due to" + e.getMessage(), Toast.LENGTH_LONG).show();

                    }
                });


            }
        });
        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText resetMail = new EditText(v.getContext());
                AlertDialog.Builder PassResetDialog = new AlertDialog.Builder(v.getContext());
                PassResetDialog.setTitle("Reset Password");
                PassResetDialog.setMessage("Enter your email to receive reset link");
                PassResetDialog.setView(resetMail);
                PassResetDialog.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String mail = resetMail.getText().toString();
                        mAuth.sendPasswordResetEmail(mail).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(NGOLoginActivity.this, "Reset link sent to your email Address", Toast.LENGTH_LONG).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(NGOLoginActivity.this, "Error! Reset link not sent" + e.getMessage(), Toast.LENGTH_LONG).show();

                            }
                        });
                    }
                });
                PassResetDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                PassResetDialog.create().show();
            }
        });
    }
}