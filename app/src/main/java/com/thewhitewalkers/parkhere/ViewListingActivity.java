package com.thewhitewalkers.parkhere;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewListingActivity extends AppCompatActivity {

    DatabaseReference listingDatabase = FirebaseDatabase.getInstance().getReference("listings");
    DatabaseReference requestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    FirebaseAuth firebaseAuth;

    private RatingBar listingRatingBar;

    private TextView listingNameText;
    private TextView listingAddressText;
    private TextView listingDescriptionText;
    private TextView listingPriceText;
    private TextView listingOwnerText;
    private TextView listingStartText;
    private TextView listingEndText;
    private Button backToHomeButton;
    private Button viewRatingsButton;
    private Button requestButton;
    private Button updateButton;
    private Button ratingButton;
    private Button deleteButton;
    private ArrayList<Request> requestsToDelete = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent listingIntent = getIntent();
        final Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");
        if(thisListing != null) {
            String listingName = thisListing.getListingName();
            String listingOwner = thisListing.getOwnerEmail();

            //listingOwner should show email, but listings made early on did not fill in email field
            if (listingOwner == null) listingOwner = thisListing.getOwnerId();
            String listingAddress = thisListing.getListingAddress();
            String listingDescription = thisListing.getListingDescription();
            String listingStart = thisListing.getStartTime();
            String listingEnd = thisListing.getEndTime();
            String listingPrice = thisListing.getListingPrice();

            listingRatingBar = findViewById(R.id.ratingBarListing);
            listingNameText = findViewById(R.id.listingNameText);
            listingOwnerText = findViewById(R.id.listingOwnerText);
            listingAddressText = findViewById(R.id.listingAddressText);
            listingDescriptionText = findViewById(R.id.listingDescriptionText);
            listingPriceText = findViewById(R.id.listingPriceText);
            listingStartText = findViewById(R.id.listingStart);
            listingEndText = findViewById(R.id.listingEnd);

            listingNameText.setText(listingName);
            listingOwnerText.setText(listingOwner);
            listingAddressText.setText(listingAddress);
            listingDescriptionText.setText(listingDescription);
            listingPriceText.setText("$" + listingPrice + "/hour");

            String AM1 = "AM";
            String AM2 = "AM";
            if (!thisListing.getTimeDetails().isStartingIsAM()) {
                AM1 = "PM";
            }
            if (!thisListing.getTimeDetails().isEndingIsAM()) {
                AM2 = "PM";
            }
            listingStartText.setText("Start Time: " + listingStart + AM1);
            listingEndText.setText("End Time: " + listingEnd + AM2);

            backToHomeButton = findViewById(R.id.buttonGoHome);
            viewRatingsButton = findViewById(R.id.buttonViewRatings);
            requestButton = findViewById(R.id.requestButton);
            ratingButton = findViewById(R.id.ratingButton);
            deleteButton = findViewById(R.id.deleteButton);
            updateButton = findViewById(R.id.updateButton);

            if (thisListing.getOwnerId() != null) {
                if (!thisListing.getOwnerEmail().equals(user.getEmail())) {
                    updateButton.setVisibility(View.GONE);
                }
            }

            final DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
            ParkingSpotDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ParkingSpot ps = dataSnapshot.child(thisListing.getParkingSpot().getParkingSpotId()).getValue(ParkingSpot.class);
                    listingRatingBar.setRating((float) ps.getAvgRating());
                    if (ps.ratingsList.size() > 0) {
                        if (ps.ratingsList.get(0).getRating() != 100.0) {
                            viewRatingsButton.setText("Click to View " + ps.ratingsList.size() + " Ratings");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });

            if (thisListing.getOwnerId() != null) {
                if (thisListing.getOwnerId().equals(user.getEmail()) || thisListing.getOwnerId().equals(user.getUid())) {
                    deleteButton.setVisibility(View.VISIBLE);
                    updateButton.setVisibility(View.VISIBLE);
                    ratingButton.setVisibility(View.GONE);
                    requestButton.setVisibility(View.GONE);
                } else {
                    deleteButton.setVisibility(View.GONE);
                    updateButton.setVisibility(View.GONE);
                    ratingButton.setVisibility(View.VISIBLE);
                    requestButton.setVisibility(View.VISIBLE);
                }
            }

            if (thisListing.getOwnerId() != null) {
                if (thisListing.getOwnerId().equals(user.getEmail()) || thisListing.getOwnerId().equals(user.getUid())) {
                    requestButton.setVisibility(View.GONE);
                }
            }

            if (thisListing.getRenterId() != null) {
                if (thisListing.getRenterId().equals(user.getEmail()) || thisListing.getRenterId().equals(user.getUid())) {
                    requestButton.setVisibility(View.GONE);
                }
            }


            viewRatingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ratingIntent = new Intent(getApplicationContext(), ViewRatingsActivity.class);
                    ratingIntent.putExtra("SPOT", thisListing.getParkingSpot());
                    startActivity(ratingIntent);
                }
            });


            requestButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent createRequestIntent = new Intent(getApplicationContext(), CreateRequestActivity.class);
                    createRequestIntent.putExtra("listing", thisListing);
                    startActivity(createRequestIntent);
                }
            });

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent updateListingIntent = new Intent(getApplicationContext(), UpdateListingActivity.class);
                    updateListingIntent.putExtra("listing", thisListing);
                    startActivity(updateListingIntent);
                }
            });

            ratingButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent createRatingIntent = new Intent(getApplicationContext(), RatingActivity.class);
                    createRatingIntent.putExtra("listing", thisListing);
                    createRatingIntent.putExtra("SPOT", thisListing.getParkingSpot());
                    startActivity(createRatingIntent);
                }
            });


            backToHomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                    Toast.makeText(ViewListingActivity.this, "Deleted listing...", Toast.LENGTH_SHORT).show();

                    requestDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                                Request request = requestSnapshot.getValue(Request.class);
                                if(request != null) {
                                    if(request.getListingID().equals(thisListing.getListingId())) {
                                        requestsToDelete.add(request);
                                    }
                                }
                            }

                            for(Request request : requestsToDelete) {
                                requestDatabase.child(request.getRequestID()).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    listingDatabase.child(thisListing.getListingId()).removeValue();
                }
            });

            backToHomeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                }
            });
        }
        else{
            startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
        }
    }


}
