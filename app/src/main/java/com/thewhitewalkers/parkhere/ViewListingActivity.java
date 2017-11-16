package com.thewhitewalkers.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class ViewListingActivity extends AppCompatActivity {

    DatabaseReference requestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    FirebaseAuth firebaseAuth;


    private TextView listingNameText;
    private TextView listingAddressText;
    private TextView listingDescriptionText;
    private TextView listingOwnerText;
    private TextView listingStartText;
    private TextView listingEndText;
    private Button requestButton;
    private Button ratingButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent listingIntent = getIntent();
        final Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");

        String listingName = thisListing.getListingName();
        String listingOwner = thisListing.getOwnerEmail();
        //listingOwner should show email, but listings made early on did not fill in email field
        if(listingOwner == null) listingOwner = thisListing.getOwnerId();
        String listingAddress = thisListing.getListingAddress();
        String listingDescription = thisListing.getListingDescription();
        String listingStart = thisListing.getStartTime();
        String listingEnd = thisListing.getEndTime();

        listingNameText = findViewById(R.id.listingNameText);
        listingOwnerText = findViewById(R.id.listingOwnerText);
        listingAddressText = findViewById(R.id.listingAddressText);
        listingDescriptionText = findViewById(R.id.listingDescriptionText);
        listingStartText = findViewById(R.id.listingStart);
        listingEndText = findViewById(R.id.listingEnd);

        listingNameText.setText(listingName);
        listingOwnerText.setText(listingOwner);
        listingAddressText.setText(listingAddress);
        listingDescriptionText.setText(listingDescription);
        listingStartText.setText("Start Time: "+listingStart);
        listingEndText.setText("End Time: "+listingEnd);

        requestButton = findViewById(R.id.requestButton);
        ratingButton = findViewById(R.id.ratingButton);

        if(thisListing.getOwnerId() != null) {
            if(thisListing.getOwnerId().equals(user.getEmail()) || thisListing.getOwnerId().equals(user.getUid())) {
                requestButton.setVisibility(View.GONE);
            }
        }

        if(thisListing.getRenterId() != null) {
            if(thisListing.getRenterId().equals(user.getEmail()) || thisListing.getRenterId().equals(user.getUid())) {
                requestButton.setVisibility(View.GONE);
            }
        }

        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createRequestIntent = new Intent(getApplicationContext(), CreateRequestActivity.class);
                createRequestIntent.putExtra("listing", thisListing);
                startActivity(createRequestIntent);
            }
        });

        ratingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createRatingIntent = new Intent(getApplicationContext(), RatingActivity.class);
                createRatingIntent.putExtra("listing", thisListing);
                startActivity(createRatingIntent);
            }
        });

    }


}
