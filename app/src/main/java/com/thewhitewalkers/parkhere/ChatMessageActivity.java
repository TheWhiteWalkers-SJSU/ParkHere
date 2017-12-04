package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatMessageActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    final DatabaseReference ChatsDatabase = FirebaseDatabase.getInstance().getReference("chats");

    private FloatingActionButton buttonSendChatMessage;
    private TextView textViewMessage;
    private ListView listViewChatMessages;
    private List<Message> messageList = new ArrayList<>();
    private Chat currentChat;
    private static DataSnapshot updateChatData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        buttonSendChatMessage = findViewById(R.id.buttonSendChatMessage);
        textViewMessage = findViewById(R.id.textViewMessage);
        listViewChatMessages = findViewById(R.id.listViewChatMessages);

        //get clicked chat from intent
        Intent chatIntent = getIntent();
        currentChat = (Chat) chatIntent.getSerializableExtra("chat");

        updateChatDatabase();
        //initial display of chat messages
        displayChatMessages();

        buttonSendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //get input, add new messsage to current chat
                String body = textViewMessage.getText().toString().trim();
                Message newMessage = new Message(currentUser.getEmail(), body);
                currentChat.addMessage(newMessage);
                ChatsDatabase.child(currentChat.getChatId()).setValue(currentChat);
                //Clear the input
                textViewMessage.setText("");
            }
        });
    }

    private void updateChatDatabase() {
        ChatsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateChatData = dataSnapshot;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void displayChatMessages() {
        ChatsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                //get the current chat and load its message list
                Chat checkChat = dataSnapshot.child(currentChat.getChatId()).getValue(Chat.class);
                messageList = checkChat.messageList;

                MessageList adapter = new MessageList(ChatMessageActivity.this, messageList);
                listViewChatMessages.setAdapter(adapter);
                //scroll to latest message
                listViewChatMessages.setSelection(adapter.getCount()-1);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
