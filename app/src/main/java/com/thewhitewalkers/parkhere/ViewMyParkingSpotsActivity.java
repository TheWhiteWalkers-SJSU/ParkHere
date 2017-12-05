package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ViewMyParkingSpotsActivity extends AppCompatActivity {

    final DatabaseReference ParkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");
    private List<ParkingSpot> parkingSpotList = new ArrayList<>();
    Button buttonHomeForViewMyParking;
    ListView listViewParkingSpots;

    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    Button homeButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_parking_spots);


        buttonHomeForViewMyParking = findViewById(R.id.buttonHomeForViewMyParking);
        listViewParkingSpots = findViewById(R.id.listViewParkingSpots);

        buttonHomeForViewMyParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        listViewParkingSpots.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView parent, View v, final int position, long id) {
                final ParkingSpot clickedParkingSpot = (ParkingSpot)parent.getItemAtPosition(position);
                // Attach a listener to read the data at our posts reference
                ParkingSpotDatabase.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Intent viewMessageIntent = new Intent(getApplicationContext(), ViewParkingSpotActivity.class);

                        ParkingSpot ParkingSpot = dataSnapshot.child(clickedParkingSpot.getParkingSpotId()).getValue(ParkingSpot.class);

                        viewMessageIntent.putExtra("ParkingSpot", ParkingSpot);
                        startActivity(viewMessageIntent);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        System.out.println("The read failed: " + databaseError.getCode());
                    }
                });
            }
        });
    }



    @Override
    protected void onStart() {
        super.onStart();

        ParkingSpotDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                parkingSpotList.clear();
                for(DataSnapshot ParkingSpotSnapshot : dataSnapshot.getChildren()) {
                    ParkingSpot parkingSpot = ParkingSpotSnapshot.getValue(ParkingSpot.class);
                    // TODO: need to change to match only email, accepting uuid because of old entries in DB
                    if(parkingSpot != null) {
                        if(parkingSpot.getOwnerId().equals(user.getEmail()) || parkingSpot.getOwnerId().equals(user.getUid())) {
                            parkingSpotList.add(parkingSpot);
                        }
                    }
                }
                ParkingSpotList ParkingSpotsAdapter = new ParkingSpotList(ViewMyParkingSpotsActivity.this, parkingSpotList);
                listViewParkingSpots.setAdapter(ParkingSpotsAdapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
