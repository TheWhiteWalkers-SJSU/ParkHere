package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class CreateRequestActivity extends AppCompatActivity {

    DatabaseReference requestDatabase = FirebaseDatabase.getInstance().getReference("requests");

    FirebaseAuth firebaseAuth;

    private TextView textViewListingName;
    private TextView textViewPricing;
    private EditText editTextRequestSubjectLine;
    private EditText editTextRequestMessage;
    private EditText editTextDateStarting;
    private EditText editTextDateEnding;
    private EditText editTextTimeStarting;
    private EditText editTextTimeEnding;
    private ToggleButton toggleStartingAM;
    private ToggleButton toggleEndingAM;
    private Button buttonCreateListing;

    private TimeDetails timeDetails;

    String listingName;
    String listingId;
    String listingOwner;
    String listingRate;

    final String PRICING = "Total Price: ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_request);

        Intent listingIntent = getIntent();
        Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");
        listingName = thisListing.getListingName();
        listingId = thisListing.getListingId();
        listingOwner = thisListing.getOwnerId();
        listingRate = thisListing.getListingPrice();

        textViewListingName = findViewById(R.id.textViewListingName);
        textViewPricing  = findViewById(R.id.textViewPricing);
        editTextRequestSubjectLine = findViewById(R.id.editTextRequestSubjectLine);
        editTextRequestMessage = findViewById(R.id.editTextRequestMessage);

        editTextDateStarting = findViewById(R.id.editTextStartDate);
        editTextDateEnding = findViewById(R.id.editTextEndDate);
        editTextTimeStarting = findViewById(R.id.editTextStartTime);
        editTextTimeEnding = findViewById(R.id.editTextEndTime);
        toggleStartingAM = findViewById(R.id.startingAMButton);
        toggleEndingAM = findViewById(R.id.endingAMButton);

        buttonCreateListing = findViewById(R.id.buttonCreateRequest);

        textViewListingName.setText(listingName);

        editTextDateStarting.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    updatedPrice();
            }
        });

        editTextDateEnding.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    updatedPrice();
            }
        });

        editTextTimeStarting.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    updatedPrice();
            }
        });

        editTextTimeEnding.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if(s.length() != 0)
                    updatedPrice();
            }
        });
        toggleStartingAM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updatedPrice();
            }
        });
        toggleEndingAM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                updatedPrice();
            }
        });

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

        if(!TextUtils.isEmpty(requestSubject) && !TextUtils.isEmpty(requestMessage) && updatedPrice()) {
            String _id = requestDatabase.push().getKey();
            Request newRequest = new Request(_id, listingOwner, user.getUid(), user.getEmail(), listingId, requestSubject, requestMessage, timeDetails, 0);
            requestDatabase.child(_id).setValue(newRequest);

            Toast.makeText(CreateRequestActivity.this, "Sent Request", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        } else {
            Toast.makeText(CreateRequestActivity.this, "need to enter name, address, dates, and times", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean updatedPrice(){
        String dateStarting = editTextDateStarting.getText().toString();
        String dateEnding = editTextDateEnding.getText().toString();
        String timeStarting = editTextTimeStarting.getText().toString();
        String timeEnding = editTextTimeEnding.getText().toString();

        boolean isStartingAM = toggleStartingAM.isChecked();
        boolean isEndingAM = toggleEndingAM.isChecked();

        if(!TextUtils.isEmpty(dateStarting) && !TextUtils.isEmpty(dateEnding) && !TextUtils.isEmpty(dateStarting) && !TextUtils.isEmpty(timeEnding)){
            timeDetails = new TimeDetails(dateStarting, dateEnding, timeStarting, isStartingAM, timeEnding, isEndingAM);
            textViewPricing.setText(PRICING + timeDetails.setPrice(listingRate));
        }
        return false;
    }
}
