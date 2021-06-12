package com.example.durakgame;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class Chat extends AppCompatActivity {
    
    private ListView listView;
    DatabaseReference reference;
    String strfullname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        getSupportActionBar().hide();//hiding top bar
        displayChatMessages();

        //private FirebaseListAdapter<ChatMessage> adapter;
        //private Firebase mRef;

        reference = FirebaseDatabase.getInstance().
                getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                strfullname = snapshot.child("fullName").getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        listView = findViewById(R.id.list_of_messages);

       // displayChatMessages();

        FloatingActionButton fab =
                (FloatingActionButton)findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText input = (EditText)findViewById(R.id.input);

                FirebaseDatabase.getInstance()
                        .getReference("Chat")
                        .push()
                        .setValue(new ChatMessage(input.getText().toString(),strfullname
                        ));

                input.setText("");
            }
        });

    }

    private void displayChatMessages() {
        DatabaseReference databaseReference;
        ListView listView;
        ArrayList<String> arrayList = new ArrayList<>();
        ArrayAdapter<String> arrayAdapter;
        TextView messageText = (TextView) findViewById(R.id.message_text);
        TextView messageUser = (TextView) findViewById(R.id.message_user);
        TextView messageTime = (TextView) findViewById(R.id.message_time);

        final ChatMessage[] chat = new ChatMessage[1];

        databaseReference = FirebaseDatabase.getInstance().getReference("Chat");
        listView = (ListView)findViewById(R.id.list_of_messages);
        arrayAdapter = new ArrayAdapter<String>(this,R.layout.message,R.id.message_text,arrayList);
        listView.setAdapter(arrayAdapter);
        LinkedHashSet<String> linkedHashSet = new LinkedHashSet<String>();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds : snapshot.getChildren()) {
                    chat[0] = ds.getValue(ChatMessage.class);
                    listView.post(new Runnable() {
                        @Override
                        public void run() {
                            // Select the last row so it will scroll into view...
                            listView.setSelection(arrayAdapter.getCount() - 1);
                        }
                    });
                    linkedHashSet.addAll(arrayList);
                    arrayList.clear();
                    arrayList.addAll(linkedHashSet);
                    arrayList.add("Username:  " + chat[0].getMessageUser()+ "\n " + chat[0].getMessageText());


                    Log.i("ARRAYLIST","TESTİNG ARR"+ arrayList);
                    Log.i("DATASNAPSHOT","DS"+ ds);

                    //String strtxt= messageText.getText().toString().trim();
                    //strtxt = chat[0].getMessageText();
                    //messageText.setText(strtxt);
                    //messageUser.setText(chat[0].getMessageUser());

                    // Format the date before showing it
                    //messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
                      //      chat[0].getMessageTime()));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}