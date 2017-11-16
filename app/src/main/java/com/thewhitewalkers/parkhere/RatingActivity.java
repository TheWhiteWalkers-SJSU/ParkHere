package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RatingActivity extends AppCompatActivity {

    Button buttonBackToListing;
    Button buttonSubmitRating;
    RatingBar ratingBar;
    EditText editTextComment;
    TextView description1;
    TextView description2;

    private Listing currentListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        currentListing = (Listing) getIntent().getSerializableExtra("listing");

        buttonBackToListing = findViewById(R.id.buttonBackToListing);
        buttonSubmitRating = findViewById(R.id.buttonSubmitRating);
        ratingBar = findViewById(R.id.ratingBar);
        editTextComment = findViewById(R.id.editTextComment);
        description1 = findViewById(R.id.description1);
        description2 = findViewById(R.id.description2);

        buttonBackToListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                viewListing();
            }
        });

        buttonSubmitRating.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //save rating in submitRating method
                if(!submitRating()){
                    Toast.makeText(getApplicationContext(),
                            "Please enter a comment and a rating!", Toast.LENGTH_SHORT).show();
                }
                else{


                    // redirect to listing
                    viewListing();
                }

            }
        });

    }
    private boolean submitRating(){
        final int numOfStars = ratingBar.getNumStars();
        String comment = editTextComment.getText().toString(); //todo: save comments to database

        if(numOfStars != 0 && !TextUtils.isEmpty(comment)){
            //save the rating to listing owner
            final DatabaseReference userDatabase = FirebaseDatabase.getInstance().getReference("users");
            userDatabase.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(DataSnapshot dataSnapshot){
                    if(dataSnapshot.getKey() == currentListing.getOwnerId()){
                        User owner = dataSnapshot.getValue(User.class);
                        owner.setAvgRating(numOfStars);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError){

                }
            });
            return true;
        }
        return false;
    }
    private void viewListing(){
        // redirect to listing
        Toast.makeText(getApplicationContext(),
                "Redirect to Listing...", Toast.LENGTH_SHORT).show();
        Intent viewListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
        viewListingIntent.putExtra("listing", currentListing);
        try {
            startActivity(viewListingIntent);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
