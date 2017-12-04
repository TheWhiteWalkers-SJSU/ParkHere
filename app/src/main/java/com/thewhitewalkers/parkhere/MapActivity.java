package com.thewhitewalkers.parkhere;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapActivity.class.getSimpleName();

    private GoogleMap map;
    private Address querriedAddress;
    //private ArrayList<Listing> searchResults;
    private ArrayList<ParkingSpot> searchResults;
    private ArrayList<Polygon> squares;

    private TextView textViewTitleDialog;
    private Button buttonViewListingDialog;

    final int TWENTY = 0x20000000; //0 bookings
    final int FOURTY = 0x40000000; // 1-3
    final int SIXTY = 0x60000000; //4-7
    final int EIGHTY = 0x80000000; //8 =<

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_activity);

        Intent mapIntent = getIntent();
        querriedAddress = (Address) mapIntent.getParcelableExtra("ADDRESS");
        //searchResults = (ArrayList<Listing>) mapIntent.getSerializableExtra("RESULTS");
        searchResults = (ArrayList<ParkingSpot>) mapIntent.getSerializableExtra("RESULTS");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        try{
            // Style Json in Raw folder
            boolean success = googleMap.setMapStyle( MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));

            if(!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch(Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }
        double lat = querriedAddress.getLatitude();
        double lng = querriedAddress.getLongitude();

        map = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        // Instantiates a new Polygon object and adds points to define a rectangle
        double edge = .00009009;

        squares = new ArrayList<>();
        double p_lat = 0;
        double p_lng = 0;

        for(int i = 0; i < searchResults.size(); i++){
            ParkingSpot p = searchResults.get(i);
            if(!p.getOwnerEmail().equals("waddup@gmail.com")){


            p_lat = p.getLat();
            p_lng = p.getLng();

            PolygonOptions rectOptions = new PolygonOptions()
                    .add(new LatLng(p_lat, p_lng),
                            new LatLng(p_lat + edge, p_lng),
                            new LatLng(p_lat + edge, p_lng+ edge),
                            new LatLng(p_lat, p_lng + edge),
                            new LatLng(p_lat, p_lng));

            // Frequency Overlay
            int color = TWENTY;
            int freq = p.getPriorBookings();
            if(freq >= 1 && freq <= 3){ // 1-3
                color = FOURTY;
            }
            else if(freq >= 4 && freq <= 7){ //4-7
                color = SIXTY;
            }
            else if(freq >= 8){ //8 =<
                color = EIGHTY;
            }

            rectOptions.fillColor(color);
            rectOptions.strokeColor(color);
            Polygon polygon = map.addPolygon(rectOptions);
            int tag = i;
            polygon.setTag(tag);
            polygon.setClickable(true);
            squares.add(polygon);
            }
        }
        map.setOnPolygonClickListener(new GoogleMap.OnPolygonClickListener() {
            @Override
            public void onPolygonClick(Polygon polygon) {
                int tag = (int) polygon.getTag();
                showListingDialog(searchResults.get(tag));
            }
        });
    }
    public void showListingDialog(ParkingSpot spot) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View view = inflater.inflate(R.layout.map_dialog, null);

        textViewTitleDialog = view.findViewById(R.id.textViewTitleDialog);
        buttonViewListingDialog =  view.findViewById(R.id.buttonViewListingDialog);

        textViewTitleDialog.setText(spot.getAddress());

        buttonViewListingDialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //intent to see that listing...
            }
        });

        builder.setView(view)
                .setTitle("Parking Spot")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                }).create().show();
    }
}

