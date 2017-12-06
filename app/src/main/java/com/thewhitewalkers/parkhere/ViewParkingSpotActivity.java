package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ViewParkingSpotActivity extends AppCompatActivity {

    DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
    DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");
    DatabaseReference RequestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    FirebaseAuth firebaseAuth;

    private TextView ParkingSpotNameText;
    private TextView ParkingSpotAddressText;
    private TextView ParkingSpotDescriptionText;
    private TextView ParkingSpotOwnerText;
    private Button updateButton;
    private Button deleteButton;
    private Button homeButton;
    private RatingBar listingRatingBar;
    private Button viewRatingsButton;
    private ArrayList<Listing> listingsToDelete = new ArrayList();
    private ArrayList<Request> requestsToDelete = new ArrayList();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking_spot);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent ParkingSpotIntent = getIntent();
        final ParkingSpot thisParkingSpot = (ParkingSpot) ParkingSpotIntent.getSerializableExtra("ParkingSpot");
        if(thisParkingSpot != null) {


            String ParkingSpotName = thisParkingSpot.getName();
            String ParkingSpotOwner = thisParkingSpot.getOwnerEmail();

            if (ParkingSpotOwner == null) ParkingSpotOwner = thisParkingSpot.getOwnerId();
            String ParkingSpotAddress = thisParkingSpot.getAddress();
            String ParkingSpotDescription = thisParkingSpot.getDescription();

            //RATING
            listingRatingBar = findViewById(R.id.ratingBarListingForSP);
            viewRatingsButton = findViewById(R.id.buttonViewRatingsForSP);

            final DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
            ParkingSpotDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ParkingSpot ps = dataSnapshot.child(thisParkingSpot.getParkingSpotId()).getValue(ParkingSpot.class);
                    listingRatingBar.setRating((float)ps.getAvgRating());
                    if(ps.ratingsList.size() > 0) {
                        if(ps.ratingsList.get(0).getRating() != 100.0) {
                            viewRatingsButton.setText("Click to View " + ps.ratingsList.size() + " Ratings");
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {}
            });

            viewRatingsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent ratingIntent = new Intent(getApplicationContext(), ViewRatingsActivity.class);
                    ratingIntent.putExtra("SPOT", thisParkingSpot);
                    startActivity(ratingIntent);
                }
            });

            ParkingSpotNameText = findViewById(R.id.textViewName);
            ParkingSpotOwnerText = findViewById(R.id.textViewOwner);
            ParkingSpotAddressText = findViewById(R.id.textViewAddress);
            ParkingSpotDescriptionText = findViewById(R.id.textViewDescription);

            deleteButton = findViewById(R.id.deleteButton);
            updateButton = findViewById(R.id.updateButton);
            homeButton = findViewById(R.id.homeButton);

            ParkingSpotNameText.setText(ParkingSpotName);
            ParkingSpotOwnerText.setText(ParkingSpotOwner);
            ParkingSpotAddressText.setText(ParkingSpotAddress);
            ParkingSpotDescriptionText.setText(ParkingSpotDescription);

            if (thisParkingSpot.getOwnerId() != null) {
                if (!thisParkingSpot.getOwnerEmail().equals(user.getEmail())) {
                    updateButton.setVisibility(View.GONE);
                }
            }

            if (thisParkingSpot.getOwnerId() != null) {
                if (thisParkingSpot.getOwnerId().equals(user.getEmail()) || thisParkingSpot.getOwnerId().equals(user.getUid())) {
                    deleteButton.setVisibility(View.VISIBLE);
                    updateButton.setVisibility(View.VISIBLE);
                } else {
                    deleteButton.setVisibility(View.GONE);
                    updateButton.setVisibility(View.GONE);
                }
            }

            updateButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent updateParkingSpotIntent = new Intent(getApplicationContext(), UpdateParkingSpotActivity.class);
                    updateParkingSpotIntent.putExtra("ParkingSpot", thisParkingSpot);
                    startActivity(updateParkingSpotIntent);
                }
            });

            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));

                    // Delete corresponding listings for parking spot to delete
                    ListingDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            for(DataSnapshot listingSnapshot : dataSnapshot.getChildren()) {
                                Listing listing = listingSnapshot.getValue(Listing.class);
                                if(listing != null) {
                                    if(listing.getParkingSpot().getParkingSpotId().equals(thisParkingSpot.getParkingSpotId())) {
                                        listingsToDelete.add(listing);
                                    }
                                }
                            }

                            for(final Listing listing : listingsToDelete) {
                                RequestDatabase.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        for(DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                                            Request request = requestSnapshot.getValue(Request.class);
                                            if(request != null) {
                                                if(request.getListingID().equals(listing.getListingId())) {
                                                    requestsToDelete.add(request);
                                                }
                                            }
                                        }

                                        for(Request request : requestsToDelete) {
                                            RequestDatabase.child(request.getRequestID()).removeValue();
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError databaseError) {

                                    }
                                });
                                ListingDatabase.child(listing.getListingId()).removeValue();
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    ParkingSpotDatabase.child(thisParkingSpot.getParkingSpotId()).removeValue();
                }
            });

            homeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
                }
            });
        }
        else{
            startActivity(new Intent(getApplicationContext(), ViewMyParkingSpotsActivity.class));
        }
    }
}
