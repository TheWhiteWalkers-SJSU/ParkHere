package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Map;
import java.util.HashMap;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewRequestActivity extends AppCompatActivity {

    private Button backToInboxButton;
    private TextView subjectLine;
    private TextView senderLine;
    private TextView messageText;
    private TextView listingAddress;
    private TextView listingPrice;
    private Button viewListingButton;
    private Button acceptRequestButton;
    private Button denyRequestButton;

    private Request currentRequest;
    private Listing currentListing;
    final DatabaseReference RequestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        currentRequest = (Request) getIntent().getSerializableExtra("request");
        currentListing = (Listing) getIntent().getSerializableExtra("listing");

        backToInboxButton = findViewById(R.id.backToInbox);
        subjectLine = findViewById(R.id.subjectLine);
        senderLine = findViewById(R.id.senderLine);
        messageText = findViewById(R.id.message);
        listingAddress = findViewById(R.id.listingAddress);
        listingPrice = findViewById(R.id.listingPrice);
        viewListingButton = findViewById(R.id.viewListing);

        subjectLine.setText(currentRequest.getSubject());
        senderLine.setText("From: " + currentRequest.getSenderEmail());
        messageText.setText(currentRequest.getMessage());
        listingAddress.setText(currentListing.getListingAddress());
        listingPrice.setText(currentListing.getListingPrice());


            acceptRequestButton = findViewById(R.id.acceptRequest);
            denyRequestButton = findViewById(R.id.denyRequest);

            acceptRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    acceptRequest();
                }
            });

            denyRequestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    denyRequest();
                }
            });



        viewListingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewListing();
            }
        });

        backToInboxButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
            }
        });
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
}
