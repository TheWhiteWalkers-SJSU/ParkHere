package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

public class ViewRequestActivity extends AppCompatActivity {

    private Button backToInboxButton;
    private TextView subjectLine;
    private TextView senderLine;
    private TextView messageText;
    private TextView listingAddress;
    private TextView listingDate;
    private TextView listingTime;
    private TextView listingPrice;
    private Button viewListingButton;
    private Button acceptRequestButton;
    private Button denyRequestButton;
    private Button cancelRequestButton;

    private Request currentRequest;
    private Listing currentListing;
    final DatabaseReference RequestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");
    DatabaseReference chatsDatabase = FirebaseDatabase.getInstance().getReference("chats");
    DatabaseReference parkingDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
    FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
    private static DataSnapshot chatData;
    private static DataSnapshot requestData;
    private boolean requestsConflict;
    String listingEmail;
    String requestEmail;
    String currentChatId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        currentRequest = (Request) getIntent().getSerializableExtra("request");
        currentListing = (Listing) getIntent().getSerializableExtra("listing");

        listingEmail = currentListing.getOwnerEmail();
        requestEmail = currentRequest.getSenderEmail();
        currentChatId = "";

        requestsConflict = false;
        updateRequestSnapshot();
        updateChatSnapshot();

        backToInboxButton = findViewById(R.id.backToInbox);
        subjectLine = findViewById(R.id.subjectLine);
        senderLine = findViewById(R.id.senderLine);
        messageText = findViewById(R.id.message);
        listingAddress = findViewById(R.id.listingAddress);
        listingDate = findViewById(R.id.listingDate);
        listingTime = findViewById(R.id.listingTime);
        listingPrice = findViewById(R.id.listingPrice);
        viewListingButton = findViewById(R.id.viewListing);

        subjectLine.setText(currentRequest.getSubject());
        senderLine.setText("From: " + currentRequest.getSenderEmail());
        messageText.setText(currentRequest.getMessage());
        listingAddress.setText(currentListing.getListingAddress());
        listingDate.setText("Starting on " + currentRequest.getTimeDetails().getStartingDate() + " to " + currentRequest.getTimeDetails().getEndingDate());
        String AM1 = "AM";
        String AM2 = "AM";
        if(!currentRequest.getTimeDetails().isStartingIsAM()){
            AM1 = "PM";
        }
        if(!currentRequest.getTimeDetails().isEndingIsAM()){
            AM2 = "PM";
        }
        listingTime.setText("From " + currentRequest.getTimeDetails().getStartingTime() + AM1 + " to " + currentRequest.getTimeDetails().getEndingTime() + AM2);
        String totalPrice = currentRequest.getTimeDetails().setPrice(currentListing.getListingPrice());
        listingPrice.setText("Price: " + totalPrice + " ($" +currentListing.getListingPrice() + "/hour)");


        acceptRequestButton = findViewById(R.id.acceptRequest);
        denyRequestButton = findViewById(R.id.denyRequest);
        cancelRequestButton = findViewById(R.id.cancelRequest);

        //for sent requests
        if(currentRequest.getSenderID().equals(currentUser.getUid())) {
            //disable accept, deny req
            acceptRequestButton.setVisibility(View.GONE);
            denyRequestButton.setVisibility(View.GONE);
        }
        //for pending requests
        if(currentListing.getOwnerId().equals(currentUser.getUid()) && currentRequest.getRequestType() == 0) {
            //disable cancel req
            cancelRequestButton.setVisibility(View.GONE);
        }
        //for accepted requests
        if(currentListing.getOwnerId().equals(currentUser.getUid()) && currentRequest.getRequestType() == 2) {
            //disable accept, deny, cancel req
            acceptRequestButton.setVisibility(View.GONE);
            denyRequestButton.setVisibility(View.GONE);
            cancelRequestButton.setVisibility(View.GONE);
        }

        acceptRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if it has conflicts with existing booked requests before allowing it to be accepted
                if(hasRequestsConflict()) {
                    Toast.makeText(ViewRequestActivity.this, "Time/date unavailable for listing", Toast.LENGTH_SHORT).show();
                }
                else {
                    acceptRequest();
                    incrementNumBookings();
                    Toast.makeText(ViewRequestActivity.this, "Request accepted", Toast.LENGTH_SHORT).show();

                    //if chat does not exist already, create one between owner and renter
                    if(!chatExists(listingEmail, requestEmail)) {
                        createChat(listingEmail, requestEmail);
                    }
                    //add message for system notification, create chat if needed
                    String message = "Your request for listing \"" + currentListing.getListingName() + "\" was accepted!";
                    if(!chatExists("ParkHere", requestEmail)) {
                        createChatWithMessage("ParkHere", requestEmail, message);
                    }
                    else {
                        addMessage(currentChatId, "ParkHere", message);
                    }
                }
            }
        });


        denyRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denyRequest();
                //add message for system notification, create chat if needed
                String message = "Your request for listing \"" + currentListing.getListingName() + "\" was denied.";
                if(!chatExists("ParkHere", requestEmail)) {
                    createChatWithMessage("ParkHere", requestEmail, message);
                }
                else {
                    addMessage(currentChatId, "ParkHere", message);
                }
            }
        });

        cancelRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancelRequest();
            }
        });

        viewListingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewListing();
            }
        });

        backToInboxButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TabbedInboxActivity.class));
            }
        });
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

    private void createChat(String user1, String user2) {
        //create a new id
        String _id = chatsDatabase.push().getKey();
        //create the chat, save to db with the id generated
        Chat newChat = new Chat(_id, user1, user2);
        chatsDatabase.child(_id).setValue(newChat);
        currentChatId = _id;
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
//                Toast.makeText(ViewRequestActivity.this, "Updated data snapshot", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void updateRequestSnapshot() {
        RequestDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for all the requests in the db
                requestData = dataSnapshot;
                //for testing when data snapshot is updated
                //Toast.makeText(ViewRequestActivity.this, "Updated data snapshot", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


    private boolean hasRequestsConflict() {
        requestsConflict = false;
        //for all the requests in the current request data snapshot
        for(DataSnapshot requestSnapshot : requestData.getChildren()) {
            Request request = requestSnapshot.getValue(Request.class);
            //only consider requests for the same listing
            if(request.getListingID().equals(currentRequest.getListingID())){
                //check requests that have been accepted by the owner already
                if(request.getRequestType() == 2) {
                    if(request.getTimeDetails().hasConflict(currentRequest.getTimeDetails())) {
                        requestsConflict = true;
                    }
                    //for testing how many requests are compared, to test print testMessage
                }
            }
        }
        return requestsConflict;
    }


    /**
        0 - Owner Action Required (from renter user)
        1 - Booking Pending (from system)
        2 - Booking Accepted (from system)
        3 - Booking Denied (from system)
     **/

    private void acceptRequest(){

        /** when a listing is booked:
         *  - set the requestType to 2
         *  - set the listing status to booked
         *  - be removed from the search page
         *  - have a booked marker on the owners myListings page
         *  - show up in the renters myBookings list
         **/

        Map<String, Object> requestUpdate = new HashMap<>();
        requestUpdate.put("requestType", 2);
        RequestDatabase.child(currentRequest.getRequestID()).updateChildren(requestUpdate);

        Map<String, Object> listingUpdate = new HashMap<>();
        listingUpdate.put("listingStatus", "booked");
        listingUpdate.put("renterId", currentRequest.getSenderID());
        ListingDatabase.child(currentListing.getListingId()).updateChildren(listingUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), TabbedInboxActivity.class));
                    }
                });
    }

    private void denyRequest(){
        Map<String, Object> requestUpdate = new HashMap<>();
        requestUpdate.put("requestType", 3);
        RequestDatabase.child(currentRequest.getRequestID()).updateChildren(requestUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), TabbedInboxActivity.class));
                    }
                });
    }

    private void viewListing(){
        //redirect to listing
        Toast.makeText(getApplicationContext(),
                "Redirect to Listing...", Toast.LENGTH_SHORT).show();
        Intent viewListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
        viewListingIntent.putExtra("listing", currentListing);
        try {
            startActivity(viewListingIntent);
        } catch (Exception e) {
            Toast.makeText(ViewRequestActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void cancelRequest() {
        RequestDatabase.child(currentRequest.getRequestID()).removeValue()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    }
                });
    }

    private void incrementNumBookings() {
        //get parking spot id for current listing
        final String parkingId = currentListing.getParkingSpot().getParkingSpotId();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("parkingSpots").child(parkingId).child("priorBookings");
        //increment prior bookings for current listing's parking spot
        ref.runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                if (mutableData.getValue() == null) {
                    mutableData.setValue(1);
                } else {
                    int count = mutableData.getValue(Integer.class);
                    mutableData.setValue(count + 1);
                }
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean success, DataSnapshot dataSnapshot) {
                // Analyse databaseError for any error during increment
            }
        });
    }
}
