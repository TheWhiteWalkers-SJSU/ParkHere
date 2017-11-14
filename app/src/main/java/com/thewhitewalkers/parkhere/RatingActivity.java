package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.EditorInfo;
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

import java.util.ArrayList;

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

        editTextComment.setImeOptions(EditorInfo.IME_ACTION_DONE);
        editTextComment.setRawInputType(InputType.TYPE_CLASS_TEXT);
    }
    private boolean submitRating(){
        final double numOfStars = ratingBar.getRating();
        final String comment = editTextComment.getText().toString(); //todo: save comments to database

        if(numOfStars != 0 && !TextUtils.isEmpty(comment)){
            //save the rating to listing owner
            final DatabaseReference userDatabase1 = FirebaseDatabase.getInstance().getReference("users");
            userDatabase1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    User u = dataSnapshot.child(currentListing.getOwnerId()).getValue(User.class);
                    Rating r = new Rating(numOfStars, comment);
                    u.ratingsList.add(r);

                    userDatabase1.child(currentListing.getOwnerId()).setValue(u);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

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
