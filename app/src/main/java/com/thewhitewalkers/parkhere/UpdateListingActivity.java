package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateListingActivity extends AppCompatActivity {

    private EditText editTextListingName;
    private EditText editTextListingAddress;
    private EditText editTextListingDescription;
    private EditText editTextListingPrice;
    private EditText editTextListingStartDate;
    private EditText editTextListingEndDate;
    private EditText editTextListingStartTime;
    private EditText editTextListingEndTime;
    private Button buttonUpdateListing;


    private Listing currentListing;
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_listing);

        currentListing = (Listing) getIntent().getSerializableExtra("listing");

        // get the TextField in the layout and map it to TextField object
        editTextListingName = findViewById(R.id.editTextListingName);
        editTextListingAddress = findViewById(R.id.editTextListingAddress);
        editTextListingDescription = findViewById(R.id.editTextListingDescription);
        editTextListingPrice = findViewById(R.id.editTextListingPrice);
        editTextListingStartDate = findViewById(R.id.editTextListingStartDate);
        editTextListingEndDate = findViewById(R.id.editTextListingEndDate);
        editTextListingStartTime = findViewById(R.id.editTextListingStartTime);
        editTextListingEndTime = findViewById(R.id.editTextListingEndTime);

        buttonUpdateListing = findViewById(R.id.buttonUpdateListing);
        buttonUpdateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListingFields();
            }
        });

    }

    public void updateListingFields() {
        Map<String, Object> listingUpdate = new HashMap<>();

        // Fetch the text entered by the user and set it to a String
        String newListingName = editTextListingName.getText().toString().trim();
        String newListingAddress = editTextListingAddress.getText().toString().trim();
        String newListingDescription = editTextListingDescription.getText().toString().trim();
        String newListingPrice = editTextListingPrice.getText().toString().trim();
        String newListingStartDate = editTextListingStartDate.getText().toString().trim();
        String newListingEndDate = editTextListingEndDate.getText().toString().trim();
        String newListingStartTime = editTextListingStartTime.getText().toString().trim();
        String newListingEndtime = editTextListingEndTime.getText().toString().trim();

        // if the value is not empty add it to the update hashmap
        if(!newListingName.isEmpty()) {
            listingUpdate.put("listingName", newListingName);
        }
        if(!newListingAddress.isEmpty()) {
            listingUpdate.put("listingAddress", newListingAddress);
        }
        if(!newListingDescription.isEmpty()) {
            listingUpdate.put("listingDescription", newListingDescription);
        }
        if(!newListingPrice.isEmpty()) {
            listingUpdate.put("listingPrice", newListingPrice);
        }
        if(!newListingStartDate.isEmpty()) {
            listingUpdate.put("listingStartDate", newListingStartDate);
        }
        if(!newListingEndDate.isEmpty()) {
            listingUpdate.put("listingEndDate", newListingEndDate);
        }
        if(!newListingStartDate.isEmpty()) {
            listingUpdate.put("listingStartTime", newListingStartTime);
        }
        if(!newListingEndtime.isEmpty()) {
            listingUpdate.put("listingEndTime", newListingEndtime);
        }

        // update the current listing with the values in the updateListing hashmap
        ListingDatabase.child(currentListing.getListingId()).updateChildren(listingUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        startActivity(new Intent(getApplicationContext(), InboxActivity.class));
                    }
                });
    }
}
