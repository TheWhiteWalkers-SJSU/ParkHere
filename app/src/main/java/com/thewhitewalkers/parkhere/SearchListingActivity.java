package com.thewhitewalkers.parkhere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchListingActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Spinner spinnerSort;
    private EditText editTextSearch;
    private Button buttonSearch;
    private Button buttonHomepage;
    private ListView listSearchListings;
    private List<Listing> searchList = new ArrayList<>();

    private FirebaseAuth firebaseAuth;
    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_listing);

        firebaseAuth = firebaseAuth.getInstance();
        progressBar = new ProgressBar(this);

        addItemsOnSpinner();
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonHomepage = findViewById(R.id.buttonHomepage);
        listSearchListings = findViewById(R.id.listSearchListings);


        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.isShown();
                //for now, no filtering possible
                Toast.makeText(SearchListingActivity.this, "Cannot search with keywords currently", Toast.LENGTH_SHORT).show();
            }
        });

        listSearchListings.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // open the view listing layout for the selected search item
                Intent viewListingIntent = new Intent(getApplicationContext(), ViewListingActivity.class);
                Listing selectedListing = (Listing)adapterView.getItemAtPosition(position);
                viewListingIntent.putExtra("listing", selectedListing);
                try {
                    startActivity(viewListingIntent);
                } catch (Exception e) {
                    Toast.makeText(SearchListingActivity.this, e.toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        ListingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                searchList.clear();
                for(DataSnapshot listingSnapshot : dataSnapshot.getChildren()) {
                    Listing listing = listingSnapshot.getValue(Listing.class);
                    if(listing.getListingStatus().equals("available")) {
                        searchList.add(listing);
                    }
                }
                SearchList adapter = new SearchList(SearchListingActivity.this, searchList);
                listSearchListings.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addItemsOnSpinner() {
        spinnerSort = findViewById(R.id.spinnerSort);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> sortAdapter = ArrayAdapter.createFromResource(this, R.array.sort_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSort.setAdapter(sortAdapter);
    }
}
