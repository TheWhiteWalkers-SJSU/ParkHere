
package com.thewhitewalkers.parkhere;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.HashMap;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;

public class CreateParkingSpotActivity extends AppCompatActivity {

    FirebaseAuth firebaseAuth;
    DatabaseReference parkingSpotDatabase;


    private EditText editTextName;
    private Button buttonFindParkingAddress;
    private EditText editTextDescription;
    private Button buttonCreateListing;
    private Button buttonHomepage;

    //address dialog
    private AddressResultReceiver receiver;
    private EditText editTextSearchAddressDialog;
    private Button buttonSearchAddressDialog;
    private ListView listViewAddressDialog;
    private Dialog addressDialog;
    private TextView textViewResultsAddressDialog;
    private Address foundAddress;
    private boolean hasFound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parking_spot);

        firebaseAuth = FirebaseAuth.getInstance();
        parkingSpotDatabase = FirebaseDatabase.getInstance().getReference("parkingSpots");

        editTextName = findViewById(R.id.editTextName);
        buttonFindParkingAddress = findViewById(R.id.buttonFindParkingAddress);
        editTextDescription = findViewById(R.id.editTextDescription);

        buttonCreateListing = findViewById(R.id.buttonCreateListing);
        buttonCreateListing.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                createParkingSpot();
            }
        });

        buttonHomepage = findViewById(R.id.buttonHomepage);
        buttonHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), HomepageActivity.class));
            }
        });

        buttonFindParkingAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddressDialog();
            }
        });

        hasFound = false;
        receiver = new AddressResultReceiver(new Handler());
    }

    private void createParkingSpot(){
        if(hasFound){
            // get the values from all the fields and save them to the Firebase db
            String parkingSpotName = editTextName.getText().toString().trim();
            String parkingSpotAddress = buttonFindParkingAddress.getText().toString().trim();
            String parkingSpotDescription = editTextDescription.getText().toString().trim();

            FirebaseUser user = firebaseAuth.getCurrentUser(); //get user

            if(TextUtils.isEmpty(parkingSpotName)  || TextUtils.isEmpty(parkingSpotDescription)) {
                Toast.makeText(CreateParkingSpotActivity.this, "Please Enter All Fields", Toast.LENGTH_SHORT).show();
            } else {
                String _id = parkingSpotDatabase.push().getKey();

                ParkingSpot newParkingSpot = new ParkingSpot(_id, parkingSpotName, parkingSpotAddress, parkingSpotDescription, user.getUid(), user.getEmail());
                parkingSpotDatabase.child(_id).setValue(newParkingSpot);

                Toast.makeText(CreateParkingSpotActivity.this, "Created Parking Spot", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(CreateParkingSpotActivity.this, HomepageActivity.class));
            }
        }
        else{
            Toast.makeText(CreateParkingSpotActivity.this, "Find Parking Spot Address!", Toast.LENGTH_SHORT).show();
        }
    }
    public void showAddressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.activity_verify_address_for_spot, null);

        listViewAddressDialog = view.findViewById(R.id.listViewAddressDialogForSpot);
        editTextSearchAddressDialog = view.findViewById(R.id.editTextSearchAddressDialogForSpot);
        buttonSearchAddressDialog =  view.findViewById(R.id.buttonSearchAddressDialogForSpot);
        textViewResultsAddressDialog = view.findViewById(R.id.textViewResultsAddressDialogForSpot);

        buttonSearchAddressDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String addressQuerry = editTextSearchAddressDialog.getText().toString();
                if(!addressQuerry.equals("")){
                    //fetch
                    System.out.println("hello?");
                    startIntentService(addressQuerry);
                }
            }
        });

        addressDialog = builder.setView(view)
                .setTitle("Search Destination Address")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create();
        addressDialog.show();
        listViewAddressDialog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(final AdapterView parent, View v, final int position, long id) {
                foundAddress = (Address) parent.getItemAtPosition(position);
                updateWithFoundAddress();
                addressDialog.dismiss();
            }
        });
    }

    public void createAddressListViewDialog(ArrayList<Address> addrs){
        if(addrs == null || addrs.isEmpty()){
            textViewResultsAddressDialog.setText("No Results. Try to Be More Exact");
        }
        else{
            textViewResultsAddressDialog.setText("Results");
            System.out.println("jj");
            AddressList adapter = new AddressList(CreateParkingSpotActivity.this, addrs);
            listViewAddressDialog.setAdapter(adapter);
        }

    }

    public void updateWithFoundAddress(){
        hasFound = true;
        ArrayList<String> addressFrags = new ArrayList<>();
        for(int i = 0; i <= foundAddress.getMaxAddressLineIndex(); i++){
            addressFrags.add(foundAddress.getAddressLine(i));
        }
        buttonFindParkingAddress.setText(TextUtils.join(System.getProperty("line.separator"), addressFrags));
    }

    private void startIntentService(String addr){
        Intent intent = new Intent(getApplicationContext(), GeocodeIntentService.class);
        intent.putExtra("RECEIVER", receiver);
        intent.putExtra("ADDRESS", addr);
        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler){
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData){

            final ArrayList<Address> addrs = resultData.getParcelableArrayList("ADDRESSES");
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    createAddressListViewDialog(addrs);
                }
            });

        }
    }
}

