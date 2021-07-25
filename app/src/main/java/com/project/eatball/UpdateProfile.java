package com.project.eatball;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class UpdateProfile extends AppCompatActivity {

    private EditText fullname, age;
    private ImageView imageView;
    private Uri filePath;
    private String picname;
    private RadioGroup radioGroup;
    private  RadioButton gender;
    public String generatedFilePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    FirebaseStorage storage2;
    StorageReference storageReference;
    StorageReference ref2;
    Button updateUser,updatephoto;
    DatabaseReference reference;
    DatabaseReference rfr;
    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private TextView uptfullname, uptemail, uptgender, uptage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);
        getSupportActionBar().hide();//hiding top bar


        uptfullname = (TextView)findViewById(R.id.uptfullname);
        uptemail = (TextView)findViewById(R.id.uptemail);
        uptgender = (TextView)findViewById(R.id.uptgender);
        uptage = (TextView)findViewById(R.id.uptage);

        rfr = FirebaseDatabase.getInstance().
                getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        rfr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String strfullname = snapshot.child("fullName").getValue(String.class);
                String stremail = snapshot.child("email").getValue(String.class);
                String strage = snapshot.child("phoneNO").getValue(String.class);
                String strgender = snapshot.child("gender").getValue(String.class);
                String link = snapshot.child("profilepic").getValue(String.class);

                picname = stremail;

                strfullname = "Name:"+ strfullname;
                stremail = "Email:"+ stremail;
                strage = "Mobile Number:"+ strage;
                strgender = "Gender:"+ strgender;
                Log.d("My Activity", "Name: " + strfullname);
                Log.d("My Activity", "Email: " + stremail);
                uptfullname.setText(strfullname);
                uptemail.setText(stremail);
                uptage.setText(strage);
                uptgender.setText(strgender);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
            imageView = findViewById(R.id.profilephoto);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectImage();
            }
        });



        reference = FirebaseDatabase.getInstance().getReference("Users");

        fullname = findViewById(R.id.nameupdate);
        age = findViewById(R.id.ageupdate);
        updateUser = findViewById(R.id.updateUser);
        //updateUser.setOnClickListener(this::update);
        updatephoto =findViewById(R.id.updatepohto);
        updatephoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        updateUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(v);
            }
        });
    }

    public void update(View view){
        radioGroup = (RadioGroup) findViewById(R.id.genderupdate);

        int selectedId = radioGroup.getCheckedRadioButtonId();
        gender = (RadioButton) findViewById(selectedId);
        if(age.length() != 0){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("phoneNO").setValue(age.getText().toString());
        }
        if(fullname.length() != 0){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fullName").setValue(fullname.getText().toString());
        }
        if(gender.isChecked()){
            reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("gender").setValue(gender.getText());

        }


        Toast.makeText(this, "Data has been  update succesfully", Toast.LENGTH_LONG).show();

    }

    private void SelectImage()
    {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode,
                                    int resultCode,
                                    Intent data)
    {

        super.onActivityResult(requestCode,
                resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            filePath = data.getData();
            try {

                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                imageView.setImageBitmap(bitmap);
            }

            catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private void uploadImage()
    {
        if (filePath != null) {

            ProgressDialog progressDialog
                    = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref
                    = storageReference.child("images/profilepic/"+picname);


            StorageTask<UploadTask.TaskSnapshot> uploadTask = ref.putFile(filePath);
            ref.putFile(filePath)
                    .addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {




                                    progressDialog.dismiss();
                                    Toast
                                            .makeText(UpdateProfile.this,
                                                    "Image Uploaded!!",
                                                    Toast.LENGTH_SHORT)
                                            .show();
                                }
                            })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e)
                        {

                            progressDialog.dismiss();
                            Toast
                                    .makeText(UpdateProfile.this,
                                            "Failed " + e.getMessage(),
                                            Toast.LENGTH_SHORT)
                                    .show();
                        }
                    })
                    .addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {

                                @Override
                                public void onProgress(
                                        UploadTask.TaskSnapshot taskSnapshot)
                                {
                                    double progress
                                            = (100.0
                                            * taskSnapshot.getBytesTransferred()
                                            / taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage(
                                            "Uploaded "
                                                    + (int)progress + "%");
                                }
                            });

            storage2 = FirebaseStorage.getInstance();
            ref2 = storage2.getReference();

            StorageReference riversRef = ref2.child("images/profilepic/"+picname);
            uploadTask = riversRef.putFile(filePath);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }

                    Log.d("PICCCCC", String.valueOf(ref.getDownloadUrl()));
                    return ref.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = task.getResult();
                        Log.d("PICCCCC2222222222222", String.valueOf(downloadUri));
                        reference.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("profilepic").setValue(String.valueOf(downloadUri));

                    } else {

                    }
                }
            });


                }
    }

}
