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
//                Message newMessage = new Message(chatIntentEmail, body);
                //TODO : if do timestamp, put here


                displayChatMessages();
                //TODO : add the new message to arraylist of messages in the current clicked chat

                // Clear the input
                textViewMessage.setText("");
            }
        });
    }

    private void updateChatDatabase() {
        ChatsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                updateChatData = dataSnapshot;
//                Toast.makeText(getApplicationContext(), "id: " + id, Toast.LENGTH_SHORT).show();

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void displayChatMessages() {
//        TODO : for every chat in the "ChatDatabase", find one that matches email 1/email 2 value combo
//        TODO : messageList = matching chat's messageList updated from the db
//
//        TODO : if doesnt work, try updateRequestSnapshot and hasRequestsConflict approach from CreateRequestActivity
//
        ChatsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                messageList.clear();
                for(DataSnapshot chatSnapshot : dataSnapshot.getChildren()) {
                    //get information from the chat
                    HashMap<String,String> emails = (HashMap<String,String>)chatSnapshot.getValue();
                    HashMap<String,ArrayList<Message>> list = (HashMap<String,ArrayList<Message>>)chatSnapshot.getValue();
                    String id = emails.get("chatId");
                    String user1 = emails.get("emailUser1");
                    String user2 = emails.get("emailUser2");
                    Chat checkChat = new Chat(id, user1, user2, list.get("messageList"));

                    //check if the chat is the current chat
                    if(checkChat.getChatId().equals(currentChat.getChatId())) {
                        //refresh and get all the messages
                        messageList = checkChat.getMessageList();
                    }

                }
                MessageList adapter = new MessageList(ChatMessageActivity.this, messageList);
                listViewChatMessages.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
