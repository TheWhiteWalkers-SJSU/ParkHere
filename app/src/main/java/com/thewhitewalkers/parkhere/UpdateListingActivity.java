package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateListingActivity extends AppCompatActivity {

    private EditText editTextListingName;
    private EditText editTextListingDescription;
    private EditText editTextListingPrice;
    private Button buttonUpdateListing;
    private Button buttonBackToListing;
    private Button buttonMarkUnavailable;

    private Listing currentListing;
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_listing);

        currentListing = (Listing) getIntent().getSerializableExtra("listing");

        // get the TextField in the layout and map it to TextField object
        editTextListingName = findViewById(R.id.editTextListingName);
        editTextListingDescription = findViewById(R.id.editTextListingDescription);
        editTextListingPrice = findViewById(R.id.editTextListingPrice);

        buttonUpdateListing = findViewById(R.id.buttonUpdateListing);
        buttonUpdateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateListingFields();
            }
        });


        buttonBackToListing= findViewById(R.id.buttonBackToListing);
        buttonBackToListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
                viewListingIntent.putExtra("listing", currentListing);
                startActivity(viewListingIntent);
            }
        });

        buttonMarkUnavailable = findViewById(R.id.buttonMarkUnavailable);
        buttonMarkUnavailable.setVisibility(View.GONE);

    }

    public void updateListingFields() {
        Map<String, Object> listingUpdate = new HashMap<>();

        // Fetch the text entered by the user and set it to a String
        String newListingName = editTextListingName.getText().toString().trim();
        String newListingDescription = editTextListingDescription.getText().toString().trim();
        String newListingPrice = editTextListingPrice.getText().toString().trim();

        // if the value is not empty add it to the update hashmap
        if(!newListingName.isEmpty()) {
            listingUpdate.put("listingName", newListingName);
        }
        if(!newListingDescription.isEmpty()) {
            listingUpdate.put("listingDescription", newListingDescription);
        }
        if(!newListingPrice.isEmpty()) {
            listingUpdate.put("listingPrice", newListingPrice);
        }

        // update the current listing with the values in the updateListing hashmap
        ListingDatabase.child(currentListing.getListingId()).updateChildren(listingUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        //Toast.makeText(UpdateListingActivity.this, "Updated listing...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                    }
                });
    }
}
