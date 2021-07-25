package com.project.eatball;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegisterUser extends AppCompatActivity implements View.OnClickListener {
    private EditText Textfullname, Textage, Textemail, Textpassword, Textconfirmpassword,edittext;
    private RadioButton male;
    private RadioButton female;
    private Button registerUser;
    private FirebaseAuth mAuth;
    Calendar myCalendar = Calendar.getInstance();
    public String strDOB;
    ProgressDialog progressDialog;
    public DatabaseReference reference;
    private boolean checkValidity = false;
    public String UID;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_user);
        getSupportActionBar().hide();//hiding top bar
        edittext= (EditText) findViewById(R.id.Birthday);


        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @SuppressLint("DefaultLocale")
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
                strDOB = String.format("%d/%d/%d", year, monthOfYear, dayOfMonth);
            }

        };

        edittext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                new DatePickerDialog(RegisterUser.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        registerUser = (Button) findViewById(R.id.registerUser);
        registerUser.setOnClickListener(this);

        Textfullname = (EditText) findViewById(R.id.fullname);
        Textage = (EditText) findViewById(R.id.age);
        Textemail = (EditText) findViewById(R.id.email);
        Textpassword = (EditText) findViewById(R.id.password);
        Textconfirmpassword = (EditText) findViewById(R.id.confirmpassword);

        male=(RadioButton) findViewById(R.id.male);
        female=(RadioButton) findViewById(R.id.female);

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
        progressDialog = ProgressDialog.show(this, "","This may take some time.", true);


        String email = Textemail.getText().toString().trim();
        String fullname = Textfullname.getText().toString().trim();
        String password = Textpassword.getText().toString().trim();
        String confirmpassword = Textconfirmpassword.getText().toString().trim();
        String age = Textage.getText().toString().trim();
        String gender = "null";


        if (!email.matches("[a-zA-Z0-9._-]+@[a-z]+\\.[a-z]+")) {//email validation with regex
            Textemail.setError("Please provide a valid email address");
            Textemail.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if (email.isEmpty()) {//email validation with regex
            Textemail.setError("Email is required");
            Textemail.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if (age.isEmpty()) {
            Textage.setError("Phone Number is required");
            Textage.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if (fullname.isEmpty()) {
            Textfullname.setError("Name is required");
            Textfullname.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if (password.isEmpty()) {
            Textpassword.setError("Password is required");
            Textpassword.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if(password.length() <= 7){
            Textpassword.setError("Password length must be greater than 7");
            Textpassword.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if (!password.equals(confirmpassword)) {
            Textconfirmpassword.setError("Confirm password and password must be equal");
            Textconfirmpassword.requestFocus();
            progressDialog.dismiss();

            return;
        }
        if(male.isChecked()){
            gender = "Male";
        } else if(female.isChecked()){
            gender ="Female";
        }

        String finalGender = gender;
        String profilepic = "https://firebasestorage.googleapis.com/v0/b/durakgame-6af91.appspot.com/o/images%2Fprofilepic%2FLKJHGF.png?alt=media&token=521d4493-fa9c-4ad1-869e-910d83276465";
        Log.d("MYACTİVİTY","msg"+profilepic);
        Log.d("MYACTİVİTY","DATE"+strDOB);
        checkValidity = true;

        int level = 0;
        int coin = 0;
        int xp = 0;
        mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                progressDialog.dismiss();
                                User user =
                                        new User(fullname, email, age, finalGender,strDOB,profilepic,0,0,0);
                                FirebaseDatabase.getInstance().getReference("Users")
                                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {



                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            FirebaseUser user = mAuth.getCurrentUser(); //You Firebase user

                                            Toast.makeText(RegisterUser.this, "User registered succesfuly", Toast.LENGTH_LONG).show();
                                        } else {
                                            Toast.makeText(RegisterUser.this, "Fail", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }
                        }
                    });
        Log.i("CHECK VALIDITY", String.valueOf(checkValidity));

        if(checkValidity){

           /* FirebaseDatabase.getInstance()
                    .getReference("GameInfo")
                    .child(fullname)
                    .push()
                    .setValue(new GameInfo(0,0,0,0
                    ));*/


        }

    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edittext.setText(sdf.format(myCalendar.getTime()));
    }
}
