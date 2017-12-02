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
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
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
    private static DataSnapshot chatData;
    private static DataSnapshot requestData;
    private boolean requestsConflict;
    String listingEmail;
    String requestEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        currentRequest = (Request) getIntent().getSerializableExtra("request");
        currentListing = (Listing) getIntent().getSerializableExtra("listing");

        listingEmail = currentListing.getOwnerEmail();
        requestEmail = currentRequest.getSenderEmail();

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
        listingPrice.setText("Price: $" + currentListing.getListingPrice());


        acceptRequestButton = findViewById(R.id.acceptRequest);
        denyRequestButton = findViewById(R.id.denyRequest);
        cancelRequestButton = findViewById(R.id.cancelRequest);

        acceptRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check if it has conflicts with existing booked requests before allowing it to be accepted
                if(hasRequestsConflict()) {
                    Toast.makeText(ViewRequestActivity.this, "Time/date unavailable for listing", Toast.LENGTH_SHORT).show();
                }
                else {
                    acceptRequest();
                    Toast.makeText(ViewRequestActivity.this, "Request accepted", Toast.LENGTH_SHORT).show();

                    //if chat does not exist already, create one
                    if(!chatExists(listingEmail, requestEmail)) {
                        String _id = chatsDatabase.push().getKey();
                        Chat newChat = new Chat(_id, listingEmail, requestEmail);
                        chatsDatabase.child(_id).setValue(newChat);
                    }
                }
            }
        });


        denyRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                denyRequest();
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
            String user1 = emails.get("emailUser1");
            String user2 = emails.get("emailUser2");

            //check if a chat btwn the same two users already exists
            if((user1.equals(check1) && user2.equals(check2)) || (user2.equals(check1) && user1.equals(check2))) {
                return true;
            }
        }
        return false;
    }

    private void updateChatSnapshot() {
        chatsDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                chatData = dataSnapshot;
                Toast.makeText(ViewRequestActivity.this, "Updated data snapshot", Toast.LENGTH_SHORT).show();
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
        //testMessage = "";
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
                    //testMessage += "Comparing with a request. ";
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
}
