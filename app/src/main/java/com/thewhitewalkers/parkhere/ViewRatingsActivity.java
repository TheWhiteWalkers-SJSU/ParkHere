package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class ViewRatingsActivity extends AppCompatActivity {

    private Button buttonBackToListing;
    private TextView ratingsTitle;
    private TextView textViewRatings;
    private RatingBar ratingBar;
    private ListView ratingList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_ratings);

        Intent listingIntent = getIntent();
        final Listing thisListing = (Listing) listingIntent.getSerializableExtra("listing");

        final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");
        userDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User u = dataSnapshot.child(thisListing.getOwnerId()).getValue(User.class);
                double numOfRatings = u.ratingsList.size();

                if(numOfRatings != 0 && u.ratingsList.get(0).getRating() != 100.0){
                    if(numOfRatings != 0){
                        textViewRatings.setText((int)numOfRatings + " Ratings");
                        ratingBar.setRating((float) u.getAvgRating());
                    }
                    RatingsList adapter = new RatingsList(ViewRatingsActivity.this, u.ratingsList);
                    ratingList.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        buttonBackToListing = findViewById(R.id.buttonBackToListing);
        ratingsTitle = findViewById(R.id.ratingsTitle);
        ratingBar = findViewById(R.id.ratingBarRating);
        textViewRatings = findViewById(R.id.textViewRatings);
        ratingList = findViewById(R.id.ratingListView);

        buttonBackToListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
                backToListingIntent.putExtra("listing", thisListing);
                startActivity(backToListingIntent);
            }
        });

    }
}