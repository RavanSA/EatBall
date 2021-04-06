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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements  View.OnClickListener{
    private EditText Textemaillogin, Textpasswordlogin;
    private Button btnsignin;
    private FirebaseAuth mAuth;
    private TextView register,forgetpassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();//hiding top bar
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(this);

        Textemaillogin = (EditText) findViewById(R.id.emaillgn);
        Textpasswordlogin = (EditText) findViewById(R.id.passwordlgn);

        btnsignin = (Button) findViewById(R.id.sign_in);
        btnsignin.setOnClickListener(this);

        forgetpassword = (TextView) findViewById(R.id.forgetpassword);
        forgetpassword.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void  onClick(View view){
        switch (view.getId()){
            case R.id.register:
                startActivity(new Intent(this, RegisterUser.class));
                break;
            case R.id.sign_in:
                userlogin();
                break;
            case R.id.forgetpassword:
                startActivity(new Intent(MainActivity.this, ForgotPassword.class));
        }
        /*if (view.getId() == R.id.register) {
            startActivity(new Intent(this, RegisterUser.class));
        } else if (view.getId() == R.id.sign_in){
            userlogin();
        }*/
    }

    private void userlogin() {
        String emaillgn = Textemaillogin.getText().toString().trim();
        String passwordlgn = Textpasswordlogin.getText().toString().trim();


        if (!emaillgn.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {//email validation with regex
            Textemaillogin.setError("Please provide a valid email address");
            Textemaillogin.requestFocus();
            return;
        }
        if (emaillgn.isEmpty()) {//email validation with regex
            Textemaillogin.setError("Email is required");
            Textemaillogin.requestFocus();
            return;
        }
        if (passwordlgn.isEmpty()) {
            Textpasswordlogin.setError("Password is required");
            Textpasswordlogin.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(emaillgn,passwordlgn).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified()){
                        startActivity(new Intent(MainActivity.this, Profile.class));
                    } else {
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check your email to verify your account", Toast.LENGTH_LONG).show();

                    }
                } else {
                    Toast.makeText(MainActivity.this, "Fail", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
/*    public void login ( View view ) {
        TextView err;
        EditText email = (EditText)findViewById(R.id.email);
        EditText password = (EditText)findViewById(R.id.password);
        String emailstr = email.getText().toString();//converting field to string
        String passwordstr = password.getText().toString();

        Intent intent = new Intent(getApplicationContext(), successfullLogin.class);//creating new intent
        intent.putExtra(sendToActivity, emailstr);//sending emailstr to the second activity

        err = findViewById(R.id.error);
        boolean checkValidate = true;

        if ( passwordstr.length() == 0 ) {
            err.setText("Password cannot be empty");
            checkValidate = false;
        } else if ( !emailstr.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+") ) {//email validation with regex
            err.setText("Incorrect email");
            checkValidate = false;
        }

        if ( checkValidate ) {

          if (emailstr.equals("revan.sadiqli99@gmail.com")
                && passwordstr.equals("revan123")) {
                startActivity(intent);// if username or password correct second activity will start
        } else {
            err.setText("Incorrect username or password");
        }

      }
    }*/
}


