package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    final DatabaseReference ChatDatabase = FirebaseDatabase.getInstance().getReference("chats");

    private Button buttonHomepage;
    private ListView listViewChat;
    private List<Chat> chatList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        buttonHomepage = findViewById(R.id.buttonHomepage);
        listViewChat = findViewById(R.id.listViewChats);

        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        listViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final Chat clickedChat = (Chat) parent.getItemAtPosition(position);
                Intent chatMessageIntent = new Intent(getApplicationContext(), ChatMessageActivity.class);
                //send the clicked chat as intent
                chatMessageIntent.putExtra("chat", clickedChat);
                startActivity(chatMessageIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ChatDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatList.clear();
                for(DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    //get information from the chat
                    HashMap<String,String> emails = (HashMap<String,String>)chatSnapshot.getValue();
                    HashMap<String,ArrayList<Message>> list = (HashMap<String,ArrayList<Message>>)chatSnapshot.getValue();
                    String id = emails.get("chatId");
                    String user1 = emails.get("emailUser1");
                    String user2 = emails.get("emailUser2");
                    Chat checkChat = new Chat(id, user1, user2, list.get("messageList"));

                    //only display chats for the current user
                    if(checkChat.getEmailUser1().equals(currentUser.getEmail()) || checkChat.getEmailUser2().equals(currentUser.getEmail())) {
                        chatList.add(checkChat);
                    }
                }
                ChatList adapter = new ChatList(ChatActivity.this, chatList);
                adapter.setCurrentChatEmail(currentUser.getEmail());
                listViewChat.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
