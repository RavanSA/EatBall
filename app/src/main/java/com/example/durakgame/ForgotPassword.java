package com.example.durakgame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText forgetemail;
    private Button forgetsend;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        getSupportActionBar().hide();//hiding top bar

        mAuth = FirebaseAuth.getInstance();
        forgetemail = (EditText) findViewById(R.id.emailforget);
        forgetsend = (Button) findViewById(R.id.forgetemailsend);
        forgetsend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                forgotPassword();
            }
        });
    }

    private void forgotPassword() {
        String forgotemail = forgetemail.getText().toString().trim();
        if(forgotemail.isEmpty()){
            forgetemail.setError("Email Addres required");
            forgetemail.requestFocus();
            return;
        }
        if (!forgotemail.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {//email validation with regex
            forgetemail.setError("Please provide a valid email address");
            forgetemail.requestFocus();
            return;
        }
        mAuth.sendPasswordResetEmail(forgotemail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(ForgotPassword.this, "Check your email to reset your password", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(ForgotPassword.this, "Try Again", Toast.LENGTH_LONG).show();

                }
            }
        });

    }

}