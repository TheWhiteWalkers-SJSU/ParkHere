package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.util.Map;
import java.util.HashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class CreateListingActivity extends AppCompatActivity {

    DatabaseReference listingDatabase = FirebaseDatabase.getInstance().getReference("listings");

    FirebaseAuth firebaseAuth;

    private EditText editTextListingName;
    private EditText editTextListingAddress;
    private EditText editTextListingDescription;
    private EditText editTextListingPrice;
    private EditText editTextListingDateStarting;
    private EditText editTextListingDateEnding;
    private EditText editTextListingTimeStarting;
    private EditText editTextListingTimeEnding;
    private Button buttonCreateListing;
    private Button buttonHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        editTextListingName = findViewById(R.id.editTextListingName);
        editTextListingAddress = findViewById(R.id.editTextListingAddress);
        editTextListingDescription = findViewById(R.id.editTextListingDescription);
        editTextListingPrice = findViewById(R.id.editTextListingPrice);
        editTextListingDateStarting = findViewById(R.id.editTextListingStartDate);
        editTextListingDateEnding = findViewById(R.id.editTextListingEndDate);
        editTextListingTimeStarting = findViewById(R.id.editTextListingStartTime);
        editTextListingTimeEnding = findViewById(R.id.editTextListingEndTime);

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
        
        FirebaseUser user = firebaseAuth.getInstance().getCurrentUser(); //get user

        if(!TextUtils.isEmpty(listingName) && !TextUtils.isEmpty(listingAddress) && !TextUtils.isEmpty(listingPrice)) {
            String _id = listingDatabase.push().getKey();
            Listing newListing = new Listing(_id, listingName, listingAddress, listingDescription, listingPrice, user.getUid(), user.getEmail(), "available");
            listingDatabase.child(_id).setValue(newListing);

            // need to add the listing Id onto the user object
            DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");
            HashMap<String, String> listingStatus = new HashMap<>();
            listingStatus.put("status", "available");
            userDatabase.child(user.getUid()).child("listings").child(_id).setValue(listingStatus);

            Toast.makeText(CreateListingActivity.this, "Created Listing", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        } else {
            Toast.makeText(CreateListingActivity.this, "need to enter name, address, and price", Toast.LENGTH_SHORT).show();
        }
    }
}
