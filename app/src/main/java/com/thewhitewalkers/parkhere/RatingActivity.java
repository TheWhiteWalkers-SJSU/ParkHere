package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class RatingActivity extends AppCompatActivity {

    Button buttonBackToListing;
    Button buttonSubmitRating;
    RatingBar ratingBar;
    EditText editTextComment;
    TextView description1;
    TextView description2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

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
                //method to submit the rating and save it to the listing
            }
        });

    }

    private void viewListing(){
        //redirect to listing
        /*
        Toast.makeText(getApplicationContext(),
                "Redirect to Listing...", Toast.LENGTH_SHORT).show();
        Intent viewListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
        viewListingIntent.putExtra("listing", currentListing);
        try {
            startActivity(viewListingIntent);
        } catch (Exception e) {
            Toast.makeText(ViewRequestActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
        }
        */
    }
}
