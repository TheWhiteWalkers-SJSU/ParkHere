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

public class InboxActivity extends AppCompatActivity {

    private TextView inboxTitle;
    private Button buttonHomepage;
    private Button requestButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);

        inboxTitle = findViewById(R.id.inboxTitle);
        buttonHomepage = findViewById(R.id.buttonHomepage);
        requestButton = findViewById(R.id.inboxRequest);

        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        String[] myStringArray = new String[6];
        myStringArray[0] = "Booking Success for: \n1 Washington Square\non 10/27 - 10/30\nfrom 1:30pm - 2:45 pm";
        myStringArray[1] = "Booking Pending for: \n1 Washington Square\non 10/27 - 10/30\nfrom 1:30pm - 2:45 pm";
        myStringArray[2] = "Booking Denied! \n1 Washington Square\non 10/1 - 10/30\nfrom 1:30pm - 2:45 pm";
        myStringArray[3] = "Booking Denied! \n1 Washington Square\non 10/1 - 10/30\nfrom 1:30pm - 2:45 pm";
        myStringArray[4] = "Review Request for Your Space on: \n1 Washington Square\non 11/1 - 11/30\nfrom 10:30am - 2:45 pm";
        myStringArray[5] = "Review Request for Your Space on: \n1 Washington Square\non 12/1 - 12/30\nfrom 10:30am - 2:45 pm";
        int requestNum = myStringArray.length; //get request number
        if(myStringArray.length == 1){
            myStringArray[0] = "No messages";
            requestNum = 0;
        }


        requestButton.setText("Requests (" + requestNum + ")");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,  R.layout.inbox_item, myStringArray);

        ListView listView = (ListView) findViewById(R.id.inboxListView);
        listView.setAdapter(adapter);
        // Create a message handling object as an anonymous class.


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView parent, View v, int position, long id) {
                Toast.makeText(getApplicationContext(),
                        "Opening message...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), ViewRequestActivity.class));
            }
        });

    }

}
