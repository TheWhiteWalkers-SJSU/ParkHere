package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class HomepageActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private TextView userEmail;
    private Button buttonLogout;
    private Button buttonInbox;
    private Button buttonCreateListing;
    private Button buttonSearchListing;
    private ListView listViewListings;
    private ListView listViewBookings;
    private List<Listing> listingList = new ArrayList<>();
    private List<Listing> bookingList = new ArrayList<>();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    final DatabaseReference UserDatabase = FirebaseDatabase.getInstance().getReference("users").child(user.getUid());
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        userEmail = findViewById(R.id.userEmail);
        userEmail.setText("Welcome to ParkHere "+user.getEmail());

        listViewListings = findViewById(R.id.listViewListings);
        listViewBookings = findViewById(R.id.listViewBookings);

        buttonCreateListing = findViewById(R.id.buttonCreateListing);
        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateListingActivity.class));
            }
        });

        buttonSearchListing = findViewById(R.id.buttonSearchListing);
        buttonSearchListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchListingActivity.class));
            }
        });

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageActivity.this, "logged out user", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });

        buttonInbox = findViewById(R.id.buttonInbox);
        buttonInbox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), InboxActivity.class));
            }
        });

        buttonSearchListing = findViewById(R.id.buttonSearchListing);
        buttonSearchListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchListingActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        ListingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                listingList.clear();
                for(DataSnapshot listingSnapshot : dataSnapshot.getChildren()) {
                    Listing listing = listingSnapshot.getValue(Listing.class);
                    // TODO: need to change to match only email, accepting uuid because of old entries in DB
                    if(listing != null) {
                        if(listing.getOwnerId().equals(user.getEmail()) || listing.getOwnerId().equals(user.getUid())) {
                            listingList.add(listing);
                        } else if (listing.getRenterId() != null) {
                            if(listing.getRenterId().equals(user.getEmail()))
                            bookingList.add(listing);
                        }
                    }
                }
                ListingList listingsAdapter = new ListingList(HomepageActivity.this, listingList);
                ListingList bookingsAdapter = new ListingList(HomepageActivity.this, bookingList);
                listViewListings.setAdapter(listingsAdapter);
                listViewBookings.setAdapter(bookingsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
