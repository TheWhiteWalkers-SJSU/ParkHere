package com.thewhitewalkers.parkhere;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.google.firebase.auth.FirebaseAuth;

public class SearchListingActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Spinner spinnerSort;
    private EditText editTextSearch;
    private Button buttonSearch;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_listing);

        firebaseAuth = firebaseAuth.getInstance();
        progressBar = new ProgressBar(this);

        addItemsOnSpinner();
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);

        buttonSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.isShown();
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
