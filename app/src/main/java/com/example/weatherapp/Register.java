package com.example.weatherapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {

    EditText uUserName,uAddress,uNumber,uEmail,uPassword;
    Button RegisterBtn;
    TextView LoginBtn;
    FirebaseAuth fAuth;
    ProgressBar progressBar;
    member member;
    DatabaseReference referance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        uUserName = findViewById(R.id.username);
        uAddress = findViewById(R.id.address);
        uNumber = findViewById(R.id.number);
        uEmail = findViewById(R.id.email);
        uPassword = findViewById(R.id.password);
        RegisterBtn = findViewById(R.id.registerBtn);
        LoginBtn = findViewById(R.id.createText2);

        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
        member = new member();
        referance = FirebaseDatabase.getInstance().getReference().child("member");

        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = uEmail.getText().toString().trim();
                String password = uPassword.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    uEmail.setError("Email is Required");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    uPassword.setError("Password is Required");
                    return;
                }

                if (password.length() < 6) {
                    uPassword.setError("Password Must Have 6 Characters");
                    return;
                }

                int Number = Integer.parseInt(uNumber.getText().toString());
                String UserName = uUserName.getText().toString();
                String Address = uAddress.getText().toString();
                member.setPhoneNumber(Number);
                member.setUserName(UserName);
                member.setAddress(Address);
                referance.push().setValue(member);


                progressBar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(Register.this, "User Created.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                            finish();

                        } else {
                            Toast.makeText(Register.this, "Error ! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Login.class));

            }
        });


    }
}