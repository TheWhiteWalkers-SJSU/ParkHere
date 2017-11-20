package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
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
import java.util.List;

public class InboxActivity extends AppCompatActivity {

    private TextView inboxTitle;
    private Button buttonHomepage;
    private Button requestButton;
    private ListView inboxList;
    private List<Request> requestsList = new ArrayList<>();
    final DatabaseReference RequestDatabase = FirebaseDatabase.getInstance().getReference("requests");
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        inboxTitle = findViewById(R.id.inboxTitle);
        buttonHomepage = findViewById(R.id.buttonHomepage);
        requestButton = findViewById(R.id.inboxRequest);
        inboxList = findViewById(R.id.inboxListView);

        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        inboxList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(final AdapterView parent, View v, final int position, long id) {
                final Request clickedRequest = (Request)parent.getItemAtPosition(position);
                clickedRequest.setHasBeenRead(true);
                RequestDatabase.child(clickedRequest.getRequestID()).setValue(clickedRequest);
                // Attach a listener to read the data at our posts reference
                ListingDatabase.addValueEventListener(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Toast.makeText(getApplicationContext(), "Opening message...", Toast.LENGTH_SHORT).show();
                        Intent viewMessageIntent = new Intent(getApplicationContext(), ViewRequestActivity.class);

                        Listing listing = dataSnapshot.child(clickedRequest.getListingID()).getValue(Listing.class);

                        viewMessageIntent.putExtra("listing", listing);
                        viewMessageIntent.putExtra("request", clickedRequest);
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
        RequestDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                requestsList.clear();
                for(DataSnapshot requestSnapshot : dataSnapshot.getChildren()) {
                    Request request = requestSnapshot.getValue(Request.class);
                    // TODO: need to change to match only email, accepting uuid because of old entries in DB
                    if(request.getRecipientID().equals(user.getEmail()) || request.getRecipientID().equals(user.getUid())){
                        requestsList.add(request);
                    }
                }
                RequestList adapter = new RequestList(InboxActivity.this, requestsList);
                inboxList.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
