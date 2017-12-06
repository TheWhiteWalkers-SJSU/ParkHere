package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RatingActivity extends AppCompatActivity {

    Button buttonBackToListing;
    Button buttonSubmitRating;
    RatingBar ratingBar;
    EditText editTextComment;
    TextView description1;
    TextView description2;

    DatabaseReference chatsDatabase = FirebaseDatabase.getInstance().getReference("chats");
    private static DataSnapshot chatData;
    String currentChatId;
    private Listing currentListing;
    private ParkingSpot currentParking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        currentChatId = "";
        currentListing = (Listing) getIntent().getSerializableExtra("listing");
        currentParking = (ParkingSpot) getIntent().getSerializableExtra("SPOT");
        updateChatSnapshot();

        buttonBackToListing = findViewById(R.id.buttonBackToListing);
        buttonSubmitRating = findViewById(R.id.buttonSubmitRating);
        ratingBar = findViewById(R.id.ratingBar);
        editTextComment = findViewById(R.id.editTextComment);
        description1 = findViewById(R.id.description1);
        description2 = findViewById(R.id.description2);

        buttonBackToListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewListing();
            }
        });

        buttonSubmitRating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //save rating in submitRating method
                if(!submitRating()){
                    Toast.makeText(getApplicationContext(),
                            "Please enter a comment and a rating!", Toast.LENGTH_SHORT).show();
                }
                else{
                    //add message for system notification, create chat if needed
                    String ownerEmail = currentListing.getOwnerEmail();
                    String message = "You received a rating for listing \"" + currentListing.getListingName() + "\".";
                    if(!chatExists("ParkHere", ownerEmail)) {
                        createChatWithMessage("ParkHere", ownerEmail, message);
                    }
                    else {
                        addMessage(currentChatId, "ParkHere", message);
                    }
                    // redirect to listing
                    viewListing();
                }

            }
        });

        editTextComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextComment.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }
    private boolean submitRating(){
        final double numOfStars = ratingBar.getRating();
        final String comment = editTextComment.getText().toString(); //todo: save comments to database

        if(numOfStars != 0 && !TextUtils.isEmpty(comment)){
            //save the rating to listing owner
            final DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
            ParkingSpotDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ParkingSpot ps = dataSnapshot.child(currentParking.getParkingSpotId()).getValue(ParkingSpot.class);

                    if(ps.ratingsList.get(0).getRating() == 100.0){
                        ps.ratingsList.remove(0);
                    }
                  
                    Rating r = new Rating(numOfStars, comment);
                    ps.ratingsList.add(r);

                    ParkingSpotDatabase.child(currentParking.getParkingSpotId()).setValue(ps);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            return true;
        }
        return false;
    }
    private void viewListing(){
        // redirect to listing

        Intent viewListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
        viewListingIntent.putExtra("listing", currentListing);
        try {
            startActivity(viewListingIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private boolean chatExists(String check1, String check2) {
        for(DataSnapshot chatSnapshot : chatData.getChildren()) {
            //get information from the chat
            HashMap<String,String> emails = (HashMap<String,String>)chatSnapshot.getValue();
            String id = emails.get("chatId");
            String user1 = emails.get("emailUser1");
            String user2 = emails.get("emailUser2");

            //check if a chat btwn the same two users already exists
            if((user1.equals(check1) && user2.equals(check2)) || (user2.equals(check1) && user1.equals(check2))) {
                currentChatId = id;
                return true;
            }
        }
        return false;
    }

    private void createChatWithMessage(String user1, String user2, String body) {
        //create a new id
        String _id = chatsDatabase.push().getKey();
        //create the chat with additional message, save to db with the id generated
        Chat newChat = new Chat(_id, user1, user2);
        Message add = new Message("ParkHere", body);
        newChat.addMessage(add);
        chatsDatabase.child(_id).setValue(newChat);
        currentChatId = _id;
    }

    private void addMessage(String chatId, String email, String body) {
        //get the current chat
        Chat currentChat = chatData.child(chatId).getValue(Chat.class);
        //create and add the message to the chat
        Message add = new Message(email, body);
        currentChat.addMessage(add);
        //update the chat in the db
        chatsDatabase.child(chatId).setValue(currentChat);
    }

    private void updateChatSnapshot() {
        chatsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatData = dataSnapshot;
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
