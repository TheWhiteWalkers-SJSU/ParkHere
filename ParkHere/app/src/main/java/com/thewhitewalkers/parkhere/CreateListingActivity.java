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
import android.widget.Toast;
import android.widget.ToggleButton;

import java.sql.Time;
import java.util.Map;
import java.util.HashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class CreateListingActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference listingDatabase;


    private EditText editTextListingName;
    private EditText editTextListingAddress;
    private EditText editTextListingDescription;
    private EditText editTextListingPrice;
    private EditText editTextListingDateStarting;
    private EditText editTextListingDateEnding;
    private EditText editTextListingTimeStarting;
    private EditText editTextListingTimeEnding;
    private ToggleButton toggleStartingAM;
    private ToggleButton toggleEndingAM;
    private Button buttonCreateListing;
    private Button buttonHomepage;
    private boolean isStartingAM;
    private boolean isEndingAM;

    private TimeDetails timeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        firebaseAuth = FirebaseAuth.getInstance();
        listingDatabase = FirebaseDatabase.getInstance().getReference("listings");
        timeDetails = new TimeDetails();

        editTextListingName = findViewById(R.id.editTextListingName);
        editTextListingAddress = findViewById(R.id.editTextListingAddress);
        editTextListingDescription = findViewById(R.id.editTextListingDescription);
        editTextListingPrice = findViewById(R.id.editTextListingPrice);
        editTextListingDateStarting = findViewById(R.id.editTextListingStartDate);
        editTextListingDateEnding = findViewById(R.id.editTextListingEndDate);
        editTextListingTimeStarting = findViewById(R.id.editTextListingStartTime);
        editTextListingTimeEnding = findViewById(R.id.editTextListingEndTime);
        toggleStartingAM = findViewById(R.id.startingAMButton);
        toggleEndingAM = findViewById(R.id.endingAMButton);

        buttonCreateListing = findViewById(R.id.buttonCreateListing);
        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createListing();
            }
        });

        buttonHomepage = findViewById(R.id.buttonHomepage);
        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

    }

    private void createListing(){
        // get the values from all the fields and save them to the Firebase db
        String listingName = editTextListingName.getText().toString().trim();
        String listingAddress = editTextListingAddress.getText().toString().trim();
        String listingDescription = editTextListingDescription.getText().toString().trim();
        String listingPrice = editTextListingPrice.getText().toString().trim();
        String listingDateStarting = editTextListingDateStarting.getText().toString();
        String listingDateEnding = editTextListingDateEnding.getText().toString();
        String listingTimeStarting = editTextListingTimeStarting.getText().toString();
        String listingTimeEnding = editTextListingTimeEnding.getText().toString();
        setToggleAM(toggleStartingAM.getText().equals("AM"), toggleEndingAM.getText().equals("AM"));
        String checkDateResult;
        String checkTimeResult;

        FirebaseUser user = firebaseAuth.getCurrentUser(); //get user

        if(!TextUtils.isEmpty(listingName) && !TextUtils.isEmpty(listingAddress) && !TextUtils.isEmpty(listingPrice) && !TextUtils.isEmpty(listingDateStarting) && !TextUtils.isEmpty(listingDateEnding) && !TextUtils.isEmpty(listingTimeStarting) && !TextUtils.isEmpty(listingTimeEnding)) {
            checkDateResult = checkBookingDate(listingDateStarting, listingDateEnding);
            checkTimeResult = checkBookingTime(listingTimeStarting, listingTimeEnding);

            if(!checkDateResult.equals(""))
                Toast.makeText(CreateListingActivity.this, checkDateResult, Toast.LENGTH_SHORT).show();
            else if (!checkTimeResult.equals(""))
                Toast.makeText(CreateListingActivity.this, checkTimeResult, Toast.LENGTH_SHORT).show();
            else
            {
                String _id = listingDatabase.push().getKey();
                timeDetails = new TimeDetails(listingDateStarting, listingDateEnding, listingTimeStarting, isStartingAM, listingTimeEnding, isEndingAM);
                Listing newListing = new Listing(_id, listingName, listingAddress, listingDescription, listingPrice, user.getUid(), user.getEmail(), timeDetails, "available");
                listingDatabase.child(_id).setValue(newListing);
                listingDatabase.child(_id).child("timeDetails").setValue(timeDetails);

                // need to add the listing Id onto the user object
                DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");
                HashMap<String, String> listingStatus = new HashMap<>();
                listingStatus.put("status", "available");
                userDatabase.child(user.getUid()).child("listings").child(_id).setValue(listingStatus);

                Toast.makeText(CreateListingActivity.this, "Created Listing", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }

        } else {
            Toast.makeText(CreateListingActivity.this, "need to enter name, address, price, dates, and times", Toast.LENGTH_SHORT).show();
        }
    }

    public String checkBookingDate(String dateStarting, String dateEnding){
        if(!timeDetails.checkDateFormat(dateStarting) || !timeDetails.checkDateFormat(dateEnding) ){
            return "dates must be in MM/DD/YYYY format!";
        }
        else if(!timeDetails.checkDateValid(dateStarting, dateEnding)){
            return "starting date should be before ending date!";
        }
        return "";
    }

    public String checkBookingTime(String timeStarting, String timeEnding){
        if(!timeDetails.checkTimeFormat(timeStarting) || !timeDetails.checkTimeFormat(timeEnding)){
            return "times must be in HH:MM format!";
        }
        else if(timeStarting.equals(timeEnding) && (isStartingAM == isEndingAM) ){
            return "times can't be the same!";
        }
        return "";
    }

    public void setTimeDetails(TimeDetails time) {
        timeDetails = time;
    }

    public void setToggleAM(boolean start, boolean end) {
        isStartingAM = start;
        isEndingAM = end;
    }
}
