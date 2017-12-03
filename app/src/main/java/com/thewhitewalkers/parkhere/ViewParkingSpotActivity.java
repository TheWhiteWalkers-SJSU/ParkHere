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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ViewParkingSpotActivity extends AppCompatActivity {

    DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
    DatabaseReference requestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    FirebaseAuth firebaseAuth;

    private TextView ParkingSpotNameText;
    private TextView ParkingSpotAddressText;
    private TextView ParkingSpotDescriptionText;
    private TextView ParkingSpotOwnerText;
    private Button updateButton;
    private Button deleteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_parking_spot);

        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        Intent ParkingSpotIntent = getIntent();
        final ParkingSpot thisParkingSpot = (ParkingSpot) ParkingSpotIntent.getSerializableExtra("ParkingSpot");

        String ParkingSpotName = thisParkingSpot.getName();
        String ParkingSpotOwner = thisParkingSpot.getOwnerEmail();

        if(ParkingSpotOwner == null) ParkingSpotOwner = thisParkingSpot.getOwnerId();
        String ParkingSpotAddress = thisParkingSpot.getAddress();
        String ParkingSpotDescription = thisParkingSpot.getDescription();

        ParkingSpotNameText = findViewById(R.id.editTextName);
        ParkingSpotOwnerText = findViewById(R.id.editTextOwner);
        ParkingSpotAddressText = findViewById(R.id.editTextAddress);
        ParkingSpotDescriptionText = findViewById(R.id.editTextDescription);

        deleteButton = findViewById(R.id.deleteButton);
        updateButton = findViewById(R.id.updateButton);

        ParkingSpotNameText.setText(ParkingSpotName);
        ParkingSpotOwnerText.setText(ParkingSpotOwner);
        ParkingSpotAddressText.setText(ParkingSpotAddress);
        ParkingSpotDescriptionText.setText(ParkingSpotDescription);

        if(thisParkingSpot.getOwnerId() != null) {
            if (!thisParkingSpot.getOwnerEmail().equals(user.getEmail())) {
                updateButton.setVisibility(View.GONE);
            }
        }

        if(thisParkingSpot.getOwnerId() != null) {
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
                Toast.makeText(ViewParkingSpotActivity.this, "Deleted ParkingSpot...", Toast.LENGTH_SHORT).show();
                ParkingSpotDatabase.child(thisParkingSpot.getParkingSpotId()).removeValue();
            }
        });


    }
}
