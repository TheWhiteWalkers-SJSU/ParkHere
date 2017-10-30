package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ViewRequestActivity extends AppCompatActivity {

    private Button backToInboxButton;
    private TextView subjectLine;
    private TextView senderLine;
    private TextView messageText;
    private Button viewListingButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        backToInboxButton = findViewById(R.id.backToInbox);
        subjectLine = findViewById(R.id.subjectLine);
        senderLine = findViewById(R.id.senderLine);
        messageText = findViewById(R.id.message);
        viewListingButton = findViewById(R.id.viewListing);

        /*
            0 - Owner Action Required (from renter user)
            1 - Booking Pending (from system)
            2 - Booking Accepted (from system)
            3 - Booking Denied (from system)
         */
        int requestType = 0; //get the request type
        if(requestType == 0){ //Owner needs to accept/ reject booking

        }
        else if(requestType == 1){

        }
        else if(requestType == 2){

        }
        else if(requestType == 3){

        }
        else{
            Toast.makeText(getApplicationContext(),
                    "ERROR...", Toast.LENGTH_SHORT).show();
        }

        String subject = "Booking Pending";
        String sender = "From: Pearl";
        String message = "Your booking request is still pending for 1 Washington Sq.";

        backToInboxButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //redirect back to inbox
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
            }
        });

        subjectLine.setText(subject);
        senderLine.setText(sender);
        messageText.setText(message);


        viewListingButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //redirect to listing
                Toast.makeText(getApplicationContext(),
                        "Redirect to Listing...", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
