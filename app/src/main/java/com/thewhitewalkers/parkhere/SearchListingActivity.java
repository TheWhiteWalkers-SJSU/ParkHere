package com.thewhitewalkers.parkhere;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.Toast;
import android.content.Intent;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SearchListingActivity extends AppCompatActivity {

    private Spinner spinnerSort;
    private EditText editTextSearch;
    private Button buttonSearch;
    private Button buttonRange;
    private Button buttonHomepage;
    private ListView listSearchListings;
    private List<Listing> searchList = new ArrayList<>();
    private TextView searchRangeSet;
    private String textRangeSetFalse;
    private String textRangeSetTrue;

    private TimeDetails searchTime;
    private boolean isStartingAM;
    private boolean isEndingAM;

    private String searchKeyword;
    private boolean isRangeSet;

    final DatabaseReference ListingDatabase = FirebaseDatabase.getInstance().getReference("listings");
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    //views for time/date range dialog
    private EditText editTextStartDate;
    private EditText editTextEndDate;
    private EditText editTextStartTime;
    private EditText editTextEndTime;
    private ToggleButton toggleStartingAM;
    private ToggleButton toggleEndingAM;

    private String startDate;
    private String endDate;
    private String startTime;
    private String endTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_listing);

        searchTime = new TimeDetails();
        searchKeyword = "";

        addItemsOnSpinner();
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        buttonRange = findViewById(R.id.buttonSearchRange);
        buttonHomepage = findViewById(R.id.buttonHomepage);
        listSearchListings = findViewById(R.id.listSearchListings);
        searchRangeSet = findViewById(R.id.searchRangeSet);

        textRangeSetFalse = "No range set";
        textRangeSetTrue = "";
        isRangeSet = false;

        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchKeyword = editTextSearch.getText().toString().trim();
                updateSearch();
                Toast.makeText(SearchListingActivity.this, "Searching", Toast.LENGTH_SHORT).show();
            }
        });

        buttonRange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimeDialog();
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
        updateSearch();
    }

    private void updateSearch() {
        ListingDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                searchList.clear();
                for(DataSnapshot listingSnapshot : dataSnapshot.getChildren()) {
                    Listing listing = listingSnapshot.getValue(Listing.class);
                    if(listing.getListingStatus().equals("available") && (!listing.getOwnerId().equals(user.getEmail()) && !listing.getOwnerId().equals(user.getUid()))) {
                        if(listing.getListingName().contains(searchKeyword) || listing.getListingAddress().contains(searchKeyword) || listing.getListingDescription().contains(searchKeyword)) {
                            //TODO: if(isRangeSet) then only get listings that within the time details range
                            searchList.add(listing);
                        }
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

    public void showTimeDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.search_filter, null);

        builder.setView(view)
                .setTitle("Set date/time range")
                .setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Dialog d = (Dialog) dialog;

                        //get views
                        editTextStartDate = d.findViewById(R.id.editTextSearchStartDate);
                        editTextEndDate = d.findViewById(R.id.editTextSearchEndDate);
                        editTextStartTime = d.findViewById(R.id.editTextSearchStartTime);
                        editTextEndTime = d.findViewById(R.id.editTextSearchEndTime);
                        toggleStartingAM = d.findViewById(R.id.searchStartAM);
                        toggleEndingAM = d.findViewById(R.id.searchEndAM);

                        //get text from the views
                        startDate = editTextStartDate.getText().toString();
                        endDate = editTextEndDate.getText().toString().trim();
                        startTime = editTextStartTime.getText().toString().trim();
                        endTime = editTextEndTime.getText().toString().trim();

                        //set the toggle AM variables
                        setToggleAM(toggleStartingAM.getText().equals("AM"), toggleEndingAM.getText().equals("AM"));

                        //check date and time have correct format and are valid
                        String checkDateResult = checkBookingDate(startDate, endDate);
                        String checkTimeResult = checkBookingTime(startTime, endTime);
                        if(!checkDateResult.equals("")) {
                            //date invalid/wrong format
                            Toast.makeText(SearchListingActivity.this, checkDateResult, Toast.LENGTH_SHORT).show();
                            searchRangeSet.setText(textRangeSetFalse);
                            isRangeSet = false;
                        }
                        else if (!checkTimeResult.equals("")) {
                            //time invalid/wrong format
                            Toast.makeText(SearchListingActivity.this, checkTimeResult, Toast.LENGTH_SHORT).show();
                            searchRangeSet.setText(textRangeSetFalse);
                            isRangeSet = false;
                        }
                        else {
                            //valid/correct format, put time info into TimeDetails
                            searchTime = new TimeDetails(startDate, endDate, startTime, isStartingAM, endTime, isEndingAM);
                            Toast.makeText(SearchListingActivity.this, "Updating search", Toast.LENGTH_SHORT).show();
                            searchRangeSet.setText(textRangeSetTrue + startDate + " @ " + startTime + " to " + endDate + " @ " + endTime);
                            isRangeSet = true;
                            //TODO : call update search
                        }
                        dialog.dismiss();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .create().show();

    }

    public String checkBookingDate(String dateStarting, String dateEnding){
        if(!searchTime.checkDateFormat(dateStarting) || !searchTime.checkDateFormat(dateEnding) ){
            return "Invalid range: dates must be in MM/DD/YYYY format!";
        }
        else if(!searchTime.checkDateValid(dateStarting, dateEnding)){
            return "Invalid range: starting date should be before ending date!";
        }
        return "";
    }

    public String checkBookingTime(String timeStarting, String timeEnding){
        if(!searchTime.checkTimeFormat(timeStarting) || !searchTime.checkTimeFormat(timeEnding)){
            return "Invalid range: times must be in HH:MM format!";
        }
        else if(timeStarting.equals(timeEnding) && (isStartingAM == isEndingAM) ){
            return "Invalid range: times can't be the same!";
        }
        return "";
    }

    public void setToggleAM(boolean start, boolean end) {
        isStartingAM = start;
        isEndingAM = end;
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
