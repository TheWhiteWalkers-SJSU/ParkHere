package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

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

    private Button buttonBackToSearch;
    private GoogleMap map;
    private Address querriedAddress;
    private ArrayList<Listing> searchResults;
    private ArrayList<Polygon> squares;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_activity);

        Intent mapIntent = getIntent();
        querriedAddress = (Address) mapIntent.getParcelableExtra("ADDRESS");
        //searchResults = (ArrayList<Listing>) mapIntent.getSerializableExtra("RESULTS");

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        buttonBackToSearch = (Button) findViewById(R.id.buttonBackToSearch);


        buttonBackToSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //startIntentService();
            }
        });
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
        double lat = querriedAddress.getLatitude();//37.35;
        double lng = querriedAddress.getLongitude(); //-122.0;
        //(37.335187, -121.881072)
        map = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(lat, lng)));
        // Instantiates a new Polygon object and adds points to define a rectangle
        double edge = .00009009;

        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(lat, lng),
                        new LatLng(lat + edge, lng),
                        new LatLng(lat + edge, lng + edge),
                        new LatLng(lat, lng + edge),
                        new LatLng(lat, lng));
        //rgba(226, 8, 8, 0.5)
        //(Color.BLACK & 0x00FFFFFF) | 0x40000000
        //0x00FFFFFF
        //0x40000000
        rectOptions.fillColor(0x40000000);
        rectOptions.strokeColor(0x40000000);
        Polygon polygon = map.addPolygon(rectOptions);


        double lat2 = 37.3501;
        double lng2 = -122.001;
        PolygonOptions rectOptions2 = new PolygonOptions()
                .add(new LatLng(lat2, lng2),
                        new LatLng(lat2 + edge, lng2),
                        new LatLng(lat2 + edge, lng2 + edge),
                        new LatLng(lat2, lng2 + edge),
                        new LatLng(lat2, lng2));
        //rgba(226, 8, 8, 0.5)
        //(Color.BLACK & 0x00FFFFFF) | 0x40000000
        //0x00FFFFFF
        //0x40000000
        rectOptions2.fillColor(0x40000000);
        rectOptions2.strokeColor(0x40000000);
        Polygon polygon2 = map.addPolygon(rectOptions2);

    }
}

