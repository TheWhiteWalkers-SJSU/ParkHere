package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatMessageActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    //TODO: get chat database
//    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
//    final DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());

    private FloatingActionButton buttonSendChatMessage;
    private TextView textViewMessage;
    private ListView listViewChatMessages;
    private List<Message> messageList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_message);

        Intent chatIntent = getIntent();
        //TODO : get clicked chat from intent
//        final String chatIntentEmail = (String) chatIntent.getSerializableExtra("email");

        //initial display of chat messages
        displayChatMessages();

        buttonSendChatMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //get input, add new messsage to current chat
                String body = textViewMessage.getText().toString().trim();
//                Message newMessage = new Message(chatIntentEmail, body);
                //TODO : if do timestamp, put here

                displayChatMessages();
                //TODO : add the new message to arraylist of messages in the current clicked chat

                // Clear the input
                textViewMessage.setText("");
            }
        });
    }

    public void displayChatMessages() {
        //TODO : listener for the "ChatDatabase"
        //TODO : for every chat in the "ChatDatabase", find one that matches email 1/email 2 value combo
        //TODO : messageList = matching chat's messageList updated from the db

        //TODO : if doesnt work, try updateRequestSnapshot and hasRequestsConflict approach from CreateRequestActivity

//        requestDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                chatList.clear();
//                for(DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
//                    User user = userSnapshot.getValue(User.class);
//                    if(user.getUserId() == currentUser.getUid()){
//                        userIntent = user;
//                        chatList = user.getChatList();
//                    }
//                }
//                ChatList adapter = new ChatList(ChatActivity.this, chatList);
//                listViewChat.setAdapter(adapter);
//            }
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }

}
