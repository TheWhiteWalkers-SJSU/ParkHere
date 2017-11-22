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
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private Switch switchGeneratePricing;
    private Button buttonCreateListing;

    private TimeDetails timeDetails;

    String listingName;
    String listingId;
    String listingOwner;
    String listingRate;

    final String PRICING = "Total Price: ";

    private static DataSnapshot requestData;
    private boolean requestsConflict;

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

        timeDetails = new TimeDetails();

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

        switchGeneratePricing = findViewById(R.id.generateSwitch);
        buttonCreateListing = findViewById(R.id.buttonCreateRequest);
        textViewListingName.setText(listingName);

        requestsConflict = false;
        updateRequestSnapshot();

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(switchGeneratePricing.isChecked()){
                    switchGeneratePricing.setChecked(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };

        editTextDateStarting.addTextChangedListener(textWatcher);
        editTextDateEnding.addTextChangedListener(textWatcher);
        editTextTimeStarting.addTextChangedListener(textWatcher);
        editTextTimeEnding.addTextChangedListener(textWatcher);

        toggleStartingAM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(switchGeneratePricing.isChecked()){
                    switchGeneratePricing.setChecked(false);
                }
            }
        });

        toggleEndingAM.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(switchGeneratePricing.isChecked()){
                    switchGeneratePricing.setChecked(false);
                }
            }
        });

        switchGeneratePricing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(!updatedPrice()){
                    switchGeneratePricing.setChecked(false);
                }
            }
        });

        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //check if inputs are valid before checking conflicts or creating the request
                if(validInputs()) {
                    //check if it has conflicts with existing booked requests before allowing it to be sent
                    if (hasRequestsConflict()) {
                        Toast.makeText(CreateRequestActivity.this, "Time/date unavailable for listing", Toast.LENGTH_SHORT).show();
                    } else {
                        createRequest();
                        Toast.makeText(CreateRequestActivity.this, "Sent Request", Toast.LENGTH_SHORT).show();
                    }
                }
                //when validInputs is false, it prints the correct error message in its method
            }
        });
    }

    private void updateRequestSnapshot() {
        requestDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //for all the requests in the db
                requestData = dataSnapshot;
                //for testing when data snapshot is updated
                //Toast.makeText(CreateRequestActivity.this, "Updated data snapshot", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private boolean hasRequestsConflict() {
        requestsConflict = false;
        //testMessage = "";
        //for all the requests in the current request data snapshot
        for(DataSnapshot requestSnapshot : requestData.getChildren()) {
            Request request = requestSnapshot.getValue(Request.class);
            //only consider requests for the same listing
            if(request.getListingID().equals(listingId)){
                //check requests that have been accepted by the owner already
                if(request.getRequestType() == 2) {
                    if(request.getTimeDetails().hasConflict(timeDetails)) {
                        requestsConflict = true;
                    }
                    //for testing how many requests are compared, to test print testMessage
                    //testMessage += "Comparing with a request. ";
                }
            }
        }
        //Toast.makeText(CreateRequestActivity.this, testMessage, Toast.LENGTH_SHORT).show();
        return requestsConflict;
    }

    private boolean validInputs() {
        // get the values from all the fields and save them to the Firebase db
        String requestSubject = editTextRequestSubjectLine.getText().toString().trim();
        String requestMessage = editTextRequestMessage.getText().toString().trim();

        String dateStarting = editTextDateStarting.getText().toString();
        String dateEnding = editTextDateEnding.getText().toString();
        String timeStarting = editTextTimeStarting.getText().toString();
        String timeEnding = editTextTimeEnding.getText().toString();

        boolean isStartingAM = toggleStartingAM.isChecked();
        boolean isEndingAM = toggleEndingAM.isChecked();
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser(); //get user

        if(!switchGeneratePricing.isChecked()){
            Toast.makeText(CreateRequestActivity.this, "click on switch to generate pricing", Toast.LENGTH_SHORT).show();
        }
        else if(!TextUtils.isEmpty(requestSubject) && !TextUtils.isEmpty(requestMessage) && updatedPrice()) {
            return true;
        }
        else {
            Toast.makeText(CreateRequestActivity.this, "need to enter name, address, dates, and times", Toast.LENGTH_SHORT).show();
        }
        return false;
    }

    private void createRequest(){
        // get the values from all the fields and save them to the Firebase db
        String requestSubject = editTextRequestSubjectLine.getText().toString().trim();
        String requestMessage = editTextRequestMessage.getText().toString().trim();
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser(); //get user

        String _id = requestDatabase.push().getKey();
        Request newRequest = new Request(_id, listingOwner, user.getUid(), user.getEmail(), listingId, requestSubject, requestMessage, timeDetails, 0);
        requestDatabase.child(_id).setValue(newRequest);

        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
    }

    private boolean updatedPrice(){
        String dateStarting = editTextDateStarting.getText().toString();
        String dateEnding = editTextDateEnding.getText().toString();
        String timeStarting = editTextTimeStarting.getText().toString();
        String timeEnding = editTextTimeEnding.getText().toString();

        boolean isStartingAM = toggleStartingAM.isChecked();
        boolean isEndingAM = toggleEndingAM.isChecked();

        if(!TextUtils.isEmpty(dateStarting) && !TextUtils.isEmpty(dateEnding) && !TextUtils.isEmpty(dateStarting) && !TextUtils.isEmpty(timeEnding)){
            //check if time and dates are in right format
            if(!timeDetails.checkDateFormat(dateStarting) || !timeDetails.checkDateFormat(dateEnding) ){
                Toast.makeText(CreateRequestActivity.this, "dates must be in MM/DD/YYYY format!", Toast.LENGTH_SHORT).show();
                if(!textViewPricing.getText().equals(PRICING + "$0.00")){
                    textViewPricing.setText(PRICING + "$0.00");
                }

            }
            else if(!timeDetails.checkDateValid(dateStarting, dateEnding)){
                Toast.makeText(CreateRequestActivity.this, "starting date should be before ending date!", Toast.LENGTH_SHORT).show();
                if(!textViewPricing.getText().equals(PRICING + "$0.00")){
                    textViewPricing.setText(PRICING + "$0.00");
                }
            }
            else if(!timeDetails.checkTimeFormat(timeStarting) || !timeDetails.checkTimeFormat(timeEnding)){
                Toast.makeText(CreateRequestActivity.this, "times must be in HH:MM format!", Toast.LENGTH_SHORT).show();
                if(!textViewPricing.getText().equals(PRICING + "$0.00")){
                    textViewPricing.setText(PRICING + "$0.00");
                }
            }
            else if(timeStarting.equals(timeEnding) && (toggleStartingAM.isChecked() == toggleEndingAM.isChecked()) ){
                Toast.makeText(CreateRequestActivity.this, "times can't be the same!", Toast.LENGTH_SHORT).show();
                if(!textViewPricing.getText().equals(PRICING + "$0.00")){
                    textViewPricing.setText(PRICING + "$0.00");
                }
            }
            else{
                timeDetails = new TimeDetails(dateStarting, dateEnding, timeStarting, isStartingAM, timeEnding, isEndingAM);
                textViewPricing.setText(PRICING + timeDetails.setPrice(listingRate));
                return true;
            }
        }
        return false;
    }
}
