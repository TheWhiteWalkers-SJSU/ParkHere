package com.thewhitewalkers.parkhere;

import android.app.Dialog;
import android.content.Intent;
import android.content.DialogInterface;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CreateListingActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    DatabaseReference listingDatabase;
    final DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");
    FirebaseUser user = firebaseAuth.getCurrentUser(); //get user

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
    private Button buttonSelectParkingSpot;
    private List<ParkingSpot> parkingSpotList = new ArrayList<>();
    ParkingSpotList parkingSpotAdapter = null;
    private boolean isStartingAM;
    private boolean isEndingAM;

    private TimeDetails timeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

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

        buttonSelectParkingSpot = findViewById(R.id.buttonSelectParkingSpot);
        buttonSelectParkingSpot.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                selectListing();
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
                startActivity(new Intent(CreateListingActivity.this, HomepageActivity.class));
            }

        } else {
            Toast.makeText(CreateListingActivity.this, "need to enter name, address, price, dates, and times", Toast.LENGTH_SHORT).show();
        }
    }

    public void selectListing() {

        AlertDialog.Builder builder = new AlertDialog.Builder(CreateListingActivity.this);
        builder.setTitle("Pick a Parking Spot")
                .setAdapter(parkingSpotAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String strName = parkingSpotAdapter.getItem(which).getAddress();
                        AlertDialog.Builder builderInner = new AlertDialog.Builder(CreateListingActivity.this);
                        builderInner.setMessage(strName);
                        builderInner.setTitle("Your Selected Item is");
                        builderInner.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,int which) {
                                dialog.dismiss();
                            }
                        });
                        builderInner.show();
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
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

    @Override
    protected void onStart() {
        super.onStart();

        ParkingSpotDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingSpotList.clear();
                for (DataSnapshot parkingSpotSnapshot : dataSnapshot.getChildren()) {
                    ParkingSpot parkingSpot = parkingSpotSnapshot.getValue(ParkingSpot.class);
                    // TODO: need to change to match only email, accepting uuid because of old entries in DB
                    if (parkingSpotList != null) {
                        if (parkingSpot.getOwnerId().equals(user.getEmail()) || parkingSpot.getOwnerId().equals(user.getUid())) {
                            parkingSpotList.add(parkingSpot);
                        }
                    }
                }
                parkingSpotAdapter = new ParkingSpotList(CreateListingActivity.this, parkingSpotList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
