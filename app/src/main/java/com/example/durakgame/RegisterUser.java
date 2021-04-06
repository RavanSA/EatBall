package com.example.durakgame;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private EditText Textfullname, Textage, Textemail, Textpassword, Textconfirmpassword;
    private FirebaseAuth mAuth;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);

        mAuth = FirebaseAuth.getInstance();
        TextView registerUser = (TextView) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        Textfullname = (EditText) findViewById(R.id.fullname);
        Textage = (EditText) findViewById(R.id.age);
        Textemail = (EditText) findViewById(R.id.email);
        Textpassword = (EditText) findViewById(R.id.password);
        Textconfirmpassword = (EditText) findViewById(R.id.confirmpassword);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.registerUser:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String email = Textemail.getText().toString().trim();
        String fullname = Textfullname.getText().toString().trim();
        String password = Textpassword.getText().toString().trim();
        String confirmpassword = Textconfirmpassword.getText().toString().trim();
        String age = Textage.getText().toString().trim();
        int ageInt = Integer.parseInt(age);

        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {//email validation with regex
            Textemail.setError("Please provide a valid email address");
            Textemail.requestFocus();
            return;
        }
        if (email.isEmpty()) {//email validation with regex
            Textemail.setError("Email is required");
            Textemail.requestFocus();
            return;
        }
        if (age.isEmpty()) {
            Textage.setError("Age is required");
            Textage.requestFocus();
            return;
        }
        if (fullname.isEmpty()) {
            Textfullname.setError("Name is required");
            Textfullname.requestFocus();
            return;
        }
        if (password.isEmpty()) {
            Textpassword.setError("Password is required");
            Textpassword.requestFocus();
            return;
        }
        if (!password.equals(confirmpassword)) {
            Textconfirmpassword.setError("Confirm password and password must be equal");
            Textconfirmpassword.requestFocus();
            return;
        }

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                User user = new User(fullname, email, age);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Toast.makeText(RegisterUser.this, "User registered succesfuly", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegisterUser.this, "Fail", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });

    }
}
