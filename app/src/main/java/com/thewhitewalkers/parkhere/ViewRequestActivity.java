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

    //Owner Action
    private Button acceptButton;
    private Button rejectButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_request);

        backToInboxButton = findViewById(R.id.backToInbox);
        subjectLine = findViewById(R.id.subjectLine);
        senderLine = findViewById(R.id.senderLine);
        messageText = findViewById(R.id.message);
        viewListingButton = findViewById(R.id.viewListing);

        Request currentMessage = (Request) getIntent().getSerializableExtra("message");
        /*
            0 - Owner Action Required (from renter user)
            1 - Booking Pending (from system)
            2 - Booking Accepted (from system)
            3 - Booking Denied (from system)
         */
        int requestType = currentMessage.getRequestType(); //get the request type
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

        String subject = currentMessage.getSubject();
        String sender = "From: " + currentMessage.getSenderID() ;
        String message = currentMessage.getMessage();

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
