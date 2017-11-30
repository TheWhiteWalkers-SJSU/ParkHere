package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;
//
//import org.json.JSONException;
//import org.json.JSONObject;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ChatActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser currentUser = firebaseAuth.getCurrentUser();
    final DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference("users").child(currentUser.getUid());
    //TODO: get chat database

    private Button buttonHomepage;
    private ListView listViewChat;
    private List<Chat> chatList = new ArrayList<>();
    private User userIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

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
                //TODO : send the clicked chat as intent
//                chatMessageIntent.putExtra("chat", clickedChat);
                startActivity(chatMessageIntent);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        //TODO : Create "chat" in the database,
        //TODO : add chats to list where the
        //TODO:             currentUser.getEmail == currentChat.getEmailUser1() || currentUser.getEmail == currentChat.getEmailUser2
//        UserDatabase.addValueEventListener(new ValueEventListener() {
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
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


//        String url = "https://androidchatapp-76776.firebaseio.com/users.json";
//
//        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
//            @Override
//            public void onResponse(String s) {
//                doOnSuccess(s);
//            }
//        },new Response.ErrorListener(){
//            @Override
//            public void onErrorResponse(VolleyError volleyError) {
//                System.out.println("" + volleyError);
//            }
//        });
//
//        RequestQueue rQueue = Volley.newRequestQueue(ChatActivity.this);
//        rQueue.add(request);
//
//        listViewChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Chat clickedChat = (Chat)parent.getItemAtPosition(position);
//
//
//                startActivity(new Intent(ChatActivity.this, ChatMessageActivity.class));
//            }
//        });
//    }
//
//    public void doOnSuccess(String s){
//        try {
//            JSONObject obj = new JSONObject(s);
//
//            Iterator i = obj.keys();
//            String key = "";
//
//            while(i.hasNext()){
//                key = i.next().toString();
//
//                if(!key.equals(UserDetails.username)) {
//                    al.add(key);
//                }
//
//                totalUsers++;
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        if(totalUsers <=1){
//            noUsersText.setVisibility(View.VISIBLE);
//            usersList.setVisibility(View.GONE);
//        }
//        else{
//            noUsersText.setVisibility(View.GONE);
//            usersList.setVisibility(View.VISIBLE);
//            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al));
//        }
//
}
