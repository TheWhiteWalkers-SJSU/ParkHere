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
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Opening message...", Toast.LENGTH_SHORT).show();
                Intent viewMessageIntent = new Intent(getApplicationContext(), ViewRequestActivity.class);
                Request clickedMessage = (Request)parent.getItemAtPosition(position);
                viewMessageIntent.putExtra("message", clickedMessage);
                startActivity(viewMessageIntent);
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
                    requestsList.add(request);
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
