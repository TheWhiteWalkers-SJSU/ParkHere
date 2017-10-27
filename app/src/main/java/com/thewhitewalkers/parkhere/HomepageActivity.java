package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.tasks.OnCompleteListener;


public class HomepageActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private TextView userEmail;
    private Button buttonLogout;
    private Button buttonCreateListing;
    private Button buttonSearchListing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        firebaseAuth = firebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();

        userEmail = findViewById(R.id.userEmail);
        userEmail.setText("Welcome to ParkHere "+user.getEmail());

        buttonCreateListing = findViewById(R.id.buttonCreateListing);
        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), CreateListingActivity.class));
            }
        });

        buttonSearchListing = findViewById(R.id.buttonSearchListing);
        buttonSearchListing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SearchListingActivity.class));
            }
        });

        buttonLogout = findViewById(R.id.buttonLogout);
        buttonLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HomepageActivity.this, "logged out user", Toast.LENGTH_SHORT).show();
                firebaseAuth.signOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

    }
}
