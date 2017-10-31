package com.thewhitewalkers.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.widget.TextView;

import java.util.List;

public class ViewListingActivity extends AppCompatActivity {


    private TextView listingNameText;
    private TextView listingAddressText;
    private TextView listingDescriptionText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        Intent listingIntent = getIntent();
        Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");

        String listingName = thisListing.getListingName();
        String listingAddress = thisListing.getListingAddress();
        String listingDescription = thisListing.getListingDescription();

        listingNameText = findViewById(R.id.listingNameText);
        listingAddressText = findViewById(R.id.listingAddressText);
        listingDescriptionText = findViewById(R.id.listingDescriptionText);

        listingNameText.setText(listingName);
        listingAddressText.setText(listingAddress);
        listingDescriptionText.setText(listingDescription);
    }


}
