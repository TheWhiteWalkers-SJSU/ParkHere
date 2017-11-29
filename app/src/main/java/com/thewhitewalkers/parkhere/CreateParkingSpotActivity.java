
package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class CreateParkingSpotActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference parkingSpotDatabase;


    private EditText editTextName;
    private EditText editTextAddress;
    private EditText editTextDescription;
    private Button buttonCreateListing;
    private Button buttonHomepage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        firebaseAuth = FirebaseAuth.getInstance();
        parkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");

        editTextName = findViewById(R.id.editTextName);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextDescription = findViewById(R.id.editTextDescription);

        buttonCreateListing = findViewById(R.id.buttonCreateListing);
        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createListing();
            }
        });

        buttonHomepage = findViewById(R.id.buttonHomepage);
        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

    }

    private void createListing(){
        // get the values from all the fields and save them to the Firebase db
        String parkingSpotName = editTextName.getText().toString().trim();
        String parkingSpotAddress = editTextAddress.getText().toString().trim();
        String parkingSpotDescription = editTextDescription.getText().toString().trim();

        FirebaseUser user = firebaseAuth.getCurrentUser(); //get user

        if(!TextUtils.isEmpty(parkingSpotName) || !TextUtils.isEmpty(parkingSpotAddress) || !TextUtils.isEmpty(parkingSpotDescription)) {
            Toast.makeText(CreateParkingSpotActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
        } else {
            String _id = parkingSpotDatabase.push().getKey();

            ParkingSpot newParkingSpot = new ParkingSpot(_id, parkingSpotName, parkingSpotAddress, parkingSpotDescription, user.getUid(), user.getEmail());
            parkingSpotDatabase.child(_id).setValue(newParkingSpot);

            Toast.makeText(CreateParkingSpotActivity.this, "Created Listing", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(CreateParkingSpotActivity.this, HomepageActivity.class));
        }
    }

}

