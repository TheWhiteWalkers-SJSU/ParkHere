package com.thewhitewalkers.parkhere;

import android.content.Intent;
import android.content.res.Resources;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polygon;
import com.google.android.gms.maps.model.PolygonOptions;

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MapActivity.class.getSimpleName();

    private AddressResultReceiver receiver;
    private EditText editText;
    private Button fetchButton;
    private Button textPlace;
    private GoogleMap map;
    private Address address1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map_activity);

        // Get the SupportMapFragment and register for the callback
        // when the map is ready for use.

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        receiver = new AddressResultReceiver(new Handler());


        editText = (EditText) findViewById(R.id.editText);
        textPlace = (Button) findViewById(R.id.textPlace);
        fetchButton = (Button) findViewById(R.id.button1);


        fetchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startIntentService();
            }
        });
        textPlace.setEnabled(false);
        textPlace.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setMap();
            }
        });
    }

    /**
     * Manipulates the map when it's available.
     * The API invokes this callback when the map is ready for use.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            this, R.raw.style_json));

            if (!success) {
                Log.e(TAG, "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(TAG, "Can't find style. Error: ", e);
        }

        //(37.335187, -121.881072)
        map = googleMap;
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(37.35, -122.0)));
        // Instantiates a new Polygon object and adds points to define a rectangle
        double edge = .00009009;
        double lat = 37.35;
        double lng = -122.0;
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
    private void setMap(){
        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(address1.getLatitude(), address1.getLongitude())));
    }

    private void startIntentService(){
        Intent intent = new Intent(getApplicationContext(), GeocodeIntentService.class);
        intent.putExtra("RECEIVER", receiver);
        intent.putExtra("ADDRESS", editText.getText().toString());

        startService(intent);
    }

    private class AddressResultReceiver extends ResultReceiver {
        public AddressResultReceiver(Handler handler){
            super(handler);
        }

        @Override
        protected void onReceiveResult(int resultCode, final Bundle resultData){
            if(resultCode == 1){
                final Address addr = resultData.getParcelable("result");
                runOnUiThread(new Runnable(){
                    @Override
                    public void run() {
                        textPlace.setText("Address" + resultData.getString("result"));
                        address1 = resultData.getParcelable("ADDRESS");
                        textPlace.setEnabled(true);


                    }
                });
            }
            else{
                runOnUiThread(new Runnable(){
                    @Override
                    public void run(){
                        textPlace.setText("No results!");
                    }
                });
            }
        }
    }
}

