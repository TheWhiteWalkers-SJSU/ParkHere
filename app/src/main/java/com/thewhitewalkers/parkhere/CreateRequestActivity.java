package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateRequestActivity extends AppCompatActivity {

    DatabaseReference requestDatabase = FirebaseDatabase.getInstance().getReference("requests");

    FirebaseAuth firebaseAuth;

    private TextView textViewListingName;
    private EditText editTextRequestSubjectLine;
    private EditText editTextRequestMessage;
    private Button buttonCreateListing;

    String listingName;
    String listingId;
    String listingOwner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        Intent listingIntent = getIntent();
        Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");
        listingName = thisListing.getListingName();
        listingId = thisListing.getListingId();
        listingOwner = thisListing.getOwnerId();

        textViewListingName = findViewById(R.id.textViewListingName);
        editTextRequestSubjectLine = findViewById(R.id.editTextRequestSubjectLine);
        editTextRequestMessage = findViewById(R.id.editTextRequestMessage);
        buttonCreateListing = findViewById(R.id.buttonCreateRequest);

        textViewListingName.setText(listingName);

        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createRequest();
            }
        });
    }

    private void createRequest(){
        // get the values from all the fields and save them to the Firebase db
        String requestSubject = editTextRequestSubjectLine.getText().toString().trim();
        String requestMessage = editTextRequestMessage.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser(); //get user

        if(!TextUtils.isEmpty(requestSubject) && !TextUtils.isEmpty(requestMessage)) {
            String _id = requestDatabase.push().getKey();
            Request newRequest = new Request(_id, listingOwner, user.getEmail(), listingId, requestSubject, requestMessage, 0);
            requestDatabase.child(_id).setValue(newRequest);

            Toast.makeText(CreateRequestActivity.this, "Sent Request", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        } else {
            Toast.makeText(CreateRequestActivity.this, "need to enter name, address, and price", Toast.LENGTH_SHORT).show();
        }
    }
}
