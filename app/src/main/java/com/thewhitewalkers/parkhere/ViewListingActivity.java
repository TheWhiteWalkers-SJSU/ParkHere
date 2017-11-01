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
    private Button requestButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        Intent listingIntent = getIntent();
        final Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");

        String listingName = thisListing.getListingName();
        String listingOwner = thisListing.getOwnerId();
        String listingAddress = thisListing.getListingAddress();
        String listingDescription = thisListing.getListingDescription();

        listingNameText = findViewById(R.id.listingNameText);
        listingOwnerText = findViewById(R.id.listingOwnerText);
        listingAddressText = findViewById(R.id.listingAddressText);
        listingDescriptionText = findViewById(R.id.listingDescriptionText);

        listingNameText.setText(listingName);
        listingOwnerText.setText(listingOwner);
        listingAddressText.setText(listingAddress);
        listingDescriptionText.setText(listingDescription);

        requestButton = findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent createRequestIntent = new Intent(getApplicationContext(), CreateRequestActivity.class);
                createRequestIntent.putExtra("listing", thisListing);
                startActivity(createRequestIntent);
            }
        });

    }


}
