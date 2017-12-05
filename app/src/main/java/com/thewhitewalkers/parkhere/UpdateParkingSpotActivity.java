package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class UpdateParkingSpotActivity extends AppCompatActivity {

    private EditText editTextParkingSpotName;
    private EditText editTextParkingSpotAddress;
    private EditText editTextParkingSpotDescription;
    private Button buttonUpdateParkingSpot;
    private Button buttonBackToParkingSpot;

    private ParkingSpot currentParkingSpot;
    final DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_parking_spot);

        currentParkingSpot = (ParkingSpot) getIntent().getSerializableExtra("ParkingSpot");

        editTextParkingSpotName = findViewById(R.id.editTextParkingSpotName);
        editTextParkingSpotAddress = findViewById(R.id.editTextParkingSpotAddress);
        editTextParkingSpotDescription = findViewById(R.id.editTextParkingSpotDescription);

        buttonUpdateParkingSpot = findViewById(R.id.buttonUpdateParkingSpot);
        buttonUpdateParkingSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateParkingSpotFields();
            }
        });

        buttonBackToParkingSpot = findViewById(R.id.buttonBackToParkingSpot);
        buttonBackToParkingSpot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent viewParkingSpotIntent = new Intent(getApplicationContext(), ViewParkingSpotActivity.class);
                viewParkingSpotIntent.putExtra("ParkingSpot", currentParkingSpot);
                startActivity(viewParkingSpotIntent);
            }
        });
    }

    public void updateParkingSpotFields() {
        Map<String, Object> ParkingSpotUpdate = new HashMap<>();

        // Fetch the text entered by the user and set it to a String
        String newParkingSpotName = editTextParkingSpotName.getText().toString().trim();
        String newParkingSpotAddress = editTextParkingSpotAddress.getText().toString().trim();
        String newParkingSpotDescription = editTextParkingSpotDescription.getText().toString().trim();

        // if the value is not empty add it to the update hashmap
        if(!newParkingSpotName.isEmpty()) {
            ParkingSpotUpdate.put("name", newParkingSpotName);
        }
        if(!newParkingSpotAddress.isEmpty()) {
            ParkingSpotUpdate.put("address", newParkingSpotAddress);
        }
        if(!newParkingSpotDescription.isEmpty()) {
            ParkingSpotUpdate.put("description", newParkingSpotDescription);
        }

        ParkingSpotDatabase.child(currentParkingSpot.getParkingSpotId()).updateChildren(ParkingSpotUpdate)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(UpdateParkingSpotActivity.this, "Updated Parking Spot...", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ViewMyParkingSpotsActivity.class));
                    }
                });
    }
}

